define( function () {
	
	//=======================================================
	// 获取基础组件
	//=======================================================
	var base = require( "app/base" )
		, message = base.message
		, tools = base.tools
		, uuid = tools.uuid
		, format = tools.format;
	
	var SETTINGS = {
		name: "file", //后台接收name
		headers: {
			busiNo: false, //业务对象UUID
			busiTyp: false,//业务对象类型码值  如请假单附件、贷款附件
			attTyp: "71000001" //默认文件类型 如身份证正面、身份正反面  这个码值还没确定
		},
		upload: true, //是否拥有上传功能 预览使用
		download: true,//是否拥有下载功能
		remove: true,//是否拥有删除功能
		accept: "*,image/*,.rtf,.doc,.docx,.csv,.xls,.xlsx", //默认选择类型 只是系统弹出框的文件类型分类而已
		maxFiles: -1, //最大文件数 不包括内置文件数 
		internals: [ //内置文件
	        /*{ attTyp: "", accept: "" }*/
		  /* { filename: "身份证正面", accept: "image/*", attTyp: "71000002" },
		   { filename: "身份证反面", accept: "image/*", attTyp: "71000003" }*/
        ],
        //internals: 4, //内置文件可为数量，初始化4个内置文件上传对象
        events: {
        	uploadAdd: { //可以用来做文件类型判断 判断是否上传的文件类型不一致
				data: {},
				handler: function( event, fileupload, internal, index ) {
					//event
					//file fileupload对象
					//file.extension 文件扩展名
					//file.originFileName 文件名
					//internal 是否内置
					//index 内置文件索引 或 非内置文件索引
					
					//return 返回值只为boolean false 才能阻止上传 其它不阻止上传
				}
			}
        }
	};
	
	function Global( vars ) {
		
		//=======================================================
		// 当前组件
		//=======================================================
		var that = this; //全局对象
		var vars = this.vars = {};//全局变梁
		var handlers = this.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			
			var settings = that.vars.settings = $.extend( true, {}, SETTINGS, that.params );
			that.vars.$files = that.selector.find( "#files" );
			that.vars.$internalFiles = that.selector.find( "#internalFiles" );
			
			this.load();
			this.layout();
		};
		
		that.load = function() {
			var settings = that.vars.settings;
			
			settings.isInAtt = "13900002"; //是否内置 默认不能修改
			settings.sort = 1000;
			
			if ( typeof settings.maxFiles != "number" ) {
				settings.maxFiles = -1;
			} else {
				settings.maxFiles = Math.ceil( settings.maxFiles );
				if ( settings.maxFiles < -1 ) {
					settings.maxFiles = -1;
				}
			}
			
			if ( typeof settings.filename != "string" ) {
				settings.filename = "";
			}
			
			if ( settings.upload ) {
				that.vars.$internalFiles.empty();
			}
			
			that.loading.show();
			
			var $file, $files = that.vars.$files, fileupload;
			
			var files = settings.internals, file;
			
			var base_file = {
				isInAtt: "13900001",
				accept: settings.accept,
				attTyp: settings.attTyp,
				filename: "",
				internal: true
			};
			
			
			if ( typeof files == "object" && files instanceof Array ) {
				for( var index = 0; index < files.length; index++ ) {
					file = $.extend( true, {}, base_file, files[ index ] );
					file.sort = index;
					
					if ( typeof file.filename != "string" ) {
						file.filename = "";
					}
					
					that.handlers.addFile( file );
				}
			} else if ( typeof files == "number" ) {
				for( var index = 0; index < files; index++ ) {
					file = $.extend( true, {}, base_file );
					file.sort = index;
					that.handlers.addFile( file );
				}
			}
			
			
			$.ajax( {
				url: "pub/asysatt/list",
				type: "post",
				data: {
					"params[busiNo]": settings.headers.busiNo,
					"params[busiTyp]": settings.headers.busiTyp
				},
				success: function( data ) {
					if ( !data.success ) {
						message.error( data.msg );
					}
					var items = data.list, item, extension, internal;
					if ( data.list.length != 0 ) {
						that.vars.$internalFiles.empty();
					}
					for ( var index = 0; index < items.length; index++ ) {
						item = items[ index ];
						
						filename = item.oldAtt;
						extension = item.attFile || "";
						download = "pub/asysatt/download/" + item.newAtt;
						thump = "pub/asysatt/download/thumb/" + item.newAtt;
						if ( typeof extension == "string" && extension.length > 0 ) {
							extension = extension.toLowerCase();
							filename += "." + extension;
							download += "." + extension;
							thump += "." + extension;
						} else {
							extension = "";
						}
						
						
						
						$file = null;
						
						if ( typeof item.sort != "number" || typeof item.isInAtt != "string" || item.isInAtt != "13900001" ) {
							item.isInAtt = "13900002";
							internal = false;
						}
						
						if ( typeof item.sort == "number" ) {
							if ( settings.sort < item.sort) {
								settings.sort = item.srot;
							}
						}
						
						if ( item.isInAtt == "13900001" ) {
							$file = $files.find( "input[type=file][name]" ).eq( item.sort );
							internal = true;
						}
						
						if ( $file == null || $file.length == 0 ) {
							fileupload = that.handlers.addFile();
						} else {
							fileupload = $file.fileUpload({});
						}
						fileupload.echo( {
							filename: filename,
							extension: extension,
							download: download,
							thump: thump
						} );
						fileupload.$input.data( "item", item );
					}
					
					if ( typeof settings.upload == "boolean" && settings.upload == true ) {
						var count = $files.find( "input[type=file][internal=false]" ).length;
						if ( settings.maxFiles == -1 || settings.maxFiles > count ) {
							that.handlers.addFile();
						}
					}
					
				},
				complete: function() {
					that.loading.hide();
				}
			} );
			
		};
		
		//布局
		that.layout = function() {};
		
		handlers.addFile = function( file ) {
			var that = this.global(),
				$files = that.vars.$files,
				$internalFiles = that.vars.$internalFiles,
				settings = that.vars.settings;
			
			if ( typeof file == "undefined" ) {
				file = {};
				file.isInAtt = settings.headers.isInAtt;
				file.attFile = settings.headers.attFile;
				file.accept = settings.accept;
				file.sort = settings.sort++;
				file.internal = false;
			}
			
			return $( format( '<input type="file" name="{0}" index="{1}" internal="{2}"/>', [ settings.name, file.sort, file.internal ] ) )
				.appendTo( file.internal ? $internalFiles :  $files )
				.fileUpload( {
					accpets: settings.accpets,
					upload: settings.upload,
					download: settings.download,
					remove: settings.remove,
					filename: file.filename,
					accept: file.accept,
					url: "pub/asysatt/upload",
					type: "POST",
					dataType: "json",
					contentType: "multipart/form-data",
					params: {
						busiNo: settings.headers.busiNo,
						busiTyp: settings.headers.busiTyp,
						isInAtt: file.isInAtt,
						sort: file.sort,
						attFile: file.attFile,
						oldAtt: file.filename
					},
					events: {
						uploadAdd: {
							handler: function( event, file ) { 
								var internal = $( this ).is( "[internal=true]" );
								var index = $( this ).index();
								if ( !internal ) { index -= 1; }
								event.data = settings.events.uploadAdd.data;
								var r = settings.events.uploadAdd.handler( event, file, internal, index );
								if ( typeof r == "boolean" && r == false ) {
									return r;
								}
								if ( internal == false ) {
									var count = $files.find( "input[type=file][internal=false]" ).length;
									if ( settings.maxFiles == -1 || settings.maxFiles > count ) {
										that.handlers.addFile();
									}
								}
							}
						},
						uploadSuccess: {
							handler: function( event, file, data ) {
								var item = data.t;
								$( this ).data( "item", item );
								var extension = item.attFile || "";
								download = "pub/asysatt/download/" + item.newAtt;
								if ( typeof extension == "string" && extension.length > 0 ) {
									extension = extension.toLowerCase();
									download += extension;
								} else {
									extension = "";
								}
								 + extension;
								$( this ).data( "item", item );
								file.echo( {
									//filename: item.filename,
									extension: extension,
									download: download,
									//thump: item.thump
								} );
							}
						},
						remove: {
							handler: function( events, file ) {
								var $loading = file.$loading;
								
								var item = $( this ).data( "item" );
								
								$loading.show();
								
								$.ajax( {
									url: "pub/asysatt/delete/" + item.id,
									type: "POST",
									complete: function() {
										$loading.hide();
									},
									success: function( data ) {
										if ( !data.success ) {
											return message.error( data.msg );
										}
										file.remove();
									}
								} );
								
								return false;
							}
						}
					}
				} );
			
		};
		
		
	};
	
	return Global;
});
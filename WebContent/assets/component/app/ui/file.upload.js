//###################################
/**
 * file.upload 上传
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//遗留优化 iframe 页面返回系统自定义异常 获取状态码
//###################################
define( [ "app/base" ], function() {
	
	var $ = require( "jquery" )
	, base = require( "app/base" )
	, format = base.tools.format
	, uuid = base.tools.uuid
	, message = base.message
	, log = base.log;
	
	var FileReader = window.FileReader || false
		, selection = document.selection || false
		, extension_imgs = [ "PNG", "JPG", "JPEG", "GIF", "BMP" ]
		, extension_excels = [ "XLS", "XLSX" ]
		, extension_words = [ "DOC", "DOCX" ]
		, extension_zips = [ "ZIP", "RAR" ];
	
	
	function FileUpload( target, options ) {
		this.$input = $( target );
		this.settings = $.extend( true, {}, this.__settings__, options );
		this.__init__();
	};
	
	FileUpload.prototype.__settings__ = {
		id: "", //容器id
		css: "", //容器类样式
		style: "",	//容器内嵌样式
		size: "md", //bootstrap 组件大小 只为[sm, md, lg]
		theme: "default", //组件主题
		cssCol: "", //容器布局 bootstrap响应式布局,
		url: "pub/asysatt/upload",
		type: "POST",
		params: {},
		dataType: "json",
		contentType: "multipart/form-data",
		accept: "*",
		filename: "",
		upload: true,
		download: true,
		remove: true,
		events: {
			uploadAdd: {
				data: {},
				handler: function( event, file ) {
					
				}
			},
			uploadError: {
				data: {},
				handler: function( event, file, data ) {
					
				}
			},
			uploadSuccess: {
				data: {},
				handler: function( event, file, data ) {
					
				}
			},
			remove:  {
				data: {},
				handler: function( event, file, item ) {
				}
			},
		}
	};
	
	FileUpload.prototype.__init__ = function() {
		var settings = this.settings
		, $input = this.$input;
		
		if ( typeof settings.id != "string" || settings.id.length == 0 ) {
			settings.id = uuid();
		}
		
		if ( typeof settings.filename != "string") {
			settings.filename = "";
		}
		
		if ( typeof settings.accept != "string" || settings.accept.length == 0 ) {
			settings.accept = "*";
		}
		
		$input.addClass( "ignore" ).attr( "autocomplete", "off" ).attr( "title", settings.filename || "请选择附件" ).attr( "id", settings.id ).attr( "accept", settings.accept );
		
		
		
		var $wrapper = this.$wrapper = $input.wrap( format( '<div id="{id}" class="{cssCol} {css} file-upload-{theme}" style="{style}"></div>', settings ) ).parent();
		
		var $container = this.$container = $( format( '<div class="container"></div>' ) ).appendTo( $wrapper );
		
		var name = "FILE-UPLOAD-FRAME" + settings.id;
		
		var $iframe = this.$iframe = $( format( '<iframe id="{0}" name="{0}" class="sr-only"></iframe>', [ name ] ) ).appendTo( $wrapper );
		var $form = this.$form = $( format( '<form method="post" action="{1}" enctype="multipart/form-data" target="{0}" class="sr-only1"></form>', [ name, settings.url ] ) ).appendTo( $container );
		//var $formSubmit = this.$formSubmit = $( '<input type="submit" value="submit"/>' ).appendTo( $form );
		
		$form.data( "validator", { settings: {} } );
		
		var params = settings.params;
		for ( var k in params ) {
			$form.append( format( '<input type="hidden" name="{0}" value="{1}" />', [ k, params[ k ] ] ) );
		}
		
		$form.append( $input );
		//var $container = this.$container = $input.addClass( "container" );
		
		var $loading = this.$loading = $( '<div class="loading"><i class="fa fa-spin fa-spinner fa-3x"></i></div>' ).appendTo( $container );
		
		var $upload = this.$upload = $( format( "<div class=\"add\"><i class=\"fa fa-plus fa-3x\"></i></div>" ) ).appendTo( $container );
		
		var $preview = this.$preview = $( format( "<div class=\"preview\"><img src=\"data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==\"/></div>" ) ).appendTo( $container );
		var $previewImg = this.$previewImg = $preview.find( "img" );
		var $previewIcon = this.$previewIcon = $( format( '<div class="icon"><i class="fa fa-file-o fa-3x"></i></div>' ) ).appendTo( $preview ).find( "i" );
		
		var $tools = this.$tools = $( format( [ 
		                                        '<div class="tools">',
		                                        '	<a class="download" href="javascript:;">',
		                                        '		<i class="fa fa-download fa-lg"></i>',
		                                        '	</a>',
		                                        '	<a class="remove" href="javascript:;">',
		                                        '		<i class="fa fa-remove fa-lg"></i>',
		                                        '	</a>',
		                                        '</div>'
		                                      ] ) ).appendTo( $preview );
		
		this.preview = this.$preview[ 0 ];
		this.previewImg = this.$previewImg[ 0 ];
		
		var $title = this.$title = $( format( '<h6>{0}</h6>', [ settings.filename || "请选择附件" ] ) );
		$wrapper.append( $title );
		
		
		if ( typeof settings.upload != "boolean" || settings.upload != true ) {
			$input.addClass( "sr-only" );
			settings.upload = false;
		}
		if ( typeof settings.download != "boolean" || settings.download != true ) {
			$tools.find( "a.download" ).hide();
			settings.download = false;
		}
		
		if ( typeof settings.remove != "boolean" || settings.remove != true ) {
			$tools.find( "a.remove" ).hide();
			settings.remove = false;
		}
		
		
		this.__event__();
	};
	
	FileUpload.prototype.__event__ = function() {
		var that = this
		, settings = this.settings
		, $iframe = this.$iframe
		, $form = this.$form
		, $input = this.$input
		, $wrapper = this.$wrapper
		, $upload = this.$upload
		, $preview = this.$preview
		, preview = this.preview
		, previewImg = this.previewImg
		, $previewImg = this.$previewImg
		, $previewIcon = this.$previewIcon
		, $tools = this.$tools
		, $title = this.$title
		, $loading = this.$loading;
		
		$form.bind( "submit", function( event ){
			$loading.show();
			return true;
		} );
		
		$iframe.bind( "load", function( event ) {
			that.__is_up_loading__ = false;
			var texts = [];
			$( this ).contents().each( function() {
				 texts.push( $( this ).text() );
			} );
			
			var data = texts.join();
			data = $.trim( data );
			
			$loading.hide();
			
			if ( data.length == 0 ) {
				return;
			}
			
			if ( settings.dataType.toUpperCase() == "JSON" ) {
				data = ( new Function( "return " +  data + ";" ) )();
			}
			
			if ( data.success ) {
				$input.trigger( "fileupload.upload.success", [ that, data ] );
				$tools.show();
			} else {
				message.error( data.msg );
				$input.trigger( "fileupload.upload.error", [ that, data ] );
			}
			
		} );
		
		$previewImg.bind( "error", function( event ) {
			this.src = "data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==";
			$previewIcon.show();
			return false;
		} );
		
		var fileReader;
		$input.bind( "change", function( event ) {
			if ( typeof that.__is_up_loading__ == "boolean" && that.__is_up_loading__ == true ) {
				return false;
			}
			var vals = $input.val().split( "\\" );
			if ( vals.length <= 0 ) {
				return false;
			}
			
			if ( vals.length >= 2 ) {
				that.originFileName = vals[ vals.length - 2 ];
			} else {
				that.originFileName = vals[ vals.length - 1 ];
			}
			
			var value = vals.pop();
			
			vals = value.split( "." );
			
			var extension;
			if ( vals.length <= 1 ) {
				extension = "";
			} else {
				extension = vals.pop().toUpperCase();
			}
			
			var filename = that.originFileName;
			$wrapper.attr( "title", filename );
			
			if ( typeof settings.filename == "string" && settings.filename.length != 0 ) {
				filename = settings.filename;
			}
			if ( filename.length >= 18 ) {
				filename = filename.substring( 0, 15 ) + "...";
			}
			$title.text( filename );
			
			that.extension = extension.toLowerCase();
			
			$preview.show();
			var vals = $input.val().split( "\\" );
			if ( vals.length > 0 ) {
				vals = vals.pop();
				$wrapper.attr( "title", vals );
				if ( vals.length >= 18 ) {
					vals = vals.substring( 0, 15 ) + "...";
				}
				$title.text( vals );
			}
			
			
			var isImagePreview = true;
			
			if ( $.inArray( extension, extension_words ) != -1 ) {
				$previewIcon.addClass( "fa fa-file-word-o" );
				isImagePreview = false;
			} else if ( $.inArray( extension, extension_excels ) != -1 ) {
				$previewIcon.addClass( "fa fa-file-excel-o" );
				isImagePreview = false;
			} if ( $.inArray( extension, extension_zips) != -1 ) {
				$previewIcon.addClass( "fa fa-file-zip-o" );
				isImagePreview = false;
			} if ( $.inArray( extension, extension_imgs) != -1 ) {
				$previewIcon.addClass( "fa fa-file-image-o" ).hide();
			} else {
				$previewIcon.addClass( "fa fa-file-o" );
				isImagePreview = false;
			}
			
			
			if ( isImagePreview && FileReader ) {
				if ( typeof fileReader == "undefined" ) fileReader = new FileReader();
				fileReader.readAsDataURL( this.files[ 0 ] );
				fileReader.onload = function ( event ) {
					that.absolutePath = $input.val();
					previewImg.src = event.target.result;
				}; 
			} else if ( isImagePreview && selection ) {
				this.select(); 
				document.body.focus();
				var uri = document.selection.createRange().text; 
				if ( typeof uri == "string" ) {
					uri = $.trim( uri );
					if ( uri.length == 0 ) uri = $input.val();
				}
				preview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				that.absolutePath = preview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = uri;
				document.selection.empty();
			}
			
			event.data = settings.events.uploadAdd.data;
			var valid = settings.events.uploadAdd.handler.call( $input[ 0 ], event, that );
			
			if ( typeof valid  == "boolean" && valid == false ) {
				return false;
			}
			that.upload();	
		} );
		
		$input.bind( "fileupload.upload.success", settings.events.uploadSuccess.data, settings.events.uploadSuccess.handler );
		$input.bind( "fileupload.upload.error", settings.events.uploadError.data, settings.events.uploadError.handler );
		
		$tools.find( "a.remove" ).bind( "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			event.target = $input[ 0 ];
			event.data = settings.events.remove.data;
			
			var r = settings.events.remove.handler.call( $input[ 0 ], event, that );
			
			if ( typeof r == "boolean" && r == false ) {
				return false;
			}
			
			that.remove();
			
			return false;
			
		} );
	};
	
	FileUpload.prototype.echo = function( data ) {
		var that = this
		, settings = this.settings
		, $iframe = this.$iframe
		, $form = this.$form
		, $input = this.$input
		, $wrapper = this.$wrapper
		, $upload = this.$upload
		, $preview = this.$preview
		, preview = this.preview
		, previewImg = this.previewImg
		, $previewIcon = this.$previewIcon
		, $tools = this.$tools
		, $title = this.$title
		, $loading = this.$loading;
	
		
		var item = data;
		
		if ( typeof item != "object" ) return;
		
		item = $.extend( true, {
			filename: false,
			extension: false
		}, data );
		
		//文件名
		if ( typeof item.filename == "string" && item.filename.length != 0 ) {
			var filename = item.filename;
			$wrapper.attr( "title", filename );
			
			if ( typeof settings.filename == "string" && settings.filename.length != 0 ) {
				filename = settings.filename;
			}
			
			if ( filename.length >= 18 ) {
				filename = filename.substring( 0, 15 ) + "...";
			}
			
			this.filename = item.filename;
			this.extension = item.extension.toLowerCase();
			
			$title.html( filename );
		}
		
		var extension = item.extension.toUpperCase()
			, isImagePreview = true;	
		if ( typeof extension == "string" ) {
			if ( $.inArray( extension, extension_words ) != -1 ) {
				$previewIcon.addClass( "fa fa-file-word-o" );
				isImagePreview = false;
			} else if ( $.inArray( extension, extension_excels ) != -1 ) {
				$previewIcon.addClass( "fa fa-file-excel-o" );
				isImagePreview = false;
			} if ( $.inArray( extension, extension_zips) != -1 ) {
				$previewIcon.addClass( "fa fa-file-zip-o" );
				isImagePreview = false;
			} if ( $.inArray( extension, extension_imgs) != -1 ) {
				$previewIcon.addClass( "fa fa-file-image-o" ).hide();
			} else {
				$previewIcon.addClass( "fa fa-file-o" );
				isImagePreview = false;
			}
		} else {
			isImagePreview = false;
		}
		$preview.show();
		if ( isImagePreview ) {
			if ( typeof item.thump == "string" && item.thump.length != 0 ) {
				previewImg.src = item.thump;
			}
		}
		
		$tools.show();
		
		if ( settings.download && typeof item.download == "string" && item.download.length != 0 ) {
			$tools.find( "a.download" ).attr( "href", item.download ).show();
		} else {
			$tools.find( "a.download" ).hide();
		}
		
		$input.data( "item", item );
	};
	
	FileUpload.prototype.upload = function( data ) {
		var that = this
			, settings = this.settings
			, $input = this.$input
			, $loading = this.$loading
			, $form = this.$form;
		
		if ( typeof data == "object" ) {
			var $o;
			for ( var key in data ) {
				$o = $form.find( format( "input[name=\"{0}\"]", [ key ] ) );
				if ( $o.length == 0 ) {
					$o = $( format( "<input type=\"hidden\" name=\"{0}\"/>", [ key ] ) ).appendTo( $form );
				}
				$o.val( data[ key ] );
			}
		};
		if ( typeof that.__is_up_loading__ == "boolean" && that.__is_up_loading__ == true ) {
			return false;
		}
		
		that.__is_up_loading__= true;
		
		$form.trigger( "submit" );
	};
	
	FileUpload.prototype.remove = function() {
		this.__is_up_loading__ = true;
		this.__remove__();
		this.__is_up_loading__ = false;
	};
	
	FileUpload.prototype.__remove__ = function() {
		var that = this
			, $input = this.$input
			, settings = this.settings
			, $wrapper = this.$wrapper
			, $loading = this.$loading
			, $preview = this.$preview
			, preview = this.preview
			, previewImg = this.previewImg
			, $tools = this.$tools
			, $title = this.$title;
		var item = $input.data( "item" );
		$title.html( settings.filename || "请选择附件" );
		$wrapper.attr( "title", settings.filename || "请选择附件" );
		$input.val("");
		$preview.hide();
		$tools.hide();
		
		
		if ( FileReader ) {
			if ( typeof fileReader == "undefined" ) fileReader = new FileReader();
			previewImg.src = "data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==";
		} else if ( isImagePreview && selection ) {
			preview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = "data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==";
		}
		
	};
	
	FileUpload.prototype.destory = function() {
		this.$wrapper.remove();
	};
	
	$.fn.fileUpload = function( options ) {
		if ( $( this ).is( "[ui]" ) || $( this ).is( ".sr-only" ) ) {
			return $( this ).data( "fileUpload" );
		}
		
		$( this ).attr( "ui", "fileUpload" );
		 
		var opts = $( this ).attr( 'options' ) || '';
		if ( typeof opts == "string" && opts.length > 0 ) {
			opts = $.trim( opts );
			if ( opts.indexOf( "{" ) != 0 ) {
				opts = { txtLabel: opts };
			} else {
				try {
					opts =  (new Function("return " +  opts ))();
				} catch (e) { 
					opts = {};
				}
			}
		} else opts = {};
		
		opts = $.extend(true, {}, options, opts);
		var fileUpload = new FileUpload( this, opts );
		$( this ).data( "fileUpload", fileUpload );
		return fileUpload;
	};
	
} );
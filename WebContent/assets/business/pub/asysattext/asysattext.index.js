define(function () { 

	function Global( vars ) {
		
		//=======================================================
		//获取基础组件
		//=======================================================
		var $ = require( "jquery" )
			, base = require( "app/base" )
			, message = base.message
			, tools = base.tools
			, code = base.code;
		
		
		var moment = require( "moment" );
		
		//缓存码值
		code.cache( "anncstat,sex,certType" );
		
		//=======================================================
		//当前组件
		//=======================================================
		var that = this;
		var vars = that.vars = $.extend( true, {}, vars );//全局变梁
		var handlers = that.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.layout(); 
		};
		
		//初始化远程请求处理
		that.load = function() {};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			that.selector.find( ".input" ).input( {} );//实例input插件
			
			that.selector.find( "input[name=attTyp]" ).select( {//实例下拉插件
				code: { type: "anncstat" }
			} );
			
			
			that.selector.find( "#addBtn" ).bind( "click", function( event ) {
				that.handlers.add();
			} );
			
			that.selector.find( "#editBtn" ).bind( "click", function( event ) {
				var items = that.vars.gridVar.selectedRows();
				that.handlers.edit( items );
				return false;
			} );
			
			that.selector.find( "#delBtn" ).bind( "click", function( event ) {
				var items = that.vars.gridVar.selectedRows();
				that.handlers.del( items );
				return false;
			} );
			
			var config = {
				remote: {
		        	url: "pub/asysattext/list",
		            params: {}
		        },
		        multi: false,
		        page: true,
		        query: {
		        	target: that.selector.find( ".grid-query" )
		        },
		        plugins: [],
		        events: {},
		        customEvents: []
			};
			config.cols = cols = [];
			cols[ cols.length ] = { title: "文件名称", name: "attName", width: "180px", lockWidth: true };
			cols[ cols.length ] = { title: "文件类型", name: "attTyp", width: "180px", lockWidth: true  ,renderer: function( val, item, rowIndex ) {
				return base.code.getText( "anncstat", val );}
			};
			cols[ cols.length ] = { title: "上传用户姓名", name: "instUserName", width: "180px", lockWidth: true };
			cols[ cols.length ] = { title: "上传时间", name: "instDate", width: "180px", lockWidth: true ,renderer: function( val, item, rowIndex ) {
				return moment( val ).format( "YYYY-MM-DD" );}
			};
			cols[ cols.length ] = { title: "操作", name: "id", width: "120px", lockWidth: true, renderer: function( val, item, rowIndex ) {
				return format(  [ '<a href="javascript:;" class="download"><i class="fa fa-eye"></i> 查看与下载文档</a>' ] );
			} };
			config.events.click = {
				handler: function( event, item, rowIndex ) {
					//item 当期行数据
					//rowIndex 当前行索引
					
					//处理其他逻辑
				}
			};
		
			config.customEvents.push( {
				target: ".download",
				handler: function( event, item, rowIndex ) {
					that.modal.open( {
						title: "文档信息",
						url: "pub/asysattext/view",
						size: "modal-lg",
						params: { item:item },
						events: {
							hiden: function( closed, data ) {
								if ( !closed ) return;
								that.vars.gridVar.load();
							}
						}
					} );
				}
//				handler: function( event, item, rowIndex ) {
//					that.dialog.dialog( {
//						title: "附件下载",
//						content: tools.format( '<h3 class="text-center"><a href="pub/asysatt/download/{attNo}">{attName}<i class="fa fa-download"></i></a></h3>', item ), 
//					} );
//				}
			} );
			
			that.vars.gridVar = that.selector.find( "#aSysAttExtGrid" ).grid( config );//renderer
			
			
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		handlers.load = function( data ) {
			
		};
		handlers.add = function(  ) {
			var that = this.global();
			that.modal.open( {
				title: "文档管理 | 新增",
				url: "pub/asysattext/form",
				size: "modal-lg",
				params: {},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.gridVar.load();
					}
				}
			} );
			
		};
		
		handlers.edit = function( items ) {
			if ( items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			
			var item = items[ 0 ]; //获取一条数据
			
			var that = this.global();
			that.modal.open( {
				title: "文档管理 | 编辑",
				url: "pub/asysattext/form",
				size: "modal-lg",
				params: { item: item },
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.gridVar.load();
					}
				}
			} );
		};
		
		handlers.del = function( items ) {
			if ( items == null || items.length == 0 ) {
				return message.error( "请选择至少一条操作数据。" );
			}
			
			var ids = [];
			$.each( items, function( index, item ) {
				ids.push( item.id );
			} ); 
			
			that.dialog.confirm( "确定删除选择的[ " + items.length + " ]条操作数据？", function( event, index ) {
				if ( index == 1 ) return false;
				$.ajax( {
					url: "pub/asysattext/delete",
					type: "POST",
					data: { ids: ids },
					success: function( data ) {
						if ( !data.success ) {
							return message.error( data.msg );
						}
						message.success( data.msg );
						that.vars.gridVar.load();
					}
				} );
			} );
			
		};
	};
	
	
	return Global;
	
} );
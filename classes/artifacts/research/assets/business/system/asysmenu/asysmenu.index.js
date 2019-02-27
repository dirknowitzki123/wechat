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
		
		
		//缓存码值
		code.cache( "Is_No, System_Module_Code, Menu_Type" );
		
		//=======================================================
		//当前组件
		//=======================================================
		var that = this;
		var vars = that.vars = $.extend( true, {}, vars );//全局变梁
		var handlers = that.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.load();
			this.layout(); 
		};
		
		//初始化远程请求处理
		that.load = function() {
			that.vars.parentMenuItem = that.params.item || {};
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			
			that.selector.find( "input[name=sysCode]" ).select( {//实例下拉插件
				code: { type: "System_Module_Code" }
			} ); 
			that.selector.find( "input[name=menuType]" ).select( {//实例下拉插件
				code: { type: "Menu_Type" }
			} ); 
			
			that.selector.find( ".input" ).input( {} );//实例input插件
			
			that.selector.find( "#addBtn" ).bind( "click", function( event ) {
				that.handlers.add();
			} );
			
			that.selector.find( "#addChildren" ).bind( "click", function( event ) {
				var items = that.vars.gridVar.selectedRows();
				that.handlers.addChildren( items );
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
		        	url: "system/asysmenu/list",
		            params: {item:that.vars.parentMenuItem}
		        },
		        multi: false,
		        page: true,
		        query: {
		        	target: that.selector.find( ".grid-query" ),
		        	isExpand: true
		        },
		        plugins: [],
		        events: {},
		        customEvents: []
			};
			config.cols = cols = [];
			cols[ cols.length ] = { title: "系统模块", name: "sysCode", width: "100px", lockWidth: true, renderer: function( val, item, rowIndex) {
				return base.code.getText(  "System_Module_Code", val );
			} };
			cols[ cols.length ] = { title: "菜单类型", name: "menuType", width: "100px", lockWidth: true, renderer: function( val, item, rowIndex) {
				return base.code.getText(  "Menu_Type", val );
			} };
			cols[ cols.length ] = { title: "菜单名称", name: "menuName", width: "120px", lockWidth: true };
			cols[ cols.length ] = { title: "权限许可", name: "menuCode", width: "200px", lockWidth: true };
			cols[ cols.length ] = { title: "级别", name: "menuLevel", width: "50px", lockWidth: true,align:'center' };
			cols[ cols.length ] = { title: "顺序", name: "byOrder", width: "50px", lockWidth: true,align:'center'};
			cols[ cols.length ] = { title: "启用", name: "isUserAble", width: "50px", lockWidth: true,align:'center',renderer: function( val, item, rowIndex ) {
				return base.code.getText( "Is_No", val );
			} };
			
			config.remote.params[ "params[parentMenuId]" ] = that.vars.parentMenuItem["id"];
			
			config.events.click = {
				handler: function( event, item, rowIndex ) {
					//item 当期行数据
					//rowIndex 当前行索引
					
					//处理其他逻辑
				}
			};
			
			config.customEvents.push( {
				target: ".edit",
				handler: function( event, item, rowIndex ) {
					
				}
			} );
			
			that.vars.gridVar = that.selector.find( "#roleGrid" ).grid( config );//renderer
			
			
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		handlers.load = function( data ) {
			
		};
		handlers.add = function(  ) {
			var that = this.global();
			var parentMenuItem = that.vars.parentMenuItem;
			that.page.open( {
				title: "菜单管理 | 新增",
				url: "system/asysmenu/form",
				size: "modal-lg",
				params: {
					item: {
						parentMenuId: parentMenuItem.id
					}
				},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.gridVar.load();
					}
				}
			} );
			
		};
		
		handlers.addChildren = function( items ) {
			var that = this.global();
			
			if ( items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			
			var item = items[ 0 ]; //获取一条数据
			that.page.open( {
				title: "菜单管理 | 子菜单",
				url: "system/asysmenu/index",
				size: "modal-lg",
				params: {
					item: item
				},
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
			that.page.open( {
				title: "菜单管理 | 编辑",
				url: "system/asysmenu/form",
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
				url: "system/asysmenu/delete",
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
			});
			
		};
		
	};
	
	
	return Global;
	
} );
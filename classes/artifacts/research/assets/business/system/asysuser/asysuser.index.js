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
		code.cache("Is_No,Sex");
		
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
			
			that.selector.find( "input[name=status]" ).select( {//实例下拉插件
				code: { type: "Is_No" }
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
			
			that.selector.find( "#authBtn" ).bind( "click", function( event ) {
				var items = that.vars.gridVar.selectedRows();
				that.handlers.auth( items );
				return false;
			} );
//			that.selector.find( "#export" ).bind( "click", function( event ) {
//				tools.exportExcel("system/asysuser/export");
//				return false;
//			} );
			var config = {
				remote: {
		        	url: "system/asysuser/list",
		            params: {}
		        },
		        multi: true,
		        page: {
		        	pageSize: 10
		        },
		        query: {
		        	target: that.selector.find( ".grid-query" )
		        },
		        plugins: [],
		        events: {},
		        customEvents: []
			};
			config.cols = cols = [];
			cols[ cols.length ] = { title: "姓名", name: "userName", width: "150px", lockWidth: true, mouseover:true};
			cols[ cols.length ] = { title: "登录名", name: "loginName", width: "150px", lockWidth: true, mouseover:true };
			cols[ cols.length ] = { title: "性别", name: "sex", width: "60px", lockWidth: true,align:'center',
					renderer: function( val, item, rowIndex ) {return base.code.getText( "Sex", val );}, mouseover:true		
			};
			cols[ cols.length ] = { title: "手机号", name: "mobile", width: "120px", lockWidth: true, mouseover:true,align:'center' };
			cols[ cols.length ] = { title: "启用", name: "status", width: "60px", lockWidth: true,align:'center',
									renderer: function( val, item, rowIndex ) {return base.code.getText( "Is_No", val );}
			};
			cols[ cols.length ] = { title: "备注", name: "remark", mouseover:true};
			cols[ cols.length ] = { title: "操作", name: "id", width: "100px", lockWidth: true,align:'center',renderer: function( val, item, rowIndex ) {
				return format( [ '<a href="javascript:;" class="pro-toggle"><i class="fa fa-retweet"></i>重置密码</a>' ] )
			}, mouseover:true };
			config.events.click = {
				handler: function( event, item, rowIndex ) {
					//item 当期行数据
					//rowIndex 当前行索引
					
					//处理其他逻辑
				}
			};
			
			config.customEvents.push( {
				target: ".pro-toggle",
				handler: function( event, item, rowIndex ) {
					that.handlers.reset(item,event.currentTarget.text);
					return false;
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
			that.page.open( {
				title: "用户管理 | 新增",
				url: "system/asysuser/form",
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
			that.page.open( {
				title: "用户管理 | 编辑",
				url: "system/asysuser/form",
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
					url: "system/asysuser/delete",
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
		
		//配置菜单
		handlers.auth = function (items){
			if ( items == null ) {
				return message.error( "请选择一条操作数据。" );
			}
			if ( items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var item = items[ 0 ]; //获取一条数据
			
			var that = this.global();
			that.modal.open( {
				title: "授权管理 ",
				url: "system/asysuser/role",
				size: "modal-md",
				params: { item: item },
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.gridVar.load();
					}
				}
			} );
		};//end
		
		/**
		 * 重置密码
		 */
		handlers.reset = function( item,text ) {
			var that = this.global();
			that.dialog.confirm( "确定"+text+"?", function( event, index ) {
				if ( index == 1 ) return false;
				that.loading.show();
				$.ajax( {
					url: "system/asysuser/reset/password",
					type: "POST",
					data: {"id":item.id},
					complete: function() {
						that.loading.hide();
					},
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
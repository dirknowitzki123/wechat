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
		code.cache( "stat,sex,certType" );
		
		//=======================================================
		//当前组件
		//=======================================================
		var that = this;
		var vars = that.vars = $.extend( true, {}, vars );//全局变量
		var handlers = that.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.layout(); 
			this.load();
		};
		
		//初始化远程请求处理
		that.load = function() {
			var that = this.global();
			if ( !that.params.item ) return;
			that.handlers.load( that.params.item );
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			that.selector.find( ".input" ).input( {} );//实例input插件
			
			that.selector.find( "input[name=status]" ).select( {//实例下拉插件
				code: { type: "stat" }
			} );
			
			//提交事件
			that.selector.find( "#submitBtn" ).bind( "click", function( event ) {
				that.handlers.save();
				return false;
			} );
			
			//关闭事件
			that.selector.find( "#closeBtn" ).click( function(event) {
				that.close();
				return false;
			});
			
			var config = {
				remote: {
		        	url: "system/asysusergroup/notBindUsers",
		            params: {}
		        },
		        multi: true,
		        page: true,
		        query: {
		        	target: that.selector.find( ".grid-query" )
		        },
		        plugins: [],
		        events: {},
		        customEvents: []
			};
			config.cols = cols = [];
			cols[ cols.length ] = { title: "用户姓名", name: "userName", width: "180px", lockWidth: true };
			cols[ cols.length ] = { title: "登录名", name: "loginName", width: "180px", lockWidth: true };
			cols[ cols.length ] = { title: "备注", name: "remark", };
			if ( that.params.item ) {
				config.remote.params["params[groupNo]"] = that.params.item.groupNo;
				config.remote.params["params[status]"] = '13900001';
			}
			
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
			
			that.vars.gridVar = that.selector.find( "#userGrid" ).grid( config );//renderer
			
			
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		handlers.load = function( data ) {
			that.selector.find( ":input[name=groupNo]" ).valChange( data.groupNo );
			that.selector.find( ":input[name=groupName]" ).valChange( data.groupName );
		};
		
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			var items = that.vars.gridVar.selectedRows();
			if ( items == null || items.length == 0 ) {
				return message.error( "请选择需要绑定的用户" );
			}
			var loginNames = [];
			$.each( items, function( index, item ) {
				loginNames.push( item.loginName );
			} );
			that.loading.show(); 
			$.ajax( {
				url: "system/asysusergroup/saveBindUsers",
				type: "POST",
				data: {loginNames:loginNames,
					   groupNo:that.selector.find(":input[name='groupNo']").val(),
					   groupName:that.selector.find(":input[name='groupName']").val()
					},
				complete: function() {
					that.loading.hide();
				},
				success: function( data ) {
					if ( !data.success ) {
						return message.error( data.msg );
					}
					message.success( data.msg );
					that.close( data );
				}
			} );
		};
		
	};
	
	return Global;
	
} );
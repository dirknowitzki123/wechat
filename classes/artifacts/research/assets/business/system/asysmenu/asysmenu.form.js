define(function () { 
	
	function Global( vars ) {
		//=======================================================
		// 获取基础组件
		//=======================================================
		var base = require( "app/base" )
			, message = base.message
			, tools = base.tools;
		
		var moment = require( "moment" );
		base.code.cache( "Is_No,System_Module_Code,Menu_Type" );
		
		//=======================================================
		// 当前组件
		//=======================================================
		var that = this; //全局对象
		var vars = this.vars = {};//全局变梁
		var handlers = this.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.layout();
			this.load();
			this.valdiate();
		};
		
		//初始化远程请求处理
		that.load = function() {
			var that = this.global();
			if ( !that.params.item ) return;
			that.handlers.load( that.params.item );
		};
		
		//验证组件
		that.valdiate = function() {
			var that = this.global(); 
			//jquery-validate 验证form表单元素
			that.vars.validator = that.selector.find( "form" ).validate( {
				rules: {
					menuCode: { required: true },
					menuName: { required: true },
					menuLevel: {required: true },
					url: {required: false },
					isUserAble: {required: true }
				}
			} );
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			
			//保存事件
			that.selector.find( "#submitBtn" ).click( function(event) {
				that.handlers.save();
				return false;
			});
			
			//关闭事件
			that.selector.find( "#closeBtn" ).click( function(event) {
				that.close();
				return false;
			});
			
			that.selector.find( "input[name=isUserAble]" ).select( {
				code: { type: "Is_No" }
			} );
			
			that.selector.find( "input[name=menuType]" ).select( {
				code: { type: "Menu_Type" },
				events: {
					change: {
						handler: function( event, val, item ) {
							var b = val == "20400001",
								o = that.selector.find( ":input[name=menuCode]" );
							o.readonly( b )
							if ( b ) o.val( "#" );
						}
					}
				}
			} );
			
			that.selector.find( "input[name=sysCode]" ).select( {
				itemOtherValues: [ "sysName:valName" ],
				code: { type: "System_Module_Code" }
			} );
			
			that.selector.find( ".input" ).input( {} );//实例input插件
		};
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			that.selector.find( ":input[name=id]" ).valChange( item.id ); 
			that.selector.find( ":input[name=menuName]" ).valChange( item.menuName ); 
			that.selector.find( ":input[name=parentMenuId]" ).valChange( item.parentMenuId ); 
			that.selector.find( ":input[name=menuLevel]" ).valChange( item.menuLevel ); 
			that.selector.find( ":input[name=menuCode]" ).valChange( item.menuCode ); 
			that.selector.find( ":input[name=url]" ).valChange( item.url ); 
			that.selector.find( ":input[name=isUserAble]" ).valChange( item.isUserAble );
			that.selector.find( ":input[name=byOrder]" ).valChange( item.byOrder ); 
			that.selector.find( ":input[name=menuIcon]" ).valChange( item.menuIcon ); 
			that.selector.find( ":input[name=menuType]" ).valChange( item.menuType ); 
			that.selector.find( ":input[name=sysCode]" ).valChange( item.sysCode ); 
			that.selector.find( ":input[name=sysName]" ).valChange( item.sysName ); 
			that.selector.find( ":input[name=remark]" ).valChange( item.remark ); 
			
			//that.selector.find( ":input[name=password]" ).disabled( true );
		};
		
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			//验证
			if ( !that.vars.validator.form() ) return;
			
			//jquery 表单数据序列化 必须是form表单中元素
			var data = that.selector.find( "form" ).serialize();
			//冻结功能
			//方案1 冻结自己的按钮
			//方案2 显示当前页的loading
			//var $button = that.selector.find( "#submitBtn" );
			//$button.disabled( true );
			that.loading.show(); 
			
			$.ajax( {
				url: "system/asysmenu/save",
				type: "POST",
				data: data,
				complete: function() {
					//$button.disabled( false );
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
});
define(function () { 
	
	function Global( vars ) {
		//=======================================================
		// 获取基础组件
		//=======================================================
		var base = require( "app/base" )
			, message = base.message
			, tools = base.tools;
		base.code.cache( "Is_No" );
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
					groupCode: {required:true,maxlength:40},
					remark : {maxlength:200}
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
			
			that.selector.find( ".input" ).input( {} );//实例input插件

		};
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			that.selector.find( ":input[name=groupCode]" ).valChange( item.groupCode );
			that.selector.find( ":input[name=remark]" ).valChange( item.remark );
			if(that.params.item.groupCode){
				that.selector.find( ":input[name=groupCode]" ).disabled( true );
			}
		};
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			if ( !that.vars.validator.form() ) return;
			var data={
				"typeCode":that.params.item.typeCode,
				"groupCode":that.selector.find(":input[name='groupCode']").val(),
				"remark":that.selector.find(":input[name='remark']").val(),
			};
			that.loading.show(); 
			$.ajax( {
				url: "system/aSysCode/groupSave",
				type: "POST",
				data: data,
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
});
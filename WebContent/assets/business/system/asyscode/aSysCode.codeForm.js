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
			if(that.params.item.id){
				that.handlers.editLoad( that.params.item );
			}else{
				that.handlers.addLoad( that.params.item );
			}
		};
		
		//验证组件
		that.valdiate = function() {
			var that = this.global(); 
			//jquery-validate 验证form表单元素
			that.vars.validator = that.selector.find( "form" ).validate( {
				rules: {
					valName: { required: true , maxlength:100},
					valCode: { required: true , maxlength:8},
					status: { required: true},
					remark: { maxlength:200}
				}
			} );
		};
		
		//页面布局
		that.layout = function() {
			//获取组件的全局对象
			var that = this.global();
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
			//实例input插件
			that.selector.find( ".input" ).input( {} );
			
			that.selector.find( "input[name=status]" ).select( {
				code: { type: "Is_No" }
			} );
		};
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.addLoad = function( item ) {
			var that = this.global();
			that.selector.find( ":input[name=typeCode]" ).valChange( item.typeCode );
		};
		handlers.editLoad = function( item ) {
			var that = this.global();
			that.selector.find( ":input[name=id]" ).valChange( item.id );
			that.selector.find( ":input[name=typeCode]" ).valChange( item.typeCode );
			that.selector.find( ":input[name=valName]" ).valChange( item.valName ); 
			that.selector.find( ":input[name=valCode]" ).valChange( item.valCode ); 
			that.selector.find( ":input[name=remark]" ).valChange( item.remark );
			that.selector.find( ":input[name=status]" ).valChange( item.status );
			that.selector.find( ":input[name=valCode]" ).disabled( true );
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
				url: "system/aSysCode/codeSave",
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
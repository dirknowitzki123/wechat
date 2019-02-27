define(function () { 
	
	function Global( vars ) {
		//=======================================================
		// 获取基础组件
		//=======================================================
		var base = require( "app/base" )
			, message = base.message
			, tools = base.tools;
		
		var moment = require( "moment" );
		
		base.code.cache( "Sex,Is_No,Postion_No,Education" );
		
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
			this.valdiate();
			this.load();
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
			that.vars.validator = that.selector.find( "form" ).validate( {
				rules: {
					branchName: {required: true},   		 //商户姓名
					parReferCode: {required: true,isRightfulString:true}, //经办人
					referCode: {required: true,isRightfulString:true}     //自身推荐码
                },
                messages: {
                	branchName: '请输入商户的姓名',
                	parReferCode: '请输入有效的经办人',
                	referCode: '请输入有效的推荐码'
                }
			} );
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			var istrue=true;
			var galobval="";
			that.selector.find( ".input" ).input( {} );//实例input插件
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
		};
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			
			if (item) {
				//that.selector.find( "input[name=parReferCode]" ).attr("readonly",true);
				that.selector.find( "input[name=referCode]" ).attr("readonly",true);
			}
			
			that.selector.find( ":input[name=id]" ).valChange( item.id ); 
			that.selector.find( ":input[name=branchName]" ).valChange( item.branchName ); 
			that.selector.find( ":input[name=referCode]" ).valChange( item.referCode );
			that.selector.find( ":input[name=parReferCode]" ).valChange( item.parReferCode );
			that.selector.find( ":input[name=status]" ).valChange( item.status );
		};
		
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			//验证
			if ( !that.vars.validator.form() ) return;
			//jquery 表单数据序列化 必须是form表单中元素
			var data = that.selector.find( "form" ).serialize();
			that.loading.show(); 
			$.ajax( {
				url: "branch/base/info/save",
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
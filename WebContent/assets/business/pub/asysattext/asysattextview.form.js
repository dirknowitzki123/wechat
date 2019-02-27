define(function () { 
	
	function Global( vars ) {
		//=======================================================
		// 获取基础组件
		//=======================================================
		var app = require( "app/base" )
			, base = require( "app/base" )
			, message = base.message
			, tools = base.tools
			, code = base.code;
		var moment = require( "moment" );
		//缓存码值
		code.cache( "Is_No , orgLevel , orgType" );
		
		//=======================================================
		// 当前组件
		//=======================================================
		var that = this; //全局对象
		var vars = this.vars = {};//全局变量
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
			if ( !that.params.item ) 
				return;
			that.handlers.load( that.params.item );
			
		};
		
		//验证组件
		that.valdiate = function() {
			var that = this.global(); 
			//jquery-validate 验证form表单元素
			that.vars.validator = that.selector.find( "#attExtForm" ).validate( {
				rules: {
					attName: { required: true },
					attDesc: { required: true },
					attTyp: {required: true }
				}
			} );
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			//关闭事件
			that.selector.find( "#closeBtn" ).click( function(event) {
				that.close();
				return false;
			});
			
			that.selector.find( ".input" ).input( {} );//实例input插件
			
			
			that.selector.find( "input[name=attTyp]" ).select( {//实例下拉插件
				code: { type: "Is_No" }
			} );
			
		};
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			that.selector.find( ":input[name=id]" ).valChange(item.id); 
			that.selector.find( ":input[name=busiNo]" ).valChange( item.busiNo ); 
			that.selector.find( ":input[name=busiTyp]" ).valChange( item.busiTyp ); 
			that.selector.find( ":input[name=attNo]" ).valChange( item.attNo ); 
			that.selector.find( ":input[name=attName]" ).valChange( item.attName ); 
			that.selector.find( ":input[name=attDesc]" ).valChange( item.attDesc ); 
			that.selector.find( ":input[name=attTyp]" ).valChange( item.attTyp ); 
			that.selector.find( ":input[name=attRemark]" ).valChange( item.attRemark ); 
			that.selector.find( ":input[name=attIsDir]" ).valChange( item.attIsDir ); 
			that.selector.find( ":input[name=attSort]" ).valChange( item.attSort ); 
			that.selector.find( ":input[name=instUserNo]" ).valChange( item.instUserNo ); 
			that.selector.find( ":input[name=instUserName]" ).valChange( item.instUserName ); 
			that.selector.find( ":input[name=instDate]" ).valChange( moment( item.instDate ).format( "YYYY-MM-DD" ) ); 
			that.selector.find( ":input[name=updtDate]" ).valChange( moment( item.updtDate ).format( "YYYY-MM-DD" ) );
			that.selector.find( "#download" ).attr("href",tools.format("pub/asysatt/download/{attNo}", item));
		};
		
	};
	
	return Global;
});
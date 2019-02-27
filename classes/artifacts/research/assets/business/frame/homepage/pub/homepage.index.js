define(function() {
	var base = require( "app/base" )
		, message = base.message
		, tools = base.tools
		, code = base.code;
	
return function ( vars ) {
	//=======================================================
	// 当前组件
	//=======================================================
	var that = this; //全局对象
	var vars = this.vars = {};//全局变梁
	var handlers = this.handlers = {};//处理程序
	handlers.global = function() { return that; };		
	
	//入口函数 java.main
	that.init = function(){
		that.layout();
//		that.load();
	};
	
	//页面渲染
	that.layout = function() {
		var that = this.global();//获取组件的全局对象
		
		
	};
	
	//页面加载
	that.load = function() {};
	
	
	//=======================================================
	// 业务逻辑申明
	//=======================================================
	handlers.load = function(){};
	
};


	
});


//function  disp_more(val)
//{
//	alert("11111");
//	var that = this.global();//获取组件的全局对象
//	that.page.open( {
//		title: "案件管理 | 编辑",
//		url: "safety/sysinfo/index",
//		size: "modal-lg",
//		events: {
//			hiden: function( closed, data ) {
//				if ( !closed ) return;
//				that.vars.gridVar.load();
//			}
//		}
//	} );
//}


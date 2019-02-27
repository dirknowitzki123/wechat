define( function() {
	//=======================================================
	// 获取基础组件
	//=======================================================
	var base = require( "app/base" )
		, tools = base.tools
		, format = tools.format
		, message = base.message;
	
	
	//=======================================================
	// 定义全局对象
	//=======================================================
	function Global( vars ) {
		
		//=======================================================
		// 当前组件
		//=======================================================
		var that = this;//全局对象
		vars = this.vars = $.extend( true, {}, vars );//全局变量
		var handlers = this.handlers = {};//处理程序
		this.handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		this.init = function() {
			this.loadBefore();
			this.layout();
			this.loadAfter();
		};
		
		this.loadBefore = function() {
			
		};
		
		this.loadAfter = function() {
			
		};
		
		//页面布局
		this.layout = function() {
			var that = this.global();
			
			var url = that.params.url;
			
			var content = format( '<iframe src="{0}" style="border:none; width: 100%; height: 1px"></iframe>', [ url ] );
			
			var $iframe = $( content ).appendTo( that.selector.find( "#iframeContainer" ) );
			
			that.section.bind( "section.custom.resize", function( event, width, height) {
				
				if ( typeof height != "number" ) {
					return false;
				}
				var h = height - that.section.find( ".page-header:eq(0)" ).height();
				
				$iframe.css( "height", h - 6 );
				
			} ).trigger( "main.resize.tabpage.section" );
			
		};
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		handlers.load = function() {
			
		};
	};
	
	return Global;
	
} );
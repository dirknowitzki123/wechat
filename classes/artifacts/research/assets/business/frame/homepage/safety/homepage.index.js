define(function() {
	var base = require( "app/base" )
		/*, message = base.message
		, tools = base.tools;
		, code = base.code*/;
	
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
		
		that.selector.find( "#dbrw" ).bind( "click", function( event ) {
			that.handlers.disp_more("dbrw");
		} );
		
		that.selector.find( "#xtgg" ).bind( "click", function( event ) {
			that.handlers.disp_more("xtgg");
		} );
		
		var config = {
				style: "width:49.5%; float: left;",
				remote: {
		        	url: "safety/casemaininfo/querybycondition",
		            params: {}
		        },
		        sort: false,
		        checkCol: false,
		        indexCol: false,
		        plugins: [],
		        events: {},
		        customEvents: []
			};
		config.cols = cols = [];
		cols[ cols.length ] = { title: "案件号", name: "caseId", width: "80px", lockWidth: true };
		cols[ cols.length ] = { title: "案件名称", name: "caseName", width: "80px", lockWidth: true};
		
		
		
		config.events.click = {
				handler: function( event, items, rowIndex ) {
					//item 当期行数据
					//rowIndex 当前行索引
					//处理其他逻辑
					that.page.open( {
						title: "案件管理 | 审批",
						url: "safety/casemaininfo/aprsend",
						size: "modal-lg",
						params: { item: items[0] },
						events: {
							hiden: function( closed, data ) {
								if ( !closed ) return;
								that.vars.gridtaskVar.load();
							}
						}
					} );
				}
			};
		
		that.vars.gridtaskVar = that.selector.find( "#roleGrid" ).grid( config );//renderer
		
		
		var config2 = {
				style: "width:49.5%; float: right;",
				remote: {
		        	url: "aanncmnginfo/aanncmnginfo/querybycondition",
		            params: {}
		        },
		        sort: false,
		        checkCol: false,
		        indexCol: false,
		        plugins: [],
		        events: {},
		        customEvents: []
			};

		config2.cols = cols2 = [];
		cols2[ cols2.length ] = { title: "标题", name: "title", width: "80px", lockWidth: true };
		cols2[ cols2.length ] = { title: "发布时间", name: "insertDate", width: "80px", lockWidth: true};
		cols2[ cols2.length ] = { title: "发布者", name: "publisher", width: "80px", lockWidth: true};
		
		config2.events.click = {
				handler: function( event, items, rowIndex ) {
					//item 当期行数据
					//rowIndex 当前行索引
					//处理其他逻辑
					that.page.open( {
						title: "公告管理 | 公告详情",
						url: "aanncmnginfo/aanncmnginfo/view",
						size: "modal-lg",
						params: { item: items[0] },
						events: {
							hiden: function( closed, data ) {
								if ( !closed ) return;
//								that.vars.gridVar.load();
							}
						}
					} );
				}
			};
		
		
		that.vars.gridVar = that.selector.find( "#roleGrid2" ).grid( config2 );//renderer
		
	};
	
	//页面加载
	that.load = function() {};
	
	
	//=======================================================
	// 业务逻辑申明
	//=======================================================
	handlers.load = function(){};
	
	handlers.disp_more = function( val ) {
		var that = this.global();
		
		var _title = "案件管理 | 待办任务";
		if(val == "xtgg")
			_title = "公告管理| 公告";
		
		that.page.open( {
			title: _title,
			url: "safety/sysinfo/index",
			size: "modal-lg",
			params: {
				flag_val: val
			},
			events: {
				hiden: function( closed, data ) {
					if ( !closed ) return;
					that.vars.gridVar.load();
				}
			}
		} );
		
	};
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


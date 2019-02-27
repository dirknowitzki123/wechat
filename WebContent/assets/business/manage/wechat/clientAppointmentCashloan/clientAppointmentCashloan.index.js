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
		code.cache("Is_No,Sex,Is_Staged");
		
		//=======================================================
		//当前组件
		//=======================================================
		var that = this;
		var condition = null;
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
			
			//缓存查询条件
            that.selector.find( "#custGrid" ).on( "click","#queryBtn",function(){
                condition = that.selector.find( "form" ).serialize();
            } );
			
			// 导出Excel
			that.selector.find("#exportExcelBtn").bind("click",function(event) {
					that.handlers.exportExcel();
					return false;
			});
			
			that.selector.find( "input[name=endDate]" ).datetimepicker({
				format : "YYYY-MM-DD"
			});
			that.selector.find( "input[name=beginDate]" ).datetimepicker({
				format : "YYYY-MM-DD"
			});
			
			var config = {
				remote: {
		        	url: "client/appointment/cashloan/list",
		            params: {}
		        },
		        multi: false,
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
			cols[ cols.length ] = { title: "姓名", name: "custName",  lockWidth: true, mouseover:true,align:'center'};
			cols[ cols.length ] = { title: "联系电话", name: "phoneNo",  lockWidth: true, mouseover:true,align:'center' };
			cols[ cols.length ] = { title: "所在城市", name: "ivingCity",lockWidth: true, mouseover:true,align:'center'};
			cols[ cols.length ] = { title: "推荐码", name: "referralCode",lockWidth: true, mouseover:true,align:'center'};
			cols[ cols.length ] = { title: "是否办理过分期贷款", name: "isStaged",lockWidth: true, mouseover:true,align:'center', renderer: function( val, item, rowIndex ) {
				return base.code.getText( "Is_Staged", val );
			} };
			cols[ cols.length ] = { title: "是否信用卡用户", name: "isCredites",lockWidth: true, mouseover:true,align:'center', renderer: function( val, item, rowIndex ) {
				return base.code.getText( "Is_No", val );
			} };
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
			
			that.vars.gridVar = that.selector.find( "#custGrid" ).grid( config );//renderer
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		handlers.load = function( data ) {
			
		};
		
		//导出
		handlers.exportExcel = function(){
			var that = this.global();
			var data = condition;
			that.loading.show();
			var url = "client/appointment/cashloan/export?"+data;
			if(!document.getElementById( "_filedown_")){
				var downWindow = document.createElement( 'iframe');
				downWindow.width = '0';
				downWindow.height = '0';
				downWindow.name = "_filedown_";
				downWindow.id = "_filedown_";
				downWindow.src =  url;
				document.body.appendChild(downWindow);
			}else{
				document.getElementById( "_filedown_").src = url;
			}
			that.loading.hide();
		}
	};
	
	
	return Global;
	
} );
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
		
		//当前页页员工信息缓存
		that.vars.currentStaffs = {
			staffNos: [],
			staffNames:[]
		};
		
		//历史页员工缓存
		that.vars.prefStaffs = {
			staffNos: [],
			staffNames: []
		};
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.layout(); 
			this.load();
		};
		
		//初始化远程请求处理
		that.load = function() {
			var that = this.global();
			if ( !that.params ||  that.params.staffNos.length == 0 ) return;
			that.handlers.load( that.params );
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			that.selector.find( ".input" ).input( {} );//实例input插件
			
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
		        	url: "system/dataauth/staffList",
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
			cols[ cols.length ] = { title: "用户姓名", name: "userName", width: "180px", lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "登录名", name: "loginName", width: "180px", lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "备注", name: "remark",mouseover: true };
			if ( that.params.item ) {
				config.remote.params["params[status]"] = '13900001';
			}
			
			config.events.click = {
				handler: function( event, item, rowIndex ) {
					//console.log('clicked');
					//item 当期行数据
					//rowIndex 当前行索引
					var items = that.vars.gridVar.selectedRows();
					//console.log(items.length);
					that.vars.currentStaffs = { staffNos: [], staffNames:[] };
					for(var i=0 ;i<items.length;i++){
						if(that.vars.currentStaffs.staffNos.indexOf(items[i].loginName) != -1 ) continue;
						that.vars.currentStaffs.staffNos.push(items[i].loginName);
						that.vars.currentStaffs.staffNames.push(items[i].userName);
					}
					//console.log(that.vars.currentStaffs);
				}
			};
			
			config.events.loaded = {
				handler: function( event, items ) {
					//item 当期行数据
					//rowIndex 当前行索引
					//清空前一页的数据，把前一页的数据放到历史中
					for(var i = 0; i<that.vars.currentStaffs.staffNos.length;i++){
						if(that.vars.prefStaffs.staffNos.indexOf(that.vars.currentStaffs.staffNos[i]) !=-1 ) continue;
						that.vars.prefStaffs.staffNos.push(that.vars.currentStaffs.staffNos[i]);
						that.vars.prefStaffs.staffNames.push(that.vars.currentStaffs.staffNames[i]);
					}
					
					that.vars.currentStaffs = { staffNos: [], staffNames:[] };
					//判断历史中的数据是否在当前页中有，如果有就让其默认选中，
					for(var i = 0 ;i< items.length;i++){
						var pidx = that.vars.prefStaffs.staffNos.indexOf(items[i].loginName);
						if(pidx!=-1){
							//选中当前item
							that.vars.gridVar.select(i);
							//移除历史
							that.vars.prefStaffs.staffNos.splice(pidx,1);
							that.vars.prefStaffs.staffNames.splice(pidx,1);
							//将当前item 添加到 cur中
							that.vars.currentStaffs.staffNos.push(items[i].loginName);
							that.vars.currentStaffs.staffNames.push(items[i].userName);
						}
					}
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
		handlers.load = function( params ) {
			that.vars.prefStaffs.staffNos = params.staffNos; //还原到历史中去
			that.vars.prefStaffs.staffNames = params.staffNames;//还原到历史中去
		};
		
		/** 保存*/
		handlers.save = function() {
			var selectedAllData = {staffNos:[],staffNames:[]};
			//将历史中的数据存入;//再将当前页中的数据加入
			selectedAllData.staffNos = selectedAllData.staffNos.concat(that.vars.prefStaffs.staffNos).concat(that.vars.currentStaffs.staffNos);
			selectedAllData.staffNames = selectedAllData.staffNames.concat(that.vars.prefStaffs.staffNames).concat(that.vars.currentStaffs.staffNames);
			that.close( selectedAllData );
		};
		
	};
	
	return Global;
	
} );
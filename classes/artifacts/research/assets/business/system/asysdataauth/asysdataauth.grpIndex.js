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
		code.cache( "Is_No,Auth_Staff_Group_Typ" );
		
		//=======================================================
		//当前组件
		//=======================================================
		var that = this;
		var vars = that.vars = $.extend( true, {}, vars );//全局变梁
		var handlers = that.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//当前页组的缓存
		that.vars.currentGrp = {
			grpNos: [],
			grpNames:[]
		};
		
		//历史页组的缓存
		that.vars.prefGrp = {
			grpNos: [],
			grpNames:[]
		};
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.layout(); 
			this.load();
		};
		
		//初始化远程请求处理
		that.load = function() {
			var that = this.global();
			if ( !that.params ||  that.params.grpNos.length == 0 ) return;
			that.handlers.load( that.params );
		};
		
		handlers.load = function( params ) {
			that.vars.prefGrp.grpNos = params.grpNos; //还原到历史中去
			that.vars.prefGrp.grpNames = params.grpNames;//还原到历史中去
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			
			that.selector.find( "input[name=authStaffGroupTyp]" ).select( {//实例下拉插件
				code: { type: "Auth_Staff_Group_Typ" }
			} );
			
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
			
			that.selector.find( ".input" ).input( {} );//实例input插件
						
			var config = {
				remote: {
		        	url: "system/dataauth/grpList",
		            params: {'stat':'13900001'}//只查询有效的
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
			cols[ cols.length ] = { title: "组编号", name: "groupNo", width: "180px", lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "组名称", name: "groupName", width: "180px", lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "备注", name: "remark",mouseover: true};
			
			config.events.click = {
				handler: function( event, item, rowIndex ) {
					var items = that.vars.gridVar.selectedRows();
					that.vars.currentGrp = { grpNos: [], grpNames:[] };
					for(var i=0 ;i<items.length;i++){
						if(that.vars.currentGrp.grpNos.indexOf(items[i].groupNo) != -1 ) continue;
						that.vars.currentGrp.grpNos.push(items[i].groupNo);
						that.vars.currentGrp.grpNames.push(items[i].groupName);
					}
				}
			};
			
			config.events.loaded = {
				handler: function( event, items ) {
					//清空前一页的数据，把前一页的数据放到历史中
					for(var i = 0; i<that.vars.currentGrp.grpNos.length;i++){
						if(that.vars.prefGrp.grpNos.indexOf(that.vars.currentGrp.grpNos[i]) !=-1 ) continue;
						that.vars.prefGrp.grpNos.push(that.vars.currentGrp.grpNos[i]);
						that.vars.prefGrp.grpNames.push(that.vars.currentGrp.grpNames[i]);
					}
					
					that.vars.currentGrp = { grpNos: [], grpNames:[] };
					//判断历史中的数据是否在当前页中有，如果有就让其默认选中，
					for(var i = 0 ;i< items.length;i++){
						var pidx = that.vars.prefGrp.grpNos.indexOf(items[i].groupNo);
						if(pidx!=-1){
							//选中当前item
							that.vars.gridVar.select(i);
							//移除历史
							that.vars.prefGrp.grpNos.splice(pidx,1);
							that.vars.prefGrp.grpNames.splice(pidx,1);
							//将当前item 添加到 cur中
							that.vars.currentGrp.grpNos.push(items[i].groupNo);
							that.vars.currentGrp.grpNames.push(items[i].groupName);
						}
					}
				}
			};
			
			config.customEvents.push( {
				target: ".edit",
				handler: function( event, item, rowIndex ) {
					
				}
			} );
			
			that.vars.gridVar = that.selector.find( "#userGroupGrid" ).grid( config );//renderer
		};
		
		//=======================================================
		//业务逻辑申明
		//=======================================================

		/** 保存*/
		handlers.save = function() {
			var selectedAllData = {grpNos:[],grpNames:[]};
			//将历史中的数据存入;//再将当前页中的数据加入
			selectedAllData.grpNos = selectedAllData.grpNos.concat(that.vars.prefGrp.grpNos).concat(that.vars.currentGrp.grpNos);
			selectedAllData.grpNames = selectedAllData.grpNames.concat(that.vars.prefGrp.grpNames).concat(that.vars.currentGrp.grpNames);
			that.close( selectedAllData );
		};
		
		
	};
	
	return Global;
	
} );
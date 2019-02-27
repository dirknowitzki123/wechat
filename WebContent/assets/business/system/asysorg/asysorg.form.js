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
		code.cache( "Is_No" );
		
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
			that.handlers.load( that.params.item );
			
		};
		
		//验证组件
		that.valdiate = function() {
			var that = this.global(); 
			//jquery-validate 验证form表单元素
			that.vars.validator = that.selector.find( "form" ).validate( {
				rules: {
					orgCode: { required: true },
					orgName: { required: true },
					orgType: { required: true },
					orgLevel: { required: true },
					status: { required: true }
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
			
			that.selector.find( "input[name=status]" ).select( {//实例下拉插件
				code: { type: "Is_No" }
			} );
			that.selector.find( ".selectArea" ).selectArea( {
				code: { type: "area.code.json" },
				itemLevelValues: [ "provNo", "cityNo", "areaNo" ]
			} );
			that.selector.find( ".input" ).input( {} );//实例input插件
			
		};
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			that.selector.find( ":input[name=parentName]" ).disabled( true );
			if ( !item ) return;
			if("add"==item.oper){
				that.selector.find( ":input[name=parentId]" ).valChange(item.id);
				handlers.setParentName(item.id);
			}else if("edit"==item.oper){
				that.selector.find( ":input[name=id]" ).valChange(item.id);
				that.selector.find( ":input[name=parentId]" ).valChange(item.parentId);
				that.selector.find( ":input[name=orgCode]" ).valChange( item.orgCode );
				that.selector.find( ":input[name=orgName]" ).valChange( item.orgName );
				that.selector.find( ":input[name=orgPhone]" ).valChange( item.orgPhone );
				that.selector.find( ":input[name=orgType]" ).valChange( item.orgType );
				that.selector.find( ":input[name=orgLevel]" ).valChange( item.orgLevel );
				that.selector.find( ":input[name=status]" ).valChange( item.status);
				that.selector.find( ":input[name=orgOrder]" ).valChange( item.orgOrder );
				that.selector.find( ":input[name=orgAddr]" ).valChange( item.orgAddr );
				that.selector.find( ":input[name=remark]" ).valChange( item.remark );
				that.selector.find( ":input[name=validCiry]" ).valChange(item.provNo+','+item.cityNo+','+item.areaNo);
				that.selector.find( ":input[name=orgCode]" ).disabled( true );
				if(item.parentId) handlers.setParentName(item.parentId);
			}
		};
		
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			if ( !that.vars.validator.form() ) return;
			var data = that.selector.find( "form" ).serialize();
			data=data+"&provName="+$(base.code.getText("area.code.json",that.selector.find(":input[name=provNo]").val())).text()
			+"&cityName="+$(base.code.getText("area.code.json",that.selector.find(":input[name=cityNo]").val())).text()
			+"&areaName="+$(base.code.getText("area.code.json",that.selector.find(":input[name=areaNo]").val())).text();
			that.loading.show(); 
			$.ajax( {
				url: "system/asysorg/save",
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
		
		handlers.setParentName=function(id){
			$.ajax( {
				url: "system/asysorg/getOrg",
				type: "POST",
				async: false,
				contentType:"application/json",
				data: JSON.stringify({"id":id}),
				success: function( data ) {
					that.selector.find( ":input[name=parentName]" ).valChange(data.orgName);
				}
			} );
		};
	};
	
	return Global;
});
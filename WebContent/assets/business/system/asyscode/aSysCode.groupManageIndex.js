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
			this.load();
			this.layout();
		};
		
		//初始化远程请求处理
		that.load = function() {
			var that = this.global();
			if ( !that.params.item ) return;
			that.handlers.load( that.params.item );
		};
		
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			
			//保存码值到码表组事件
			that.selector.find( "#addBtn" ).click( function(event) {
				var items = that.vars.notSelectedCodeGrid.selectedRows();
				that.handlers.save(items);
				return false;
			});
			
			//从码表组移除码值事件
			that.selector.find( "#delBtn" ).click( function(event) {
				var items = that.vars.groupCodeGrid.selectedRows();
				that.handlers.del(items);
				return false;
			});
			
			that.selector.find( ".input" ).input( {} );//实例input插件
			
			//已选grid
			var codeGridConfig = {
				remote: {
		        	url: "system/aSysCode/grpExistCodeLst",
		            params: {}
		        },
		        multi: true,
		        page: true,
		        query: {
		        	target: that.selector.find( "#code-grid-query" )
		        },
		        plugins: [],
		        events: {},
		        customEvents: []
			};
			codeGridConfig.cols = cols = [];
			cols[ cols.length ] = { title: "类型编码", name: "typeCode", width: "120px", lockWidth: false };
			cols[ cols.length ] = { title: "码值名称", name: "valName", width: "180px", lockWidth: false };
			cols[ cols.length ] = { title: "码值编码", name: "valCode", width: "100px", lockWidth: false };
			cols[ cols.length ] = { title: "启用", name: "status", width: "50px", lockWidth: true ,
									renderer:function(val,item,rowIndex){return base.code.getText("Is_No",val);}
			};
			cols[ cols.length ] = { title: "备注", name: "remark" };
			if ( that.params.item ) {
				codeGridConfig.remote.params["params[groupCode]"] = that.params.item.groupCode;
				codeGridConfig.remote.params["params[typeCode]"] = that.params.item.typeCode;
			}
			that.vars.groupCodeGrid = that.selector.find( "#codeGrid" ).grid( codeGridConfig );
			
			//待选grid
			var notSelectedCodeGridConfig = {
				remote: {
		        	url: "system/aSysCode/grpNotExistCodeLst",
		            params: {}
		        },
		        multi: true,
		        autoLoad:true,
		        page: true,
		        query: {
		        	target: that.selector.find( "#nse-grid-query" )
		        },
		        plugins: [],
		        events: {},
		        customEvents: []
			};
			notSelectedCodeGridConfig.cols = ncols = [];
			ncols[ ncols.length ] = { title: "类型编码", name: "typeCode", width: "120px", lockWidth: false };
			ncols[ ncols.length ] = { title: "码值名称", name: "valName", width: "180px", lockWidth: false };
			ncols[ ncols.length ] = { title: "码值编码", name: "valCode", width: "100px", lockWidth: false };
			ncols[ ncols.length ] = { title: "启用", name: "status", width: "50px", lockWidth: true ,
									renderer:function(val,item,rowIndex){return base.code.getText("Is_No",val);}
			};
			ncols[ ncols.length ] = { title: "备注", name: "remark" };
			if(that.params.item ) {
				notSelectedCodeGridConfig.remote.params["params[groupCode]"] = that.params.item.groupCode;
				notSelectedCodeGridConfig.remote.params["params[typeCode]"] = that.params.item.typeCode;
			}
			that.vars.notSelectedCodeGrid = that.selector.find( "#notSelectedCodeGrid" ).grid( notSelectedCodeGridConfig );

		};
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			that.selector.find( ":input[name=groupCode]" ).valChange( item.groupCode );
			that.selector.find( ":input[name=typeCode]" ).valChange( item.typeCode );
		};
		
		/** 保存到码表组*/
		handlers.save = function(items) {
			var that = this.global();
			if ( items == null || items.length == 0 ) {
				return message.error( "请选择至少一条操作数据。" );
			}
			var codeGroupInfo = {
					"codeGrp" : that.params.item,
					"codes" : items
			}
			$.ajax( {
				url: "system/aSysCode/saveGrpCodes",
				type: "POST",
				data: JSON.stringify(codeGroupInfo),
				contentType:"application/json",
				success: function( data ) {
					if ( !data.success ) {
						return message.error( data.msg );
					}
					message.success( data.msg );
					that.vars.notSelectedCodeGrid.load();
					that.vars.groupCodeGrid.load();
				}
			} );
		};
	
		//移除
		handlers.del = function(items) {
			var that = this.global();
			if ( items == null || items.length == 0 ) {
				return message.error( "请选择至少一条操作数据。" );
			}
			var valCodes = [];
			$.each( items, function( index, item ) {
				valCodes.push( item.valCode );
			} ); 
			var typeCode = that.selector.find(":input[name='typeCode']").val();
			var groupCode = that.selector.find(":input[name='groupCode']").val();
			
			that.dialog.confirm( "确定删除选择的[ " + items.length + " ]条操作数据？", function( event, index ) {
				if ( index == 1 ) return false;
				$.ajax({
					url: "system/aSysCode/delGroupCode",
					type: "POST",
					data: { valCodes: valCodes,typeCode:typeCode,groupCode:groupCode},
					success: function( data ) {
						if ( !data.success ) {
							return message.error( data.msg );
						}
						message.success( data.msg );
						that.vars.notSelectedCodeGrid.load();
						that.vars.groupCodeGrid.load();
					}
				});
			});
		}
	
	};
	
	return Global;
});
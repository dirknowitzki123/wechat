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
		code.cache( "Is_No" );
		
		//=======================================================
		//当前组件
		//=======================================================
		var that = this;
		var vars = that.vars = $.extend( true, {}, vars );//全局变梁
		var handlers = that.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.layout(); 
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			
			that.selector.find( "input[name=autPropTyp]" ).select( {//实例下拉插件
				code: { type: "Aut_Type" }
			} ); 
			
			that.selector.find( ".input" ).input( {} );//实例input插件
			
			that.selector.find( "#addBtn" ).bind( "click", function( event ) {
				that.handlers.add();
			} );
					
			that.selector.find( "#editBtn" ).bind( "click", function( event ) {
				var items = that.vars.gridVar.selectedRows();
				that.handlers.edit( items );
				return false;
			} );
			
			that.selector.find( "#delBtn" ).bind( "click", function( event ) {
				var items = that.vars.gridVar.selectedRows();
				that.handlers.del( items );
				return false;
			} );
			
			var config = {
				remote: {
		        	url: "system/dataauth/list",
		            params: {item:that.vars.parentMenuItem}
		        },
		        multi: true,
		        page: true,
		        query: {
		        	target: that.selector.find( ".grid-query" ),
		        	isExpand: true
		        },
		        plugins: [],
		        events: {},
		        customEvents: []
			};
			config.cols = cols = [];
			cols[ cols.length ] = { title: "业务功能编号", name: "busiTypCod",align: 'center',  width: "100px", lockWidth: true,mouseover: true};
			cols[ cols.length ] = { title: "业务功能名称", name: "busiName", width: "200px",align: 'center',  lockWidth: true,mouseover: true};
			cols[ cols.length ] = { title: "权限所属类型", name: "autPropTyp", width: "100px",align: 'center',  lockWidth: true, renderer: function( val, item, rowIndex) {
				return base.code.getText(  "Aut_Type", val );
			} };
			cols[ cols.length ] = { title: "SQL映射", name: "sqlMapId", width: "300px",align: 'center',  lockWidth: true,mouseover: true};
			cols[ cols.length ] = { title: "角色名称", name: "roleName", width: "200px",align: 'center',  lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "起始机构", name: "orgName", width: "200px",align: 'center',  lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "机构匹配方式", name: "orgMatchTyp", width: "100px",align: 'center',  lockWidth: true,mouseover: true, renderer: function( val, item, rowIndex) {
				return base.code.getText(  "Org_Match_Type", val );
			} };
			cols[ cols.length ] = { title: "员工名称", name: "staffName", width: "200px",align: 'center',  lockWidth: true,mouseover: true,renderer: function( val, item, rowIndex){
				var autPropTyp =  item.autPropTyp;
				if('25900003' == autPropTyp){//组
					return "";
				}
				return val;
			} };
			cols[ cols.length ] = { title: "组名称", name: "grpName", width: "200px",align: 'center',  lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "权限类型", name: "autTypCod", width: "100px",align: 'center',  lockWidth: true,mouseover: true, renderer: function( val, item, rowIndex) {
				return base.code.getText(  "Aut_Code_Type", val );
			} };
			cols[ cols.length ] = { title: "指定机构名称", name: "aptOrgName", width: "150px",align: 'center',  lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "指定角色名称", name: "aptRoleName", width: "150px",align: 'center',  lockWidth: true,mouseover: true};
			cols[ cols.length ] = { title: "指定人员名称", name: "aptStaffName", width: "150px",align: 'center',  lockWidth: true,mouseover: true};
			cols[ cols.length ] = { title: "指定组名称", name: "aptGrpName", width: "150px",align: 'center',  lockWidth: true,mouseover: true};
			cols[ cols.length ] = { title: "是否启用", name: "stat", width: "80px",align: 'center',  lockWidth: true,mouseover: true,renderer: function( val, item, rowIndex ) {
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
				target: ".edit",
				handler: function( event, item, rowIndex ) {
					
				}
			} );
			
			that.vars.gridVar = that.selector.find( "#authGrid" ).grid( config );//renderer
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		handlers.add = function() {
			var that = this.global();
			that.page.open( {
				title: "数据权限 | 新增",
				url: "system/dataauth/form",
				size: "modal-lg",
				params: {},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.gridVar.load();
					}
				}
			});
		};
		
		handlers.edit = function( items ) {
			if (!items || items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var item = items[ 0 ]; //获取一条数据
			var that = this.global();
			that.page.open( {
				title: "数据权限 | 编辑",
				url: "system/dataauth/form",
				size: "modal-lg",
				params: { item: item },
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.gridVar.load();
					}
				}
			} );
		};
		
		handlers.del = function( items ) {
			if ( items == null || items.length == 0 ) {
				return message.error( "请选择至少一条操作数据。" );
			}
			
			var ids = [];
			$.each( items, function( index, item ) {
				ids.push( item.id );
			} ); 
			
			that.dialog.confirm( "确定删除选择的[ " + items.length + " ]条操作数据？", function( event, index ) {
				if ( index == 1 ) return false;
				$.ajax({
					url: "system/dataauth/delete",
					type: "POST",
					data: { ids: ids },
					success: function( data ) {
						if ( !data.success ) {
							return message.error( data.msg );
						}
						message.success( data.msg );
						that.vars.gridVar.load();
					}
				} );
			});
			
		};
		
	};
	
	
	return Global;
	
} );
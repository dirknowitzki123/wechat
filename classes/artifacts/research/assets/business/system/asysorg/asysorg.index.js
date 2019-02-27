define(function () { 
	
	function Global( vars ) {
		
		//=======================================================
		//获取基础组件
		//=======================================================
		var $ = require( "jquery" )
			, base = require( "app/base" )
			, message = base.message
			, code = base.code
			, tools = base.tools;
		var moment = require( "moment" );
		
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
		
		//初始化远程请求处理
		that.load = function() {};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			
			that.selector.find( "#addBtn" ).bind( "click", function( event ) {
				var items = that.vars.orgTree.getSelectedNodes();
				that.handlers.add(items);
				return false;
			} );
			
			that.selector.find( "#editBtn" ).bind( "click", function( event ) {
				var items = that.vars.orgTree.getSelectedNodes();
				that.handlers.edit( items );
				return false;
			} );
			
			that.selector.find( "#delBtn" ).bind( "click", function( event ) {
				var items = that.vars.orgTree.getSelectedNodes();
				that.handlers.del( items );
				return false;
			} );
			
			var setting = {
					data : {
						key: {
							name: "orgName"
						},
						simpleData : {
							enable : true,
							idKey : 'id',
							pIdKey : 'parentId'
						}
					},
					async : {
						enable : true,
						url : "system/asysorg/queryTreeList"
					},
					callback : {
						onClick : function(event, treeId, treeNode, clickFlag) {
							handlers.showOrgInfo(treeNode);
						},
						onAsyncSuccess : function(event, treeId, treeNode, msg) {
							that.vars.orgTree.expandAll(true);
						}
					}
			};
			that.vars.orgTree=$.fn.zTree.init(that.selector.find("ul[class='ztree']"), setting);
		};
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		
		handlers.add = function( items ) {
			var that = this.global();
			if(items[0])items[0].oper="add";
			that.modal.open( {
				title: "组织机构管理 | 新增",
				url: "system/asysorg/form",
				size: "modal-lg",
				params: {item: items[0]},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.orgTree.reAsyncChildNodes(null, "refresh");
					}
				}
			} );
		};
		
		handlers.edit = function( items ) {
			if ( items.length != 1 ) {
				return message.error( "请选择一条操作数据" );
			}
			items[0].oper="edit";
			var that = this.global();
			that.modal.open( {
				title: "组织机构管理 | 编辑",
				url: "system/asysorg/form",
				size: "modal-lg",
				params: { item: items[0] },
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.orgTree.reAsyncChildNodes(null, "refresh");
					}
				}
			} );
			
		};
		
		handlers.del = function( items ) {
			if ( items == null || items.length == 0 ) {
				return message.error( "请选择一条操作数据" );
			}
			var ids = [];
			$.each( items, function( index, item ) {
				ids.push( item.id );
			} ); 
			that.dialog.confirm( "确定删除[ " + items[0].orgName + " ]及其子机构？", function( event, index ) {
				if ( index == 1 ) return false;
				$.ajax( {
					url: "system/asysorg/delete",
					type: "POST",
					data: { ids: ids },
					success: function( data ) {
						if ( !data.success ) {
							return message.error( data.msg );
						}
						message.success( data.msg );
						that.vars.orgTree.reAsyncChildNodes(null, "refresh");
					}
				} );
			} );
		};
		
		handlers.showOrgInfo=function(treeNode){
			$.ajax( {
				url: "system/asysorg/getOrg",
				type: "POST",
				async: false,
				contentType:"application/json",
				data: JSON.stringify({"id":treeNode.id}),
				success: function( data ) {
					that.selector.find( ".form-control-static:eq(0)" ).html(data.orgCode);
					that.selector.find( ".form-control-static:eq(1)" ).html(data.orgName);
					that.selector.find( ".form-control-static:eq(2)" ).html(data.orgPhone);
					that.selector.find( ".form-control-static:eq(3)" ).html(data.orgType);
					that.selector.find( ".form-control-static:eq(4)" ).html(data.orgLevel);
					var pca = data.provName?data.provName:""+data.cityName?data.cityName:""+data.areaName?data.areaName:"";
					that.selector.find( ".form-control-static:eq(5)" ).html(pca?pca:"");
					that.selector.find( ".form-control-static:eq(6)" ).html(base.code.getText("Is_No",data.status));
					that.selector.find( ".form-control-static:eq(7)" ).html(data.orgAddr);
					that.selector.find( ".form-control-static:eq(8)" ).html(data.remark);
				}
			} );
		};
	};
	
	return Global;
	
} );
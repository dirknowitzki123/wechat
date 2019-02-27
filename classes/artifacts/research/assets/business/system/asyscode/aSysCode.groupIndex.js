define(function () { 
	
	function Global( vars ) {
		
		//=======================================================
		//获取基础组件
		//=======================================================
		var $ = require( "jquery" )
			, base = require( "app/base" )
			, message = base.message
			, tools = base.tools;
		base.code.cache( "stat" );
		
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
			that.selector.find( ".input" ).input( {} );//实例input插件
			//group
			that.selector.find( "#groupGrid #addBtn" ).bind( "click", function( event ) {
				that.handlers.groupAdd();
			} );
			that.selector.find( "#groupGrid #editBtn" ).bind( "click", function( event ) {
				var items = that.vars.groupGrid.selectedRows();
				that.handlers.groupEdit( items );
				return false;
			} );
			that.selector.find( "#groupGrid #delBtn" ).bind( "click", function( event ) {
				var items = that.vars.groupGrid.selectedRows();
				that.handlers.groupDel( items );
				return false;
			} );
			that.selector.find( "#groupGrid #manageBtn" ).bind( "click", function( event ) {
				var items = that.vars.groupGrid.selectedRows();
				that.handlers.groupManage( items );
				return false;
			} );
			
			//码组列表
			var groupGridConfig = {
				remote: {
		        	url: "system/aSysCode/groupList",
		            params: {"params[typeCode]":that.params.typeItem.typeCode}
		        },
		        multi: false,
		        page: {
		        	pageSize: 5
		        },
		        query: {
		        	target: that.selector.find( ".grid-query" )
		        },
		        plugins: [],
		        customEvents: []
			};
			groupGridConfig.cols = cols = [];
			cols[ cols.length ] = { title: "码组编码", name: "groupCode", width: "120px", lockWidth: true };
			cols[ cols.length ] = { title: "备注", name: "remark" };
			that.vars.groupGrid = that.selector.find( "#groupGrid" ).grid( groupGridConfig );
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		
		handlers.groupAdd = function(  ) {
			var that = this.global();
			that.page.open( {
				title: "码组 | 新增",
				url: "system/aSysCode/groupForm",
				size: "modal-lg",
				params: {item: {"typeCode":that.params.typeItem.typeCode}},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.groupGrid.load();
					}
				}
			} );
		};
		
		handlers.groupEdit = function( items ) {
			var that = this.global();
			if ( items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var item = items[ 0 ]; //获取一条数据
			item.typeCode=that.params.typeItem.typeCode;
			that.page.open( {
				title: "码组 | 编辑",
				url: "system/aSysCode/groupForm",
				size: "modal-lg",
				params: { item: item },
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.groupGrid.load();
					}
				}
			} );
		};
		
		handlers.groupDel = function( items ) {
			if ( items == null || items.length == 0 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var ids = [];
			$.each( items, function( index, item ) {
				ids.push( item.groupCode );
			} );
			that.dialog.confirm( "确定删除选择的[ " + items.length + " ]条数据？", function( event, index ) {
				if ( index == 1 ) return false;
				$.ajax( {
					url: "system/aSysCode/groupDelete",
					type: "POST",
					data: {ids:ids,typeCode:that.params.typeItem.typeCode},
					success: function( data ) {
						if ( !data.success ) {
							return message.error( data.msg );
						}
						message.success( data.msg );
						that.vars.groupGrid.load();
					}
				} );
			} );
		};
		
		handlers.groupManage = function( items ) {
			var that = this.global();
			if (items ==null || items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var item = items[ 0 ]; //获取一条数据
			that.page.open( {
				title: "码组 | 码值管理",
				url: "system/aSysCode/groupManageIndex",
				size: "modal-lg",
				params: { item: item },
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.groupGrid.load();
					}
				}
			} );
		};
		
	};
	
	return Global;
} );
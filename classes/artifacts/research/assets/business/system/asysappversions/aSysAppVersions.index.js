define(function () { 
	
	function Global( vars ) {
		
		//=======================================================
		//获取基础组件
		//=======================================================
		var $ = require( "jquery" )
			, base = require( "app/base" )
			, message = base.message
			, tools = base.tools;
		base.code.cache( "Os_Type,Apply_Type,Is_No" );
		
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
			
			that.selector.find( "input[name=osType]" ).select( {//实例下拉插件
				code: { type: "Os_Type" }
			} ); 
			
			that.selector.find( ".input" ).input( {} );//实例input插件
			
			that.selector.find( "#versionsGrid #addBtn" ).bind( "click", function( event ) {
				that.handlers.add();
			} );
			that.selector.find( "#versionsGrid #editBtn" ).bind( "click", function( event ) {
				var items = that.vars.versionsGrid.selectedRows();
				that.handlers.edit( items );
				return false;
			} );
			that.selector.find( "#versionsGrid #delBtn" ).bind( "click", function( event ) {
				var items = that.vars.versionsGrid.selectedRows();
				that.handlers.del( items );
				return false;
			} );
			
			var versionsGridConfig = {
				remote: {
		        	url: "system/aSysAppVersions/list",
		            params: {}
		        },
		        multi: true,
		        page: {
		        	pageSize: 10
		        },
		        query: {
		        	target: that.selector.find( ".grid-query" )
		        },
		        plugins: [],
		        customEvents: []
			};
			versionsGridConfig.cols = cols = [];
			cols[ cols.length ] = { title: "版本类型", name: "osType", width: "120px", lockWidth: true,align:'center',
					renderer: function( val, item, rowIndex ) {return base.code.getText( "Os_Type", val );}
			};
			cols[ cols.length ] = { title: "版本编号", name: "versionNo", width: "120px", lockWidth: true,align:'center' };
			cols[ cols.length ] = { title: "适用类型", name: "applyType", width: "100px", lockWidth: true,align:'center',
					renderer: function( val, item, rowIndex ) {return base.code.getText( "Apply_Type", val );}
			};
			cols[ cols.length ] = { title: "适用环境", name: "applyEnvir", width: "150px", lockWidth: true, align:'center',mouseover:true };
			cols[ cols.length ] = { title: "是否强制更新", name: "isNo", width: "100px", lockWidth: true,align:'center',
					renderer: function( val, item, rowIndex ) {return base.code.getText( "Is_No", val );}
			};
			cols[ cols.length ] = { title: "说明", name: "remark", mouseover:true };
			
			that.vars.versionsGrid = that.selector.find( "#versionsGrid" ).grid( versionsGridConfig );
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		
		handlers.add = function(  ) {
			var that = this.global();
			that.page.open( {
				title: "版本管理 | 新增",
				url: "system/aSysAppVersions/form",
				size: "modal-lg",
				params: {},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.versionsGrid.load();
					}
				}
			} );
		};
		
		handlers.edit = function( items ) {
			if ( items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var item = items[ 0 ]; //获取一条数据
			var that = this.global();
			that.page.open( {
				title: "版本管理 | 编辑",
				url: "system/aSysAppVersions/form",
				size: "modal-lg",
				params: { item: item },
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.versionsGrid.load();
					}
				}
			} );
		};
		
		handlers.del = function( items ) {
			if ( items == null || items.length == 0 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var ids = [];
			$.each( items, function( index, item ) {
				ids.push( item.id );
			} );
			that.dialog.confirm( "确定删除选择的[ " + items.length + " ]条数据？", function( event, index ) {
				if ( index == 1 ) return false;
				$.ajax( {
					url: "system/aSysAppVersions/delete",
					type: "POST",
					data: { ids: ids },
					success: function( data ) {
						if ( !data.success ) {
							return message.error( data.msg );
						}
						message.success( data.msg );
						that.vars.versionsGrid.load();
					}
				} );
			} );
		};
		
		
		
	};
	return Global;
} );
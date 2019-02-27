define( function() {
function Global() {
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
	
	//初始化远程请求处理
	that.load = function() {};
	
	that.layout = function() {
		var that = this.global();
		
		that.selector.find( ".input" ).input( {} );
		
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
		that.selector.find( "#authBtn" ).bind( "click", function( event ) {
			var items = that.vars.gridVar.selectedRows();
			that.handlers.auth( items );
			return false;
		} );
		
		var config = {
			remote: {
	        	url: "system/asyssecret/list",
	            params: {}
	        },
	        root: "list",
	        page: {
	        	disabled: false,
	        },
	        query: {
	        	disabled: false,
	        	target: that.selector.find( ".grid-query" )
	        },
	        plugins: []
		};
		config.cols = cols = [];
		cols[ cols.length ] = { title: "客户端标志", name: "clientFlag", width: "180px", lockWidth: true };
		cols[ cols.length ] = { title: "公钥", name: "pubKey", width: "180px", lockWidth: true };
		cols[ cols.length ] = { title: "私钥", name: "priKey", width: "180px", lockWidth: true };
		cols[ cols.length ] = { title: "启用状态", name: "status", width: "180px", lockWidth: true, renderer: function( val, item, rowIndex ) {
			return base.code.getText( "stat", val );
		} };
		that.vars.gridVar = that.selector.find( "#roleGrid" ).grid( config );//renderer
	};
	
	
	//=======================================================
	//业务逻辑申明
	//=======================================================
	handlers.load = function( data ) {
		
	};
	
	handlers.add = function(  ) {
		var that = this.global();
		that.page.open( {
			title: "密钥管理 | 新增",
			url: "system/asyssecret/form",
			params: {},
			events: {
				hiden: function( closed, data ) {
					if ( !closed ) return;
					that.vars.gridVar.load();
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
			title: "密钥管理 | 编辑",
			url: "system/asyssecret/form",
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
			$.ajax( {
				url: "system/asyssecret/delete",
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
		} );
	};
	
	//配置菜单
	handlers.auth = function (items){
		if ( items == null ) {
			return message.error( "请选择一条操作数据。" );
		}
		if ( items.length != 1 ) {
			return message.error( "请选择一条操作数据。" );
		}
		var item = items[ 0 ]; //获取一条数据
		
		var that = this.global();
		that.modal.open( {
			title: "授权管理 ",
			url: "system/asysrole/menu",
			size: "modal-md",
			params: { item: item },
			events: {
				hiden: function( closed, data ) {
					if ( !closed ) return;
					that.vars.gridVar.load();
				}
			}
		} );
	};//end
	
	
};


return Global;
} );
define(function () { 
	
	function Global( vars ) {
		
		//=======================================================
		//获取基础组件
		//=======================================================
		var $ = require( "jquery" )
			, base = require( "app/base" )
			, message = base.message
			, tools = base.tools;
		base.code.cache( "Is_No" );
		
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
			//type
			that.selector.find( "#typeGrid #addBtn" ).bind( "click", function( event ) {
				that.handlers.typeAdd();
			} );
			that.selector.find( "#typeGrid #editBtn" ).bind( "click", function( event ) {
				var items = that.vars.typeGrid.selectedRows();
				that.handlers.typeEdit( items );
				return false;
			} );
			that.selector.find( "#typeGrid #delBtn" ).bind( "click", function( event ) {
				var items = that.vars.typeGrid.selectedRows();
				that.handlers.typeDel( items );
				return false;
			} );
			//code
			that.selector.find( "#codeGrid #addBtn" ).bind( "click", function( event ) {
				var items = that.vars.typeGrid.selectedRows();
				that.handlers.codeAdd(items);
				return false;
			} );
			that.selector.find( "#codeGrid #editBtn" ).bind( "click", function( event ) {
				var items = that.vars.codeGrid.selectedRows();
				that.handlers.codeEdit( items );
				return false;
			} );
			that.selector.find( "#codeGrid #delBtn" ).bind( "click", function( event ) {
				var items = that.vars.codeGrid.selectedRows();
				that.handlers.codeDel( items );
				return false;
			} );
			
			//码类列表
			var typeGridConfig = {
				remote: {
		        	url: "system/aSysCode/typeList",
		            params: {}
		        },
		        multi: false,
		        page: {
		        	pageSize: 5
		        },
		        query: {
		        	isExpand:true,
		        	target: that.selector.find( ".grid-query" )
		        },
		        plugins: [],
		        customEvents: [],
		        events:{
		        	loaded: {
		        		handler: function( event, items ) {
		        			that.vars.typeGrid.select( 0 );
		        			var item = items[ 0 ] || { typeCode: "$NO$" };
		        			that.vars.codeGrid.load({"params[typeCode]": item.typeCode});
		        		}
		        	},
		        	click: {
		        		 handler: function (event, items, rowIndex) {
		        			 that.vars.codeGrid.load({"params[typeCode]":items[0].typeCode});
		        		 }
		        	}
		        }
			};
			typeGridConfig.cols = cols = [];
			cols[ cols.length ] = { title: "码类名称", name: "typeName", width: "250px", lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "码类编码", name: "typeCode", width: "200px", lockWidth: true,align:'center',mouseover: true };
			cols[ cols.length ] = { title: "备注", name: "remark",mouseover: true };
			cols[ cols.length ] = { title: "操作", name: "id", width: "120px", lockWidth: true,align:'center', renderer: function( val, item, rowIndex ) {
				return format( [ '<a href="javascript:;" class="open-group-manager"><i class="fa fa-eye"></i> 码组管理</a>' ] )
			} };
			
			//自定义事件 码组管理
			typeGridConfig.customEvents.push( {
				target: ".open-group-manager", //目标 选择器
				//data: "" //参数
				handler: function( event, item, rowIndex ) {
					//event.data 获取参数
					that.handlers.openGroupManger( item );
					return false;
				}
			} );
			
			that.vars.typeGrid = that.selector.find( "#typeGrid" ).grid( typeGridConfig );
			
			//码值列表
			var codeGridConfig = {
				remote: {
		        	url: "system/aSysCode/codeList",
		        	params: {}
		        },
		        autoLoad:false,
		        page: true
			};
			codeGridConfig.cols = cols = [];
			cols[ cols.length ] = { title: "码类", name: "typeCode", width: "200px", lockWidth: true,align:'center',mouseover: true };
			cols[ cols.length ] = { title: "码值名称", name: "valName", width: "250px", lockWidth: true,mouseover: true };
			cols[ cols.length ] = { title: "码值编码", name: "valCode", width: "100px", lockWidth: true,align:'center' };
			cols[ cols.length ] = { title: "启用", name: "status", width: "60px", lockWidth: true ,align:'center',
									renderer:function(val,item,rowIndex){return base.code.getText("Is_No",val);}
			};
			cols[ cols.length ] = { title: "备注", name: "remark",mouseover: true };
			that.vars.codeGrid = that.selector.find( "#codeGrid" ).grid( codeGridConfig );
			
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		
		handlers.typeAdd = function(  ) {
			var that = this.global();
			that.page.open( {
				title: "码类 | 新增",
				url: "system/aSysCode/typeForm",
				size: "modal-lg",
				params: {},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.typeGrid.load();
					}
				}
			} );
		};
		
		handlers.typeEdit = function( items ) {
			if ( items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var item = items[ 0 ]; //获取一条数据
			var that = this.global();
			that.page.open( {
				title: "码类 | 编辑",
				url: "system/aSysCode/typeForm",
				size: "modal-lg",
				params: { item: item },
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.typeGrid.load();
					}
				}
			} );
		};
		
		handlers.typeDel = function( items ) {
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
					url: "system/aSysCode/typeDelete",
					type: "POST",
					data: { ids: ids },
					success: function( data ) {
						if ( !data.success ) {
							return message.error( data.msg );
						}
						message.success( data.msg );
						that.vars.typeGrid.load();
					}
				} );
			} );
		};
		
		handlers.codeAdd = function( items ) {
			if ( items.length != 1 ) {
				return message.error( "请选择一条类型数据。" );
			}
			var that = this.global();
			var item = items[ 0 ];
			that.page.open( {
				title: "码值 | 新增",
				url: "system/aSysCode/codeForm",
				size: "modal-lg",
				params: {item: {"typeCode":item.typeCode}},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.codeGrid.load();
					}
				}
			} );
		};
		
		handlers.codeEdit = function( items ) {
			if ( items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var item = items[ 0 ]; //获取一条数据
			var that = this.global();
			that.page.open( {
				title: "码值 | 编辑",
				url: "system/aSysCode/codeForm",
				size: "modal-lg",
				params: { item: item },
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.codeGrid.load();
					}
				}
			} );
		};
		
		handlers.codeDel = function( items ) {
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
					url: "system/aSysCode/codeDelete",
					type: "POST",
					data: { ids: ids },
					success: function( data ) {
						if ( !data.success ) {
							return message.error( data.msg );
						}
						message.success( data.msg );
						that.vars.codeGrid.load();
					}
				} );
			} );
		};
		//打开码组管理
		handlers.openGroupManger = function( item ) {
			var that = this.global();
			that.page.open( {
				title: "码组管理 ",
				url: "system/aSysCode/groupIndex",
				params: {
					typeItem: item
				},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.typeGrid.load();
					}
				}
			} );
		}
	};
	return Global;
} );
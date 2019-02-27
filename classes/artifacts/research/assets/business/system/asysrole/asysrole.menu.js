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
		//=======================================================
		base.code.cache( "Is_No,SYSTEM_MODULE_CODE,Menu_Type" );
		
		//当前组件
		//=======================================================
		var that = this;
		var vars = that.vars = $.extend( true, {}, vars );//全局变梁
		var handlers = that.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.load();
			this.layout(); 
		};
		
		//初始化远程请求处理 加载ztree
		that.load = function() {
			var that = this.global();
		};//end
		
		
		that.layout = function() {
			var that = this.global();
			var setting = {
	            view: {  
	                selectedMulti: true,        //禁止多点选中  
	                nameIsHTML: true,
	                showTitle: false
	            },  
	            data: {
	            	key: {
	        			name: "menuName"
	        		},
	                simpleData: {  
	                    enable:true ,
	                    idKey: "id",
	        			pIdKey: "parentMenuId"
	                }  
	            },  
	            check : {
					enable : true,
					chkStyle : "checkbox",
					radioType : "all",
					chkboxType: { "Y": "s", "N": "s" }
	            },
	            async : {
					enable : true,
					url : "system/asysmenu/treeList",
					autoParam:["id"],
					otherParam:{"isUserAble":"13900001"},
					dataFilter: function( treeId, parentNode, responseData ) {
						var data = responseData.list;
						if ( !data ) return data;
						for ( var index = 0; index < data.length; index++ ) {
							if ( !data[ index ].parentMenuId ) {
								data[ index ].parentMenuId = data[ index ].sysCode;
							}
							//data[ index ].menuName = data[ index ].menuName + "【" + base.code.getText( "Menu_Type", data[ index ].menuType ) + "】";
							data[ index ].menuName = data[ index ].menuName;
						} 
						if ( parentNode == null ) {
							for ( var index = 0; index < that.vars.sysCodes.length; index++ ) {
								data.push( that.vars.sysCodes[ index ] );
							}
							setTimeout( function(){
								handlers.setMenuChecked();
							}, 300 );
						}
						return data;
					}
				},
	            callback: {  
	                onClick: function(event, treeId, treeNode) {  
	                	event.preventDefault();
						event.stopPropagation();
	                    var treeObj = $.fn.zTree.getZTreeObj( treeId );  
	                    
	                    treeObj.checkNode( treeNode );
                    	if ( !treeNode.checked ) {
                    		treeObj.cancelSelectedNode( treeNode );
                    	}
	                    //var selectedNode = treeObj.getSelectedNodes()[0];  
	                    return false;
	                },
	                onCheck: function( event, treeId, treeNode ) {
						event.preventDefault();
						event.stopPropagation();
						return false;
					},
	            }  
	        }; 
			
			base.code.getCacheByCallBack( "SYSTEM_MODULE_CODE", "__default__", function( cache ) {
				that.vars.sysCodes = [];
//	        	for ( var index = 0; index < cache.length; index++ ) {
//	        		that.vars.sysCodes.push( {
//	        			id: cache[ index ].valCode,
//	        			menuName: '【系统模块】' + cache[ index ].valName,
//	        			checked: true,
//	        			open: false
//	        		} );
//	        	}
	        	var zTree = that.vars.zTree = $.fn.zTree.init(that.selector.find( "#menuTree" ), setting); 
	        }, that, 1);
	        //, that.params.menus
			
	        
	        
	        //保存事件
	        that.selector.find( "#submitBtn" ).click( function(event) {
				that.handlers.saveOrUpdate();
				return false;
			});
			
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		
		handlers.setMenuChecked = function() {
			var that = this.global();
			var zTree = that.vars.zTree;
			var item = that.params.item;
        	$.ajax( {
				url: "system/asysrole/getMenuByRoleId",
				type: "POST",
				async:false,
				data: {"roleId":item.id},
				complete: function() {},
				success: function( data ) {
					var roles = data.list || [], treeNode;
		        	for(var i=0;i<roles.length;i++){
			        	var role = roles[i];
			        	if ( typeof role.menuId != "string" ) {
			        		continue;
			        	}
			        	treeNode = zTree.getNodeByParam( "id",role.menuId );
			        	if ( treeNode ) {
			        		zTree.checkNode( treeNode , true );
			        	}
			        }
				}
			} );
			
		}
		
		handlers.saveOrUpdate = function (){
			var that = this.global();
			var item = that.params.item;
			var treeObj = $.fn.zTree.getZTreeObj("menuTree");
			var nodes =  treeObj.getCheckedNodes(true);
			var ids = [];
			$.each( nodes, function( index, node ) {
				if ( !node.sysCode ) return true;
				ids.push( node.id );
			} );
			item.ids = ids;
			that.loading.show(); 
			$.ajax( {
				url: "system/asysrole/updateMenus",
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(item),
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
		}
		
		
	};
	
	
	return Global;
} );
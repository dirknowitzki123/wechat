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
		//当前组件
		//=======================================================
		var that = this;
		var vars = that.vars = $.extend( true, {}, vars );//全局变梁
		var handlers = that.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.loadSelectMenu();
			this.load();
			this.layout(); 
		};
		
		//初始化远程请求处理 加载ztree
		that.load = function() {
			var that = this.global();
			that.params.roles = {};
			$.ajax( {
				url: "system/asysrole/getList",
				type: "POST",
				async:false,
				data:{"isUse":"13900001"},
				success: function( data ) {
					if ( data.success ) {
						that.params.roles = data.list;
					}
					else{
						return message.error( data.msg );
					}
					
				}
			} );
		};//end
		
		//初始化远程请求处理  加载该角色已经勾选了的菜单数据
		that.loadSelectMenu = function (){
			var that = this.global();
			var item = that.params.item;
			that.params.role_select = {};
			//加载该角色已经选择了的菜单信息
			$.ajax( {
				url: "system/asysuser/getRoleByUserId",
				type: "POST",
				async:false,
				data: {"userId":item.id},
				complete: function() {
				},
				success: function( data ) {
					that.params.role_select = data.list;
				}
			} );
		};//end
		
		that.layout = function() {
			var that = this.global();
			var setting = {
	            view: {  
	                selectedMulti: true        //禁止多点选中  
	            },  
	            data: {
	            	key: {
	        			name: "roleName"
	        		},
	                simpleData: {  
	                    enable:true ,
	                    idKey: "id",
	        			pIdKey: "pId"
	                }  
	            },  
	            check : {
					enable : true,
					chkStyle : "checkbox",
					radioType : "all",
					chkboxType:{"Y":"ps","N":"ps"}
	            },
	            async : {
					enable : true,
					url : "system/asysuser/list",
					dataFilter:that.handlers.zTreeFilter
				},
	            callback: {  
	                onClick: function(treeId, treeNode) {  
	                    var treeObj = $.fn.zTree.getZTreeObj(treeNode);  
	                    var selectedNode = treeObj.getSelectedNodes()[0];  
	                }  
	            }  
	        };  
	        //, that.params.menus
			var zTree = $.fn.zTree.init(that.selector.find( "#roleTree" ), setting,that.params.roles); 
	        var roles = that.params.role_select, role, treeNode;
	        if(roles.length>=1){
	        	for(var i=0;i<roles.length;i++){
		        	role = roles[i];
		        	treeNode = zTree.getNodeByParam( "id",role.roleId );
		        	if ( !treeNode ) continue;
		        	zTree.checkNode(treeNode, true );
		        }
	        }
	        
	      //保存事件
			that.selector.find( "#submitBtn" ).click( function(event) {
				that.handlers.saveOrUpdate();
				return false;
			});
			
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		
		handlers.zTreeFilter = function (treeId, parentNode, responseData){
			
		};
		
		handlers.saveOrUpdate = function (){
			var that = this.global();
			var item = that.params.item;
			var treeObj = $.fn.zTree.getZTreeObj("roleTree");
			var nodes =  treeObj.getCheckedNodes(true);
			var ids = [];
			$.each( nodes, function( index, nodes ) {
				ids.push( nodes.id );
			} );
			
			that.loading.show(); 
			item.ids = ids;
			$.ajax( {
				url: "system/asysuser/updateRoles",
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
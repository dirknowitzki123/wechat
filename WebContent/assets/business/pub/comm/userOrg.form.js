
define(function () { 
	
	function Global( vars ) {
		//=======================================================
		// 获取基础组件
		//=======================================================
		var base = require( "app/base" )
			, message = base.message
			, code = base.code
			, tools = base.tools;
		
		var moment = require( "moment" );
		
//		//缓存码值
//		code.cache( "stat,illeflag,oCheaFlg,ilEvFlg" );
		
		
		//=======================================================
		// 当前组件
		//=======================================================
		var that = this; //全局对象
		var vars = this.vars = {};//全局变梁
		var handlers = this.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		var SETTINGS = that.vars.SETTINGS = {
			multi: true, //是否多选 
			selectedUserNo: false, //选择用户编号
			selectedUserNos: []//选中用户编号
		};
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.loadBefore();
			this.layout();
			this.loadAfter();
		};
		
		//初始化远程请求处理
		that.loadBefore = function() {
			var that = this.global();
			
			var settings = that.vars.settings = $.extend( true, that.vars.SETTINGS, that.params.settings );
			
			if ( typeof settings.multi != "boolean" ) {
				settings.multi = true;
			}
			if ( settings.multi ) {
				if ( typeof settings.selectedUserNos != "object" && !settings.selectedUserNos instanceof Array ) {
					settings.selectedUserNos = [];
				}
			} else if ( typeof settings.selectedUserNo == "string" ) {
				settings.selectedUserNos = [ settings.selectedUserNo ];
			}
			
			that.handlers.load();
		};
		
		//页面加载后函数
		that.loadAfter = function() {
			var that = this.global();
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			
			//关闭事件
			that.selector.find( "#closeBtn" ).click( function( event ) {
				that.close();
				return false;
			});
			
			that.handlers.loadUserTree();
			
			that.selector.find( "#submitBtn" ).click( function( event ) {
				that.handlers.submit();
				return false;
			} );
			
		};
		
		
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function() {
			var that = this.global();

		};
		
		handlers.loadUserTree = function() {
			var that = this.global();
			that.loading.show();
			$.ajax( {
				url: "pub/asysatt/listuserorg",
				type: "POST",
				success: function( data ) {
					if ( !data.success ) {
						message.error( data.msg );
					} 
					var settings = that.vars.settings;
					var zNodes = data.list || [], node;
					for ( var index = 0; index < zNodes.length; index++ ) {
						node = zNodes[ index ];
						node.isOrg = node.nocheck == "true";
						if ( settings.multi ) {
							node.nocheck = false;
						}
						if ( node.isOrg ) {
							node.node_name =  "【机构】" + node.orgname;
						} else {
							node.node_name =  "【人员】" + node.orgname;
						}
					}
					
					
					var setting = {
						check: {
							enable: true,
							chkboxType: { "Y": "ps", "N": "ps" },
							chkStyle: settings.multi ? "checkbox" : "radio",
							radioType: "all"
						},
						data: {
							key: {
								name: "node_name" 
							},
							simpleData: {
								enable: true,
								idKey: "orgcode",
								pIdKey: "pid"
							}
						},
						callback: {
							onClick: function( event, treeId, treeNode ) {
								event.preventDefault();
								event.stopPropagation(); 
								if ( treeNode.isOrg ) {
									ztree.cancelSelectedNode( treeNode );
									ztree.expandNode( treeNode, !treeNode.open );
									return false;
								}
								/*if ( !settings.multi && !treeNode.checked ) {
									var treeNodes = ztree.getCheckedNodes( true );
									for ( var index = 0; index < treeNodes.length; index++ ) {
										ztree.checkNode( treeNodes[ index ], false );
									}
								}*/
								ztree.checkNode( treeNode );
								if ( !treeNode.checked ) {
									ztree.cancelSelectedNode( treeNode );
								}
								return false;
							},
							onCheck: function( event, treeId, treeNode ) {
								event.preventDefault();
								event.stopPropagation();
								
								console.log( treeNode );
								
								return false;
							},
							onExpand: function( event, treeId, treeNode ) {
								event.preventDefault();
								event.stopPropagation();
								return false;
							},
							onCollapse:function( event, treeId, treeNode ) {
								event.preventDefault();
								event.stopPropagation();
								return false;
							},
						}
					};
					var $userTree = that.selector.find( "#userTree" );
					var ztree = that.vars.ztree = $.fn.zTree.init( $userTree , setting, zNodes);
					
					var userNos = settings.selectedUserNos;
					
					var userNo, treeNodes;
					for ( var index = 0; index < userNos.length; index++ ) {
						userNo = userNos[ index ];
						console.log( userNo );
						if ( typeof userNo != "string" ) {
							continue;
						}
						userNo = $.trim( userNo );
						if ( userNo.length == 0 ) {
							continue;
						}
						
						treeNodes = ztree.getNodesByParam( "orgcode", userNo );
						if ( typeof treeNodes == "object" && treeNodes instanceof Array ) {
							for ( var j = 0; j < treeNodes.length; j++ ) {
								ztree.checkNode( treeNodes[ j ], true )
								ztree.selectNode( treeNodes[ j ], true );
							}
						}
					}
					
				},
				complete: function() {
					that.loading.hide();
				}
			} );
			
		};
		
		
		handlers.submit = function() {
			var that = this.global();
			var ztree = that.vars.ztree;
			
			var treeNodes = ztree.getCheckedNodes( true ), treeNode;
			var userNos = [], userNames = [], orgNos = [], items = [], item;
			for ( var index = 0; index < treeNodes.length; index++ ) {
				treeNode = treeNodes[ index ];
				if ( treeNode.isOrg ) continue;
				item = {
					userNo: treeNode.orgcode,
					userName: treeNode.orgname,
					orgNo: treeNode.pid
				};
				items.push( item );
				userNos.push( item.userNo );
				userNames.push( item.userName );
				orgNos.push( item.orgNo );
			}
			
			if ( that.vars.settings.multi ) {
				that.close( {
					userNos: userNos,
					userNames: userNames,
					orgNos: userNos,
					items: items
				} );
			} else {
				that.close( {
					userNo: userNos[ 0 ] || "",
					userName: userNames[ 0 ] || "",
					orgNo: userNos[ 0 ] || "",
					item: items[ 0 ] || null
				} );
			}
			
			
		};
		
		
	};
	
	
	return Global;
});
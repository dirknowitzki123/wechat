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
		code.cache("Is_No,Sex");
		
		//=======================================================
		//当前组件
		//=======================================================
		var that = this;
		var condition = null;
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
			
			that.selector.find( "#addBtn" ).bind( "click", function( event ) {
				that.handlers.add();
				return false;
			} );
			
			that.selector.find( "#delBtn" ).bind( "click", function( event ) {
				var items = that.vars.gridVar.selectedRows();
				that.handlers.del( items );
				return false;
			} );
			
			that.selector.find( "#enableBtn" ).bind( "click", function( event ) {
				var items = that.vars.gridVar.selectedRows();
				that.handlers.enable( items );
				return false;
			} );
			
			that.selector.find( "#editBtn" ).bind( "click", function( event ) {
				var items = that.vars.gridVar.selectedRows();
				that.handlers.edit( items );
				return false;
			} );
			
			//缓存查询条件
            that.selector.find( "#custGrid" ).on( "click","#queryBtn",function(){
                condition = that.selector.find( "form" ).serialize();
            } );
			
			// 导出Excel
			that.selector.find("#exportExcelBtn").bind("click",function(event) {
					that.handlers.exportExcel();
					return false;
			});
			
			var config = {
				remote: {
		        	url: "cust/base/info/list",
		            params: {}
		        },
		        multi: false,
		        page: {
		        	pageSize: 10
		        },
		        query: {
		        	target: that.selector.find( ".grid-query" )
		        },
		        plugins: [],
		        events: {},
		        customEvents: []
			};
			config.cols = cols = [];
			cols[ cols.length ] = { title: "姓名", name: "custName",  lockWidth: true, mouseover:true,align:'center'};
			cols[ cols.length ] = { title: "身份证号", name: "certNo",  lockWidth: true, mouseover:true,align:'center'};
			cols[ cols.length ] = { title: "手机号", name: "phoneNo",  lockWidth: true, mouseover:true,align:'center' };
			cols[ cols.length ] = { title: "推荐码", name: "referralCode",lockWidth: true, mouseover:true,align:'center'};
			cols[ cols.length ] = { title: "银行卡号", name: "bankNo", lockWidth: true, mouseover:true,align:'center'};
			cols[ cols.length ] = { title: "开户行", name: "openingBank", lockWidth: true, mouseover:true,align:'center'};
			cols[ cols.length ] = { title: "注册来源", name: "imei", lockWidth: true, mouseover:true,align:'center', renderer: function( val, item, rowIndex) {
				if (val != null && val != "") {
					return "APP";
				}else {
					return "微信";
				}
			} };
			cols[ cols.length ] = { title: "状态", name: "status", lockWidth: true, mouseover:true,align:'center', renderer: function( val, item, rowIndex) {
				if (val == "10002001") {
					return "正常";
				}else {
					return "已注销";
				}
			}};
			cols[ cols.length ] = { title: "电子合同下载", name: "phoneNo", lockWidth: true, mouseover:true, align:'center', renderer: function( val, item, rowIndex ) {
				return format( [ '<a href="javascript:;" class="download-elecontract"><i class="fa fa-eye"></i> 下载</a>' ] )
			}};
			
			//自定义事件 电子合同下载
			config.customEvents.push( {
				target: ".download-elecontract", 
				//data: "" //参数
				handler: function( event, item, rowIndex ) {
					//event.data 获取参数
					that.handlers.downloadEleContract(item);
					return false;
				}
			});
			
			config.events.click = {
				handler: function( event, item, rowIndex ) {
					//item 当期行数据
					//rowIndex 当前行索引
					
					//处理其他逻辑
				}
			};
			
			config.customEvents.push( {
				target: ".pro-toggle",
				handler: function( event, item, rowIndex ) {
					that.handlers.reset(item,event.currentTarget.text);
					return false;
				}
			} );
			
			that.vars.gridVar = that.selector.find( "#custGrid" ).grid( config );//renderer
		};
		
		
		//=======================================================
		//业务逻辑申明
		//=======================================================
		handlers.load = function( data ) {
			
		};
		handlers.add = function(  ) {
			var that = this.global();
			that.page.open( {
				title: "微信用户管理 | 新增",
				url: "cust/base/info/form",
				size: "modal-lg",
				params: {},
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.vars.gridVar.load();
					}
				}
			} );
		};
		
		handlers.del = function(items){
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
					url: "cust/base/info/delete",
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
		}
		//启用功能
		handlers.enable = function(items){
			if ( items == null || items.length == 0 ) {
				return message.error( "请选择至少一条操作数据。" );
			}
			var ids = [];
			$.each( items, function( index, item ) {
				ids.push( item.id );
			} ); 
			
			that.dialog.confirm( "确定启用选择的[ " + items.length + " ]条操作数据？", function( event, index ) {
				if ( index == 1 ) return false;
				$.ajax( {
					url: "cust/base/info/enable",
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
		}
		
		handlers.edit = function( items ) {
			if ( items.length != 1 ) {
				return message.error( "请选择一条操作数据。" );
			}
			var item = items[ 0 ]; //获取一条数据
			var that = this.global();
			that.page.open( {
				title: "微信用户管理 | 编辑",
				url: "cust/base/info/form",
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
		//导出
		handlers.exportExcel = function(){
			var that = this.global();
			var data = condition;
			that.loading.show();
			var url = "cust/base/info/export?"+data;
			if(!document.getElementById( "_filedown_")){
				var downWindow = document.createElement( 'iframe');
				downWindow.width = '0';
				downWindow.height = '0';
				downWindow.name = "_filedown_";
				downWindow.id = "_filedown_";
				downWindow.src =  url;
				document.body.appendChild(downWindow);
			}else{
				document.getElementById( "_filedown_").src = url;
			}
			that.loading.hide();
		}
		
		//下载电子合同
		handlers.downloadEleContract = function( item ) {
			
			var phoneNo = item.phoneNo;
        	var iframeDown = document.getElementById("_download_attribExcel");
    		if(!iframeDown){
    			iframeDown = document.createElement('iframe');
    			iframeDown.width = '0';
    			iframeDown.height = '0';
    			iframeDown.name = "_download_attribExcel";
    			iframeDown.id = "_download_attribExcel";
    			if (iframeDown.attachEvent){
    				iframeDown.attachEvent("onload", function(){
    					base.message.error("导出失败!<br/>"+$(window.frames["_download_attribExcel"].document).find("div[class='alert alert-danger']").html());
    			    });
    			} else {
    				iframeDown.onload = function(){
    					if($(window.frames["_download_attribExcel"].document).find("div[class='alert alert-danger']").length==1)
    					base.message.error("导出失败!<br/>"+$(window.frames["_download_attribExcel"].document).find("div[class='alert alert-danger']").html());	
    				};
    			}
    			document.body.appendChild(iframeDown);
    		}
    		iframeDown.src = "cust/base/info/dwloadelecontract/"+phoneNo;
			
		}
	};
	
	
	return Global;
	
} );
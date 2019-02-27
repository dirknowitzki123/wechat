define(function () { 
	
	function Global( vars ) {
		//=======================================================
		// 获取基础组件
		//=======================================================
		var base = require( "app/base" )
			, message = base.message
			, tools = base.tools;
		
		var moment = require( "moment" );
		
		base.code.cache( "Sex,Is_No,Postion_No,Education" );
		
		//=======================================================
		// 当前组件
		//=======================================================
		var that = this; //全局对象
		var vars = this.vars = {};//全局变梁
		var handlers = this.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.layout();
			this.valdiate();
			this.load();
		};
		
		//初始化远程请求处理
		that.load = function() {
			var that = this.global();
			if ( !that.params.item ) return;
			that.handlers.load( that.params.item );
		};
		
		//验证组件
		that.valdiate = function() {
			var that = this.global(); 
			that.vars.validator = that.selector.find( "form" ).validate( {
				rules: {
					userName: { required: true },
					loginName: { required: true, minlength:5, maxlength:20},
					password: {required: true },
					sex: {required: true },
					status: {required: true }
				}
			} );
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			var istrue=true;
			var galobval="";
			that.selector.find( "input[name=positionNo]" ).select( {});
			var congiff={
					multi: false,
					remote: {
						url: "system/asysorgposition/getposition",
						params: {"orgCode":galobval+""},
						type: 'POST',
						dataType: 'json',
						callback: function (data) {
							return data;
						}
					},		
					itemLabel: "postionName",
					itemValue: "postionNo",
					autoLoad: true,
					readonly: true
			}
			
			that.selector.find( "input[name=orgCode]" ).selectTree( {
				multi: false,
				itemLabel: "orgName",
				itemValue: "orgCode",
				itemId: "id",
				itemPId: "parentId",
				itemOtherValues: [ "orgName:orgName"],
				remote: {
					url: "system/asysorg/list"
				},
				events: {
					change: {
						data: {},
						handler: function (event, val, item) {
							galobval=val;
							that.selector.find( "input[name=positionNo]" ).parent().remove();
							//var tt="options="+"'{txtLabel:'岗位名称'}'";
							var inputt="<input type='text' class='select' name='positionNo'/>";
							that.selector.find( "input[name=arriWorkDate]" ).parent().after( inputt);
							that.selector.find( "input[name=positionNo]" ).attr("options","{txtLabel:'岗位名称'}");
							that.selector.find( "input[name=positionNo]" ).select({
									remote: {
										url: "system/asysorgposition/getposition",
										params: {"orgCode":galobval+""},
										type: 'POST',
										dataType: 'json',
										async:false,
										callback: function (data) {	
											that.selector.find( "input[name=positionNo]" ).valChange(that.vars.positionNo);
											return data;
										}
									},		
									itemLabel: "postionName",
									itemValue: "postionNo",
									autoLoad: true,
									readonly: true
							});
							
						}
					}
				}
			} );
			
			that.selector.find( "input[name=sex]" ).select( {
				code: { type: "Sex" },
				events: {
					change: {
						data: {},
						handler: function (event, val, item) {
							//console.log( arguments );
							//单选参数 event, val, item,
							//多选参数 event, vals, items
							//console.log(arguments);
						}
					}
				}
			} );

			that.selector.find( "input[name=status]" ).select( {
				code: { type: "Is_No" }
			} );
//			that.selector.find( "input[name=positionNo]" ).select( {
//				code: { type: "Postion_No" }
//			} );
			that.selector.find( ":input[name=education]" ).select( {
				code: { type: "Education" }
			} );
			
			
//			that.selector.find( ":input[name=levelNo]" ).select( {
//				code: { type: "Sex" }
//			} );
			
			that.selector.find( ":input[name=titleNo]" ).select( {
				code: { type: "Postion_No" }
			} );
			
			that.selector.find( "input[name=birhday]" ).datetimepicker({
				maxDate: moment(that.vars.nowDate).format( "YYYY-MM-DD" )
			});
			
			that.selector.find( "input[name=arriWorkDate]" ).datetimepicker({
				
			});
			
			that.selector.find( ".input" ).input( {} );//实例input插件
			
			//保存事件
			that.selector.find( "#submitBtn" ).click( function(event) {
				that.handlers.save();
				return false;
			});
			
			//关闭事件
			that.selector.find( "#closeBtn" ).click( function(event) {
				that.close();
				return false;
			});
			
		};
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			that.selector.find( ":input[name=id]" ).valChange( item.id ); 
			that.selector.find( ":input[name=userName]" ).valChange( item.userName ); 
			that.selector.find( ":input[name=loginName]" ).valChange( item.loginName );
			that.selector.find( ":input[name=sex]" ).valChange( item.sex );
			that.selector.find( ":input[name=birhday]" ).valChange( item.birhday );
			that.selector.find( ":input[name=cardNo]" ).valChange( item.cardNo );
			that.selector.find( ":input[name=mobile]" ).valChange( item.mobile );
			that.selector.find( ":input[name=orgCode]" ).valChange( item.orgCode ); 
			that.selector.find( ":input[name=status]" ).valChange( item.status );
			that.selector.find( ":input[name=remark]" ).valChange( item.remark );
			
			that.selector.find( ":input[name=email]" ).valChange( item.email );
			that.selector.find( ":input[name=homeAddr]" ).valChange( item.homeAddr );
			that.selector.find( ":input[name=homeTel]" ).valChange( item.homeTel );
			that.selector.find( ":input[name=education]" ).valChange( item.education );
			that.selector.find( ":input[name=workYears]" ).valChange( item.workYears );
			that.selector.find( ":input[name=loginName]" ).attr("readOnly",'true');
			handlers.selectUserDetail(item.loginName);
			that.selector.find(":input[name=password]").disabled(true);
		};
		
		handlers.selectUserDetail=function(loginName)
		{
			//var code;
			$.ajax( {
				url: "system/asysuserdetail/list",
				type: "POST",
				data: {"loginName":loginName},
				async:false,
				success: function( data ) {
					if ( !data.success ) {
						return message.error( data.msg );
					}				
					if(typeof data.list[0] == "undefined")
					{
						that.vars.positionNo="";
						   return;
					}
					that.vars.positionNo=(data.list[0])["positionNo"];					
					that.selector.find( "input[name=positionName]" ).valChange((data.list[0])["positionName"]);
					that.selector.find( "input[name=college]" ).valChange((data.list[0])["college"]);
					that.selector.find( "input[name=leaderComment]" ).valChange((data.list[0])["leaderComment"]);
					that.selector.find( "input[name=levelNo]" ).valChange((data.list[0])["levelNo"]);
					that.selector.find( "input[name=levelName]" ).valChange((data.list[0])["levelName"]);
					that.selector.find( "input[name=titleNo]" ).valChange((data.list[0])["titleNo"]);
					that.selector.find( "input[name=titleName]" ).valChange((data.list[0])["titleName"]);
					that.selector.find( "input[name=workArea]" ).valChange((data.list[0])["workArea"]);
					that.selector.find( "input[name=natiPlac]" ).valChange((data.list[0])["natiPlac"]);
					that.selector.find( "input[name=nowAddre]" ).valChange((data.list[0])["nowAddre"]);
					that.selector.find( "input[name=homeRegiType]" ).valChange((data.list[0])["homeRegiType"]);
					that.selector.find( "input[name=arriWorkDate]" ).valChange((data.list[0])["arriWorkDate"]);
					that.selector.find( "input[name=finaObtaTimes]" ).valChange((data.list[0])["finaObtaTimes"]);
					that.selector.find( "input[name=weixinAddr]" ).valChange((data.list[0])["weixinAddr"]);
					that.selector.find( "input[name=profInfo]" ).valChange((data.list[0])["profInfo"]);
					that.selector.find( "input[name=bankCardNo]" ).valChange((data.list[0])["bankCardNo"]);
					that.selector.find( "input[name=openAcctInfo]" ).valChange((data.list[0])["openAcctInfo"]);
					that.selector.find( "input[name=prvCompName]" ).valChange((data.list[0])["prvCompName"]);
					that.selector.find( "input[name=soseInfo]" ).valChange((data.list[0])["soseInfo"]);
					that.selector.find( "input[name=emplChnl]" ).valChange((data.list[0])["emplChnl"]);
					
					return data;
				
				}
			} );
		}
		
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			//验证
			if ( !that.vars.validator.form() ) return;
			
			
			var positionNo =that.selector.find( "input[name=positionNo]" ).val();
			var positionName = that.selector.find(tools.format( "li[value=\"{0}\"] a", [positionNo] ) ).text();
			that.selector.find( "input[name=positionName]" ).valChange( positionName );
			
//			var levelNo =that.selector.find( "input[name=levelNo]" ).val();
//			var levelName = that.selector.find(tools.format( "li[value=\"{0}\"] a", [levelNo] ) ).text();
			//that.selector.find( "input[name=levelName]" ).valChange( levelName );
			
			var titleNo =that.selector.find( "input[name=titleNo]" ).val();
			var titleName = that.selector.find(tools.format( "li[value=\"{0}\"] a", [titleNo] ) ).text();
			that.selector.find( "input[name=titleName]" ).valChange( titleName );
			
			//jquery 表单数据序列化 必须是form表单中元素
			var data = that.selector.find( "form" ).serialize();
		
			
			//冻结功能
			//方案1 冻结自己的按钮
			//方案2 显示当前页的loading
			//var $button = that.selector.find( "#submitBtn" );
			//$button.disabled( true );
			that.loading.show(); 
			
			$.ajax( {
				url: "system/asysuser/save",
				type: "POST",
				data: data,
				complete: function() {
					//$button.disabled( false );
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
			
		};
	};
	
	return Global;
});
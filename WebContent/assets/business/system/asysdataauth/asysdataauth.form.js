define(function () { 
	
	function Global( vars ) {
		//=======================================================
		// 获取基础组件
		//=======================================================
		var base = require( "app/base" )
			, message = base.message
			, tools = base.tools;
		
		base.code.cache( "Is_No,Aut_Type,Org_Match_Type,Aut_Code_Type" );
		
		//=======================================================
		// 当前组件
		//=======================================================
		var that = this; //全局对象
		var vars = this.vars = {};//全局变梁
		var handlers = this.handlers = {};//处理程序
		handlers.global = function() { return that; };
		var styctr = this.styctr = {};
		styctr.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.layout();
			this.load();
			this.valdiate();
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
			//jquery-validate 验证form表单元素
			that.vars.validator = that.selector.find( "form" ).validate( {
				rules: {
					busiTypCod: { required: true },
					busiName: { required: true },
					stat:{required: true},
					sqlMapId: {required: true },
					autPropTyp: {required: true },
					autTypCod:{required: true}
				}
			} );
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			
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
			
			that.selector.find( "input[name=sqlMapId]" ).input( {
				suffixButtons: [ { icon: "fa fa-close" },"添加" ],
				suffixButtonsHandler: function( event, index ) {
					that.handlers.openSqlMapIndex( index,$( this ).val() );
				}
			} );
			
			that.selector.find( "input[name=roleNo],input[name=aptRoleNo]" ).select( {
				multi: true,
				remote: {
					url: "system/dataauth/roleList",
					type: 'POST'
				},
				events: {
					change: {
						data: {},
						handler: function (event, val, items) {
							//单选参数 event, val, item,
							//多选参数 event, vals, items
							//console.log(arguments);
							if(items==null || !items || items.length == 0) return;
							var oTyp = $(this).attr("name");
							var roleNames = [];
							for(var i=0 ; i<items.length; i++){
								roleNames.push(items[i].label);
							}
							
							if('roleNo'==oTyp){//角色
								that.selector.find("input[name=roleName]").valChange(roleNames.join(","));
							}else if('aptRoleNo' == oTyp){//指定角色
								that.selector.find("input[name=aptRoleName]").valChange(roleNames.join(","));
							}
							
						}
					}
				}
			} );
			
			that.selector.find( "input[name=staffName],input[name=aptStaffName]" ).input( {
				suffixButtons: [ { icon: "fa fa-close" },"添加" ],
				suffixButtonsHandler: function( event, index ) {
					that.handlers.openStaffIndex(index, $( this ) );
				}
			} );
			
			that.selector.find("input[name=grpName],input[name=aptGrpName]").input( {
				suffixButtons: [ { icon: "fa fa-close" },"添加" ],
				suffixButtonsHandler: function( event, index ) {
					that.handlers.openGrpIndex(index, $( this ));
				}
			} );
			
			that.selector.find( "input[name=autPropTyp]" ).select( {
				code: { type: "Aut_Type" },
				events: {
					change: {
						data: {},
						handler: function (event, val, items) {
							//单选参数 event, val, item,
							//多选参数 event, vals, items
							//console.log(arguments);
							if( !val || '' == $.trim(val)) return;
							that.styctr.init('Aut_Type',val);
						}
					}
				}
			} );
			
			that.selector.find( "input[name=orgMatchTyp]" ).select( {
				code: { type: "Org_Match_Type" }
			} );
			
			that.selector.find( "input[name=autTypCod]" ).select( {
				code: { type: "Aut_Code_Type" },
				events: {
					change: {
						data: {},
						handler: function (event, val, items) {
							//单选参数 event, val, item,
							//多选参数 event, vals, items
							//console.log(arguments);
							if( !val || '' == $.trim(val)) return;
							that.styctr.init('Aut_Code_Type',val);
						}
					}
				}
			} );
			
			that.selector.find( "input[name=stat]" ).select( {
				code: { type: "Is_No" }
			} );
			
			that.selector.find( "input[name=orgNo]" ).selectTree( {
				multi: false,
				itemLabel: "orgName",
				itemValue: "orgCode",
				itemId: "id",
				itemPId: "parentId",
				itemOtherValues: [ "orgName:orgName"],
				remote: {
					url: "system/dataauth/orglist"
				},
				events:{
					change:{
						data: {},
						handler: function (event, val, item) {
							//单选参数 event, val, item,
							//多选参数 event, vals, items
							//console.log(arguments);
							if(item==null || !item) return;
							that.selector.find( "input[name=orgPath]" ).valChange(item.orgPath);//机构path
							that.selector.find( "input[name=orgName]" ).valChange(item.orgName);//机构名称
						}
					}
				}
			});
			
			that.selector.find( "input[name=aptOrgNo]" ).selectTree( {
				multi: true,
				itemLabel: "aptOrgName",
				itemValue: "aptOrgCode",
				itemId: "id",
				itemPId: "parentId",
				itemOtherValues: [ "aptOrgName:aptOrgName"],
				remote: {
					url: "system/dataauth/aptorglist"
				},
				events:{
					change:{
						data: {},
						handler: function (event, val, items) {
							//单选参数 event, val, item,
							//多选参数 event, vals, items
							//console.log(arguments);
							/*
							if(items==null || !items || items.length == 0) return;
							var orgNames = [];
							for(var i= 0; i<items.length ; i++){
								orgNames.push(items[i].orgName);
							}
							that.selector.find( "input[name=aptOrgName]" ).valChange(orgNames.join(","));//指定机构名称
							*/
						}
					}
				}
			} );
			
			that.selector.find( ".input" ).input( {} );//实例input插件
			
		};
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			if(!item) return;
			//必填项信息的赋值
			that.selector.find( ":input[name=id]" ).valChange( item.id ); 
			that.selector.find( ":input[name=stat]" ).valChange( item.stat );
			that.selector.find( ":input[name=busiTypCod]" ).valChange( item.busiTypCod );
			that.selector.find( ":input[name=busiName]" ).valChange( item.busiName );
			that.selector.find( ":input[name=sqlMapId]" ).valChange( item.sqlMapId );
			that.selector.find( ":input[name=autPropTyp]" ).valChange( item.autPropTyp );
			that.selector.find( ":input[name=autTypCod ]" ).valChange( item.autTypCod );
			
			//权限涉众赋值
			that.selector.find( ":input[name=roleNo]" ).valChange( item.roleNo );
			that.selector.find( ":input[name=roleName]" ).valChange( item.roleName );
			that.selector.find( ":input[name=orgNo]" ).valChange( item.orgNo );
			//that.selector.find( ":input[name=orgName]" ).valChange( item.orgName );
			that.selector.find( ":input[name=orgPath]" ).valChange( item.orgPath );
			that.selector.find( ":input[name=orgMatchTyp]" ).valChange( item.orgMatchTyp );
			that.selector.find( ":input[name=staffName]" ).valChange( item.staffName );
			that.selector.find( ":input[name=staffNo]" ).valChange( item.staffNo );
			that.selector.find( ":input[name=grpName]" ).valChange( item.grpName );
			that.selector.find( ":input[name=grpNo]" ).valChange( item.grpNo );
			
			//权限范围赋值
			that.selector.find( ":input[name=aptOrgNo]" ).valChange( item.aptOrgNo );
			//that.selector.find( ":input[name=aptOrgName]" ).valChange( item.aptOrgName );
			that.selector.find( ":input[name=aptRoleNo]" ).valChange( item.aptRoleNo );
			that.selector.find( ":input[name=aptRoleName]" ).valChange( item.aptRoleName );
			that.selector.find( ":input[name=aptStaffName]" ).valChange( item.aptStaffName );
			that.selector.find( ":input[name=aptStaffNo]" ).valChange( item.aptStaffNo );
			that.selector.find( ":input[name=aptGrpName]" ).valChange( item.aptGrpName );
			that.selector.find( ":input[name=aptGrpNo]" ).valChange( item.aptGrpNo );
			
			//赋值完毕后控制数据的隐藏和禁用样式
			that.styctr.init('Aut_Type',item.autPropTyp);
			that.styctr.init('Aut_Code_Type',item.autTypCod);
			
		};
		
		handlers.openSqlMapIndex = function( index,sqlMap ) {
			var that = this.global();
			if(index==0){ //清除按钮
				that.selector.find( "input[name=sqlMapId]" ).valChange( "" );
				return;
			}
			
			that.modal.open( {
				title: "SQL映射 | 添加",
				url: "system/dataauth/sqlmap",
				params: {
					sqlMap: sqlMap
				},
				size: "modal-md",
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						that.selector.find( "input[name=sqlMapId]" ).valChange( data );
					}
				}
			} );
			
		};
		
		handlers.openStaffIndex = function(index,obj) {
			var that = this.global();
			var operateType = obj.attr("name");
			if(index==0){ //清除按钮
				obj.valChange(""); 
				if('staffName' == operateType){
					that.selector.find("input[name=staffNo]").valChange("");
				}else if('aptStaffName' == operateType){
					that.selector.find("input[name=aptStaffNo]").valChange("");
				}
				return; 
			}
			
			var staffNames = [];
			if('' != $.trim(obj.val()) ){
				staffNames = obj.val().split(",");
			}
			
			var staffNos = [];
			if('staffName' == operateType && '' != $.trim(that.selector.find("input[name=staffNo]").val()) ){ //人员操作
				staffNos = that.selector.find("input[name=staffNo]").val().split(",");
			}else if('aptStaffName' == operateType &&  '' != $.trim(that.selector.find("input[name=aptStaffNo]").val()) ){//指定人员操作
				staffNos = that.selector.find("input[name=aptStaffNo]").val().split(",");
			}
			
			that.modal.open( {
				title: "员工 | 添加",
				url: "system/dataauth/staffindex",
				params: {
					staffNos: staffNos,
					staffNames:staffNames
				},
				size: "modal-lg",
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						obj.valChange(data.staffNames.join(","));
						staffNos = data.staffNos;
						if('staffName' == operateType){
							that.selector.find("input[name=staffNo]").valChange(staffNos.join(","));
						}else if('aptStaffName' == operateType){
							that.selector.find("input[name=aptStaffNo]").valChange(staffNos.join(","));
						}
					}
				}
			} );
			
		};
		
		//打开组的选择界面
		handlers.openGrpIndex = function(index,obj){
			var that = this.global();
			var oTyp = obj.attr("name");
			
			if(index==0){//清除按钮
				obj.valChange("");
				if('grpName' == oTyp){
					that.selector.find("input[name = grpNo ]").valChange("");
				}else if('aptGrpName' == oTyp ){
					that.selector.find("input[name = aptGrpNo ]").valChange("");
				}
				return;
			}
			
			var grpNames = [];
			if('' != $.trim( obj.val()) ){
				grpNames = obj.val().split(",");
			}
			var grpNos = [];
			if('grpName' == oTyp && '' != $.trim(that.selector.find("input[name = grpNo]").val()) ){//组操作
				grpNos = that.selector.find("input[name = grpNo]").val().split(",");
			}else if('aptGrpName' == oTyp && '' != $.trim(that.selector.find("input[name = aptGrpNo]").val())  ){//指定组操作
				grpNos = that.selector.find("input[name = aptGrpNo]").val().split(",");
			}
			
			that.modal.open( {
				title: "组 | 添加",
				url: "system/dataauth/grpIndex",
				params: {
					grpNos: grpNos,
					grpNames:grpNames
				},
				size: "modal-lg",
				events: {
					hiden: function( closed, data ) {
						if ( !closed ) return;
						obj.valChange(data.grpNames.join(","));
						grpNos = data.grpNos;
						if('grpName' == oTyp){
							that.selector.find("input[name=grpNo]").valChange(grpNos.join(","));
						}else if('aptGrpName' == oTyp ){
							that.selector.find("input[name = aptGrpNo ]").valChange(grpNos.join(","));
						}
					}
				}
			} );
		}
		
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			//验证
			if ( !that.vars.validator.form() ) return;
			//动态验证
			var autPropTyp = that.selector.find("input[name=autPropTyp]").val();
			if('25900002' == autPropTyp){ //人员
				var staffName = that.selector.find("input[name=staffName]").val()
				if( !staffName || '' == $.trim(staffName) ) {
					that.selector.find( "input[name=staffName]" ).validErrorTip("权限涉众请添加员工");
					return;
				}
				
			}
			if('25900001' == autPropTyp){//角色
				 var roleNo = that.selector.find("input[name=roleNo]").val();
				 var orgNo = that.selector.find("input[name=orgNo]").val();
				 var orgMatchTyp = that.selector.find("input[name=orgMatchTyp]").val();
				 if(!roleNo || ''==$.trim(roleNo)){
					 that.selector.find("input[name=roleNo]").validErrorTip("权限涉众请选择角色");
					 return;
				 }
				 if(!orgNo || ''==$.trim(orgNo)){
					 that.selector.find("input[name=orgNo]").validErrorTip("权限涉众请选择起始机构");
					 return;
				 }
				 if(!orgMatchTyp || ''==$.trim(orgMatchTyp)){
					 that.selector.find("input[name=orgMatchTyp]").validErrorTip("权限涉众请选择起始机构匹配方式");
					 return;
				 }
				 
			}
			if('25900004' == autPropTyp){//机构
				var orgNo = that.selector.find("input[name=orgNo]").val();
				var orgMatchTyp = that.selector.find("input[name=orgMatchTyp]").val();
				if(!orgNo || ''==$.trim(orgNo)){
					 that.selector.find("input[name=orgNo]").validErrorTip("权限涉众请选择起始机构");
					 return;
				 }
				 if(!orgMatchTyp || ''==$.trim(orgMatchTyp)){
					 that.selector.find("input[name=orgMatchTyp]").validErrorTip("权限涉众请选择起始机构匹配方式");
					 return;
				 }
				
			}
			if('25900003' == autPropTyp){//组
				var grpName = that.selector.find("input[name=grpName]").val();
				if(!grpName || '' == $.trim(grpName)){
					that.selector.find("input[name=grpName]").validErrorTip("权限涉众请添加组");
					return;
				}
				
			}
			
			var autTypCod = that.selector.find("input[name=autTypCod]").val();
			if('26200004' == autTypCod){//指定类型
				var aptOrgNo = that.selector.find("input[name=aptOrgNo]").val();
				var aptRoleNo = that.selector.find("input[name=aptRoleNo]").val();
				var aptStaffName = that.selector.find("input[name=aptStaffName]").val();
				var aptGrpName = that.selector.find("input[name=aptGrpName]").val();
				
				if((!aptOrgNo || '' == $.trim(aptOrgNo)) && (!aptRoleNo || '' == $.trim(aptRoleNo)) && (!aptStaffName || '' == $.trim(aptStaffName)) && (!aptGrpName || '' == $.trim(aptGrpName)) ){
					that.selector.find("input[name=autTypCod]").validErrorTip("权限类型为指定时，必须指定一个作用范围");
					return;
				}
			}
			
			//提交表单
			var data = that.selector.find( "form" ).serialize();
			//冻结功能
			//方案1 冻结自己的按钮
			//方案2 显示当前页的loading
			//var $button = that.selector.find( "#submitBtn" );
			//$button.disabled( true );
			that.loading.show(); 
			$.ajax( {
				url: "system/dataauth/save",
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
	
		//======================================================
		// 选择权限所属类型一些样式的控制
		//======================================================
		styctr.init = function (type,value){
			var that = this.global();
			if('Aut_Type' == type){//权限所属类型
				if('25900002' == value){ //人员
					that.styctr.staffsty();
				}else if('25900001' == value){//角色
					that.styctr.rolesty();
				}else if('25900004' == value){//机构
					that.styctr.orgsty();
				}else if('25900003' == value){//组
					that.styctr.grpsty();
				}
				
			}else if('Aut_Code_Type' == type){ //权限类型
				if('26200004' == value){//指定
					that.styctr.aptsty();
				}else{
					that.styctr.substy();
				}
				/*
				if('26200001'==value){//下级
					that.styctr.substy();
				}else if('26200002' == value){//本级及下级
					that.styctr.samsubsty();
				}else if('26200003' == value){//平级及下级
					that.styctr.ordsubsty();
				}else if('26200004' == value){//指定
					that.styctr.aptsty();
				}
				*/
			}
			
		}
		
		//选择的是人员
		styctr.staffsty = function (){
			var that = this.global();
			//隐藏和禁用角色，角色所属机构，角色所属机构匹配方式，组
			that.selector.find( "input[name=roleNo]" ).parent().hide();
			that.selector.find( "input[name=orgNo]" ).parent().hide();
			that.selector.find( "input[name=orgMatchTyp]" ).parent().hide();
			that.selector.find( "input[name=staffName]" ).parent().parent().show();
			that.selector.find( "input[name=grpName]" ).parent().parent().hide();
			
			//启用
			that.selector.find( "input[name=staffName]" ).removeAttr("disabled");
			that.selector.find( "input[name=staffNo]" ).removeAttr("disabled");
			//禁用
			that.selector.find( "input[name=roleNo]").attr("disabled","disabled");
			that.selector.find( "input[name=roleName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=orgNo]" ).attr("disabled","disabled");
			that.selector.find( "input[name=orgPath]" ).attr("disabled","disabled");
			that.selector.find( "input[name=orgName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=orgMatchTyp]" ).attr("disabled","disabled");
			that.selector.find( "input[name=grpName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=grpNo]" ).attr("disabled","disabled");
		};
		
		//选择的是角色
		styctr.rolesty = function (){
			var that = this.global();
			//隐藏和禁用员工，组
			that.selector.find( "input[name=roleNo]" ).parent().show();
			that.selector.find( "input[name=orgNo]" ).parent().show();
			that.selector.find( "input[name=orgMatchTyp]" ).parent().show();
			that.selector.find( "input[name=staffName]" ).parent().parent().hide();
			that.selector.find( "input[name=grpName]" ).parent().parent().hide();
			
			//启用
			that.selector.find( "input[name=roleNo]").removeAttr("disabled");
			that.selector.find( "input[name=roleName]" ).removeAttr("disabled");
			that.selector.find( "input[name=orgNo]" ).removeAttr("disabled");
			that.selector.find( "input[name=orgPath]" ).removeAttr("disabled");
			that.selector.find( "input[name=orgName]" ).removeAttr("disabled");
			that.selector.find( "input[name=orgMatchTyp]" ).removeAttr("disabled");
			//禁用
			that.selector.find( "input[name=staffName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=staffNo]" ).attr("disabled","disabled");
			that.selector.find( "input[name=grpName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=grpNo]" ).attr("disabled","disabled");
		};
		
		//选择的是机构
		styctr.orgsty = function (){
			var that = this.global();
			//隐藏和禁用员工，组
			that.selector.find( "input[name=roleNo]" ).parent().hide();
			that.selector.find( "input[name=orgNo]" ).parent().show();
			that.selector.find( "input[name=orgMatchTyp]" ).parent().show();
			that.selector.find( "input[name=staffName]" ).parent().parent().hide();
			that.selector.find( "input[name=grpName]" ).parent().parent().hide();
			
			//启用
			that.selector.find( "input[name=orgNo]" ).removeAttr("disabled");
			that.selector.find( "input[name=orgPath]" ).removeAttr("disabled");
			that.selector.find( "input[name=orgName]" ).removeAttr("disabled");
			that.selector.find( "input[name=orgMatchTyp]" ).removeAttr("disabled");
			//禁用
			that.selector.find( "input[name=roleNo]").attr("disabled","disabled");
			that.selector.find( "input[name=roleName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=staffName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=staffNo]" ).attr("disabled","disabled");
			that.selector.find( "input[name=grpName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=grpNo]" ).attr("disabled","disabled");
		};
		
		//选择的是组
		styctr.grpsty = function (){
			var that = this.global();
			that.selector.find( "input[name=roleNo]" ).parent().hide();
			that.selector.find( "input[name=orgNo]" ).parent().hide();
			that.selector.find( "input[name=orgMatchTyp]" ).parent().hide();
			that.selector.find( "input[name=staffName]" ).parent().parent().hide();
			that.selector.find( "input[name=grpName]" ).parent().parent().show();
			
			//启用
			that.selector.find( "input[name=grpName]" ).removeAttr("disabled");
			that.selector.find( "input[name=grpNo]" ).removeAttr("disabled");
			//禁用
			that.selector.find( "input[name=roleNo]").attr("disabled","disabled");
			that.selector.find( "input[name=roleName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=staffName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=staffNo]" ).attr("disabled","disabled");
			that.selector.find( "input[name=orgNo]" ).attr("disabled","disabled");
			that.selector.find( "input[name=orgPath]" ).attr("disabled","disabled");
			that.selector.find( "input[name=orgName]" ).attr("disabled","disabled");
			that.selector.find( "input[name=orgMatchTyp]" ).attr("disabled","disabled");
		};
		
		//权限类型--下级
		styctr.substy = function(){
			var that = this.global();
			that.selector.find("input[name=aptOrgNo]").parent().hide();
			that.selector.find("input[name=aptRoleNo]").parent().hide();
			that.selector.find("input[name=aptStaffName]").parent().parent().hide();
			that.selector.find("input[name=aptGrpName]").parent().parent().hide();
			
			that.selector.find("input[name=aptOrgNo]").attr("disabled","disabled");
			that.selector.find("input[name=aptOrgName]").attr("disabled","disabled");
			that.selector.find("input[name=aptRoleNo]").attr("disabled","disabled");
			that.selector.find("input[name=aptRoleName]").attr("disabled","disabled");
			that.selector.find("input[name=aptStaffNo]").attr("disabled","disabled");
			that.selector.find("input[name=aptStaffName]").attr("disabled","disabled");
			that.selector.find("input[name=aptGrpName]").attr("disabled","disabled");
			that.selector.find("input[name=aptGrpNo]").attr("disabled","disabled");
		}
		/*
		//权限类型--本级及下级
		styctr.samsubsty = function(){
			var that = this.global();
			that.styctr.substy();
		}
		
		//权限类型-平级及下级
		styctr.ordsubsty = function(){
			var that = this.global();
			that.styctr.substy();
		}
		*/
		//权限类型--指定
		styctr.aptsty = function(){
			var that = this.global();
			that.selector.find("input[name=aptOrgNo]").parent().show();
			that.selector.find("input[name=aptRoleNo]").parent().show();
			that.selector.find("input[name=aptStaffName]").parent().parent().show();
			that.selector.find("input[name=aptGrpName]").parent().parent().show();
			
			that.selector.find("input[name=aptOrgNo]").removeAttr("disabled");
			that.selector.find("input[name=aptOrgName]").removeAttr("disabled");
			that.selector.find("input[name=aptRoleNo]").removeAttr("disabled");
			that.selector.find("input[name=aptRoleName]").removeAttr("disabled");
			that.selector.find("input[name=aptStaffNo]").removeAttr("disabled");
			that.selector.find("input[name=aptStaffName]").removeAttr("disabled");
			that.selector.find("input[name=aptGrpName]").removeAttr("disabled");
			that.selector.find("input[name=aptGrpNo]").removeAttr("disabled");
		}
		
	};
	
	return Global;
});
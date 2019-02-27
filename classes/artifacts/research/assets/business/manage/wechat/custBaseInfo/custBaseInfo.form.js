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
                    custName: {required: true,isChinese:true},   //客户姓名
                    phoneNo: {required: true,isMobile: true},	//手机号
                    certNo: {required: true,isIdCardNo:true}, //身份证
                    bankNo: {required: true,isBankCardNo:true}, //银行卡
                    openingBank: {required: true},//开户行
                    referralCode: {required: true}, //推荐码
                    parentReferralCode: {required: true} //父节点
                },
                messages: {
                    custName: '请输入客户的姓名',
                    phoneNo: '请输入有效的手机号',
                    certNo: '请输入有效的身份证号',
                    bankNo: '请输入有效的银行卡号',
                    openingBank: '请输入开户行',
                    referralCode:"推荐码不能为空",
                    parentReferralCode: "父节点不能为空"
                }
			} );
		};
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			var istrue=true;
			var galobval="";
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
			
			if (item) {
				//that.selector.find( "input[name=parentReferralCode]" ).attr("readonly",true);
				that.selector.find( "input[name=referralCode]" ).attr("readonly",true);
			}
			
			that.selector.find( ":input[name=id]" ).valChange( item.id ); 
			that.selector.find( ":input[name=custNo]" ).valChange( item.custNo ); 
			that.selector.find( ":input[name=parentReferralCode]" ).valChange( item.parentReferralCode );
			that.selector.find( ":input[name=custName]" ).valChange( item.custName );
			that.selector.find( ":input[name=certNo]" ).valChange( item.certNo );
			that.selector.find( ":input[name=phoneNo]" ).valChange( item.phoneNo );
			that.selector.find( ":input[name=referralCode]" ).valChange( item.referralCode );
			that.selector.find( ":input[name=bankNo]" ).valChange( item.bankNo ); 
			that.selector.find( ":input[name=openingBank]" ).valChange( item.openingBank );
		};
		
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			//验证
			if ( !that.vars.validator.form() ) return;
			//jquery 表单数据序列化 必须是form表单中元素
			var data = that.selector.find( "form" ).serialize();
			that.loading.show(); 
			$.ajax( {
				url: "cust/base/info/save",
				type: "POST",
				data: data,
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
			
		};
	};
	
	return Global;
});
define(function () { 
	
	function Global( vars ) {
		//=======================================================
		// 获取基础组件
		//=======================================================
		var base = require( "app/base" )
			, message = base.message
			, tools = base.tools;
		var moment = require( "moment" );
		base.code.cache( "Os_Type,Apply_Type,Is_No" );
		
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
			that.vars.validator = that.selector.find( "form" ).validate( {
				rules: {
					osType: { required: true },
					versionNo: { required: true },
					applyType: {required: true },
					applyEnvir: {required: true },
					isNo: {required: true }
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
			
			that.selector.find( "input[name=osType]" ).select( {
				code: { type: "Os_Type" }
			} );
			that.selector.find( "input[name=applyType]" ).select( {
				code: { type: "Apply_Type" }
			} );
			that.selector.find( "input[name=isNo]" ).select( {
				code: { type: "Is_No" }
			} );

			that.selector.find( "input[name=onlineDate]" ).datetimepicker({
				//maxDate: moment(that.vars.nowDate).format( "YYYY-MM-DD" )
			});
			
			that.selector.find( ".input" ).input( {} );//实例input插件
		};
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			that.selector.find( ":input[name=id]" ).valChange( item.id ); 
			that.selector.find( ":input[name=osType]" ).valChange( item.osType ); 
			that.selector.find( ":input[name=versionNo]" ).valChange( item.versionNo );
			that.selector.find( ":input[name=applyType]" ).valChange( item.applyType );
			that.selector.find( ":input[name=downPath]" ).valChange( item.downPath );
			that.selector.find( ":input[name=isNo]" ).valChange( item.isNo );
			that.selector.find( ":input[name=appSize]" ).valChange( item.appSize );
			that.selector.find( ":input[name=applyEnvir]" ).valChange( item.applyEnvir );
			that.selector.find( ":input[name=onlineDate]" ).valChange( item.onlineDate );
			var s =item.remark;
			var sp=s.replace(/\&nbsp;/g," ").replace(/<br>/g,"\n");
			that.selector.find( ":input[name=remark]" ).valChange(sp);
			
			//that.selector.find( ":input[name=feeNo]" ).disabled( true );
		};
		
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			//验证
			if ( !that.vars.validator.form() ) return;
			var sp=that.selector.find( ":input[name=remark]" ).val();
			that.selector.find( ":input[name=remark]" ).valChange(sp.replace(/ /g,"&nbsp;"));
			var s=that.selector.find( ":input[name=remark]" ).val();
			that.selector.find( ":input[name=remark]" ).valChange(s.replace(/\n/g,"<br>"));
			var data = that.selector.find( "form" ).serialize();
			
			that.loading.show(); 
			$.ajax( {
				url: "system/aSysAppVersions/save",
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
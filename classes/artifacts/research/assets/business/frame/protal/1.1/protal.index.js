define( [ "frame/protal/1.1/protal.core" ], function() { 
	
	var $ = require( "jquery" )
	, base = require( "app/base" )
	, message = base.message
	, tools = base.tools;
	function Global() {
		var that = this;
		this.namespace = "protal.index";
		//this.content = "#protalSection";
		var handlers = this.handlers = {};
		handlers.global = this.global = function() {
			return that;
		}; 
		
		this.init = function() {
			this.layout();
		};
		
		this.layout = function() {
			var that = this.global();
			
			that.selector.find( ".nav-tools-search" ).bind( "click", function( event ) {
				that.selector.find( "#mainSerach" ).slideToggle();
			} );
			
			that.selector.find( ".nav-tools-password" ).bind( "click", function( event ) {
				that.handlers.modifyPassword();
			} );
			
			that.selector.find( ".nav-tools-logout" ).bind( "click", function( event ) {
				that.handlers.logout();
			} );
			
			//未登录监听
			$( document ).bind( "request.ajax.error.510", function( event ) {
				that.handlers.loginForm();
			} );
			
		};
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		handlers.loginForm = function() {
			var that = this.global();
			that.modal.open( {
				title: "用户登录",
				size: "modal-md",
				style: "width: 600px;",
				url: "frame/login/form",
				closeBtn: false,
				events: {
					hiden: function( closed, data ) {
						message.success( "登录成功， 马上跳转..." );
						setTimeout( function() {
							window.location.reload( true );	
						}, 500 );
					}
				}
			} );
		};
		
		
		handlers.modifyPassword = function() {
			var that = this.global();
			that.modal.open( {
				title: "修改密码",
				size: "modal-md",
				url: "frame/protal/modifyPassword"
			} );
		};
		
		handlers.logout = function() {
			var that = this.global();
			that.dialog.confirm( "确认用户注销?", function( event, index ) {
				if ( index == 0 ) {
					$.ajax( {
						url: "frame/logout",
						type: "POST",
						success: function ( data ) {
							if ( !data.success ) {
								return message.error( data.msg );
							}
							message.success( "用户注销成功，马上跳转..." );
							setTimeout( function(){
								window.location.reload(true);
							}, 700 );
						},
						dataType: "json"
					} );
				}
			} );
		};
	}
	return Global;
} );
define(function() {
	var base = require( "app/base" )
		, message = base.message
		, tools = base.tools;
	
	function Global ( vars ) {
		//=======================================================
		// 当前组件
		//=======================================================
		var that = this; //全局对象
		var vars = this.vars = {};//全局变梁
		var handlers = this.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//入口函数 java.main
		that.init = function(){
			this.loadBefore();
			this.layout();
			this.validate();
			this.loadAfter();
		};
		
		//验证
		that.validate = function() {
			var that = this.global();
			that.vars.validator = that.selector.find( "form" ).validate( {
				rules: {
					loginName: { required: true, rangelength: [ 4, 20 ] },
					password: { required: true, rangelength: [ 6, 20 ] },
					code: { required: true, rangelength: [ 4, 4 ] }
				}
			} );
		};
		
		//页面渲染
		that.layout = function() {
			
			that.selector.find( "input[name=loginName]" ).input( {
				size: "lg",
				cssPrefix: "text-right",
				cssPrefixIcon: 'fa fa-user fa-lg',
				cssCol: "col-xs-12"
			} );
			
			
			that.selector.find( "input[name=password]" ).input( {
				size: "lg",
				cssCol: "col-xs-12",
				cssPrefix: "text-right",
				cssPrefixIcon: 'fa fa-keyboard-o fa-lg',
				suffixButtons: [ { icon: "fa fa-eye" } ],
				suffixButtonsHandler: function( event, index ) {
					if ( $( this ).is( "[type=password]" ) ) {
						$( this ).attr( "type", "text" );
						$( event.currentTarget ).find( "i" ).removeClass( "fa-eye" ).addClass( "fa-eye-slash" );
					} else {
						$( this ).attr( "type", "password" );
						$( event.currentTarget ).find( "i" ).removeClass( "fa-eye-slash" ).addClass( "fa-eye" );
					}
				}
			} );
			
			var button = {};
			button.style = format( 'width: 120px;background: center center no-repeat; background-image: url({0}?v={1});', [ "frame/login/captcha.jpg", Math.random() ] );
			button.text = "&nbsp;";
			button.title = "看不清，更换验证码"; 
			
			that.selector.find( "input[name=code]" ).input( {
				size: "lg",
				cssCol: "col-xs-12",
				cssPrefix: "text-right",
				cssPrefixIcon: 'fa fa-picture-o fa-lg',
				suffixButtons: [ button ],
				suffixButtonsHandler: function( event, index ) {
					$( event.target ).css( "background-image", format( "url({0}?v={1})", [ "frame/login/captcha.jpg", Math.random() ] ) );
				}
			} );
			
			that.selector.find( "#submitBtn" ).bind( "click", function( event ) {
				that.handlers.login();
			} );
			
			that.selector.find( "form" ).bind( "keydown", function( event ) {
				if ( event.keyCode == 13 ) {
					that.handlers.login();
				}
			} );
		};
		
		//页面加载
		that.loadBefore = function() {};
		that.loadAfter = function() {};
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		handlers.load = function(){};
		
		handlers.login = function(){
			var that = this.global();
			if ( !that.vars.validator.form() ) return;
			
			var data = that.selector.find( "form" ).serialize();
			
			that.loading.show(); 
			
			$.ajax( {
				url: "frame/login",
				type: "POST",
				data: data,
				complete: function() {
					that.loading.hide(); 
				},
				success: function( data ) {
					that.close( data );
				}
			} );
		};
	}
	
	return Global;
} );
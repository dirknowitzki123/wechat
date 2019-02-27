define( ["bootstrap","toastr"], function () {
	var $ = require("jquery"), toastr = require("toastr");
	toastr.options.newestOnTop = false;
	toastr.options.positionClass = "toast-bottom-full-width";
	toastr.options.closeButton = true;
	toastr.options.closeHtml = '<button><i class="fa fa-power-off"></i></button>';
	toastr.options.showMethod = 'slideDown';
	toastr.options.hideMethod = 'slideUp';
	toastr.options.closeMethod = 'slideUp';
	toastr.options.hideDuration = 300;
	
	function Login() {
		var that = this;
		var vars = this.vars = {};
		this.init = function() {
			this.style();
			this.css();
			vars.$username = $("#username");
			vars.$password = $("#password");
			vars.$code = $("#code");
			vars.$captcha = $("#captcha");
			vars.$btnSubmit = $("#btnSubmit");
			vars.$errMsg = $("#err-msg");
			this.event();
		};
		
		this.css = function(){
			$(window).resize(function() {
				that.style();
			});
		}
		this.style = function(){
			if(parseFloat($(window).width()) < 1200){
		        $(".get-class").removeClass("login-right");
		        $(".get-class").css({
		            "position":"absolute",
		            "left":"50%",
		            "marginLeft":"-200px"
		        });
		    }else{
		        $(".get-class").addClass("login-right");
		        $(".get-class").css({
		            "position":"static",
		            "left":"0",
		            "marginLeft":"0px"
		        });
		    }
		    if(parseFloat($(window).height()) < 665){
		        $(".get-class").css("margin-top","1%");
		    }else{
		        $(".get-class").css("margin-top","5%");
		    }
		}
		
		this.event = function(){
			$("div[class*='login-box']").show();
			vars.$captcha.bind( "click", function() {
				this.src = "frame/login/captcha.jpg?v=" + Math.random();
				return false;
			});
			$("body").bind("keyup",function(event){
				if(event.keyCode == 13)vars.$btnSubmit.trigger("click");
			});
			vars.$btnSubmit.bind("click", function(){
				var username = vars.$username.val().replace(/(^\s*)|(\s*$)/g,""), password = vars.$password.val().replace(/(^\s*)|(\s*$)/g,""), code = vars.$code.val().replace(/(^\s*)|(\s*$)/g,"");
				if(!username || username.length<5 ||username.length>20){
					toastr.warning("登录号长度为5至20");
					return false;
				}else{
					toastr.clear();
				}
				if(!password || password.length<6 ||password.length>20){
					toastr.warning("密码长度为6至20");
					return false;
				}else{
					toastr.clear();
				}
				if(!code || code.length!=4){
					toastr.warning("验证码长度为4");
					return false;
				}else{
					toastr.clear();
				}
				vars.$btnSubmit.attr("disabled", "disabled");
				$.ajax( {
					url: "frame/login",
					type: "POST",
					data: { 
						code: code,
						loginName: username,
						password: password
					},
					complete: function() {
						vars.$btnSubmit.removeAttr("disabled");
					},
					error: function (data) {
						vars.$captcha.trigger("click");
						data = data.responseJSON;
						toastr.warning(data.msg);
					},
					success: function (data) {
						if (data.success) {
							window.location.href = "index";
						}else{
							toastr.warning(data.msg);
						}
					},
					dataType: "json"
				} );
			} );
		};
	};
	
	require( [ "css2!anon/login/1.0/login.index" ], function() {
		$( function() {
			new Login().init();
		} );
	});
	return Login;
} );
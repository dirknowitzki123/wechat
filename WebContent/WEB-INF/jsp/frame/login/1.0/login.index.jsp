<%@ page language="java" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; pageContext.setAttribute("basePath", basePath);%>
<% pageContext.setAttribute("rmd", java.lang.Math.random()); %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<script type="text/javascript"> require( [ "anon/login/1.0/login.index" ] );</script>
</head>
<body class="container-fluid">
<div class="login-box" style="display: none;">
	<div class="login-logo">
        <div class="logo-img">
            <img src="assets/business/anon/login/1.0/images/logo.png" />
        </div>
    </div>
	<div class="login-right get-class">
		<div class="login-text">
			<div class="login-r-top">
	        	<img src="assets/business/anon/login/1.0/images/login.png" />
	        </div>
			<div class="login-bottom">
				<div class="login-row">
	                <p class="row-one">
	                	<span class="login-item-icon"><i class="fa fa-user"></i></span>
	                	<input class="long-inout" type="text" id="username" maxlength="20" autocomplete="off" value="" placeholder="登录号" />
	                </p>
	                <div class="clear"></div>
                </div>
                <div class="login-row">
                	<p class="row-one">
                    	<span class="login-item-icon"><i class="fa fa-lock"></i></span>
                    	<input class="long-inout" type="password" id="password" maxlength="20" autocomplete="off" value="" placeholder="密码" />
                    </p>
                    <div class="clear"></div>
                </div>                
                <div class="login-row">
                    <p class="row-one row-code">
                    	<span class="login-item-icon"><i class="fa fa-angle-double-right"></i></span>
                        <input class="short-inout" type="text" autocomplete="off" maxlength="4" value="" id="code" placeholder="验证码" />
                    </p>
                    <p class="code-img">
						<img src="frame/login/captcha.jpg?v=${rmd}" id="captcha" alt="验证码" title="看不清，更换验证码"/>
					</p>
                    <div class="clear"></div>
                </div>
                <div class="login-row fun-item-box">
                	<input class="submit-btn" type="button" autocomplete="off" id="btnSubmit" value="登&nbsp;录" />
                </div>
                <div class="login-row" id="err-msg"></div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
</div>
</body>
</html>
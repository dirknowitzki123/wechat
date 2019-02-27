<%@ page language="java" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; pageContext.setAttribute("basePath", basePath);%>
<% pageContext.setAttribute("rmd", java.lang.Math.random()); %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<script type="text/javascript"> require( [ "anon/login/1.1/login.index" ] );</script>
</head>
<body class="container-fluid">
<div class="login-logo">
    <img src="assets/business/anon/login/1.1/images/logo.png" />
</div>
<form class="login-div">
    <div class="login-left"><img src="assets/business/anon/login/1.1/images/bg.png" /></div>
    <div class="login-right get-class">
        <div class="login-box">
            <div class="login-r-top">
                <img src="assets/business/anon/login/1.1/images/login.png" />
            </div>
            <div class="login-bottom">
                <form action="" method="post">
                    <div class="login-row">
                        <p class="row-one">
                            <span class="login-item-icon"><i class="fa fa-user"></i></span>
                            <input class="long-inout" type="text" value="" placeholder="用户名/邮箱/手机号" />
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div class="login-row">
                        <p class="row-one">
                            <span class="login-item-icon"><i class="fa fa-lock"></i></span>
                            <input class="long-inout" type="password" value="" placeholder="密码" />
                        </p>
                        <div class="clear"></div>
                    </div>
                    <div class="login-row">
                        <p class="row-one row-code">
                            <span class="login-item-icon"><i class="fa fa-angle-double-right"></i></span>
                            <input class="short-inout" type="text" value="" placeholder="验证码" />
                        </p>
                        <p class="code-img"><img src="frame/login/captcha.jpg?v=${rmd}" /></p>
                        <div class="clear"></div>
                    </div>
                    <div class="login-row fun-item-box">
                        <p>
		                    <span class="fun-rem-me">
		                        <span class="checkbox-box"><i class="fa fa-check-square"></i></span><input type="checkbox" name="rem-me" />&nbsp;记住我
		                    </span>
                            <span class="fun-one"><span class="red-line"></span><a class="a-color" href="#">注册</a></span>
                            <span class="fun-one"><span class="red-line"></span><a class="a-color" href="#">忘记密码？</a></span>
                            <input class="submit-btn" type="submit" value="登&nbsp;陆" />
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</form>
</body>
</html>
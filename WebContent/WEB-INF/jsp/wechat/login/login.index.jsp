<%--
  Created by IntelliJ IDEA.
  User: yiqr
  Date: 2017/6/1
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath %>"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title></title>

    <link rel="stylesheet" href="assets/business/wechat/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" href="assets/business/wechat/css/common.css" />
    <link rel="stylesheet" href="assets/business/wechat/css/style.css" />

    <script type="text/javascript" src="assets/business/wechat/js/jquery.min.js"></script>
    
    <script type="text/javascript" src="assets/business/wechat/validate/jquery.validate.js"></script>
	<script type="text/javascript" src="assets/business/wechat/validate/jquery.validate.extend.js"></script>
	<script type="text/javascript" src="assets/business/wechat/validate/jquery.validate.method.extend.js"></script>
	
    <script type="text/javascript" src="assets/business/wechat/js/main.js"></script>
</head>
<body>
<div class="login">
    <div class="logo-img">
        <img src="assets/business/wechat/img/logo.png"/>
    </div>
    <div class="reg-item" style="padding-top: 15px;">
        <form id="loginForm">
            <p class="base-reg"><input type="text" id="moblNo" name="moblNo" placeholder="请输入手机号" /></p>
            <p class="base-reg"><input type="password"  id="password" name="password" placeholder="请输入登录密码" /></p>
        </form>
        <input class="btn" id="loginBtn" type="button" value="登录" />
        <label class="login-fun">
            <a style="float: left;" href="wechat/register">注册新账户</a>
            <a style="float: right;" href="wechat/password/first">忘记密码</a>
        </label>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        //初始化
        var init = function(){}
        //页面布局
        var layout = function() {
            $("#loginBtn").click( function(event) {
                login();
                return false;
            });
        }
        var login = function () {
            var moblNo = $("#moblNo").val();
            var password = $("#password").val();
            if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(moblNo))){
                toast("fail", "请输入有效的手机号", 1500, ".login", "16%", "20px");
                return false;
            }
            if(!password){
                toast("fail", "请输入你的密码", 1500, ".login", "16%", "20px");
                return false;
            }
            var params = {
                moblNo : moblNo,
                password : password
            }
            
          	//禁用按钮
			$('#loginBtn').attr('disabled','disabled');
            $.ajax( {
                url: "wechat/login",
                type: "POST",
                data: {
                    params : params
                },
                complete: function() {
                },
                success: function( data ) {
                    if ( !data.success ) {
                        toast("warning", data.msg, 2000, ".login", "16%", "20px");
                        $('#loginBtn').removeAttr("disabled");
                        return false;
                    }
                    toast("success", data.msg, 2000, ".login", "16%", "20px");
                    window.location.href="wechat/home";
                }
            } );

        }
        $(function(){
            init();
            layout();
        });
    });
</script>
</body>
</html>


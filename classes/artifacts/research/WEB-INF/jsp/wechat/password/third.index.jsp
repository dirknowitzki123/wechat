<%--
  Created by IntelliJ IDEA.
  User: yiqr
  Date: 2017/6/8
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><html>
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
    <script type="text/javascript" src="assets/business/wechat/js/main.js"></script>
    
     <script type="text/javascript" src="assets/business/wechat/validate/jquery.validate.js"></script>
	<script type="text/javascript" src="assets/business/wechat/validate/jquery.validate.extend.js"></script>
	<script type="text/javascript" src="assets/business/wechat/validate/jquery.validate.method.extend.js"></script>
</head>
<body>
<div class="register">
    <div class="alert-succeed">
        <img src="assets/business/wechat/img/icon.jpg" />
        <p>修改成功</p>
    </div>
    <div class="reg-item" style="padding-top: 15px;">
        <input class="btn" id="goLogin" type="button" value="我要登陆" />
    </div>
</div>
<script type="text/javascript">
    $(function () {
        //初始化
        var init = function(){
        }
        //页面布局
        var layout = function() {
            $("#goLogin").click( function(event) {
                goLogin();
                return false;
            });
        }
        var goLogin = function () {
            window.location.href="wechat/login";
        }
        $(function(){
            init();
            layout();
        });
    });
</script>
</body>
</html>

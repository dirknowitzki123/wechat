<%--
  Created by IntelliJ IDEA.
  User: yiqr
  Date: 2017/6/8
  Time: 16:27
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
    <header class="header">
        <a class="back" href="wechat/login"><i class="fa fa-angle-left"></i></a>
        <h3 class="title">修改密码</h3>
    </header>
    <div class="reg-item" style="padding-top: 15px;">
        <input type="hidden" id="phoneNo" value="${phoneNo}">
        <p class="base-reg"><input type="password" id="password" placeholder="请输入新密码" /></p>
        <p class="base-reg"><input type="password" id="rePassword" placeholder="确认新密码" /></p>
        <input class="btn" id="saveBtn" type="button" value="确定" />
    </div>
</div>
<script type="text/javascript">
    $(function () {
        //初始化
        var init = function(){
        }
        //页面布局
        var layout = function() {
            $("#saveBtn").click( function(event) {
                save();
                return false;
            });
        }
        var save = function () {
            var phoneNo = $("#phoneNo").val();
            if(!phoneNo){
                toast("fail", "手机号不存在", 2000, ".register", "16%", "20px");
                return false;
            }
            if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(phoneNo))){
                toast("fail", "手机号不合法", 2000, ".register", "16%", "20px");
                return false;
            }
            var password = $("#password").val();
            var rePassword = $("#rePassword").val();

            if(password != rePassword){
                toast("fail", "密码不一致", 2000, ".register", "16%", "20px");
                return false;
            }

            var params = {
                phoneNo : phoneNo,
                password : password,
                rePassword : rePassword
            }
            $("#saveBtn").attr('disabled','disabled');
            $.ajax( {
                url: "wechat/password/second/save",
                type: "POST",
                data: {
                    params : params
                },
                complete: function() {
                },
                success: function( data ) {
                    if ( !data.success ) {
                        toast("warning", data.msg, 1500, ".register", "16%", "20px");
                        $("#saveBtn").removeAttr("disabled");
                        return false;
                    }
                    toast("success", data.msg, 1500, ".register", "16%", "20px");
                    window.location.href="wechat/password/third?phoneNo="+phoneNo;
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

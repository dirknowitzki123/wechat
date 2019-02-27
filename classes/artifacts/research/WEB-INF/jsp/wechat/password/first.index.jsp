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
        <h3 class="title">找回密码</h3>
    </header>
    <div class="reg-item" style="padding-top: 15px;">
        <p class="base-reg"><input type="text" id="moblNo" placeholder="请输入手机号" /></p>
        <p class="base-reg"><input style="width: 60%;" type="text" id="checkReslt" placeholder="请输入验证码" /><input class="get-code" id="sendMsg" type="button" value="获取验证码"  /></p>
        <input class="btn" id="next" type="button" value="下一步" />
    </div>
</div>
<script type="text/javascript">
    $(function () {
        //初始化
        var init = function(){
        }
        //页面布局
        var layout = function() {
            $("#next").click( function(event) {
                next();
                return false;
            });
            $("#sendMsg").click(function (event) {
                sendMsg();
                return false;
            })
        }
        var sendMsg = function () {
            var moblNo = $("#moblNo").val();
            if(!moblNo){
                toast("fail", "请输入有效的的手机号", 2000, ".register", "16%", "20px");
                return false;
            }
            if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(moblNo))){
                toast("fail", "请输入有效的的手机号", 1500, ".register", "16%", "20px");
                return false;
            }
            settime($("#sendMsg").get(0));
            var checkTyp = "B010060002";
            var params = {
                moblNo : moblNo,
                checkTyp : checkTyp
            }
         	//禁用按钮
			$('#sendMsg').attr('disabled',true);
            $.ajax( {
                url: "wechat/captcha/send",
                type: "POST",
                data: {
                    params : params
                },
                complete: function() {
                },
                success: function( data ) {
                    if ( !data.success ) {
                        toast("warning", data.msg, 1500, ".register", "16%", "20px");
                        $('#sendMsg').removeAttr("disabled");
                        return false;
                    }
                    toast("success", data.msg, 1500, ".register", "16%", "20px");
                }
            } );
            return false;
        }
        var next = function () {
            var moblNo = $("#moblNo").val();
            if(!moblNo){
                toast("fail", "请输入有效的手机号", 2000, ".register", "16%", "20px");
                return false;
            }
            if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(moblNo))){
                toast("fail", "请输入有效的的手机号", 2000, ".register", "16%", "20px");
                return false;
            }
            var checkReslt = $("#checkReslt").val();

            if(!checkReslt){
                toast("fail", "请输入有效的验证码", 2000, ".register", "16%", "20px");
                return false;
            }

            var checkTyp = "B010060002";
            var params = {
                moblNo : moblNo,
                checkReslt : checkReslt,
                checkTyp : checkTyp
            }

          	//禁用按钮
			$('#next').attr('disabled','disabled');
            $.ajax( {
                url: "wechat/password/first/save",
                type: "POST",
                data: {
                    params : params
                },
                complete: function() {
                },
                success: function( data ) {
                    if ( !data.success ) {
                        toast("warning", data.msg, 1500, ".register", "16%", "20px");
                        $('#next').removeAttr("disabled");
                        return false;
                    }
                    toast("success", data.msg, 1500, ".register", "16%", "20px");
                    window.location.href="wechat/password/second?phoneNo="+moblNo;
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


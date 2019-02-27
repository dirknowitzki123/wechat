<%--
  Created by IntelliJ IDEA.
  User: yiqr
  Date: 2017/6/5
  Time: 14:21
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
    <script type="text/javascript" src="assets/business/wechat/js/main.js"></script>
    
     <script type="text/javascript" src="assets/business/wechat/validate/jquery.validate.js"></script>
	<script type="text/javascript" src="assets/business/wechat/validate/jquery.validate.extend.js"></script>
	<script type="text/javascript" src="assets/business/wechat/validate/jquery.validate.method.extend.js"></script>
</head>
<body>
<div class="cover" id="codeDiv">
    <div class="t-code">
        <img src="assets/business/wechat/img/icon.jpg" style="width: 88px;" />
        <p>您已注册成功，您的推荐码为</p>
        <span id="code"></span>
        <input class="btn" id="goHome" type="button" value="确定" />
    </div>
</div>
<div class="register" style="background: #F3F4F5;">
    <header class="header blue-bg">
        <a class="back" href="wechat/login"><i class="fa fa-angle-left"></i></a>
        <h3 class="title">个人注册信息</h3>
    </header>
    <form id="custForm">
        <div class="reg-item">
            <p class="person-reg"><span>真实姓名</span><input id="custName" name="custName" type="text" /></p>
            <p class="person-reg"><span>联系电话</span><input id="phoneNo" name="phoneNo" value="${custNo}" readonly="readonly"  type="text" /></p>
        </div>
        <div class="reg-item">
            <p class="person-reg"><span>身份证号</span><input id="certNo" name="certNo" type="text" /></p>
            <p class="person-reg"><span>银行卡号</span><input id="bankNo" name="bankNo" type="text" /></p>
            <p class="person-reg"><span>开户行信息</span><input id="openingBank" name="openingBank" type="text" /></p>
            <p class="person-reg"><span>您的邀请码</span><input id="parentReferralCode" name="parentReferralCode" type="text" /></p>
        </div>
    </form>
    <div class="reg-item" style="background: transparent;">
        <input class="btn" id="saveBtn" type="button" value="提交" />
    </div>
</div>

<script type="text/javascript">
    $(function () {

        var validatorCustInfoForm = null;

        //初始化
        var init = function(){}
        //页面布局
        var layout = function() {
            $("#saveBtn").click( function(event) {
                save();
                return false;
            });

            $("#goHome").click(function (event) {
                goHome();
                return false;
            })
        }

        var validator = function () {
            validatorCustInfoForm =  $('#custForm').validate({
                rules: {
                    custName: {required: true,isChinese:true},   //客户姓名
                    phoneNo: {required: true,isMobile: true},	//手机号
                    certNo: {required: true,isIdCardNo:true}, //身份证
                    bankNo: {required: true,isBankCardNo:true}, //银行卡
                    openingBank: {required: true},//开户行
                    parentReferralCode: {required: true} //推荐码
                },
                messages: {
                    custName: '请输入你的姓名',
                    phoneNo: '请输入有效的手机号',
                    certNo: '请输入有效的身份证号',
                    bankNo: '请输入有效的银行卡号',
                    openingBank: '请输入开户行',
                    parentReferralCode:"推荐码不能为空"
                }
            });
        }

        var save = function () {
            if(!validatorCustInfoForm.form()) return false;
            var data = $("#custForm").serialize();
            //禁用按钮
			$('#saveBtn').attr('disabled','disabled')
            $.ajax( {
                url: "wechat/cust/info/save",
                type: "POST",
                data: data,
                complete: function() {
                },
                success: function( data ) {
                    if ( !data.success ) {
                        toast("warning", data.msg, 2000, ".register", "16%", "20px");
                        $( "#saveBtn" ).removeAttr("disabled")
                        return false;
                    }
                    $("#code").text(data.t.referralCode);
                    $("#codeDiv").show();
                    return false;
                }
            } );
        }
        //定义一个函数
        var goHome  = function(){
            window.location.href="wechat/home";//跳转的链接
        }

        $(function(){
            init();
            validator();
            layout();
        });
    });
</script>
</body>
</html>

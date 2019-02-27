<%--
  Created by IntelliJ IDEA.
  User: yiqr
  Date: 2017/6/5
  Time: 14:24
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

<div class="apply" style="background: #F3F4F5;position: relative;">
	<div class="info-box" style="text-align: center;">
		<div class="t-code">
	        <img src="assets/business/wechat/img/icon.jpg" style="width: 88px;" />
	        <p style="margin-top: 15px;color: #3586ff;font-size: 20px" >提交成功，感谢您的参与!</p>
	        <span id="code"></span>
	    </div>
	</div>
</div>
<script type="text/javascript">
  $(function () {
  	var validatorCustInfoForm = null;
      //初始化
      var init = function(){}
      //页面布局
      var layout = function() {
      }
      var validator = function () {
      }
      var save = function(){
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

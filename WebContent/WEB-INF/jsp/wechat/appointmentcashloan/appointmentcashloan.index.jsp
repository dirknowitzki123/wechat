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
	<header class="header blue-bg" style="padding-bottom: 160px;">
		<!-- <a class="back" href="#"><i class="fa fa-angle-left"></i></a> -->
		<h3 class="title">分期资格预约申请</h3>
	</header>
	<div class="info-box">
		<div class="reg-item" style="padding-bottom: 25px;">
		<form id="appointmentForm">
			<div class="apply-item">
				<table class="tab-input">
					<tr>
						<td width="80px"><label>您的姓氏：</label></td>
						<td><input type="text" name="custName" placeholder="请填写您的姓氏" /></td>
					</tr>
					<tr>
						<td><label>联系电话：</label></td>
						<td><input type="text" name="phoneNo" placeholder="请填写您的联系电话" /></td>
					</tr>
					<tr>
						<td><label>所在城市：</label></td>
						<td><input type="text" name="ivingCity" placeholder="请填写您的所在城市" /></td>
					</tr>
				</table>
			</div>
			<!-- <div class="apply-item">
				<table>
					<tr>
						<td rowspan="3" style="vertical-align: text-top;"><label>是否办理过分期：</label></td>
						<td><input type="radio" name="isStaged" value = "39200003"/><span>是，还满5期</span></td>
					</tr>
					<tr>
						<td><input type="radio" name="isStaged" value = "39200002"/><span>是，未满5期</span></td>
					</tr>
					<tr>
						<td><input type="radio" name="isStaged" value = "39200001"/><span>否</span></td>
					</tr>
				</table>
			</div> -->
			<!-- <div class="apply-item">
				<table>
					<tr>
						<td rowspan="3"><label>是否为信用卡客户：</label></td>
						<td width="45px"><input type="radio" name="isCredites" value = "13900001"/><span>是</span></td>
						<td><input type="radio" name="isCredites" value = "13900002"/><span>否</span></td>
					</tr>
				</table>
			</div> -->
			<div class="apply-item">
				<table class="tab-input">
					<tr>
						<td width="96px"><label>推荐码：</label></td>
						<td><input type="text" name="referralCode"  value="${referralCode}" readonly="readonly" /></td>
					</tr>
				</table>
			</div>
			<input class="btn" id="saveBtn" type="button" value="提交" />
			</form>
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
        	$("#saveBtn").click( function(event) {
        		save();
                return false;
            });
        }
        
        var validator = function () {
        	validatorCustInfoForm =  $('#appointmentForm').validate({
                rules: {
                	custName: {required: true,isChinese:true},
                	phoneNo: {required: true,isMobile: true},
                	ivingCity: {required: true},
                	referralCode: {required: true},
                	isStaged: {required: true},
                	isCredites: {required: true}
                },
                messages: {
                    custName: '请输入你的姓氏',
                    phoneNo: '请输入有效的手机号',
                    ivingCity: '请输入你所在的城市',
                    referralCode: '推荐码不能为空',
                    /* isStaged: '请选择是否办理过分期',
                    isCredites: '请选择是否为信用卡客户' */
                }
            });
        }
        
        var save = function(){
        	 if(!validatorCustInfoForm.form()) return false;
        	 var data = $("#appointmentForm").serialize();
        	 $("#saveBtn").attr("disabled","disabled");
        	 $.ajax( {
                 url: "wechat/client/appointment/cashloan/save",
                 type: "POST",
                 data: data,
                 complete: function() {
                 },
                 success: function( data ) {
                     if ( !data.success ) {
                         toast("warning", data.msg, 2000, "body", "16%", "20px");
                         $("#saveBtn").removeAttr("disabled");
                         return false;
                     }
                     toast("success", data.msg, 2000, "body", "16%", "20px");
                     window.location.href="wechat/client/appointment/cashloan/submit/success";
                     return false;
                 }
             } );
        	
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

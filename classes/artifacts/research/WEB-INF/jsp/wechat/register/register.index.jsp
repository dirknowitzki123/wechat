<%--
  Created by IntelliJ IDEA.
  User: yiqr
  Date: 2017/6/5
  Time: 11:28
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
<div class="register">
    <header class="header">
        <a class="back" href="wechat/login"><i class="fa fa-angle-left"></i></a>
        <h3 class="title">注册</h3>
    </header>
    <div class="reg-item" style="padding-top: 15px;">
        <form id="registerForm">
            <p class="base-reg"><input type="text" id="moblNo" name="moblNo" placeholder="请输入手机号" /></p>
            <p class="base-reg"><input type="password" id="password" name="password" placeholder="请输入密码" /></p>
            <p class="base-reg"><input style="width: 60%;" type="text" id="checkReslt" name="checkReslt"  placeholder="请输入验证码" /><input class="get-code" type="button" id="sendMsg" value="获取验证码" /></p>
            <input class="btn" id="saveBtn" type="button" value="注册" />
            <p>
                <input type="checkbox" id="checkBtn"/>
                <label>我已阅读并同意遵守<a id="agreement">《协议》</a></label>
            </p>
            <div id="agreMemo" style="display: none">
            	<h4>信息提交确认书</h4>
		        <p style="text-indent:2em;">用户、您好，您在此不可撤销地同意本次注册后的所有操作流程（包括但不限于注册，登录等）均通过本公司指定的第三方提供的手机应用、微信服务号、网站服务平台等完成。通过您本人手机号注册后的账号内所有进行的信息提交均视为由您本人操作。</p>
				<table border="1"  cellspacing="0">
					<tr><th>基本信息</th></tr>
					<tr><td>甲方（本公司）：四川满得利科技有限公司</td></tr>
					<tr><td>乙方（用户/您）手机号：</td></tr>
				</table>
		        <p style="text-indent:2em;font-weight: bold;">您点击同意/确认/进入或扫描指定二维码，即视为您同意并确认以下条款，并自愿承担条款内容可能引发的一切责任：</p>
		        <p>一、您确认，您是具备独立民事权利及民事行为能力，能独立承担民事责任的自然人；您知悉并确认<span>四川满得利科技有限公司</span>（下简称本公司）是依法成立，且合法存续，具备独立的民事权利及民事行为能力，能独立承担相应的民事责任的民事主体。</p>
		        <p style="font-weight: bold">二、您确认，您自愿将您的或有借款需求的企业或个人（下简称“客户”）的姓名、联系电话、借款需求等信息提交给本公司。提交客户信息的，提交前已经获得该客户的合法授权，即该客户同意您将信息提交至本公司以获得借款。</p>
		        <p style="font-weight: bold">三、您确认，您提交至本公司的信息，本公司可因借款事宜，将您或客户的信息提供给与本公司有合作关系的第三方公司，并可用于向您或客户推介相关借款产品，但本公司应要求第三方公司妥善保管，且仅限于借款事宜使用。</p>
		        <p style="font-weight: bold">四、您确认，您提交的信息，经本公司确认，您或客户确实具有借款需求，且您或客户成功办理本公司推介的借款产品后，本公司按照以下方式支付您的报酬：</p>
		        <p style="text-indent:2em">依据您提供的有效客户信息，即经本公司确认，您或客户确实具有借款需求，且您或客户成功办理本公司推介的借款产品后，本公司按您或客户借款金额的 3% ，并在该金额基础上扣除15%的手续费之后支付您相应的报酬。</p>
		        <p style="font-weight: bold">五、您确认，您向本公司提交的信息均合法、真实、有效。提供客户信息的，您提供的信息的取得、使用等均经过客户的合法授权，授权或信息的取得、使用未采用骚扰、辱骂、购买、交换等不当或违法手段。</p>
		        <p>六、您确认，未擅自或以本公司名义对客户做出必定能获得借款或其他有关借款的一切承诺。</p>
		        <p style="font-weight: bold">七、您确认，本确认书，并不表示您与本公司之间建立了劳动关系，本公司不承担用人单位的任何义务，您亦不享有劳动者的任何权利。双方的关系接受《中华人民共和国合同法》的调整，双方均应按照约定履行各自的权利义务。</p>
		        <p style="font-weight: bold">八、您确认，您若违反本确认书的相关约定，您自愿承担因此引发的一切责任，本公司有权拒绝支付您的报酬，若给本公司造成损失的，您还应赔偿本公司的损失。</p>
		        <p style="font-weight: bold">九、您确认，您将严格按照本公司产品要求和工作流程开展工作，不以任何方式收取客户任何形式的费用，不实施任何形式的欺诈行为，不给客户承诺产品要求以外的内容。</p>
		        <p style="font-weight: bold">十、您确认，您将严格按照《个人信息保护法》等相关法律法规和公司规定开展工作。</p>
		        <p style="font-weight: bold">您确认，您已阅读本确认书的所有内容，并充分理解，您点击同意/确认/进入或扫描指定二维码，即视为您同意并确认以上条款，并自愿承担条款内容可能引发的一切责任。</p>
		    	<div class="confirm_agree">
			    	<p style="text-indent:2em;font-weight: bold;">本人已经认真阅读并理解《信息提交确认书》的所有条款和涵义，自愿签署并接受《信息提交确认书》的所有内容。本人认同《信息提交确认书》的签署方式为远程签署。本人知悉并理解远程签署是指本人身处异地，
			    	通过远程拍摄或录音设备等提供本人手机号等必要资料，并在相关文件上签字，经相关主体确认无误后成立并生效,或者本人通过网络平台(平台展现形式包括不限于微信、APP、网页等）以线上点击确认的方式签署合同，签署后成立并生效
			    	(本人知悉应妥善保管本人在网络平台的注册用户名和密码，自行承担因注册用户名和密码丢失、泄露或允许他人使用所产生的一切后果。同时，只要是通过本人用户名和密码登陆的任何操作均视为本人的真实意思表示，本人愿意承担因此产生的一切责任)。</p>
					<p style="text-indent:2em;font-weight: bold;">本人再次确认，远程签署的确认书效力与当面签署的确认书效力一致，对本人均具有约束力。</p>
		    	</div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var validatorForm = null;	        
        //初始化
        var init = function(){
        	
        }
        //页面布局
        var layout = function() {
        	 var moblNo = localStorage.getItem("moblNo"); 
        	 var password = localStorage.getItem("password");
        	 var checkReslt = localStorage.getItem("checkReslt");
        	 var contractNo = localStorage.getItem("contractNo");
             var isRead = localStorage.getItem("isRead");
             //如果contractNo存在去查询签约状态，签约成功才可以注册
             if (contractNo != "false"){
            	 querySignInfo();
             }
             
             if (moblNo != "false") {
            	 $("#moblNo").val(moblNo);
             }
             if (password != "false") {
            	 $("#password").val(password);
             }
             if (checkReslt != "false") {
            	 $("#checkReslt").val(checkReslt);
             }
             if (isRead == "true") {
            	 $("#checkBtn").attr("checked",true);
             }
             if (isRead == "null") {
            	 $("#checkBtn").attr("checked",false);
             }
        	 $("#checkBtn").click(function () {
                 if ($(this).is(":checked")) {
                	 $("#checkBtn").attr("checked",true);
                	 connectBtn();
                 } else {
                	 toast("fail", "请阅读协议并选择", 1500, ".register", "16%", "20px");
                 }
             })
			
             $("#agreement").click(function () {
   			 	var moblNo = $("#moblNo").val();
                var password = $("#password").val();
                var checkReslt = $("#checkReslt").val();
                var url = "assets/business/wechat/agreement.html?moblNo="+moblNo+"&password="+password+"&checkReslt="+checkReslt;//此处拼接内容
                if (!isEmpty(moblNo)){
                	window.location.href = url;
                }else{
                	toast("fail", "请输入手机号", 1500, ".register", "16%", "20px");
                }
                	
   			});
        	
            $("#saveBtn").click( function(event) {
            	var checkBtn = $("input[type='checkbox']").is(':checked');
            	//是否签约成功
            	var isSign = localStorage.getItem("isSign");
            	if(!checkBtn){
            		toast("fail", "请阅读协议并选择", 1500, ".register", "16%", "20px");
            	}
            	if(!isSign){
            		localStorage.removeItem("contractNo");
            		toast("fail", "请先阅读并签约协议", 1500, ".register", "16%", "20px");
            	}
            	//签约成功并且勾选我已阅读协议才可以注册
            	if(checkBtn && isSign){
            		register();
            		downloadPDF();
                    return false;
            	}
            });
            $("#sendMsg").click(function (event) {
                   sendMsg();
                   return false;
               })
            
        }
        var validator = function () {
            validatorForm =  $('#registerForm').validate({
                rules: {
                    moblNo: {required: true,isMobile: true},   //手机号
                    password: {required: true},	//密码
                    checkReslt: {required: true} //验证码
                },
                messages: {
                    moblNo: '请输入有效的的手机号',
                    password: '请输入密码',
                    checkReslt: '请输入验证码'
                }
            });
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
            var checkTyp = "B010060001";
            var params = {
                moblNo : moblNo,
                checkTyp : checkTyp
            }
            $("#sendMsg").attr('disabled',true);
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
                        $("#sendMsg").removeAttr("disabled");
                        return false;
                    }
                    toast("success", data.msg, 1500, ".register", "16%", "20px");
                }
            } );
            return false;
        }

        var register = function () {
            if(!validatorForm.form()) return false;
            var moblNo = $("#moblNo").val();
            var password = $("#password").val();
            var checkReslt = $("#checkReslt").val();
            var agreMemo = $("#agreMemo").text();
            var checkTyp = "B010060001";
            var params = {
                moblNo : moblNo,
                password : password,
                agreMemo : agreMemo, 
                checkReslt : checkReslt,
                checkTyp : checkTyp
            }
            $("#saveBtn").attr('disabled','disabled');
            $.ajax( {
                url: "wechat/register",
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
                    //清除本地存储值
                    localStorage.clear();
                    toast("success", data.msg, 1500, ".register", "16%", "20px");
                    window.location.href="wechat/login";
                }
            } ); 

            return false;
        }
        
        var connectBtn = function(){
        	var moblNo = $("#moblNo").val();
            var password = $("#password").val();
            var checkReslt = $("#checkReslt").val();
            var url = "assets/business/wechat/agreement.html?moblNo="+moblNo+"&password="+password+"&checkReslt="+checkReslt;//此处拼接内容
            
            if (!isEmpty(moblNo)){
            	window.location.href = url;
            }
            else{
            	toast("fail", "请输入手机号", 1500, ".register", "16%", "20px");
            }
        }
        
      	//查询签约状态
        var querySignInfo = function(){
        	var phoneNo = localStorage.getItem("moblNo");
        	var contractNo = localStorage.getItem("contractNo");
        	var params = {
        			"phoneNo" : phoneNo,
        			"contractNo" : contractNo
                }
        	var jsonData = JSON.stringify(params);
        	$.ajax({
                url: "/research/wechat/signature/contract/querysigninfo",
                type: "POST",
                data: jsonData,
                contentType: "application/json; charset=utf-8",
                async : false, //同步调用
                success:function(data){
    				if (data.map.isSign) {
    					var isSign = data.map.isSign;
    					localStorage.setItem("isSign", isSign);
    					toast("warning", "你的协议签署成功", 1500, ".register", "16%", "20px");
    				}else{
    					//querySignInfo();
    				}
    			},
    			error: function(data){
    				console.log(data);
    				toast("warning", "查询签约状态失败", 1500, ".register", "16%", "20px");
    			}
            }); 
        }
    	
      	//下载合同PDF
        var downloadPDF = function(){
        	var phoneNo = localStorage.getItem("moblNo");
        	var contractNo = localStorage.getItem("contractNo");
        	var params = {
        			"phoneNo" : phoneNo,
        			"contractNo" : contractNo
                }
        	var jsonData = JSON.stringify(params);
        	$.ajax({
                url: "/research/wechat/signature/contract/downloadPDF",
                type: "POST",
                data: jsonData,
                contentType: "application/json; charset=utf-8",
                async : false, //同步调用
                success:function(data){
    				if (data.success) {
    					toast("warning", "下载合同PDF成功", 1500, ".register", "16%", "20px");
    				}else{
    					//toast("warning", data.map.msg, 1500, ".register", "16%", "20px");
    				}
    			},
    			error: function(data){
    				console.log(data);
    				toast("warning", "下载合同PDF失败", 1500, ".register", "16%", "20px");
    			}
            }); 
        }
        
      	//判断字符是否为空的方法
        function isEmpty(obj){
            if(typeof obj == "undefined" || obj == null || obj == ""){
                return true;
            }else{
                return false;
            }
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


<%--
  Created by IntelliJ IDEA.
  User: yiqr
  Date: 2017/6/6
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath %>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title></title>

<link rel="stylesheet"
	href="assets/business/wechat/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="assets/business/wechat/css/common.css" />
<link rel="stylesheet" href="assets/business/wechat/css/style.css" />

<script type="text/javascript"
	src="assets/business/wechat/js/jquery.min.js"></script>
<script type="text/javascript" src="assets/business/wechat/js/main.js"></script>
</head>
<body>
	<div class="register" style="background: #F3F4F5; position: relative;">
		<header class="header blue-bg" style="padding-bottom: 160px;">
			<a class="back" href="wechat/login"><i class="fa fa-angle-left"></i></a>
			<h3 class="title">我的信息</h3>
		</header>
		<input type="hidden" id="custNo" value="${custNo}" />
		<div class="info-box">
			<div class="head-pic">
				<label class="head-img"> <img
					src="assets/business/wechat/img/tuijian.png" />
				</label>
				<p class="name"></p>
			</div>
			<div class="reg-item" style="padding-top: 110px;">
				<p class="person-reg">
					<label>真实姓名</label><input type="text" id="custName"
						readonly="readonly" />
				</p>
				<p class="person-reg">
					<label>联系电话</label><input type="text" id="phoneNo"
						readonly="readonly" />
				</p>
				<p class="person-reg">
					<label>身份证号</label><input type="text" id="certNo"
						readonly="readonly" />
				</p>
				<p class="person-reg">
					<label>推荐码</label><input type="text" id="referralCode"
						readonly="readonly" />
				</p>
			</div>

			<div class="reg-item" style="margin-bottom: 10px;">
				<p class="person-reg">
					<label>分期资格预约申请</label>
				</p>
				<table class="tab-link">
					<tr>
						<td><textarea id="textLink"></textarea></td>
						<td></td>
						 <!-- <td id ="linkTd"></td> -->
						<td>
						<p><img src="wechat/cust/info/show/qrcode/${custNo}" /></p></td>
					</tr>
					<!-- <tr>
						<td><input class="link-btn" type="button" id="copyLink1"
							value="复制链接" /></td>
						<td><input class="link-btn" id="downloadQrcode" type="button" value="下载二维码" /></td>
					</tr> -->
				</table>
			</div>
			<div class="reg-item" style="margin-bottom: 10px;">
				<label>客户批量提交,请点击</label><br><br> 
					<a id="linkwjx" href="https://www.wjx.top/jq/17648117.aspx">https://www.wjx.top/jq/17648117.aspx</a>
					<br>
					<br>
					<br>
					<br>
				<table class="tab-link">
					<!-- <tr>
						<td><input class="link-btn" type="button" id="copyLink2" value="复制链接" />
					</tr> -->
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
    $(function () {
        //初始化
        var init = function(){}
        //页面布局
        var layout = function() {
        	//基本信息
        	fillBaseInfo();
        	//专属链接
        	getDedicatedLink();
        	//下载二维码
        	$("#downloadQrcode").click( function(event) {
        		downloadQrcode();
                return false;
            });
        	
        	   $("#copyLink2").click(function () {
        		   var Url2=$("#linkwjx");
        		  Url2.select(); // 选择对象 
        		  console.log(Url2.select());
        		  if(document.execCommand('copy', false, null)){
        			   document.execCommand("Copy");
        			   alert("已复制好，可贴粘。");
        		  }else{
        			   alert("复制失败，请手动复制");
        			 } 
             });
        	  
        	
        }
       	//获取客服基本信息
        var fillBaseInfo = function(){
        	 $.ajax( {
                 url: "wechat/cust/info",
                 type: "POST",
                 data: {
                     custNo : $("#custNo").val()
                 },
                 async: false,
                 complete: function() {
                 },
                 success: function( data ) {
                     if ( !data.success ) {
                         window.location.href="wechat/cust/info";
                         return false;
                     }
                     var info = data.t;
                     $("#custName").val(info.custName);
                     $("#phoneNo").val(info.phoneNo);
                     $("#certNo").val(info.certNo);
                     $("#referralCode").val(info.referralCode);
                     return false;
                 }
             } );
        }
        var getDedicatedLink = function(){
        	$.ajax( {
                url: "wechat/cust/info/get/dedicatedlink",
                type: "POST",
                data: {
                    custNo : $("#custNo").val()
                },
                complete: function() {
                },
                success: function( data ) {
                    if ( !data.success ) {
                        return false;
                    }
                    var map = data.map;
                    /* $a=$("<a href="+map.url+">"+map.url+"</a>");
                    $("#linkTd").append($a); */
                    console.log(map.url);
                    $("#textLink").val(map.url);
                    return false;
                }
            } );
        }
        
        var downloadQrcode = function(){
        	$("#downloadQrcode").attr("disabled","disabled");
        	var custNo = $("#custNo").val();
        	var iframeDown = document.getElementById("_download_attribExcel");
    		if(!iframeDown){
    			iframeDown = document.createElement('iframe');
    			iframeDown.width = '0';
    			iframeDown.height = '0';
    			iframeDown.name = "_download_attribExcel";
    			iframeDown.id = "_download_attribExcel";
    			if (iframeDown.attachEvent){
    				iframeDown.attachEvent("onload", function(){
    					base.message.error("导出失败!<br/>"+$(window.frames["_download_attribExcel"].document).find("div[class='alert alert-danger']").html());
    			    });
    			} else {
    				iframeDown.onload = function(){
    					if($(window.frames["_download_attribExcel"].document).find("div[class='alert alert-danger']").length==1)
    					base.message.error("导出失败!<br/>"+$(window.frames["_download_attribExcel"].document).find("div[class='alert alert-danger']").html());	
    				};
    			}
    			document.body.appendChild(iframeDown);
    		}
    		iframeDown.src = "wechat/cust/info/download/qrcode/"+custNo;
        	$("#downloadQrcode").removeAttr("disabled");
        }
        
        var login = function () {
        }
        $(function(){
            init();
            layout();
        });
    });
</script>
</body>
</html>


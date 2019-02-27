<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("nowDate", new Date().getTime()); %>
<!doctype html>
<html>
<head>
<title>用户管理 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/system/asysuser/asysuser.form", { nowDate: "${nowDate}"} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<input type="hidden" name="positionName"/>
			<input type="hidden" name="titleName"/>
<!-- 			<input type="hidden" name="levelName"/> -->
			<div class="row">
				<input type="text" class="input" name="userName" options='{txtLabel: "姓名"}'/>
				<input type="text" class="input" name="loginName" options='{txtLabel: "登录名"}'/>
				<input type="password" class="input" name="password" options='{txtLabel: "初始密码"}' value="123123"/>
				<input type="text" class="select" name="sex" options='{txtLabel: "性别"}'/>
				<input type="text" class="input" name="birhday" options='{txtLabel: "出生日期"}'/>
				<input type="text" class="input" name="cardNo" options='{txtLabel: "证件编号"}'/>
				<input type="text" class="input" name="mobile" options='{txtLabel: "手机"}'/>
				<input type="text" class="selectree" name="orgCode" options='{txtLabel: "所属机构"}'/>
				<input type="text" class="select" name="status" options='{txtLabel: "是否启用"}'/>
				<input type="text" class="input" name="email" options='{txtLabel: "邮箱"}'/>
				<input type="text" class="input" name="homeAddr" options='{txtLabel: "家庭住址"}'/>
				<input type="text" class="input" name="homeTel" options='{txtLabel: "家庭电话"}'/>
				<input type="text" class="input" name="workYears" options='{txtLabel: "工作年限"}'/>
				<input type="text" class="input" name="leaderComment" options='{txtLabel: "员工评价"}'/>
				
<!-- 				<input type="text" class="select" name="levelNo" options='{txtLabel: "员工等级名称"}'/> -->
				<input type="text" class="input" name="workArea" options='{txtLabel: "区域"}'/>
				<input type="text" class="input" name="arriWorkDate" options='{txtLabel: "到岗时间"}'/>
			    <input type="text" class="select" name="positionNo" options='{txtLabel: "岗位名称"}'/>
			    <input type="text" class="select" name="titleNo" options='{txtLabel: "职称名称"}'/>
			    <input type="text" class="input" name="natiPlac" options='{txtLabel: "籍贯"}'/>
				<input type="text" class="input" name="nowAddre" options='{txtLabel: "现居地"}'/>
				<input type="text" class="input" name="homeRegiType" options='{txtLabel: "户籍类别"}'/>
				<input type="text" class="input" name="college" options='{txtLabel: "员工毕业学校"}'/>
				<input type="text" class="select" name="education" options='{txtLabel: "学历"}'/>
				<input type="text" class="input" name="profInfo" options='{txtLabel: "专业名称"}'/>
				<input type="text" class="input" name="weixinAddr" options='{txtLabel: "微信地址"}'/>
				<input type="text" class="input" name="bankCardNo" options='{txtLabel: "银行卡号"}'/>
				<input type="text" class="input" name="openAcctInfo" options='{txtLabel: "开户行信息"}'/>
				<input type="text" class="input" name="prvCompName" options='{txtLabel: "上一家公司名字"}'/>
				<input type="text" class="input" name="finaObtaTimes" options='{txtLabel: "金融从业年限"}'/>
				<input type="text" class="input" name="soseInfo" options='{txtLabel: "参保情况"}'/>
				<input type="text" class="input" name="emplChnl" options='{txtLabel: "招聘渠道"}'/>
				<textarea type="text" class="input" name="remark" options='{txtLabel: "备注", cssCol: "col-xs-12"}'></textarea>
			</div>
		</form>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="submitBtn"><i class="fa fa-save"></i> 提交</button>
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>
	</div>
</body>
</html>
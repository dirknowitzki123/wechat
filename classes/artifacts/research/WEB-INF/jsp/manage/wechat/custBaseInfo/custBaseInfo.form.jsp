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
	App( "business/manage/wechat/custBaseInfo/custBaseInfo.form", { nowDate: "${nowDate}"} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<input type="hidden" name="custNo"/>
			<div class="row">
				<input type="text" class="input" name="parentReferralCode" options='{txtLabel: "父节点(推荐码)"}'/>
				<input type="text" class="input" name="custName" options='{txtLabel: "客户姓名"}'/>
				<input type="text" class="input" name="certNo" options='{txtLabel: "身份证号"}'/>
				<input type="text" class="input" name="phoneNo" options='{txtLabel: "手机号"}'/>
				<input type="text" class="input" name="referralCode" options='{txtLabel: "推荐码"}'/>
				<input type="text" class="input" name="bankNo" options='{txtLabel: "银行卡号"}'/>
				<input type="text" class="input" name="openingBank" options='{txtLabel: "开户行"}'/>
			</div>
		</form>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="submitBtn"><i class="fa fa-save"></i> 提交</button>
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>
	</div>
</body>
</html>
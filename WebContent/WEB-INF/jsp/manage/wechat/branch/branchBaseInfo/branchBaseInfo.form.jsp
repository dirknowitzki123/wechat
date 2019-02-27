<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("nowDate", new Date().getTime()); %>
<!doctype html>
<html>
<head>
<title>商户管理 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/manage/wechat/branch/branchBaseInfo/branchBaseInfo.form", { nowDate: "${nowDate}"} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<div class="row">
				<input type="text" class="input" name="branchName" options='{txtLabel: "商户名称"}'/>
				<input type="text" class="input" name="parReferCode" options='{txtLabel: "经办人(父级推荐码)"}'/>
				<input type="text" class="input" name="referCode" options='{txtLabel: "推荐码"}'/>
			</div>
		</form>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="submitBtn"><i class="fa fa-save"></i> 提交</button>
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>
	</div>
</body>
</html>
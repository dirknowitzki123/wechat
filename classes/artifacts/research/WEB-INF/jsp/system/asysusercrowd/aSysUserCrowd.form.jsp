<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>用户群管理 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/system/asysusercrowd/aSysUserCrowd.form", {} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<div class="row">
				<input type="text" class="input" name="crowdNo" options='{txtLabel: "群编号"}'/>
				<input type="text" class="input" name="crowdName" options='{txtLabel: "群名称"}'/>
				<input type="text" class="select" name="ruleId" options='{txtLabel: "规则"}'/>
				<input type="text" class="select" name="stat" options='{txtLabel: "状态"}'/>
				<textarea type="text" class="input" name="crowdDesc" options='{txtLabel: "描述", cssCol: "col-xs-12"}'></textarea>
			</div>
		</form>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="submitBtn"><i class="fa fa-save"></i> 提交</button>
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>
	</div>
</body>
</html>
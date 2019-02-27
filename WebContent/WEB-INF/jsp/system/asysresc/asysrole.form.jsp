<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>用户管理 | 查询</title>
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript"> 
	App("business/system/asysrole/asysrole.form", {});
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<div class="row">
				<input type="text" class="input" name="roleCode" options='{txtLabel: "角色编号"}'/>
				<input type="text" class="input" name="roleName" options='{txtLabel: "角色名称"}'/>
				<input type="text" class="select isUse" name="isUse" options='{txtLabel: "角色状态"}'/>
				<textarea type="text" class="input" name="remark" options='{txtLabel: "备注", cssCol: "col-xs-12"}'></textarea>
			</div>
		</form>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
			<button type="button" class="btn btn-danger" id="submitBtn"><i class="fa fa-save"></i> 提交</button>
		</div>
	</div>
</body>
</html>
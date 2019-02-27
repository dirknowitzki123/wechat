<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>用户组管理 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/system/asysusergroup/aSysUserGroup.form", {} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<div class="row">
				<input type="text" class="input" name="groupNo" options='{txtLabel: "组编号"}'/>
				<input type="text" class="input" name="groupName" options='{txtLabel: "组名称"}'/>
				<input type="text" class="select" name="stat" options='{txtLabel: "状态"}'/>
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
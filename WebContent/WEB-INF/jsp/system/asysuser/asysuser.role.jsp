<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>角色管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript"> 
	App("business/system/asysuser/asysuser.role", {});
</script>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<ul id="roleTree" class="ztree"></ul>
		</div>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger" id="submitBtn"><i class="fa fa-save"></i> 保存</button>
		</div>
	</div>
</body>
</html>
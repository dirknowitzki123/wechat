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
	App("business/system/asysresc/asysresc.index", {});
</script>
</head>

<body>
	<div class="container-fluid">
		<div class="grid-query">
			<input type="text" class="input" name="roleCode" options='{txtPrefix: "角色编号"}' />
			<input type="text" class="input" name="roleName" options='{txtPrefix: "角色名称"}' />
			<input type="text" class="select" name="isUse" options='{txtPrefix: "角色状态"}' />
		</div>
		<table id="rescGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
			</caption>
		</table>
	</div>
</body>
</html>
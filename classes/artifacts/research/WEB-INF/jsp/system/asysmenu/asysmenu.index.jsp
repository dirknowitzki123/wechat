<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>用户管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript"> 
	App("business/system/asysmenu/asysmenu.index", {demo: 1} );
</script>
</head>

<body>
	<div class="container-fluid">
		<div class="grid-query">
			<input type="text" class="input" name="sysCode" options='{txtPrefix: "系统模块"}' />
			<input type="text" class="input" name="menuType" options='{txtPrefix: "菜单类型"}' />
			<input type="text" class="input" name="menuName" options='{txtPrefix: "菜单名称"}' />
			<input type="text" class="input" name="menuCode" options='{txtPrefix: "权限许可"}' />
		</div>
		<table id="roleGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
				<button type="button" class="btn btn-default" id="addChildren"><i class="fa fa-plus"></i> 下级管理</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
			</caption>
		</table>
	</div>
</body>
</html>
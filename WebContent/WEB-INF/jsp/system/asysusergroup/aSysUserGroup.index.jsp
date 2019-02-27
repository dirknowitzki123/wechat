<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("nowDate", new Date()); %>
<!doctype html>
<html>
<head>
<title>用户组管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
${param.PAGEID}.section .section-container #roleGrid { }
</style>
<script type="text/javascript"> 
	App("business/system/asysusergroup/aSysUserGroup.index", {});
</script>
</head>

<body>
	<div class="container-fluid">
		<div class="grid-query">
			<input type="text" class="input" name="groupNo" options='{txtPrefix: "组编号"}' />
			<input type="text" class="input" name="groupName" options='{txtPrefix: "组名称"}' />
			<input type="text" class="select" name="stat" options='{txtPrefix: "状态"}' />
		</div>
		<table id="userGroupGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
				<button type="button" class="btn btn-default" id="userManagerBtn"><i class="fa fa-user"></i> 用户管理</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
			</caption>
		</table>
	</div>
</body>
</html>
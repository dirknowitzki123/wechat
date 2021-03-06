<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>用户组员管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
${param.PAGEID}.section .section-container #roleGrid { }
</style>
<script type="text/javascript"> 
	App("business/system/asysusergroup/aSysUserGroup.user", {});
</script>
</head>

<body>
	<div class="container-fluid">
		<input type="hidden" name="groupNo"/>
		<input type="hidden" name="groupName"/>
		<div class="grid-query">
			<input type="text" class="input" name="userName" options='{txtPrefix: "用户名"}' />
			<input type="text" class="input" name="loginName" options='{txtPrefix: "登录名"}' />
<!-- 			<input type="text" class="select" name="stat" options='{txtPrefix: "启用状态"}' /> -->
		</div>
		<table id="userGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 添加组员</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 移除组员</button>
			</caption>
		</table>
	</div>
</body>
</html>
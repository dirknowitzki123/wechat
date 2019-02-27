<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("nowDate", new Date()); %>
<!doctype html>
<html>
<head>
<title>用户管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
${param.PAGEID}.section .section-container #roleGrid { }
</style>
<script type="text/javascript"> 
	App("business/system/asysuser/asysuser.index", { now: "${nowDate}"});
</script>
</head>

<body>
	<div class="container-fluid">
		<div class="grid-query">
			<input type="text" class="input" name="userName" options='{txtPrefix: "姓名"}' />
			<input type="text" class="input" name="loginName" options='{txtPrefix: "登录名"}' />
		</div>
		<table id="roleGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
				<button type="button" class="btn btn-default" id="authBtn"><i class="fa fa-edit"></i> 角色配置</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
<!-- 				<button type="button" class="btn btn-default" id="export"><i class="fa fa-remove"></i> 导出</button> -->
			</caption>
		</table>
	</div>
</body>
</html>
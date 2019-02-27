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
	App("business/system/asyssecret/asyssecret.index", {});
</script>
</head>

<body>
 
	<div class="container-fluid">
		<div class="grid-query">
			<input type="hidden" name="id">
			<input type="text" class="input" name="clientFlag"  options='{txtPrefix: "客户端标志"}' />
			<input type="text" class="input" name="pubKey" options='{txtPrefix: "公钥"}' />
			<input type="text" class="input" name="priKey" options='{txtPrefix: "私钥"}' />
		</div>
		<table id="roleGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
			</caption>
		</table>
	</div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>码组管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript"> 
	App("business/system/asyscode/aSysCode.groupIndex", {} );
</script>
</head>

<body>
	<div class="container-fluid col-sm-12 col-md-12">
		<div class="grid-query">
			<input type="text" class="input" name="groupCode" options='{txtPrefix: "码组编码"}' />
		</div>
		<table id="groupGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
				<button type="button" class="btn btn-default" id="manageBtn"><i class="fa fa-edit"></i> 码值管理</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
			</caption>
		</table>
	</div>
</body>
</html>
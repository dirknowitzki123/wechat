<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>码值管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript"> 
	App("business/system/asyscode/aSysCode.typeIndex", {} );
</script>
</head>

<body>
	<div class="container-fluid col-sm-12 col-md-12">
		<div class="grid-query">
			<input type="text" class="input" name="typeName" options='{txtPrefix: "码类名称"}' />
			<input type="text" class="input" name="typeCode" options='{txtPrefix: "码类编码"}' />
		</div>
		<table id="typeGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
			</caption>
		</table>
	</div>
	<h6 class="page-header"><i class="fa fa-tasks"></i>&nbsp;&nbsp;码值管理</h6>
	<div class="container-fluid col-sm-12 col-md-12">
		<table id="codeGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
			</caption>
		</table>
	</div>
</body>
</html>
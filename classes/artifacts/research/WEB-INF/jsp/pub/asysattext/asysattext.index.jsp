<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>公告管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
${param.PAGEID}.section .section-container #aSysAttExtGrid { }
</style>
<script type="text/javascript"> 
	App("business/pub/asysattext/asysattext.index", {});
</script>
</head>

<body>
	<div class="container-fluid">
		<div class="grid-query">
			<input type="text" class="input" name="attName" options='{txtPrefix: "文档名称"}'/>
			<input type="text" class="select" name="attTyp" options='{txtPrefix: "文档类型"}' />
			<input type="text" class="input" name="instUserName" options='{txtPrefix: "上传用户姓名"}' />
		</div>
		<table id="aSysAttExtGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增文档</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑文档</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除文档</button>
			</caption>
		</table>
	</div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>版本管理</title>
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App("business/system/asysappversions/aSysAppVersions.index", {} );
</script>
</head>
<body>
	<div class="container-fluid col-sm-12 col-md-12">
		<div class="grid-query">
			<input type="text" class="input" name="versionNo" options='{txtPrefix: "版本编号"}' />
			<input type="text" class="input" name="osType" options='{txtPrefix: "版本类型"}' />
		</div>
		<table id="versionsGrid">
			<caption>
				<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
			</caption>
		</table>
	</div>
</body>
</html>
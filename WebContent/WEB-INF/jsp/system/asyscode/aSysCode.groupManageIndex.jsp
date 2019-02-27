<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>码类管理 | 码值管理</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/system/asyscode/aSysCode.groupManageIndex", {} );
</script>
</head>
<body>
	<div class="container-fluid">
		<input type="hidden" name="groupCode"/>
		<input type="hidden" name="typeCode"/>
		<h6 class="page-header"><i class="fa fa-tasks"></i>&nbsp;&nbsp;已选码值</h6>
		<div class="container-fluid col-sm-12 col-md-12">
			<div class="grid-query" id="code-grid-query">
				<input type="text" class="input" name="valCode" options='{txtPrefix: "码值编码"}' />
				<input type="text" class="input" name="valName" options='{txtPrefix: "码值名称"}' />
			</div>
			<table id="codeGrid">
				<caption>
					<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 移除码值</button>
				</caption>
			</table>
		</div>
		<h6 class="page-header"><i class="fa fa-tasks"></i>&nbsp;&nbsp;待选码值</h6>
		<div class="container-fluid col-sm-12 col-md-12">
			<div class="grid-query" id="nse-grid-query">
				<input type="text" class="input" name="valCode" options='{txtPrefix: "码值编码"}' />
				<input type="text" class="input" name="valName" options='{txtPrefix: "码值名称"}' />
			</div>
			<table id="notSelectedCodeGrid">
				<caption>
					<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 添加到码组</button>
				</caption>
			</table>
		</div>
	</div>
</body>
</html>
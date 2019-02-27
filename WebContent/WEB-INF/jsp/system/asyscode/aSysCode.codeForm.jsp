<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>码值管理 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/system/asyscode/aSysCode.codeForm", {} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<input type="hidden" name="typeCode"/>
			<div class="row">
				<input type="text" class="input" name="valName" options='{txtLabel: "码值名称"}'/>
				<input type="text" class="input" name="valCode" options='{txtLabel: "码值"}'/>
				<input type="text" class="select" name="status" options='{txtLabel: "启用"}'/>
				<textarea type="text" class="input" name="remark" options='{txtLabel: "备注", cssCol: "col-xs-12"}'></textarea>
			</div>
		</form>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="submitBtn"><i class="fa fa-save"></i> 提交</button>
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>
	</div>
</body>
</html>
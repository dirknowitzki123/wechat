<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("nowDate", new Date().getTime()); %>
<!doctype html>
<html>
<head>
<title>附件管理 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/pub/asysattext/asysattext.form", {  nowDate : "${nowDate}"} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="" id="attExtForm">
			<input type="hidden" name="id"/>
			<input type="hidden" name="attNo" id="attNo" />
			<input type="hidden" name="attIsDir" value="false"/>
			<input type="hidden" name="attSort" value="1"/>
			<div class="row">
				<input type="text" class="input" name="attName" options='{txtLabel: "文档名称"}'/>
				<input type="text" class="select" name="attTyp" options='{txtLabel: "文档类型"}' />
				<textarea type="text" class="input" name="attDesc" options='{txtLabel: "文档描述", cssCol: "col-xs-12"}'></textarea>
				<textarea type="text" class="input" name="attRemark" options='{txtLabel: "备注信息", cssCol: "col-xs-12"}'></textarea>
			</div>
		</form>
		<div class="row row0">
			<input type="file" class="input" name="file" options='{txtLabel: "上传文档"}'/>
		</div>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="submitBtn"><i class="fa fa-save"></i> 保存</button>
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>
	</div>
</body>
</html>
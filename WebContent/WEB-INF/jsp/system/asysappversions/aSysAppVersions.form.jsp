<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>版本管理 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/system/asysappversions/aSysAppVersions.form", {} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<div class="row">
				<input type="text" class="select" name="osType" options='{txtLabel: "版本类型"}'/>
				<input type="text" class="input" name="versionNo" options='{txtLabel: "版本编号"}'/>
				<input type="text" class="select" name="applyType" options='{txtLabel: "适用类型"}'/>
				<input type="text" class="input" name="downPath" options='{txtLabel: "下载地址"}'/>
				<input type="text" class="select" name="isNo" options='{txtLabel: "是否强制更新"}'/>
				<input type="text" class="input" name="appSize" options='{txtLabel: "大小"}'/>
				<input type="text" class="input" name="applyEnvir" options='{txtLabel: "适用环境"}'/>
				<input type="text" class="input" name="onlineDate" options='{txtLabel: "上线日期"}'/>
				<textarea type="text" class="input" name="remark" options='{txtLabel: "说明", cssCol: "col-xs-12"}'></textarea>
			</div>
		</form>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="submitBtn"><i class="fa fa-save"></i> 提交</button>
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>
	</div>
</body>
</html>
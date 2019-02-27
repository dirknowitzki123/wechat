<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>用户管理 | 查询</title>
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript"> 
	App("business/system/asyssecret/asyssecret.form", {});
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
				<input type="hidden" name="id"/>
			<div class="row">
				<input type="text" class="select" name="clientFlag" options='{txtLabel: "客户端标志"}'/>
				<input type="text" class="select" name="status" options='{txtLabel: "是否启用"}'/>
				<textarea type="text" style="resize: none; " rows="12"  class="input" name="pubKey" options='{txtLabel: "公钥", cssCol: "col-xs-12"}'></textarea>
				<textarea type="text"style="resize: none; " rows="12" class="input" name="priKey" options='{txtLabel: "私钥", cssCol: "col-xs-12"}'></textarea>
			</div>
				
		</form>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger" id="submitBtn"><i class="fa fa-save"></i> 提交</button>
			<button type="button" class="btn btn-danger" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>
	</div>
</body>
</html>
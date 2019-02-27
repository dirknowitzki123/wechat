<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>组织机构管理 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/system/asysorg/asysorg.form", {} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<input type="hidden" name="parentId"/>
			<div class="row">
				<input type="text" class="input" name="orgCode" options='{txtLabel: "编号"}'/>
				<input type="text" class="input" name="orgName" options='{txtLabel: "名称"}'/>
				<input type="text" class="input" name="orgPhone" options='{txtLabel: "电话"}'/>
				<input type="text" class="input" name="orgType" options='{txtLabel: "类型"}'/>
				<input type="text" class="input" name="orgLevel" options='{txtLabel: "级别"}'/>
				<input type="text" class="input" name="status" options='{txtLabel: "是否启用"}'/>
				<input type="text" class="input" name="orgOrder" options='{txtLabel: "排序"}'/>
				<input type="text" class="input" name="parentName" options='{txtLabel: "父机构"}'/>
				<input type="text" class="selectArea"  name="validCiry" options='{txtLabel: "所在地区"}'/>
				<textarea type="text" class="input" name="orgAddr" options='{txtLabel: "地址", cssCol: "col-xs-12"}'></textarea>
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
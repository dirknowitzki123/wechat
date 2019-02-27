<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>用户管理 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/system/asysmenu/asysmenu.form", {} );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="">
			<input type="hidden" name="id"/>
			<input type="hidden" name="parentMenuId"/>
			
			<div class="row">
				<input type="text" class="select" name="sysCode" options='{txtLabel: "系统模块"}'/>
				<input type="text" class="input" name="menuName" options='{txtLabel: "菜单名称"}'/>
				<input type="text" class="select" name="menuType" options='{txtLabel: "菜单类型"}'/>
			    <input type="text" class="input" name="menuCode" options='{txtLabel: "权限许可"}'/>
				<input type="text" class="input" name="menuLevel" options='{txtLabel: "菜单级别"}'/>
				<input type="text" class="input" name="url" options='{txtLabel: "菜单路径"}'/>
				<input type="text" class="input" name="byOrder" options='{txtLabel: "菜单顺序"}'/>
				<input type="text" class="input" name="menuIcon" options='{txtLabel: "菜单图标"}'/>
				<input type="text" class="select" name="isUserAble" options='{txtLabel: "是否启用"}'/>
				<!-- <input type="text" class="input" name="sysCode" options='{txtLabel: "系统代码"}'/> -->
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
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>用户管理 | 人员选择</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container .ztree-container { min-height: 200px; border-bottom: 1px solid #ddd; margin-bottom: 15px; padding-bottom: 15px; }
	${param.PAGEID}.section .section-container .ztree * { font-size: 14px; }
</style>
<script type="text/javascript">
	App( "business/pub/comm/userOrg.form", {} );
</script>
</head>
<body>
	<div  class="container-fluid">
<!-- 	    <div class="grid-query">
			<input type="text" class="input" name="userName" options='{txtPrefix: "用户名"}' />
			<input type="text" class="input" name="loginName" options='{txtPrefix: "登录名"}' />
		</div>     -->
		<div class="ztree-container">
			<ul id="userTree" class="ztree"></ul>
		</div>
 		
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="submitBtn"><i class="fa fa-check"></i> 确定</button>
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>         
	</div>
</body>
</html>
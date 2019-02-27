<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>用户登录 | 表单</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript"> App("anon/login/login.form", {} ); </script>
</head>
<body>
	<div class="container-fluid" namespace="anon.login.login.form">
		<form class="row">
			<input type="text" class="input" name="loginName" autocomplete="off" options="{txtPrefix: ' 用户账户' }" maxlength="20" value="" placeholder="登录号"/>
			<input type="password" class="input-password" name="password" autocomplete="off" options="{txtPrefix: '用户密码' }" maxlength="20" value="" placeholder="密码"/>
			<input type="text" class="input-code" name="code" autocomplete="off" options="{txtPrefix: '&#12288;验证码' }" maxlength="4" placeholder="验证码"/>
		</form>
		<div class="row row0 text-center tools-container" style="border-top: 1px solid #ddd; padding-top: 15px; margin-top: 15px;">
			<button type="button" class="btn btn-danger btn-lg" id="submitBtn"><i class="fa fa-check"></i> &#12288;登&#12288;&#12288;录&#12288;</button>
		</div>
	</div>
</body>
</html>
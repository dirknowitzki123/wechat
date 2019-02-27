<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<sitemesh:write property='head' />
	<script type="text/javascript"> App("business/frame/protal/protal.modifyPassword", {} );</script>
</head>
<body>
	<form class="container-fluid">
		<input type="text" class="input" name="oldPassword" options="{txtPrefix: '旧&#12288;密&#12288;码' }" />
		<input type="text" class="input" name="newPassword" options="{txtPrefix: '新&#12288;密&#12288;码' }" />
		<input type="text" class="input" name="reNewPassword" options="{txtPrefix: '确认新密码' }" />
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger" id="submitBtn"><i class="fa fa-save"></i> 提交</button>
			<button type="button" class="btn btn-danger" id="closeBtn"><i class="fa fa-remove"></i> 取消</button>
		</div>
	</form>
</body>
</html>
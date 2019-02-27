<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title><sitemesh:write property='title' />${ config[ "system.name" ] }</title>
	<sitemesh:write property='head' />
</head>
<body>
	<sitemesh:write property='body'/>
</body>
</html>
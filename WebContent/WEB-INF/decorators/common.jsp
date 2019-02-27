<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; if(basePath == null || basePath.equals("")) basePath = "/"; pageContext.setAttribute("basePath", basePath);%>
	<base href="${basePath }" />
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<title>${ config[ "system.name" ] }</title>	
	<script type="text/javascript" src="assets/component/requirejs/2.1.11/require.js" data-main=""></script>
	<script type="text/javascript" src="assets/require.config.min.js"></script>
	<!--[if IE 8]>
	<script type="text/javascript" src="assets/component/html5shiv/3.7.3/html5shiv.min.js"></script>
	<script type="text/javascript" src="assets/component/respond/1.4.2/respond.min.js"></script>
	<script type="text/javascript" src="assets/component/respond/1.4.2/respond.matchmedia.addListener.min.js"></script>
	<script src="assets/component/bootstrap-ie8/1.1.0/bootstrap.ie8.amd.js"></script>
	<![endif]-->
	<!--[if lte IE 7]>
	<script src="assets/component/browser-support/1.1.0/browser.support.ie7.js"></script>
	<![endif]-->
	<sitemesh:write property='head' />
	<link type="image/x-icon" rel="shortcut icon" href="assets/business/anon/favicon.ico"/>	

</head>
<body><sitemesh:write property='body' /></body>
</html>

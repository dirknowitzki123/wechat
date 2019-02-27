<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("currentUser", com.by.core.util.SessionUtil.getCurrentASysUser()); %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title><sitemesh:write property='title' /></title>
	<sitemesh:write property='head' />
	<script type="text/javascript"> 
		function Protal( resc, vars ) {
			require( [ "jquery", "app/base", resc ], function( $, base, Global ) {
				$( function() {
					base.init( Global, vars );
					base.init();
				} );
			} );	
		};
		Protal( "frame/protal/1.1/protal.index", {} );
	</script>
</head>
<body class="container-fluid">
<div class="sections" style="display: none;padding: 0;" namespace="protal.index">
	<div class="main-header" id="mainHeader">
		<div class="col-md-4 head-left">
            <a class="head-logo" href="#"><img src="assets/business/frame/protal/1.1/images/logo.png" alt="logo"></a>
            <span class="col-md-7 name-url color-white">
                <span class="head-name">${ config[ "system.name" ] }</span><br/>
                <a class="white-a color-white" href="javascript:;">${ config[ "system.site" ] }</a>
            </span>
        </div>
        <div class="col-md-8 head-right" id="headerNavs">
            <div class="r-nav hide-nav" id="verticalHeaderNav">
                <a href="javascript:;" class="nav-tools-search"><i class="fa fa-search"></i><br/><span class="fun-text color-white">搜索</span></a>
                <a href="javascript:;" class="nav-tools-message"><i class="fa fa-phone"></i><br/><span class="fun-text color-white">留言</span></a>
                <a href="javascript:;" class="nav-tools-dialog"><i class="fa fa-comments"></i><br/><span class="fun-text color-white">对话框</span></a>
                <a href="javascript:;" class="nav-tools-mail"><i class="fa fa-envelope"></i><br/><span class="fun-text color-white">邮件</span></a>
                <a href="javascript:;" class="nav-tools-user"><i class="fa fa-user"></i><br/><span class="fun-text color-white">${currentUser.userName }</span></a>
                <a href="javascript:;" class="nav-tools-password"><i class="fa fa-keyboard-o"></i><br/><span class="fun-text color-white">修改密码</span></a>
                <a href="javascript:;" class="exit-sys nav-tools-logout"><i class="fa fa-sign-out"></i><br/><span class="fun-text color-white">退出系统</span></a>
            </div>
            <div id="barToggle" class="button-bar hidden-md hidden-lg">
                <div class="inner"><i class="fa fa-navicon"></i></div>
            </div>
            <div class="r-nav show-nav hidden-xs hidden-sm">
                <a href="javascript:;" class="nav-tools-search"><i class="fa fa-search"></i><br/><span class="fun-text color-white">搜索</span></a>
                <a href="javascript:;" class="nav-tools-message"><i class="fa fa-phone"></i><br/><span class="fun-text color-white">留言</span></a>
                <a href="javascript:;" class="nav-tools-dialog"><i class="fa fa-comments"></i><br/><span class="fun-text color-white">对话框</span></a>
                <a href="javascript:;" class="nav-tools-mail"><i class="fa fa-envelope"></i><br/><span class="fun-text color-white">邮件</span></a>
                <a href="javascript:;" class="nav-tools-user"><i class="fa fa-user"></i><br/><span class="fun-text color-white">${currentUser.userName }</span></a>
                <a href="javascript:;" class="nav-tools-password"><i class="fa fa-keyboard-o"></i><br/><span class="fun-text color-white">修改密码</span></a>
                <a href="javascript:;" class="exit-sys nav-tools-logout"><i class="fa fa-sign-out"></i><br/><span class="fun-text color-white">退出系统</span></a>
            </div>
            <p class="search-box" id="mainSerach">
            	<i class="search-sou fa fa-search"></i>
            	<input name="search" type="text" class="main-search"/><i class="search-del fa fa-times" title="取消搜索"></i>
           	</p>
        </div>
        <div class="clearfix"></div>
	</div>
	<div class="main-section" id="mainSection">
		<div class="main-navigation" id="mainNavigation">
			<div class="navigations-panel">
				<a class="navicon" href="javascript:;" id="toggle">
					<i class="icon fa fa-navicon"></i>
					<i class="dir fa fa-caret-left"></i>
				</a>
			</div>
			<ul class="navigations list-unstyled" id="navigations"></ul>
			<div class="main-subnavigations">
				<div id="subnavigations" class="subnavigations"></div>
			</div>
		</div>
		
		<div class="main-container sections" id="sections">
			<div class="nav-tabs-header" id="sectionTabsHeader" style="display:none;">
				<div class="nav-tabs-arrow nav-tabs-arrow-left"><a href="javascript:;"><i class="fa fa-angle-left"></i></a></div>
				<div class="nav-tabs-container">
					<ul class="list-unstyled list-inline section-tabs" id="sectionTabs"></ul>
				</div>
				<div class="nav-tabs-arrow nav-tabs-arrow-right"><a href="javascript:;"><i class="fa fa-angle-right"></i></a></div>
			</div>
			<div class="sections-wrapper" id="sectionsWrapper">
				<div class="section-loading" id="sectionLoading"><div><i class="fa fa-spinner fa-spin"></i> 加载中...</div></div>
			</div>
		</div>
	</div>
	<div class="sections-wrapper"></div>
</div>
</body>
</html>
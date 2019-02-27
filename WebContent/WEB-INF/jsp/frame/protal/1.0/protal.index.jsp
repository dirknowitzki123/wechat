<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("currentUser", com.by.core.util.SessionUtil.getCurrentASysUser()); %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title><sitemesh:write property='title' /> ${sysName} </title>
	<sitemesh:write property='head' />
	<script type="text/javascript"> require( [ "business/frame/protal/1.0/protal.index" ] );</script>
</head>
<body>
	<section class="protal" style="display: none;" id="PROTAL">
		<div id="protalLeftPanel" class="protal-left-panel">
			<div class="protal-logo-panel">
				 <h1><img src="assets/business/frame/protal/1.0/images/logo.png" />${sysName}</h1>
			</div>
			<div class="protal-inner-panel" id="protalInnerPanel">
				<div class="user-panel">
					<p>姓名：${currentUser.userName }</p>
					<p>机构：${currentUser.orgName }</p>
				</div>
				<ul class="nav nav-stacked nav-bracket" id="navMenus"></ul>
			</div>
		</div>
		<div id="protalrightPanel" class="protal-right-panel">
			<div class="protal-header-panel">
				<a class="menu-toggle" id="menuToggle"><i class="fa fa-bars"></i></a>
				
				<div class="tool-panel">
					<ul class="list-unstyle list-inline pull-right">
						<!-- <li>
							<div class="btn-group">
								<button class="btn btn-default dropdown-toggle top-icon" data-toggle="dropdown">
				                	<i class="glyphicon glyphicon-user"></i>
				                	<span class="badge">2</span>
				              	</button>
							</div>
						</li>
						<li>
							<div class="btn-group">
								<button class="btn btn-default dropdown-toggle top-icon" data-toggle="dropdown">
				                	<i class="glyphicon glyphicon-envelope"></i>
				                	<span class="badge">3</span>
				              	</button>
							</div>
						</li>
						<li>
							<div class="btn-group">
								<button class="btn btn-default dropdown-toggle top-icon" data-toggle="dropdown">
				                	<i class="glyphicon glyphicon-globe"></i>
				                	<span class="badge">5</span>
				              	</button>
							</div>
						</li> -->
						<li>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<i class="fa fa-user fa-lg"></i> ${currentUser.userName }
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu dropdown-menu-usermenu pull-right">
									<!-- <li><a href="profile.html">
										<i class="glyphicon glyphicon-user"></i> 个人中心</a></li>
									<li><a href="#">
										<i class="glyphicon glyphicon-cog"></i> 账户设置</a></li>
									<li><a href="#">
										<i class="glyphicon glyphicon-question-sign"></i> 帮助文档</a></li> -->
									<li><a href="javascript:;" id="protalModifyPassword">
										<i class="fa fa-edit fa-lg"></i> 修改密码</a></li>
									<li><a href="javascript:;" id="protalLogout">
										<i class="fa fa-sign-out fa-lg"></i> 用户注销</a></li>
								</ul>
							</div>
						</li>
						<li>
							<div class="btn-group">
								<button class="btn btn-default dropdown-toggle top-icon" id="protalWelcome">
				                	<i class="fa fa-home fa-lg"></i>
				              	</button>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="protal-nav-panel" >
				<ul class="list-unstyled list-inline" id="protalNavPanel"></ul>
			</div>
			<div class="protal-page-panel">
				<div class="sections" id="sections">
					<div class="nav-tabs-header" id="sectionTabsHeader">
						<div class="nav-tabs-arrow nav-tabs-arrow-left">
							<a href="javascript:;"><i class="fa fa-arrow-left fa-lg"></i></a>
						</div>
						<div class="nav-tabs-content">
							<ul class="nav nav-tabs" id="sectionTabs"></ul>
						</div>
						<div class="nav-tabs-arrow nav-tabs-arrow-right">
							<a href="javascript:;"><i class="fa fa-arrow-right fa-lg"></i></a>
						</div>
					</div>
					<div class="sections-wrapper" id="sectionsWrapper">
						<!-- <div class="section" id="protalSection">
							<div class="section-container">
								<sitemesh:write property='body' />
							</div>
							<div class="sections-wrapper"></div>
						</div> -->
						<div class="section-loading" id="sectionLoading">
							<div><i class="fa fa-spinner fa-spin"></i> 加载中...</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</section>
</body>
</html>
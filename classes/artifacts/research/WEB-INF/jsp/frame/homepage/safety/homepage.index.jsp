<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>首页</title>
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
${param.PAGEID}.section .section-container #roleGrid thead,
${param.PAGEID}.section .section-container #roleGrid2 thead { display:none;}
${param.PAGEID}.section .section-container .page-header a { font-size: 12px; }
</style>
<script type="text/javascript">
App( "business/frame/homepage/safety/homepage.index", {} );
</script>
</head>
<body>
	<div class="container-fluid">
	    <div class="row row0" style="margin-top: -10px;">
	        <table width="100%">
	        	<tr>
	           		<td width="49.5%">
	           			<div class="page-header">
		           			<span>待办任务</span>
		           			<div class="pull-right">
		           				<a id="dbrw" class="todo-more" href="javascript:;"><i class="fa fa-angle-double-right"></i> 更多</a>
	           				</div>
	           			</div>
           			</td>
           			<td width="1%">&nbsp;</td>
	           		<td width="49.5%">
	           			<div class="page-header">
		           			<span>系统公告</span>
		           			<div class="pull-right">
		           				<a id="xtgg" class="todo-more" href="javascript:;"><i class="fa fa-angle-double-right"></i> 更多</a>
	           				</div>
           				</div>
	           		</td>
           		</tr>
	        </table>
		</div>
		<div class="row row0">
			<table id="roleGrid" ></table>
			<table id="roleGrid2" ></table>
		</div>
	</div>
</body>
</html>
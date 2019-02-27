<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>微信用户管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript"> 
	App("business/manage/wechat/custBaseInfo/custBaseInfo.index", {demo: 1} );
</script>
</head>

<body>
	<div class="container-fluid">
		<form >
			<div class="grid-query">
				<input type="text" class="input" name="phoneNo" options='{txtPrefix: "手机号"}' />
				<input type="text" class="input" name="custName" options='{txtPrefix: "姓名"}' />
				<input type="text" class="input" name="referralCode" options='{txtPrefix: "推荐码"}' />
			</div>
		</form>
		<table id="custGrid">
			<caption>
				<!-- <button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i>新增</button> -->
				<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i>编辑</button>
				<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i>注销</button>
				<button type="button" class="btn btn-default" id="enableBtn"><i class="fa fa-plus"></i>启用</button>
				<button type="button" class="btn btn-default" id="exportExcelBtn"><i class="fa fa-download"></i> 导出</button>
				
			</caption>
		</table>
	</div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>组织机构管理</title>
<meta charset="utf-8">
<style type="text/css">
${param.PAGEID}.section {}
${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript"> 
	App("business/system/asysorg/asysorg.index", {} );
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="">
			<button type="button" class="btn btn-default" id="addBtn"><i class="fa fa-plus"></i> 新增</button>
			<button type="button" class="btn btn-default" id="editBtn"><i class="fa fa-edit"></i> 编辑</button>
			<button type="button" class="btn btn-default" id="delBtn"><i class="fa fa-remove"></i> 删除</button>
		</div>
		<div class="col-xs-5">
			<ul class="ztree"></ul>
		</div>
		<div class="col-xs-7">
			<form class="form-horizontal">
				<div class="form-group">
				    <label class="col-xs-2 control-label">编号</label>
				    <div class="col-xs-10">
				      <p class="form-control-static"></p>
				    </div>
		  		</div>
		  		<div class="form-group">
				    <label class="col-xs-2 control-label">名称</label>
				    <div class="col-xs-10">
				      <p class="form-control-static"></p>
				    </div>
		  		</div>
		  		<div class="form-group">
				    <label class="col-xs-2 control-label">电话</label>
				    <div class="col-xs-10">
				      <p class="form-control-static"></p>
				    </div>
		  		</div>
		  		<div class="form-group">
				    <label class="col-xs-2 control-label">类型</label>
				    <div class="col-xs-10">
				      <p class="form-control-static"></p>
				    </div>
		  		</div>
		  		<div class="form-group">
				    <label class="col-xs-2 control-label">级别</label>
				    <div class="col-xs-10">
				      <p class="form-control-static"></p>
				    </div>
		  		</div>
		  		<div class="form-group">
				    <label class="col-xs-2 control-label">地区</label>
				    <div class="col-xs-10">
				      <p class="form-control-static"></p>
				    </div>
		  		</div>
		  		<div class="form-group">
				    <label class="col-xs-2 control-label">启用</label>
				    <div class="col-xs-10">
				      <p class="form-control-static"></p>
				    </div>
		  		</div>
		  		<div class="form-group">
				    <label class="col-xs-2 control-label">地址</label>
				    <div class="col-xs-10">
				      <p class="form-control-static"></p>
				    </div>
		  		</div>
		  		<div class="form-group">
				    <label class="col-xs-2 control-label">备注</label>
				    <div class="col-xs-10">
				      <p class="form-control-static"></p>
				    </div>
		  		</div>
	  		</form>
		</div>
	</div>
</body>
</html>
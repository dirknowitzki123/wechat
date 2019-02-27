<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>文档管理 | 查看</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container {}
</style>
<script type="text/javascript">
	App( "business/pub/asysattext/asysattextview.form", { } );
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="" id="attExtForm">
			<input type="hidden" name="id"/>
			<input type="hidden" name="attIsDir" value="false"/>
			<input type="hidden" name="attSort" value="1"/>
			<div class="row">
				<input type="text" class="input" name="attName" options='{txtLabel: "文档标题"}' readonly="readonly"/>
				<input type="text" class="select" name="attTyp" options='{txtLabel: "文档类型"}' readonly="readonly"/>
				<input type="text" class="input" name="instUserName" options='{txtLabel: "上传用户姓名"}' readonly="readonly" />
				<input type="text" class="input" name="instDate" options='{txtLabel: "上传时间"}' readonly="readonly" />
				<textarea type="text" class="input" name="attDesc" options='{txtLabel: "文档描述", cssCol: "col-xs-12"}' readonly="readonly"></textarea>
				<textarea type="text" class="input" name="attRemark" options='{txtLabel: "备注信息", cssCol: "col-xs-12"}' readonly="readonly" ></textarea>
				<h3 class="text-center"><a id="download" href="javascript:;">下载文档<i class="fa fa-download"></i></a></h3>
			</div>
		</form>
		<div class="row row0 text-center">
			<button type="button" class="btn btn-danger btn-md btn-embossed" id="closeBtn"><i class="fa fa-remove"></i> 关闭</button>
		</div>
	</div>
</body>
</html>
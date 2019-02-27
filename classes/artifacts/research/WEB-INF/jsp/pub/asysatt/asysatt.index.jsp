<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>附件上传</title>
<style type="text/css">
	${param.PAGEID}.section {}
	${param.PAGEID}.section .section-container > .container-fluid { border: 1px solid #ddd; border-radius: 2px 2px;}
	${param.PAGEID}.section .section-container .files { display: table-cell; padding: 10px 10px; margin-top: 15px; margin-bottom: "15px";}
	${param.PAGEID}.section .section-container .files:before,
	${param.PAGEID}.section .section-container .files:after { clear: both; content: "" }
	
</style>
<script type="text/javascript">
 	App( "business/pub/asysatt/asysatt.index", {} );
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="files" id="files">
			<div class="internal-files" id="internalFiles">
				<i class="fa fa-search"></i><span> 没有查询出相关的附件信息...</span>
			</div>
		</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="section-header">
	<sitemesh:write property='head' />
</div>
<div class="sections-wrapper"></div>
<div class="section-container">
	<sitemesh:write property='body' />
	<div class="section-loading2">
		<div class="section-loadding-spiner">
			<i class="fa fa-spinner fa-spin"></i> <span>数据处理中...</span>
		</div>
	</div>
</div>
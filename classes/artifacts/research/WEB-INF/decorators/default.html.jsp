<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title><sitemesh:write property='title' />${ config[ "system.name" ] }</title>
<script type="text/javascript">
( function() {
	var  __args__ = []
		, __loaded_resc__ = false
		, __loaded_html__ = false;
	
	App = function() {
		__args__.push( arguments );
		
		if ( __loaded_resc__ ) return;
		
		require( ["business/frame/core/core.index"], function() {
			__loaded_resc__ = true;
			__loaded__();
		} );
	};
	
	function __loaded__() {
		
		if ( !__loaded_html__ || !__loaded_resc__ ) return;
		
		for ( var index = 0; index < __args__.length; index++ ) {
			App.apply( window, __args__[ index ] );
		}
		__args__ = [];
		var base = require( "app/base" );
		base.__apps__ = [];
		
		var $ = require( "jquery" );
		$( "body" ).css( "overflow", "auto" );
		
	};
	
	window.onload = function() {
		__loaded_html__ = true;
		__loaded__();
	};
})();
</script>
<sitemesh:write property='head' />
</head>
<body>
	<div class="section">
		<div class="section-headr"></div>
		<div class="sections-wrapper"></div>
		<div class="section-container">
			<div class="section-loading2">
				<div class="section-loadding-spiner">
					<i class="fa fa-spinner fa-spin"></i> <span>数据处理中...</span>
				</div>
			</div>
			<sitemesh:write property='body' />
		</div>
	</div>
</body>
</html>
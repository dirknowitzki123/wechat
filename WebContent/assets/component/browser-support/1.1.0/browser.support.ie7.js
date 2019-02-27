( function (){ 
	require( [ "jquery" ], function( $ ) { 
		var $body = $( "body" );
		
		var $lock = $();
		function lock() {
			if ( $lock.length != 0 ) return;
			$lock = $( "<div class=\"lock\"></div>" ).appendTo( "body" );
			$lock.css( {
				position: "absolute",
				top: 0,
				left: 0,
				width: "100%",
				height: "100%",
				"z-index": 99999,
				background: "#fff"
			} );
			$lock.append( "<h1 class=\"text-center\">系统不支持IE7及IE7以下的版本</h1>" );
			$lock.append( "<h3 class=\"text-center\">请使用火狐、谷歌、IE9以上的浏览器访问</h3>" );
			$lock.append( "<h5 class=\"text-center\">IE8、IE9浏览器效果支持不友好</h5>" );
			
			$body.css( "display", "block!important" );
		}
		
		if ( $body.length == 0 ) {
			$( function() {
				lock();
			} );
		} else {
			lock();
		}
		
		
	} );
} )();
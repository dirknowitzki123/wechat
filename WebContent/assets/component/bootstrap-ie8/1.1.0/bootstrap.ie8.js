( function( $ ){
	if ( !Array.prototype.indexOf ) {
	  Array.prototype.indexOf = function( elt ) {
	    var len = this.length >>> 0;

	    var from = Number( arguments[ 1 ] ) || 0;
	    from = ( from < 0 ) ? Math.ceil( from ) : Math.floor( from );
	    if ( from < 0 ) {
	    	from += len;
	    }

	    for ( ; from < len; from++ ) {
	      if ( from in this && this[ from ] === elt ) {
	    	  return from;
	      }
	    }
	    return -1;
	  };
	}
	
	var $body = $( "body" );
	
	function respond() {
		if ( $body.length == 0 ) {
			$body = $( "body" )
		}
		var width =  $( window ).width();
		if ( width >= 1200 ) {
			$body.removeClass( "respond-xs respond-sm respond-md" ).addClass( "respond-lg" );
			console.log( $body.attr( "class" ) );
		} else if ( width >= 992 ) {
			$body.removeClass( "respond-xs respond-sm respond-lg" ).addClass( "respond-md" );
		} else if ( width >= 768 ) {
			$body.removeClass( "respond-xs respond-md respond-lg" ).addClass( "respond-sm" );
		} else {
			$body.removeClass( "respond-md-sm respond-md respond-lg" ).addClass( "respond-xs" );
		}
	}
	
	$( window ).bind( "resize", function( event ) {
		respond();
	} );
	
	$( function() {
		respond();
	} );
	
	if ( $body.length != 0 ) {
		respond();
	}
} )( jQuery );
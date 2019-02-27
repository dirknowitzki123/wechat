//###################################
/**
 * form 表单
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ 
     "app/base",
     "app/ui/input",
     "app/ui/select",
     "app/ui/select.tree",
     "app/ui/select.group",
     "app/ui/select.area",
     "app/ui/select.area.tab",
     "app/ui/file.upload",
     "app/extend/validate.extend"
], function() {
	
	require( [ "css2!app/ui/css/form" ] );
	
	var $ = require( "jquery" )
		, app = require( "app/base" )
		, format = app.tools.format
		, uuid = app.tools.uuid
		, log = app.log;
	
	$.fn.valChange = function( value ) {
		return $( this ).each( function() {
			if ( $( this ).hasClass( "has-app-change" ) ) {
				$( this ).trigger( "change2.app.ui", [ value ] );
				return true;
			}
			if ( typeof value == "string" || typeof value == "number" ) {
				$( this ).val( value );
			}
		} );
	};
	
	$.fn.disabled = function( bool ) {
		if ( bool !== false ) bool = true;
		return $( this ).each( function() {
			if ( bool ) {
				$( this ).addClass( "disabled" ).attr( "disabled", "disabled" );
			} else {
				$( this ).removeClass( "disabled" ).removeAttr( "disabled" );
			}
			$( this ).trigger( "disabled.app.ui" );
		} );
	};
	
	$.fn.readonly = function( bool ) {
		if ( bool !== false ) bool = true;
		return $( this ).each( function() {
			if ( bool ) {
				$( this ).addClass( "readonly" ).attr( "readonly", "readonly" );
			} else {
				$( this ).removeClass( "readonly" ).removeAttr( "readonly" );
			}
			$( this ).trigger( "readonly.app.ui" );
		} );
	};
	
	$( document ).delegate( ".page-header.toggle", "click", function( event ) {
		var p, n, s, c;
		p = $( this );
		n = p.is( ".next" );
		
		c = p.attr( "data-next" );
		if ( typeof c != "undefined" ) c = p.parent().find( c );
		else s = n ? p.next() : p.find( "> div" );
		
		s.slideToggle( function( event ) {
			p.toggleClass( "open" );
		} );
		
		
		
		return false;
	} );
	
} );
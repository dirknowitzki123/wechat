( function( $ ) {
	$( function() {
		main();
	} );
	
	function main() {
		var canvas = $( "canvas" ).get(0);
		var context=canvas.getContext("2d");
		context.beginPath();
		context.arc(700,400,200,0,Math.PI*2,true);
		context.closePath();
		
	};
	
} )( jQuery );
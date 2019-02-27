//###################################
/**
 * placeholder 提示
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/ui/cursor.position" ], function(){
	var $ = require( "jquery" );
	$.fn.placeholder = function(options, callback) {
	    return this.each( function() {
	    	new Placeholder(this, options);
	        if ( $.isFunction( callback ) ) callback( $( this ) );
	    } );
	};
	
	function Placeholder( target, options ) {
		this.settings = $.extend( true, {}, this.settings, options );
		this.$that = $( target );
		this.__init__();
	}
	
	
	Placeholder.prototype.settings = {
		word: '',
        color: '#ccc',
        evtType: 'focus'
	};
	
	Placeholder.prototype.__init__ = function() {
		var settings  = this.settings
			, $that = this.$that;
		 // some alias
        var word    = settings.word;
        var color   = settings.color;
        var evtType = settings.evtType;
 
        // default
        var defColor = $that.css( 'color' );
        var defVal   = $that.val();
 
        if ( defVal == '' || defVal == word ) {
            $that.css( { color: color } ).val( word );
        } else {
            $that.css( { color: defColor } );
        }
 
        function switchStatus(isDef) {
            if ( isDef ) {
                $that.val( '' ).css( { color: defColor } );   
            } else {
                $that.val( word ).css( { color: color } );
            }
        }
        function asFocus() {
            $that.bind( evtType, function() {
                var txt = $that.val();
                if ( txt == word ) {
                    switchStatus( true );
                }
            } ).bind( 'blur', function() {
                var txt = $that.val();
                if ( txt == '' ) {
                    switchStatus( false );
                }
            });
        }
        function asKeydown() {
            $that.bind('focus', function() {
                var elem = $that[ 0 ];
                var val  = $that.val();
                if ( val == word ) {
                    setTimeout( function() {
                        // 光标定位到首位
                        $that.setCursorPosition( { index: 0 } );
                    }, 10 );                 
                }
            });
        }
 
        if ( evtType == 'focus' ) {
            asFocus();
        } else if ( evtType == 'keydown' ) {
            asKeydown();
        }
 
        // keydown事件里处理placeholder
        $that.keydown( function() {
            var val = $that.val();
            if (val == word) {
                switchStatus( true );
            }
        } ).keyup( function() {
            var val = $that.val();
            if ( val == '') {
                switchStatus( false );
                $that.setCursorPosition( { index: 0 } );
            }
        } );
	};
	
	 function bootstrap( $that ) {
       
    };
} ); 
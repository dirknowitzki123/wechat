//###################################
/**
 * 获取坐标 提示
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "jquery" ], function() {
	var $ = require( "jquery" );
	
	/*
	 * 设置输入域(input/textarea)光标的位置
	 * @param {HTMLInputElement/HTMLTextAreaElement} elem
	 * @param {Number} index
	 */
	function setCursorPosition( elem, index ) {
	    var val = elem.value;
	    var len = val.length;
	 
	    // 超过文本长度直接返回
	    if ( len < index ) return;
	    setTimeout( function() {
	        elem.focus();
	        if ( elem.setSelectionRange ) { // 标准浏览器
	            elem.setSelectionRange( index, index );
	        } else { // IE9-
	            var range = elem.createTextRange();
	            range.moveStart( "character", -len );
	            range.moveEnd( "character", -len );
	            range.moveStart( "character", index );
	            range.moveEnd( "character", 0 );
	            range.select();
	        }
	    }, 10 );
	};
	
	
	$.fn.setCursorPosition = function( options ) {
	    var settings = $.extend({
	        index: 0
	    }, options );
	    return this.each(function() {
	        // 非input和textarea直接返回
	        var $elem = $( this );
	        if (!$elem.is('input,textarea')) return;
	        setCursorPosition( this, settings.index );
	    });
	};
} );
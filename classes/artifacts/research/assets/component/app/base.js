//###################################
/**
 * base 基础 命名空间
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "jquery" ], function () {
	var $ = jQuery = require( "jquery" );
	
	function Tools() {}
	Tools.prototype.format = format = function() {
		var tmpl = arguments[0] || "";
        if (tmpl instanceof Array) tmpl = tmpl.join('');
        if (arguments.length == 2 && typeof(arguments[1]) != "array") {
            var dta = arguments[1];
            var format = { };
            return tmpl.replace(/{(\w+)}/g, function(m1, m2) {
                if (!m2)return "";
                return (format && format[m2]) ? format[m2](dta[m2]) || '' : typeof dta[m2] == 'undefined' || dta[m2] == null ? '' : dta[m2];
            });
        } else {
            var args = arguments[1];
            return tmpl.replace(/{(\d+)}/g, function(match, number) {
                return typeof args[number + 1] != 'undefined' ? args[number + 1] || '' : match || '';
            });
        }
	};
	
	Tools.prototype.uuid = uuid = function( len, radix ) {
		if ( !len ) len = 16;
		if (!radix ) radix = len * 2;
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;

        if (len) {
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
        } else {
            var r;
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random()*16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
	};
	
	 /**获取参数*/
    Tools.prototype.params = params = function() {
		var vars = [], hash;
		var hashes = window.location.href.slice( window.location.href.indexOf( '?' ) + 1 ).split( '&' );
		for ( var i = 0; i < hashes.length; i++ ) {
			hash = hashes[ i ].split( '=' );
			vars.push( hash[ 0 ] );
			vars[ hash[ 0 ] ] = hash[ 1 ];
		}
		return vars;
	};

	/**获取参数明细*/
	Tools.prototype.param = param = function( name ) {
		return params()[ name ];
	};

	/**树形数据转换**/
	Tools.prototype.toTreeJson = toTreeJson = function ( data, options ) {
		var app = this;
		if ( typeof data == 'undefined' ) return [];
		if ( !$.isArray( data ) ) data = [ data ];

		var opts = $.extend( true, arguments.callee.defaults, options );
		
		
		var a = data, r = [], hash = {}, id = opts.id, pid = opts.pid, children = opts.children, i = 0, j = 0, len = a.length;    
	    for(; i < len; i++){    
	        hash[a[i][id]] = a[i];    
	    }    
	    for(; j < len; j++){    
	        var aVal = a[j], hashVP = hash[aVal[pid]];    
	        if(hashVP){    
	            !hashVP[children] && (hashVP[children] = []);    
	            hashVP[children].push(aVal);    
	        }else{    
	            r.push(aVal);    
	        }    
	    }    
		return r;
	};
	toTreeJson.defaults = {
		id: 'id',
		pid: 'pId',
		children: 'children'
	}; 
	
	function Log() {}
	Log.prototype.info = info = function() {
		if ( window.console.log ) window.console.log( arguments[ 0 ] );
		else if ( window.console.info ) window.console.info( arguments[ 0 ] );
		else if ( window.console.debug ) window.console.debug( arguments[ 0 ] );
	};
	Log.prototype.warn = warn = function() {
		if ( window.console ) {
			if ( window.console.warn ) window.console.warn( arguments[ 0 ] );
			else if ( window.console.log ) window.console.log( arguments[ 0 ] );
			else if ( window.console.info ) window.console.info( arguments[ 0 ] );
			else if ( window.console.debug ) window.console.debug( arguments[ 0 ] );
		}
	};
	Log.prototype.error = error = function() {
		throw new Error( arguments[ 0 ] );
	};
	
	function Message() {}
	Message.prototype.success 
		= Message.prototype.info
		= Message.prototype.warn
		= Message.prototype.error 
		= function() {
			alert( arguments[ 0 ] );
		};
	
	/**
	 * 应用页面组件对象
	 */
	function AppObject() {
		this.__apps__ = [];
	};
	
	function App() {}
	
	App.prototype.tools = new Tools();
	App.prototype.log = new Log();
	App.prototype.message = new Message();
	App.prototype.AppObject = AppObject; 
	return new App();
} );
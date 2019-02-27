define( [ 
  "bootstrap",
  "app/core",
  "app/ui/code",
  "app/ui/grid",
  "app/ui/form",
  "bootstrap.datetimepicker",
  "toastr"
], function () {
	"use strict";

	require( [ "css2!business/frame/core/core.index.css" ] );
	require( [ "bootstrap.datetimepicker" ], function() {
		var $ = jQuery = require( "jquery" );
		$.extend(true, $.fn.datetimepicker.defaults, {
			debug: true,
			widgetPositioning: {
	            horizontal: 'auto',
	            vertical: 'bottom'
	        },
			locale: "zh_cn",
			format: "YYYY-MM-DD",
			showTodayButton: true,
			showClear: true,
	        showClose: true
		} );
	} );
	//require( [ "app/ui/grid" ] );
	//require( [ "app/ui/form" ] );
	
	var $ = jQuery = require( "jquery" )
	, base = require( "app/base" )
	, tools = base.tools
	, log = base.log
	, Log = log.constructor
	, message = base.message
	, Message = message.constructor
	, page = require( "app/ui/page" )
	, App = base.constructor
	, format = tools.format
	, toTreeJson = tools.toTreeJson;
	
	var toastr = require( "toastr" );
	toastr.options.newestOnTop = false;
	//toastr.options.extendedTimeOut = 1000 * 60 * 10;
	toastr.options.positionClass = "toast-bottom-full-width";
	toastr.options.closeButton = true;
	toastr.options.closeHtml = '<button><i class="fa fa-power-off"></i></button>';
	toastr.options.showMethod = 'slideDown';
	toastr.options.hideMethod = 'slideUp';
	toastr.options.closeMethod = 'slideUp';
	toastr.options.hideDuration = 300;
	
	Message.prototype.error = function() { 
		toastr.error.apply( toastr, arguments );
	};
	Message.prototype.info = toastr.info;
	Message.prototype.warning = toastr.warning;
	Message.prototype.success = toastr.success;
	
	requirejs.onError = function ( err ) {
	    message.error( "JSApp异常，请联系管理员。");
	    console.log( arguments );
	    throw err;
	};
	
	base.code.getCacheByCallBack( "area.code.json", function( code ) {
		code.__default__ = toTreeJson( code.__default__, {
			id: 'valCode',
			pid: 'pValCode',
		} );
	} );
	
	
	
	window.App = function() { 
		var base = require( "app/base" );
		if ( arguments.length == 2 && typeof arguments[0] == "string" && typeof arguments[1] == "object" ) {
			var namespace = arguments[ 0 ]
				, vars = arguments[ 1 ]
				, moduleName;
			if ( namespace.indexOf( "/" )  == -1 ) {
				moduleName = namespace.split( "." ).join( "/" );
			} else {
				moduleName = namespace;
				namespace = namespace.split( "/" ).join( "." );
			}
			
			var global = {
				namespace: namespace,
				vars: vars,
				init: function() {},
			};
			base.init( global );
			require( [ moduleName ], function() {
				var base = require( "app/base" )
					, Global = require( moduleName );
				delete global.vars;
				delete global.init;
				
				if ( !global.selector || global.selector.length == 0 ) {
					global.register();
				}
				global = $.extend( true, new Global( vars ), global );
				base.push( global );
				
				global.init();
				
			} );
			return;
		}
		
		base.init.apply( app, arguments );
	};
	
	
	$.ajaxSetup({
		type: 'GET',
		dataType: 'json',
		cache: false,
		statusCode: {
			404: function (xhr) {
				message.error( "请求路径不存在404。" );
			},
			500: function (xhr) {
				var data = xhr.responseJSON;
				if ( typeof data == "undefined" ) {
					data = xhr.responseText;
					if ( typeof data == "string" && data.length != 0 ) {
						data = ( new Function( "return " + data ) )();
					}
				}
				
				if ( typeof data == "object" && typeof data.msg == "string" && data.msg.length != 0 ) {
					data = data.msg + "&nbsp;&nbsp;&nbsp;&nbsp;错误码:" + data.code;
				}
				message.error(data||"系统异常");
			},
			510: function(xhr) {
				var data = xhr.responseJSON;
				if ( typeof data == "undefined" ) {
					data = xhr.responseText;
					if ( typeof data == "string" && data.length != 0 ) {
						data = ( new Function( "return " + data ) )();
					}
				}
				
				if ( typeof data == "object" && typeof data.msg == "string" && data.msg.length != 0 ) {
					data = data.msg;
				}
				$( document ).trigger( "request.ajax.error.510", [ data ] );
			},
			520: function(xhr) {
				message.error(xhr.responseJSON.msg || "业务异常" + "&nbsp;&nbsp;&nbsp;&nbsp;错误码:" +xhr.responseJSON.code);
			}
		}
	});
	
	
});
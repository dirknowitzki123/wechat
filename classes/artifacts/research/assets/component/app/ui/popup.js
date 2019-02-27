//###################################
/**
 * popup 弹出页
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base" ], function() {
	"use strict";
	
	require( [ "css2!app/ui/css/popup" ] );
	
	var $ = jQuery = require( "jquery" )
		, base = require( "app/base" )
		, log = base.log
		, die = base.die
		, format = base.tools.format
		, uuid = base.tools.uuid
		, AppObject = base.AppObject;
	
	function Popup() {}
	
	Popup.prototype.__DEFAULT__ = {
		__template__: [
           '<div id="{id}" class="{id} section active {css} section-popup section-popup-theme-{theme} section-popup-dir-{direction} animate" style="{style}">',
           '	<div class="section-popup-mask"></div>',
           '	<div class="section-popup-container" style="{popupStyle};display:none;">',
           '	<div class="section-popup-toggle"><p><i class="fa fa-angle-right"></i><span>点击已收回</span><i class="fa fa-angle-right"></i></p></div>',
           '		<div class="popup-body row" ></div>',
           '	</div>',
           '</div>'
        ],
		id: "",
		css: "",
		style: "",
		title: "",
		direction: "right",
		offset: 90, 
		/*speed: 450,*/
		speed: 0,
		step: 1.194,
		theme: "default",
		icon: "fa fa-tasks",
		content: "#sectionsWrapper",
		params: {},
		__data__: {},
		events: {
			shown:  function ( apps ) {},
			hiden: function ( closed, data ) {}
		}
	};
	
	Popup.prototype.open = function( options, openHandler, closeHandler, errorHandler ) {
		var popup = $.extend( true, {}, this.__DEFAULT__, options );
		if ( typeof popup.id == "undefined" || popup.id == "" ) popup.id = "popup" + uuid();
		
		if ( typeof popup.direction != "string" || $.inArray( popup.direction, [ "top", "right", "bottom", "left" ] ) == -1 ) {
			popup.direction = "right";
		} 
		
		var popupStyle = "";
		if ( typeof popup.offset == "number") {
			if ( popup.offset > 100 ) {
				popup.offset = 100;
			} else if ( popup.offset < 0 ) {
				popup.offset = 0;
			} 
			
			if ( popup.direction == "right" || popup.direction == "left" ) {
				popupStyle += format( "width: {0}%;", [ popup.offset ] );
			} else {
				popupStyle += format( "height: {0}%;", [ popup.offset ] );
			}
			//popupStyle += format( "{1}: -{0}%;", [ popup.offset, popup.direction ]  );
		}
		
		
		popup.popupStyle = popupStyle;
		
		popup.__close_handler__ = closeHandler;
		
		var $popup = $( format( popup.__template__, popup ) ).appendTo( popup.content );
		popup.url += ( popup.url.indexOf( "?" ) != -1 ? "&" : "?" ) + "PAGEID=." + popup.id;
		
		
		var $popupContainer = popup.popupContainer = $popup.find( ".section-popup-container:eq(0)" );
		
		var appObject = new AppObject();
		
		$.ajax( {
			url: popup.url,
			dataType: "html",
			success: function( html ) {
				$popup.find( ".popup-body" ).html( html );
				if ( typeof popup.title == "string" && popup.title.length > 0 ) {
					$popup.find( ".section-container" ).prepend( format( '<h6 class="page-header"><i class="{icon}"></i> {title}</h6>', popup ) );
				}
				
				var apps = base.init( true );
				$.each( apps, function( index, app ) {
					app.params = popup.params;
					app.__data__ = popup.__data__;
					app.content = $popup;
					app.section = $popup;
					app.__popup__ = popup;
					appObject.__apps__.push( app.__namespace__ );
				} );
				
				if ( typeof openHandler == "function" ) openHandler( apps );
				var apps = base.init();
				popup.events.shown( apps );
				
				var w = $popup.width();
				w = w * popup.offset / 100;
				w = Math.floor( w ) - 25;
				
				if ( typeof popup.speed != "number" || popup.speed <= 0 ) {
					popup.speed = Math.floor( w / popup.step );
				}
				
				var config = {};
				config[ popup.direction ] = "-100%";
				
				$popupContainer.css( config ).show();
				
				config[ popup.direction ] = 0;
				
				$popupContainer.animate( config, popup.speed, function() {
					$popup.removeClass( "animate" );
					$popup.css( "width", "100%" );
				} );
			},
			error: function() {
				$page.remove();
				if ( typeof error == "function" ) {
					error.call( page );
				}
			}
		} );
		
		
		return appObject;
		
	};
	
	
	Popup.prototype.close = function( app, closed, data ) {
		if ( typeof app != "object" || typeof app.__popup__ != "object" ) log.error( "JS: 非JsApp对象" );
		
		var popup = app.__popup__;
		var config = {};
		config[ popup.direction ] = "-100%";
		
		app.content.addClass( "animate" );
		
		popup.popupContainer.animate( config, popup.speed, function() {
			popup.content.removeClass( "animate" );
			if ( typeof popup.__close_handler__ == "function" ) {
				popup.__close_handler__( app );
			}
			popup.events.hiden( closed, data );
		} );
		
	};
	
	return new Popup();
	
} );
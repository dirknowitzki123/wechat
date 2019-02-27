//###################################
/**
 * page 子页
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base" ], function() {
	"use strict";
	
	var $ = jQuery = require( "jquery" )
		, base = require( "app/base" )
		, log = base.log
		, die = base.die
		, format = base.tools.format
		, uuid = base.tools.uuid
		, AppObject = base.AppObject;
	
	function Page() {}
	
	Page.prototype.__DEFAULT__ = {
		__template__: [
           '<div id="{id}" class="section active {css} {id}" style="{style}">',
           '</div>'
        ],
		id: "",
		css: "",
		style: "",
		title: "",
		icon: "fa fa-tasks",
		content: "#sectionsWrapper",
		params: {},
		__data__: {},
		events: {
			shown:  function ( apps ) {},
			hiden: function ( closed, data ) {}
		}
	};
	
	Page.prototype.open = function( options, before, error ) {
		var page = $.extend( true, {}, this.__DEFAULT__, options );
		if ( typeof page.id == "undefined" || page.id == "" ) page.id = "page" + uuid();
		var $page = $( format( page.__template__, page ) ).appendTo( page.content );
		page.url += ( page.url.indexOf( "?" ) != -1 ? "&" : "?" ) + "PAGEID=." + page.id;
		
		var appObject = new AppObject();
		
		$.ajax( {
			url: page.url,
			dataType: "html",
			success: function( html ) {
				$page.html( html );
				if ( typeof page.title == "string" && page.title.length > 0 ) {
					$page.find( ".section-container" ).prepend( format( '<h6 class="page-header"><i class="{icon}"></i> {title}</h6>', page ) );
				}
				
				var apps = base.init( true );
				$.each( apps, function( index, app ) {
					app.params = page.params;
					app.__data__ = page.__data__;
					app.content = $page;
					app.section = $page;
					app.__page__ = page;
					appObject.__apps__.push( app.__namespace__ );
				} );
				
				if ( typeof before == "function" ) before( apps );
				var apps = base.init();
				//if ( $.isArray( apps ) && apps.length == 1 ) apps = apps[ 0 ];
				page.events.shown( apps );
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
	
	Page.prototype.close = function( app, closed, data ) {
		if ( typeof app != "object" || typeof app.__page__ != "object" ) log.error( "JS: 非JsApp对象" );
		app.__page__.events.hiden( closed, data );
	};
	
	return new Page();
	
} );
//###################################
/**
 * modal 窗口
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base" ], function() {
	
	require( [ "css2!app/ui/css/modal" ] );
	
	var $ = jQuery = require( "jquery" )
		, base = require( "app/base" )
		, uuid = base.tools.uuid
		, format = base.tools.format
		, log = base.log
		, AppObject = base.AppObject;
	
	function Modal() {};
	
	Modal.prototype.__DEFAULT__ = {
		__template__: [
           '<div id="{id}" class="section active section-modal {id}">',
           '	<div class="sections-wrapper"></div>',
           '	<div class="modal fade modal-theme-{theme}">',
           '		<div class="modal-dialog {size}" style="{style}">',
           '			<div class="modal-content">',
           '				<div class="modal-header">',
           '					<button type="button" class="close {closeBtnCss}" data-dismiss="modal" aria-label="Close">',
           '						<span aria-hidden="true">&times;</span>',
           '					</button>',
           '					<h4 class="modal-title"><i class="fa fa-chain-broken"></i> {title}</h4>',
           '				</div>',
           '			<div class="modal-body"></div>',
           '		</div>',
           '	</div>',
           '</div>'
        ],
        __apps__: [],
        id: "",
        css: "",
        style: "",
        theme: "default",
        size: "modal-lg",
    	title: "",
    	url: "",
    	content: "",
    	params: "",
        content: "body",
        closeBtn: true,
        modal: {
        	backdrop: "static",
            keyboard: true,
            show: true
        },
        events: {
        	shown: function( apps ) {},
        	hiden: function( closed, data ) {}
        }
        
	};
	
	Modal.prototype.open = function( options, before, after, error ) {
		var m = $.extend( true, {}, this.__DEFAULT__, options );
		
		if ( !m.id ) {
			m.id = "PAGE"+uuid();
		}
		
		if ( typeof m.closeBtn == "boolean" && m.closeBtn == false ) {
			m.closeBtnCss = "hide";
		}
		
		var $content = $( format( m.__template__, {
			id: m.id,
			css: m.css,
			style: m.style,
			theme: m.theme,
			size: m.size,
			title: m.title,
			closeBtnCss: m.closeBtnCss
		} ) ).appendTo( m.content );
		
		var $modal = $content.find( ".modal" );
		
		m.url += ( m.url.indexOf( "?" ) != -1 ? "&" : "?" ) + "PAGEID=." + m.id;
		
		var appObject = new AppObject();
		
		$.ajax( {
			url: m.url,
			dataType: "html",
			success: function( html ) {
				$content.find( ".modal-body" ).html( html );
				
				var apps = base.init( true );
				$.each( apps, function( index, app ) {
					app.params = m.params;
					app.__data__ = m.__data__;
					app.content = $content;
					app.section = $content;
					app.__modal__ = m;
					m.__apps__.push( app.__namespace__ );
					appObject.__apps__.push( app.__namespace__ );
				} );
				appObject = m.__apps__;
				
				$modal.modal( m.modal );
				$modal.on( "hidden.bs.modal", function( event ) {
					var data = false;
					if ( m.__apps__.length > 0 ) {
						data = base.init( m.__apps__[ 0 ] ).__data__;
					}
					m.events.hiden( typeof data != "undfined" && data != null, data );
					
					if ( typeof after == "function" ) {
						var apps = [];
						for ( var index = 0; index < m.__apps__.length; index++ ) {
							apps.push( base.init( m.__apps__[ index ] ) );
						}
						after( apps );
					}
				} );
				
				if ( typeof before == "function" ) before( apps );
				var apps = base.init();
				m.events.shown( apps );
			},
			error: function() {
				if ( typeof error == "function" ) {
					error.call( m );
				}
				$content.remove();
			}
		} );
		
		return appObject;
	};
	
	return new Modal();
} );
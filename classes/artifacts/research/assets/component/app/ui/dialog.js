//###################################
/**
 * dialog 对话框
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base", "app/ui/modal" ], function() {
	
	var $ = jQuery = require( "jquery" )
		, base = require( "app/base" )
		, uuid = base.tools.uuid
		, format = base.tools.format
		, log = base.log
		, modal = require( "app/ui/modal" );

	function Dialog() {
		
	};
	
	Dialog.prototype.__DEFAULT__ = {
		id: false,
		css: '',
		style: '',
		width: "",
		height: 'auto',
		theme: 'default',
		content: '',
		contentCss: '',
		contentStyle: '',
		theme: 'default',
		container: 'body',
		title: '',
		closeBtn: true,
		buttonCss: '',
		buttonStyle: '',
		buttons: [],
		/*buttons: [
          { id: "", css: '', style: '', text: ''},
          { id: "", css: '', style: '', text: ''},
          ...
		],*/
		buttonHandler: false,
		//buttonHandler: function (event, index, $modal) {},
		cssModal: 'modal-md',
		modal: {
			backdrop: 'static',
			keyboard: true,
			show: true
		},
		callback: {
			show: false,
			shown: false,
			hide: false,
			hiden: false 
		}
	};
	
	Dialog.prototype.__DEFAULT_ALERT__ =  {
		contentStyle: 'font-size: 16px; padding-top: 15px; padding-bottom: 10px;',
		contentCss: 'text-center',
		buttonCss: 'text-center',
		buttons: [
		    {text: '确定', css: "btn-danger", icon: "fa fa-check" }
        ] 
	};
	
	Dialog.prototype.__DEFAULT_CONFIRM__ = {
		contentStyle: 'font-size: 16px; padding-top: 15px; padding-bottom: 10px;',
		contentCss: 'text-center',
		buttonCss: 'text-center',
		buttons: [
            {text: '确定', css: "btn-danger", icon: "fa fa-check" },
		    {text: '取消', css: "btn-danger", icon: "fa fa-remove" }
        ] 
	};

	Dialog.prototype.alert = function( title, content, buttonHandler ) {
		var opts;
		if (arguments.length == 0) {
			opts = {};
		} else if (arguments.length == 1) {
			if (typeof arguments[0] == 'object') {
				opts = arguments[0];
			} else {
				opts = {
					title: "操作提示",
					content: arguments[0]
				};
			}
			
		} else if (arguments.length == 2) {
			if (typeof arguments[1] == "function") {
				opts = {
					title: "操作提示",
					content: arguments[0],
					buttonHandler: arguments[1]
				};
			} else {
				opts = {
					title: arguments[0],
					content: arguments[1]
				};
			}
		} else {
			opts = {
				title: arguments[0],
				content: arguments[1],
				buttonHandler: arguments[2]
			};
		}
		
		this.dialog( $.extend( true, {}, this.__DEFAULT_ALERT__, opts ) );
	};
	
	Dialog.prototype.confirm = function( title, content, buttonHandler ) {
		var opts;
		if (arguments.length == 0) {
			opts = {};
		} else if (arguments.length == 1) {
			if (typeof arguments[0] == 'object') {
				opts = arguments[0];
			} else {
				opts = {
					title: "操作提示",
					content: arguments[0]
				};
			}
			
		} else if (arguments.length == 2) {
			if (typeof arguments[1] == "function") {
				opts = {
					title: "操作提示",
					content: arguments[0],
					buttonHandler: arguments[1]
				};
			} else {
				opts = {
					title: arguments[0],
					content: arguments[1]
				};
			}
		} else {
			opts = {
				title: arguments[0],
				content: arguments[1],
				buttonHandler: arguments[2]
			};
		}
		
		this.dialog( $.extend( true, {}, this.__DEFAULT_CONFIRM__, opts ) );
		
	};
	
	Dialog.prototype.dialog = function( options ) {
		var opts = $.extend( true, {}, this.__DEFAULT__, options );
		
		if ( typeof opts.closeBtn == "boolean" && opts.closeBtn == false ) {
			opts.closeBtnCss = "hide";
		}
		
		var buttons = [];
		$.each( opts.buttons, function ( index, button ) {
			if ( typeof button != 'object' ) {
				opts.buttons[ index ] = button = { text: button };
			}
			buttons.push( format( '<button type="button" class="btn btn-embossed btn-default {css}" style="{style}" id="{id}"><i class="{icon}"></i> {text}</button>', button ) );
		} );
		
		var $container = $( opts.container );
		
		var $modal = $(format([
          '<div class="modal fade dialog-ui dialog-theme-{theme} {css}" id="{id}" style="{style}">',
          '		<div class="modal-dialog {cssModal}" style="width: {width};">',
          '			<div class="modal-content">',
          '				<div class="modal-header">',
          '					<button type="button" class="close {closeBtnCss}" data-dismiss="modal" aria-hidden="true">×</button>',
          '					<h4 class="modal-title">{title}</h4>',
          '				</div>',
          '				<div class="modal-body">',
          '					<div class="{contentCss}" style="{contentStyle}">' + ( (typeof opts.content == 'object') ? $(opts.content).html() : opts.content ) + '</div>', 
          '				</div>',
          '				<div class="modal-footer">',
          					'<div class="{buttonCss}" style="{buttonStyle}">' + buttons.join('') + '</div>',
          '				</div>',
          '			</div>',
          '		</div>',
          '</div>'
          ], opts ), $container ).appendTo( $container );
		
		if ( typeof opts.buttons != "object" || !( opts.buttons instanceof Array ) || opts.buttons.length == 0 ) {
			$modal.find( "modal-footer" ).hide();
		}
		
		$modal.modal(opts.modal);
		
		$modal.find( ".modal-footer button" ).each( function ( event ) {
			$( this ).bind( "click", function (event) {
				if ( typeof opts.buttonHandler == "function" ) {
					if ( opts.buttonHandler( event, $( this ).index(), $modal ) === true ) return false;
				}
				$modal.modal( "hide" );
			} );
		} );
		
		$modal.on( "hidden.bs.modal", function( event ) {
			$( this ).remove();
			try {
				delete opts;
			} catch (e) {}
		});
		
	};
	
	return Dialog;
	
} );
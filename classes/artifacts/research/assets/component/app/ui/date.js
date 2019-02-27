define( [ "app/base", "app/ui/input", "app/ui/code" ], function() {
	var $ = require( "jquery" )
		, base = require( "app/base" )
		, Input = require( "app/ui/input" )
		, format = base.tools.format
		, uuid = base.tools.uuid
		, log = base.log;
	
	var __DEFAULT__ = {
		theme: "default",
		
	};
	
	function DatePicker( target, options ) {
		this.$input = $( target );
		var opts = this.opts = $.extend( true, {}, __DEFAULT__, options );
		
		if (opts.code) this.opts = opts = $.extend(true, opts, this.__DEFAULT_CODE__, options);
		else if (opts.remote) this.opts = opts = $.extend(true, opts, this.__DEFAULT_AJAX__, options);
		
		if ( typeof opts.id != "string" || opts.id.length == 0 ) opts.id = "DATE_PICKER_" + uuid();
		this.__init__();
		this.__event__();
	}
	
	DatePicker.prototype.__DEFAULT__ = __DEFAULT__;
	
	DatePicker.prototype.__init__ = function() {
		var opts = this.opts
			, $input = this.$input;
		
		if ( opts.readonly ) {
			this.$show = $( '<input type="text" class="form-control" readonly="true" />' );
			this.$show.insertAfter( $input );
		} else {
			this.$show = $input;
			opts.itemLabel = opts.itemValue;
		}
		
		var $show = this.$show;
		
		var input = new Input( $show, opts ),
			$wrapper = this.$wrapper = input.$wrapper;
		
		if ( opts.readonly ) {//只读隐藏属性
			$wrapper.prepend( $input );
			$input.addClass( "sr-only" ).removeClass( "form-control" ).attr( "tabindex", "9999" );
		} 
		//$input.insertBefore( $show );
		
		//验证提示
		if ( $input.is(" [name]" ) && $input.attr( "name" ).length != 0
				&& ( !$input.is(" [vname]" ) || $input.attr( "vname" ).length == 0 ) ) {
			if ( typeof opts.txtLabel == "string" && opts.txtLabel.length > 0 ) {
				$input.attr( "vname", opts.txtLabel );
			} else if ( typeof opts.txtPrefix == "string" && opts.txtPrefix.length > 0 ) {
				$input.attr( "vname", opts.txtPrefix );
			}
			
			$input.attr( "data-valid", ".select-" + opts.theme );
		}
		
		var $inputGroup = $wrapper.addClass( "select-" + opts.theme ).find( ".input-group" );
		
		if ( $inputGroup.length == 0 ) {
			$inputGroup = $show.wrap( '<div class="input-group"></div>' ).parent();
		}
		
		var $buttonGroup = $inputGroup.find( ".input-group-btn" );
		if ( $buttonGroup.length == 0)
			$buttonGroup = $( format( '<div class="input-group-btn"></div>' ) ).insertAfter( $show );
		
		if ( typeof opts.dropDirection.vertical == "string" && opts.dropDirection.vertical == "up" ) {
			$buttonGroup.addClass( "dropup" );
		}
		
		if ( typeof opts.dropDirection.horizontal != "string" || opts.dropDirection.horizontal != "right" ) {
			opts.dropDirection.horizontal = "left";
		}
		
		var $drop;
		if ( typeof opts.dropButton.icon == "string" && opts.dropButton.icon.length > 0 ) {
			$drop = $( format( '<button class="{css} dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="true" aria-haspopup="true"><i class="{icon}"></i> {text}</button>', opts.dropButton ) ).appendTo( $buttonGroup );
		} else {
			$drop = $( format( '<button id="{id}" class="{css}" type="button" style="{style}">{text}</button>', opts.dropButton ) ).appendTo( $buttonGroup );
		}
		
		this.$drop = $drop;
		
		var $dropUl = this.$dropUl = $( format( '<ul class="dropdown-menu dropdown-menu-{0}"></ul>', [ opts.dropDirection.horizontal ] ) );
		$dropUl.insertAfter( $drop );
		
		console.log( $dropUl );
		
		if (!opts.autoLoad) return false;
		
		if (opts.items) this.__loadNative__(opts.items);
		else if (opts.code) this.__loadCode__();
		else if (opts.remote) this.__loadAjax__();
		
	};
	
	
	
	//jQuery扩展
	$.prototype.date = function( options ) {
		
		return this.each( function() {
			if ( !$( this ).data( "date" ) ) {
				return true;
			}
			$( this ).addClass( "ui-date" ).addClass( "has-app-change" );
			
			var opts = $( this ).attr( 'options' ) || '';
			if ( typeof opts == "string" && opts.length > 0 ) {
				opts = $.trim( opts );
				try {
					opts =  (new Function("return " +  opts ))();
				} catch (e) { 
					opts = {};
				}
			} else opts = {};
			
			opts = $.extend(true, {}, options, opts);
			
			$( this ).data( "date", new DatePicker( this, opts ) );
		} );
	};
	
	return Select;

} );
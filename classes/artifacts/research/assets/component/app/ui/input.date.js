define( [ "app/base", "app/ui/input", "bootstrap.datetimepicker" ], function() {
	var $ = require( "jquery" )
	, app = require( "app/base" )
	, Input = require( "app/ui/input" )
	, format = app.tools.format
	, uuid = app.tools.uuid
	, log = app.log;
	
	
	__DEFAULT__ = {
		theme: "default",
		cssButton: "btn btn-default",
		txtButton: "",
		cssButtonIcon: "fa fa-calendar",
		afterDateName: "",
		afterDatePrefix: "至",
		picker: {
			timeZone: 'Etc/UTC',
			format: "YYYY-MM-DD", //YYYY-MM-DD hh:mm:ss
	        dayViewHeaderFormat: 'MMMM YYYY',
	        extraFormats: false,
	        stepping: 1,
	        minDate: false, //最小时间
	        maxDate: false, //最大时间
	        useCurrent: true, //是否使用当前日期
	        collapse: true,
	        locale: "zh_CN", //国际化资源
	        defaultDate: false,
	        disabledDates: false,
	        enabledDates: false,
	        sideBySide: false,
	        daysOfWeekDisabled: false,
	        calendarWeeks: false, 
	        viewMode: 'days', //显示视图 初始化 days 天  months 月 years 年 decades小时
	        toolbarPlacement: 'default', 
	        showTodayButton: false, //地板显示是否使用当前日期
	        showClear: false,
	        showClose: false,
	        widgetPositioning: {
	            horizontal: 'auto',
	            vertical: 'auto'
	        },
	        widgetParent: null,
	        ignoreReadonly: false,
	        keepOpen: false,
	        focusOnShow: true,
	        inline: false,
	        keepInvalid: false,
	        datepickerInput: '.datepickerinput',
	        viewDate: false
		}
	};
	function InputDate( target, options ) {
		this.$input = $( target );
		var opts = this.opts = $.extend( true, {}, __DEFAULT__, options );
		
		this.__init__();
	};
	
	InputDate.prototype.__init__ = function() {
		var opts = this.opts
		, $input = this.$input;
		
		var input = new Input( $input, opts )
			, $wrap = input.$wrap;
		
		var $inputGroup = $wrap.find( ".input-group" );
		if ( $inputGroup.length == 0 ) {
			$inputGroup = $input.wrap( '<div class="input-group"></div>' ).parent();
		}
		
		$input.datetimepicker( opts.picker );
		
		if ( typeof opts.afterDateName == "string" && opts.afterDateName.length > 0 ) {
			
			var $buttonGroup = $inputGroup.find( ".input-group-btn" );
			if ( $buttonGroup.length == 0) {
				$buttonGroup = $( format( '<div class="input-group-btn"></div>' ) ).insertAfter( $input );
			}
			
			var $inputButton = this.$inputButton = $( format( '<button class="{cssButton}"><i class="{cssButtonIcon}"></i> {txtButton}</button>') ).appendTo( $buttonGroup );
			
			$inputGroup.append( format( '<div class="input-group-addon">{0}</div>', [ opts.afterDatePrefix ] ) );
			
			var $afterInput = this.$afterInput = $( format( '<input type="text" name="{0}" class="form-control"', [ opts.afterDateName ] ) ).appendTo( $inputGroup );
			
			$buttonGroup = $( format( '<div class="input-group-btn"></div>' ) ).appendTo( $buttonGroup );
			
			var $afterButton = this.$afterButton = $( format( '<button class="{cssButton}"><i class="{cssButtonIcon}"></i> {txtButton}</button>') ).appendTo( $buttonGroup );
			
			$afterInput.datetimepicker( opts.picker );
		}
		
	};
	
	
	$.fn.date =function( options ){
		return $( this ).each( function() {
			if ( $( this ).is( ".ui-input-date" ) ) {
				return true;
			}
			
			$( this ).addClass( "ui-input-date" );
			
			var opts = $( this ).attr( 'options' ) || '';
			if ( typeof opts == "string" && opts.length > 0 ) {
				opts = $.trim( opts );
				try {
					opts =  (new Function("return " +  opts ))();
				} catch (e) { 
					opts = {};
				}
			} else opts = {};
			
			options = $.extend(true, opts, options);
			
			$( this ).data( "input.date", new InputDate( this, options ) );
		} );
	};
	
} );
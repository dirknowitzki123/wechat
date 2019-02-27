//###################################
/**
 * validate.extend jquery-validate 核心封装扩展
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ 
    "app/base",
	"jquery.validate.min",
	"additional.methods",
	"app/extend/validate.method.extend"
], function() {
	var $ = jQuery = require( "jquery" )
		, app = require( "app/base" )
		, message = app.message
		, format = app.tools.format;
	
	//@config 自带验证国际化中文
	$.extend( $.validator.messages, {
		required: "这是必填字段",
		remote: "请修正此字段",
		email: "请输入有效的电子邮件地址",
		url: "请输入有效的网址",
		date: "请输入有效的日期",
		dateISO: "请输入有效的日期 (YYYY-MM-DD)",
		number: "请输入有效的数字",
		digits: "只能输入数字",
		creditcard: "请输入有效的信用卡号码",
		equalTo: "你的输入不相同",
		extension: "请输入有效的后缀",
		maxlength: $.validator.format("最多可以输入 {0} 个字符"),
		minlength: $.validator.format("最少要输入 {0} 个字符"),
		rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串"),
		range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
		max: $.validator.format("请输入不大于 {0} 的数值"),
		min: $.validator.format("请输入不小于 {0} 的数值")
	} );
	
	
	//@config 参数配置
	$.validator.setDefaults( {
		onsubmit: false,
		onfocusout: false,
		onkeyup: false,
		onclick: false,
		focusCleanup: false,
		invalidHandler: function( event, validator ) {
		},
		errorPlacementOverride: function(error, element) {
		    var $error = $( element );
		    if ( $error.is( "[data-valid]" ) ) {
		    	var valid = $error.attr( "data-valid" ) || "";
		    		valid = $.trim( valid );
		    	if ( typeof valid == "string" &&  valid.length > 0 ) {
		    		$error.parents( valid + ":eq(0)" )
		    			.addClass( "has-error" )
		    			.find( ":input.form-control" )
		    			.popover( {
		    				title: $error.attr( "vname" ) || "",
			    			animation: true,
			    			content: error,
			    			placement: "auto",
			    			trigger: "hover",
			    			template: '<div class="popover has-error" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
			    		} );
		    		
		    		return;
		    	}
		    }
		    
		    error.insertAfter( element );
		    //$.validator.defaults.errorPlacement( error, element );
		},
		showErrors: function( errorMap, errorList ) {
			var $form = $( this.currentForm )
				, defaults_errors = []
				, msgs = []
				, $error;
			var errorPlacementOverride = this.settings.errorPlacementOverride;
			$.each( errorMap, function( index, error ) {
				$error = $form.find( format( ":input[name={0}]", [ index ] ) );
				 if ( $error.is( "[data-valid]" ) ) {
			    	var valid = $error.attr( "data-valid" ) || "";
			    		valid = $.trim( valid );
			    	if ( typeof valid == "string" &&  valid.length > 0 ) {
			    		msgs.push( format( "<span style=\"white-space:nowrap;\">{0}：{1}</span>", [ $error.attr( "vname" ), error ] ) );
			    		errorPlacementOverride( error, $error );
			    		return true;
			    	}
			    	defaults_errors.push( defaults_errors );
		    	}
			} );
			
			if ( msgs.length > 0 ) {
				message.error( msgs.join( '&#12288;' ) );
			}
			
			if ( defaults_errors.length > 0 ) {
				$.validator.defaults.defaultShowErrors( defaults_errors );
			} else {
				for ( i = 0, elements = this.validElements(); elements[ i ]; i++ ) {
					this.settings.unhighlightOverride.call( this, elements[ i ], this.settings.errorClass, this.settings.validClass );
				}
			}
		},
		unhighlightOverride: function( element, errorClass, validClass ) {
			var $error = $( element );
		    if ( $error.is( "[data-valid]" ) ) {
		    	var valid = $error.attr( "data-valid" ) || "";
		    		valid = $.trim( valid );
		    	if ( typeof valid == "string" &&  valid.length > 0 ) {
		    		$error.parents( valid + ":eq(0)" )
		    			.removeClass( "has-error" )
		    			.find( ":input.form-control" )
		    			.popover('destroy');
		    		return;
		    	}
		    }
		    
		}
	} );
	

	$.validator.prototype.__super_check__ = $.validator.prototype.check;
	//@override 重写方法
	$.validator.prototype.check = function( element ) {
		if ( this.__super_check__( element ) ) {
			var $input =  $( element );
			var name = $input.attr( "name" );
			
			if ( typeof name != "string" || name.length == 0 ) {
				return true;
			}
			
			var valid = $input.attr( "data-valid" ) || "";
    		valid = $.trim( valid );
    		if ( typeof valid != "string" ||  valid.length == 0 ) {
    			return true;
    		}
    		
    		var handlers = ( ( this.__custom_rules__ || {} )[ name ] ) || [];
    		var message;
    		for ( var index = 0; index < handlers.length; index++ ) { 
    			message = handlers[ index ].call( element );
    			if ( typeof message != "string" || message.length == 0 ) {
    				continue;
    			}
    			
    			this.errorList.push( {
					message: message,
					element: $input[ 0 ],
					method: "addValidHandler"
				} );
    			
    			this.errorMap[ name ] = message;
    			this.submitted[ name ] = message;
    			
    			this.successList.pop();
    		}
		}
	};
	
	$.validator.prototype.__super_resetForm__ = $.validator.prototype.resetForm;
	//@add 新增form局部验证
	$.validator.prototype.group = function( group ) {
		this.prepareForm();
		var elements = $( group );
		for ( var i = 0; elements[ i ]; i++ ) {
			if ( elements[ i ][ 'name' ] ) this.check( elements[ i ] );
		}
		elements = $(":input[name]", group).not(':hidden');
		for ( var i = 0; elements[ i ]; i++ ) {
			if (elements[ i ][ 'name' ]) this.check( elements[ i ] );
		}
		this.showErrors();
		return this.valid();
	};
	
	//@override 重写方法
	$.validator.prototype.resetForm = function() {
		this.__super_resetForm__();
		var $input;
		$(this.currentForm).find(':input[name]').not(':hidden').each( function() {
			$input = $( this );
			if ( $input.is( "[data-valid]" ) ) {
		    	var valid = $input.attr( "data-valid" ) || "";
		    		valid = $.trim( valid );
		    	if ( typeof valid == "string" &&  valid.length > 0 ) {
		    		$input.parents( valid + ":eq(0)" ).removeClass( "has-error" )
		    			.find( ":input.form-control" ).popover('destroy');
		    	}
		    }
		} );
	};
	
	//@add 新增页面表单验证函数规则 
	$.validator.prototype.addValidHandler = function( name, handler ) {
		if ( typeof name != "string" || name.length == 0 ) {
			return this;
		}
		if ( typeof handler != "function" ) {
			return this;
		}
		
		if ( typeof this.__custom_rules__ == "undefined" ) {
			this.__custom_rules__ = {};
		}
		
		if ( typeof this.__custom_rules__[ name ] == "undefined" ) {
			this.__custom_rules__[ name ] = [];
		} 
		
		
		this.__custom_rules__[ name ].push( handler );
		
	};
	
	//@add 新增文本验证  
	//@param handler 函数 返回值 {string} length==0为验证通过与不通过 
	$.fn.validErrorTip = function( handler, showMessage ) {
		if ( typeof showMessage != "boolean" ) {
			showMessage = true;
		}
		var $input, valid, error, isValid = true;
		$( this ).each( function (){
			$input = $( this );
			if ( $input.is( "[data-valid]" ) ) {
				valid = $input.attr( "data-valid" ) || "";
	    		valid = $.trim( valid );
	    		if ( valid.length == 0 ) {
	    			return true;
	    		}
			}
			
			error = "";
			if ( typeof handler == "string" ) {
				error = handler;
			} else if ( typeof handler == "function" ) {
				error = handler.call( this );
			}
			
			if ( error.length > 0 ) {
				$input.parents( valid + ":eq(0)" )
	    			.addClass( "has-error" )
	    			.find( ":input.form-control" )
	    			.popover( {
	    				title: $input.attr( "vname" ) || "",
		    			animation: true,
		    			content: error,
		    			placement: "auto",
		    			trigger: "hover",
		    			template: '<div class="popover has-error" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
		    		} );
				isValid = false;
				if ( showMessage ) {
					message.error( format( "{0}：{1}", [ $input.attr( "vname" ), error ] ) );
				}
    			return true;
    		}
			
			$input.parents( valid + ":eq(0)" )
				.removeClass( "has-error" )
				.find( ":input.form-control" )
				.popover('destroy');
			
		});
		
		return isValid;
	};
	
	
} );;
//###################################
/**
 * input 输入框
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base" ], function() {
	var $ = require( "jquery" )
	, app = require( "app/base" )
	, format = app.tools.format
	, uuid = app.tools.uuid
	, log = app.log;
	
	var __DEFAULT__ = {
		id: "", //容器id
		css: "", //容器类样式
		style: "",	//容器内嵌样式
		size: "md", //bootstrap 组件大小 只为[sm, md, lg]
		theme: "default", //组件主题
		required: false,//是否必输入项 添加必输入的样式效果
		requiredLocation: "label",//required显示位置 [ "label", "prefix", "suffix" ] 标签，前缀，后缀
		requiredHTML: '<font color="red" style="font-weight: normal;" title="必填项">(*)</font>',//required模板信息
		cssCol: "col-xs-12 col-sm-6 col-md-6 col-lg-3", //容器布局 bootstrap响应式布局
        cssLabel: "",//标签演示 可以与cssGroup一起水平布局
        txtLabel: "",//标签文本
        cssGroup: "",//容器组样式  一般与cssLabel用于水平布局
        cssPrefix: "",//前缀类样式
        cssPrefixIcon: "",//前缀图标`
        txtPrefix: "",//前缀文本
        cssSuffix: "",//后缀类样式
        txtSuffix: "",//后缀文本
        cssSuffixIcon: "",//后缀图标
        suffixButtonsLocationLast: true, //按钮位置存放 true存放在最后，false存放在其他按钮之前
        suffixButtons: [], 
       /* suffixButtons: [
			{ id: "", css: "", style: "", icon: "", text: "", title="" },
			{ id: "", css: "", style: "", icon: "", text: "", title="" }
        ], */
    	suffixButtonsHandler: function( event, index ) {
    		//index 代表第几个按钮
    	}
	};
	
	//构造函数
	function Input( target, options ) {
		this.$input = $( target );
		var opts = this.opts = $.extend( true, {}, __DEFAULT__, options );
		if ( typeof opts.id != "string" || opts.id.length == 0 ) opts.id = "INPUT_" + uuid();
		this.__init__();
		this.__events__();
	};
	
	Input.prototype.__DEFAULT__ = __DEFAULT__;
	
	//初始化方法
	Input.prototype.__init__ = function() {
		var opts = this.opts
			, $input = this.$input
			, $group = this.$group = false;
		
		$input.addClass( "form-control" ).attr( "autocomplete", "off" ).addClass( "input-" + opts.size );
		
		var $wrapper = this.$wrapper = $input.wrap( format( '<div id="{id}" class="{cssCol} {css} input-{theme}" style="{style}" ></div>', opts  ) ).parent();

		if ( opts.required && $.inArray( opts.requiredLocation, [ "label", "prefix", "suffix" ] ) != -1 ) {
			if ( opts.requiredLocation == "label" ) {
				opts.txtRequiredLabel = opts.requiredHTML;
			} else if ( opts.requiredLocation == "prefix" ) {
				opts.txtRequiredPrefix = opts.requiredHTML;
			} else if ( opts.requiredLocation == "suffix" ) {
				opts.txtRequiredSuffix = opts.requiredHTML;
			} 
		}
		//标签
		if ( typeof opts.txtLabel == "string" && opts.txtLabel.length > 0 ) {
			$wrapper.prepend( format( '<label class="control-label {cssLabel}">{txtLabel}{txtRequiredLabel}</label>', opts ) );
		}
		
		//验证提示
		if ( $input.is(" [name]" ) && $input.attr( "name" ).length != 0
				&& ( !$input.is(" [vname]" ) || $input.attr( "vname" ).length == 0 ) ) {
			if ( typeof opts.txtLabel == "string" && opts.txtLabel.length > 0 ) {
				$input.attr( "vname", opts.txtLabel );
			} else if ( typeof opts.txtPrefix == "string" && opts.txtPrefix.length > 0 ) {
				$input.attr( "vname", opts.txtPrefix );
			}
			$input.attr( "data-valid", ".input-" + opts.theme );
		}
		
		//前缀
		if ( ( prefix = typeof opts.cssPrefixIcon == "string" && opts.cssPrefixIcon.length > 0 )
				|| ( typeof opts.txtPrefix == "string" && opts.txtPrefix.length > 0 ) ) {
				
			if ( typeof $group == "boolean" && $group == false ) {
				$group = $( format( '<div class="input-group input-group-{size} {cssGroup}"></div>', opts ) ).appendTo( $wrapper );
				$group.append( $input );
			} 
			
			var $prefix = $( format( '<div class="input-group-addon {cssPrefix}">{txtPrefix}{txtRequiredPrefix}</div>', opts ) );
			
			if ( prefix ) {
				$prefix.prepend( format( '<i class="{cssPrefixIcon}"></i> ', opts ) );
			}
			
			$group.prepend( $prefix );
		}
		
		//后缀
		if ( ( suffix = typeof opts.cssSuffixIcon == "string" && opts.cssSuffixIcon.length > 0 )
				|| ( typeof opts.txtSuffix == "string" && opts.txtSuffix.length > 0 ) ) {
			
			if ( typeof $group == "boolean" && $group == false ) {
				$group = $( format( '<div class="input-group input-group-{size} {cssGroup}"></div>', opts ) ).appendTo( $wrapper );
				$group.append( $input );
			} 
			
			var $suffix = $( format( '<div class="input-group-addon {cssSuffix}">{txtSuffix}{txtRequiredSuffix}</div>', opts ) ).appendTo( $group );
			if ( suffix ) {
				$suffix.prepend( format( '<i class="{cssSuffixIcon}"></i> ', opts ) );
			}
		}
		
		if ( suffixButton = $.isArray( opts.suffixButtons ) &&  opts.suffixButtons.length > 0  ) {
			if ( typeof $group == "boolean" && $group == false ) {
				$group = $( format( '<div class="input-group input-group-{size} {cssGroup}"></div>', opts ) ).appendTo( $wrapper );
				$group.append( $input );
			} 

			var $buttonGroup = $( format( '<div class="input-group-btn" style="display:none;"></div>' ) ).appendTo( $group );
			var $suffixButtonsGroup  = $( format( '<div class="input-group-btn suffix-buttons-group"></div>' ) );
			
			if( typeof opts.suffixButtonsLocationLast == "boolean" && opts.suffixButtonsLocationLast == true ) {
				$suffixButtonsGroup.insertAfter( $buttonGroup );
			} else {
				$suffixButtonsGroup.insertBefore( $buttonGroup );
			}
			
			$.each( opts.suffixButtons, function( index, button ) {
				if ( typeof button != "object" ) {
					button = { id: "", css: "", style: "", icon: "", text: button, title: "", size: opts.size };
				}
				if ( typeof button.size != "string" ) {
					button.size = opts.size;
				}
				if ( typeof button.icon == "string" && button.icon.length > 0 ) {
					$suffixButtonsGroup.append( format( '<button id="{id}" class="btn btn-default {css} btn-{size}" type="button" style="{style}" title="{title}"><i class="{icon}"></i> {text}</button>', button ) );
				} else {
					$suffixButtonsGroup.append( format( '<button id="{id}" class="btn btn-default {css} btn-{size}" type="button" style="{style}" title="{title}">{text}</button>', button ) );
				}
			} );
			
			
			$suffixButtonsGroup.find( "button" ).bind( "click", function( event ) {
				opts.suffixButtonsHandler.call( $input, event, $( this ).index() );
				return false;
			} );
			
		}

		//横向排版 标签 左边 文本框右边 水平排版
		if ( typeof opts.txtLabel == "string" && opts.txtLabel.length > 0 
			&& typeof opts.cssLabel == "string" && opts.cssLabel.length > 0
			&& typeof opts.cssGroup == "string" && opts.cssGroup.length > 0
			&& $.isArray( opts.suffixButton.buttons ) &&  opts.suffixButton.buttons.length > 0 
			&& typeof $group == "boolean" && $group == false
		) {
			$group = $( format( '<div class="input-group input-group-{size} {cssGroup}"></div>', opts ) ).appendTo( $wrapper );
			$group.append( $input );
		}
		if ( typeof opts.cssGroup == "string" && opts.cssGroup.length != 0 ) {debugger;
			if ( typeof $group == "boolean" && $group == false ) {
				$group = $( format( '<div class="input-group input-group-{size} {cssGroup}"></div>', opts ) ).appendTo( $wrapper );
				$group.append( $input );
			} 
		}
		
		if ( typeof $group == "boolean" && $group == false ) {
			$wrapper.append( $input );
		} 
		
	};
	
	Input.prototype.__events__ = function() {
		var $wrapper = this.$wrapper,
			$input = this.$input;
		
		$input.bind( "disabled.app.ui", function( event, bool ) {
			if ( typeof bool != "boolean" ) bool = $( this ).is( ":disabled" );
			
			if ( bool ) {
				$wrapper.find( "input:not(:disabled)" ).each( function() {
					$( this ).attr( "disabled", "disabled" );
				} );
			} else {
				$wrapper.find( "input:disabled" ).each( function() {
					$( this ).removeAttr( "disabled" );
				} );
			}
		} );
		
		$input.bind( "readonly.app.ui", function( event, bool ) {
			if ( typeof bool != "boolean" ) bool = $( this ).is( "[readonly]" );
			
			if ( bool ) {
				$wrapper.find( "input:not([readonly])" ).each( function() {
					$( this ).attr( "readonly", "readonly" );
				} );
			} else {
				$wrapper.find( "input[readonly]" ).each( function() {
					$( this ).removeAttr( "readonly" );
				} );
			}
		} );
		
	};
	
	//jQuery扩展
	$.prototype.input = function( options ) {
		
		return $( this ).each( function() {
			if ( $( this ).is( ".sr-only, [type=hidden], ui-input" ) ) {
				return true;
			}
			
			var opts = $( this ).attr( 'options' ) || '';
			if ( typeof opts == "string" && opts.length > 0 ) {
				opts = $.trim( opts );
				if ( opts.indexOf( "{" ) != 0 ) {
					opts = { txtLabel: opts };
				} else {
					try {
						opts =  (new Function("return " +  opts ))();
					} catch (e) { 
						opts = {};
					}
				}
			} else opts = {};
			
			opts = $.extend(true, {}, options, opts);
			
			$( this ).data( "input", new Input( this, opts ) );
		} );
	};
	
	$.prototype.drag = function(options){
		var $div = $(this);
		$div.css(options.css);
		$div.mousedown(function(e){
			var offset = $div.offset();
			var x = e.pageX - offset.left;
			var y = e.pageY - offset.top;
			$(document).bind("mousemove",function(ev){
				$div.stop();
				var _x = ev.pageX - x;
				var _y = ev.pageY - y;
				$div.animate({left:_x>200?_x:200+"px",top:_y>10?_y:10+"px"},1);
			});
		});
		$(document).mouseup(function(){
			$(this).unbind("mousemove");
		});
		return $div;
	};
	return Input;
} );
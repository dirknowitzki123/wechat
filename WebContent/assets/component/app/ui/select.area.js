//###################################
/**
 * select.area 下拉级联地区
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ 
          "app/base", 
          "app/ui/input", 
          "app/ui/code",
          ], function() {
	
	var $ = require( "jquery" )
	, base = require( "app/base" )
	, Input = require( "app/ui/input" )
	, code = require( "app/ui/code" )
	, format = base.tools.format
	, toTreeJson = base.tools.toTreeJson
	, uuid = base.tools.uuid
	, log = base.log;
	
	var __DEFAULT__ = {
		theme: "default",
		cssCol: "", //容器布局 bootstrap响应式布局
		dropButton: {
        	id: "", 
        	css: "btn btn-default", 
        	style: "", 
        	icon: "" 
		},
		dropDirection: { //下拉显示方向
			vertical: "down",//垂直方向
			horizontal: "right" //水平方向
		},
        suffixButton: {
        	buttons: [],
        	handlers: function( event, index ) {}
        },
        dropHeight: "135px;",
		autoLoad: true,
		readonly: true,
		multi: false,
		itemLabel: "label",
		itemValue: "value",
		itemChildren: "children",
		itemValueLimit: ",", //multi 多选时，连接字符串
		itemLevelValues: false, //层次itemValue 是否存取， false 不存取， 数组存取，为数组三个值
		/*itemLevelValues: [ "prov", "city", "area" ],*/
		requiredLevel: 'all',
        showChildrenLevel: 3,
        showChildrenLabels: ["请选择...", "请选择...", "请选择..."],
        releSelect: true, //是否关联选择
        reasonable: true, //是否数据合理化， 添加其他市，其他区
		items: false,
		remote: false,
		root: 'list',
		events: {
			change: function (event, val, item) {
				//单选参数 event, val, item,
				//多选参数 event, vals, items
				//console.log(arguments);
			}
		}
	};
	
	//码表配置
	var __DEFAULT_CODE__ = {
	    itemLabel: 'valName',
        itemValue: 'valCode',
		code: {
			type: '',
			group: "__default__",
			callback: function (data) {
                return data;
            }
		}
	};
	
	
	function Area( target, options ) {
		this.$input = $( target );
		var opts = this.opts = $.extend( true, {}, __DEFAULT__, options );
		
		if (opts.code) this.opts = opts = $.extend(true, opts, __DEFAULT_CODE__, options);
		
		if ( typeof opts.cssCol != "string" || opts.cssCol.length == 0 ) {
			
			if ( opts.showChildrenLevel == 1 ) {
				delete opts.cssCol;
			} else if ( opts.showChildrenLevel == 2 ) {
				opts.cssCol = "col-xs-12 col-sm-12 col-md-12 col-lg-6";
			} else if ( opts.showChildrenLevel == 3 ) {
				opts.cssCol = "col-xs-12 col-sm-12 col-md-12 col-lg-9";
			} else if ( opts.showChildrenLevel >= 4 ) {
				opts.cssCol = "col-xs-12 col-sm-12 col-md-12 col-lg-12";
			} 
			
			
		}
		
		if ( typeof opts.releSelect != "boolean" ) {
			opts.releSelect = false;
		}
		
		if ( typeof opts.requiredLevel != "number" || opts.requiredLevel > opts.showChildrenLevel ) {
			opts.requiredLevel = opts.showChildrenLevel;
		} else if ( opts.requiredLevel < 1 ) {
			opts.requiredLevel = 1;
		}
		
		if ( typeof opts.id != "string" || opts.id.length == 0 ) opts.id = "SELECT_AREA_" + uuid();
		this.__init__();
	};
	
	Area.prototype.__init__ = function() {
		var opts = this.opts
			, $input = this.$input;
		
		this.loaded = false;
		this.__change_value__ = false;
		
		$input.addClass( "sr-only" ).attr( "tabindex", "9999" );
		
		var $show = $( '<input type="text" class="form-control" readonly="true" />' );
		$show.insertAfter( $input );
		
		//验证提示
		if ( $input.is(" [name]" ) && $input.attr( "name" ).length != 0
				&& ( !$input.is(" [vname]" ) || $input.attr( "vname" ).length == 0 ) ) {
			if ( typeof opts.txtLabel == "string" && opts.txtLabel.length > 0 ) {
				$input.attr( "vname", opts.txtLabel );
			} else if ( typeof opts.txtPrefix == "string" && opts.txtPrefix.length > 0 ) {
				$input.attr( "vname", opts.txtPrefix );
			}
			
			$input.attr( "data-valid", ".select-area-" + opts.theme );
		}
		
		var input = new Input( $show, opts ),
			$wrapper = this.$wrapper = input.$wrapper;
		
		
		var $inputGroup = $wrapper.addClass( "select-area-" + opts.theme ).find( ".input-group" );
		
		if ( $inputGroup.length == 0 ) {
			$inputGroup = $show.wrap( format( '<div class="input-group input-group-{size} {cssGroup}"></div>', opts ) ).parent();
		}
		
		if ( typeof opts.dropDirection.horizontal != "string" || opts.dropDirection.horizontal != "right" ) {
			opts.dropDirection.horizontal = "left";
		}
		
		if ( typeof opts.dropDirection.vertical == "string" && opts.dropDirection.vertical == "up" ) {
			if ( typeof opts.dropButton.icon != "string" || opts.dropButton.icon.length == 0 ) {
				opts.dropButton.icon = "fa fa-caret-up";
			}
		} else {
			if ( typeof opts.dropButton.icon != "string" || opts.dropButton.icon.length == 0 ) {
				opts.dropButton.icon = "fa fa-caret-down";
			}
		}
		
		var $buttonGroup,
			$show, 
			$drop,
			$dropUl; 
		for ( var index = 0; index < opts.showChildrenLevel; index++ ) {
			if ( index != 0 ) {
				$show = $( '<input type="text" class="form-control" readonly="true" />' ).appendTo( $inputGroup );
			}
			
			$buttonGroup = $( '<div class="input-group-btn"></div>' ).appendTo( $inputGroup );
			
			if ( typeof opts.dropDirection.vertical == "string" && opts.dropDirection.vertical == "up" ) {
				$buttonGroup.addClass( "dropup" );
			}
		
			if ( typeof opts.dropButton.icon == "string" && opts.dropButton.icon.length > 0 ) {
				$drop = $( format( '<button class="{css} dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="true" aria-haspopup="true"><i class="{icon}"></i> {text}</button>', opts.dropButton ) ).appendTo( $buttonGroup );
			} else {
				$drop = $( format( '<button id="{id}" class="{css}" type="button" style="{style}">{text}</button>', opts.dropButton ) ).appendTo( $buttonGroup );
			}
			
			
			$dropUl = $( format( '<ul class="dropdown-menu dropdown-menu-{0}" style="max-height:{1};overflow:auto;" ></ul>', [ opts.dropDirection.horizontal, opts.dropHeight ] ) ).appendTo( $buttonGroup );;
			
			$inputGroup.appendTo( $wrapper );
		}
		
		
		this.$dropBtns = $wrapper.find( "button" );
		this.$dropUls = $wrapper.find( "ul" );
		this.$labels = $wrapper.find( "input.form-control" );
		
		this.$values = $();
		
		var valueNames;
		if ( typeof opts.itemLevelValues == "string" ) {
			valueNames = opts.itemLevelValues.split( opts.itemValueLimit );
		} else if ( typeof opts.itemLevelValues == "object" && opts.itemLevelValues instanceof Array ) {
			valueNames = opts.itemLevelValues;
		}
		
		if ( typeof valueNames == "object" && valueNames instanceof Array ) {
			var values = [];
			values.push( "<div class=\"level-item-value\">" );
			for ( var index = 0; index < valueNames.length; index++ ) {
				values.push( format( "<input type=\"hidden\" name=\"{0}\" />", [ $.trim( valueNames[ index ] ) ] ) );
			}
			values.push( "</div>" );
			$values = $( values.join( "" ) ).insertAfter( $input );
			this.$values = $values.find( "input" );
		}
		
		this.__event__();
		
		if (!opts.autoLoad) return false;
		
		if (opts.items) this.__loadNative__(opts.items);
		else if (opts.code) this.__loadCode__();
	};
	
	//加载码表
	Area.prototype.__loadCode__ = function () { 
		var that = this; opts = this.opts;
		this.loaded = false;
		//获取缓存数据通过回调函数
		code.getCacheByCallBack( opts.code.type, opts.code.group, function ( code ) {
			code = this.opts.code.callback( code );
			this.__loadNative__( code );
		}, that);
	};
	
	//加载本地静态数据
	Area.prototype.__loadNative__ = function ( items ) {
		var opts = this.opts
			, $wrapper = this.$wrapper
			, $drop = this.$drop
			, $input = this.$input;
		
		if (!items instanceof Array) items = [items];
		
		this.__build__( 0, items ); 
		
		this.loaded = true, value = $input.data( "value" );
		if ( typeof value != "undefined" ) {
			$input.trigger( "change2.app.ui.select.area", [ value ] );
		} else {
			var value = $input.val();
			if ( value.lenth != 0 ) {
				$input.trigger( "change2.app.ui.select.area", [ value ] );
			} 
		}
		
	};
	
	//加载区域
	Area.prototype.__build__ = function( level, items ) {
		var opts = this.opts
			, $dropBtns = this.$dropBtns
			, $dropUls = this.$dropUls
			, $labels = this.$labels
			, $values = this.$values;
		
		if ( level >= opts.showChildrenLevel ) {
			return;
		}
		
		for ( var index = level + 1; index < opts.showChildrenLevel; index++ ) {
			$dropBtns.eq( index ).addClass("disabled").attr( "disabled", "disabled" );
			$dropUls.eq( index ).empty();
			
			$labels.eq( index ).val( "" ).data( "value", "" ); 
			$values.eq( index ).val( "" );
		}
		
		$labels.eq( level ).val( "" ).data( "value", "" ); ; 
		$values.eq( level ).val( "" );
		
		$dropBtns.eq( level ).removeClass("disabled").removeAttr( "disabled" );
		
		var $dropUl = $dropUls.eq( level ).empty();
		
		if ( items.length > 0 && opts.reasonable == true ) {
			var item = items[ items.length -1 ], item2;
			value = item[ opts.itemValue ];
			if ( level == 1 && value.substring( 2 ) != "9900" ) {
				item2 = {};
				item2[ opts.itemLabel ] = "其他市";
				item2[ opts.itemValue ] = value.substring( 0, 2 ) + "9900";
				items.push( item2 ); 
			} else if ( level == 2 && value.substring( 4 ) != "99") {
				item2 = {};
				item2[ opts.itemLabel ] = "其他区";
				item2[ opts.itemValue ] = value.substring( 0, 4 ) + "99";
				items.push( item2 ); 
			} 
		}
		
		var item;
		for( var index = 0; index < items.length; index++ ) {
			var item = items[ index ];
			$( format( "<li value=\"{1}\" level=\"{0}\"><a href=\"javascript:;\">{2}</a></li>", [ level, item[ opts.itemValue ], item[ opts.itemLabel ] ] ) ).appendTo( $dropUl ).data( "item", item );
		}
		
	};
	
	Area.prototype.__event__ = function() {
		var that = this
			, opts = this.opts
			, $input = this.$input
			, $dropBtns = this.$dropBtns
			, $dropUls = this.$dropUls
			, $labels = this.$labels
			, $values = this.$values;
		
		$dropUls.delegate( "li", "click", function( event ) {
			event.preventDefault();
			if ( $( this ).is( ".active" ) ) return true;
			var item = $( this ).data( "item" ),
				level = Number( $( this ).attr( "level" ) );
			
			$labels.eq( level ).val( item[ opts.itemLabel ] ).data( "value", item[ opts.itemValue ] );; 
			$values.eq( level ).val( item[ opts.itemValue ] );
			
			$( this ).parent().find( "li.active" ).removeClass( "active" );
			$( this ).addClass( "active" );

			var items;
			if ( opts.releSelect ) {
				for ( var index2 = level + 1; index2 < opts.showChildrenLevel; index2++ ) {
					items = item[ opts.itemChildren ] || [];
					if ( items.length == 0 && opts.reasonable == true ) {
						var tmp = {};
						tmp[ opts.itemChildren ] = [];
						if ( index2 == 1 ) {
							tmp[ opts.itemLabel ] = "其他市";
							tmp[ opts.itemValue ] = item[ opts.itemValue ].substring( 0, 2 ) + "9900";
						} else if ( index2 == 2 ) {
							tmp[ opts.itemLabel ] = "其他区";
							tmp[ opts.itemValue ] = item[ opts.itemValue ].substring( 0, 4 ) + "99";
						} 
						items.push( tmp );
					} 
					if ( items.length > 0 ) {
						that.__build__( index2, items );
						item = items[ 0 ];
						$labels.eq( index2 ).val( item[ opts.itemLabel ] ).data( "value", item[ opts.itemValue ] );
						$values.eq( index2 ).val( item[ opts.itemValue ] );
						$dropUls.eq( index2 ).find( "li:first" ).addClass( "active" );
					} else {
						for ( var index = index2; index < opts.showChildrenLevel; index++ ) {
							$dropBtns.eq( index ).addClass("disabled").attr( "disabled", "disabled" );
							$dropUls.eq( index ).empty();
							
							$labels.eq( index ).val( "-" ).data( "value", "" ); 
							$values.eq( index ).val( "" );
						}
						break;
					}
				}
				var values = [];
				for ( var index = 0; index < $labels.length; index++ ) {
					values.push( $labels.eq( index ).data( "value" ) || "" );
				}
				
				$input.val( values.join( opts.itemValueLimit ) );
				
			} else {
				items = item[ opts.itemChildren ] || [];
				if ( items.length == 0 && opts.reasonable == true ) {
					var tmp = {};
					tmp[ opts.itemChildren ] = [];
					if ( level == 0 ) {
						tmp[ opts.itemLabel ] = "其他市";
						tmp[ opts.itemValue ] = item[ opts.itemValue ].substring( 0, 2 ) + "9900";
					} else if ( level == 1 ) {
						tmp[ opts.itemLabel ] = "其他区";
						tmp[ opts.itemValue ] = item[ opts.itemValue ].substring( 0, 4 ) + "99";
					} 
					items.push( tmp );
				} 
				var index2 = level + 1;
				if ( items.length > 0 ) {
					that.__build__( index2, items );
					/*item = items[ 0 ];
					$labels.eq( index2 ).val( item[ opts.itemLabel ] ).data( "value", item[ opts.itemValue ] );
					$values.eq( index2 ).val( item[ opts.itemValue ] );
					$dropUls.eq( index2 ).find( "li:first" ).addClass( "active" );*/
				} else {
					for ( var index = index2; index < opts.showChildrenLevel; index++ ) {
						$dropBtns.eq( index ).addClass("disabled").attr( "disabled", "disabled" );
						$dropUls.eq( index ).empty();
						
						$labels.eq( index ).val( "-" ).data( "value", "" ); 
						$values.eq( index ).val( "" );
					}
				}
				
				if ( opts.requiredLevel <= level ) {
					//$input.val( item[ opts. ] );
					var values = [];
					for ( var index = 0; index < $labels.length; index++ ) {
						values.push( $labels.eq( index ).data( "value" ) || "" );
					}
					
					$input.val( values.join( opts.itemValueLimit ) );
				}
			}
		} );
		
		
		$input.bind( "CHANGE3", opts.events.change.data, opts.events.change.handler );
		
		$input.bind( "change2.app.ui.select.area", function( event, value ) {
			if ( !that.loaded ) { 
				$( this ).data( "value", value );
				return false;
			}
			
			
			this.__change_value__ = false;
			var values;
			if ( typeof value == "object" && value instanceof Array ) {
				values = value;
			} else {
				if ( typeof value != "string" ) {
					value = value.toString();
				}
				values = value.split( opts.itemValueLimit );
			} 
			
			for ( var index = 0; index < opts.showChildrenLevel; index++ ) {
				if ( index != 0 ) {
					$dropBtns.eq( index ).addClass("disabled").attr( "disabled", "disabled" );
					$dropUls.eq( index ).empty();
				}
				
				$labels.eq( index ).val( "" ).data( "value", "" ); 
				$values.eq( index ).val( "" );
			}
			
			if ( values.length == 0 ) {
				return;
			}
			
			var $li, item, items, index2;
			for( var index = 0; index < values.length; index++ ) {
				value = values[ index ];
				$li = $dropUls.eq( index ).find( format( "li[value=\"{0}\"]", [ value ] ) );
				if ( $li.length == 0 ) continue;
				$li.parent().find( "li.active" ).removeClass( "active" );
				$li.addClass( "active" );
				item = $li.data( "item" ) || {};
				$labels.eq( index ).val( item[ opts.itemLabel ] ).data( "value", item[ opts.itemValue ] );; 
				$values.eq( index ).val( item[ opts.itemValue ] );
				items = item[ opts.itemChildren ] || [];
				if ( items.length == 0 && opts.reasonable == true ) {
					var tmp = {};
					tmp[ opts.itemChildren ] = [];
					if ( index == 0 ) {
						tmp[ opts.itemLabel ] = "[其他市]";
						tmp[ opts.itemValue ] = item[ opts.itemValue ].substring( 0, 2 ) + "9900";
					} else if ( index == 1 ) {
						tmp[ opts.itemLabel ] = "[其他区]";
						tmp[ opts.itemValue ] = item[ opts.itemValue ].substring( 0, 4 ) + "99";
					} 
					items.push( tmp );
				} 
				if ( items.length > 0 ) {
					index2 = index + 1;
					that.__build__( index2, items );
					item = items[ 0 ];
					$labels.eq( index2 ).val( item[ opts.itemLabel ] ).data( "value", item[ opts.itemValue ] );
					$values.eq( index2 ).val( item[ opts.itemValue ] );
					$dropUls.eq( index2 ).find( "li:first" ).addClass( "active" );
				} else {
					for ( var index = index2; index < opts.showChildrenLevel; index++ ) {
						$dropBtns.eq( index ).addClass("disabled").attr( "disabled", "disabled" );
						$dropUls.eq( index ).empty();
						
						$labels.eq( index ).val( "-" ).data( "value", "" ); 
						$values.eq( index ).val( "" );
					}
					break;
				}
			}
			
		});
		
	};
	
	//jQuery扩展
	$.prototype.selectArea = function( options ) {
		
		return this.each( function() {
			if ( $( this ).is( ".sr-only, [type=hidden], .ui-select-area" ) ) {
				return true;
			}
			$( this ).addClass( "ui-select-area" ).addClass( "has-app-change" );
			
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
			
			$( this ).data( "selectArea", new Area( this, opts ) );
		} );
	};
	
	
	return Area;
} );
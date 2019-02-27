//###################################
/**
 * select.tab.area 下拉选项卡地区
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ 
          "app/base", 
          "app/ui/input", 
          "app/ui/code"
          ], function() {
	
	require( [ "css2!component/ztree/css/metroStyle/metroStyle" ] );
	//require( [ "css2!component/ztree/css/awesomeStyle/awesome.css" ] );
	
	var $ = require( "jquery" )
	, app = require( "app/base" )
	, Input = require( "app/ui/input" )
	, code = require( "app/ui/code" )
	, format = app.tools.format
	, uuid = app.tools.uuid
	, log = app.log;
	
	var __DEFAULT__ = {
		theme: "default",
		dropHeight: "150px",
		dropWidth: "400px",
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
        autoLoad: true,
		readonly: true,
		multi: false,
		itemLabel: "label",
		itemValue: "value",
		itemChildren: "children",
		itemValueLimit: ",", //multi 多选时，连接字符串
		itemOtherValues: [], // [ "name1:key1", "name2:key2", "name3:key3", ... ]
		itemLevelValues: false, //层次itemValue 是否存取， false 不存取， 数组存取，为数组三个值
		/*itemLevelValues: [ "prov", "city", "area" ],*/
		requiredLevel: 'all',
        showChildrenLevel: 3,
        showChildrenLabels: ["请选择省级", "请选择市级", "请选择区级"],
        valueIsLimit: false, //是否分割  false　代表只取最后一个值  true所有值用itemValueLimit来分割 
        //releSelect: false, //是否关联选择
        reasonable: true, //是否数据合理化， 添加其他市，其他区
		items: false,
		remote: false,
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
	
	function AreaTab( target, options ) {
		this.$input = $( target );
		var opts = this.opts = $.extend( true, {}, __DEFAULT__, options );
		
		if (opts.code) this.opts = opts = $.extend(true, opts, __DEFAULT_CODE__, options);
		
		if ( typeof opts.requiredLevel != "number" || opts.requiredLevel > opts.showChildrenLevel ) {
			opts.requiredLevel = opts.showChildrenLevel;
		} else if ( opts.requiredLevel < 1 ) {
			opts.requiredLevel = 1;
		}
		
		if ( typeof opts.id != "string" || opts.id.length == 0 ) opts.id = "SELECT_AREA_TAB_" + uuid();
		this.__init__();
	};
	
	AreaTab.prototype.__init__ = function() {
		var opts = this.opts
			, $input = this.$input;
		this.loaded = false;
		this.__change_value__ = false;
		
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
			
			$input.attr( "data-valid", ".select-area-tab-" + opts.theme );
		}
		
		var $inputGroup = $wrapper.addClass( "select-area-tab-" + opts.theme ).find( ".input-group" );
		
		if ( $inputGroup.length == 0 ) {
			$inputGroup = $show.wrap( '<div class="input-group"></div>' ).parent();
		}
		
		var $buttonGroup = $inputGroup.find( ".input-group-btn:not(.suffix-buttons-group)" ).show();
		if ( $buttonGroup.length == 0)
			$buttonGroup = $( format( '<div class="input-group-btn"></div>' ) ).insertAfter( $show );
		
		if ( typeof opts.dropDirection.vertical == "string" && opts.dropDirection.vertical == "up" ) {
			$buttonGroup.addClass( "dropup" );
			if ( typeof opts.dropButton.icon != "string" || opts.dropButton.icon.length == 0 ) {
				opts.dropButton.icon = "fa fa-caret-up";
			}
		} else {
			if ( typeof opts.dropButton.icon != "string" || opts.dropButton.icon.length == 0 ) {
				opts.dropButton.icon = "fa fa-caret-down";
			}
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
		
		var $dropContainer = this.$dropContainer = $( format( '<div class="dropdown-menu dropdown-menu-{0}" style="max-width:{1}"></div>', [ opts.dropDirection.horizontal, opts.dropWidth ] ) );
		$dropContainer.insertAfter( $drop );
		
		var htmls = [], active;
		for ( var index = 0; index < opts.showChildrenLevel; index++ ) {
			active = index == 0 ? "active" : "";
			htmls.push( format( "<li class=\"{0}\" style=\"display:none;\"><a href=\"javascript:;\">{1}</a></li>", [ active, opts.showChildrenLabels[ index ] ] ) );
		}
		var $tab = $( "<ul class=\"nav nav-tabs\">" + htmls.join( "" ) + "</ul>" ).appendTo( $dropContainer );
		var $tabContent = $( format( "<div class=\"nav-tab-content\" style=\"overflow:visible;\"></div>", [ opts.dropHeight ] ) ).appendTo( $dropContainer );
		
		var $tabs = this.$tabs = $tab.find( ">li" );
		
		htmls = [];
		for ( var index = 0; index < opts.showChildrenLevel; index++ ) {
			htmls.push( format( "<ul class=\"list-unstyled\" style=\"display:none;\"></ul>" ) );
		}
		var $tabUls = this.$tabUls = $( htmls.join( "" ) ).appendTo( $tabContent );
		
		var $others = this.$others = [], others = this.others = [], other;
		for ( var i = 0; i < opts.itemOtherValues.length; i++ ) {
			other = opts.itemOtherValues[ i ];
			if ( typeof other != "string" || other.length == 0 ) continue;
			other = other.split( ":" );
			if ( other.length == 1 ) {
				other[ 1 ] = other[ 0 ]; 
			}
			other = { name: other[ 0 ], key: other[ 1 ] };
			others.push( other );
			
			$other = $( format( '<input type="hidden" class="sr-only" name="{name}" />', other ) );
			
			$wrapper.prepend( $other );
			
			$others.push( $other );
		}
		
		this.__event__();
		
		if (!opts.autoLoad) return false;
		
		if (opts.items) this.__loadNative__(opts.items);
		else if (opts.code) this.__loadCode__();
	};
	
	//加载码表
	AreaTab.prototype.__loadCode__ = function () { 
		var that = this; opts = this.opts;
		this.loaded = false;
		//获取缓存数据通过回调函数
		code.getCacheByCallBack( opts.code.type, opts.code.group, function ( code ) {
			code = this.opts.code.callback( code );
			this.__loadNative__( code );
		}, that);
	};
	
	//加载本地静态数据
	AreaTab.prototype.__loadNative__ = function ( items ) {
		var opts = this.opts
			, $wrapper = this.$wrapper
			, $drop = this.$drop
			, $dropContainer = this.$dropContainer
			, $input = this.$input;
		
		if (!items instanceof Array) items = [items];
		
		this.__build__( 0, items );
		
		this.loaded = true, value = $input.data( "value" );
		if ( typeof value != "undefined" ) {
			$input.trigger( "change2.app.ui.select.area.tab", [ value ] );
		} else {
			var value = $input.val();
			if ( value.lenth != 0 ) {
				$input.trigger( "change2.app.ui.select.area.tab", [ value ] );
			} 
		}
		
	};
	
	//加载区域
	AreaTab.prototype.__build__ = function( level, items ) {
		var opts = this.opts
			, $tabs = this.$tabs
			, $tabUls = this.$tabUls;
		if ( level >= opts.showChildrenLevel ) {
			return;
		}
		
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
		
		for ( var index = 0; index < level; index++ ) {
			$tabs.eq( index ).removeClass( "active" );
			$tabUls.eq( index ).hide();
		}
		
		for ( var index = level + 1; index < opts.showChildrenLevel; index++ ) {
			$tabs.eq( index ).removeClass( "active" ).hide();
			$tabUls.eq( index ).empty().hide();
		}
		
		
		$tabs.eq( level ).addClass( "active" ).show();
		var title = opts.showChildrenLabels[ level ] || "请选择";
		$tabs.eq( level ).attr( "title", title ).find( "a" ).text( title );
		$tabUls.eq( level ).show();
		
		var $tabUl = $tabUls.eq( level ).empty();
		
		var item;
		for ( var index = 0; index < items.length; index++ ) {
			item = items[ index ];
			$( format( "<li value=\"{1}\" index=\"{0}\"><a href=\"javascript:;\">{2}</a></li>", [ level, item[ opts.itemValue ], item[ opts.itemLabel ] ] ) ).appendTo( $tabUl ).data( "item", item );
		}
		
		
	};
	
	
	AreaTab.prototype.__event__ = function() {
		var that = this
			, opts = this.opts
			, $input = this.$input
			, $show = this.$show
			, $drop = this.$drop
			, $dropContainer = this.$dropContainer
			, $tabs = this.$tabs
			, $tabUls = this.$tabUls
			, others = this.others
			, $others = this.$others;
		
		$dropContainer.bind( "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			return false;
		} ); 
		
		$tabs.bind( "click", function( event ) { 
			if ( $( this ).is( ".active" ) ) return false;
			var level = $tabs.filter( ".active" ).removeClass( "active" ).index();
			$tabUls.eq( level ).hide();
			
			level = $( this ).index();
			$( this ).addClass( "active" );
			$tabUls.eq( level ).show();
		} );
		
		$tabUls.delegate( "li", "click", function( event ) {
			var item = $( this ).data( "item" )
				, level = Number( $( this ).attr( "index" ) );
			
			if ( level + 1 >= opts.showChildrenLevel) {
				$drop.parent().removeClass( "open" );
			}
			
			if ( $( this ).is( ".active" ) ){
				if ( level + 1 < opts.showChildrenLevel ) {
					$tabs.eq( level + 1 ).trigger( "click" );
				}
			} else {
				
				$tabUls.eq( level ).find( ".active" ).removeClass( "active" );
				
				$( this ).addClass( "active" );
				//$tabs.eq( level ).find( "a" ).text( item[ opts.itemLabel ] );
				var title = item[ opts.itemLabel ];
				$tabs.eq( level ).attr( "title", title ).find( "a" ).text( title.length >= 8 ? title.substring( 0, 5 ) + "..." : title );
				var items = item[ opts.itemChildren ] || [];
				if ( items.length == 0  && opts.reasonable == true ) {
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
				if ( items.length > 0 ) {
					that.__build__( level + 1, items );
				} else {
					$drop.parent().removeClass( "open" );
				}
				
				var labels = [], values = [];
				items = [];
				$tabUls.find( ".active" ).each( function() {
					item = $( this ).data( "item" );
					labels.push( item[ opts.itemLabel ] );
					values.push( item[ opts.itemValue ] );
					items.push( item );
				} );
				
				$show.val( labels.join( "" ) );
				$input.val( "" );
				
				if ( level + 1 >= opts.requiredLevel || opts.reasonable == false ) {
					if ( opts.valueIsLimit == false ) {
						value = values[ values.length - 1 ] || "";
						$input.val( value );
					} else {
						$input.val( values.join( opts.itemValueLimit ) );
					}
				}
				
				$input.trigger( "CHANGE3", [ values, items ] );
			}
			
		} );
		
		
		$input.bind( "CHANGE3", opts.events.change.data, opts.events.change.handler );
		
		$input.bind( "change2.app.ui.select.area.tab", function( event, value ) { 
			if ( !that.loaded ) {
				$( this ).data( "value", value );
				return false;
			}
			
			if ( typeof value == "number" ){
				value += "";
			} else if ( typeof value != "string" ) {
				value = "";
			}
			
			this.__change_value__ = false;
			
			for ( var index = 0; index < opts.showChildrenLevel; index++ ) {
				if ( index == 0 ) {
					$tabs.eq( index ).addClass( "active" ).show();
					$tabUls.eq( index ).show().find( ".active" ).removeClass("active");
				} else {
					$tabs.eq( index ).removeClass( "active" ).hide();
					$tabUls.eq( index ).empty().hide();
				}
			}
			
			var values = [];
			
			if ( opts.valueIsLimit == false ) {
				if ( value.length == 6 ) {
					values.push( value );
					if ( value.substring( 2 ) == "0000" ) {
					} else if ( value.substring( 4 ) == "00" ) {
						values.unshift( value.substring( 0, 2 ) + "0000" );
					} else {
						values.unshift( value.substring( 0, 4 ) + "00" );
						values.unshift( value.substring( 0, 2 ) + "0000" );
					}			
				}
			} else {
				values = value.split( opts.itemValueLimit );
			}
			
			
			var labels = [], items2 = [], values2 = [];
			var $li, item, items=[];
			for ( var index = 0; index < values.length; index++ ) {
				value = values[ index ];
				$li = $tabUls.eq( index ).find( format( "li[value=\"{0}\"]", [ value ] ) );
				if ( $li.length != 1 ) {
					break;
				}
				
				item = $li.data( "item" ); 
				items2.push( item );
				values2.push( value );
				$li.addClass( "active" );
				items = item[ opts.itemChildren ] || [];
				if ( items.length == 0  && opts.reasonable == true ) {
					var tmp = {};
					tmp[ opts.itemChildren ] = [];
					if ( index == 0 ) {
						tmp[ opts.itemLabel ] = "其他市";
						tmp[ opts.itemValue ] = item[ opts.itemValue ].substring( 0, 2 ) + "9900";
					} else if ( index == 1 ) {
						tmp[ opts.itemLabel ] = "其他区";
						tmp[ opts.itemValue ] = item[ opts.itemValue ].substring( 0, 4 ) + "99";
					} 
					items.push( tmp );
				} 
				
				var title = item[ opts.itemLabel ];
				labels.push( title );
				$tabs.eq( index ).attr( "title", title ).find( "a" ).text( title.length >= 8 ? title.substring( 0, 5 ) + "..." : title );
				
				if ( items.length > 0 ) {
					that.__build__( index + 1, items );
				}
				
			}
			
			
			$show.val( labels.join( "" ) ); 
			if ( opts.valueIsLimit == false ) {
				value = values2[ values2.length - 1 ] || "";
				$input.val( value );
			} else {
				$input.val( values2.join( opts.itemValueLimit ) );
			}
			
			$input.trigger( "CHANGE3", [ values2, items2 ] );
			
			return false;
		} );
	};
	
	
	//jQuery扩展
	$.prototype.selectAreaTab = function( options ) {
		
		return this.each( function() {
			if ( $( this ).is( ".sr-only, [type=hidden], .ui-select-area-tab" ) ) {
				return true;
			}
			$( this ).addClass( "ui-select-area-tab" ).addClass( "has-app-change" );
			
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
			
			$( this ).data( "selectAreaTab", new AreaTab( this, opts ) );
		} );
	};
	
	return AreaTab;
	
} );
//###################################
/**
 * select.tree 下拉
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base", "app/ui/input", "app/ui/code" ], function() {
	var $ = require( "jquery" )
	, app = require( "app/base" )
	, Input = require( "app/ui/input" )
	, code = require( "app/ui/code" )
	, format = app.tools.format
	, uuid = app.tools.uuid
	, log = app.log;
	
	var __DEFAULT__ = {
		theme: "default",
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
		itemValueLimit: ",", //multi 多选时，连接字符串
		itemOtherValues: [],
		items: false,
		remote: false,
		code: false,
		root: 'list',
		events: {
			change: {
				data: {},
				handler: function (event, val, item) {
					//单选参数 event, val, item,
					//多选参数 event, vals, items
					//console.log(arguments);
				}
			}
		}
	};
	
	//ajax配置 远程配置
	var __DEFAULT_AJAX__ =  {
		remote: {
			url: false,
			params: '',
			type: 'POST',
			dataType: 'json',
			error: function () {},
			callback: function (data) {
				return data;
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
	
	function Select( target, options ) {
		this.$input = $( target );
		var opts = this.opts = $.extend( true, {}, __DEFAULT__, options );
		
		if (opts.code) this.opts = opts = $.extend(true, opts, __DEFAULT_CODE__, options);
		else if (opts.remote) this.opts = opts = $.extend(true, opts, __DEFAULT_AJAX__, options);
		
		if ( typeof opts.id != "string" || opts.id.length == 0 ) opts.id = "SELECT_" + uuid();
		this.__init__();
	}
	
	Select.prototype.__init__ = function() {
		var opts = this.opts
			, $input = this.$input;
		
		this.loaded = false;
		
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
			$drop = $( format( '<button class="{css} dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="true" aria-haspopup="true"><i class="{icon}"></i> {text}</button>', opts.dropButton ) );
		} else {
			$drop = $( format( '<button id="{id}" class="{css}" type="button" style="{style}">{text}</button>', opts.dropButton ) );
		}
		
		$buttonGroup.append( $drop );
		
		this.$drop = $drop;
		
		var $dropUl = this.$dropUl = $( format( '<ul class="dropdown-menu dropdown-menu-{0}" style="overflow: auto; max-height:{1};"></ul>', [ opts.dropDirection.horizontal, opts.dropHeight ] ) );
		$dropUl.insertAfter( $drop );
		
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
		else if (opts.remote) this.__loadAjax__();
		
	};
	
	//加载码表
	Select.prototype.__loadCode__ = function () {
		var that = this; opts = this.opts;
		this.loaded = false;
		//获取缓存数据通过回调函数
		code.getCacheByCallBack( opts.code.type, opts.code.group, function ( code ) {
			code = this.opts.code.callback( code );
			this.__loadNative__( code );
		}, that);
	};
	
	//ajax远程加载
	Select.prototype.__loadAjax__ = function () {
		var that = this; opts = this.opts;
		this.loaded = false;
		$.ajax({
			url: opts.remote.url,
			data: opts.remote.params,
			type: opts.remote.type,
			error: opts.remote.error,
			success: function (data) {
				var opts = that.opts;
				data = opts.remote.callback(data);
				if (typeof data != 'object') return;
				if (!(data instanceof Array)) { data = data[opts.root];}
				items = data || [];
				that.__loadNative__(items);
			},
			dataType: opts.remote.dataType
		});
	};
	
	//加载本地静态数据
	Select.prototype.__loadNative__ = function ( items ) {
		var opts = this.opts
			, $wrapper = this.$wrapper
			, $drop = this.$drop
			, $input = this.$input;
		
		if (!items instanceof Array) items = [items];
		
		var htmls = [], tmp;
		$.each(items, function (index, item) {
			if ( typeof item != "object" ) {
				tmp = item;
				items[ index ] = item = {};
				item[ opts.itemLabel ] = item[ opts.itemValue ] = tmp;
			}
			if ( opts.multi == false ) {
				htmls.push( format( '<li value="{0}"><a href="javascript:;"><i class="fa fa-radio"></i> {1}</a></li>', [ item[ opts.itemValue ], item[ opts.itemLabel ] ]));
			} else {
				htmls.push( format( '<li value="{0}"><a href="javascript:;"><i class="fa fa-checkbox"></i> {1}</a></li>', [ item[ opts.itemValue ], item[ opts.itemLabel ] ]));
			}
		});
		
		this.$dropUl.append( htmls.join( '' ) );
		//var $dropUl = this.$dropUl = $( format( '<ul class="dropdown-menu dropdown-menu-{0}">{1}</ul>', [ opts.dropDirection.horizontal, htmls.join( '' ) ] ) ).insertAfter( $drop );
		this.$dropUl.find( "> li" ).each( function( index ) {
			$( this ).data( "item", items[ index ] );
		} );
		
		this.loaded = true;
		
		var value = $input.data( "value" );
		
		if (typeof value != "undefined") {  
			$input.trigger( "change2.app.ui.select", [ value ] );
		} else {
			value = $input.val();
			if ( value.length > 0 ) {
				$input.trigger( "change2.app.ui.select", [ value ] );
			}
		}
	};
	
	Select.prototype.__event__ = function() {
		var that = this
			, opts = this.opts
			, $input = this.$input
			, $show = this.$show
			, $dropUl = this.$dropUl
			, others = this.others
			, $others = this.$others;
		
		
		//click
		$dropUl.delegate( "li", "click", function( event ) {
			
			if ( !opts.multi ) {
				var checked = $( this ).is( ".active" )
					, item;

				if ( !checked ) {
					$( this ).parent().find( "li.active" ).removeClass( "active" );
					$( this ).addClass( "active" );
					item = $( this ).data( "item" );
				} else {
					$( this ).removeClass( "active" );
					item = {};
				}
				$show.val( item[ opts.itemLabel ] || "" );
				$input.val( item[ opts.itemValue ] || "" );
				for( var i = 0; i < others.length; i++ ) {
					$others[ i ].val( item[ others[ i ].key ] || "" );
				}
				//opts.event.change( event, item[ opts.itemValue ], item );
				if ( checked ) {
					$input.trigger( "CHANGE3" );
				} else {
					$input.trigger( "CHANGE3", [ item[ opts.itemValue ], item ] );
				}
				
				return true;
			}
			
			//多选
			$( this ).toggleClass( "active" );
			
			var items = []
				, labels = []
				, values = []
				, item;
			
			$( this ).parent().find( "li.active" ).each( function( index ) {
				var item = $( this ).data( 'item' );
				items.push( item );
				labels.push( item[ opts.itemLabel ] );
				values.push( item[ opts.itemValue ] );
			} );
			
			$show.val( labels.join( opts.itemValueLimit ) );
			$input.val( values.join( opts.itemValueLimit ) );
			
			var otherVals, key;
			for( var i = 0; i < others.length; i++ ) {
				otherVals = [];
				key = others[ i ].key;
				for( var j = 0; j < items.length; j++ ) {
					item = items[ j ];
					otherVals.push( item[ key ] || "" );
				}
				$others[ i ].val( otherVals.join( opts.itemValueLimit ) );
			}
			
			//opts.events.change( event, values, items );
			
			$input.trigger( "CHANGE3", [ values, items ] );
			
			return false;
			
		} );
		
		$input.bind( "CHANGE3", opts.events.change.data, opts.events.change.handler );
		
		$input.bind( "change2.app.ui.select", function( event, value ) {
			if ( !that.loaded ) {
				$( this ).data( "value", value );
				return false;
			}
			that.__change__( value );
			return false;
		} );
	};
	
	Select.prototype.__change__ = function( value ) {
		var opts = this.opts
			, $input = this.$input
			, $show = this.$show
			, $dropUl = this.$dropUl
			, $others = this.$others
			, others = this.others;
		
		if ( typeof value != "string" ) value = "";
		
		$dropUl.find( "li.active" ).removeClass( "active" );
		
		if ( value.length > 0 ) {
			if ( opts.multi ) {
				var values = value.split( opts.itemValueLimit )
					, vals = []
					, labels = []
					, items = []
					, val
					, item;
				for ( var index = 0; index < values.length; index++ ) {
					val = values[ index ];
					item = $dropUl.find( format( "li[value=\"{0}\"]", [ val ] ) ).addClass( "active" ).data( "item" );
					if ( typeof item == "undefined" ) {
						
						if ( opts.readonly ) {
							continue;
						} 
						
						item = {};
						item[ opts.itemLabel ] = item[ opts.itemValue ] = val;
					}
					vals.push( val );
					labels.push( item[ opts.itemLabel ] );
					items.push( item );
				}
				
				var otherVals, key, node;
				for( var i = 0; i < others.length; i++ ) {
					otherVals = [];
					key = others[ i ].key;
					for( var j = 0; j < nodes.length; j++ ) {
						node = nodes[ j ];
						otherVals.push( node[ key ] || "" );
					}
					$others[ i ].val( otherVals.join( opts.itemValueLimit ) );
				}
				
				$input.val( vals.join( opts.itemValueLimit ) );
				$show.val( labels.join( opts.itemValueLimit ) );
				
				$input.trigger( "CHANGE3", [ vals, items ] );
				
				return true;
			} else {
				var $li = $dropUl.find( format( "li[value=\"{0}\"]", [ value ] ) );
				if ( $li.length > 0 ) {
					$li.trigger( "click" );
					return false;
				} else if ( opts.readonly == false ) { 
					item = {};
					item[ opts.itemLabel ] = item[ opts.itemValue ] = value;
					
					$input.val( value );
					$show.val( value );
					
					$input.trigger( "CHANGE3", [ value, item ] );
				}
			}
		}
		
		//只读，不可输入无关联值
		if ( opts.readonly ) {
			value  = "";
		}
		
		$input.val( value );
		$show.val( value );
		for( var i = 0; i < others.length; i++ ) {
			$others[ i ].val( value );
		}
		
		if ( opts.multi )  {
			$input.trigger( "CHANGE3", [ [], [] ] );
		} else {
			$input.trigger( "CHANGE3" );
		}
		
		return false;
	};
	
	//jQuery扩展
	$.prototype.select = function( options ) {
		
		return $( this ).each( function() {
			if ( $( this ).is( ".sr-only, [type=hidden], .ui-select" ) ) {
				return true;
			}
			$( this ).addClass( "ui-select" ).addClass( "has-app-change" );
			
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
			
			$( this ).data( "select", new Select( this, opts ) );
		} );
	};
	
	return Select;

} );
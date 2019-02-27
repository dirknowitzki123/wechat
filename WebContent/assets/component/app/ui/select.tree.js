//###################################
/**
 * select.tree 下拉树
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ 
  "app/base", 
  "app/ui/input", 
  "ztree.all",
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
		itemId: "id",
		itemPId: "pId",
		itemRId: null,
		itemValueLimit: ",", //multi 多选时，连接字符串
		itemOtherValues: [], // [ "name1:key1", "name2:key2", "name3:key3", ... ]
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
	
	//ajax配置 远程配置
	var __DEFAULT_AJAX__ =  {
		remote: {
			url: false,
			params: {},
			type: 'POST',
			dataType: 'json',
			error: function () {},
			callback: function (data) {
				return data;
			}
		}
	};
	
	function Tree( target, options ) {
		this.$input = $( target );
		var opts = this.opts = $.extend( true, {}, __DEFAULT__, options );
		
		if (opts.remote) this.opts = opts = $.extend(true, opts, __DEFAULT_AJAX__, options);
		
		if ( typeof opts.id != "string" || opts.id.length == 0 ) opts.id = "SELECT_TREE_" + uuid();
		this.__init__();
	};
	
	Tree.prototype.__init__ = function() {
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
			
			$input.attr( "data-valid", ".select-tree-" + opts.theme );
		}
		
		var $inputGroup = $wrapper.addClass( "select-tree-" + opts.theme ).find( ".input-group" );
		
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
		
		var $dropUl = this.$dropUl = $( format( '<ul class="dropdown-menu dropdown-menu-{0} ztree" style="max-height:{2}" id="ZTREE_{1}"></ul>', [ opts.dropDirection.horizontal, opts.id, opts.dropHeight ] ) );
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
		else if (opts.remote) this.__loadAjax__();
	};
	
	//ajax远程加载
	Tree.prototype.__loadAjax__ = function () {
		var that = this; opts = this.opts;
		opts.__loaded__ = false;
		$.ajax({
			url: opts.remote.url,
			data: opts.remote.data,
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
	Tree.prototype.__loadNative__ = function ( items ) {
		var opts = this.opts
			, $wrapper = this.$wrapper
			, $dropUl = this.$dropUl
			, $input = this.$input;
		
		if (!items instanceof Array) items = [items];
		
		var config = {
			data: {
				key: {
					name: opts.itemLabel,
					title: opts.itemLabel
				},
				simpleData: {
					enable: true,
					idKey: opts.itemId,
					pIdKey: opts.itemPId,
					rootPId: opts.itemRId
				}
			},
			check: {
				enable: true,
				chkStyle: opts.multi ? "checkbox" : "radio"
			},
			callback: {
				onClick: function( event, treeId, treeNode ) {
					event.preventDefault();
					event.stopPropagation();
					ztree.checkNode( treeNode );
					return false;
				},
				onCheck: function( event, treeId, treeNode ) {
					event.preventDefault();
					event.stopPropagation();
					return false;
				},
				onExpand: function( event, treeId, treeNode ) {
					event.preventDefault();
					event.stopPropagation();
					return false;
				},
				onCollapse:function( event, treeId, treeNode ) {
					event.preventDefault();
					event.stopPropagation();
					return false;
				},
			}
		};
		var ztree = $.fn.zTree.init($dropUl, config, items);
		
		$input.data( "ztree", ztree );
		
		this.loaded = true, value = $input.data( "value" );
		if ( typeof value != "undefined" ) {
			$input.trigger( "change2.app.ui.select.tree", [ value ] );
		} else {
			var value = $input.val();
			if ( value.lenth != 0 ) {
				$input.trigger( "change2.app.ui.select.tree", [ value ] );
			} 
		}
		
		/*config.async = opts.remote;
		config.async.enable = true;
		
		console.log( config.async );
		
		this.ztree = $.fn.zTree.init($dropUl, config);*/
		
	};
	
	
	Tree.prototype.__event__ = function() {
		var that = this
			, opts = this.opts
			, $input = this.$input
			, $show = this.$show
			, $dropUl = this.$dropUl
			, others = this.others
			, $others = this.$others;
		
		
		$dropUl.delegate( "li > span.switch", "click", function( event ) { 
			event.preventDefault();
			event.stopPropagation();
			$( this ).parent().find( " > a" ).trigger( "dblclick" );
			return false;
		} );
		
		$dropUl.delegate( "li > span.chk", "click", function( event ) { 
			event.preventDefault();
			event.stopPropagation();
			var treeId = $( this ).attr( "id" ).replace( "_check", "" );
			$input.trigger( "SELECT_NODE", [ treeId ] );
			return false;
		} );

		$dropUl.delegate( "li > a", "click", function( event ) { 
			event.preventDefault();
			event.stopPropagation();
			var treeId = $( this ).attr( "id" ).replace( "_a", "" );
			$input.trigger( "SELECT_NODE", [ treeId ] );
			return false;
		} );
		
		$dropUl.delegate( "li", "click", function( event ) { 
			event.preventDefault();
			event.stopPropagation();
			return false;
		} );
		
		$input.bind( "SELECT_NODE", function( event, treeId ) {
			var ztree = $( this ).data( "ztree" );
			var node = ztree.getNodeByTId( treeId );
			if ( !opts.multi ) {
				
				var checked = node.checked;
				if ( !checked ) {
					ztree.checkNode( node );
					ztree.selectNode( node );
				} else {
					ztree.checkNode( node, false );
					ztree.cancelSelectedNode( node );
					node = {};
				}
				
				$input.val( node[ opts.itemValue ] || "" );
				if ( opts.readonly ) {
					$show.val( node[ opts.itemLabel ] || "" );
				}
				for( var i = 0; i < others.length; i++ ) {
					$others[ i ].val( node[ others[ i ].key ] || "" );
				}
				
				checked = node.checked;
				if ( checked ) {
					$input.trigger( "CHANGE3", [ node[ opts.itemValue ], node ] );
				} else {
					$input.trigger( "CHANGE3" );
				}
				
				return true;
			} 
			
			//多选情况
			
			if ( node.checked ) {
				ztree.checkNode( node, false );
				ztree.cancelSelectedNode( node );
			} else {
				ztree.checkNode( node, true );
				ztree.selectNode( node, true );
			}
			
			
			var nodes = ztree.getCheckedNodes( true ),
				vals = [], labels = [];
			//getSelectedNodes
			for( var i = 0; i < nodes.length; i++ ) {
				vals.push( nodes[ i ][ opts.itemValue ] );
				labels.push( nodes[ i ][ opts.itemLabel ] );
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
			if ( opts.readonly ) {
				$show.val( labels.join( opts.itemValueLimit ) );
			}
			$input.trigger( "CHANGE3", [ vals, nodes ] );
			
		} );
		
		$input.bind( "CHANGE3", opts.events.change.data, opts.events.change.handler );
		
		$input.bind( "change2.app.ui.select.tree", function( event, value ) {
			if ( !that.loaded ) {
				$( this ).data( "value", value );
				return false;
			}
			
			this.__change_value__ = false;
			
			
			var ztree = $( this ).data( "ztree" );
			
			if ( typeof value != "string" || value.length == 0 ) value = false;
			
			var nodes, node;
			
			nodes = ztree.getSelectedNodes();
			for ( var i = 0; i< nodes.length; i++ ) {
				ztree.checkNode( node, false );
				ztree.cancelSelectedNode( node );
			}
			
			nodes = ztree.getCheckedNodes( true );
			for ( var i = 0; i< nodes.length; i++ ) {
				ztree.checkNode( node, false );
			}
			
			if ( value === false ) {
				if ( opts.multi == false ) {
					$input.trigger( "CHANGE3" );
				} else {
					$input.trigger( "CHANGE3", [ [], [] ] );
				}
				return false;
			}
			
			if ( opts.multi == false ) {
				nodes = ztree.getNodesByParam( opts.itemValue, value );
				if ( nodes.length > 0 ) {
					node = nodes[ 0 ];
					ztree.checkNode( node );
					ztree.selectNode( node );
				} else { 
					node = {};
				}
				$input.val( node[ opts.itemValue ] || "" );
				if ( opts.readonly ) {
					$show.val( node[ opts.itemLabel ] || "" );
				}
				for( var i = 0; i < others.length; i++ ) {
					$others[ i ].val( node[ others[ i ].key ] || "" );
				}
				
				$input.trigger( "CHANGE3", [ node[ opts.itemValue ], node ] );
				
				return false;
			}
			
			var vals = value.split( "," ), nodes = [];
			for( var i = 0; i < vals.length; i++ ) {
				node = ztree.getNodesByParam( opts.itemValue, vals[ i ] ); //node is array ==> nodes
				if ( node.length == 0 ) continue;
				node = node[ 0 ];
				ztree.checkNode( node, true );
				ztree.selectNode( node, true );
				nodes.push( node );
			}
			
			var vals = [], labels = [];
			//getSelectedNodes
			for( var i = 0; i < nodes.length; i++ ) {
				vals.push( nodes[ i ][ opts.itemValue ] );
				labels.push( nodes[ i ][ opts.itemLabel ] );
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
			if ( opts.readonly ) {
				$show.val( labels.join( opts.itemValueLimit ) );
			}			
			
			$input.trigger( "CHANGE3", [ vals, nodes ] );
			
			return false;
		} );
		
		
		
		
	};
	
	
	//jQuery扩展
	$.prototype.selectTree = function( options ) {
		
		return this.each( function() {
			if ( $( this ).is( ".sr-only, [type=hidden], .ui-select-tree" ) ) {
				return true;
			}
			$( this ).addClass( "ui-select-tree" ).addClass( "has-app-change" );
			
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
			
			$( this ).data( "selectTree", new Tree( this, opts ) );
		} );
	};
	
	return Tree;
	
} );
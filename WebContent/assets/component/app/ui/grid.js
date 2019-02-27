//###################################
/**
 * grid 列表
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base", "app/ui/grid.page", "app/ui/grid.query" ] , function () {
	require( [ "css2!app/ui/css/grid" ] );
	
	var $ = require( "jquery" )
		, app = require( "app/base" )
		, format = app.tools.format
		, uuid = app.tools.uuid
		, log = app.log;
	
	function Grid( options, target ) {
		this.opts = opts = $.extend( true, {}, this.__DEFAULT__, options );
		this.$table = $table = $( target );
		this.__init__();
	}
	
	Grid.prototype.__QUERY__ = {
    	 target: "", //目标对象， jQuery所有选择器、jQuery对象(推荐)， element元素对象
    	 btnGroupTemplate: '<div class="pull-right"></div>',
    	 queryBtnTempalte: '<button id="queryBtn" class="btn btn-default"><i class="fa fa-search"></i> 查询</button>',
 		 resetBtnTempalte: '<button id="resetBtn" class="btn btn-default"><i class="fa fa-repeat"></i> 重置</button>',
 		 toggleBtnTempalte: '<button id="toggleBtn" class="btn btn-default" id="visibleBtn"><i class="fa fa-angle-double-down"></i> <span>展开</span></button>',
 		 queryBtnDisabled: false,
 		 resetBtnDisabled: false,
 		 toggleBtnDisabled: false
     };
	
	Grid.prototype.__PAGE__ = {};
	
	
	Grid.prototype.__DEFAULT__ = {
		 id: "",
		 css: "",
		 style: "",
		 theme: 'default',
		 cols: [],
		 autoLoad: true,
         remote: {
        	 url: '',
        	 async: true,
             cache: false,
             method: 'POST',
             params: {},
             callback: function (data) {
            	 return data;
             },
             dataType: 'json'
         },
         items: false,
         root: "list",
         multi: true,
         checkCol: true,
         indexCol: true,
         nextCol: false,
         indexColWidth: '60px',
         defaultColWidth: '100px',
         noDataTipHtml: '&nbsp;', 
         height: 'auto',
         nowrap : false, //内容不折行
         page: false, 
         query: false, 
         sort: false, //默认是否排序 针对没有配置的列
         sortKey: "order",//remote 排序key
         sortDesckey: "orderDesc", //remote 排序方向key
         sortPrefix: "", //排序字段前缀  如: 后台排序需加上别名t.  最后排序字段order t.is_enable
         sortHump: true,//是否是驼峰命名法 true时大写字母转下划线  //只对远程remote数据源奇效   isEnable ==> is_enable
         sortDesc: "asc",//默认升序排序
         plugins: [],
         events: {
        	 loaded: {
        		data: false,
        		 handler: function (event, items) {
        		 }
        	 },
        	 change: {
        		 data: '',
        		 handler: function (event, items, rowIndexs) {
        			 //单选 参数为 event, item, rowIndex
        			 //多选 参数为event, items, rowIndexs
        		 }
        	 },
        	 click: {
        		 data: '',
        		 handler: function (event, item, rowIndex) {
        		 }
        	 }
         },
         customEvents: []
         /*//自定义事件 码组管理
		 config.customEvents.push( {
			target: ".open-group-manager", //目标 选择器
			//data: "" //参数 非必须
			handler: function( event, item, rowIndex ) {
				//event.data 获取参数
				that.handlers.openGroupManger( item );
				return false;
			}
		 } );*/
			
	};
	Grid.prototype.__init__ = function() { 
		var opts = this.opts, $table = this.$table;
		
		var $grid = this.$grid = $( format( '<div class="grid grid-theme-{theme} {css}" style="{style}" id="{id}"></div>', opts ) ).insertAfter( $table );
		var $loading = this.$loading = $( '<div class="grid-loadding"><div class="grid-spin"><i class="fa fa-spin fa-spinner fa-lg"></i> 数据加载中...<div></div>' ).appendTo( $grid );
		$grid.append( $table );
		
		$table.addClass( "table-caption" );
		
		var $tableData = this.$tableData = $( format( '<table class="table table-hover table-responsive table-data"></table>' ) ).appendTo( $grid );
		var $tableDataContainer = this.$tableDataContainer = $tableData.wrap( '<div class="table-data-container"></div>' ).parent();
		//$table.wrap( '<div class="source-table">' );
		
		//$table = this.$table = $();
		
		var $tableCaption = this.$tableCaption = this.$table;
		$table = this.$table = $tableData;
		
		
		var cols = opts.cols;
		if ( typeof cols == 'undefined' || !cols instanceof Array ) return log.error($table.selector + " grid config: \"options.cols\" must is a array.");
		
		//选中项
		if ( opts.checkCol ) {
             cols.unshift( {
            	 //title: opts.multi ? '<input type="checkbox" class="checkall" >' : '<input type="checkbox" disabled="disabled" class="checkall">', 
            	 title: opts.multi ? '<i class="fa fa-square-o checkall"></i>' : '<i class="fa fa-square-o disabled"></i>', 
            	 name: "", 
            	 width: "30px", 
            	 align: 'center', 
            	 lockWidth: true, 
            	 checkCol: true, 
            	 renderer: function (val) {
            		 //return '<input type="checkbox" class="grid-ui-check">';
            		 return '<i class="fa fa-square-o grid-ui-check"></i>';
            	 }
             } );
		}
		
		//索引
		if( opts.indexCol ) {
            cols.unshift( {
        		title: '#',
        		name: "", 
        		width: opts.indexColWidth, 
        		align: 'center',
        		lockWidth: true, 
        		indexCol:true, 
        		renderer: function (val, item, rowIndex) {
        			return '<span class="grid-ui-index">' + (rowIndex+1) + '</span>';
        		}
            } );
        }
		//下级
		if ( opts.nextCol ) {
			  cols.unshift( {
	        	 //title: opts.multi ? '<input type="checkbox" class="checkall" >' : '<input type="checkbox" disabled="disabled" class="checkall">', 
	        	 title: '<i class="fa fa-plus-square"></i>', 
	        	 name: "", 
	        	 width: "25px", 
	        	 align: 'center', 
	        	 lockWidth: true, 
	        	 checkCol: true, 
	        	 renderer: function (val) {
	        		 //return '<input type="checkbox" class="grid-ui-check">';
	        		 return '<i class="fa fa-plus-square grid-ui-next"></i>';
	        	 }
	         } );
		}
		
		
		
		if ( typeof opts.sort != "boolean" ) {
			opts.sort = true;
		}
		if ( opts.sort ) {
			if ( typeof opts.sortPrefix != "string" ) {
				opts.sortPrefix = "";
			}
			if ( typeof opts.sortDesc != "string" || $.inArray( opts.sortDesc.toLowerCase() , [ "asc", "desc" ] ) == -1 ) {
				opts.sortDesc = "asc";
			}
		}
		var isremote = false;
		if ( typeof opts.remote != "object" ){
			opts.sortHump = false;
			isremote = true;
		}
		
		
		//生成头部
		var theads = [], isSort, sort, hump, hasSort, width;
		$.each( cols, function( index, col ) {
			isSort = false; sort = "", hump = false, hasSort = "";
			width = col.width || opts.defaultColWidth;
			width = width.replace( /[^0-9]/g, "" );
			if ( opts.sort ) {
				if ( ( typeof col.sort == "undefined" )
						|| ( typeof col.sort == "string" && col.sort.length == 0 ) 
						|| ( typeof col.sort == "boolean" && col.sort ) ) {
					isSort = typeof col.name == "string" && col.name.length != 0;
					if ( isSort ) {
						sort = col.name;
					}
				} else if ( typeof col.sort == "string" && col.sort.length != 0 ) {
					isSort = true;
					sort = col.sort;
				}
				
				if ( isSort ) {
					
					width = Number( width ) + 10;
					
					if ( !isremote ) {
						if ( typeof col.sortHump == "boolean" ) {
							hump = col.sortHump;
						} else {
							hump = opts.sortHump;
						}
					}
					
					if ( hump ) {
						sort = hump2underline( sort );
					}
					
					if ( typeof col.sortPrefix == "string" && col.sortPrefix.length != 0 ) {
						sort = col.sortPrefix + sort;
					} else {
						sort = opts.sortPrefix + sort;
					}
					
					hasSort = "has-sort";
					
					//是否是默认排序字段
					if ( typeof col.sortDesc == "string" && $.inArray( col.sortDesc.toLowerCase() , [ "asc", "desc" ] ) != -1 ) {
						hasSort += " " + opts.sortDesc || "";
					}
				}
			}
			width += "px";
			theads.push( format( '<th nowrap="nowrap" style="min-width: {width}; {locakWidth}; text-align: {align}; {style}" class="{css} {hasSort}" data-sort="{sort}" data-min-width="{minWidth}" ><span>{title}</span><i class="fa fa-sort sort-icon"></i></th>', {
				title: col.title,
				css: col.css || '',
				align: col.align || 'left',
				style: col.style || '',
				width: width ,
				locakWidth: Boolean(col.lockWidth) ? format( 'width: {0}; max-width: {0};', [ width ] ) : '',
			    minWidth: ( Boolean(col.lockWidth) ? width : opts.defaultColWidth ).replace( /[^0-9]/g, "" ),
			    hasSort: hasSort || "",
				sort: sort
			} ) );
			
			
		} );
		var $thead = this.$thead = $( format( "<thead>{0}</thead>", [ theads.join( "" ) ] ) ).appendTo( $table );
		var $ths = this.$ths = $thead.find( "th" );
		
		
		if ( opts.page !== false ){
			opts.page = $.extend( true, {}, this.__PAGE__, opts.page );
			var $tableTfoot = this.$tableTfoot = $( format( "<table class=\"table-tfoot\"><tfoot><tr><td colspan=\"{0}\"></td></tr></tfoot></table>", [ cols.length ] ) ).appendTo( $grid );
			opts.plugins.push( $tableTfoot.find( "td" ).page( opts.page ) );
		} 
		
		if ( opts.query !== false ) {
			opts.query = $.extend( true, {}, this.__QUERY__, opts.query );
			var $caption = $tableCaption.find( "caption" );
			if ( $caption.length == 0 ) {
				$caption = $( "<caption></caption>" ).appendTo( $tableCaption );
			}
		
			var query = opts.query;
			var $query = $( query.target );
			if ( $query.length == 0 ) {
				return log.error( "JS: Grid.Query 注册插件<br>没有找到查询表单" );
			}
			
			var $groupBtn = $( query.btnGroupTemplate );
			
			if ( !query.queryBtnDisabled ) {
				$groupBtn.append( query.queryBtnTempalte );
			}
			if ( !query.resetBtnDisabled ) {
				$groupBtn.append( query.resetBtnTempalte );
			}
			if ( !query.toggleBtnDisabled ) {
				$groupBtn.append( query.toggleBtnTempalte );
			}
			$groupBtn.appendTo( $caption );
			query.btnGroupContext = $groupBtn;
			opts.plugins.push( $query.query( query ) );
		}
		
		var grid = this;
		$.each( opts.plugins, function( index, plugin ) {
			if ( typeof plugin.register == "function" ) plugin.register( grid );
		} );
		
		var $caption =  $tableCaption.find( "caption" );
		if ( $caption.length == 0 || $caption.is( ":empty" ) ) {
			$caption.hide();
		}
		
		this.__event__();
		
		
		if (!opts.autoLoad) return;
		
		if (opts.items) this.__loadNative__();
		else if (opts.remote) this.__loadAjax__();
		
	};
	
	Grid.prototype.__loadAjax__ = function( params ) {
		var grid = this, opts = this.opts;
		var __params__ = $( this ).data( '__params__' ); 
		
		
		if ( typeof __params__ != "object" ) {
			__params__ = {};
			$.each( opts.plugins, function ( index, plugin ) {
				if ( plugin && typeof plugin.params == 'function' ) __params__ = $.extend( true, __params__, plugin.params() );
			});
		} 
		
		
		if ( typeof opts.remote.params == "function" ) __params__ = $.extend( true, __params__, opts.remote.params(), params );
		if ( typeof opts.remote.params == "object" ) __params__ = $.extend( true, __params__, opts.remote.params, params );
		else __params__ = $.extend( true, __params__, params );
		
		$( this ).data( '__params__', __params__ );
		
		this.$loading.show();
		
		$.ajax( {
			url: opts.remote.url,
			method: opts.remote.method,
			data: __params__,
			cache: opts.remote.cache,
			async : opts.remote.async,
			error: function() {
				grid.$loading.fadeOut();
			},
			success: function ( data ) {
				var data = opts.remote.callback( data );
				if ( typeof data != 'object' ) return;
				var items = data;		
				if ( !( items instanceof Array ) ) items = data[ opts.root ] || [];
				grid.__loadNative__( items );
				$.each( opts.plugins, function ( index, plugin ) {
					if ( plugin && typeof plugin.load == 'function' ) plugin.load(data);
				});	
			},
			dataType: opts.remote.dataType
		} );
	};
	
	Grid.prototype.__loadNative__ = function( items ) {
		var grid = this, opts = this.opts, $table = this.$table;
		
		this.$thead.find( ".checkall" ).removeClass( "checked" );
		
		$table.find( "tbody" ).remove();
		
		if ( typeof items == "undefined" ) items = opts.items;
		if ( typeof items == "undefined" || !items instanceof Array ) return;
		
		$( this ).data( 'items', items );
		
		var tbodys = [];
		$.each(items, function (index, item) {
			tbodys.push( grid.__generateTbody__( item, index ) );
		});
		
		$table.append( tbodys.join( "" ) );
		
		
		this.$loading.fadeOut( "fast" );
		
		$table.trigger( "LOADED", [ items ] );
	};
	
	Grid.prototype.__generateTbody__ = function( item, rowIndex ) {
		var cols = this.opts.cols, tbody = [], val;
		tbody.push( "<tbody><tr>" );
		
		$.each( cols, function( index, col) {
			var val = item[ col.name ];
			if ( col.renderer ) val = col.renderer( val, item, rowIndex );
			
			if ( val == "undefined" || val == null || ( typeof val == "string" && $.trim(val).length == 0  ) ) val = "&nbsp;";
			
			var css, style;
			if ( typeof col.css == "function" ) {
				css = col.css( val, item, rowIndex );
			} else {
				css = col.css;
			}
			
			if ( typeof col.style == "function" ) {
				style = col.style( val, item, rowIndex );
			} else {
				style = col.style;
			}
			
			var title = '';
			if( col.mouseover ){
				var span = document.createElement('span');
				span.innerHTML = new String( val );
				title = span.innerText;
			}
			
			tbody.push( format( '<td title="{title}" style="text-align: {align}; {style}" class="{css}" ><span>{value}</span></td>', {
				align: col.align || 'left',
				css: css || '',
				style: style || '',
				value: val,
				title: title
			}));
		} );
		
		tbody.push( "</tr>" );
		
		tbody.push( format( '<tr class="row-next"><td colspan="{0}"><div class="row-next-container">{1}</div></td></tr>', [ cols.length, "下级页面" ] ) );
		
		tbody.push( "</tbody>" );
		
		return tbody.join("");
	};
	
	//外部事件
	Grid.prototype.__event__ = function() {
		var grid = this
			, opts = this.opts
			, $table = this.$table
			, $thead = this.$thead
			, $ths = this.$ths;

		//用户加载完事件
		$table.bind( "LOADED", opts.events.loaded.data, opts.events.loaded.handler );
		//用户change事件
		$table.bind( 'CHANGE', opts.events.change.data, opts.events.change.handler );
		//用户自定义行点击事件
		$table.delegate('tbody > tr:first-child', 'CUSTOM_ROW_CLICK', opts.events.click.data, opts.events.click.handler);
		
		var customEvent = null, ce_selector, ce_type, ce_data, ce_handler;
        for (var i = 0; i < opts.customEvents.length; i++) {
          customEvent = opts.customEvents[i];

          ce_selector = customEvent.target;
          ce_type = 'CUSTOMEVENT' + customEvent.target + "." + i;
          ce_data = customEvent.data;
          ce_handler = customEvent.handler;

          $table.delegate('tbody > tr:first-child > td ' + ce_selector, ce_type, ce_data, ce_handler);

          $table.delegate('tbody > tr:first-child > td ' + ce_selector, "click", {name: ce_type}, function (event) {
              event.preventDefault();
              event.stopPropagation();
              var $this = $(this).parents("tbody:eq(0)");
              var index =  $table.find( "tbody" ).index( $this );
              var item = ( $.data( grid, "items" ) || [] )[ index ];
              $( this ).trigger( event.data.name, [ item, index ]);
              return false;
          });
        }
        
        $thead.find( "th.has-sort > i.sort-icon" ).bind( "click", function( event ) {
        	var $icon = $( this ), $th = $icon.parent();
        	 $thead.find( "th.asc,th.desc" ).not( $th ).each( function() {
    			 $( this ).removeClass( "asc desc" )
    			 	.find( ".sort-icon" ).removeClass( "fa-sort-asc fa-sort-desc" ).addClass( "fa-sort" );
    		 } );
        	if ( !$th.is( ".asc,.desc" ) ) {
        		 $th.addClass( opts.sortDesc == "asc" ? "desc" : "asc" );
        	}
        	var sortDesc;
        	if ( $th.is( ".asc" ) ) {
        		$th.removeClass( "asc" ).addClass( "desc" );
        		$icon.removeClass( "fa-sort fa-sort-asc" ).addClass( "fa-sort-desc" );
        		sortDesc = "desc";
        	} else if ( $th.is( ".desc" ) ) {
        		$th.removeClass( "desc" ).addClass( "asc" );
        		$icon.removeClass( "fa-sort fa-sort-desc" ).addClass( "fa-sort-asc" );
        		sortDesc = "asc";
        	}
        	var params = {};
        	params[ opts.sortKey ] = $th.attr( "data-sort" );
        	params[ opts.sortDesckey ] = sortDesc;
        	grid.load( params );
        } );

		
		//内部行点击事件
		$table.delegate('tbody > tr:first-child', 'click', function (event) {
			event.preventDefault();
            event.stopPropagation();
            var index =  $table.find( "tbody" ).index( $( this ).parent() );
			if ( !opts.multi ) {
				if ($( this ).hasClass('active') ) return;
				grid.select(index);
			} else {
				//多选情况
				if ($(this).hasClass('active')) {
					grid.unSelect(index);
				} else {
					grid.select(index);
				}
			}
			var item = grid.getRow(index);
			//$(this).trigger('click_event.grid.ui', [item, index]);
			
			$( this ).trigger( "CUSTOM_ROW_CLICK", [ item, index ] );
			
			return false;
		});
		
		
		if ( opts.nextCol ) {
			//内部行点击事件
			$table.delegate('tbody > tr:first-child > td:first-child', 'click', function (event) {
				event.preventDefault();
	            event.stopPropagation();
	            var $icon = $( this ).find( ".grid-ui-next" );
	            var $tbody = $icon.parents( "tbody:eq(0)" );
	            var index =  $table.find( "tbody" ).index( $tbody );
	            
	            var $contianer = $tbody.find( "tr:eq(1) > td:first-child > div" );
	            
	            if ( $contianer.is( ":visible" ) ) {
	            	$icon.removeClass( "fa-minus-square" ).addClass( "fa-plus-square" );
	            	$contianer.slideUp( "fast", function() {
	            	} );
	            } else {
	            	$icon.removeClass( "fa-plus-square" ).addClass( "fa-minus-square" );
	            	$contianer.slideDown( "fast", function() {
	        			
	        		} );
	            }
				return false;
			});
		}
		
		
		//全选择事件
		$table.delegate('thead > tr > th .checkall', 'click', function (event) {
			event.preventDefault();
            event.stopPropagation();
            if ( !$( this ).is( ".checked" ) ) {
            	$( this ).addClass( "checked" );
            	$table.find( "tbody > tr:first-child" ).addClass( "active" );
            } else {
            	$( this ).removeClass( "checked" );
            	$table.find( "tbody > tr:first-child.active" ).removeClass( "active" );
            }
            
            $table.trigger('CHNAGE', [ grid.selectedRows(), grid.selectedRowIndexs() ]);
            
			return false;
		});
	};

	//选择
	Grid.prototype.select = function( indexs ) {
		var grid = this
			, opts = this.opts 
			, $thead = this.$thead
			, $table = this.$table;
		
		if ( !indexs && indexs != 0 ) return;
		
		if ( indexs instanceof Array ) {
			$.each( indexs, function (i, index ) {
				grid.select( index );
			});
			return;
		}
		
		if (!opts.multi){
			$table.find( 'tbody > tr.active:first-child' ).each( function () {
				$( this ).removeClass( 'active' );
			});
		}
		
		$table.find( "tbody" ).eq( indexs ).find( "tr:first-child" ).addClass('active');
		
		if ( opts.multi ) {
			var length = $table.find( "tbody > tr.active:first-child" ).length;
			var $thead = this.$thead;
			$checkbox = $thead.find( '.checkall' );
			if ( length > 0 && length == $table.find('tbody').length ) {
				if ( !$checkbox.is( '.checked' ) ) $checkbox.addClass( "checked" );
			} else{
				if ( $checkbox.is('.checked') ) $checkbox.removeClass('checked');
			}
		}
		
		$table.trigger('CHNAGE', [ grid.selectedRows(), grid.selectedRowIndexs() ] );
	};
	
	Grid.prototype.unSelect = function( indexs ) {
		var grid = this
			, opts = this.opts
			, $thead = this.$thead
			, $table = this.$table;
		
		if ( !indexs && indexs != 0) return;
		
		if (indexs instanceof Array) {
			$.each(indexs, function (i, index) {
				grid.unSelect(index);
			});
			return;
		}
		
		var $checkbox = $table.find( "tbody" ).eq( indexs ).find( "tr:first-child" ).removeClass( 'active' );
		
		if ( opts.multi ) {
			var $thead = this.$thead;
			$checkbox = $thead.find( '.checkall' );
			if ( $checkbox.is('.checked') ) $checkbox.removeClass('checked');
		}
		
		$table.trigger('CHNAGE', [ grid.selectedRows(), grid.selectedRowIndexs() ]);
	};
	
	//item, index 都必须参数
	Grid.prototype.updateRows = function( item, index ) {
		if ( typeof index != "number") {
			return this.insertRows( item );
		} else if ( index < 0 ) {
			return this.insertRows( item, 0 );
		}
		
		var data = $(this).data('items') || [];
		
		if ( index + 1 > data.length ) {
			return this.insertRows( item );
		}
		
		data[ index ] = item;
		
		this.load( data );
		return this;
	};
	
	//indexs
	Grid.prototype.removeRows = function( indexs ) {
		if ( typeof indexs == "number" ) {
			indexs = [ indexs ];
		} else if ( typeof indexs != "object" || !( indexs instanceof Array ) ) {
			return;
		}
		var data = $(this).data('items') || []
			, items = [];
		
		for ( var index = 0; index < data.length; index++ ) {
			if ( $.inArray( index, indexs ) == -1 ) {
				items.push( data[ index ] );
			}
		}
		
		this.load( items );
		
		return this;
	};
	
	//index 索引
	Grid.prototype.insertRows = function( items, index ) {
		if ( typeof items != "object" ) return;
		if ( !( items instanceof Array ) ) {
			items = [ items ];
		}
		
		var data = $(this).data('items') || [],
			length = data.length;
		
		if ( data.length == 0 ) {
			data = items;
		} else {
			if ( typeof index != "number") {
				index =  length;
			} else {
				if ( index <= 0 ) {
					index = 0;
				} else if ( index >= length ) {
					index = length;
				}
			}
			
			var item;
			for ( var index2 = 0; index2 < items.length; index2++ ) {
				item = items[ index2 ];
				if ( index >= length ) {
					data.push( item );
				} else {
					data.splice( index + index2, 0, item );
				}
			}
		}
		
		this.load( data );
		
		return this;
	};
	
	//获取选额数据
	Grid.prototype.selectedRows = function () {
		var grid = this
			, $table = this.$table
			, opts = this.opts;
		var items = [], data = $(this).data('items');
		$table.find('tbody > tr.active:first-child').each(function () {
			var index =  $table.find( "tbody" ).index( $( this ).parent() );
			items.push( data[ index ] );
		});
		return items;
	};
	
	//获取选择索引
	Grid.prototype.selectedRowIndexs = function () {
		var $table = this.$table;
		var indexs = [];
		$table.find( 'tbody > tr.active:first-child' ).each(function () {
			var index =  $table.find( "tbody" ).index( $( this ).parent() );
			indexs.push( index );
		});
		
		return indexs;
	};
	
	//获取全部数据
	Grid.prototype.getAllRows = function () {
		return $( this ).data( "items" ) || [];
	};
	
	//获取数据
	Grid.prototype.getRow = function ( indexs ) {
		var grid = this
			, items
			, item;
		if (typeof indexs == 'object' && indexs instanceof Array) {
			$.each(indexs, function (__index, index) {
				item = grid.getRow(index);
				if (typeof item != 'undefined' || item != null) {
					items.push(items);
				}
			});
		} else if (typeof indexs == 'number') {
			item = ($(this).data('items') || [])[indexs];
		}
		
		if ( item != null ) {
			items = [ item ];
		} 
		return items || [];
	};
	
	//加载数据
	Grid.prototype.load = function() { 
		var that = this, opts = this.opts;
		
		var items = false, params = {};
		if ( typeof arguments[ 0 ] == 'object' ) {
			if ( arguments[ 0 ] instanceof Array ) {
				items = arguments[ 0 ];
			} else if ( arguments[ 0 ][ opts.root ] instanceof  Array ) {
				items = arguments[ 0 ][ opts.root ];
			} else  {
				params = arguments[ 0 ];
			}
		}
		
		if ( items ) {
			opts.items = items;
			this.__loadNative__();
		} else {
			this.__loadAjax__( params );
		}
		
	};
	
	//驼峰转下滑线
	function hump2underline( name ) {
		if ( typeof name != "string" || name.length == 0 ) return name;
		
		name = $.trim( name );
		if ( name.length == 0 ) return name;
		
		var r = [], s = name.split( "" ), w, regx = /^[A-Z]+$/;
		for ( var i = 0; i < s.length; i++ ) {
			w = s[ i ];
			if ( i == 0 ) {
				r.push( w );
				continue;
			} 
			if ( regx.test( w ) ) {
				r.push( "_" );
			} 
			r.push( w );
		}
		
		return r.join( "" ).toLowerCase();
	} 
	
	
	$.prototype.grid = function ( options ) {
		if ( $(this).data("GRID") ) {
			return $(this).data("GRID");
		}
		var grid =  new Grid( options, this );
		$( this ).addClass("had-grid-init").data("GRID", grid);
		
		return grid;
	};
	
});
//###################################
/**
 * grid.page 列表分页组件
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base" ], function () {
	var jQuery = $ = require( "jquery" )
		, app = require( "app/base" )
		, format = app.tools.format;
	
	/**
	 * 分页构造函数
	 * @param options 参数配置，具体参照 Page.prototype.__DEFAULT__
	 * @param target 分页Dom对象
	 */
	function Page( options, target ) {
		this.opts = opts = $.extend( true, {}, this.__DEFAULT__, options );
		if ( !opts.id ) opts['id'] = 'page' + uuid();
		this.$page = $(target);
		
		this.__event__();
	}
	
	/**
	 * 分页默认参数
	 */
	Page.prototype.__DEFAULT__ = {
		theme: 'default', //主题
		pageKey: 'pageNo', //当前页码索引
		pageSizeKey: 'pageSize', //分页条数索引
		totalSizeKey: 'totalSize', //总条数索引
		page: 1, //默认当前页码
		pageSize: 10, //默认分页条数
		showPageSize: 6, //dom显示分页按钮数
		params: {
			isPage: true //控制后台是否分页
		}
	};
	
	/**
	 * Grid列表分页插件注册
	 * @param grid {object:map} grid列表实例
	 */
	Page.prototype.register = function ( grid ) {
		var opts = this.opts, $page = this.$page;
		this.grid = grid;
		if( grid.opts.indexCol ) {
			var index = grid.opts.nextCol ? 1 : 0;
			grid.opts.cols[ index ].renderer = function( val, item, rowIndex ) {
				return rowIndex + ( opts.page - 1 ) * opts.pageSize + 1;
			};
		}
		
		$page.addClass( "page" ).attr( "id", opts.id ); 
		if ( opts.theme ) $page.addClass( "page-theme-" + opts.theme );
		
		this.load({});
	};
	
	/**
	 * 加载分页数据
	 * @param data {object:map:page}
	 */
	Page.prototype.load = function ( data ) {
		var opts = this.opts;
		if (typeof data != "object") {
			data = {};
			data[ opts.pageKey ] = 1; 
		} else {
			if ( typeof data[ opts.pageKey ] != 'number') data[ opts.pageKey ] = opts.page;
			if ( typeof data[ opts.pageSizeKey ] != 'number') data[ opts.pageSizeKey ] = opts.pageSize;
		}

		var page = data[ opts.pageKey ],
			totalSize = data[ opts.totalSizeKey ] || 0,
			pageSize = data[ opts.pageSizeKey ],
			pages = Math.floor( totalSize / pageSize ) + ( totalSize % pageSize == 0 ? 0 : 1 );		
		
		if ( pages == 0 ) pages = 1;
		if ( page > pages ) page = pages;
		
		var showPageSize = opts.showPageSize - 1, showPages = [ page ];
		var prev = next = page;
		for ( var index = 1; index <= showPageSize; index++ ) {
			if ( prev - 1 > 0 ) {
				prev -= 1;
				showPages.unshift( prev );
				
				if ( next + 1 <= pages ) {
					next += 1;
					showPages.push( next );
					index++;
				}
			} else {
				if ( next + 1 <= pages ) {
					next += 1;
					showPages.push( next );
				} else {
					break;
				}
			}
		}
		
		var htmls = [];
		htmls.push( format( '<div class="pull-left"><span>共{0}条 | {1}页</span></div><div class="pull-right"><ul class="pagination">', [ totalSize, pages ] ) );
		
		//上一页
		if ( page == 1 ) {
			htmls.push( format( '<li class="disabled"><span>上一页</span></li>' ) );
		} else {
			htmls.push( format( '<li value="{0}"><a href="javascript:;">上一页</a></li>', [ page - 1 ] ) );
		}
		
		//附近页面
		$.each( showPages, function (index, __page__) {
			if ( __page__ == page ) {
				htmls.push( format( '<li class="active"><span>{0}</span></li>', [ __page__ ] ) );
			} else {
				htmls.push( format( '<li value="{0}"><a href="javascript:;">{0}</a></li>', [ __page__ ] ) );
			} 
		});
		
		//下一页
		if ( page == pages ) {
			htmls.push( format( '<li class="disabled"><span>下一页</span></li>' ) );
		} else {
			htmls.push( format( '<li value="{0}"><a href="javascript:;">下一页</a></li>', [ page + 1 ] ) );
		}
		htmls.push( '</ul></div>' );
		
		var $page = this.$page;
		$page.html( htmls.join( '' ) );
	};
	
	/**
	 * 获取分页参数
	 * @return 参数 {object:map}
	 */
	Page.prototype.params = function() {
		var opts = this.opts, params = {};
		params[ opts.pageKey ] = opts.page;
		params[ opts.pageSizeKey ] = opts.pageSize;
		return $.extend( true, params, opts.params );
	};
	
	/**
	 * 跳转 (分页跳转)
	 * @param page {number} 跳转页码
	 */
	Page.prototype.skip = function( page ) {
		var opts = this.opts, grid = this.grid, params = {};
		params[ opts.pageKey ] = this.opts.page = page;
		grid.load( params );
	};
	
	/**
	 * 事件绑定
	 */
	Page.prototype.__event__ = function() {
		var $page = this.$page, page = this;
		
		$page.delegate( "li:not(.disabled, .active)", "click", function( event ) {
			page.skip( $( this ).val() );
			return false;
		} );
	};
	
	
	jQuery.prototype.page = function( options ) {
		if ( $( this ).hasClass( "had-page-init" ) ) return $( this ).data( "page" );
		var page = new Page( options, this );
		$( this ).addClass( "had-page-init" ).data( "page", page );
		return page;
	};
	
} );
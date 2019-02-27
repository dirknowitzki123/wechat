//###################################
/**
 * grid.query 列表查询组件
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base" ], function () {
	var jQuery = $ = require( "jquery" )
		, app = require( "app/base" )
		, format = app.tools.format;
	
	/**
	 * 查询构造函数
	 * @param options 参数配置，具体参照 Query.prototype.__DEFAULT__
	 * @param target 查询Dom对象
	 */
	function Query( options, target ) {
		var opts = this.opts = $.extend( true, {}, this.__DEFAULT__, options );
		if ( !opts.id ) opts['id'] = 'query' + uuid();
		this.$query = $( target );
		this.__event__();
		if ( typeof opts.isExpand == "boolean" && opts.isExpand === true ) {
			if ( !this.$query.is( ":visible" ) ) { 
				this.$btnGroupContext.find( opts.toggleBtn ).trigger( "click" );
			}
		}
	}
	
	/**
	 * 查询默认参数
	 */
	Query.prototype.__DEFAULT__ = {
		isExpand: false, //是否展开
		queryBtn: "#queryBtn",
		resetBtn: "#resetBtn",
		toggleBtn: "#toggleBtn",
		btnGroupContext: "",
        param: "params[{0}]"
	};
	
	/**
	 * Grid列表查询插件注册
	 * @param grid {object:map} grid列表实例
	 */
	Query.prototype.register = function ( grid ) {
		var opts = this.opts, $query = this.$query;
		this.grid = grid;
		$query.addClass( "query" ).attr( "id", opts.id ); 
		if ( opts.theme ) $query.addClass( "query-theme-" + opts.theme );
	};
	
	/**
	 * 加载查询数据
	 * @param data {object:map:query}
	 */
	Query.prototype.load = function ( data ) {
		var opts = this.opts;
	};
	
	/**
	 * 获取查询参数
	 * @return 参数 {object:map}
	 */
	Query.prototype.params = function() {
		var $query = this.$query, opts = this.opts;
		var params = {}, n, v;
        var $inputs = $query.find(":input[name]").each(function(){
            n = $(this).attr("name");
            if(n){
                v = $.trim($(this).val());
                if (typeof v != "undefined" && v != null ) {
                    n = format( opts.param, [ n ] );
                    if(!params[n]) params[n] = v;
                    else params[n] += "," + v;
                }
            }
        });
        return params;
	};
	
	/**
	 * 事件绑定
	 */
	Query.prototype.__event__ = function() {
		var $query = this.$query, query = this, opts = this.opts;
		if ( typeof opts.btnGroupContext == "string" && opts.btnGroupContext.length == 0 ) {
			opts.btnGroupContext = $query;
		}
		
		var $btnGroupContext = this.$btnGroupContext = $( opts.btnGroupContext );
		
		if ( $btnGroupContext.length == 0 ) {
			return ;
		}
		
		$btnGroupContext.find( opts.queryBtn ).bind( "click", function( event ) {
			query.grid.load( query.params() );
		} );
		
		$btnGroupContext.find( opts.resetBtn ).bind( "click", function( event ) {
			$query.find( ":input:not([button],[type=hidden])" ).each( function() {
				$( this ).valChange( '' );
			} );
			query.grid.load( query.params() );
		} );
		
		$btnGroupContext.find( opts.toggleBtn ).bind( "click", function( event ) {
			if ( $query.is( ":visible" ) ) {
				$( this ).find( "span" ).text( "展开" );
				$( this ).find( "i" ).addClass( "fa-angle-double-down" )
					.removeClass( "fa-angle-double-up" );
				$query.slideUp( function() {
					$( window ).trigger( "resize" );
				} );
				return false;
			}
			$( this ).find( "span" ).text( "收起" );
			$( this ).find( "i" ).addClass( "fa-angle-double-up" )
				.removeClass( "fa-angle-double-down" );
			$query.slideDown( function() {
				$( window ).trigger( "resize" );
			} );
		} );
		
	};
	
	
	jQuery.prototype.query = function( options ) {
		if ( $( this ).hasClass( "had-query-init" ) ) return $( this ).data( "query" );
		var query = new Query( options, this );
		$( this ).addClass( "had-query-init" ).data( "query", query );
		return query;
	};
	
} );
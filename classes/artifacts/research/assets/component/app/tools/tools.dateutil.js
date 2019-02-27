//###################################
/**
 * tools.dateutil jquery-validate 核心封装扩展
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base", "moment" ], function ( base ) {
	var $ = jQuery = require( "jquery" )
		, base = require( "app/base" )
		, tools = base.tools
		, Tools = tools.constructor
		, log = base.log;
	
	var moment = require( "moment" );
	
	function DateUtil() {
		this.default_formatStr = "YYYY-MM-DD hh:mm:ss";//默认格式化字符串
	}
	
	//内部函数 解析Date类型
	DateUtil.prototype.__parseDate__ = function( date ) {
		return moment( date );
	};
	
	//内部函数 解析时间戳类型
	DateUtil.prototype.__parseTime__ = function( time ) {
		var t, d, a;
		a = arguments;
		t = a[ 0 ];
		if ( typeof t != "number" ) {
			try{ t = Number( t ); } catch( e ) { return; }
		}
		var d = new Date( t );
		if ( typeof d != "object" && !d instanceof Date ) return;
		if ( d.getFullYear() == "1970" ) return ;
		return moment( d );
	};
	
	//内部函数 解析时间字符串
	DateUtil.prototype.__parseStr__ = function ( str, parseFormatStr ) {
		return moment( str, parseFormatStr );
	};
	
	/**
	 * @param dateOrTimeOrStr { date | time | dateStr } Date对象 时间戳 时间字符串
	 * @param parseFormatStr 时间字符解析格式 dateOrTimeOrStr为时间字符串赋值 如其它可以省略
	 * @param formatStr 格式化字符串 默认YYYY-MM-DD hh:mm:ss 可省略
	 * 实例
	 *  tools.dateUtil.format( new Date() );
	 *  tools.dateUtil.format( new Date(), "YYYY-MM-DD" );
	 *  tools.dateUtil.format( "2015/06/03", "YYYY/MM/DD" );
	 *  tools.dateUtil.format( "2015/06/03", "YYYY/MM/DD", "YYYY-MM-DD" );
	 *  tools.dateUtil.format( "1461215526581" )
	 *  tools.dateUtil.format( "1461215526581", "YYYY-MM-DD" )
	 *  tools.dateUtil.format( 1461215526581 )
	 *  tools.dateUtil.format( 1461215526581, "YYYY-MM-DD" )
	 */
	DateUtil.prototype.format = function( dateOrTimeOrStr, parseFormatStr, formatStr ) {
		if ( arguments.length == 0 ) {
			return;
		}
		var d, p, f, m, t, a, r; 
		
		a = arguments;
		
		if ( a.length == 1 ) {
			d = a[ 0 ];
			p = false;
			f = this.default_formatStr;
		} else if ( a.length == 2 ) {
			d = a[ 0 ];
			p = false;
			f = a[ 1 ];
			if ( typeof f != "string" && f.length == 0 ) {
				f = this.default_formatStr;
			}
		} else if ( a.length == 3 ) {
			d = a[ 0 ];
			p = a[ 1 ];
			f = a[ 2 ];
			if ( typeof f != "string" && f.length == 0 ) {
				f = this.default_formatStr;
			}
		}
		
		t = typeof d;
		
		if ( t == "number" ) {
			d = d.toString();
			t = "string";
		}
		
		if ( t == "object" && d instanceof Date ) m = this.__parseDate__( d );
		else if ( t == "string" ) 
			if ( t.trim ) t = t.trim();
			if ( p === false ) m = this.__parseTime__( d );
			else m = this.__parseStr__( d );
		
		if ( typeof m == "undefined" ) return d;
		
		r = m.format( f );
		
		return r;
	};
	
	Tools.prototype.dateUtil = new DateUtil();
	
} );
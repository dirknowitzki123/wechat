define( [ "app/base" ], function ( base ) {
	var $ = jQuery = require( "jquery" )
		, base = require( "app/base" )
		, tools = base.tools
		, Tools = tools.constructor
		, log = base.log;
	
	tools.add2 = function() {
		console.log( "实例扩展" );
	};
	
	Tools.prototype.add3 = function() {
		console.log( "原型扩张" );
	};
	
	/**
	 * 金额格式化
	 * s:格式化的金额
	 * n:四舍五入保留的小数位数
	 * f：币种，默认为"￥"
	 */
	tools.moneyFromat = function (s, n, f){  
		
		if ( typeof s != "string" ) s = 0;
		else try{ s = Number( s ); } catch( e ) { s = 0; }
		if ( typeof n != "number" ) s = 0;
		
   		n = n > 0 && n <= 20 ? n : 2;   
   		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   		var l = s.split(".")[0].split("").reverse(),   
   		r = s.split(".")[1];   
   		t = "";   
   		for(i = 0; i < l.length; i ++ ){   
      		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   		}   
   		
   		if(f && f != ""){
   			return f + t.split("").reverse().join("") + "." + r; 
   		}else{
   			return "￥" + t.split("").reverse().join("") + "." + r; 
   		}
	};
	
} );
//###################################
/**
 * code 码表
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ 
     "app/base"
], function() {
	var $ = require( "jquery" )
		, base = require( "app/base" )
		, format = base.tools.format
		, uuid = base.tools.uuid
		, log = base.log;
	
	function Code () {}
	
	//码表缓存域
	Code.prototype.__cache__ = {};
	
	Code.prototype.push = function( type, codes ) { 
		if ( typeof codes == "object" && codes instanceof Array ) {
			codes = { __default__: codes };
		}
		var that = this;
		$.each( codes[ "__default__" ] || [], function( index, code ) {
			codes[ code.valCode ] = code;
			that.__recursion__( code, codes, "" );
		} );
		
		if ( typeof codes.level == "undefined" ) {
			codes.level = 1;
		} else if ( typeof codes.level == "string" ) {
			try{ codes.level = Number( codes.level ); } catch( e ) { codes.level = 1; }
		}
		if ( typeof codes.level != "number" || codes.level < 1  ) {
			codes.level = 1;
		}
		
		this.__cache__[ type ] = codes;
	};
	
	//码表递归 树形码表 层次结构
	Code.prototype.__recursion__ = function( code, top, prefix ) {
		var codes = code.children;
		var key = prefix + code.valCode + "_";
		if ( typeof codes != "object" || !( codes instanceof Array ) ) {
			return;
		}
		
		var __recursion__ = this.__recursion__;
		for ( var index = 0; index < codes.length; index++ ) {
			code = codes[ index ];
			top[ key + code.valCode ] = code;
			__recursion__( code, top, key );
		}
	};
	
	Code.prototype.cache = function() {
		var that = this;
		var args;
		var codes = [], jsons = [];
		if ( arguments.length == 1) {
			if ( typeof arguments[ 0 ] == "string" ) {
				args = arguments[ 0 ].split( "," );
			} else if ( typeof arguments[ 0 ] == "object" && arguments[ 0 ] instanceof Array ) {
				args = arguments[ 0 ];
			} else if ( typeof arguments[ 0 ] == "object" ) {
				args = Array.prototype.slice.call(arguments);
			}
		} else {
			args = Array.prototype.slice.call(arguments);
		}
		
		var arg, str, cache;
		for( var index = 0; index < args.length; index ++ ) {
			arg = args[ index ];
			
			if ( typeof arg == "string" ) {
				arg = arg.split( "," );
				
				if ( arg.length == 1 ) {
					arg = $.trim( arg );
					if ( arg.length == 0 ) {
						continue;
					}
					arg = { type: arg, level: 1 };
				} else {
					var args2 = [], arg2;
					for( var index = 0; index < arg.length; index++ ) {
						arg2 = $.trim( arg[ index ] );
						if ( arg2.length == 0 ) continue;
						args2.push( { type: arg2, level: 1 } );
					}
					if ( args2.length > 0 ) {
						this.cache( args2 );
					}
					continue;
				}
				
			} else if ( typeof arg != "object" ) {
				continue;
			}
			
			if ( typeof arg.level != "number" || arg.level < 1 ) {
				arg.level = 1;
			}
			
			
			str = arg.type = $.trim( arg.type );
			
			cache = this.__cache__[ str ];
			
			if ( typeof cache == "number" ) {
				if ( cache >= arg.level ) {
					continue;
				}
			} else if ( typeof cache == "object" ) {
				if ( cache.level >= arg.level ) {
					continue;
				}
			} else {
				this.__cache__[ str ] = 1;
			}
			
			if ( str.indexOf( ".json" ) != -1 ) {
				jsons.push( arg );
			} else {
				codes.push( arg );
			}
		}
		
		if ( codes.length > 0 ) {
			 $.ajax({
                url: 'frame/code/cache/2',
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify( codes ),
                success: function ( data ) {
                	var level = 0;
                    $.each( data.map, function ( type, code ) {
                    	that.push( type, code );
                    	level = code.level || 1;
                    	for ( var index = 1; index <= level; index++ ) {
                    		$( document ).triggerHandler( type + '.app.code.' + index, [ code ] );
                    	}
                    });
                }
            });
		}
		
		for ( var index = 0; index < jsons.length; index++ ) {
			( function( json, code ){
				var arr = json.type.split("/");
				if ( arr.length > 1 ) {
					json.type = arr.pop();
					if ( typeof json.uri == "undefined" ) {
						json.uri = arr.join("/");
					} else {
						json.uri += arr.join("/");
					}
				}
				if ( typeof json.uri == "undefined" ) {
					json.uri = "assets/component/app/data/";
				}
				code.__cache__[ json.type ] = true;
				$.ajax({
	                url: json.uri + "/" + json.type + "?v=1.1",
	                type: "GET",
	                success: function ( data ) {
	                	if ( typeof data == "object" && data instanceof Array ) {
	                		var tmp = data;
	                		data = {};
	                		data[ "__default__" ] = tmp;
	                	}
	                	that.push( json.type, data );
	                	if ( typeof json.callback == "function" ) {
	                		data[ "__default__" ] = json.callback.call( that, json, data[ "__default__" ] );
	                	} 
                        $( document ).triggerHandler( json.type + '.app.code', [ data ] );
	                }
	            } );
			} )( jsons[ index ], that );
			
		}
	};
	
	
	__default__ = {
		type: null,
		group: "__default__",
		callback: null,
		scope: null,
		index: 1
	};
	Code.prototype.getCache = function (type, group, index) {
		var cache = this.__cache__[type];
		if ( typeof cache == "boolean" && cache === true ) return;
		if (arguments.length == 1 || typeof group == "boolean") return cache;
		if ( typeof cache == "undefined" ) return cache;
		
		if ( typeof index != "number" || index < 1 ) {
			index = 1;
		}
		
		if ( cache.level < index ) {
			return;
		}
		if ( arguments.length == 1 && typeof arguments[ 0 ] == "object" ) {
			var opts = $.extend( true, {}, __default__, arguments[ 0 ] );
			this.getCacheByCallBack( opts.type, opts.group, opts.callback, opts.scope, opts.index );
			return; 
		}
		
		return cache[ group ];
	};
	
	//获取缓存信息通过回调函数
    //group 可以忽略
    Code.prototype.getCacheByCallBack = function (type, group, callback, scope, index) {
        if (typeof arguments[0] != "string" && arguments[0].length > 0) return;
        if (typeof arguments[1] == 'function') {
            if (typeof arguments[2] == 'object') {
                scope = arguments[2];
            }
            callback = arguments[1];
            group = false;
        }
        if (!group) group = false;
        
        if ( typeof index != "number" || index < 1) {
        	index = 1;
        }

        if (typeof callback != 'function') return;

        var cache = this.getCache( type, group, index );
        
        if ( typeof cache == 'object' ) {
            callback.call(scope || window, cache);
            return;
        }
        
        this.cache( { type: type, level: index } );

        var id = "get_cache_by_callback" + uuid(); 
        var name = type + '.app.code.' + index + "." + id;
        $( document ).one(name, {
        	name: name,
            group: group,
            callback: callback,
            scope: scope
        }, function (event, cache) { 
        	//$(window).unbind(event.data.name, false);
            if ( event.data.group ) {
            	event.data.callback.call(event.data.scope || window, cache[ event.data.group ] );
            } else {
            	event.data.callback.call(event.data.scope || window, cache );
            }
        });
    };
    
    //append {boolean} 是否追加
    Code.prototype.getTextByArea = Code.prototype.getNameByArea = function( code, append ) {
    	
    	if ( typeof append != "boolean" ) {
    		append = true;
    	}
    	
    	if ( typeof code == "number" ) {
    		code += "";
    	}
    	if ( typeof code != "string" ) {
    		return code;
    	}
    	
    	code = $.trim( code );
    	
    	if ( !append ) {
    		return this.getText( code );
    	}
    	
    	if ( code.length != 6 ) {
    		return code;
    	}
    	
    	var s = [], r, c, oc = "";
    	for ( var index = 0; index < 3; index++ ) {
    		c = c = code.substring( 0, 2 + ( 2 * index ) );
    		if ( index == 0 ) {
    			c += "0000";
    		} else if ( index == 1 ) {
    			c += "00";
    		}
    		
    		if ( c == oc ) {
    			break;
    		}
    		
    		oc = c;
    		
    		r = this.getText( "area.code.json", c );
    		if ( typeof r == "undefined" || r == c ) {
        		s.push( code ); 
    			break;
        	}
    		
    		s.push( r );
    	}
    	
    	return s.join( "" );
    };
    
    //获取文本信息
    /**
     * @param type 码类
     * @param code 码值
     * @param index 层级 树形码表查询几级 默认1级
     * @param append 是否追加 树形码表是否追加码值对应的文本信息 默认true
     * @param appendChar 追加符号 默认空字符串
     */
    Code.prototype.getText = Code.prototype.getName = function (type, code, index, append, appendChar) {
    	
    	if ( typeof type != "string" || type.length == 0 ) {
    		return code;
    	}
    	
    	if ( typeof code == "undefined" 
    		|| ( typeof code != "string" || code.length == 0 ) ) {
    		return code;
    	}
    	
    	if ( typeof appendChar != "string" ) {
    		appendChar = "";
    	}
    	
        if ( typeof index != "number" || index < 1 ) {
        	index = 1;
        }
        if ( typeof append != "boolean" ) {
        	append = true;
        }
        var cache = this.getCache(type);
        
        if ( typeof cache != "object" || cache.level < index ) {
        	this.cache( {
        		type: type,
        		level: index
        	} );
        	cache = true;
        }
        
        var text; //文本信息
        if (typeof cache == 'undefined' || ( typeof cache == "boolean" && cache == true ) ) {
            var id = "get_text_or_name_" + uuid(); 
            var name = type + '.app.code.' + index + '.' + id;
            $( document ).one( name, {
            	id: id,
                code: code,
                level: index,
                append: append,
                appendChar: appendChar
            }, function (event, cache) {
            	//$(window).unbind(event.data.name, false);
            	var id = "#" + event.data.id,
            		index = event.data.level;
            	if ( index <= 1 ) {
                	cache = cache[ event.data.code ] || {};
                	cache = cache.valName || event.data.code;
            		$( id ).text( cache );
            	} else {
            		var codes;
            		if ( typeof code == "string" ) {
            			codes = code.split( "," );
            		} else if ( typeof code == "object" && code instanceof Array ) {
            			codes = code;
            		}
            		
            		var level = event.data.level, txts = [], key = "", val;
            		for ( var index = 0; index < codes.length; index++ ) {
            			val = $.trim( codes[ index ] );
            			prefix += val;
            			code = cache[ key ];
            			txts.push( code.valName || val );
            			prefix += "_";
            		}
            		
            		if ( event.data.append ) {
            			$( id ).text( txts.join( event.data.appendChar ) );
            		} else {
            			$( id ).text( txts.pop() );
            		}
            	}
            });
            text = '<span id="' + id + '">-</span>';
        } else {
        	if ( typeof code == "undefined" || code == null || ( typeof code == "string" && code.length == 0 ) ) return "";
        	text = ( cache[ code ] || {} ).valName || code;
            text = '<span class="get_text_or_name_code_app">' + text + '</span>';
        }
        return text;
    };
	
    base.code = new Code();
    
	return base.code;
	
	
} );
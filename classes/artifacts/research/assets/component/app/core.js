//###################################
/**
 * core 核心
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ 
	"app/base",
	"app/tools/tools.extend",
	"app/ui/code",
	"app/ui/shiro",
	"app/ui/page",
	"app/ui/module",
	"app/ui/popup",
	"app/ui/dialog"
], function() {
	var $ = jQuery = require( "jquery" )
		, base = require( "app/base" )
		, log = base.log
		, page = require( "app/ui/page" )
		, module = require( "app/ui/module" )
		, modal = require( "app/ui/modal" )
		, popup = require( "app/ui/popup" )
		, Dialog = require( "app/ui/dialog" )
		, App = base.constructor
		, AppObject = base.AppObject;
	
	/**
	 * 子页构造函数（重写与封装 app/ui/page page对象）
	 * @param namespace {string} 子页全局命名空间
	 * 返回子页实例对象
	 */
	function Page( namespace ) {
		this.namespace = namespace;
	};
	/**
	 * 子页默认配置参数
	 */
	Page.prototype.__DEFAULT__ = {};
	/**
	 * 获取子页全局对象
	 */
	Page.prototype.global = function() {
		return base.init( this.namespace );
	};
	
	/**
	 * 打开子页
	 * @param options {object:map} 配置参数
	 */
	Page.prototype.open = function( options ) {
		var opts = $.extend( true, {}, this.__DEFAULT__, options );
		var appSup = this.global();
		opts.content = appSup.__sectionsWrapper__;
		if ( opts.url ) opts.url += ".tpl";
		var appObject = page.open( opts, 
			function( apps ) {
				$.each( apps, function( index, appSub ) {
					appSub.__super__ = appSup.__namespace__;
					appSup.push( appSub );
					$( format( [
					            '<button type="button" class="section-close close">',
					            '	<i class="fa fa-undo"></i> 返回',
					            '</button>'
	                 ] ) ).appendTo( appSub.content ).bind( "click", function() {
	                	 appSub.page.close();
	                 } );
				} );
			},
			function() {
				if ( appSup.__sectionsWrapper__.is( ":empty" ) ) {
					if ( appSup.__sectionsWrapper__.is( ":visible" ) ) {
						appSup.__sectionsWrapper__.hide();
					} 
					if ( appSup.__sectionCotainer__.is( ":hidden" ) ) {
						appSup.__sectionCotainer__.fadeIn();
					}
				}
			}
		);
			
		if ( appSup.__sectionsWrapper__.is( ":hidden" ) ) {
			appSup.__sectionsWrapper__.show();
		}
		if ( appSup.__sectionCotainer__.is( ":visible" ) ) {
			appSup.__sectionCotainer__.hide();
		}
		
		return appObject;
	};
	
	/**
	 * 关闭子页
	 * @param data {object:map} 返回结果集
	 */
	Page.prototype.close = function( data ) {
		var appCur = this.global()
			, namespace = appCur.__namespace__
			, appSup = base.init( appCur.__super__ ); 
		
		if ( typeof appCur.__page__ == "undefined" ) return log.error( "JS: 当前非内嵌页。" );
		
		page.close( appCur, arguments.length > 0, data );
		base.die( appCur );
		
		var index = $.inArray( namespace, appSup.__apps__ );
		if ( index >= 0 ) {
			delete appSup.__apps__[ index ];
			appSup.__apps__.length -= 1;
		}
		
		if ( appSup.__sectionsWrapper__.is( ":empty" ) ) {
			if ( appSup.__sectionsWrapper__.is( ":visible" ) ) {
				appSup.__sectionsWrapper__.hide();
			} 
			if ( appSup.__sectionCotainer__.is( ":hidden" ) ) {
				appSup.__sectionCotainer__.fadeIn();
			}
		}
		
	};
	
	/**
	 * 弹出页构造函数（重写与封装 app/ui/popup popup对象）
	 * @param namespace {string} 子页全局命名空间
	 * 返回弹出页实例对象
	 */
	function Popup( namespace ) {
		this.namespace = namespace;
	};
	/**
	 * 弹出页默认配置参数
	 */
	Popup.prototype.__DEFAULT__ = {};
	/**
	 * 获取弹出页全局对象
	 */
	Popup.prototype.global = function() {
		return base.init( this.namespace );
	};
	
	/**
	 * 打开弹出页
	 * @param options {object:map} 配置参数
	 */
	Popup.prototype.open = function( options ) {
		var opts = $.extend( true, {}, this.__DEFAULT__, options );
		var appSup = this.global();
		opts.content = appSup.__sectionsWrapper__;
		if ( opts.url ) opts.url += ".tpl";
		
		appSup.loading.show();
		
		var appObject = popup.open( opts, 
			function( apps ) {
				$.each( apps, function( index, appSub ) {
					appSub.__super__ = appSup.__namespace__;
					appSup.push( appSub );
					$( format( [
					            '<button type="button" class="section-close close">',
					            '	<i class="fa fa-undo"></i> 返回',
					            '</button>'
	                 ] ) ).appendTo( appSub.section.find( ".section-container:first" ) ).bind( "click", function() {
	                	 appSub.popup.close();
	                 } );
					
					appSub.content.delegate( ".section-popup-toggle > p" , "click", function( event ) {
						appSub.popup.close();
					} );
					
					appSub.section.bind( "section.custom.resize", function( event, width, height ) {
						
						if ( typeof width == "undefined" ) {
							return false;
						} 
						appSub.section.css( {
							"width":  width + "px",
							"height": height + "px"
						} );
						
						if ( typeof appSub.__sectionsWrapper__ == "undefined" ) {
							appSub.__sectionsWrapper__ = $( ".section-container:first", appSub.content );
						}
						
						appSub.__sectionsWrapper__.css( {
							/*"height": height + "px",*/
							"min-height": height + "px"
						} );
						
					} ).trigger( "section.resize" );
				} );
				
				setTimeout( function() {
					appSup.loading.hide();
				}, 500 );
			},
			function( appCur ) {//error
				var namespace = appCur.__namespace__
					, appSup = base.init( appCur.__super__ ); 
				
				base.die( appCur );
				
				var index = $.inArray( namespace, appSup.__apps__ );
				if ( index >= 0 ) {
					delete appSup.__apps__[ index ];
					appSup.__apps__.length -= 1;
				}
				
				if ( appSup.__sectionsWrapper__.is( ":empty" ) ) {
					if ( appSup.__sectionsWrapper__.is( ":visible" ) ) {
						appSup.__sectionsWrapper__.hide();
					} 
					if ( appSup.content.is( ".section-popup-open" ) ) {
						appSup.content.removeClass( "section-popup-open" );
						appSup.section.unbind( "section.custom.resize" );
					}
				}
			}
		);
		
		if ( appSup.__sectionsWrapper__.is( ":hidden" ) ) {
			appSup.__sectionsWrapper__.show();
		}
		if ( !appSup.content.is( ".section-popup-open" ) ) {
			appSup.section.bind( "section.custom.resize", function( event, width, height ) {
				appSup.__sectionsWrapper__.css( {
					"height": height + "px",
					"min-heihgt": height + "px"
				} );
				appSup.__sectionCotainer__.css( {
					"height": height + "px"
				} );
			} ).trigger( "section.resize" );
			
			appSup.content.addClass( "section-popup-open" );
		}
		
		
		return appObject;
	};
	
	/**
	 * 弹出页子页
	 * @param data {object:map} 返回结果集
	 */
	Popup.prototype.close = function( data ) {
		var appCur = this.global();
		
		if ( typeof appCur.__popup__ == "undefined" ) return log.error( "JS: 当前非内嵌页。" );
		
		popup.close( appCur, arguments.length > 0, data );
		//base.die( appCur );
		
	};
	
	/**
	 * 集成页构造函数（重写与封装 app/ui/module module对象）
	 * @param namespace {string} 子页全局命名空间
	 * 返回子页实例对象
	 */
	function Module( namespace ) {
		this.namespace = namespace;
		this.__modules__ = {};
	};
	/**
	 * 集成页默认配置参数
	 */
	Module.prototype.__DEFAULT__ = {};
	/**
	 * 获取集成页全局对象
	 */
	Module.prototype.global = function() {
		return base.init( this.namespace );
	};
	
	/**
	 * 打开集成页
	 * @param options {object:map} 配置参数
	 */
	Module.prototype.open = function( options, appObject ) {
		var opts = $.extend( true, {}, this.__DEFAULT__, options );
		var appSup = this.global();
		
		var valid = false;
		if ( typeof opts.content != "undefined" ) {
			if ( typeof opts.content == "string" ) {
				 opts.content = appSup.selector.find( opts.content );
			} else {
				opts.content = $( opts.content );
			}
			
			if ( opts.content.length == 1 ) {
				valid = true;
			}
		}
		
		if ( !valid ) {
			throw new Error("没有找到集成模块[Module]的容器");
		}
		
		
		var moduleid;
		if (  typeof appObject == "undefined" || typeof appObject.moduleid != "string" || appObject.moduleid.length == 0 ) {
			moduleid = "Module_" + uuid();
		} else {
			moduleid = appObject.moduleid;
		}
		this.__modules__[ moduleid ] = {
			options: options
		};
		
		var __apps__ = this.__modules__[ moduleid ].__apps__ = [];
		
		if ( opts.url ) opts.url += ".tpl";
		var __appObject__ = module.open( opts, 
			function( apps ) {//before
				$.each( apps, function( index, appSub ) {
					appSub.__super__ = appSup.__namespace__;
					appSup.push( appSub );
					__apps__.push( appSub.__namespace__ );
				} );
			},
			function( options ) {//error
				
			}
		);
		
		if ( typeof appObject == "undefined" ) {
			appObject = __appObject__;
			appObject.moduleid = moduleid;
		} else {
			appObject = $.extend( true, appObject, __appObject__);
		}
		
		return appObject;
	}; 
	Module.prototype.load = function( appObject, options ) {
		if ( arguments.length == 1) {
			options = appObject;
		} else if ( arguments.length == 2 && typeof appObject == "object" ) {
			var moduleid = appObject.id;
			var m = this.__modules__[ moduleid ];
			options = $.extend( true, m.options, options );
			base.die( m.__apps__ );
			m.__apps__ = [];
		} else {
			throw Error( "【MODULE】模块加载参数不正确." );
		}
		
		return this.open( options, appObject );
	};
	
	/**
	 * 关闭集成页
	 * @param data {object:map} 返回结果集
	 */
	Module.prototype.close = function( data ) {
		var appCur = this.global()
			, namespace = appCur.__namespace__
			, appSup = base.init( appCur.__super__ );
		
		//module 用户hiden事件 返回非布尔值时 关闭集成页 调用父类的close函数
		var r = module.close( appCur, arguments.length > 0, data );
		
		if ( typeof r != "boolean" ) {
			base.die( appCur );
			appSup.close( data );
		} else if ( r == true ) {
			base.die( appCur );
		}
		
	};
	
	/**
	 * 子窗口构造函数（重写与封装 app/ui/modal modal对象）
	 * @param namespace {string} 子窗口全局命名空间
	 * 返回子页实例对象
	 */
	function Modal( namespace ) {
		this.namespace = namespace;
	};
	/**
	 * 子窗口默认配置参数
	 */
	Modal.prototype.__DEFAULT__ = {};
	/**
	 * 获取子窗口全局对象
	 */
	Modal.prototype.global = function() {
		return base.init( this.namespace );
	};
	
	/**
	 * 打开子窗口
	 * @param options {object:map} 配置参数
	 */
	Modal.prototype.open = function( options ) {
		var opts = $.extend( true, {}, this.__DEFAULT__, options );
		var appSup = this.global(); 
		opts.content = appSup.__sectionsWrapper__;
		if ( opts.url ) opts.url += ".tpl";
		appSup.loading.show();
		var appObject = modal.open( opts, 
			function( apps ) { //before
				$.each( apps, function( index, appSub ) {
					appSub.__super__ = appSup.__namespace__;
					appSup.push( appSub );
				} );
				appSup.loading.fadeOut();
			}, 
			function( apps ) { //after
				base.die( apps );
				
				if ( appSup.__sectionsWrapper__.is( ":empty" ) ) {
					if ( appSup.__sectionsWrapper__.is( ":visible" ) ) {
						appSup.__sectionsWrapper__.hide();
					} 
				}
			},
			function() { //error
				appSup.loading.fadeOut();
			}
		);
		
		if ( appSup.__sectionsWrapper__.is( ":hidden" ) ) {
			appSup.__sectionsWrapper__.show();
		}
		
		/*if ( app.__sectionCotainer__.is( ":visible" ) ) {
			app.__sectionCotainer__.hide();
		}*/
		
		return appObject;
	};
	
	/**
	 * 关闭子窗口
	 * @param data {object:map} 返回结果集
	 */
	Modal.prototype.close = function( data ) {
		var appCur = this.global()
			, namespace = appCur.__namespace__
			, appSup = base.init( appCur.__super__ );
		
		if ( typeof appCur.__modal__ == "undefined" ) return log.error( "JS: 当前非窗口视图。" );
		appCur.__data__ = data;
		
		appCur.content.find( ".modal" ).modal( "hide" );
		
		//require( "app/base" ).die( app2 );
		var index = $.inArray( namespace, appSup.__apps__ );
		if ( index >= 0 ) {
			delete appSup.__apps__[ index ];
			appSup.__apps__.length -= 1;
		}
		
	};
	
	/**
	 * 页面全局Js对象构造函数
	 * 返回页面全局（global）对象
	 */
	function JsApp() {};
	
	/**
	 * 全局对象注册（实例一些JsApp默认对象
	 * __namespace__ 全局命名空间
	 * content 根级上下文
	 * selector 自定义上下文
	 * __sectionCotainer__ 页面内容容器
	 * __sectionsWrapper__ 子页容器（子页存放路径）
	 * __apps__ 子页命名空间存放（集成）
	 * page 子页对象继承
	 * 
	 * 没有参数或只有一个参数
	 * @param args1 {undefined|boolean:false} 注册命名空间，并实例Js全局对象
	 * 只有一个参数
	 * @param args1 {boolean:true} 只注册命名空间，不实例Js全局对象
	 */
	JsApp.prototype.register = function( args1 ) { 
		if ( arguments.length == 0 ) args1 = false;
		if ( !this.__namespace__ && typeof args1 == "boolean") {
			this.__namespace__ = this.namespace.split( "." ).join( "$" ) + uuid();
			this.__apps__ = [];
			
			this.page = new Page( this.__namespace__ );
			this.modal = new Modal( this.__namespace__ );
			this.module = new Module( this.__namespace__ );
			this.popup = new Popup( this.__namespace__ );
			base.push( this );
			if ( args1 === true ) return;
		}

		if ( !this.content ) {
			this.content = $( "html" );
		}
		
		this.selector = $( format ( "[namespace=\"{namespace}\"]:not(.had-app-init)", { namespace: this.namespace } ),  this.content || "html" );
		if ( this.selector.length == 0 ) {
			this.content.addClass( this.namespace.split( "." ).join( "-" ) );
			this.selector = this.content.find( ".section-container:not(.had-app-init)" );
			this.selector.attr( "namespace", this.namespace );
		}
		if ( this.selector.length != 1 ) {
			warn( format( "namespace({0}):找不到jQuery对象" , [ this.namespace ] ) );
			return this.init();
		} 
		this.selector.addClass("container-fluid").addClass("had-app-init");
		this.loading = this.selector.find( "> .section-loading2:first" );
		this.__sectionsWrapper__ = $( ".sections-wrapper:first", this.content );
		this.__sectionCotainer__ = $( ".section-container:first", this.content );
		
		if ( typeof this.params == "undefined" ) {
			this.params = {};
		}
		
		this.selector.find( "[data-shiro]" ).shiro();
		
	};
	
	/**
	 * 获取全局对象
	 */
	JsApp.prototype.global = function() {
		return this;
	};
	
	/**
	 * 存放子页命名空间
	 * @param args {object:map:JsApp|string} JsApp全局对象或命名空间
	 */
	JsApp.prototype.push = function( args1 ) {
		if ( ( $.isPlainObject( args1 ) || args1 instanceof JsApp ) && typeof args1.__namespace__ =="string" ) {
			args1 = args1.__namespace__;
		}
		if ( typeof args1 == "string" ) this.__apps__.push( args1 );
	};
	
	/**
	 * 关闭窗口或关闭页
	 * @param data 回值
	 */
	JsApp.prototype.close = function( data ) {
		var appCur = this.global(); 
		if ( typeof appCur.__modal__ != "undefined" ) {
			this.modal.close.apply( this, arguments );
		} else if ( typeof appCur.__page__ != "undefined" ) {
			this.page.close.apply( this, arguments );
		} else if ( typeof appCur.__popup__ != "undefined" ) {
			this.popup.close.apply( this, arguments );
		} else if ( typeof appCur.__module__ != "undefined" ) {
			this.module.close.apply( this, arguments );
		}
	};
	
	/**
	 * 对话框
	 */
	JsApp.prototype.dialog = new Dialog();
	
	/**
	 * 触发通知 下派通知
	 * @param options [ {type:{stirng}, obj:{object:AppObject}, data:{stirng:object:map}(可省略)} ]
	 * @param callback 参数为 options数组的下派通知的回值 其中如果一个模块回值为多个值，那其值数组，如果为一个那为对象
	 */
	JsApp.prototype.triggerAdvice = function( options, callback ) {
		if ( typeof options != "object" 
			|| !options instanceof Array ) {
			throw new Error( "options 需是数组参数[ {type:{stirng}, obj:{object:AppObject}, data:{stirng:object:map}(可省略)} ]" );
		}
		var datas, option, appObject, type, appNs, counts = options.length;
		datas = new Array( counts );
		
		for ( var index = 0; index < options.lengt; index++ ) {
			option = options[ index ];
			if ( typeof option != "object"
				|| typeof option.type != "string"
				|| typeof option.obj != "object" || option.obj instanceof AppObject ) {
				throw new Error( "options 格式为：{type:{stirng}, obj:{object:AppObject}, data:{stirng:object:map}(可省略)}" );
			}
			
			type = option.type;
			appObject = option.appObject;
			
			( function(){
				appObject.triggerAdvice( type, function() {
					var args = Array.prototype.slice.call( arguments );
					counts -= 0;
					if ( args.length >= 1 ) {
						datas[ index ] = args.length == 1 ? args[ 0 ] : args;
					}
					if ( counts <= 0 ) {
						callback.apply( null, datas );
					}
				}, data );
			} )( appObject, type, index );
		}
	};
	
	
	/**
	 * 临时存放未执行页面JsApp全局对象(内部)（未注册）
	 */
	App.prototype.__apps__ = [];
	/**
	 * 存放未执行页面JsApp全局对象(内部)（未注册）
	 * 继承JsApp对象
	 */
	App.prototype.__push__ = function( appCur ) {
		appCur = $.extend( true, appCur, new JsApp());
		this.__apps__.push( appCur );
		return appCur;
	};
	/**
	 * 存放页面JsApp对象（已注册）
	 */
	App.prototype.apps = {};
	/**
	 * 存放已注册的JsApp全局对象
	 * @param app {object:map:JsApp} JsApp全局对象(已注册)
	 */
	App.prototype.push = function( app ) {
		this.apps[ app.__namespace__ ] = app;
	};
	/**
	 * 多种定义
	 * 1. 执行（或注册）未注册的JsApp对象
	 * 无参数
	 * @return 会犯执行的JsApp对象数组
	 * 2. 定义未注册JsApp对象
	 * @param args1 {object:map} 定义的app
	 * 3. 查询所有未注册的JsApp并清空
	 * @param args1 {boolean:true}
	 * @return返回JsApp全局对象并注册未执行
	 * 4. 产讯所有未注册的JsApp
	 * @param args1 {boolean:false}
	 * @return返回未注册JsApp全局对象
	 * 5. 查询JsApp全局对象
	 * @param args1 {string} 命名空间
	 * @return 返回JsApp全局对象
	 */
	App.prototype.init = function( args1, args2 ) {
		if ( arguments.length == 0 ) {
			var apps = this.__apps__.splice( 0, this.__apps__.length ), app;
			if ( apps.length == 0 ) return ;
			for ( var l = 0; l < apps.length; l++ ) {
				app = apps[ l ];
				if ( typeof app.namespace != "string" ) { 
					warn( "app 没有入口命令空间" );
			    } else {
			    	app.register();
				}
				app.init(); 
			};
			return apps;
		} else if ( arguments.length == 1 ) {
			if ( typeof args1 == "function" ) {
				/*args1.prototype = new JsApp();*/
				this.__push__( new args1() );
 			} else if ( $.isPlainObject( args1 ) ) {
				if ( typeof args1.init != "function" ) {
					return error("App没有入口函数，需要一个init入口函数");
				}
				this.__push__( args1 );
			} else if ( typeof args1 == "boolean" && args1 === true ){
				var apps = this.__apps__ || [];
				for ( var i = 0; i < apps.length; i++ ) {
					apps[ i ].register( true );
				}
				return apps;
			} else if ( typeof args1 == "boolean" && args1 === false ){
				return this.__apps__;
			} else if ( typeof args1 == "string" ) {
				return this.apps[ args1 ];
			}
		} else if ( arguments.length == 2 ) {
			if ( typeof args1 == "function" && typeof args2 == "object" ) {
				this.__push__( new args1( args2 ) );
			}
		}
	};
	
	
	/**
	 * 销毁JsApp全局对象
	 * @param args {object:map:JsApp|string} JsApp对象 或 命令空间
	 */
	App.prototype.die = function( args1 ) {
		if (typeof arguments.length == 0 ) {
			return false;
		} 
		
		if ( typeof args1 == "object" && $.isArray( args1 ) ) {
			for ( var i = 0; i < args1.length; i++ ) {
				this.die( args1[ i ] );
			}
			return;
		}
		
		if ( typeof args1 == "string" ) {
			args1 = base.init( args1 );
		}
		
		if ( typeof args1 != "object" ) return;
		
		if( args1.__namespace__ ) {
			var apps = args1.__apps__ || [];
			
			if ( apps.length > 0 ) this.die( apps );
			
			if ( args1.content ) {
				args1.content.remove();
			}
			
			delete this.apps[ args1.__namespace__ ];
			delete args1;
		}
	};
	
	/**
	 * 触发向下级发送通知
	 * @param type {string} 类型 用于区分通知类型
	 * @param callback {function} 回调函数
	 * @param params 传递参数值
	 */
	AppObject.prototype.triggerAdvice = function( type, callback, params ) {
		if ( typeof type != "string" ) throw new Error( "type 需字符串类型。" );
		if ( arguments.length == 2 ) {
			if ( typeof arguments[ 1 ] != "function" ) {
				params = arguments[ 1 ];
				callback = function(){};
			}
		}
		var apps = this.__apps__, app;
		for ( var index = 0; index < apps.length; index++ ) {
			app = base.init( apps[ index ] );
			if ( typeof app != "object" || typeof app.bindAdvice != "function"  ) continue;
			if ( arguments == 0 ) {
				return;
			}
			if ( arguments.length == 1 ) {
				if ( typeof arguments[ 0 ] == "function" ) {
					callback == arguments[ 0 ];
					type = null;
				}
				if ( typeof callback != "function" ) {
					callback = function(){};
				}
			}
			app.bindAdvice( type, callback, params );
		}
	};
	
	return base;
} );
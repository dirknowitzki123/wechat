//###################################
/**
 * module 模块集成
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
define( [ "app/base" ], function() {
	
	require( [ "css2!app/ui/css/modal" ] );
	
	var $ = jQuery = require( "jquery" )
		, base = require( "app/base" )
		, uuid = base.tools.uuid
		, format = base.tools.format
		, log = base.log
		, AppObject = base.AppObject;
	
	
	function Module() {};
	
	Module.prototype.settings = {
			__template__: [
	           '<div id="{id}" class="section section-module active {css} {id}" style="{style}">',
	           '</div>'
	        ],
			id: "",//ID
			css: "",//类
			style: "",//样式
			title: "",//标题
			toggle: true,//是否展开与伸缩
			toggleOpen: true,//是否展开
			icon: "fa fa-tasks",//图标
			content: false,//容器 需用户自定义
			params: {},//参数 父级页面向子级页面传的参数
			__data__: {},//内置相应结果
			events: {
				shown:  function ( apps ) {},//打开后事件
				hiden: function ( closed, data ) {}//关闭后事件
			}
		};
	
	Module.prototype.open = function( options, before, error ) {
		var module = $.extend( true, {}, this.settings, options );
		if ( typeof module.id == "undefined" || module.id == "" ) module.id = "module" + uuid();
		var $module = $( format( module.__template__, module ) ).appendTo( module.content );
		module.url += ( module.url.indexOf( "?" ) != -1 ? "&" : "?" ) + "PAGEID=." + module.id;
		
		var appObject = new AppObject();
		
		$.ajax( {
			url: module.url,
			dataType: "html",
			success: function( html ) {
				$module.html( html );
				if ( typeof module.title == "string" && module.title.length > 0 ) {
					var style = [];
					if ( module.toggle ) {
						style.push( "toggle" );
						style.push( "next" );
						if ( module.toggleOpen ) style.push( "open" );
					}
					$module.find( ".section-container" ).prepend( format( '<h6 class="page-header {1}"><i class="{0}"></i> {2}</h6>', [ module.icon, style.join( " " ), module.title ] ) );
				}
				
				var apps = base.init( true );
				$.each( apps, function( index, app ) {
					app.params = module.params;
					app.__data__ = module.__data__;
					app.content = $module;
					app.section = $module;
					app.__module__ = module;
					appObject.__apps__.push( app.__namespace__ );
				} );
				
				if ( typeof before == "function" ) before( apps );
				var apps = base.init();
				//if ( $.isArray( apps ) && apps.length == 1 ) apps = apps[ 0 ];
				module.events.shown( apps );
			},
			error: function() {
				if ( typeof error == "function" ) {
					error.call( module );
				}
				$module.remove();
			}
		} );
		
		return appObject;
		
	};
	
	Module.prototype.close = function( app, closed, data ) {
		if ( typeof app != "object" || typeof app.__module__ != "object" ) log.error( "JS: 非JsApp对象" );
		return app.__module__.events.hiden( closed, data );
	};
	
	return new Module();
	
} );
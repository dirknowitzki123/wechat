define( [ "business/frame/core/core.index"], function() {
	
	var $ = jQuery = require( "jquery" )
	, base = require( "app/base" )
	, tools = base.tools
	, log = base.log
	, message = base.message
	, page = require( "app/ui/page" )
	, App = base.constructor
	, format = tools.format;
	
	require( [ "css2!business/frame/protal/1.0/protal.index" ] );
	function Protal() {
		this.cache();
	};
	
	
	Protal.prototype.cache = function() {
		// 获取列表数据
		var menus = this.menus;
		$.ajax({
			url: "frame/protal/menulist",
			type: "POST",
			success: function ( data ) {
				var ds = [];
				$.each( data.list, function ( index, d ) {
					if ( d.menuType == "PAGE" ) {
						ds.push( d );
					}
				});
				ds = tools.toTreeJson(ds, {
					id: "id",
					pid: "parentMenuId"
				});
				menus.build( ds );
			},
			dataType: 'json'
		});
	};
	
	
	Protal.prototype.init = function() {
		$( "body" ).addClass( "protal-left-open" );
		$( "#PROTAL" ).fadeIn();
		
		// 窗口改变事件
		$( window ).resize( function() {
			var height = $( window ).height() - $(".protal-header-panel:visible").height();
			$( "#protalInnerPanel" ).css( "min-height", height);
			if ( $( "#sectionTabsHeader" ).is(":visible") ) {
				$( "#sectionsWrapper" ).css( "height", height - $( "#protalNavPanel:visible" ).outerHeight() - $("#sectionTabsHeader:visible").height() );
			}
		} ).trigger( "resize" );
		
		
		// 显示/隐藏左边菜单
		$( "#menuToggle" ).bind( "click", function() {
			$( "body" ).toggleClass( "protal-left-open" );
			$( window ).trigger( "resize" );
		} );
		
		this.menus.init();
		tabs.__init__();
		
		tabs.open( "welcome", "frame/homepage/index" );
	};
	
	function Menus() {};
	Protal.prototype.menus = new Menus();
	Menus.prototype.vars = {
		ready: false,
		data: false
	};
	Menus.prototype.init = function() {
		this.build( true );
		
		$( "#navMenus, #protalNavPanel" ).delegate( "li.nav-parent > a", "click", function ( event ) {
			var $parent = $(this).parent(),
			$sub = $parent.find( "> ul" );
			if ($sub.is(":visible")) {
				$sub.slideUp( 200 , function() {
					$parent.removeClass( "nav-active" );
				} );
				return false;
			}
			
			var $prev = $( "#navMenus" ).find( "li.nav-active" );
			setTimeout( function() {
				$prev.find( ">ul" ).slideUp( 150, function() {
					$prev.removeClass( "nav-active" );
				} );
			}, 100 );
			
			$parent.addClass("nav-active");
			$sub.slideDown( 200 );
			
			return false;
		});
		
		$( "#navMenus" ).delegate( "li:not(.nav-parent) > a", "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			
			var url = $( this ).attr( "href" )
			, title = $( this ).text()
			, id = "page" + $( this ).attr( "id" );
			
			tabs.open( id, title, url );
			return false;
		} );
		$( "#protalNavPanel" ).delegate( "li:not(.nav-parent) > a", "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			
			var url = $( this ).attr( "href" )
			, title = $( this ).text()
			, id = "page" + $( this ).attr( "id" );
			
			tabs.open( id, title, url );
			var $tab = $( "#protalNavPanel" ).find( ">li.nav-active" );
			setTimeout( function() {
				$tab.find( ">ul" ).slideUp( 300, function() {
					$tab.removeClass( "nav-active" );
				} );
			}, 100 );
			return false;
		} );
	};
	Menus.prototype.build = function( args1 ) {
		if ( typeof args1 == "boolean" ) this.vars.ready = true;
		else this.vars.data = args1;
		if ( !this.vars.ready || !this.vars.data ) return;
		
		var ds = this.vars.data;
		if ( ds.length == 1 && ds[ 0 ][ "children" ] ) {
			ds = ds[ 0 ][ "children" ];
		}
		$( "#navMenus" ).append( this.toHTML( ds, 0 ) );
		$( "#protalNavPanel" ).append( this.toHTML( ds, 0) );
		$( window ).trigger( "resize" );
	};
	Menus.prototype.toHTML = function( ds, level ) {
		var Menu = this, htmls = [], d;
		for ( var index = 0; index < ds.length; index++ ) {
			d = ds[ index ];
			if ( typeof d[ "id" ] == "undefined" && d[ "id" ] == "" ) d[ "id" ] = String(Math.random()).substring(2);
			if ( typeof d[ "children" ] != "undefined" &&  $.isArray( d[ "children" ] ) && d[ "children" ].length > 0 ) {
				
				if ( level == 0 ) htmls.push( "<li class='nav-parent'>" );
				else htmls.push( "<li>" );
				
				htmls.push( format( '<a id="{1}" href="javascript:;" ><i class="fa fa-th-list"></i>&nbsp;<span>{0}</span><i class="fa fa-angle-left pull-right"></i></a>', [ d[ "menuName" ], d[ "id" ] ] ) );
				htmls.push( format( "<ul class='list-unstyled children'>{0}</ul>", [ this.toHTML( d[ "children" ], level + 1 ) ] ) );
			} else {
				if ( typeof d.url == "string" && d.url.indexOf( "/" ) == 0 ) d.url = d.url.substring(1);
				htmls.push( "<li>" );
				htmls.push( format( '<a id="{2}" href="{1}" ><i class="fa fa-tasks"></i>&nbsp;<span>{0}</span></a>', [ d[ "menuName" ], d["url"], d[ "id" ] ] ) );
			}
			htmls.push( "</li>" );
		};
		
		return htmls.join( "" );
	};
	
	function TabPage() {};
	var tabs = App.prototype.page = new TabPage();
	TabPage.prototype.__init__ = function() {
		var $sectionTabsHeader = this.$sectionTabsHeader = $( "#sectionTabsHeader" )
			, $sectionTabs = this.$sectionTabs = $( "#sectionTabs")
			, $sections = this.$sections = $( "#sections" )
			, $sectionLoading = this.$sectionLoading = $( "#sectionLoading" );
		
		$sectionTabs.data("left", 0);
		var count = -1;
		$sectionTabs.data("width", $sectionTabsHeader.width());
		
		
		$sectionTabsHeader.find( ".nav-tabs-arrow-left" ).bind( "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			if ( count < 0 ) {
				if ( count != -1 ) count = -1;
				return false;
			}
			var left = 0;
			$sectionTabs.find( "li" ).each( function( index ) {
				if ( index >= count ) return false;
				left += $( this ).outerWidth();
			} );
			$sectionTabs.data( "left", left );
			if ( left > 1 ) left -= 1;
			left = ( 0 - left ) + "px";
			$sectionTabs.animate( { left: left } );
			count--;
			return false;
		} );
		
		$sectionTabsHeader.find(".nav-tabs-arrow-right").bind( "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			var left =  Number( $sectionTabs.data( "left" ) ) || 0;
			var headerWidth = $sectionTabsHeader.outerWidth();
			var tabsWidth = 50 * 2 - left;
			$sectionTabs.find( "li" ).each( function( index ) {
				tabsWidth += $( this ).outerWidth();
				if ( headerWidth < tabsWidth ) {
					left += tabsWidth - headerWidth;
					$sectionTabs.data( "left", left );
					left = ( 0 - left ) + "px";
					$sectionTabs.animate( { left: left } );
					count++;
					return false;
				}
			} );
			return false;
		} );
		
		$sectionTabs.delegate( "li > .nav-tabs-close", "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			var $tab = $( this ).parent()
				, ns = $tab.data( "ns" ) || [];
			base.die( ns );
			$tab.remove();
			
			$tab = $sectionTabs.find( "li:last" ).addClass( "active" );
			var pageId = $tab.attr( "pageid" );
			$( "#" + pageId ).addClass( "acitve" ).fadeIn( 200 );
			
			$( window ).trigger( "resize" );
			
			return false;
		});
		
		$sectionTabs.delegate( "li > a", "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			var $tab = $( this ).parent();
			if ( $tab.hasClass("active") ) return false;
			
			var pageid = $sectionTabs.find( "li.active" ).removeClass( "active" ).attr( "pageid" );
			
			$( "#" + pageid ).removeClass("acitve").hide();
			
			$tab.addClass( "active" );
			$( "#" + $tab.attr( "pageid" ) ).addClass( "acitve" ).fadeIn( 700 );
		});
		
	};
	TabPage.prototype.open = function( id, title, url ) {
		var $sectionTabsHeader = this.$sectionTabsHeader
			, $sectionTabs = this.$sectionTabs
			, $sections = this.$sections
			, $sectionLoading = this.$sectionLoading;
		
		if ( arguments.length == 1 ) {
			var args = arguments;
			url = args[2];
			title = args[1];
			id = args[0];
		} else if ( arguments.length == 2) {
			url = title;
			title = false;
		}
		
		$sectionLoading.show();
		
		// 当前菜单没有相应的加载的页面, 那查找之前是否有其他加载的页面，关闭/隐藏它们。
		var $tab = $sectionTabs.find( "li.active" ).removeClass( "active" );
		$sections.find( ".active" ).removeClass( "active" );
		
		if ( title !== false ) {
			$tab = $sectionTabs.find( "[pageid=" + id + "]" );
			if ( $tab.length == 0 ) {
				
				$tab = $( format( [ '<li class="active">',
				                    '	<a href="javascript:;">',
				                    '		<i class="fa fa-caret-right"></i>&nbsp;',
				                    '		<span>{0}</span></a>',
				                    '		<i class="fa fa-remove nav-tabs-close"></i>',
				                    '	</a>',
				                    '</li>'
				                    ], [ title ] ) ).appendTo($sectionTabs).attr( "pageid" , id);
				
			} else {
				$tab.addClass( "active" );
				var ns = $tab.data( "ns" );
				base.die( ns );
			}
		}
		
		$( window ).trigger( "resize" );
		
		page.open( {
			id: id,
			title: title,
			url: url + ".tpl",
			content: "#sectionsWrapper"
		}, function( apps ) {
			var ns = [];
			for ( var i = 0; i < apps.length; i++ ) {
				ns.push( apps[ i ].__namespace__ );
			}
			$tab.data( "ns", ns );
			$sectionLoading.fadeOut( 700 );
		} );
	};
	
	TabPage.prototype.close = function() {
		
	};

	var protal = new Protal();
	
	function Global() {
		var that = this;
		this.namespace = "protal.index";
		//this.content = "#protalSection";
		var handlers = this.handlers = {};
		handlers.global = this.global = function() {
			return that;
		}; 
		
		this.init = function() {
			this.layout();
		};
		
		this.layout = function() {
			var that = this.global();
			
			that.selector.find( "#protalModifyPassword" ).bind( "click", function( event ) {
				that.handlers.modifyPassword();
			} );
			
			that.selector.find( "#protalLogout" ).bind( "click", function( event ) {
				that.handlers.logout();
			} );
			
		};
		
		handlers.modifyPassword = function() {
			var that = this.global();
			that.modal.open( {
				title: "修改密码",
				size: "modal-md",
				url: "frame/protal/modifyPassword"
			} );
		};
		
		handlers.logout = function() {
			var that = this.global();
			that.dialog.confirm( "确认用户注销?", function( event, index ) {
				if ( index == 0 ) {
					$.ajax( {
						url: "frame/logout",
						type: "POST",
						success: function ( data ) {
							if ( !data.success ) {
								return message.error( data.msg );
							}
							message.success( "用户注销成功，马上跳转..." );
							setTimeout( function(){
								window.location.reload(true);
							}, 700 );
						},
						dataType: "json"
					} );
				}
			} );
		};
	}
	
	$( function() { 
		protal.init();
		$( "#PROTAL" ).attr( "namespace", "protal.index" ).css( {
			"padding": "0" 
		} ).prepend( '<div class="sections-wrapper"></div>' );
		base.init( Global );
		base.init();
	} );
	
	
	
	
	
	
} );
define( [ "frame/core/core.index", "jquery.slimscroll" ], function() {
	
	var $ = jQuery = require( "jquery" )
	, base = require( "app/base" )
	, tools = base.tools
	, log = base.log
	, message = base.message
	, page = require( "app/ui/page" )
	, App = base.constructor
	, format = tools.format;
	
	require( [ "css2!frame/protal/1.1/protal.core" ], function() {
		$( function() {
			$( ".sections:eq(0)" ).fadeIn( function() {
				$( document ).trigger( "main.resize" );
			} );
		} );
	} );
	
	
	$( document ).bind( "resize.section", function( event, width, height ) {
		var width = $( WW ).width(), height = $( window ).height();
		$( "#mainSection" ).css( "height", height - $( "#mainHeader" ).height() );
	} );
	
	function Protal( selector, sub, toggle ) {
		this.settings = {
			ready: false,
			data: false,
			selector: selector,
			sub: sub,
			toggle: toggle
		};
		
		this.cache();
	};
	Protal.prototype.cache = function() {
		var _this = this;
		$.ajax( {
			url: "frame/protal/menulist",
			type: "POST",
			success: function ( data ) {
				var ds = data.list;
				_this.settings.data = tools.toTreeJson(ds, {
					id: "id",
					pid: "parentMenuId"
				} );
				_this.init();
			},
			dataType: 'json'
		} );
	};
	Protal.prototype.init = function( ready ) {
		if ( typeof ready == "boolean" ) this.settings.ready = ready;
		if ( this.settings.ready == false || this.settings.data == false ) { return; }
		var ds = this.settings.data;
		if ( ds.length == 1 && ds[ 0 ][ "children" ] ) {
			ds = ds[ 0 ][ "children" ];
		}
		
		var $nav = this.$nav = $( this.settings.selector ).parent();
		var $subs = this.$subs = $( this.settings.sub );
		var $selector = this.$selector = $( this.settings.selector );
		
		$selector.html( this.generate( ds, 0 ) );
		
		this.events();
	};
	Protal.prototype.generate = function( ds, level ) {
		var Menu = this, htmls = [], d;
		for ( var index = 0; index < ds.length; index++ ) {
			d = ds[ index ];
			if ( typeof d[ "id" ] == "undefined" && d[ "id" ] == "" ) d[ "id" ] = String(Math.random()).substring(2);
			if ( typeof d[ "children" ] != "undefined" &&  $.isArray( d[ "children" ] ) && d[ "children" ].length > 0 ) {
				
				if ( level == 0 ) htmls.push( format( "<li class='nav-parent' sub-index=\"{0}\">", [ level + "-" + index ] ) );
				else htmls.push( "<li>" );
				
				htmls.push( format( '<a id="{1}" href="javascript:;" ><i class="{2}"></i>&nbsp;<span>{0}</span></a>', [ d[ "menuName" ], d[ "id" ], d[ "menuIcon" ] || "fa fa-list-alt" ] ) );
				
				this.$subs.append( format( "<ul class='list-unstyled children' index='{1}'>{0}</ul>", [ this.generate( d[ "children" ], level + 1 ), level + "-" + index ] ) );
				
				//htmls.push(  );
			} else {
				if ( typeof d.url == "string" && d.url.indexOf( "/" ) == 0 ) d.url = d.url.substring(1);
				htmls.push( "<li>" );
				htmls.push( format( '<a id="{2}" href="{1}" ><i class="{3}"></i>&nbsp;<span>{0}</span></a>', [ d[ "menuName" ], d["url"], d[ "id" ], d[ "menuIcon" ] || "fa fa-file" ] ) );
			}
			htmls.push( "</li>" );
		};
		
		return htmls.join( "" );
	};
	Protal.prototype.events = function() {
		var $nav = this.$nav,
			$selector = this.$selector,
			$subs = this.$subs,
			$mainSubNav = $( ".main-subnavigations" );
		
		
		$nav.find( this.settings.toggle ).bind( "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			//$nav.toggleClass( "closed" );
			$( "body" ).toggleClass( "nav-closed", function( event ) {
				setTimeout( function() {
					$( window ).trigger( "resize" );
				}, 500 );
			} );
			return false;
		} );
		
		$selector.delegate( "li.nav-parent", "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			var subIndex = $( this ).attr( "sub-index" );
			
			var $sub = $subs.find( format( "ul[index=\"{0}\"]", [ subIndex ] ) );
			$mainSubNav.show();
			$subs.find( "ul:visible" ).not( $sub ).hide();
			$sub.show();
			
			return false;
		} );
		
		$nav.delegate( "li:not(.nav-parent) > a", "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			
			var url = $( this ).attr( "href" )
			, title = $( this ).text()
			, id = "page" + $( this ).attr( "id" );
			
			tabs.open( id, title, url );
			
			$subs.find( "ul:visible" ).hide();
			$mainSubNav.hide();
			return false;
		} );
		
		$( document ).bind( "click", function( event ) {
			$subs.find( "ul:visible" ).hide();
			$mainSubNav.hide();
		} );
		
		$( document ).bind( "main.resize.navigations", function( event, width, height ) {
			if ( typeof width != "number" ) {
				width = $( window ).width();
				height = $( window ).height();
			}
			var h = height - $( "#mainHeader:visible" ).height();
			
			var $nav1 = $( "#navigations" ),
				$sub1 = $( "#subnavigations" );
			
			if ( !$sub1.is( ".had-init-slimscroll" ) ) {
				$sub1.addClass( "had-init-slimscroll" ).slimScroll( {
					wrapperClass: "slimscrolldiv",
			        width: 'auto', //可滚动区域宽度
			        height: h //可滚动区域高度
			    } );
			} else {
				$sub1.css( "height", h );
				$sub1.parent().css( "height", h );
			}
			
			if ( !$nav1.is( ".had-init-slimscroll" ) ) {
				$nav1.addClass( "had-init-slimscroll" ).slimScroll( {
					wrapperClass: "slimscrolldiv",
			        width: 'auto', //可滚动区域宽度
			        height: h - 45 //可滚动区域高度
			    } );
			} else {
				$nav1.css( "height", h - 45 );
				$nav1.parent().css( "height", h - 45 );
			}
		} ).trigger( "main.resize.navigations" );
		
	};
	var protal = new Protal( "#navigations", "#subnavigations" , "#toggle" );
	$( document ).bind( "main.ready.protal", function( event ) {
		protal.init( true );
	} );
	
	
	function TabPage() {};
	var tabs = App.prototype.page = new TabPage();
	TabPage.prototype.init = function() {
		var $sectionTabsHeader = this.$sectionTabsHeader = $( "#sectionTabsHeader" )
			, $sectionTabs = this.$sectionTabs = $( "#sectionTabs")
			, $sections = this.$sections = $( "#sections" )
			, $sectionsWrapper = this.$sectionsWrapper = $( "#sectionsWrapper" )
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
				if ( index != 0 ) {
					left += 5;
				}
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
			var tabsWidth = 51 * 2 - left;
			$sectionTabs.find( "li" ).each( function( index ) {
				tabsWidth += $( this ).outerWidth();
				if ( index != 0 ) {
					tabsWidth += 5;
				}
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
			
			$tab = $sectionTabs.find( "li:eq(0)" ).addClass( "active" );
			var pageId = $tab.attr( "pageid" );
			$( "#" + pageId ).show().addClass( "active" );
			
			if ( $sectionTabs.is( ":empty" ) && $sectionTabs.is( ":visible" ) ) {
				$( "#welcome" ).show().addClass( "active" );
				$sectionTabsHeader.hide();
				$( window ).trigger( "main.resize");
			}
			
			//$( document ).trigger( "resize" );
			
			return false;
		});
		
		$sectionTabs.delegate( "li > a", "click", function( event ) {
			event.preventDefault();
			event.stopPropagation();
			var $tab = $( this ).parent();
			if ( $tab.hasClass( "active" ) ) return false;
			
			var pageid = $sectionTabs.find( "li.active" ).removeClass( "active" ).attr( "pageid" );
			
			$( "#" + pageid ).removeClass("active").hide();
			$tab.addClass( "active" );
			$( "#" + $tab.attr( "pageid" ) ).show().addClass( "active" );
		});
		
		$( document ).bind( "main.resize.tabpage.section", function( event, width, height ) {
			if ( typeof width != "number" ) {
				width = $( window ).width();
				height = $( window ).height();
			}
			
			var h = height - $( "#mainHeader:visible" ).height();
			if ( $sectionTabsHeader.is( ":visible" ) ) {
				h -= $sectionTabsHeader.height();
			}
			var w = width - $( "#mainNavigation:visible" ).width();
			$sectionsWrapper.css( "height", h + "px" );
			$sectionsWrapper.find( ".section" ).each( function() {
				$( this ).trigger( "section.resize", [ w, h ] );
			} );
		} );
		
		$sectionsWrapper.delegate( ".section", "section.resize", function( event, width, height ) {
			event.preventDefault();
			event.stopPropagation();
			
			if ( typeof width != "number" ) {
				width = $sectionsWrapper.width();
			}
			if ( typeof height != "number" ) {
				height = $sectionsWrapper.height();
			}
			
			$( this ).trigger( "section.custom.resize", [ width, height ] );
			return false;
		} );
		

	};
	TabPage.prototype.open = function( id, title, url ) {
		var $sectionTabsHeader = this.$sectionTabsHeader
			, $sectionTabs = this.$sectionTabs
			, $sections = this.$sections
			, $sectionsWrapper = this.$sectionsWrapper
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
		$sectionsWrapper.find( ">.section.active" ).each( function() {
			$( this ).hide().removeClass( "active" );
		} );
		
		if ( title !== false ) {
			$tab = $sectionTabs.find( "[pageid=" + id + "]" );
			if ( $tab.length == 0 ) {
				
				$tab = $( format( [ '<li class="active">',
				                    '	<a href="javascript:;">',
				                    '		<i class="fa fa-caret-right"></i>',
				                    '		<span>{0}</span></a>',
				                    '		<i class="nav-tabs-close">x</i>',
				                    '	</a>',
				                    '</li>'
				                    ], [ title ] ) ).attr( "pageid" , id);
				$tab.hide();
				$sectionTabs.prepend( $tab );
			} else {
				$tab.addClass( "active" );
				var ns = $tab.data( "ns" );
				base.die( ns );
			}
		}
		
		$( document ).trigger( "main.resize.tabpage.section" );
		
		var params = {};
		if ( url.indexOf( "http://" ) == 0 || url.indexOf( "https://" ) == 0 ) {
			params.url = url;
			title = false;
			url = "frame/iframe/index";
		}

		
		page.open( {
			id: id,
			title: title,
			params: params,
			url: url + ".tpl",
			content: $sectionsWrapper
		}, function( apps ) {
			var ns = [];
			for ( var i = 0; i < apps.length; i++ ) {
				ns.push( apps[ i ].__namespace__ );
			}
			$tab.data( "ns", ns );
			$sectionLoading.fadeOut( 700 );
			
			if ( $tab.is( ":hidden" ) ) {
				$tab.show();
			}
		}, function() {
			$sectionLoading.hide();
			$tab.find( ".nav-tabs-close" ).trigger( "click" );
		} );
		
		if ( !$sectionTabs.is( ":empty" ) && $sectionTabsHeader.is( ":hidden" ) ) {
			$sectionTabsHeader.show();
			$( document ).trigger( "main.resize");
		}
	};
	
	$( document ).bind( "main.ready.tabpage", function( event ) {
		tabs.init();
		$( document ).trigger( "main.resize.tabpage.section" );
		tabs.open( "welcome", "frame/homepage/index" );
	} );
	
	
	
	function Header() {};
	Header.prototype.init = function() {
		var $barToggle = this.$barToggle = $( "#barToggle" );
		var $verticalHeaderNav = this.$verticalHeaderNav = $( "#verticalHeaderNav" );
		var $headerNavs = this.$headerNavs = $( "#headerNavs" );
		
		this.events();
	};
	Header.prototype.events = function() {
		var $barToggle = this.$barToggle,
			$verticalHeaderNav = this.$verticalHeaderNav;
		
		$barToggle.bind( "click", function( event ) {
			$verticalHeaderNav.toggle();
			return false;
		} );
		
		$( document ).bind( "click.main.header", function( event ) {
			if ( $verticalHeaderNav.is( ":visible" ) ) {
				$verticalHeaderNav.hide();
			}
		} );
		
	};
	
	var header = new Header();
	$( document ).bind( "main.ready.header", function( event ) {
		header.init();
	} );
	
	$( window ).resize( function() {
		$( document ).trigger( "main.resize" );
	} );
	
	$( function(){ 
		$( document ).trigger( "main.resize" );
		$( document ).trigger( "main.ready" );
	} );
	
} );
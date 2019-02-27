define( [ "app/base" ], function () {
	
	var base = require( "app/base" )
		, $ = require( "jquery" );
	
	var Shiro = function( target, options ) {
		var $target = this.$target = $( target ); 
		var opts = this.opts = $.extend( true, {}, this.settings, options ) ;		
		
		$target.attr( "disabled", "disabled" );
		
		var perms = opts.perms || [], perm, perms_not = [], r, t;
		
		var stop = false;
		
		for ( var index = 0; index < perms.length; index++ ) {
			perm = perms[ index ];
			perm = perms[ index ] = $.trim( perm );
			r = this.__cache__[ perm ];
			t = typeof r;
			
			if ( t != "boolean" ) {
				perms_not.push( perm );
				continue;
			}
			
			//并且关系判断
			if ( !opts.permsOr ) {
				if ( r ) continue;
				this.$target.hide();
				stop = true;
				break;
			}
			
			
			//或者关系
			if ( !r ) continue;
			this.$target.removeAttr( "disabled" );
			stop = true;
		}
		
		if ( stop ) return;
		
		var roles = opts.roles || [], role, roles_not = [], r, t;
		for ( var index = 0; index < roles.length; index++ ) {
			role = $.trim( roles[ index ] );
			r = this.cache( role );
			t = typeof r;
			
			if ( r != "boolean" ) {
				roles_not.push( role );
				continue;
			}
			
			//并且关系判断
			if ( !opts.rolesOr ) {
				if ( r ) continue;
				this.$target.hide();
				stop = true;
				break;
			}
			
			
			//或者关系
			if ( !r ) continue;
			this.$target.removeAttr( "disabled" );
			stop = true;
		}
		
		if ( stop ) return;
		
		if ( perms_not.length == 0 && roles_not.length == 0 ) {
			$target.hide();
		}
		
		this.cache( perms_not, roles_not );
		
	};
	
	
	Shiro.prototype.settings = {
		permOrRole: true,
		permsOr: true,
		roleOr: true,
		perms: [],
		roles: []
	};
	
	Shiro.prototype.__cache__ = {};
	
	Shiro.prototype.cache = function( perms, roles ) {
		if ( !( typeof perms == "object" && perms instanceof Array ) ) perms = [];
		if ( !( typeof roles == "object" && roles instanceof Array ) ) roles = [];
		
		if ( perms.length == 0 && roles.length == 0 ) {
			shiro.$target.hide();
			return;
		}
		
		var shiro = this;
		
		var data = [];
		for ( var index = 0; index < perms.length; index++ ) {
			data.push( "perms[" + index + "]=" + 222 );
		}
		/*for ( var index = 0; index < roles.length; index++ ) {
			data.push( "roles[" + index + "]=" + roles[index] );
		}*/
		
		$.ajax( {
			url: "frame/shiro/perms",
			type: "POST",
			data: { perms: perms, roles: roles },
			success: function( data ) {
				var map = data.map || {}, key;
				for ( var index = 0; index < perms.length; index++ ) {
					key = perms[ index ];
					shiro.__cache__[ key ] = map[ key ];
				}
				
				for ( var index = 0; index < roles.length; index++ ) {
					key = roles[ index ];
					shiro.__cache__[ key ] = map[ key ];
				}
				
				//并且关系
				if ( ( shiro.opts.permsOr && map.valid ) 
					|| ( !shiro.opts.permsOr && map.all ) ) {
					shiro.$target.removeAttr( "disabled" );
					return;
				}
				shiro.$target.hide();
			}
		} );
		
		
	};
	
	$.fn.shiro = function( options ) {
		return $( this ).each( function() {
			var opts = $( this ).attr( 'data-shiro' ) || '';
			if ( typeof opts == "string" && opts.length > 0 ) {
				opts = $.trim( opts );
				if ( opts.indexOf( "{" ) != 0 || opts.indexOf( "[" ) != 0 ) {
					opts = { perms: opts.split( "," ) };
				} else {
					try {
						opts =  (new Function("return " +  opts ))();
					} catch (e) { 
						opts = {};
					}
				}
			} else if ( typeof opts != "object" ) {
				opts = {};
			}
			
			if ( typeof opts == "object" && opts instanceof Array ) {
				opts = { perms: opts };
			}
			
			opts = $.extend(true, {}, options, opts);
			
			$( this ).data( "shiro", new Shiro( this, opts ) );
		} );
	};
	
	
	return Shiro;
} );
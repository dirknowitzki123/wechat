define(function () { 
	
	function Global( vars ) {
		//=======================================================
		// 获取基础组件
		//=======================================================
		var base = require( "app/base" )
			, message = base.message
			, tools = base.tools
			, format = tools.format;
		
		base.code.cache( "Is_No,Aut_Type,Org_Match_Type,Aut_Code_Type" );
		
		//=======================================================
		// 当前组件
		//=======================================================
		var that = this; //全局对象
		var vars = this.vars = {};//全局变梁
		var handlers = this.handlers = {};//处理程序
		handlers.global = function() { return that; };
		
		//组件入口函数  相当于java.main
		that.init = function() {
			this.layout();
			this.load();
		};
		
		//初始化远程请求处理
		that.load = function() {
			var that = this.global();
			that.handlers.load();
		};
		
		
		//页面布局
		that.layout = function() {
			var that = this.global();//获取组件的全局对象
			//保存事件
			that.selector.find( "#submitBtn" ).click( function(event) {
				that.handlers.save();
				return false;
			});
			//关闭事件
			that.selector.find( "#closeBtn" ).click( function(event) {
				that.close();
				return false;
			});
			that.selector.find( ".input" ).input( {
				cssCol: "col-xs-12",
				suffixButtons: [ "添加" ],
				suffixButtonsHandler: function( event, index ) {
					that.handlers.add( that.selector.find( "input[name=sqlMapId]" ).val() );
				}
			} );//实例input插件
			
			that.selector.find( "#mapperIdUl" ).delegate( "a", "click", function( event ) {
				$(this).parent().remove();
			} );
			
		};
		
		
		//=======================================================
		// 业务逻辑申明
		//=======================================================
		
		handlers.add = function( sqlMapId ) {
			var that = this.global();
			if ( typeof sqlMapId != "string" || sqlMapId.trim().length == 0 ) {
				message.error( "请输入正确的mapperID！" );
				return;
			}
			var sqlItems = that.selector.find( "#mapperIdUl li[sqlmapid=\""+sqlMapId+"\"]" );
			if(sqlItems.length>0){
				message.error( "重复的mapperID！" );
				return;
			}
			
			that.selector.find( "#mapperIdUl" ).append( format( '<li sqlmapid="{0}">{0}<a href="javascript:;"><i class="fa fa-remove"><i></a></li>', [ sqlMapId ] ) );
		};
		
		/** 加载数据*/
		handlers.load = function( item ) {
			var that = this.global();
			
			if ( typeof that.params.sqlMap != "string"  ) that.params.sqlMap = "";
			if ( that.params.sqlMap.length == 0 ) return;
			var sqlMapIds = that.params.sqlMap.split( "," );
			var $mapperIdUl = that.vars.$mapperIdUlVar = that.selector.find( "#mapperIdUl" ), sqlMapId;
			for ( var index = 0; index < sqlMapIds.length; index++ ) {
				sqlMapId = sqlMapIds[ index ];
				$mapperIdUl.append( format( '<li sqlmapid="{0}">{0}<a href="javascript:;"><i class="fa fa-remove"><i></a></li>', [ sqlMapId ] ) );
			}
		};
		
		/** 保存*/
		handlers.save = function() {
			var that = this.global();
			var sqlMapIds = [];
			that.selector.find( "#mapperIdUl" ).find( "li" ).each( function( event ) {
				sqlMapIds.push( $( this ).attr( "sqlmapid" ) );
			} );
			
			var mapId = that.selector.find( "input[name=sqlMapId]" ).val()+'';
			if(sqlMapIds.length==0 && $.trim(mapId).length!=0){
				sqlMapIds.push( mapId );
			}
			
			that.close( sqlMapIds.join(",") );
		};
	};
	
	return Global;
});
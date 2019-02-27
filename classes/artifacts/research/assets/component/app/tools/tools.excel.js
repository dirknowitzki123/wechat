define( [ "app/base" ], function ( base ) {
	var $ = jQuery = require( "jquery" )
		, base = require( "app/base" )
		, tools = base.tools
		, Tools = tools.constructor
		, log = base.log;
	/**
	 *导出excel
	 */
	tools.exportExcel = function (url){
		var iframeDown = document.getElementById("_download_attribExcel");
		if(!iframeDown){
			iframeDown = document.createElement('iframe');
			iframeDown.width = '0';
			iframeDown.height = '0';
			iframeDown.name = "_download_attribExcel";
			iframeDown.id = "_download_attribExcel";
			if (iframeDown.attachEvent){
				iframeDown.attachEvent("onload", function(){
					base.message.error("导出失败!<br/>"+$(window.frames["_download_attribExcel"].document).find("div[class='alert alert-danger']").html());
			    });
			} else {
				iframeDown.onload = function(){
					base.message.error("导出失败!<br/>"+$(window.frames["_download_attribExcel"].document).find("div[class='alert alert-danger']").html());
			    };
			}
			document.body.appendChild(iframeDown);
		}
		iframeDown.src = url;
	};
} );
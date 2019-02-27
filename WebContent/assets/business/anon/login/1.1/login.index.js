define( [ "bootstrap", "validator" ], function () {
	var $ = require( "jquery" )
		, validator = require('validator');

	require( [ "css2!anon/login/1.1/login.index" ] );
/**
 * Created by WW on 2016/3/21.
 */
$(function(){
//    var bodyHei = parseFloat($(window).height());
//    var newHei = bodyHei-136;
//    $(".login-left").css("height",newHei+"px");

    if(parseFloat($(document).width()) < 940){
        $(".login-left").hide();
        $(".get-class").removeClass("login-right");
        $(".login-box").css({
            "position":"absolute",
            "left":"50%",
            "marginLeft":"-200px"
        });
    }else{
        $(".login-left").show();
        $(".get-class").addClass("login-right");
        $(".login-box").css({
            "position":"none",
            "left":"0",
            "marginLeft":"0px"
        });
    }


    if($('input[name="rem-me"]').is(':checked')){
        $(".checkbox-box").css("display","inline-block");
    }else{
        $(".checkbox-box").hide();
    }
    $('input[name="rem-me"]').click(function(){
        $(".checkbox-box").css("display","inline-block");
    });
    $(".checkbox-box").click(function(){
        $('input[name="rem-me"]').prop("checked",false);
        $(this).hide();
    });

});

$(window).resize(function() {
    if(parseFloat($(document).width()) < 940){
        $(".login-left").hide();
        $(".login-right").removeClass("login-right");
        $(".login-box").css({
            "position":"absolute",
            "left":"50%",
            "marginLeft":"-200px"
        });
    }else{
        $(".login-left").show();
        $(".get-class").addClass("login-right");
        $(".login-box").css({
            "position":"static",
            "left":"0",
            "marginLeft":"0px"
        });
    }
});

});
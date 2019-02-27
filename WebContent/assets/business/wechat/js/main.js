function style(){
	$(".register, .cover").css("height", $(window).height()+"px");
	
}
//  send code
var countdown=60;
function settime(obj) {
    if (countdown == 0) {
        obj.removeAttribute("disabled");
        obj.value="获取验证码";
        obj.style.color = "#88C6FF";
        countdown = 60;
        return;
    } else {
        obj.setAttribute("disabled", true);
        obj.value="重新发送(" + countdown + ")";
        obj.style.color = "#BFBEBE";
        countdown--;
    }
    setTimeout(function() {
            settime(obj) }
        ,1000)
}
$(function(){
	style();
	
	$("#subBtn").click(function(){
		$(".cover").show();
		setTimeout(function () { 
	        location.href="login.html";
	    }, 500);
	});
	$("#regNext").click(function(){
		location.href="registerPerson.html";
	});
	$("#alertNext").click(function(){
		location.href="alterPwd.html";
	});
	$("#sureBtn").click(function(){
		location.href="alterSucceed.html";
	});
	$("#goHome").click(function(){
		location.href="myInfo.html";
	});
});
$(window).resize(function(){
	style();
});

//  className区别颜色的class text提示语  time弹框展示时间  obj提示框位置  top提示框top值  right提示框right值
//  className值：success 绿色 fail 红色 warning 橙色
function toast(className, text, time, obj, top, right) {
	$('.point').remove();
	var divObj = '<div class="point '+className+'" style="top:'+top+';right:'+right+';">'+ text + '</div>';
	$(obj).before(divObj);
	if (time > 0) {
		setTimeout(function() {
			$('.point').remove();
		}, time);
	}
}


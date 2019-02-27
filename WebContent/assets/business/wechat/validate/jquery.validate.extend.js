(function ($) {
	/*
	 * 扩展jQuery validate 插件 中文提示
	 * */
	$.extend($.validator.messages, {
		required: "{vname} : 请输入字符",
		remote: "{vname} : 请重新输入有效的字符",
		email: "{vname} : 请输入正确格式的电子邮件",
		url: "{vname} : 请输入合法的网址",
		date: "{vname} : 请输入合法的日期",
		dateISO: "{vname} : 请输入合法的日期 .",
		number: "{vname} : 请输入合法的数字",
		digits: "{vname} : 请输入是整数数字",
		creditcard: "{vname} : 请输入合法的信用卡号",
		equalTo: "{vname} : 请输入输入相同的值",
		accept: "{vname} : 请输入合法后缀名",
		maxlength: $.validator.format("{vname} :长度最大长度{0}"),
		minlength: $.validator.format("{vname} :长度最小长度{0}"),
		rangelength: $.validator.format("{vname} :长度介于 {0} 和 {1}"),
		range: $.validator.format("{vname} :介于 {0} 和 {1}"),
		max: $.validator.format("{vname} :最大为 {0}"),
		min: $.validator.format("{vname} :最小为 {0}")
	});
	/*
	 * 扩展jQuery validate 插件提示信息
	 */
	$.validator.setDefaults( {
		ignore: '.ignore',
		submit: false, //提交时验证。设置为 false 就用其他方法去验证。 	true
		onfocusout: false, //失去焦点时验证（不包括复选框/单选按钮）。 	true
		onkeyup: false, //在 keyup 时验证。 	true
		onclick: false, //在点击复选框和单选按钮时验证。 	true
		//focusInvalid: false, //提交表单后，未通过验证的表单（第一个或提交之前获得焦点的未通过验证的表单）会获得焦点。 	true
		//focusCleanup: true, //如果是 true 那么当未通过验证的元素获得焦点时，移除错误提示。避免和 focusInvalid 一起用。
		errorPlacement : function(error, element){
			$(element).parent().addClass('has-error');
			var $error = $(element).prev().find('ui-icon-warn');
			if ($error.length != 0) $error.attr('error', $(error).text());
			else $(element).parent().children().eq(0).append('<i class="ui-icon-warn" error="'+$(error).text()+'"></i>');
		},
		success: function(error, element){
			$(element).parent().removeClass('has-error');
			$(element).parent().children().eq(0).find('i.ui-icon-warn').hide();
		},
		showErrors : function(errors){
			if(errors){
				var msgs = [], msg = null, ele = null, vname = null;
				for ( var name in errors ) {
					msg = errors[ name ];
					msgs[msgs.length] = msg;
				}
				if(!msgs[0]){
					return false;
				}{
                    toast("fail", msgs[0], 1500, "body", "16%", "20px");
                }
				// if(msgs) app.message.error(msgs[0]);
			}
			this.defaultShowErrors();
		}
	});

	$.validator.prototype._resetForm = $.validator.prototype.resetForm;

	$.extend($.validator.prototype, {
		validGroup: function(group){
			this.prepareForm();
			var elements = $(group);
			for ( var i = 0; elements[ i ]; i++ ) {
				if ($(elements[ i ]).hasClass('ignore')) continue;
				if (elements[ i ][ 'name' ]) this.check( elements[ i ] );
			}
			elements = $("input[type=text], input[type=checkbox], input[type=radio], textarea", group).not(':hidden');
			for ( var i = 0; elements[ i ]; i++ ) {
				if ($(elements[ i ]).hasClass('ignore')) continue;
				if (elements[ i ][ 'name' ]) this.check( elements[ i ] );
			}
			this.showErrors();
			return this.valid();
		},
		resetForm: function () {
			this._resetForm();
			$(this.currentForm).find('.has-error').removeClass('has-error');
		}
	});

	$.fn.showError = function (method, message) {
		if (typeof method != 'function') throw new Error("method is typeof function");
		if (typeof message != 'string') throw new Error('message is typeof string');
		var result = method.call(this), element = this;
		if (typeof result == 'string') {message = result; result = false}
		else if (typeof result != 'boolean') result = false;
		if (result) {
			$(element).parent().removeClass('has-error');
			$(element).parent().children().eq(0).find('i.ui-icon-warn').hide();
			return true;
		}

		$(element).parent().addClass('has-error');
		var $error = $(element).prev().find('ui-icon-warn');
		if ($error.length != 0) $error.attr('error', $(error).text());
		else $(element).parent().children().eq(0).append('<i class="ui-icon-warn" error="' + message + '"></i>');
		app.message.error(message);
		return false;
	};


})(jQuery);
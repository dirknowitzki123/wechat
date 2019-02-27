package com.by.system.service;

public interface IASysMsgCaptchaService {
	
	public final static String CHECK_RESLT_YES = "Verified";
	public final static String CHECK_RESLT_NO = "Verifing";
	
	/**
	 * 发送手机短信验证码
	 * @param mobile
	 */
	void sendCaptcha(String mobile);
	
	/**
	 * 校验手机短信验证码 校验失败会抛异常
	 * @param mobile
	 * @param code
	 */
	void validateCaptcha(String mobile,String code);
}


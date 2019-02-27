package com.by.core.exception;
/**
 * 登录异常
 */
public class LoginException extends BaseException{
	private static final long serialVersionUID = 1L;

	public LoginException(String message) {
		super(message);
	}

	public LoginException(String message, String code) {
		super(message, code);
	}

	public LoginException(Throwable exception) {
		super(exception);
	}

	public LoginException(String message, String code, Throwable exception) {
		super(message, code, exception);
	}
}

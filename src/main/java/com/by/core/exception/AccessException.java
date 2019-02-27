package com.by.core.exception;
/**
 * 数据层访问异常
 */
public class AccessException extends BaseException {
	private static final long serialVersionUID = 1L;

	public AccessException(String message) {
		super(message);
	}

	public AccessException(String message, String code) {
		super(message, code);
	}

	public AccessException(Throwable exception) {
		super(exception);
	}

	public AccessException(String message, String code, Throwable exception) {
		super(message, code, exception);
	}
}

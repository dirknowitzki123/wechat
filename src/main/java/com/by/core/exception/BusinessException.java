package com.by.core.exception;
/**
 * 业务异常
 */
public class BusinessException extends BaseException {
	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, String code) {
		super(message, code);
	}

	public BusinessException(Throwable exception) {
		super(exception);
	}

	public BusinessException(String message, String code, Throwable exception) {
		super(message, code, exception);
	}
}

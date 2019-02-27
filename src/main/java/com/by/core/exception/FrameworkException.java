package com.by.core.exception;

/**
 * 框架异常
 */
public class FrameworkException extends BaseException{
	private static final long serialVersionUID = 1L;

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(String message, String code) {
		super(message, code);
	}

	public FrameworkException(Throwable exception) {
		super(exception);
	}

	public FrameworkException(String message, String code, Throwable exception) {
		super(message, code, exception);
	}
}

package com.by.core.exception;

public class WeChatSessionException extends BaseException{
	private static final long serialVersionUID = 1L;

	public WeChatSessionException() {
		super();
	}

	public WeChatSessionException(String msg, String code, Throwable thrown) {
		super(msg, code, thrown);
	}

	public WeChatSessionException(String msg, String code) {
		super(msg, code);
	}

	public WeChatSessionException(String msg) {
		super(msg);
	}

	public WeChatSessionException(Throwable thrown) {
		super(thrown);
	}
	
}

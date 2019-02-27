package com.by.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Throwable exception;
	private String message;
	private String code;

	public BaseException() {
		initCause(null);
	}

	public BaseException(String msg) {
		super(msg);
		initCause(null);
		this.message = msg;
	}

	public BaseException(String msg, String code) {
		super(msg);
		initCause(null);
		this.message = msg;
		this.code = code;
	}

	public BaseException(Throwable thrown) {
		initCause(null);
		exception = thrown;
	}

	public BaseException(String msg, String code, Throwable thrown) {
		super(msg);
		initCause(null);
		this.message = msg;
		this.code = code;
		this.exception = thrown;
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream outStream) {
		printStackTrace(new PrintWriter(outStream));
	}

	public void printStackTrace(PrintWriter writer) {
		super.printStackTrace(writer);
		if (getException() != null) {
			getException().printStackTrace(writer);
		}
		writer.flush();
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
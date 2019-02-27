package com.by.frame.intfc.model;

import java.io.Serializable;

/**
 * 接口返回对象
 * @author HeJian
 *
 */
public class IntfcResult implements Serializable{
	private static final long serialVersionUID = -3464610023663143401L;
	
	private String code;
	private String msg;
	private Object result;
	
	public IntfcResult() {	}
	
	public IntfcResult(String code,Object result) {
		super();
		this.code = code;
		this.result = result;
	}
	
	public IntfcResult(String code, String msg, Object result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
	
}

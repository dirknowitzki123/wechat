package com.by.frame.intfc.model;

import java.io.Serializable;

public class TransModel implements Serializable{
	private static final long serialVersionUID = -7399547820874884951L;
	
	/*** 交易头，json格式 */
	private String transHead;
	/*** 交易体，json格式数据RSA公钥加密后的字符串*/
	private String transBody;
	
	public TransModel() {}
	
	public TransModel(String transHead, String transBody) {
		super();
		this.transHead = transHead;
		this.transBody = transBody;
	}
	
	public String getTransHead() {
		return transHead;
	}
	public void setTransHead(String transHead) {
		this.transHead = transHead;
	}
	public String getTransBody() {
		return transBody;
	}
	public void setTransBody(String transBody) {
		this.transBody = transBody;
	}
	
}

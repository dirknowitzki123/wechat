package com.by.frame.intfc.model;

import java.io.Serializable;

public class TransResult implements Serializable{
	private static final long serialVersionUID = -3464610023663143401L;

	/*** 返回码 ，非空*/
	private String code;
	/*** 返回消息，错误的时候一定非空 */
	private String msg;
	/*** 请求编号；32，可空 */
	private String reqNo;
	/*** 接口版本：0.1.0  可空*/
	private String version;
	/*** 接口名称：非空 */
	private String service;
	/*** 客户端标志 : 非空*/
	private String clientFlag;
	/*** AES密钥，经RSA公钥加密，非空 */
	private String secretKey;
	/*** 响应结果，json数据经AES加密的字符串 */
	private String result;
	
	public TransResult(String code, String result) {
		super();
		this.code = code;
		this.result = result;
	}
	public TransResult(String code, String msg, String result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
	}
	public TransResult() {
		super();
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
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getClientFlag() {
		return clientFlag;
	}
	public void setClientFlag(String clientFlag) {
		this.clientFlag = clientFlag;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	
}

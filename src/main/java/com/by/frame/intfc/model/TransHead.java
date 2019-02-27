package com.by.frame.intfc.model;

import java.io.Serializable;

public class TransHead  implements Serializable{
	private static final long serialVersionUID = 8941004081955844452L;

	/*** 请求编号；40，非空且唯一 */
	private String reqNo;
	/*** 接口版本：0.1.0  可空*/
	private String version;
	/*** 接口名称：非空 */
	private String service;
	/*** 客户端标志 : 非空*/
	private String clientFlag;
	/*** AES密钥，经RSA公钥加密，非空 */
	private String secretKey;
	
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	
}

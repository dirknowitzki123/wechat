package com.by.bussiness.wechat.model;

import java.util.Date;

//PUB_MSG_CAPTCHA
public class PubMsgCaptcha implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
	/** 手机号 */
	private String moblNo;
	/** 类型 */
	private String checkTyp;
	/** 检查时间 */
	private Date checkDate;
	/** 验证码 */
	private String checkDesc;
	/** 验证码值 */
	private String checkReslt;
	/**  */
	private Date createDate;
	/**  */
	private Date modifyDate;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getMoblNo(){
		return moblNo;
	}
	public void setMoblNo(String moblNo){
		this.moblNo=moblNo;
	}
	public String getCheckTyp(){
		return checkTyp;
	}
	public void setCheckTyp(String checkTyp){
		this.checkTyp=checkTyp;
	}
	public Date getCheckDate(){
		return checkDate;
	}
	public void setCheckDate(Date checkDate){
		this.checkDate=checkDate;
	}
	public String getCheckDesc(){
		return checkDesc;
	}
	public void setCheckDesc(String checkDesc){
		this.checkDesc=checkDesc;
	}
	public String getCheckReslt(){
		return checkReslt;
	}
	public void setCheckReslt(String checkReslt){
		this.checkReslt=checkReslt;
	}
	public Date getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}
	public Date getModifyDate(){
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate){
		this.modifyDate=modifyDate;
	}
}


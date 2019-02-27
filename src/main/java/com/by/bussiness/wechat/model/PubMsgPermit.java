package com.by.bussiness.wechat.model;

import java.util.Date;

//PUB_MSG_PERMIT
public class PubMsgPermit implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**  */
	private String id;
	/** 手机号 */
	private String moblNo;
	/** 白名单 */
	private String whitelist;
	/** 灰名单 */
	private String greylist;
	/** 黑名单 */
	private String blacklist;
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
	public String getMoblNo() {
		return moblNo;
	}
	public void setMoblNo(String moblNo) {
		this.moblNo = moblNo;
	}
	public String getWhitelist(){
		return whitelist;
	}
	public void setWhitelist(String whitelist){
		this.whitelist=whitelist;
	}
	public String getGreylist(){
		return greylist;
	}
	public void setGreylist(String greylist){
		this.greylist=greylist;
	}
	public String getBlacklist(){
		return blacklist;
	}
	public void setBlacklist(String blacklist){
		this.blacklist=blacklist;
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


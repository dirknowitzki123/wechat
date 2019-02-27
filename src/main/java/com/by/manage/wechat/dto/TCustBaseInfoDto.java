package com.by.manage.wechat.dto;

import java.util.Date;

public class TCustBaseInfoDto {
	/** id */
	private String id;
	/** 客户编号 */
	private String custNo;
	/** 父节点(推荐码) */
	private String parentReferralCode;
	/** 级别 */
	private String referralLevel;
	/** 推荐码 */
	private String referralCode;
	/** 推荐码路径 */
	private String referralPath;
	/** 客户姓名 */
	private String custName;
	/** 身份证号 */
	private String certNo;
	/** 手机号 */
	private String phoneNo;
	/** 银行卡号 */
	private String bankNo;
	/** 开户行 */
	private String openingBank;
	/** 记录创建日期 */
	private Date createDate;
	/** 记录修改日期 */
	private Date modifyDate;
	/** 客户状态 */
	private String status;
	/** imei */
	private String imei;
	
	public String getId() {
		return id;
	}
	public String getCustNo() {
		return custNo;
	}
	public String getParentReferralCode() {
		return parentReferralCode;
	}
	public String getReferralLevel() {
		return referralLevel;
	}
	public String getReferralCode() {
		return referralCode;
	}
	public String getReferralPath() {
		return referralPath;
	}
	public String getCustName() {
		return custName;
	}
	public String getCertNo() {
		return certNo;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public String getBankNo() {
		return bankNo;
	}
	public String getOpeningBank() {
		return openingBank;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public String getStatus() {
		return status;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public void setParentReferralCode(String parentReferralCode) {
		this.parentReferralCode = parentReferralCode;
	}
	public void setReferralLevel(String referralLevel) {
		this.referralLevel = referralLevel;
	}
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}
	public void setReferralPath(String referralPath) {
		this.referralPath = referralPath;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	
}

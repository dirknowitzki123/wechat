package com.by.bussiness.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "T_CUST_BASE_INFO")
public class TCustBaseInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
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
	/**  */
	private Date createDate;
	/**  */
	private Date modifyDate;
	@Id
	@Column(name="ID",length=64,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="CUST_NO",length=64,nullable=true)
	public String getCustNo(){
		return custNo;
	}
	public void setCustNo(String custNo){
		this.custNo=custNo;
	}
	@Column(name="PARENT_REFERRAL_CODE",length=255,nullable=true)
	public String getParentReferralCode(){
		return parentReferralCode;
	}
	public void setParentReferralCode(String parentReferralCode){
		this.parentReferralCode=parentReferralCode;
	}
	@Column(name="REFERRAL_LEVEL",length=32,nullable=true)
	public String getReferralLevel(){
		return referralLevel;
	}
	public void setReferralLevel(String referralLevel){
		this.referralLevel=referralLevel;
	}
	@Column(name="REFERRAL_CODE",length=16,nullable=true)
	public String getReferralCode(){
		return referralCode;
	}
	public void setReferralCode(String referralCode){
		this.referralCode=referralCode;
	}
	@Column(name="REFERRAL_PATH",length=255,nullable=true)
	public String getReferralPath(){
		return referralPath;
	}
	public void setReferralPath(String referralPath){
		this.referralPath=referralPath;
	}
	@Column(name="CUST_NAME",length=160,nullable=true)
	public String getCustName(){
		return custName;
	}
	public void setCustName(String custName){
		this.custName=custName;
	}
	@Column(name="CERT_NO",length=18,nullable=true)
	public String getCertNo(){
		return certNo;
	}
	public void setCertNo(String certNo){
		this.certNo=certNo;
	}
	@Column(name="PHONE_NO",length=11,nullable=true)
	public String getPhoneNo(){
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo){
		this.phoneNo=phoneNo;
	}
	@Column(name="BANK_NO",length=20,nullable=true)
	public String getBankNo(){
		return bankNo;
	}
	public void setBankNo(String bankNo){
		this.bankNo=bankNo;
	}
	@Column(name="OPENING_BANK",length=255,nullable=true)
	public String getOpeningBank(){
		return openingBank;
	}
	public void setOpeningBank(String openingBank){
		this.openingBank=openingBank;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE",length=19,nullable=true)
	public Date getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFY_DATE",length=19,nullable=true)
	public Date getModifyDate(){
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate){
		this.modifyDate=modifyDate;
	}
}


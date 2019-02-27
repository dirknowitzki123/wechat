package com.by.bussiness.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "T_CLIENT_APPOINTMENT_CASHLOAN")
public class TClientAppointmentCashloan implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
	/** 姓名 */
	private String custName;
	/** 联系电话 */
	private String phoneNo;
	/** 所在城市 */
	private String ivingCity;
	/** 推荐码 */
	private String referralCode;
	/** 是否办理过分期贷款 */
	private String isStaged;
	/** 是否信用卡用户(Is_No) */
	private String isCredites;
	/**  */
	private Date createDate;
	/**  */
	private Date modifyDate;
	@Id
	@Column(name="ID",length=60,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="CUST_NAME",length=200,nullable=true)
	public String getCustName(){
		return custName;
	}
	public void setCustName(String custName){
		this.custName=custName;
	}
	@Column(name="PHONE_NO",length=11,nullable=true)
	public String getPhoneNo(){
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo){
		this.phoneNo=phoneNo;
	}
	@Column(name="IVING_CITY",length=255,nullable=true)
	public String getIvingCity(){
		return ivingCity;
	}
	public void setIvingCity(String ivingCity){
		this.ivingCity=ivingCity;
	}
	@Column(name="REFERRAL_CODE",length=16,nullable=true)
	public String getReferralCode(){
		return referralCode;
	}
	public void setReferralCode(String referralCode){
		this.referralCode=referralCode;
	}
	@Column(name="IS_STAGED",length=16,nullable=true)
	public String getIsStaged(){
		return isStaged;
	}
	public void setIsStaged(String isStaged){
		this.isStaged=isStaged;
	}
	@Column(name="IS_CREDITES",length=16,nullable=true)
	public String getIsCredites(){
		return isCredites;
	}
	public void setIsCredites(String isCredites){
		this.isCredites=isCredites;
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


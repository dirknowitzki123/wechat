package com.by.manage.wechat.dto;

public class TClientAppointmentCashloanDto {
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
	/** 注册开始日期 **/
	private String beginDate;
	/** 注册结束日期 **/
	private String endDate;
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getIvingCity() {
		return ivingCity;
	}
	public void setIvingCity(String ivingCity) {
		this.ivingCity = ivingCity;
	}
	public String getReferralCode() {
		return referralCode;
	}
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}
	public String getIsStaged() {
		return isStaged;
	}
	public void setIsStaged(String isStaged) {
		this.isStaged = isStaged;
	}
	public String getIsCredites() {
		return isCredites;
	}
	public void setIsCredites(String isCredites) {
		this.isCredites = isCredites;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}

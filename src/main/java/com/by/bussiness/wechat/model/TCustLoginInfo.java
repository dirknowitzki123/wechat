package com.by.bussiness.wechat.model;

import java.util.Date;
import java.math.BigDecimal;
import java.sql.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "T_CUST_LOGIN_INFO")
public class TCustLoginInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
	/** 客户编号 */
	private String custNo;
	/** 密码 */
	private String custPwd;
	/** open_id */
	private String openId;
	/** 登录类型 */
	private String type;
	/** 客户状态 */
	private String status;
	/** imei */
	private String imei;
	/**  */
	private Date createDate;
	/**是否阅读  */
	private String agreFlag;
	/**协议*/
	private String agreMemo;
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
	@Column(name="CUST_PWD",length=160,nullable=true)
	public String getCustPwd(){
		return custPwd;
	}
	public void setCustPwd(String custPwd){
		this.custPwd=custPwd;
	}
	@Column(name="OPEN_ID",length=255,nullable=true)
	public String getOpenId(){
		return openId;
	}
	public void setOpenId(String openId){
		this.openId=openId;
	}
	@Column(name="TYPE",length=64,nullable=true)
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type=type;
	}
	@Column(name="STATUS",length=64,nullable=true)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	@Column(name="IMEI",length=40,nullable=true)
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE",length=19,nullable=true)
	public Date getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}
	
	@Column(name="AGRE_FLAG",length=19,nullable=true)
	public String getAgreFlag() {
		return agreFlag;
	}
	public void setAgreFlag(String agreFlag) {
		this.agreFlag = agreFlag;
	}
	@Column(name="AGRE_MEMO",length=19,nullable=true)
	public String getAgreMemo() {
		return agreMemo;
	}
	public void setAgreMemo(String agreMemo) {
		this.agreMemo = agreMemo;
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


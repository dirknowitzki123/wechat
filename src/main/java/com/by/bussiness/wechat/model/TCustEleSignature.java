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
@Table(name = "T_CUST_ELE_SIGNATURE")
public class TCustEleSignature implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**  */
	private String id;
	/** 业务编号 */
	private String busiNo;
	/** 业务类型 */
	private String busiType;
	/** 是否是微信用户电子合同 */
	private String isWechat;
	/** 附件名称 */
	private String attName;
	/** 附件后缀名 */
	private String attSuffix;
	/** 附件地址 */
	private String attPath;
	/** 备注 */
	private String remark;
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
	@Column(name="BUSI_NO",length=60,nullable=true)
	public String getBusiNo(){
		return busiNo;
	}
	public void setBusiNo(String busiNo){
		this.busiNo=busiNo;
	}
	@Column(name="BUSI_TYPE",length=60,nullable=true)
	public String getBusiType(){
		return busiType;
	}
	public void setBusiType(String busiType){
		this.busiType=busiType;
	}
	@Column(name="IS_WECHAT",length=8,nullable=true)
	public String getIsWechat(){
		return isWechat;
	}
	public void setIsWechat(String isWechat){
		this.isWechat=isWechat;
	}
	@Column(name="ATT_NAME",length=200,nullable=true)
	public String getAttName(){
		return attName;
	}
	public void setAttName(String attName){
		this.attName=attName;
	}
	@Column(name="ATT_SUFFIX",length=20,nullable=true)
	public String getAttSuffix(){
		return attSuffix;
	}
	public void setAttSuffix(String attSuffix){
		this.attSuffix=attSuffix;
	}
	@Column(name="ATT_PATH",length=255,nullable=true)
	public String getAttPath(){
		return attPath;
	}
	public void setAttPath(String attPath){
		this.attPath=attPath;
	}
	@Column(name="REMARK",length=255,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
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


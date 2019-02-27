package com.by.manage.wechat.model;

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
@Table(name = "B_CO_WECHAT_BRANCH")
public class BCoWechatBranch implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
	/** 商户名称 */
	private String branchName;
	/** 推荐码 */
	private String referCode;
	/** 经办人(上级推荐码-SA) */
	private String parReferCode;
	/** 商户状态 */
	private String status;
	/**  */
	private Date instDate;
	/**  */
	private String instUserNo;
	/**  */
	private Date updtDate;
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="BRANCH_NAME",length=80,nullable=true)
	public String getBranchName(){
		return branchName;
	}
	public void setBranchName(String branchName){
		this.branchName=branchName;
	}
	@Column(name="REFER_CODE",length=16,nullable=true)
	public String getReferCode(){
		return referCode;
	}
	public void setReferCode(String referCode){
		this.referCode=referCode;
	}
	@Column(name="PAR_REFER_CODE",length=16,nullable=true)
	public String getParReferCode(){
		return parReferCode;
	}
	public void setParReferCode(String parReferCode){
		this.parReferCode=parReferCode;
	}
	@Column(name="STATUS",length=16,nullable=true)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INST_DATE",length=19,nullable=true)
	public Date getInstDate(){
		return instDate;
	}
	public void setInstDate(Date instDate){
		this.instDate=instDate;
	}
	@Column(name="INST_USER_NO",length=40,nullable=true)
	public String getInstUserNo(){
		return instUserNo;
	}
	public void setInstUserNo(String instUserNo){
		this.instUserNo=instUserNo;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDT_DATE",length=19,nullable=true)
	public Date getUpdtDate(){
		return updtDate;
	}
	public void setUpdtDate(Date updtDate){
		this.updtDate=updtDate;
	}
}


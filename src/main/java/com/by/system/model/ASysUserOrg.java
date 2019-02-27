package com.by.system.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_USER_ORG")
public class ASysUserOrg implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String userId;
	private String orgId;
	private String isMainOrg;
	private Date instDate;
	private String instUserNo;
	private Date updtDate;
	@Id
	@Column(name="USER_ID",length=64,nullable=false,unique=true)
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId=userId;
	}
	@Id
	@Column(name="ORG_ID",length=40,nullable=false,unique=true)
	public String getOrgId(){
		return orgId;
	}
	public void setOrgId(String orgId){
		this.orgId=orgId;
	}
	@Column(name="IS_MAIN_ORG",length=8,nullable=true)
	public String getIsMainOrg(){
		return isMainOrg;
	}
	public void setIsMainOrg(String isMainOrg){
		this.isMainOrg=isMainOrg;
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


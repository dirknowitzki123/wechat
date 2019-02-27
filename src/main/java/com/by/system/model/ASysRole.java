package com.by.system.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_ROLE")
public class ASysRole implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String roleCode;
	private String roleName;
	private String isUse;
	private String remark;
	private Date instDate;
	private String instUserNo;
	private Date updtDate;
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="ROLE_CODE",length=40,nullable=true)
	public String getRoleCode(){
		return roleCode;
	}
	public void setRoleCode(String roleCode){
		this.roleCode=roleCode;
	}
	@Column(name="ROLE_NAME",length=40,nullable=true)
	public String getRoleName(){
		return roleName;
	}
	public void setRoleName(String roleName){
		this.roleName=roleName;
	}
	@Column(name="IS_USE",length=10,nullable=true)
	public String getIsUse(){
		return isUse;
	}
	public void setIsUse(String isUse){
		this.isUse=isUse;
	}
	@Column(name="REMARK",length=200,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
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


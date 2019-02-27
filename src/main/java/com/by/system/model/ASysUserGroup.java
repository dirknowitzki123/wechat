package com.by.system.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_USER_GROUP")
public class ASysUserGroup implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String groupNo;
	private String groupName;
	private String remark;
	private String stat;
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
	@Column(name="GROUP_NO",length=40,nullable=true)
	public String getGroupNo(){
		return groupNo;
	}
	public void setGroupNo(String groupNo){
		this.groupNo=groupNo;
	}
	@Column(name="GROUP_NAME",length=80,nullable=true)
	public String getGroupName(){
		return groupName;
	}
	public void setGroupName(String groupName){
		this.groupName=groupName;
	}
	@Column(name="REMARK",length=255,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	@Column(name="STAT",length=10,nullable=true)
	public String getStat(){
		return stat;
	}
	public void setStat(String stat){
		this.stat=stat;
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


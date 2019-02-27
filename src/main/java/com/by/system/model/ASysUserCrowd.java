package com.by.system.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_USER_CROWD")
public class ASysUserCrowd implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String crowdNo;
	private String crowdName;
	private String ruleId;
	private String crowdDesc;
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
	@Column(name="CROWD_NO",length=40,nullable=true)
	public String getCrowdNo(){
		return crowdNo;
	}
	public void setCrowdNo(String crowdNo){
		this.crowdNo=crowdNo;
	}
	@Column(name="CROWD_NAME",length=40,nullable=true)
	public String getCrowdName(){
		return crowdName;
	}
	public void setCrowdName(String crowdName){
		this.crowdName=crowdName;
	}
	@Column(name="RULE_ID",length=8,nullable=true)
	public String getRuleId(){
		return ruleId;
	}
	public void setRuleId(String ruleId){
		this.ruleId=ruleId;
	}
	@Column(name="CROWD_DESC",length=300,nullable=true)
	public String getCrowdDesc(){
		return crowdDesc;
	}
	public void setCrowdDesc(String crowdDesc){
		this.crowdDesc=crowdDesc;
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


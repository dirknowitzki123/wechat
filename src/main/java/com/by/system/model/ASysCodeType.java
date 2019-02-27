package com.by.system.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_CODE_TYPE")
public class ASysCodeType implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String typeName;
	private String typeCode;
	private String remark;
	private Date instDate;
	private String instUserNo;
	private Date updtDate;
	private String status;
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="TYPE_NAME",length=128,nullable=true)
	public String getTypeName(){
		return typeName;
	}
	public void setTypeName(String typeName){
		this.typeName=typeName;
	}
	@Column(name="TYPE_CODE",length=40,nullable=true)
	public String getTypeCode(){
		return typeCode;
	}
	public void setTypeCode(String typeCode){
		this.typeCode=typeCode;
	}
	@Column(name="REMARK",length=255,nullable=true)
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
	@Column(name="STATUS",length=8,nullable=true)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
}


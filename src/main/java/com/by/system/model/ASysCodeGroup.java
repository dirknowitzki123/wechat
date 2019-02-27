package com.by.system.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_CODE_GROUP")
public class ASysCodeGroup implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String groupCode;
	private String typeCode;
	private String valCode;
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
	@Column(name="GROUP_CODE",length=40,nullable=true)
	public String getGroupCode(){
		return groupCode;
	}
	public void setGroupCode(String groupCode){
		this.groupCode=groupCode;
	}
	@Column(name="TYPE_CODE",length=40,nullable=true)
	public String getTypeCode(){
		return typeCode;
	}
	public void setTypeCode(String typeCode){
		this.typeCode=typeCode;
	}
	@Column(name="VAL_CODE",length=8,nullable=true)
	public String getValCode(){
		return valCode;
	}
	public void setValCode(String valCode){
		this.valCode=valCode;
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
}


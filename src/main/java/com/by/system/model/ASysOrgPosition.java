package com.by.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "A_SYS_ORG_POSITION")
public class ASysOrgPosition implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**  */
	private String id;
	/**  */
	private String orgCode;
	/**  */
	private String orgName;
	/**  */
	private String postionNo;
	/**  */
	private String postionName;
	/**  */
	private String remark;
	/**  */
	private String status;
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="ORG_CODE",length=40,nullable=false)
	public String getOrgCode(){
		return orgCode;
	}
	public void setOrgCode(String orgCode){
		this.orgCode=orgCode;
	}
	@Column(name="ORG_NAME",length=128,nullable=false)
	public String getOrgName(){
		return orgName;
	}
	public void setOrgName(String orgName){
		this.orgName=orgName;
	}
	@Column(name="POSTION_NO",length=40,nullable=false)
	public String getPostionNo(){
		return postionNo;
	}
	public void setPostionNo(String postionNo){
		this.postionNo=postionNo;
	}
	@Column(name="POSTION_NAME",length=128,nullable=false)
	public String getPostionName(){
		return postionName;
	}
	public void setPostionName(String postionName){
		this.postionName=postionName;
	}
	@Column(name="REMARK",length=200,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	@Column(name="STATUS",length=8,nullable=false)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
}


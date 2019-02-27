package com.by.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "A_SYS_GROUP_USER")
public class ASysGroupUser implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String groupNo;
	private String loginName;
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
	@Column(name="LOGIN_NAME",length=40,nullable=true)
	public String getLoginName(){
		return loginName;
	}
	public void setLoginName(String loginName){
		this.loginName=loginName;
	}
}


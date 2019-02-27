package com.by.frame.intfc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "A_SYS_CLIENT_KEY")
public class ASysClientKey implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String clientFlag;
	private String pubKey;
	private String priKey;
	private String status;
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Id
	@Column(name="CLIENT_FLAG",length=40,nullable=false,unique=true)
	public String getClientFlag(){
		return clientFlag;
	}
	public void setClientFlag(String clientFlag){
		this.clientFlag=clientFlag;
	}
	@Column(name="PUB_KEY",length=21845,nullable=true)
	public String getPubKey(){
		return pubKey;
	}
	public void setPubKey(String pubKey){
		this.pubKey=pubKey;
	}
	@Column(name="PRI_KEY",length=21845,nullable=true)
	public String getPriKey(){
		return priKey;
	}
	public void setPriKey(String priKey){
		this.priKey=priKey;
	}
	@Column(name="STATUS",length=8,nullable=true)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
}


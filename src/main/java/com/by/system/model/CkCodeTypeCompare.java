package com.by.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "CK_CODE_TYPE_COMPARE")
public class CkCodeTypeCompare implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/*** 主键id */
	//private String id;
	/**  */
	private String oldType;
	/**  */
	private String newType;
	/**  */
	private String oldCodeVal;
	/**  */
	private String newCodeVal;
	/**  */
	private String state;
	
	
	@Id
	@Column(name="new_type",length=40,nullable=true)
	public String getNewType(){
		return newType;
	}
	public void setNewType(String newType){
		this.newType=newType;
	}
	@Id
	@Column(name="new_code_val",length=40,nullable=true)
	public String getNewCodeVal(){
		return newCodeVal;
	}
	public void setNewCodeVal(String newCodeVal){
		this.newCodeVal=newCodeVal;
	}
	@Column(name="old_type",length=40,nullable=true)
	public String getOldType(){
		return oldType;
	}
	public void setOldType(String oldType){
		this.oldType=oldType;
	}
	@Column(name="old_code_val",length=40,nullable=true)
	public String getOldCodeVal(){
		return oldCodeVal;
	}
	public void setOldCodeVal(String oldCodeVal){
		this.oldCodeVal=oldCodeVal;
	}
	@Column(name="state",length=40,nullable=true)
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state=state;
	}
}


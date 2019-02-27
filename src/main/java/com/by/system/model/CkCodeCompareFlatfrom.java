package com.by.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "CK_CODE_COMPARE_FLATFROM")
public class CkCodeCompareFlatfrom implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**  */
	private String id;
	/**  */
	private String mineCodeVal;
	/**  */
	private String flatCodeVal;
	/**  */
	private String type;
	
	@Id
	@Column(name="id",length=36,nullable=false)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="mine_code_val",length=40,nullable=false)
	public String getMineCodeVal(){
		return mineCodeVal;
	}
	public void setMineCodeVal(String mineCodeVal){
		this.mineCodeVal=mineCodeVal;
	}
	@Column(name="flat_code_val",length=40,nullable=false)
	public String getFlatCodeVal(){
		return flatCodeVal;
	}
	public void setFlatCodeVal(String flatCodeVal){
		this.flatCodeVal=flatCodeVal;
	}
	@Column(name="type",length=40,nullable=false)
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type=type;
	}
}


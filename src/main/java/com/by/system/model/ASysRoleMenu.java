package com.by.system.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
@Entity
@Table(name = "A_SYS_ROLE_MENU")
public class ASysRoleMenu implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String menuId;
	private String roleId;
	private String remark;
	@Id
	@Column(name="ID",length=32,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="MENU_ID",length=32,nullable=true)
	public String getMenuId(){
		return menuId;
	}
	public void setMenuId(String menuId){
		this.menuId=menuId;
	}
	@Column(name="ROLE_ID",length=32,nullable=true)
	public String getRoleId(){
		return roleId;
	}
	public void setRoleId(String roleId){
		this.roleId=roleId;
	}
	@Column(name="REMARK",length=255,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
}


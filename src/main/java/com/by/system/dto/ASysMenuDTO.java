package com.by.system.dto;

import com.by.system.model.ASysMenu;

public class ASysMenuDTO extends ASysMenu{
	
	private static final long serialVersionUID = 99665809411964073L;
	private String pName;
	private String pCode;
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	
}

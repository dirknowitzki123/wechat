package com.by.system.dto;

import com.by.system.model.ASysOrg;

public class ASysOrgDTO extends ASysOrg {

	private static final long serialVersionUID = 7696862337787294773L;
	private String parentOgrName;

	public String getParentOgrName() {
		return parentOgrName;
	}

	public void setParentOgrName(String parentOgrName) {
		this.parentOgrName = parentOgrName;
	}
	
	
}

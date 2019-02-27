package com.by.system.dto;

import java.util.ArrayList;
import java.util.List;

import com.by.system.model.ASysCodeGroup;
import com.by.system.model.ASysCodeInfo;

public class CodeGroupInfoDTO {
	private ASysCodeGroup codeGrp;
	private List<ASysCodeInfo> codes=new ArrayList<ASysCodeInfo>();
	
	
	public ASysCodeGroup getCodeGrp() {
		return codeGrp;
	}
	public void setCodeGrp(ASysCodeGroup codeGrp) {
		this.codeGrp = codeGrp;
	}
	public List<ASysCodeInfo> getCodes() {
		return codes;
	}
	public void setCodes(List<ASysCodeInfo> codes) {
		this.codes = codes;
	}
	
	
	
	
}

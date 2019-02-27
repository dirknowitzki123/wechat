package com.by.system.dto;

import java.util.ArrayList;
import java.util.List;

import com.by.system.model.ASysCodeInfo;

public class CodeGroupDTO {
	private String oper;
	private String groupCode;
	private String remark;
	private List<ASysCodeInfo> list=new ArrayList<ASysCodeInfo>();
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<ASysCodeInfo> getList() {
		return list;
	}
	public void setList(List<ASysCodeInfo> list) {
		this.list = list;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
}

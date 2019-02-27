package com.by.pub.model;

public class UserOrgBO {
	private String orgcode; // 机构时 ：机构代码 用户时：登陆ID
	private String orgname; // 机构时 ：机构名称 用户时：用户名称
	private String pid; 	// 父机构ID
	private String nocheck; // 是否选择，机构时为"true" 用户时为"false"

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getNocheck() {
		return nocheck;
	}

	public void setNocheck(String nocheck) {
		this.nocheck = nocheck;
	}
}

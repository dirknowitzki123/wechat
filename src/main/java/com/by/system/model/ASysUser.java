package com.by.system.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
@Entity
@Table(name = "A_SYS_USER")
public class ASysUser implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	private String loginName;
	private String password;
	private String orgCode;//主机构编号
	private String orgName;//主机构名
	private String roleCode;//主角色编号
	private String roleName;//主角色名
	private String status;
	private String sex;
	private String birhday;
	private String cardNo;
	private String politicalStatus;
	private String userLevel;
	private Integer workYears;
	private String education;
	private String mobile;
	private String email;
	private String homeAddr;
	private String homeTel;
	private String officeTel;
	private String instUserNo;
	private String instUserName;
	private Date instDate;
	private String instOrgCode;
	private String instOrgName;
	private String updtUserNo;
	private String updtUserName;
	private Date updtDate;
	private String updtOrgCode;
	private String updtOrgName;
	private String remark;
	private Integer creditYear;
	private Integer loginErrNum;
	private String lastLoginIp;
	private String lastLoginDate;
	private List<ASysRole> roles = new ArrayList<ASysRole>();//角色集
	private List<ASysOrg> orgs = new ArrayList<ASysOrg>();//机构集
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="USER_NAME",length=128,nullable=true)
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	@Column(name="LOGIN_NAME",length=64,nullable=true)
	public String getLoginName(){
		return loginName;
	}
	public void setLoginName(String loginName){
		this.loginName=loginName;
	}
	@Column(name="PASSWORD",length=64,nullable=true)
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password=password;
	}
	@Column(name="ORG_CODE",length=32,nullable=true)
	public String getOrgCode(){
		return orgCode;
	}
	public void setOrgCode(String orgCode){
		this.orgCode=orgCode;
	}
	@Column(name="ORG_NAME",length=32,nullable=true)
	public String getOrgName(){
		return orgName;
	}
	public void setOrgName(String orgName){
		this.orgName=orgName;
	}
	@Column(name="ROLE_CODE",length=32,nullable=true)
	public String getRoleCode(){
		return roleCode;
	}
	public void setRoleCode(String roleCode){
		this.roleCode=roleCode;
	}
	@Column(name="ROLE_NAME",length=32,nullable=true)
	public String getRoleName(){
		return roleName;
	}
	public void setRoleName(String roleName){
		this.roleName=roleName;
	}
	@Column(name="STATUS",length=8,nullable=true)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	@Column(name="SEX",length=8,nullable=true)
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex=sex;
	}
	@Column(name="BIRHDAY",length=10,nullable=true)
	public String getBirhday(){
		return birhday;
	}
	public void setBirhday(String birhday){
		this.birhday=birhday;
	}
	@Column(name="CARD_NO",length=100,nullable=true)
	public String getCardNo(){
		return cardNo;
	}
	public void setCardNo(String cardNo){
		this.cardNo=cardNo;
	}
	@Column(name="POLITICAL_STATUS",length=8,nullable=true)
	public String getPoliticalStatus(){
		return politicalStatus;
	}
	public void setPoliticalStatus(String politicalStatus){
		this.politicalStatus=politicalStatus;
	}
	@Column(name="USER_LEVEL",length=8,nullable=true)
	public String getUserLevel(){
		return userLevel;
	}
	public void setUserLevel(String userLevel){
		this.userLevel=userLevel;
	}
	@Column(name="WORK_YEARS",length=11,nullable=true)
	public Integer getWorkYears(){
		return workYears;
	}
	public void setWorkYears(Integer workYears){
		this.workYears=workYears;
	}
	@Column(name="EDUCATION",length=8,nullable=true)
	public String getEducation(){
		return education;
	}
	public void setEducation(String education){
		this.education=education;
	}
	@Column(name="MOBILE",length=20,nullable=true)
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	@Column(name="EMAIL",length=64,nullable=true)
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	@Column(name="HOME_ADDR",length=128,nullable=true)
	public String getHomeAddr(){
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr){
		this.homeAddr=homeAddr;
	}
	@Column(name="HOME_TEL",length=20,nullable=true)
	public String getHomeTel(){
		return homeTel;
	}
	public void setHomeTel(String homeTel){
		this.homeTel=homeTel;
	}
	@Column(name="OFFICE_TEL",length=20,nullable=true)
	public String getOfficeTel(){
		return officeTel;
	}
	public void setOfficeTel(String officeTel){
		this.officeTel=officeTel;
	}
	@Column(name="INST_USER_NO",length=40,nullable=true)
	public String getInstUserNo(){
		return instUserNo;
	}
	public void setInstUserNo(String instUserNo){
		this.instUserNo=instUserNo;
	}
	@Column(name="INST_USER_NAME",length=128,nullable=true)
	public String getInstUserName(){
		return instUserName;
	}
	public void setInstUserName(String instUserName){
		this.instUserName=instUserName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INST_DATE",length=19,nullable=true)
	public Date getInstDate(){
		return instDate;
	}
	public void setInstDate(Date instDate){
		this.instDate=instDate;
	}
	@Column(name="INST_ORG_CODE",length=40,nullable=true)
	public String getInstOrgCode(){
		return instOrgCode;
	}
	public void setInstOrgCode(String instOrgCode){
		this.instOrgCode=instOrgCode;
	}
	@Column(name="INST_ORG_NAME",length=128,nullable=true)
	public String getInstOrgName(){
		return instOrgName;
	}
	public void setInstOrgName(String instOrgName){
		this.instOrgName=instOrgName;
	}
	@Column(name="UPDT_USER_NO",length=40,nullable=true)
	public String getUpdtUserNo(){
		return updtUserNo;
	}
	public void setUpdtUserNo(String updtUserNo){
		this.updtUserNo=updtUserNo;
	}
	@Column(name="UPDT_USER_NAME",length=128,nullable=true)
	public String getUpdtUserName(){
		return updtUserName;
	}
	public void setUpdtUserName(String updtUserName){
		this.updtUserName=updtUserName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDT_DATE",length=19,nullable=true)
	public Date getUpdtDate(){
		return updtDate;
	}
	public void setUpdtDate(Date updtDate){
		this.updtDate=updtDate;
	}
	@Column(name="UPDT_ORG_CODE",length=40,nullable=true)
	public String getUpdtOrgCode(){
		return updtOrgCode;
	}
	public void setUpdtOrgCode(String updtOrgCode){
		this.updtOrgCode=updtOrgCode;
	}
	@Column(name="UPDT_ORG_NAME",length=128,nullable=true)
	public String getUpdtOrgName(){
		return updtOrgName;
	}
	public void setUpdtOrgName(String updtOrgName){
		this.updtOrgName=updtOrgName;
	}
	@Column(name="REMARK",length=128,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	@Column(name="CREDIT_YEAR",length=11,nullable=true)
	public Integer getCreditYear(){
		return creditYear;
	}
	public void setCreditYear(Integer creditYear){
		this.creditYear=creditYear;
	}
	@Column(name="LOGIN_ERR_NUM",length=11,nullable=true)
	public Integer getLoginErrNum(){
		return loginErrNum;
	}
	public void setLoginErrNum(Integer loginErrNum){
		this.loginErrNum=loginErrNum;
	}
	@Column(name="LAST_LOGIN_IP",length=32,nullable=true)
	public String getLastLoginIp(){
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp){
		this.lastLoginIp=lastLoginIp;
	}
	@Column(name="LAST_LOGIN_DATE",length=32,nullable=true)
	public String getLastLoginDate(){
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate){
		this.lastLoginDate=lastLoginDate;
	}
	@Transient
	public List<ASysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<ASysRole> roles) {
		this.roles = roles;
	}
	@Transient
	public List<ASysOrg> getOrgs() {
		return orgs;
	}
	public void setOrgs(List<ASysOrg> orgs) {
		this.orgs = orgs;
	}
}


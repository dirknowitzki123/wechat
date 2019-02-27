package com.by.system.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_ORG")
public class ASysOrg implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
	/** 机构编码 */
	private String orgCode;
	/** 机构名称 */
	private String orgName;
	/** 父节点ID */
	private String parentId;
	/** 排序 */
	private Integer orgOrder;
	/** 级别 */
	private String orgLevel;
	/** 类型 */
	private String orgType;
	/** 机构路径 */
	private String orgPath;
	/** 省编码 */
	private String provNo;
	/** 省名称 */
	private String provName;
	/** 市编码 */
	private String cityNo;
	/** 市名称 */
	private String cityName;
	/** 县编码 */
	private String areaNo;
	/** 县名称 */
	private String areaName;
	/** 地址 */
	private String orgAddr;
	/** 电话 */
	private String orgPhone;
	/** 备注 */
	private String remark;
	/** 状态 （是：13900001   否：13900002） */
	private String status;
	/** 插入时间 */
	private Date instDate;
	/** 插入人员编码 */
	private String instUserNo;
	/** 更新时间 */
	private Date updtDate;
	@Column(name="ID",length=40,nullable=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Id
	@Column(name="ORG_CODE",length=40,nullable=false,unique=true)
	public String getOrgCode(){
		return orgCode;
	}
	public void setOrgCode(String orgCode){
		this.orgCode=orgCode;
	}
	@Column(name="ORG_NAME",length=128,nullable=true)
	public String getOrgName(){
		return orgName;
	}
	public void setOrgName(String orgName){
		this.orgName=orgName;
	}
	@Column(name="PARENT_ID",length=32,nullable=true)
	public String getParentId(){
		return parentId;
	}
	public void setParentId(String parentId){
		this.parentId=parentId;
	}
	@Column(name="ORG_ORDER",length=11,nullable=true)
	public Integer getOrgOrder(){
		return orgOrder;
	}
	public void setOrgOrder(Integer orgOrder){
		this.orgOrder=orgOrder;
	}
	@Column(name="ORG_LEVEL",length=32,nullable=true)
	public String getOrgLevel(){
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel){
		this.orgLevel=orgLevel;
	}
	@Column(name="ORG_TYPE",length=32,nullable=true)
	public String getOrgType(){
		return orgType;
	}
	public void setOrgType(String orgType){
		this.orgType=orgType;
	}
	@Column(name="ORG_PATH",length=255,nullable=true)
	public String getOrgPath(){
		return orgPath;
	}
	public void setOrgPath(String orgPath){
		this.orgPath=orgPath;
	}
	@Column(name="PROV_NO",length=40,nullable=true)
	public String getProvNo(){
		return provNo;
	}
	public void setProvNo(String provNo){
		this.provNo=provNo;
	}
	@Column(name="PROV_NAME",length=128,nullable=true)
	public String getProvName(){
		return provName;
	}
	public void setProvName(String provName){
		this.provName=provName;
	}
	@Column(name="CITY_NO",length=40,nullable=true)
	public String getCityNo(){
		return cityNo;
	}
	public void setCityNo(String cityNo){
		this.cityNo=cityNo;
	}
	@Column(name="CITY_NAME",length=128,nullable=true)
	public String getCityName(){
		return cityName;
	}
	public void setCityName(String cityName){
		this.cityName=cityName;
	}
	@Column(name="AREA_NO",length=40,nullable=true)
	public String getAreaNo(){
		return areaNo;
	}
	public void setAreaNo(String areaNo){
		this.areaNo=areaNo;
	}
	@Column(name="AREA_NAME",length=128,nullable=true)
	public String getAreaName(){
		return areaName;
	}
	public void setAreaName(String areaName){
		this.areaName=areaName;
	}
	@Column(name="ORG_ADDR",length=255,nullable=true)
	public String getOrgAddr(){
		return orgAddr;
	}
	public void setOrgAddr(String orgAddr){
		this.orgAddr=orgAddr;
	}
	@Column(name="ORG_PHONE",length=32,nullable=true)
	public String getOrgPhone(){
		return orgPhone;
	}
	public void setOrgPhone(String orgPhone){
		this.orgPhone=orgPhone;
	}
	@Column(name="REMARK",length=255,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	@Column(name="STATUS",length=8,nullable=true)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INST_DATE",length=19,nullable=true)
	public Date getInstDate(){
		return instDate;
	}
	public void setInstDate(Date instDate){
		this.instDate=instDate;
	}
	@Column(name="INST_USER_NO",length=40,nullable=true)
	public String getInstUserNo(){
		return instUserNo;
	}
	public void setInstUserNo(String instUserNo){
		this.instUserNo=instUserNo;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDT_DATE",length=19,nullable=true)
	public Date getUpdtDate(){
		return updtDate;
	}
	public void setUpdtDate(Date updtDate){
		this.updtDate=updtDate;
	}
}


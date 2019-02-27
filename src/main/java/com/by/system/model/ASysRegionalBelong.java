package com.by.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_REGIONAL_BELONG")
public class ASysRegionalBelong implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
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
	/** 插入时间 */
	private Date instDate;
	/** 插入人员编码 */
	private String instUserNo;
	/** 更新时间 */
	private Date updtDate;
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
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


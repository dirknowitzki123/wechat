package com.by.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_APP_VERSIONS")
public class ASysAppVersions implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**  */
	private String id;
	/** 版本类型（码类：Os_Type） */
	private String osType;
	/** 版本编号 */
	private String versionNo;
	/** 适用类型（码类：Apply_Type） */
	private String applyType;
	/** 下载地址 */
	private String downPath;
	/** 说明 */
	private String remark;
	/** 是否强制更新(码类：Is_No) */
	private String isNo;
	/** 大小 */
	private String appSize;
	/** 适用环境 */
	private String applyEnvir;
	/** 上线日期 */
	private String onlineDate;
	/** 插入时间 */
	private Date instDate;
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
	@Column(name="OS_TYPE",length=8,nullable=true)
	public String getOsType(){
		return osType;
	}
	public void setOsType(String osType){
		this.osType=osType;
	}
	@Column(name="VERSION_NO",length=40,nullable=true)
	public String getVersionNo(){
		return versionNo;
	}
	public void setVersionNo(String versionNo){
		this.versionNo=versionNo;
	}
	@Column(name="APPLY_TYPE",length=8,nullable=true)
	public String getApplyType(){
		return applyType;
	}
	public void setApplyType(String applyType){
		this.applyType=applyType;
	}
	@Column(name="DOWN_PATH",length=250,nullable=true)
	public String getDownPath(){
		return downPath;
	}
	public void setDownPath(String downPath){
		this.downPath=downPath;
	}
	@Column(name="REMARK",length=2000,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	@Column(name="IS_NO",length=8,nullable=true)
	public String getIsNo(){
		return isNo;
	}
	public void setIsNo(String isNo){
		this.isNo=isNo;
	}
	@Column(name="APP_SIZE",length=20,nullable=true)
	public String getAppSize(){
		return appSize;
	}
	public void setAppSize(String appSize){
		this.appSize=appSize;
	}
	@Column(name="APPLY_ENVIR",length=50,nullable=true)
	public String getApplyEnvir(){
		return applyEnvir;
	}
	public void setApplyEnvir(String applyEnvir){
		this.applyEnvir=applyEnvir;
	}
	@Column(name="ONLINE_DATE",length=10,nullable=true)
	public String getOnlineDate(){
		return onlineDate;
	}
	public void setOnlineDate(String onlineDate){
		this.onlineDate=onlineDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INST_DATE",length=19,nullable=true)
	public Date getInstDate(){
		return instDate;
	}
	public void setInstDate(Date instDate){
		this.instDate=instDate;
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
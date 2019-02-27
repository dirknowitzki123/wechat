package com.by.pub.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.ibatis.type.Alias;
@Entity
@Table(name = "A_SYS_ATT")
@Alias(value="com.by.pub.model.ASysAtt")
public class ASysAtt implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String busiNo;
	private String busiTyp;
	private String newAtt;
	private String oldAtt;
	private String attTyp;
	private String attFile;
	private String attSize;
	private String phPath;
	private String inPath;
	private String isInAtt;
	private Integer sort;
	private String remark;
	private String instUserNo;
	private String instUserName;
	private String instUserOrg;
	private Date instDate;
	private String updtUserNo;
	private String updtUserName;
	private String updtUserOrg;
	private Date updtDate;
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="BUSI_NO",length=40,nullable=true)
	public String getBusiNo(){
		return busiNo;
	}
	public void setBusiNo(String busiNo){
		this.busiNo=busiNo;
	}
	@Column(name="BUSI_TYP",length=8,nullable=true)
	public String getBusiTyp(){
		return busiTyp;
	}
	public void setBusiTyp(String busiTyp){
		this.busiTyp=busiTyp;
	}
	@Column(name="NEW_ATT",length=40,nullable=true)
	public String getNewAtt(){
		return newAtt;
	}
	public void setNewAtt(String newAtt){
		this.newAtt=newAtt;
	}
	@Column(name="OLD_ATT",length=120,nullable=true)
	public String getOldAtt(){
		return oldAtt;
	}
	public void setOldAtt(String oldAtt){
		this.oldAtt=oldAtt;
	}
	@Column(name="ATT_TYP",length=8,nullable=true)
	public String getAttTyp(){
		return attTyp;
	}
	public void setAttTyp(String attTyp){
		this.attTyp=attTyp;
	}
	@Column(name="ATT_FILE",length=20,nullable=true)
	public String getAttFile(){
		return attFile;
	}
	public void setAttFile(String attFile){
		this.attFile=attFile;
	}
	@Column(name="ATT_SIZE",length=20,nullable=true)
	public String getAttSize(){
		return attSize;
	}
	public void setAttSize(String attSize){
		this.attSize=attSize;
	}
	@Column(name="PH_PATH",length=120,nullable=true)
	public String getPhPath(){
		return phPath;
	}
	public void setPhPath(String phPath){
		this.phPath=phPath;
	}
	@Column(name="IN_PATH",length=120,nullable=true)
	public String getInPath(){
		return inPath;
	}
	public void setInPath(String inPath){
		this.inPath=inPath;
	}
	@Column(name="IS_IN_ATT",length=8,nullable=true)
	public String getIsInAtt(){
		return isInAtt;
	}
	public void setIsInAtt(String isInAtt){
		this.isInAtt=isInAtt;
	}
	@Column(name="SORT",length=11,nullable=true)
	public Integer getSort(){
		return sort;
	}
	public void setSort(Integer sort){
		this.sort=sort;
	}
	@Column(name="REMARK",length=255,nullable=true)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
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
	@Column(name="INST_USER_ORG",length=40,nullable=true)
	public String getInstUserOrg(){
		return instUserOrg;
	}
	public void setInstUserOrg(String instUserOrg){
		this.instUserOrg=instUserOrg;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INST_DATE",length=19,nullable=true)
	public Date getInstDate(){
		return instDate;
	}
	public void setInstDate(Date instDate){
		this.instDate=instDate;
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
	@Column(name="UPDT_USER_ORG",length=40,nullable=true)
	public String getUpdtUserOrg(){
		return updtUserOrg;
	}
	public void setUpdtUserOrg(String updtUserOrg){
		this.updtUserOrg=updtUserOrg;
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


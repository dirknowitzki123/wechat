package com.by.pub.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_ATT_EXT")
public class ASysAttExt implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** ID */
	private String id;
	/** 业务编号 */
	private String busiNo;
	/** 业务类型 */
	private String busiTyp;
	/** 文件编号 */
	private String attNo;
	/** 文件名称 */
	private String attName;
	/** 文件扩展名*/
	private String attFile;
	/** 文件描述 */
	private String attDesc;
	/** 文件类型 */
	private String attTyp;
	/** 文件备注 */
	private String attRemark;
	/** 文件是否是文件目录 */
	private String attIsDir;
	/** 文件序号 */
	private Integer attSort;
	/** 新增用户编号 */
	private String instUserNo;
	/** 新增用户姓名 */
	private String instUserName;
	/** 新增时间 */
	private Date instDate;
	/** 修改时间 */
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
	@Column(name="ATT_NO",length=40,nullable=true)
	public String getAttNo(){
		return attNo;
	}
	public void setAttNo(String attNo){
		this.attNo=attNo;
	}
	@Column(name="ATT_NAME",length=255,nullable=true)
	public String getAttName(){
		return attName;
	}
	public void setAttName(String attName){
		this.attName=attName;
	}
	@Column(name="ATT_FILE",length=20,nullable=true)
	public String getAttFile() {
		return attFile;
	}
	public void setAttFile(String attFile) {
		this.attFile = attFile;
	}
	@Column(name="ATT_DESC",length=8,nullable=true)
	public String getAttDesc(){
		return attDesc;
	}
	public void setAttDesc(String attDesc){
		this.attDesc=attDesc;
	}
	@Column(name="ATT_TYP",length=255,nullable=true)
	public String getAttTyp(){
		return attTyp;
	}
	public void setAttTyp(String attTyp){
		this.attTyp=attTyp;
	}
	@Column(name="ATT_REMARK",length=255,nullable=true)
	public String getAttRemark(){
		return attRemark;
	}
	public void setAttRemark(String attRemark){
		this.attRemark=attRemark;
	}
	@Column(name="ATT_IS_DIR",length=8,nullable=true)
	public String getAttIsDir(){
		return attIsDir;
	}
	public void setAttIsDir(String attIsDir){
		this.attIsDir=attIsDir;
	}
	@Column(name="ATT_SORT",length=11,nullable=true)
	public Integer getAttSort(){
		return attSort;
	}
	public void setAttSort(Integer attSort){
		this.attSort=attSort;
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDT_DATE",length=19,nullable=true)
	public Date getUpdtDate(){
		return updtDate;
	}
	public void setUpdtDate(Date updtDate){
		this.updtDate=updtDate;
	}
}


package com.by.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_BIG_DATA_REQUEST")
public class ASysBigDataRequest implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private String id;
	/** 证件号码 */
	private String certNo;
	/** 请求次数 */
	private Integer reqCnt;
	/** 请求源 */
	private String reqSource;
	/** 请求地址 */
	private String reqUrl;
	/** 请求参数 */
	private String reqParams;
	/** 请求结果 */
	private String reqResult;
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
	@Column(name="CERT_NO",length=40,nullable=true)
	public String getCertNo(){
		return certNo;
	}
	public void setCertNo(String certNo){
		this.certNo=certNo;
	}
	@Column(name="REQ_CNT",length=11,nullable=true)
	public Integer getReqCnt(){
		return reqCnt;
	}
	public void setReqCnt(Integer reqCnt){
		this.reqCnt=reqCnt;
	}
	@Column(name="REQ_SOURCE",length=400,nullable=true)
	public String getReqSource(){
		return reqSource;
	}
	public void setReqSource(String reqSource){
		this.reqSource=reqSource;
	}
	@Column(name="REQ_URL",length=400,nullable=true)
	public String getReqUrl(){
		return reqUrl;
	}
	public void setReqUrl(String reqUrl){
		this.reqUrl=reqUrl;
	}
	@Column(name="REQ_PARAMS",length=40,nullable=true)
	public String getReqParams(){
		return reqParams;
	}
	public void setReqParams(String reqParams){
		this.reqParams=reqParams;
	}
	@Column(name="REQ_RESULT",length=40,nullable=true)
	public String getReqResult(){
		return reqResult;
	}
	public void setReqResult(String reqResult){
		this.reqResult=reqResult;
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


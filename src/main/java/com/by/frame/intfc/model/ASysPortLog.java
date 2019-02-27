package com.by.frame.intfc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_PORT_LOG")
public class ASysPortLog implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String serverName;
	private String reqNo;
	private String tranHead;
	private String tranBody;
	private String respData;
	private Date recTime;
	private Date respTime;
	private Date instTime;
	@Id
	@Column(name="ID",length=40,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="SERVER_NAME",length=80,nullable=true)
	public String getServerName(){
		return serverName;
	}
	public void setServerName(String serverName){
		this.serverName=serverName;
	}
	@Column(name="REQ_NO",length=40,nullable=true)
	public String getReqNo(){
		return reqNo;
	}
	public void setReqNo(String reqNo){
		this.reqNo=reqNo;
	}
	@Column(name="TRAN_HEAD",length=21845,nullable=true)
	public String getTranHead(){
		return tranHead;
	}
	public void setTranHead(String tranHead){
		this.tranHead=tranHead;
	}
	@Column(name="TRAN_BODY",length=21845,nullable=true)
	public String getTranBody(){
		return tranBody;
	}
	public void setTranBody(String tranBody){
		this.tranBody=tranBody;
	}
	@Column(name="RESP_DATA",length=21845,nullable=true)
	public String getRespData(){
		return respData;
	}
	public void setRespData(String respData){
		this.respData=respData;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REC_TIME",length=19,nullable=true)
	public Date getRecTime(){
		return recTime;
	}
	public void setRecTime(Date recTime){
		this.recTime=recTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RESP_TIME",length=19,nullable=true)
	public Date getRespTime(){
		return respTime;
	}
	public void setRespTime(Date respTime){
		this.respTime=respTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INST_TIME",length=19,nullable=true)
	public Date getInstTime(){
		return instTime;
	}
	public void setInstTime(Date instTime){
		this.instTime=instTime;
	}
}


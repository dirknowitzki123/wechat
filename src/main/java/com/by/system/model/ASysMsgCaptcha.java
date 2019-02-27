package com.by.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "A_SYS_MSG_CAPTCHA")
public class ASysMsgCaptcha implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**  */
	private String id;
	/** 电话号码 */
	private String moble;
	/** 检查时间 */
	private Date checkDate;
	/** 验证码 */
	private String code;
	/** 检查结果 */
	private String checkReslt;
	/** 创建时间 */
	private Date instDate;
	/** 修改时间 */
	private Date updtDate;
	@Id
	@Column(name="ID",length=32,nullable=false,unique=true)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	@Column(name="moble",length=16,nullable=true)
	public String getMoble(){
		return moble;
	}
	public void setMoble(String moble){
		this.moble=moble;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="check_date",length=19,nullable=true)
	public Date getCheckDate(){
		return checkDate;
	}
	public void setCheckDate(Date checkDate){
		this.checkDate=checkDate;
	}
	@Column(name="code",length=16,nullable=true)
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code=code;
	}
	@Column(name="check_reslt",length=16,nullable=true)
	public String getCheckReslt(){
		return checkReslt;
	}
	public void setCheckReslt(String checkReslt){
		this.checkReslt=checkReslt;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="inst_date",length=19,nullable=true)
	public Date getInstDate(){
		return instDate;
	}
	public void setInstDate(Date instDate){
		this.instDate=instDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updt_date",length=19,nullable=true)
	public Date getUpdtDate(){
		return updtDate;
	}
	public void setUpdtDate(Date updtDate){
		this.updtDate=updtDate;
	}
}


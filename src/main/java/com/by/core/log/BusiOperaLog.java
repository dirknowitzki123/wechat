package com.by.core.log;

import java.util.Date;

import com.by.core.util.DateUtils;

public class BusiOperaLog {
	/** 自定义的日志事件 */
	private BusiOperaLogEventEnum eventEnum;
	/** 记录时间*/
  	private String reqTime;
  	/**执行某个操作的参数*/
  	private String reqParams;
  	/** 日志描述*/
  	private String desc;
  	/** */
  	private String event;
  	/** 记录操作的返回结果 */
  	private String respMsg;
  	/** 记录返回时间 */
  	private String respTime;
  	/** 操作异常信息 */
  	private String errorMsg;
  	/** 操作正常为true，操作失败为false */
  	private boolean isSuccess;
  	/** 关键词 */
  	private String keyWord;
  
  	public BusiOperaLog(BusiOperaLogEventEnum eventEnum,String reqParams){
		this.event=eventEnum.toString();
		this.desc = eventEnum.getDesc();
		this.reqTime= DateUtils.format(new Date(),"yyyy-MM:dd HH:mm:ss");
		this.reqParams=reqParams;
		this.isSuccess=true;
		this.respMsg="success";
	}
	public BusiOperaLog(BusiOperaLogEventEnum eventEnum){
		this.event=eventEnum.toString();
		this.desc = eventEnum.getDesc();
		this.reqTime= DateUtils.format(new Date(),"yyyy-MM:dd HH:mm:ss");
		this.isSuccess=true;
	}
	public BusiOperaLogEventEnum getEventEnum() {
		return eventEnum;
	}
	public void setEventEnum(BusiOperaLogEventEnum eventEnum) {
		this.eventEnum = eventEnum;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getReqParams() {
		return reqParams;
	}
	public void setReqParams(String reqParams) {
		this.reqParams = reqParams;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getRespTime() {
		return respTime;
	}
	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
}

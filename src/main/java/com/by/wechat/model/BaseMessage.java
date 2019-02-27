package com.by.wechat.model;

public class BaseMessage {
	/** 接收方帐号（收到的OpenID）  **/
	public String ToUserName;
	/** 开发者微信号 **/
	public String FromUserName;
	/** 消息创建时间 （整型） **/
	public long CreateTime;
	/** 消息类型 **/
	public String MsgType;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}

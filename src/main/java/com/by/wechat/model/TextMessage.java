package com.by.wechat.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.by.wechat.util.CDataAdapter;

/**
 * 文本消息
 * Created by yiqr on 2017/6/12.
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextMessage extends BaseMessage{
	@XmlJavaTypeAdapter(CDataAdapter.class)
	/** 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示） **/
	public String Content;
	public TextMessage() {
		super();
		setMsgType("text");
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}

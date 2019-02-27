package com.by.wechat.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 客服
 * @author yiqr
 *
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerServiceMessage extends BaseMessage{
	
	public CustomerServiceMessage() {
		super();
		setMsgType("transfer_customer_service");
	}
	
}

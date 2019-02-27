package com.by.wechat.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDataAdapter extends XmlAdapter<String, String> {

	@Override
	public String unmarshal(String v) throws Exception {
		// TODO Auto-generated method stub
		return v;
	}

	@Override
	public String marshal(String v) throws Exception {
		// TODO Auto-generated method stub
		return "<![CDATA[" + v+ "]]>";
	}

}

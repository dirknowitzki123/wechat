package com.by.core.util;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.by.core.constant.IntfcConstant;
import com.by.core.exception.BusinessException;
import com.by.core.startup.AppConfig;
import com.by.core.util.HttpHelper;
import com.by.frame.intfc.crypto.AESUtil;
import com.by.frame.intfc.crypto.RSAUtil;
import com.by.frame.intfc.crypto.RandomUtil;
import com.by.frame.intfc.model.TransHead;
import com.by.frame.intfc.model.TransResult;

public class IntfcClient {

	public static <T> T sendData(Class<T> clazz, String url,Object param){
		if(param==null) return null;
		String rsaPubKey =  AppConfig.get("trans.head.rsa.pub.key");
		String aesKey = RandomUtil.randChar(16);//必须16个长度
		String jsonContent = JSON.toJSONString(param);
		TransHead head = new TransHead();
		head.setClientFlag(AppConfig.get("trans.head.client.flag"));
		head.setSecretKey(RSAUtil.pubKeyEnc(aesKey, rsaPubKey));
		head.setService("test_service");
		head.setVersion("0.1.0");
		head.setReqNo(System.currentTimeMillis()+"");
		
		Map<String,String> pMap = new HashMap<String,String>();
		pMap.put("transHead", JSON.toJSONString(head));
		pMap.put("transBody",AESUtil.encrypt(jsonContent, aesKey));
		
		Map<String, Object> rsMap = HttpHelper.postForm(url, pMap);
		String rsStr = (String) rsMap.get(HttpHelper.RESP_STR);
		TransResult trs = JSON.parseObject(rsStr, TransResult.class);
		if(!IntfcConstant.SUCC.equals(trs.getCode())){
			throw new BusinessException("错误："+trs.getClientFlag()+";"+trs.getMsg());
		}
		//业务数据获取
		String rsAesKey = RSAUtil.pubKeyDec(trs.getSecretKey(), rsaPubKey);
		System.out.println("rspAesKey:"+rsAesKey);
		String jsonStr = AESUtil.decrypt((String)trs.getResult(), rsAesKey);
		System.out.println("rspData===>>>"+jsonStr);
		T rs = JSON.parseObject(jsonStr, clazz);
		return rs;
	}
	
	
}

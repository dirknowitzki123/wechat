package com.by.core.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.by.core.constant.IntfcConstant;
import com.by.core.startup.ContextInit;
import com.by.core.util.SessionUtil;
import com.by.core.util.StringUtils;
import com.by.frame.intfc.crypto.AESUtil;
import com.by.frame.intfc.crypto.RSAUtil;
import com.by.frame.intfc.crypto.RandomUtil;
import com.by.frame.intfc.model.ASysClientKey;
import com.by.frame.intfc.model.ASysPortLog;
import com.by.frame.intfc.model.TransHead;
import com.by.frame.intfc.model.TransResult;
import com.by.frame.intfc.service.IASysPortLogService;
import com.by.system.model.ASysUser;

public class BaseController {

	public static final Log log = LogFactory.getLog(BaseController.class);

	public String getClientIP() {
		String ip = getRequest().getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("PRoxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getRemoteAddr();
		}
		return ip;
	}

	public ASysUser getCurrentASysUser() {
		return SessionUtil.getCurrentASysUser();
	}

	public String getCurrenUserId() {
		return getCurrentASysUser().getId();
	}

	public String getCurrentLoginName() {
		return getCurrentASysUser().getLoginName();
	}

	public String getCurrentUserName() {
		return getCurrentASysUser().getUserName();
	}

	public String getCurrentOrgCode() {
		return getCurrentASysUser().getOrgCode();
	}

	public String getCurrentOrgName() {
		return getCurrentASysUser().getOrgName();
	}
	
	public HttpServletRequest getRequest() {
		return SessionUtil.getRequest();
	}
	
	/**
	 * 获取接口结果
	 * @param code 结果的code
	 * @param msg 结果的消息
	 * @param rsObj 结果的内容
	 * @return
	 */
	public TransResult getTransResult(String code,String msg,Object rsObj){
		IASysPortLogService logService = ContextInit.getContext().getBean(IASysPortLogService.class);
		HttpServletRequest request = getRequest();
		
		ASysClientKey client = (ASysClientKey) request.getAttribute("clientKey");
		ASysPortLog log = (ASysPortLog) request.getAttribute("aSysPortLog");
		TransHead head = (TransHead) request.getAttribute("transHead");
		//必须是16个字节长度
		String aesKey = RandomUtil.randChar(16);
		String jsonStr = "";
		if(rsObj!=null){
			jsonStr = JSON.toJSONString(rsObj);
		}
		//组装返回结果
		TransResult rs = new TransResult();
		rs.setClientFlag(head.getClientFlag());
		rs.setCode(code);
		rs.setMsg(msg);
		rs.setReqNo(head.getReqNo());
		rs.setResult(jsonStr);
		rs.setSecretKey(RSAUtil.privKeyEnc(aesKey, client.getPriKey()));
		rs.setService(head.getService());
		rs.setVersion(head.getVersion());
		
		//记录日志
		log.setRespData(JSON.toJSONString(rs));
		log.setRespTime(new Date());
		//加密消息
		if(!StringUtils.isEmpty(jsonStr)){
			rs.setResult(AESUtil.encrypt(jsonStr, aesKey));
		}
		//记录到日志表
		logService.update(log);
		return rs;
	}
	
	/**
	 * 获取接口结果，默认返回操作成功
	 * @param rsObj
	 * @return
	 */
	public TransResult getTransResult(Object rsObj){
		String code = IntfcConstant.SUCC;
		String msg = "操作成功";
		return getTransResult(code,msg,rsObj);
	}

}

package com.by.frame.intfc;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.by.core.startup.ExceptionConfig;
import com.by.core.util.StringUtils;
import com.by.frame.intfc.crypto.AESUtil;
import com.by.frame.intfc.crypto.RSAUtil;
import com.by.frame.intfc.model.ASysClientKey;
import com.by.frame.intfc.model.ASysPortLog;
import com.by.frame.intfc.model.TransHead;
import com.by.frame.intfc.service.IASysClientKeyService;
import com.by.frame.intfc.service.IASysPortLogService;

/**
 * 接口安全拦截器
 * @author HeJian
 *
 */
public class IntfaceSecurityInterceptor extends HandlerInterceptorAdapter {
	
	public static final Log logger = LogFactory.getLog(IntfaceSecurityInterceptor.class);

	@Resource
	private IASysClientKeyService aSysClientKeyService;
	@Resource
	private IASysPortLogService aSysPortLogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ASysPortLog log = null;
		try {
			log = new ASysPortLog();
			log.setId(StringUtils.getUUID());
			log.setRecTime(new Date());
			
			//判断参数是否为空
			String transHead = request.getParameter("transHead");
			String transBody	= request.getParameter("transBody");
			if(StringUtils.isEmpty(transHead) || StringUtils.isEmpty(transBody)){
				String rs = RespUtil.writeCode("100002", ExceptionConfig.get("100002").toString(), response);//参数不可以为空
				log.setRespData(rs);
				log.setRespTime(new Date());
				return false;
			}
			
			//解析参数
			TransHead head = JSON.parseObject(transHead, TransHead.class);
			String clientFlag = head.getClientFlag();
			String serviceName = head.getService();
			String aesKey = head.getSecretKey();
			String reqNo = head.getReqNo();
			log.setServerName(serviceName);
			log.setTranBody(transBody);
			log.setTranHead(transHead);
			log.setReqNo(reqNo);
			if(StringUtils.isEmpty(reqNo)){
				logger.error("100002,接口请求编号参数不可为空");
				String rs = RespUtil.writeCode("100002", "接口请求编号参数不可为空", response);
				log.setRespData(rs);
				log.setRespTime(new Date());
				return false;
			}
			if(StringUtils.isEmpty(clientFlag)){
				logger.error("100002,接口客户端标志参数不可为空");
				String rs = RespUtil.writeCode("100002", "接口客户端标志参数不可为空", response);
				log.setRespData(rs);
				log.setRespTime(new Date());
				return false;
			}
			/* 占时可以放开接口名称的校验，正规做法还应该有个接口编号
			if(StringUtils.isEmpty(serviceName)){
				String rs = RespUtil.writeCode("100002", "接口接口名称不可为空", response);
				log.setRespData(rs);
				log.setRespTime(new Date());
				return false;
			}
			*/
			if(StringUtils.isEmpty(aesKey)){
				logger.error("100002,接口secretKey不可为空");
				String rs = RespUtil.writeCode("100002", "接口secretKey不可为空", response);
				log.setRespData(rs);
				log.setRespTime(new Date());
				return false;
			}
			
			//判断服务名的正确性，暂时不加校验
			
			//判断clientFlag的正确性。存不存在这个客户端
			ASysClientKey client = aSysClientKeyService.getByClient(clientFlag);
			if(client==null){
				logger.error("190001,非法访问");
				String rs = RespUtil.writeCode("190001", ExceptionConfig.get("190001").toString(), response);//非法访问
				log.setRespData(rs);
				log.setRespTime(new Date());
				return false;
			}
			String rsaPriKey = client.getPriKey();
			//解密aeskey
			try {
				//私钥解密
				aesKey = RSAUtil.privKeyDec(aesKey, rsaPriKey);
				head.setSecretKey(aesKey);
				log.setTranHead(JSON.toJSONString(head));
				//使用aes可以解密content
				transBody = AESUtil.decrypt(transBody, aesKey);
				log.setTranBody(transBody);
			} catch (Exception e) {
				logger.error("190001,非法访问");
				String rs = RespUtil.writeCode("190001", ExceptionConfig.get("190001").toString(), response);//非法访问
				log.setRecTime(new Date());
				log.setRespData(rs);
				return false;
			}
			request.setAttribute("clientKey", client);
			request.setAttribute("aSysPortLog", log);
			request.setAttribute("transHead", head);
			request.setAttribute("transBody", transBody);
			return super.preHandle(request, response, handler);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("190000,系统错误");
			String rs = RespUtil.writeCode("190000", ExceptionConfig.get("190000").toString(), response);//系统错误
			log.setRecTime(new Date());
			log.setRespData(rs);
			return false;
		}finally{
			//无论成功和失败都写日志表
			if(log!=null){
				//写日志
				log.setInstTime(new Date());
				aSysPortLogService.save(log);
			}
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		/*
		TransResult rs = (TransResult) request.getAttribute("transResult");
		String jsonStr = JSON.toJSONString(rs);
		RespUtil.writeJson(jsonStr, response);
		*/
		super.postHandle(request, response, handler, modelAndView);
	}

	
	
	
}

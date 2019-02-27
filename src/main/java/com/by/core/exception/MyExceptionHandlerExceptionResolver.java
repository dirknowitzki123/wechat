package com.by.core.exception;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.alibaba.fastjson.JSON;
import com.by.core.startup.AppConfig;
import com.by.core.startup.ContextInit;
import com.by.core.startup.ExceptionConfig;
import com.by.core.util.SessionUtil;
import com.by.frame.intfc.model.ASysPortLog;
import com.by.frame.intfc.model.TransHead;
import com.by.frame.intfc.service.IASysPortLogService;

public class MyExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {
	private static Log log = LogFactory.getLog(MyExceptionHandlerExceptionResolver.class);
	private String defaultErrorView;
	private Properties exceptionMappings;

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	public Properties getExceptionMappings() {
		return exceptionMappings;
	}

	public void setExceptionMappings(Properties exceptionMappings) {
		this.exceptionMappings = exceptionMappings;
	}

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		if (handlerMethod == null) {
			return null;
		}
		Method method = handlerMethod.getMethod();
		if (method == null) {
			return null;
		}
		String logMsg = getLogMsg(exception);
		ModelAndView returnValue = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || responseBodyAnn != null) {
			try {
				ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
				String errorCode = "999999";
				String errorMsg = (String) ExceptionConfig.get(errorCode);

				if (exception instanceof BusinessException) {
					BusinessException businessEx = (BusinessException) exception;
					errorCode = businessEx.getCode();
					if (StringUtils.isEmpty(errorCode)) {
						errorCode = "100000";
					}
					errorMsg = businessEx.getMessage();
					if (StringUtils.isEmpty(errorMsg)) {
						errorMsg = (String) ExceptionConfig.get(errorCode);
					}
					response.setStatus(500);
					modelAndView.getModel().put("success", false);
					modelAndView.getModel().put("msg", errorMsg);
					modelAndView.getModel().put("code", errorCode);
					packIntfcExceptionInfo(modelAndView);
					log.error(logMsg);
					return modelAndView;
				}

				if (exception instanceof LoginException) {
					LoginException loginEx = (LoginException) exception;
					errorCode = loginEx.getCode();
					if (StringUtils.isEmpty(errorCode)) {
						errorCode = "110000";
					}
					errorMsg = loginEx.getMessage();
					if (StringUtils.isEmpty(errorMsg)) {
						errorMsg = (String) ExceptionConfig.get(errorCode);
					}
					response.setStatus(510);
					modelAndView.getModel().put("success", false);
					modelAndView.getModel().put("msg", errorMsg);
					modelAndView.getModel().put("code", errorCode);
					packIntfcExceptionInfo(modelAndView);
					log.error(logMsg);
					return modelAndView;
				}
				if (exception instanceof UnauthorizedException) {
					errorMsg = exception.getMessage();
					errorMsg = errorMsg.replace("Subject does not have permission", "没有访问\\: ") + " 权限许可(505).";
					response.setStatus(500);
					modelAndView.getModel().put("success", false);
					modelAndView.getModel().put("msg", errorMsg);
					modelAndView.getModel().put("code", 405);
					packIntfcExceptionInfo(modelAndView);
					log.error(logMsg);
					return modelAndView;
				}

				if (exception instanceof FrameworkException) {
					FrameworkException frameworkEx = (FrameworkException) exception;
					errorCode = frameworkEx.getCode();
					if (StringUtils.isEmpty(errorCode)) {
						errorCode = "190000";
					}
					errorMsg = frameworkEx.getMessage();
					if (StringUtils.isEmpty(errorMsg)) {
						errorMsg = (String) ExceptionConfig.get(errorCode);
					}
					response.setStatus(500);
					modelAndView.getModel().put("success", false);
					modelAndView.getModel().put("msg", errorMsg);
					modelAndView.getModel().put("code", errorCode);
					packIntfcExceptionInfo(modelAndView);
					log.error(logMsg);
					return modelAndView;
				}

				if (exception instanceof AccessException) {
					AccessException accessEx = (AccessException) exception;
					errorCode = accessEx.getCode();
					if (StringUtils.isEmpty(errorCode)) {
						errorCode = "130000";
					}
					errorMsg = accessEx.getMessage();
					if (StringUtils.isEmpty(errorMsg)) {
						errorMsg = (String) ExceptionConfig.get(errorCode);
					}
					response.setStatus(500);
					modelAndView.getModel().put("success", false);
					modelAndView.getModel().put("msg", errorMsg);
					modelAndView.getModel().put("code", errorCode);
					packIntfcExceptionInfo(modelAndView);
					log.error(logMsg);
					return modelAndView;
				}

				if (exception instanceof ShiroException) {
					errorMsg = exception.getMessage();
					response.setStatus(500);
					modelAndView.getModel().put("success", false);
					modelAndView.getModel().put("msg", errorMsg);
					modelAndView.getModel().put("code", "120000");
					packIntfcExceptionInfo(modelAndView);
					log.error(logMsg);
					return modelAndView;
				}

				response.setStatus(500);
				modelAndView.getModel().put("success", false);
				
				modelAndView.getModel().put("msg", errorMsg);
				modelAndView.getModel().put("code", errorCode);
				packIntfcExceptionInfo(modelAndView);
				log.error(logMsg);
				exception.printStackTrace();
				return modelAndView;

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			if (returnValue == null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("success", false);
				//系统调试是否启用
				if ( AppConfig.getBoolean( "system.debug.enable" ) ) {
					params.put("msg", getLogMsg(exception));
				} else {
					params.put("msg", "系统异常！");
				}
				try{ response.reset(); } catch( Exception e ) {
					log.error( e );
				};
				returnValue = new ModelAndView(defaultErrorView, params);
			}

			if (exception instanceof WeChatSessionException) {
				log.error("session已失效");
				return new ModelAndView("redirect:"+"/wechat/login");
			}
			
			if (exception instanceof LoginException) {
				log.error(logMsg);
				return returnValue;
			}
			if (exception instanceof BusinessException) {
				log.error(logMsg);
				return returnValue;
			}
			if (exception instanceof FrameworkException) {
				log.error(logMsg);
				return returnValue;
			}
			if (exception instanceof AccessException) {
				log.error(logMsg);
				return returnValue;
			}
			if (exception instanceof ShiroException) {
				log.error(logMsg);
				return returnValue;
			}

			log.error(logMsg);
			exception.printStackTrace();
			return returnValue;
		}
	}

	private String getLogMsg(Exception exception) {
		StackTraceElement stackTraceElement = exception.getStackTrace()[0];
		StringBuilder logMsg = new StringBuilder("\n");
		logMsg.append(exception.toString());
		logMsg.append("\n方法=");
		logMsg.append(stackTraceElement.getClassName());
		logMsg.append(".");
		logMsg.append(stackTraceElement.getMethodName());
		logMsg.append(",文件名=");
		logMsg.append(stackTraceElement.getFileName());
		logMsg.append(",行号=");
		logMsg.append(stackTraceElement.getLineNumber());
		return logMsg.toString();
	}
	
	/**
	 * 接口调用异常时候，组装相关内容
	 * @param model
	 */
	private void packIntfcExceptionInfo(ModelAndView model){
		HttpServletRequest request = SessionUtil.getRequest();
		IASysPortLogService logService = ContextInit.getContext().getBean(IASysPortLogService.class);
		TransHead head = (TransHead) request.getAttribute("transHead");
		ASysPortLog aSysPortLog = (ASysPortLog) request.getAttribute("aSysPortLog");
		
		if(head==null || aSysPortLog==null ) return;
		
		String path = request.getServletPath();
		if(path.startsWith("/anon") && path.endsWith(".int")){
			model.getModel().put("reqNo", head.getReqNo());
			model.getModel().put("version", head.getVersion());
			model.getModel().put("service", head.getService());
			model.getModel().put("clientFlag", head.getClientFlag());
			
			//记录日志
			aSysPortLog.setRespData(JSON.toJSONString(model.getModel()));
			aSysPortLog.setRespTime(new Date());
			logService.update(aSysPortLog);
		}
	}
	
}

package com.by.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.by.core.exception.LoginException;
import com.by.system.model.ASysUser;

/**
 * session会话存放
 * @author qiaoml
 */
public class SessionUtil {
	public final static String CURRENT_USER = "CURRENT_USER";
	
	/**
	 * 获取request对象
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	};
	
	/**
	 * 获取session对象
	 * @return
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	
	/**
	 * 获取当前用户对象
	 * @return
	 */
	public static ASysUser getCurrentASysUser(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Object obj = request.getSession().getAttribute(CURRENT_USER);
		if(obj==null){
			throw new LoginException("用户信息为空，请先登录！");
		}
		return (ASysUser)obj;
	}
	
	/**
	 * 获取当前用户对象(不做空判断)
	 * @return
	 */
	public static ASysUser getUserAllowNull(){
		try{
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Object obj = request.getSession().getAttribute(CURRENT_USER);
			return obj==null?null:(ASysUser)obj;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 设置当前用户对象
	 * @param aSysUser
	 */
	public static void setCurrentASysUser( ASysUser aSysUser ) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		request.getSession().setAttribute(CURRENT_USER, aSysUser);
	}
	
	/**
	 * 获取客户端IP地址
	 * @return
	 */
	public static String getClientIP() {
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
}

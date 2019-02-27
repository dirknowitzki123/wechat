package com.by.frame.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.CaptchaUtil;
import com.by.core.util.Page;
import com.by.frame.service.ILoginService;
import com.by.system.model.ASysUser;

/**
 * @author hemf
 * 登录
 */
@Controller
@RequestMapping(value="/frame/login")
@SuppressWarnings("unused")
public class LoginController extends BaseController {
	
	private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
	private static final String X_REQUESTED_WITH = "X-Requested-With";
	
	private static final String CACHE_LAST_MODIFIED_KEY = "If-Modified-Since";
	private static final String CACHE_LAST_MODIFIED_VALUE = "Thu, 01 Jun 1970 00:00:00 GMT";
	private static final String CACHE_PRAGMA_KEY = "Pragma";
	private static final String CACHE_PRAGMA_VALUE = "no-cache";
	private static final String CACHE_CONTROL_KEY = "Cache-Control";
	private static final String CACHE_CONTROL_VALUE = "no-cache";
	private static final String CACHE_EXPIRES_KEY = "Expires";
	private static final Integer CACHE_EXPIRES_VALUE = -10;
	
	
	
	@Autowired private ILoginService loginService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		if ( savedRequest != null ) {
			String uri = savedRequest.getRequestURI();
			String extension = FilenameUtils.getExtension(uri);
			
			if ( XML_HTTP_REQUEST.equals( request.getHeader( X_REQUESTED_WITH ) ) ) {
				super.getCurrentASysUser();
				System.out.println("登陆成功");
				return "redirect:/";
			}
		}
		
		try { 
			super.getCurrentASysUser();
		} catch( Exception e ) {
			
			//解决IE下 登陆304缓存的问题
			response.setHeader(CACHE_LAST_MODIFIED_KEY, CACHE_LAST_MODIFIED_VALUE);
			response.setHeader(CACHE_PRAGMA_KEY, CACHE_PRAGMA_VALUE);
			response.setHeader(CACHE_CONTROL_KEY,CACHE_CONTROL_VALUE);
			response.setDateHeader(CACHE_EXPIRES_KEY, CACHE_EXPIRES_VALUE);
			
			return "/frame/login/login.index";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String loginForm(HttpServletRequest request) {
		return "/frame/login/login.form";
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public Page<?> login( Page<?> page, ASysUser aSysUser, String code) {
		loginService.login(aSysUser, code);
		return page;
	}
	
	@RequestMapping(value="/captcha",method=RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 // 设置响应的类型格式为图片格式   
        response.setContentType("image/jpeg");   
        //禁止图像缓存。   
        response.setHeader(CACHE_PRAGMA_KEY, CACHE_PRAGMA_VALUE);   
        response.setHeader(CACHE_CONTROL_KEY, CACHE_CONTROL_VALUE);   
        response.setDateHeader(CACHE_EXPIRES_KEY, CACHE_EXPIRES_VALUE);   
           
        HttpSession session = request.getSession();   
           
        CaptchaUtil captcha = new CaptchaUtil(110,40,4,100);  
        session.setAttribute("captcha", captcha.getCode());   
        captcha.write(response.getOutputStream());   
	}
	
	
}

package com.by.frame.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;

/**
 * @author hemf
 * 注销
 */
@Controller
@RequestMapping("/frame")
public class LogoutController extends BaseController {
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/frame/login";
	}
	
	@ResponseBody
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public Page<Object> logout(Page<Object> page) {
		SecurityUtils.getSubject().logout();
		return page;
	}
}

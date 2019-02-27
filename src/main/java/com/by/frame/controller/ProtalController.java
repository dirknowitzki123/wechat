package com.by.frame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.frame.service.IProtalService;
import com.by.system.model.ASysMenu;

/**
 * @author hemf
 * 主题
 */
@Controller
@RequestMapping("/frame/protal")
public class ProtalController extends BaseController {
	
	@Autowired
	private IProtalService protalService;

	@RequestMapping(method=RequestMethod.GET)
	public String index() {
		return "/frame/protal/protal.index";
	}
	
	@ResponseBody
	@RequestMapping( value = "/menulist", method = RequestMethod.POST )
	public Page<Object> menuList(Page<Object> page) {
		String userId = super.getCurrenUserId();
		List<ASysMenu> list = protalService.getMenusByUserId(userId);
		page.setList(list);
		return page;
	}
	
	@RequestMapping(value="/modifyPassword", method=RequestMethod.GET)
	public String modifyPassword() {
		return "/frame/protal/protal.modifyPassword";
	}
	
	@ResponseBody
	@RequestMapping(value="/modifyPassword", method=RequestMethod.POST)
	public Page<Object> modifyPassword(Page<Object> page, String newPassword, String oldPassword) {
		protalService.modifyPassword(super.getCurrentASysUser(), newPassword, oldPassword);
		return page;
	}
}

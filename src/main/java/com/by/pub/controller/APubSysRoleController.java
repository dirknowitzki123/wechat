package com.by.pub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.system.model.ASysRole;
import com.by.system.service.IASysRoleService;

@Controller
@RequestMapping(value="/pub/asysrole")
public class APubSysRoleController  extends BaseController {
	
	@Autowired private IASysRoleService aSysRoleService;
	
	@ResponseBody
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> Login(Page<?> page) {
		page.startPage();
		List<ASysRole> list = aSysRoleService.getList(page.getParams());
		page.setList(list);
		return page;
	}
}

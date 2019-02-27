package com.by.system.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.system.model.ASysResc;
import com.by.system.service.IASysRescService;

@Controller
@RequestMapping(value="/system/asysresc")
public class ASysRescController extends BaseController{

	@Autowired private IASysRescService asysRescService;
	
	@RequiresPermissions(value="system:asysresc:query")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/system/asysresc/asysresc.index";
	}
	@ResponseBody
	@RequiresPermissions(value="system:asysresc:query")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> list(Page<?> page) {		
		page.startPage();
		List<ASysResc> list = asysRescService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	@RequiresPermissions(value="system:asysresc:save" )
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/system/asysresc/asysresc.form";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysresc:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, ASysResc aSysResc) {
		asysRescService.save(aSysResc);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysresc:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		asysRescService.delete(ids);
		return page;
	}
}

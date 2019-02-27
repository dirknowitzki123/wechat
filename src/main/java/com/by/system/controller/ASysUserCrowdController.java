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
import com.by.system.model.ASysUserCrowd;
import com.by.system.service.IASysUserCrowdService;

/**
 * 用户群Controller
 * @author hmzhou
 *
 */
@Controller
@RequestMapping(value="/system/asysusercrowd")
public class ASysUserCrowdController extends BaseController {
	
	@Autowired private IASysUserCrowdService aSysUserCrowdService;
	
	@RequiresPermissions(value="system:asysusercrowd:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/system/asysusercrowd/aSysUserCrowd.index";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysusercrowd:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> list(Page<?> page) {
		List<ASysUserCrowd> list = aSysUserCrowdService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	@RequiresPermissions(value="system:asysusercrowd:list" )
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/system/asysusercrowd/aSysUserCrowd.form";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysusercrowd:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, ASysUserCrowd aSysUserCrowd) {
		aSysUserCrowdService.save(aSysUserCrowd);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysusercrowd:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysUserCrowdService.delete(ids);
		return page;
	}
}

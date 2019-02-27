package com.by.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.ApplicationUtil;
import com.by.core.util.Page;
import com.by.system.model.ASysMenu;
import com.by.system.service.IASysMenuService;

@Controller
@RequestMapping(value="/system/asysmenu")
public class ASysMenuController extends BaseController {
	
	@Autowired private IASysMenuService aSysMenuService;
	
	@RequiresPermissions(value="system:asysmenu:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/system/asysmenu/asysmenu.index";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysmenu:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> list(Page<?> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = page.getParams();
		List<ASysMenu> list = aSysMenuService.getList(map);
		page.setList(list);
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value="/treeList", method = RequestMethod.POST)
	public Page<?> treeList(Page<?> page ,@RequestParam("isUserAble") String isUserAble) {
		Map<String, Object> map = page.getParams();
		map.put("isUserAble", isUserAble);
		map.put("sysCodes", ApplicationUtil.getModules());
		List<ASysMenu> list = aSysMenuService.getMenusByModules(map);
		page.setList(list);
		return page;
	}
	
	@RequiresPermissions(value="system:asysmenu:list")
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/system/asysmenu/asysmenu.form";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysmenu:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, ASysMenu aSysMenu) {
		aSysMenuService.save(aSysMenu);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysmenu:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysMenuService.delete(ids);
		return page;
	}
	
}

package com.by.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.system.model.ASysRole;
import com.by.system.model.ASysRoleMenu;
import com.by.system.service.IASysRoleMenuService;
import com.by.system.service.IASysRoleService;

@Controller
@RequestMapping(value="/system/asysrole")
public class ASysRoleController extends BaseController {
	
	@Autowired private IASysRoleService aSysRoleService;
	
	@Autowired
	private IASysRoleMenuService aSysRoleMenuService;
	
	@RequiresPermissions(value="system:asysrole:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/system/asysrole/asysrole.index";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysrole:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> Login(Page<?> page) {
		page.startPage();
		List<ASysRole> list = aSysRoleService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysrole:list")
	@RequestMapping(value="/getList", method = RequestMethod.POST)
	public Page<?> list(Page<?> page ,@RequestParam("isUse") String isUse) {
		Map<String, Object> map = page.getParams();
		map.put("isUse", isUse);
		List<ASysRole> list = aSysRoleService.getList(map);
		page.setList(list);
		return page;
	}
	
	@RequiresPermissions(value="system:asysrole:list")
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/system/asysrole/asysrole.form";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysrole:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, ASysRole aSysRole) {
		aSysRoleService.save(aSysRole);
		return page;
	}
	
	
	@ResponseBody
	@RequiresPermissions(value="system:asysrole:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysRoleService.delete(ids);
		return page;
	}
	
	/**
	 * 根据角色ID查询菜单
	 * @param page
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysrole:setMenu")
	@RequestMapping(value="/getMenuByRoleId", method = RequestMethod.POST)
	public Page<?> getMenuByRoleId(Page<?> page, @RequestParam("roleId") String roleId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		List<ASysRoleMenu> list= aSysRoleMenuService.getList(map);
		page.setList(list);
		return page;
	}
	
	@RequiresPermissions(value="system:asysrole:setMenu")
	@RequestMapping(value="/menu", method = RequestMethod.GET)
	public String setMenu(){
		return "/system/asysrole/asysrole.menu";
	}
	
	/**
	 * 
	 * @param page
	 * @param roleId 角色ID
	 * @param ids 更新的菜单集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequiresPermissions(value="system:asysrole:setMenu")
	@RequestMapping(value="/updateMenus", method = RequestMethod.POST)
	public Page<?> updateMenus(Page<?> page, @RequestBody Map<String,Object> map){
		List<String> ids = (List<String>) map.get("ids");
		String roleId = (String) map.get("id");
		ASysRole role = aSysRoleService.getBygetByPrimaryKey(roleId);
		aSysRoleService.save(role, ids);
		return page;
	}
	
}

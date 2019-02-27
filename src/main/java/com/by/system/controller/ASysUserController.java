package com.by.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.ExcelUtil;
import com.by.core.util.Page;
import com.by.system.model.ASysRoleUser;
import com.by.system.model.ASysUser;
import com.by.system.model.ASysUserDetail;
import com.by.system.service.IASysRoleUserService;
import com.by.system.service.IASysUserService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/system/asysuser")
public class ASysUserController extends BaseController {
	
	@Autowired private IASysUserService aSysUserService;
	
	@Autowired
	private IASysRoleUserService aSysRoleUserService;
	
	@RequiresPermissions(value="system:asysuser:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/system/asysuser/asysuser.index";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysuser:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> list(Page<?> page) {
		List<ASysUser> list = aSysUserService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	@RequiresPermissions(value="system:asysuser:list" )
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/system/asysuser/asysuser.form";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysuser:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, ASysUser aSysUser,ASysUserDetail aSysUserDetail) {
		aSysUserService.save(aSysUser,aSysUserDetail);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysuser:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysUserService.delete(ids);
		return page;
	}
	
	/**
	 * 根据角色ID查询菜单
	 * @param page
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysuser:role" )
	@RequestMapping(value="/getRoleByUserId", method = RequestMethod.POST)
	public Page<?> getRoleByUserId(Page<?> page, @RequestParam("userId") String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<ASysRoleUser> list= aSysRoleUserService.getList(map);
		page.setList(list);
		return page;
	}
	
	@RequiresPermissions(value="system:asysuser:role" )
	@RequestMapping(value="/role", method = RequestMethod.GET)
	public String role(Page<?> page) {
		return "/system/asysuser/asysuser.role";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequiresPermissions(value="system:asysuser:role" )
	@RequestMapping(value="/updateRoles", method = RequestMethod.POST)
	public Page<?> updateMenus(Page<?> page, @RequestBody Map<String,Object> map){
		List<String> ids = (List<String>) map.get("ids");
		String userId = (String) map.get("id");
		ASysUser user = aSysUserService.getBygetByPrimaryKey(userId);
		aSysUserService.save(user, ids);
		return page;
	}
	/**
	 * 用户导出
	 */
	@RequestMapping(value="/export", method = RequestMethod.GET)
	public void export(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String[] columns={"userName","loginName","birhday"};
		String[] columnNames={"用户姓名","登录名","生日"};
		int rowStart = 0;
		ExcelUtil.export("", aSysUserService.getList(new HashMap<String,Object>()), "用户", "Sheet1", columns, columnNames, rowStart, request, response);
	}
	/**
	 * 重置密码
	 * @param page
	 * @param aSysUser
	 * @param aSysUserDetail
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysuser:reset")
	@RequestMapping(value="/reset/password", method = RequestMethod.POST)
	public Page<?> resetPassword(Page<?> page, String id) {
		aSysUserService.resetPassword(id);
		return page;
	}
}

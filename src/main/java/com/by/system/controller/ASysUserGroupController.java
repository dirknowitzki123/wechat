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
import com.by.system.model.ASysUser;
import com.by.system.model.ASysUserGroup;
import com.by.system.service.IASysGroupUserService;
import com.by.system.service.IASysUserGroupService;

/**
 * 用户组Controller
 * @author hmzhou
 *
 */
@Controller
@RequestMapping(value="/system/asysusergroup")
public class ASysUserGroupController extends BaseController {
	
	@Autowired private IASysUserGroupService aSysUserGroupService;
	
	@Autowired private IASysGroupUserService aSysGroupUserService;
	
	@RequiresPermissions(value="system:asysusergroup:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/system/asysusergroup/aSysUserGroup.index";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysusergroup:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> list(Page<?> page) {
		List<ASysUserGroup> list = aSysUserGroupService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	@RequiresPermissions(value="system:asysusergroup:list" )
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/system/asysusergroup/aSysUserGroup.form";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysusergroup:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, ASysUserGroup aSysUserGroup) {
		aSysUserGroupService.save(aSysUserGroup);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysusergroup:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysUserGroupService.delete(ids);
		return page;
	}
	
	@RequiresPermissions(value="system:asysusergroup:list")
	@RequestMapping(value="/togroupuser", method = RequestMethod.GET)
	public String userManager(Page<?> page) {
		return "/system/asysusergroup/aSysUserGroup.user";
	}
	
	/**
	 * 根据用户组编号查询 绑定人员列表
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysusergroup:list")
	@RequestMapping(value="/groupusers", method = RequestMethod.POST)
	public Page<?> groupusers(Page<?> page) {
		List<ASysUser> list = aSysGroupUserService.getUserList(page.getParams());
		page.setList(list);
		return page;
	}
	
	
	@RequiresPermissions(value="system:asysusergroup:list")
	@RequestMapping(value="/toadduser", method = RequestMethod.GET)
	public String toadduser(Page<?> page) {
		return "/system/asysusergroup/aSysUserGroup.adduser";
	}
	/**
	 * 根据用户组编号查询 绑定人员列表
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysusergroup:list")
	@RequestMapping(value="/notBindUsers", method = RequestMethod.POST)
	public Page<?> notBindUsers(Page<?> page) {
		List<ASysUser> list = aSysGroupUserService.getNotBindUsers(page.getParams());
		page.setList(list);
		return page;
	}
	
	/**
	 * 用户组绑定用户
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysusergroup:save")
	@RequestMapping(value="/saveBindUsers", method = RequestMethod.POST)
	public Page<?> saveBindUsers(Page<?> page,@RequestParam("loginNames[]") List<String> loginNames,@RequestParam("groupNo") String groupNo,@RequestParam("groupName") String groupName) {
		aSysGroupUserService.saveBindUsers(loginNames, groupNo);
		return page;
	}
	
	/**
	 * 移除用户组绑定的用户
	 * @param page
	 * @param loginNames
	 * @param groupNo
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysusergroup:delete")
	@RequestMapping(value="/delGroupUser", method = RequestMethod.POST)
	public Page<?> delGroupUser(Page<?> page, @RequestParam("loginNames[]") List<String> loginNames,@RequestParam("groupNo") String groupNo) {
		aSysGroupUserService.delGroupUser(loginNames, groupNo);
		return page;
	}
	
}

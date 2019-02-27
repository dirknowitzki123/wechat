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
import com.by.system.model.ASysSecret;
import com.by.system.service.IASysSecretService;

@Controller
@RequestMapping(value="/system/asyssecret")
public class ASysSecretController extends BaseController {
	
	@Autowired private IASysSecretService aSyssecretService;
	
	
	/**
	 * 跳转页面到密钥管理
	 * 
	 * */
	@RequiresPermissions(value="system:keymanage:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/system/asyssecret/asyssecret.index";
	}

	/**
	 * 返回密钥信息
	 * 
	 * @param page<?> page
	 * */
	@ResponseBody
	@RequiresPermissions(value="system:keymanage:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> getSecretList(Page<?> page) {
		
		List<ASysSecret> secret = aSyssecretService.getList(page.getParams());
		page.setList(secret);
		return page;
	}
	

	/**
	 * 进入添加或修改页面
	 * 
	 * */
	@RequiresPermissions(value="system:keymanage:list")
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		
		return "/system/asyssecret/asyssecret.form";
	}

	/**
	 * 根据是否有id来进行添加和修改操作
	 * 
	 * @param ASysSecret
	 * return page
	 * 
	 * */
	@ResponseBody
	@RequiresPermissions(value="system:keymanage:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page , ASysSecret asyssecret) {
		aSyssecretService.insert(asyssecret);
		return page;
	}
	

	/**
	 * 删除一条或多条记录
	 * 
	 * @param  List<String> ids
	 * */
	@ResponseBody
	@RequiresPermissions(value="system:keymanage:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		System.out.println(ids);
		aSyssecretService.deleteByPrimaryKey(ids);
		return page;
	}
	
/*	*//**
	 * 按id查询一条记录
	 * 
	 * @param  string roleId
	 * return page
	 * *//*
	@ResponseBody
	@RequiresPermissions(value="system:keymanage:list")
	@RequestMapping(value="/getMenuByRoleId", method = RequestMethod.POST)
	public Page<?> getMenuByRoleId(Page<?> page, @RequestParam("roleId") String roleId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", roleId);
		List<ASysSecret> list= aSyssecretService.getListId(map);
		page.setList(list);
		return page;
	}
		*/
}

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
import com.by.system.model.ASysAppVersions;
import com.by.system.service.IASysAppVersionsService;

@Controller
@RequestMapping(value="/system/aSysAppVersions")
public class ASysAppVersionsController  extends BaseController {

	@Autowired 
	private IASysAppVersionsService aSysAppVersionsService;

	/**
	 * 进入index
	 * @param page
	 * @return
	 */
	@RequiresPermissions(value="system:asysappversions:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/system/asysappversions/aSysAppVersions.index";
	}
	
	/**
	 * 获取列表数据
	 * @param page 页面参数
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysappversions:index")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> typeList(Page<?> page) {
		 List<ASysAppVersions> list = aSysAppVersionsService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	/**
	 * 进入form
	 * @param page
	 * @return
	 */
	@RequiresPermissions(value="system:asysappversions:list")
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/system/asysappversions/aSysAppVersions.form";
	}
	
	/**
	 * 添加
	 * @param page 
	 * @param aSysAppVersions
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysappversions:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, ASysAppVersions aSysAppVersions) {
		aSysAppVersionsService.save(aSysAppVersions);
		return page;
	}
	
	/**
	 * 删除
	 * @param page
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="system:asysappversions:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysAppVersionsService.delete(ids);
		return page;
	}
	
}

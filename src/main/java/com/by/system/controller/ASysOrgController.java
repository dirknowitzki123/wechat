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
import com.by.system.dto.ASysOrgDTO;
import com.by.system.model.ASysOrg;
import com.by.system.service.IASysOrgService;

@Controller
@RequestMapping(value="/system/asysorg")
public class ASysOrgController extends BaseController{
	@Autowired
	private IASysOrgService aSysOrgService;
	
	@RequiresPermissions(value="system:asysorg:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page){
		return "/system/asysorg/asysorg.index";
	}
	@ResponseBody
	@RequiresPermissions(value="system:asysorg:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> list(Page<?> page) {
		List<ASysOrgDTO> list = aSysOrgService.getListBo(page.getParams());
		page.setList(list);
		return page;
	}
	@ResponseBody
	@RequiresPermissions(value="system:asysorg:list")
	@RequestMapping(value="/queryTreeList", method = RequestMethod.POST)
	public List<ASysOrg> queryTreeList(Page<?> page) {
		List<ASysOrg> list = aSysOrgService.getList(page.getParams());
		return list;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysorg:list" )
	@RequestMapping(value="/getOrg", method = RequestMethod.POST)
	public ASysOrg getOrg(@RequestBody ASysOrg org) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", org.getId());
		return aSysOrgService.get(map);
	}
	
	@RequiresPermissions(value="system:asysorg:list" )
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/system/asysorg/asysorg.form";
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysorg:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, ASysOrg aSysOrg) {
		aSysOrgService.save(aSysOrg);
		return page;
	}
	
	@ResponseBody
	@RequiresPermissions(value="system:asysorg:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysOrgService.delete(ids);
		return page;
	}

}

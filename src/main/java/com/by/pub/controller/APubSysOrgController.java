package com.by.pub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.system.dto.ASysOrgDTO;
import com.by.system.service.IASysOrgService;

@Controller
@RequestMapping(value="/pub/asysorg")
public class APubSysOrgController  extends BaseController{
	@Autowired	private IASysOrgService aSysOrgService;
	
	@ResponseBody
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> list(Page<?> page) {
		List<ASysOrgDTO> list = aSysOrgService.getListBo(page.getParams());
		page.setList(list);
		return page;
	}
}

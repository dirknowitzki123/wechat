package com.by.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.system.model.ASysOrgPosition;
import com.by.system.service.IASysOrgPositionService;

@Controller
@RequestMapping(value="/system/asysorgposition")
public class ASysOrgPositionController extends BaseController {
	
	@Autowired private IASysOrgPositionService asysOrgPositionService ;	
	@ResponseBody
	@RequestMapping(value="/getposition", method = RequestMethod.POST)
	public Page<?> list(Page<?> page,@RequestParam("orgCode") String orgCode) {
		List<ASysOrgPosition> list = asysOrgPositionService.getPositionInfoByOrgCode(orgCode);
		page.setList(list);
		return page;
	}		
}

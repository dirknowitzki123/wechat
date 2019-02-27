package com.by.system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.system.model.ASysUserDetail;
import com.by.system.service.IASysUserDetailService;

@Controller
@RequestMapping(value="/system/asysuserdetail")
public class ASysUserDetailController extends BaseController {
	
	@Autowired private IASysUserDetailService sSysUserDetailService;

	
	@ResponseBody
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> list(Page<?> page,@RequestParam("loginName") String loginName) {
		//page.startPage();
		Map<String,Object> map=page.getParams();
		map.put("id", loginName);
		List<ASysUserDetail> list = sSysUserDetailService.getList(map);
		page.setList(list);
		return page;
	}		
}

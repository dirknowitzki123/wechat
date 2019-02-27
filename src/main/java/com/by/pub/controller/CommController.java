package com.by.pub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.pub.model.UserOrgBO;
import com.by.pub.service.ICommService;

@Controller
@RequestMapping("/pub/asysatt")
public class CommController extends BaseController {
	
	@Autowired private ICommService commService;
	
	@RequestMapping(value="/seletuser", method = RequestMethod.GET)
	public String seletuser(Page<?> page) {
		return "/pub/comm/userOrg.form";
	}
	
	@ResponseBody
	@RequestMapping(value="/listuserorg", method=RequestMethod.POST)
	public Page<Object> list(Page<Object> page) {
		List<UserOrgBO> list = commService.getListUserOrg(page.getParams());
		page.setList(list);
		return page;
	}	
}

package com.by.frame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.core.controller.BaseController;
import com.by.frame.service.IHomePageService;

/**
 * @author hemf
 * 首页
 */
@Controller
@RequestMapping(value = "/frame/homepage" )
@SuppressWarnings("unused")
public class HomePageController extends BaseController {
	@Autowired private IHomePageService homePageService;
	@RequestMapping(value="index",method=RequestMethod.GET )
	public String index() {
		return "frame/homepage/homepage.index";
	}
	
	
}

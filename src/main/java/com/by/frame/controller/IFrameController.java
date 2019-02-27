package com.by.frame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frame/iframe")
public class IFrameController {

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return "/frame/iframe/iframe.index";
	}
}

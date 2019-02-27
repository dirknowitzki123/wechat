package com.by.system.intfc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.frame.intfc.agrument.MapWrap;
import com.by.frame.intfc.annotation.TransBody;
import com.by.frame.intfc.model.TransResult;
import com.by.system.intfc.service.IIntCodeService;

/**
 * ***********************************
 * @describe       系统对外码表接口
 * @version        V1.0
 * @author         张敏
 * @phone          13699488431
 * @date           2016年5月15日
 *
 * @modifyReason 
 * @modifyAuthor
 * @phone           
 * @modifyDate 
 * ***********************************
 */
@Controller
@RequestMapping("/anon/system/code")
public class IntCodeController extends BaseController{

	@Autowired
	private IIntCodeService intCodeService;
	
	@ResponseBody
	@RequestMapping(value="/getCode.int", method = RequestMethod.POST)
	public TransResult getCode(@TransBody MapWrap<String, Object> map) {
		return super.getTransResult(intCodeService.getCode(map.getInnerMap()));
	}
}

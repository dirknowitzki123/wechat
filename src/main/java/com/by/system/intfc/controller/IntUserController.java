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
import com.by.system.intfc.service.IIntUserService;
/**
 * ***********************************
 * @describe       用户对外接口
 * @version        V1.0
 * @author         QML
 * @phone          15208262710
 * @date           2016年4月25日
 *
 * @modifyReason 
 * @modifyAuthor
 * @phone           
 * @modifyDate 
 * ***********************************
 */
@Controller
@RequestMapping("/anon/system/user")
public class IntUserController extends BaseController{
	@Autowired
	private IIntUserService intUserService;
	
	/**
	 * 登录
	 * @param loginName登录名   password密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login.int", method = RequestMethod.POST)
	public TransResult login(@TransBody MapWrap<String, Object> map) {
		intUserService.login(map.getInnerMap());
		return super.getTransResult(null);
	}
	/**
	 * 用户信息查询
	 * @param loginName登录名
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query.int", method = RequestMethod.POST)
	public TransResult query(@TransBody MapWrap<String, Object> map) {
		return super.getTransResult(intUserService.get(map.getInnerMap()));
	}
	/**
	 * 销售信息查询
	 * @param loginName登录名
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querySaler.int", method = RequestMethod.POST)
	public TransResult querySaler(@TransBody MapWrap<String, Object> map) {
		return super.getTransResult(intUserService.getSaler(map.getInnerMap()));
	}
	
}

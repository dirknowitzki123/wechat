/**
 * ***********************************
 * @describe       博雅分期GO   
 * @version        V1.0
 * @author         张敏
 * @phone          13699488431
 * @date           2016年5月24日
 * ***********************************
 */
package com.by.pub.intfc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.frame.intfc.agrument.MapWrap;
import com.by.frame.intfc.annotation.TransBody;
import com.by.frame.intfc.model.TransResult;
import com.by.pub.intfc.service.AppPubAttaService;

/**
 * ***********************************
 * @describe       
 * @version        V1.0
 * @author         张敏
 * @phone          13699488431
 * @date           2016年5月24日
 *
 * @modifyReason 
 * @modifyAuthor
 * @phone           
 * @modifyDate 
 * ***********************************
 */
@Controller
@RequestMapping("/anon/app/atta")
public class AppPubAttaController extends BaseController{

	@Autowired
	private AppPubAttaService appPubAttaService;
	
	/**
	 * 根据附件ID 获取附件
	 * @param map {id:''}
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getById.int", method = RequestMethod.POST)
	public TransResult login(@TransBody MapWrap<String, Object> map){
		return super.getTransResult(appPubAttaService.getById(map.getInnerMap()));
	}
	
	/**
	 * 保存附件
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save.int", method = RequestMethod.POST)
	public TransResult save(@TransBody MapWrap<String, Object> map){
		return super.getTransResult(appPubAttaService.save(map.getInnerMap()));
	}
	
	/**
	 * 删除附件
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete.int", method = RequestMethod.POST)
	public TransResult delete(@TransBody MapWrap<String, Object> map){
		appPubAttaService.delete(map.getInnerMap());
		return super.getTransResult(null);
	}
	
	@ResponseBody
	@RequestMapping(value="/getListByInstDateDesc.int", method = RequestMethod.POST)
	public TransResult getListByInstDateDesc(@TransBody MapWrap<String, Object> map){
		appPubAttaService.getListByInstDateDesc(map.getInnerMap());
		return super.getTransResult(null);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getList.int", method = RequestMethod.POST)
	public TransResult getList(@TransBody MapWrap<String, Object> map){
		return super.getTransResult(appPubAttaService.getList(map.getInnerMap()));
	}
	
}

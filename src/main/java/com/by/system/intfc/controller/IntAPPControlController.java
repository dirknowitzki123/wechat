/**
 * 
 */
package com.by.system.intfc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.frame.intfc.annotation.TransBody;
import com.by.frame.intfc.model.TransResult;
import com.by.system.model.ASysAppVersions;
import com.by.system.service.IASysAppVersionsService;

/**
 * ***********************************
 * @describe 	app管理接口
 * @version        V1.0
 * @author         YQR
 * @phone          13086658775
 * @date           2016年5月24日
 *
 * @modifyReason 
 * @modifyAuthor
 * @phone           
 * @modifyDate 
 * ***********************************
 */
@Controller
@RequestMapping("/anon/system/app")
public class IntAPPControlController extends BaseController{
	
	@Autowired private IASysAppVersionsService sysAppVersionsService;
	
	@ResponseBody
	@RequestMapping(value="/queryversion.int")
	public TransResult queryNewVersion(@TransBody ASysAppVersions aSysAppVersions) {
		return super.getTransResult(sysAppVersionsService.queryNewVersion(aSysAppVersions));
	}
	
}

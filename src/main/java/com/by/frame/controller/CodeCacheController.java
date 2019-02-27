package com.by.frame.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.core.controller.BaseController;
import com.by.core.util.Page;
import com.by.frame.bo.CodeBo;
import com.by.system.service.IASysCodeInfoService;

/**
 * @author hemf
 * 码表缓存
 */
@Controller
@RequestMapping( "/frame/code" )
public class CodeCacheController extends BaseController {
	
	@Autowired IASysCodeInfoService aSysCodeInfoService;
	
	@Deprecated
	@ResponseBody
	@RequestMapping( value = "/cache", method = RequestMethod.POST )
	public Page<?> cache( Page<?> page, @RequestParam("types[]") List<String> types ) {
		if ( types.size() == 0 ) return page;
		Map<String, Object> map = aSysCodeInfoService.getCode(types);
		page.setMap(map);;
		return page;
	}
	
	@ResponseBody
	@RequestMapping( value = "/cache/2", method = RequestMethod.POST )
	public Page<?> cache2( Page<?> page, @RequestBody List<CodeBo> codes ) {
		if ( codes.size() == 0 ) return page;
		Map<String, Object> map = aSysCodeInfoService.getCode2(codes);
		page.setMap(map);
		return page;
	}
	
	
}

package com.by.pub.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.by.core.controller.BaseController;
import com.by.core.exception.FrameworkException;
import com.by.core.util.Page;
import com.by.pub.model.ASysAtt;
import com.by.pub.model.ASysAttExt;
import com.by.pub.service.IASysAttExtService;

@Controller
@RequestMapping("/pub/asysattext")
public class ASysAttExtController extends BaseController {
	
	@Autowired
	private IASysAttExtService aSysAttExtService;
	
	@RequiresPermissions(value="pub:asysattext:query")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(){
		return "pub/asysattext/asysattext.index";
	}
	
	@ResponseBody
	@RequiresPermissions(value="pub:asysattext:query")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> list(Page<?> page) {
		page.startPage();
		List<ASysAttExt> list = aSysAttExtService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "pub/asysattext/asysattext.form";
	}
	
	@RequestMapping(value="/view", method = RequestMethod.GET)
	public String view(Page<?> page) {
		return "pub/asysattext/asysattextview.form";
	}
	
	@ResponseBody
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public void upload(
		HttpServletRequest request,
		HttpServletResponse response,
		Page<Object> page,
		@RequestParam(value = "file[]", required = false) MultipartFile[] files,
        @RequestParam(value = "file", required = true) MultipartFile file,
        ASysAtt aSysAtt,
        ASysAttExt aSysAttExt
	) {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		aSysAttExt = aSysAttExtService.upload( file, aSysAtt, aSysAttExt);
		page.setT(aSysAttExt);
		
		try{
			PrintWriter writer = response.getWriter();
			writer.println( JSONObject.toJSONString(page) );
		} catch (IOException e) {
			throw new FrameworkException("系统文件输出异常");
		}
		
	}
	
	@ResponseBody
	@RequiresPermissions(value="pub:asysattext:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		aSysAttExtService.delete(ids);
		return page;
	}

}

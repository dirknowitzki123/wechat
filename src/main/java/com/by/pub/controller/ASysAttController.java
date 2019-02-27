package com.by.pub.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.by.core.controller.BaseController;
import com.by.core.exception.BusinessException;
import com.by.core.exception.FrameworkException;
import com.by.core.util.Page;
import com.by.core.util.StringUtils;
import com.by.pub.model.ASysAtt;
import com.by.pub.service.IASysAttService;
import com.file.model.FileBody;

@Controller
@RequestMapping("/pub/asysatt")
public class ASysAttController extends BaseController {
	
	@Autowired private IASysAttService aSysAttService;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return "/pub/asysatt/asysatt.index";
	}
	
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public void upload(
		HttpServletRequest request,
		HttpServletResponse response,
		Page<Object> page,
		@RequestParam(value = "file[]", required = false) MultipartFile[] files,
        @RequestParam(value = "file", required = true) MultipartFile file,
        ASysAtt aSysAtt
	) {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if(request.getSession(false)==null)
	           System.out.println("Session has been invalidated!");
	        else
	           System.out.println("Session is active!"); 
		aSysAtt = aSysAttService.uploadPUB( file, aSysAtt );
		page.setT(aSysAtt);
		
		try{
			PrintWriter writer = response.getWriter();
			writer.println( JSONObject.toJSONString(page) );
		} catch (IOException e) {
			throw new FrameworkException("系统文件输出异常");
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public Page<Object> delete(Page<Object> page, @PathVariable String id) {
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		aSysAttService.delete(ids);
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public Page<Object> list(Page<Object> page) {
		List<ASysAtt> list = aSysAttService.getList(page.getParams());
		page.setList(list);
		return page;
	}
	
	@RequestMapping(value="/download/thumb/{uuid}", method=RequestMethod.GET)
	public void download(@PathVariable String uuid, HttpServletRequest request, HttpServletResponse response) { 
		download(uuid, "thumb", request, response);
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value="/download/{uuid}", method=RequestMethod.GET)
	public void download(@PathVariable String uuid, String thumb, HttpServletRequest request ,HttpServletResponse response) {

		if ( thumb == null ) thumb = "";
		
		FileBody fileBody = aSysAttService.downloadPUB(uuid, thumb);
		
		
		byte[] data = new byte[1024];
        InputStream inputStream = fileBody.getInputStream();
        String filename = fileBody.getOriginFileName() + fileBody.getExtName();
        
        try {
        	String agent = request.getHeader("USER-AGENT");    
        	if (null != agent && -1 != agent.indexOf("MSIE")){ //IE    
        		filename = java.net.URLEncoder.encode(filename, "UTF-8");   
        	} else if (null != agent && -1 != agent.indexOf("Mozilla")) { //Firefox        
        		filename = new String(filename.getBytes("UTF-8"),"iso-8859-1");     
        	} else {  
        		filename = java.net.URLEncoder.encode(filename, "UTF-8");   
        	}   
		} catch (Exception e) {
			throw new BusinessException("文件下载异常");
		}
        
        response.addHeader("Content-Length",fileBody.getFileSize());//设置下载文件大小
        response.addHeader("Content-Disposition", "attachment;filename="+filename);
        
        String extension = fileBody.getExtName();
        if ( StringUtils.isNotEmpty( extension ) && aSysAttService.imageExtensions.contains( extension ) ) {
        	response.setContentType("image/png;charset=UTF-8");
        } else {
        	response.setContentType("application/octet-stream");
        }
        
		try {

			OutputStream outputStream = response.getOutputStream();
			int c;
            while( ( c = inputStream.read( data ) ) !=-1 ) {
            	outputStream.write(data, 0, c);
            }
            outputStream.flush();
            outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value="/download/{busiNo}/{attTyp}", method=RequestMethod.GET)
	public void download(@PathVariable String busiNo, @PathVariable String attTyp, String thumb,
			HttpServletRequest request ,HttpServletResponse response) {
		if ( thumb == null ) thumb = "";
		
		FileBody fileBody = aSysAttService.downloadPUB(busiNo,attTyp, thumb);
		if(null == fileBody){
			throw new BusinessException("文件下载异常");
		}
		byte[] data = new byte[1024];
        InputStream inputStream = fileBody.getInputStream();
        String filename = fileBody.getOriginFileName() + fileBody.getExtName();
        
        try {
        	String agent = request.getHeader("USER-AGENT");    
        	if (null != agent && -1 != agent.indexOf("MSIE")){ //IE    
        		filename = java.net.URLEncoder.encode(filename, "UTF-8");   
        	} else if (null != agent && -1 != agent.indexOf("Mozilla")) { //Firefox        
        		filename = new String(filename.getBytes("UTF-8"),"iso-8859-1");     
        	} else {  
        		filename = java.net.URLEncoder.encode(filename, "UTF-8");   
        	}   
		} catch (Exception e) {
			throw new BusinessException("文件下载异常");
		}
        
        response.addHeader("Content-Length",fileBody.getFileSize());//设置下载文件大小
        response.addHeader("Content-Disposition", "attachment;filename="+filename);
        
        String extension = fileBody.getExtName();
        if ( StringUtils.isNotEmpty( extension ) && aSysAttService.imageExtensions.contains( extension ) ) {
        	response.setContentType("image/png;charset=UTF-8");
        } else {
        	response.setContentType("application/octet-stream");
        }
		try {
			OutputStream outputStream = response.getOutputStream();
			int c;
            while( ( c = inputStream.read( data ) ) !=-1 ) {
            	outputStream.write(data, 0, c);
            }
            outputStream.flush();
            outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

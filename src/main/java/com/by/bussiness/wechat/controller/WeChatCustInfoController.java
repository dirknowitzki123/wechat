package com.by.bussiness.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.bussiness.wechat.service.IWeChatCustInfoService;
import com.by.core.exception.BusinessException;
import com.by.core.exception.WeChatSessionException;
import com.by.core.util.Page;
import com.by.core.util.StringUtils;
import com.by.pub.service.IASysAttService;
import com.by.wechat.util.WeChatSessionUtil;
import com.file.model.FileBody;

/**
 * 用户信息
 * Created by yiqr on 2017/6/5.
 */
@Controller
@RequestMapping(value = "/wechat/cust/info")
public class WeChatCustInfoController {

    @Autowired
    private IWeChatCustInfoService custInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        TCustBaseInfo currentWeChatCust = WeChatSessionUtil.getCurrentWeChatCust();
        if (null == currentWeChatCust) {
        	throw new WeChatSessionException("session已失效");
		}
        mv.addObject("custNo",currentWeChatCust.getCustNo());
        mv.setViewName("/wechat/custInfo/custInfo.index");
        return mv;
    }

    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String form(){
        return "/wechat/custInfo/custInfo.form";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Page<TCustBaseInfo> info(Page<TCustBaseInfo> page,String custNo){
        TCustBaseInfo tCustBaseInfo = custInfoService.get(custNo);
        if (tCustBaseInfo == null){
            page.setSuccess(false);
            return page;
        }
        page.setT(tCustBaseInfo);
        page.setSuccess(true);
        return page;
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Page<TCustBaseInfo> save(Page<TCustBaseInfo> page,TCustBaseInfo info){
//        custInfoService.save(info);
//        if (baseInfo == null){
//            page.setSuccess(false);
//            return page;
//        }
//        page.setT(baseInfo);
//        page.setSuccess(true);
    	if (null == info) {
    		page.setSuccess(false);
          return page;
		}
    	TCustBaseInfo currentWeChatCust = WeChatSessionUtil.getCurrentWeChatCust();
    	if (null == currentWeChatCust) {
        	throw new WeChatSessionException("session已失效");
		}
        return  custInfoService.save(info);
    }
    
    @ResponseBody
    @RequestMapping(value = "/get/dedicatedlink",method = RequestMethod.POST)
    public Page<Object> getDedicatedLink(Page<Object> page){
		return custInfoService.getDedicatedLink(page);
    }
    
	@RequestMapping(value="/show/qrcode/{custNo}", method=RequestMethod.GET)
	public void showQrcode(@PathVariable String custNo, HttpServletRequest request ,HttpServletResponse response) {
    	FileBody fileBody = custInfoService.showDedicatedQrcode(custNo);
    	byte[] data = new byte[1024];
        InputStream inputStream = fileBody.getInputStream();
        String filename = fileBody.getOriginFileName() +"."+ fileBody.getExtName();
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
        if ( StringUtils.isNotEmpty( extension ) && IASysAttService.imageExtensions.contains( extension.toUpperCase() ) ) {
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
    
	@RequestMapping(value="/download/qrcode/{custNo}", method=RequestMethod.GET)
	public void downloadQrcode(@PathVariable String custNo, HttpServletRequest request ,HttpServletResponse response) {
		FileBody fileBody = custInfoService.showDedicatedQrcode(custNo);
		InputStream in = fileBody.getInputStream();
		OutputStream out = null;
		try {
			//设置文件名
			String fileName=new String((fileBody.getOriginFileName() +"."+ fileBody.getExtName()).getBytes("UTF-8"),"iso-8859-1");
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+fileName);
			//response.setContentType("text/x-plain");// 定义输出类型 
			response.setContentType("multipart/form-data");  
			
			out = response.getOutputStream();
			byte[] outByte = new byte[in.available()];
			int len;  
			while ((len = in.read(outByte)) > -1 ) {  
				out.write(outByte, 0, len);  
			} 
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//暂时停止和满得利的信息同步
	/*@ResponseBody
    @RequestMapping(value = "/mdlSave",method = RequestMethod.POST)
    public Page<?> MDLsave(@RequestBody Map<String, Object> map){
        return custInfoService.mdlSave(map);
    }*/
	
	
}

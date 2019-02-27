package com.by.manage.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.core.controller.BaseController;
import com.by.core.exception.BusinessException;
import com.by.core.util.BeanUtil;
import com.by.core.util.DateUtils;
import com.by.core.util.ExcelHelper;
import com.by.core.util.Page;
import com.by.manage.wechat.model.BCoWechatBranch;
import com.by.manage.wechat.service.IBCoWechatBranchService;
import com.file.model.FileBody;

/**
 * 微信商户信息管理
 * @author duxin
 *
 */
@Controller
@RequestMapping(value="/branch/base/info")
public class TBranchBaseInfoController extends BaseController{
	
	@Autowired
	private IBCoWechatBranchService bCoWechatBranchService;
	
	/**
	 * 进入index
	 * @param page
	 * @return
	 */
	@RequiresPermissions(value="branch:base:info:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/manage/wechat/branch/branchBaseInfo/branchBaseInfo.index";
	}
	
	/**
	 * 获取列表数据
	 * @param page 页面参数
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="branch:base:info:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> typeList(Page<?> page) {
		Map<String, Object> map = page.getParams();
		List<BCoWechatBranch> list = bCoWechatBranchService.getList(map);
		page.setList(list);
		return page;
	}
	
	/**
	 * 进入form
	 * @param page
	 * @return
	 */
	@RequiresPermissions(value="branch:base:info:form")
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/manage/wechat/branch/branchBaseInfo/branchBaseInfo.form";
	}
	
	/**
	 * 添加
	 * @param page 
	 * @param coWechatBranch
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="branch:base:info:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, BCoWechatBranch coWechatBranch) {
		bCoWechatBranchService.save(coWechatBranch);
		return page;
	}
	
	/**
	 * 注销
	 * @param page
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="branch:base:info:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		bCoWechatBranchService.delete(ids);
		return page;
	}
	
	/**
	 * 启用
	 * @param page
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="branch:base:info:enable")
	@RequestMapping(value="/enable", method = RequestMethod.POST)
	public Page<?> enable(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		bCoWechatBranchService.enable(ids);
		return page;
	}
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param coWechatBranch
	 */
    @RequiresPermissions(value="branch:base:info:export")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request,HttpServletResponse response, BCoWechatBranch coWechatBranch){
    	Map<String, Object> map = BeanUtil.transBean2Map(coWechatBranch);
		map.put("status", Constants.STAT_ENABLE);
		List<BCoWechatBranch> list = bCoWechatBranchService.getList(map);
		if (list == null || list.size() == 0) {
			throw new BusinessException("导出数据不能为空");
		}
		String[] columns={
            "branchName","referCode","parReferCode"
        };
        String[] columnNames={
            "商户姓名","推荐码","经办人"
            };
        String  excelName = "商户信息_"+ DateUtils.format(new Date(), "yyyyMMddHHmmss");
        try {
        	ExcelHelper.export("", list, excelName, "Sheet1", columns, columnNames, 0, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("导出商户信息失败:"+e.getMessage());
        }
	 }
    
    /**
	 * 二维码下载
	 * @param Page<?> page
	 * @param ids
	 * @param request
	 * @param response
	 */
    @RequiresPermissions(value="branch:base:info:dwloadqrcode")
    @RequestMapping(value="/dwloadqrcode/{id}", method=RequestMethod.GET)
	public void downloadQrcode(Page<?> page, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("id is "+id);
		FileBody fileBody = bCoWechatBranchService.showDedicatedQrcode(id);
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
}

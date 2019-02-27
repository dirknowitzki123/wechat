package com.by.manage.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.by.manage.wechat.dto.TCustBaseInfoDto;
import com.by.manage.wechat.service.ITCustBaseInfoService;
import com.file.model.FileBody;

/**
 * 微信用户信息管理
 * @author yiqr
 *
 */
@Controller
@RequestMapping(value="/cust/base/info")
public class TCustBaseInfoController  extends BaseController {
	
	@Autowired
	private ITCustBaseInfoService custBaseInfoService;
	
	/**
	 * 进入index
	 * @param page
	 * @return
	 */
	@RequiresPermissions(value="cust:base:info:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/manage/wechat/custBaseInfo/custBaseInfo.index";
	}
	
	/**
	 * 获取列表数据
	 * @param page 页面参数
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="cust:base:info:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> typeList(Page<?> page) {
		Map<String, Object> map = page.getParams();
		List<TCustBaseInfoDto> list = custBaseInfoService.getAllList(map);
		page.setList(list);
		return page;
	}
	
	/**
	 * 进入form
	 * @param page
	 * @return
	 */
	@RequiresPermissions(value="cust:base:info:form")
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/manage/wechat/custBaseInfo/custBaseInfo.form";
	}
	
	/**
	 * 添加
	 * @param page 
	 * @param custBaseInfo
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="cust:base:info:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, TCustBaseInfo custBaseInfo) {
		custBaseInfoService.save(custBaseInfo);
		return page;
	}
	
	/**
	 * 注销
	 * @param page
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="cust:base:info:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		custBaseInfoService.delete(ids);
		return page;
	}

	/**
	 * 启用
	 * @param page
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="cust:base:info:enable")
	@RequestMapping(value="/enable", method = RequestMethod.POST)
	public Page<?> enable(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		custBaseInfoService.enable(ids);
		return page;
	}
	
	/**
	 * Excel导出
	 * @param request
	 * @param response
	 * @param tCustBaseInfo
	 */
    @RequiresPermissions(value="cust:base:info:export")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request,HttpServletResponse response, TCustBaseInfo tCustBaseInfo){
    	Map<String, Object> map = BeanUtil.transBean2Map(tCustBaseInfo);
		map.put("status", Constants.STAT_DISABLE);
		List<TCustBaseInfoDto> list = custBaseInfoService.getAllList(map);
		if (list == null || list.size() == 0) {
			throw new BusinessException("导出数据不能为空");
		}
		String[] columns={
            "custName","certNo","phoneNo","referralCode","parentReferralCode","referralLevel","referralPath","bankNo","openingBank","imei"
        };
        String[] columnNames={
            "用户姓名","身份证号","手机号","推荐码","父级推荐码","级别","推荐码路径","银行卡号","开户行","注册来源"
            };
        String  excelName = "客户信息_"+ DateUtils.format(new Date(), "yyyyMMddHHmmss");
        try {
        	ExcelHelper.export("", list, excelName, "Sheet1", columns, columnNames, 0, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("导出请款单失败:"+e.getMessage());
        }
	 }
    
    /**
	 * 电子签名合同下载
	 * @param phoneNo
	 * @param request
	 * @param response
	 */
    @RequiresPermissions(value="cust:base:info:dwloadelecontract")
    @RequestMapping(value="/dwloadelecontract/{phoneNo}", method=RequestMethod.GET)
	public void downloadElecontract(Page<?> page, @PathVariable String phoneNo, HttpServletRequest request, HttpServletResponse response){
    	FileBody fileBody = custBaseInfoService.getEleContractInputStream(phoneNo);
    	InputStream in = fileBody.getInputStream();
    	OutputStream out = null;
    	
    	//设置文件名
    	try {
			String fileName = new String((fileBody.getOriginFileName()+"."+fileBody.getExtName()).getBytes("UTF-8"), "iso-8859-1");
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+fileName);
			response.setContentType("multipart/form-data");
			
			out = response.getOutputStream();
			byte[] buf = new byte[in.available()];
			int len = 0;
			while((len = in.read(buf)) != -1){
				out.write(buf, 0, len);
			}
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
    }
}

package com.by.manage.wechat.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.model.TClientAppointmentCashloan;
import com.by.core.exception.BusinessException;
import com.by.core.util.BeanUtil;
import com.by.core.util.DateUtils;
import com.by.core.util.ExcelHelper;
import com.by.core.util.Page;
import com.by.manage.wechat.dto.TClientAppointmentCashloanDto;
import com.by.manage.wechat.service.ITClientAppointmentCashloanService;
import com.by.system.comp.CodeUtil;

/***
 * 客户预约现金贷管理
 * @author yiqr
 *
 */
@Controller
@RequestMapping(value="/client/appointment/cashloan")
public class TClientAppointmentCashloanController {
	@Autowired
	private ITClientAppointmentCashloanService clientAppointmentCashloanService;
	
	/**
	 * 进入index
	 * @param page
	 * @return
	 */
	@RequiresPermissions(value="client:appointment:cashloan:index")
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(Page<?> page) {
		return "/manage/wechat/clientAppointmentCashloan/clientAppointmentCashloan.index";
	}
	
	/**
	 * 获取列表数据
	 * @param page 页面参数
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="client:appointment:cashloan:list")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Page<?> typeList(Page<?> page) {
		Map<String, Object> map = page.getParams();
		map.put("status", Constants.STAT_DISABLE);
		List<TClientAppointmentCashloan> list = clientAppointmentCashloanService.getList(map);
		page.setList(list);
		return page;
	}
	
	/**
	 * 进入form
	 * @param page
	 * @return
	 */
	@RequiresPermissions(value="client:appointment:cashloan:form")
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String form(Page<?> page) {
		return "/manage/wechat/clientAppointmentCashloan/clientAppointmentCashloan.form";
	}
	
	/**
	 * 添加
	 * @param page 
	 * @param clientAppointmentCashloan
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="client:appointment:cashloan:save")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public Page<?> save(Page<?> page, TClientAppointmentCashloan clientAppointmentCashloan) {
		clientAppointmentCashloanService.save(clientAppointmentCashloan);
		return page;
	}
	
	/**
	 * 删除
	 * @param page
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value="client:appointment:cashloan:delete")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Page<?> delete(Page<?> page, @RequestParam("ids[]") List<String> ids) {
		return page;
	}
	
    @RequiresPermissions(value="client:appointment:cashloan:export")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request,HttpServletResponse response, TClientAppointmentCashloanDto clientAppointmentCashloan){
    	Map<String, Object> map = BeanUtil.transBean2Map(clientAppointmentCashloan);
		map.put("status", Constants.STAT_DISABLE);
		List<TClientAppointmentCashloan> list = clientAppointmentCashloanService.getList(map);
		if (list == null || list.size() == 0) {
			throw new BusinessException("导出数据不能为空");
		}
		//是否办理过分期
		Map<String, String> isStagedCodes = CodeUtil.getCodesByTypes("Is_Staged");
		//是否信用卡用户
		Map<String, String> isNoCodes = CodeUtil.getCodesByTypes("Is_No");
		
		//码值替换
		for( TClientAppointmentCashloan tClientAppointmentCashloan : list){
			tClientAppointmentCashloan.setIsStaged(isStagedCodes.get(tClientAppointmentCashloan.getIsStaged()));
		}
		for( TClientAppointmentCashloan tClientAppointmentCashloan : list){
			tClientAppointmentCashloan.setIsCredites(isNoCodes.get(tClientAppointmentCashloan.getIsCredites()));
		}
		
		String[] columns={
            "custName","phoneNo","ivingCity","referralCode","isStaged","isCredites"
        };
        String[] columnNames={
            "姓名","联系电话","所在城市","推荐码","是否办理过分期贷款","是否信用卡用户"
            };
        String  excelName = "现金贷预约信息_"+ DateUtils.format(new Date(), "yyyyMMddHHmmss");
        try {
        	ExcelHelper.export("", list, excelName, "Sheet1", columns, columnNames, 0, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("导出请款单失败:"+e.getMessage());
        }
	 }
}

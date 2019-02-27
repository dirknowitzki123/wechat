package com.by.bussiness.wechat.controller;

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

import com.by.bussiness.wechat.model.TClientAppointmentCashloan;
import com.by.bussiness.wechat.service.IWeChatClientAppointmentCashloanService;
import com.by.core.util.Page;

/**
 * 微信用户现金资格预约申请
 * Created by yiqr on 2017/6/29.
 */
@Controller
@RequestMapping(value = "/wechat/client/appointment/cashloan")
public class WeChatClientAppointmentCashloanController {
	
	@Autowired
	private IWeChatClientAppointmentCashloanService weChatClientAppointmentCashloanService;
	
	@RequestMapping(value="/submit/success" , method=RequestMethod.GET)
	public String submitSuccess(){
		return "/wechat/appointmentcashloan/appointmentcashloan.ok";
	}
	
	@RequestMapping(value = "{code}" , method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable String code) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("referralCode", code);
        mv.setViewName("/wechat/appointmentcashloan/appointmentcashloan.index");
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="save" , method=RequestMethod.POST)
	public Page<TClientAppointmentCashloan> save(Page<TClientAppointmentCashloan> page , TClientAppointmentCashloan clientAppointmentCashloan){
		return weChatClientAppointmentCashloanService.save(page, clientAppointmentCashloan);
	}
	
	//暂时停止和满得利的信息同步
	/*@ResponseBody
	@RequestMapping(value="/mdlSave" , method=RequestMethod.POST)
	public Page<?> mdlSave(@RequestBody Map<String, Object> map){
		return weChatClientAppointmentCashloanService.mdlSave(map);
	}*/
}

package com.by.bussiness.wechat.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.bussiness.wechat.service.IWeChatRegisterService;
import com.by.core.util.Page;

/**
 * 微信用户注册
 * Created by yiqr on 2017/6/1.
 */
@Controller
@RequestMapping(value = "/wechat/register")
public class WeChatRegisterController {

    @Autowired
    private IWeChatRegisterService registerService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return  "/wechat/register/register.index";
    }

   /* @RequestMapping(value = "/agreement",method = RequestMethod.GET)
    public String agreement(HttpServletRequest request, HttpServletResponse response) {
        return  "/wechat/register/agreement.index";
    }*/

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Page<?> register(Page<TCustLoginInfo> page,HttpServletRequest request, HttpSession session,
            HttpServletResponse response){
        return registerService.register(page,session);
    }
    
    /**
   	 * 提供给满得利的用户注册接口
   	 * @param map
   	 */
    /*@ResponseBody
    @RequestMapping(value="/mdlRegister", method = RequestMethod.POST)
    public Page<?> mdlRegister(@RequestBody Map<String, Object> map){
    	return registerService.mdlRegister(map);
    }*/
    
    /**
   	 * 提供给满得利的客户启用或者注销功能接口
   	 * @param map
   	 */
    /*@ResponseBody
   	@RequestMapping(value="/mdlModifyStatus", method = RequestMethod.POST)
    public Page<?> mdlModifyInfo(@RequestBody Map<String, Object> map){
   		return registerService.mdlModifyInfo(map);
    }*/
}

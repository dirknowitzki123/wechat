package com.by.bussiness.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.bussiness.wechat.service.IWeChatLoginService;
import com.by.core.util.Page;

/**
 * 微信用户登录
 * Created by yiqr on 2017/6/1.
 */
@Controller
@RequestMapping(value = "/wechat/login")
public class WeChatLoginController {

    @Autowired
    private IWeChatLoginService loginService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "/wechat/login/login.index";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Page<?> login(Page<TCustLoginInfo> page){
        return loginService.login(page);
    }

}

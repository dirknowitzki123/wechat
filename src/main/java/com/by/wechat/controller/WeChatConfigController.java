package com.by.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.wechat.service.IWeChatConfigService;

/**
 * 微信配置信息进口
 * Created by yiqr on 2017/6/12.
 */
@Controller
@RequestMapping("/wechat/config")
public class WeChatConfigController {

    @Autowired
    private IWeChatConfigService weChatConfigService;

    @ResponseBody
    @RequestMapping(value = "/refresh")
    public String refresh () {
        //获取access_token
        return weChatConfigService.getToken();
    }
    
    @ResponseBody
    @RequestMapping(value = "/query")
    public String query(){
    	return weChatConfigService.query();
    }

}

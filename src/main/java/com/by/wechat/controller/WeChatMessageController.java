package com.by.wechat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.wechat.service.IWeChatMessageService;

/**
 * 微信消息接入接口（接口配置信息）
 * Created by yiqr on 2017/6/12.
 */
@Controller
@RequestMapping("/wechat")
public class WeChatMessageController {
	
	private Log log= LogFactory.getLog(WeChatMessageController.class);

    @Autowired
    private IWeChatMessageService weChatMessageService;

    /**
     * 接入消息（域名配置）
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String access(HttpServletRequest request) throws IOException {
        //随机字符串
        String echostr = null;
        if (weChatMessageService.access(request)) {
            // 请求来自微信
        	echostr = request.getParameter("echostr");
        	log.info("==============》微信接入成功！！");
        }else {
            echostr = "error";
        }
        return echostr;
    }

    /**
     * 接入消息
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST)
    public void process(HttpServletRequest request,HttpServletResponse response) throws Exception {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        weChatMessageService.process(request,response);
    }

}

package com.by.bussiness.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.core.exception.WeChatSessionException;
import com.by.wechat.util.WeChatSessionUtil;

/**
 * 微信用户首页
 * Created by yiqr on 2017/6/6.
 */
@Controller
@RequestMapping(value = "/wechat/home")
public class WeChatHomeController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        TCustBaseInfo currentWeChatCust = WeChatSessionUtil.getCurrentWeChatCust();
        if (null == currentWeChatCust) {
        	throw new WeChatSessionException("session已失效");
		}
        mv.addObject("custNo",currentWeChatCust.getCustNo());
        mv.setViewName("/wechat/home/home.index");
        return mv;
    }
}

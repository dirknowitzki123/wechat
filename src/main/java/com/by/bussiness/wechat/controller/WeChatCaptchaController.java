package com.by.bussiness.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.bussiness.wechat.model.PubMsgCaptcha;
import com.by.bussiness.wechat.service.IWeChatCaptchaService;
import com.by.core.util.Page;

/**
 * 微信用户登录注册验证码发送/验证
 * Created by yiqr on 2017/6/1.
 */
@Controller
@RequestMapping(value = "/wechat/captcha")
public class WeChatCaptchaController {

    @Autowired
    private IWeChatCaptchaService captchaService;

    /**
     * 发送验证码
     *
     * @param page {moblNo: 手机号码(不为空)}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Page<PubMsgCaptcha> send(Page<PubMsgCaptcha> page) {
        return captchaService.send(page);
    }

//    /**
//     * 验证验证码
//     * @param page {moblNo: 手机号码(不为空), checkTyp : 验证类型(不为空) , checkReslt: 验证码值（不为空）}
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/verify", method = RequestMethod.POST)
//    public Page<PubMsgCaptcha> verify(Page<PubMsgCaptcha> page) {
//        return page;
//    }
}
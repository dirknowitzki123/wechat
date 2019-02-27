package com.by.bussiness.wechat.service;

import java.util.Map;

import com.by.bussiness.wechat.model.PubMsgCaptcha;
import com.by.core.util.Page;

/**
 * 发送、验证验证码
 * Created by yiqr on 2017/6/2.
 */
public interface IWeChatCaptchaService {

    /**
     * 发送验证码
     * @param page
     * @return
     */
    public Page<PubMsgCaptcha> send(Page<PubMsgCaptcha> page);

    /**
     * 验证验证码
     * @param moblNo 手机号
     * @param checkTyp 短信类型 - 注册、修改密码
     * @param checkReslt 待验证的验证码
     * @return
     */
    public Map<String,Object> verify(String moblNo, String checkTyp, String checkReslt);

}

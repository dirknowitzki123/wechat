package com.by.wechat.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消息管理
 * Created by yiqr on 2017/6/13.
 */
public interface IWeChatMessageService {
    /**
     * 微信接入（域名配置）
     * @param request
     */
    public boolean access(HttpServletRequest request);

    /**
     * 微信消息接入
     * @param request
     */
    public void process(HttpServletRequest request,HttpServletResponse response);

    /**
     * 向用户发送消息
     * @param openId 用户openId
     * @param content 发送内容
     */
    public void sendTextMessage(String openId,String content);
}

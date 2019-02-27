package com.by.wechat.service;

/**
 * Created by yiqr on 2017/6/12.
 */
public interface IWeChatConfigService {
    /**
     * 获取access_token 更新本地access_token
     * 获取jsapi_ticket 更新本地jsapi_ticket
     * @return
     */
    public String getToken();

    /**
     * 每两小时获取一次token 定时任务
     */
    public void timingToken();

    /**
     * 获取本地access_token jsapi_ticket
     * @return
     */
    public String query();
    
}

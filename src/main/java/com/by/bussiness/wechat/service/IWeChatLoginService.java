package com.by.bussiness.wechat.service;

import java.util.List;

import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.core.util.Page;

/**
 * Created by yiqr on 2017/6/2.
 */
public interface IWeChatLoginService {

    /**
     * 登录
     * @param page
     * @return
     */
    public Page<?> login(Page<TCustLoginInfo> page);

    /**
     * 免登陆
     * @param page
     * @return
     */
    public Page<TCustLoginInfo> avoidlogin(Page<TCustLoginInfo> page);
    
    /**
     * 通过openId 获取客户注册信息
     * @param openId
     * @return
     */
    public List<TCustLoginInfo> getList(String openId);

}

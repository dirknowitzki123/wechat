package com.by.bussiness.wechat.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.core.util.Page;

/**
 * 注册
 * Created by yiqr on 2017/6/5.
 */
public interface IWeChatRegisterService {
    public Page<?> register(Page<TCustLoginInfo> page,HttpSession session);
    
    //提供给满得利的注册接口
    public Page<?> mdlRegister(Map<String, Object> map);
    
    //启用客户功能(和满得利信息同步)
    public Page<?> mdlModifyInfo(Map<String, Object> map);
}

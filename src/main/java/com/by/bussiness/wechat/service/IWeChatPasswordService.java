package com.by.bussiness.wechat.service;

import java.util.Map;

import com.by.core.util.Page;

/**
 * Created by yiqr on 2017/6/8.
 */
public interface IWeChatPasswordService {
    public Page<?> firstSave(Page<Object> page);
    public Page<?> secondSave(Page<Object> page);
    
    /**
     * @desc  提供给满得利的修改密码接口
     * @param jsonString
     */
    public Page<?> mdlSave(Map<String, Object> map);
}

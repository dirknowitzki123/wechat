package com.by.bussiness.wechat.service;

import java.util.Map;

import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.core.util.Page;
import com.file.model.FileBody;

/**
 * Created by yiqr on 2017/6/5.
 */
public interface IWeChatCustInfoService {
    /**
     * 通过客户编号获取客户信息
     * @param custNo
     * @return
     */
    public TCustBaseInfo get(String custNo);

    /**
     * 保存用户信息
     * @param info
     */
    public Page<TCustBaseInfo> save(TCustBaseInfo info);
    
    /**
     * 获取用户专属链接
     * @param page
     * @return
     */
    public Page<Object> getDedicatedLink(Page<Object> page);
    
    /**
     * 专属二维码展示
     * @param custNo
     * @return
     */
    public FileBody showDedicatedQrcode(String custNo);
    
    /**
     * 提供给满得利的保存用户信息方法
     * @param info
     */
    public Page<?> mdlSave(Map<String, Object> map);
}

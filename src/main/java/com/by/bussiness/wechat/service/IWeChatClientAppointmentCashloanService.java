package com.by.bussiness.wechat.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.by.bussiness.wechat.model.TClientAppointmentCashloan;
import com.by.core.util.Page;

public interface IWeChatClientAppointmentCashloanService {
	/**
     * 保存现金资格预约申请信息
     * @param info
     */
    public Page<TClientAppointmentCashloan> save(Page<TClientAppointmentCashloan> page,TClientAppointmentCashloan info);
    
    public Page<?> mdlSave(Map<String, Object> map);
}

package com.by.frame.intfc.service;
import com.by.core.service.IBaseService;
import com.by.frame.intfc.model.ASysClientKey;

public interface IASysClientKeyService extends IBaseService<ASysClientKey>{
	
	/**
	 * 通过客户端标志获取信息
	 * @param clientFlag
	 * @return
	 */
	public ASysClientKey getByClient(String clientFlag);
	
}


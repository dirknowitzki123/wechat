package com.by.system.intfc.service;

import java.util.List;
import java.util.Map;

import com.by.core.service.IBaseService;
import com.by.system.model.ASysUser;

public interface IIntUserService extends IBaseService<ASysUser>{
	void login(Map<String, Object> map);
	List<Map<String, Object>> getSaler(Map<String, Object> map);
}

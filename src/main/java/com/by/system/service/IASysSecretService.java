package com.by.system.service;

import java.util.List;
import java.util.Map;

import com.by.system.model.ASysSecret;

public interface IASysSecretService {
	
	List<ASysSecret> getList(Map<String, Object> params);

	void insert(ASysSecret asyssecret);

	void deleteByPrimaryKey(List<String> ids);

	List<ASysSecret> getListId(Map<String, Object> map);


}

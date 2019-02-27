package com.by.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.service.BaseService;
import com.by.core.util.StringUtils;
import com.by.system.mapper.ASysSecretMapper;
import com.by.system.model.ASysSecret;
import com.by.system.service.IASysSecretService;

@Service
public class ASysSecretService extends BaseService implements IASysSecretService {
	
	@Autowired
	private ASysSecretMapper asyssecretMapper;
		
	@Override
	public List<ASysSecret> getList(Map<String, Object> params) {
		System.out.println("=============="+params.get("pubKey"));
		return asyssecretMapper.getList(params);
	}

	@Override
	public void insert(ASysSecret asyssecret) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", asyssecret.getId());
		map.put("clientFlag", asyssecret.getClientFlag());
		map.put("pubKey", asyssecret.getPubKey());
		map.put("priKey", asyssecret.getPriKey());
		map.put("status", asyssecret.getStatus());
		List<ASysSecret>  list = getListId(map);
		if (list.size()>0 && asyssecret.getId() != null) {
			
			asyssecretMapper.updateByPrimaryKey(map);
		}
		else{
			asyssecret.setId(StringUtils.getUUID());
			asyssecretMapper.insert(asyssecret);
		}
	}

	@Override
	public void deleteByPrimaryKey(List<String> ids) {
		
		for (String str : ids) {
			this.delete(str);
		}
	}
	

	public void delete(String ids) {
		asyssecretMapper.delete(ids);
	}

	@Override
	public List<ASysSecret> getListId(Map<String, Object> map) {
		
		return asyssecretMapper.getListId(map);
	}


}

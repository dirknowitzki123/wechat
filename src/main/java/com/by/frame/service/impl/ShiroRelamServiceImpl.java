package com.by.frame.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.mapper.ShiroRelamMapper;
import com.by.core.util.ApplicationUtil;
import com.by.frame.service.IShiroRealmService;

@Service
public class ShiroRelamServiceImpl implements IShiroRealmService {
	
	@Autowired private ShiroRelamMapper shiroRelamMapper;
	

	@Override
	public List<String> findRolesByUserId(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("modules", ApplicationUtil.getModules());
		return shiroRelamMapper.findRoles(params);
	}

	@Override
	public List<String> findPermissionsByUserId(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("modules", ApplicationUtil.getModules());
		return shiroRelamMapper.findPermissions(params );
	}

	@Deprecated
	@Override
	public List<String> findRoles() {
		return shiroRelamMapper.findRoles(new HashMap<String, Object>());
	}
	
	@Deprecated
	@Override
	public List<String> findPermissions() {
		return shiroRelamMapper.findRoles(new HashMap<String, Object>());
	}

}

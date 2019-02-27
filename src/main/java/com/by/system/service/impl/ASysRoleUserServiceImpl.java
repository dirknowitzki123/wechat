package com.by.system.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.service.BaseService;
import com.by.system.mapper.ASysRoleUserMapper;
import com.by.system.model.ASysRoleUser;
import com.by.system.service.IASysRoleUserService;

@Service
public class ASysRoleUserServiceImpl extends BaseService implements IASysRoleUserService{
	@Autowired
	private ASysRoleUserMapper aSysRoleUserMapper;

	@Override
	public ASysRoleUser get(Map<String,Object> map){
		//aSysRoleUserMapper.get(map);用mybatis对其中一个数据源进行查询
		return null;
	}
	@Override
	public List<ASysRoleUser> getList(Map<String,Object> map){
		return aSysRoleUserMapper.getList(map);
	}
	@Override
	public void save(ASysRoleUser obj) {
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
		
	}
	@Override
	public void update(ASysRoleUser Obj) {
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(List<String> ids) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<ASysRoleUser> findRolesByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return aSysRoleUserMapper.getList(map);
	}
}


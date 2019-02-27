package com.by.system.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.service.BaseService;
import com.by.system.mapper.ASysRoleMenuMapper;
import com.by.system.model.ASysRoleMenu;
import com.by.system.service.IASysRoleMenuService;

@Service
public class ASysRoleMenuServiceImpl extends BaseService implements IASysRoleMenuService{
	@Autowired
	private ASysRoleMenuMapper aSysRoleMenuMapper;

	@Override
	public ASysRoleMenu get(Map<String,Object> map){
		return null;
	}
	@Override
	public List<ASysRoleMenu> getList(Map<String,Object> map){
		return aSysRoleMenuMapper.getList(map);
	}
	@Override
	public void save(ASysRoleMenu obj) {
		
	}
	@Override
	public void update(ASysRoleMenu Obj) {
		
	}
	public void delete(String id) {
		aSysRoleMenuMapper.deleteByPrimaryKey(id);
		
		
	}
	@Override
	public void delete(List<String> ids) {
		
	}
	@Override
	public void deleteByRoleId(String roleId) {
		aSysRoleMenuMapper.deleteByRoleId(roleId);
		
	}
}


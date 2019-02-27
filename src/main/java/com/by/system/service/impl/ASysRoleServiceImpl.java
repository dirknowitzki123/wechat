package com.by.system.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.SessionUtil;
import com.by.core.util.StringUtils;
import com.by.system.mapper.ASysRoleMapper;
import com.by.system.mapper.ASysRoleMenuMapper;
import com.by.system.mapper.ASysRoleUserMapper;
import com.by.system.model.ASysRole;
import com.by.system.model.ASysRoleMenu;
import com.by.system.service.IASysRoleService;

@Service
public class ASysRoleServiceImpl extends BaseService implements IASysRoleService{
	
	@Autowired
	private ASysRoleMapper aSysRoleMapper;
	
	@Autowired
	private ASysRoleMenuMapper aSysRoleMenuMapper;
	
	@Autowired
	private ASysRoleUserMapper aSysRoleUserMapper;
	
	@Override
	public ASysRole get(Map<String,Object> params){
		return aSysRoleMapper.get(params);
	}
	
	@Override
	public List<ASysRole> getList(Map<String,Object> params){
		return aSysRoleMapper.getList(params);
	}
	
	@Override
	public void save(ASysRole aSysRole) {
		if (StringUtils.isEmpty(aSysRole.getId())) {
			aSysRole.setId(aSysRole.getRoleCode());
			aSysRole.setInstUserNo(SessionUtil.getCurrentASysUser().getLoginName());
			aSysRole.setInstDate(new Date());
			super.daoMysql.save(aSysRole);
			return;
		}
		else{
			ASysRole old = aSysRoleMapper.getByPrimaryKey(aSysRole.getId());
			aSysRole.setRoleCode(old.getRoleCode());
			old.setUpdtDate(new Date());
			super.daoMysql.update(aSysRole);
		}
	}
	
	/**
	 * 角色删除  需要删除角色信息，角色与资源的关系，角色与用户的关系
	 */
	@Override
	public void delete(List<String> ids) {
		for(String id : ids){
			this.delete(id);
		}
	}

	@Override
	public void update(ASysRole aSysRole) {
		ASysRole old = aSysRoleMapper.getByPrimaryKey(aSysRole.getId());
		aSysRole.setRoleCode(old.getRoleCode());
		aSysRole.setUpdtDate(new Date());
		super.daoMysql.update(aSysRole);
	}

	/**
	 * 角色删除  需要删除角色信息，角色与资源的关系，角色与用户的关系
	 */
	public void delete(String id) {
		aSysRoleUserMapper.deleteByRoleId(id);
		aSysRoleMenuMapper.deleteByRoleId(id);
		aSysRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void callProcedure() {
		
	}

	@Override
	public void save(ASysRole aSysRole, List<String> menuIds) {
		try {
			if(null == aSysRole){
				throw new BusinessException("传递的参数有误");
			}
			this.save(aSysRole);
			aSysRoleMenuMapper.deleteByRoleId(aSysRole.getId());
			if(!CollectionUtils.isEmpty(menuIds)){
				for(String menuId : menuIds){
					ASysRoleMenu roleMenu = new ASysRoleMenu();
					roleMenu.setId(super.daoMysql.getUUID());
					roleMenu.setRoleId(aSysRole.getId());
					roleMenu.setMenuId(menuId);
					aSysRoleMenuMapper.insert(roleMenu);
				}
			}
			
		}catch(BusinessException e){
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("新增角色失败");
			
		}
		
	}

	@Override
	public ASysRole getBygetByPrimaryKey(String key) {
		return aSysRoleMapper.getByPrimaryKey(key);
	}
}


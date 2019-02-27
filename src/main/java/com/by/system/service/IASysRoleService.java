package com.by.system.service;
import java.util.List;

import com.by.core.service.IBaseService;
import com.by.system.model.ASysRole;

public interface IASysRoleService extends IBaseService<ASysRole>{
	
	ASysRole getBygetByPrimaryKey(String key);
	
	/**
	 * 存储过程调用测试方法
	 */
	void callProcedure();
	
	/**
	 * 保存角色信息 建立角色与菜单之间的关系
	 * @param aSysRole 角色信息
	 * @param menuIds 菜单ID集合
	 */
	void save(ASysRole aSysRole,List<String> menuIds);
	
	/**
	 * 删除角色信息，需要删除角色与用户自己的关系，删除角色与菜单自己的关系
	 * @param id
	 */
	void delete(String id);
}


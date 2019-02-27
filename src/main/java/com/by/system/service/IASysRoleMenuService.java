package com.by.system.service;
import com.by.core.service.IBaseService;
import com.by.system.model.ASysRoleMenu;

public interface IASysRoleMenuService extends IBaseService<ASysRoleMenu>{
	/**
	 * 删除和角色ROLEID 有关联关系的菜单关系数据
	 * @param roleId
	 */
	void deleteByRoleId(String roleId);
}


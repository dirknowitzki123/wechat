package com.by.system.mapper;
import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysRoleMenu;

@MyBatisRepositoryMysql
public interface ASysRoleMenuMapper extends BaseMapper<ASysRoleMenu>{
	
	/**
	 * 删除和角色ROLEID 有关联关系的菜单关系数据
	 * @param roleId
	 */
	void deleteByRoleId(String roleId);
	
	/**
	 * 删除角色和菜单之间的关联关系
	 * @param menuId
	 */
	void deleteByMenuId(String menuId);
}


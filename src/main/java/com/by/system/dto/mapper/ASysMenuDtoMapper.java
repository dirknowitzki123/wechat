package com.by.system.dto.mapper;

import java.util.List;
import java.util.Map;

import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysMenu;

@MyBatisRepositoryMysql
public interface ASysMenuDtoMapper {
	/**根据配置文件查找菜单*/
	List<ASysMenu> getMenusByModules(Map<String,Object> map);
}

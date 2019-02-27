package com.by.frame.mapper;

import java.util.List;
import java.util.Map;

import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysMenu;

@MyBatisRepositoryMysql
public interface ProtalMapper {
	List<ASysMenu> getMenus( Map<String, Object> map );
}

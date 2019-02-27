package com.by.core.mapper;

import java.util.List;
import java.util.Map;

import com.by.core.mybatis.MyBatisRepositoryMysql;

@MyBatisRepositoryMysql
public interface ShiroRelamMapper {
	
	List<String> findRoles( Map<String, Object> params );
	
	List<String> findPermissions( Map<String, Object> params );
}

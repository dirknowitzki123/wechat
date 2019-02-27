package com.by.system.mapper;
import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysRoleUser;

@MyBatisRepositoryMysql
public interface ASysRoleUserMapper extends BaseMapper<ASysRoleUser>{
	
	void deleteByUserId(String userId);
	
	void deleteByRoleId(String roleId);
}


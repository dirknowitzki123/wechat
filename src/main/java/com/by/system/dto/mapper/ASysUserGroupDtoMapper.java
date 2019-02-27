package com.by.system.dto.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysUserGroup;

@MyBatisRepositoryMysql
public interface ASysUserGroupDtoMapper extends BaseMapper<ASysUserGroup>{
	public List<ASysUserGroup> getList(Map<String,Object> map);
}


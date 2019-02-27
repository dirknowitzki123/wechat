package com.by.system.dto.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysUserCrowd;

@MyBatisRepositoryMysql
public interface ASysUserCrowdDtoMapper extends BaseMapper<ASysUserCrowd>{
	public List<ASysUserCrowd> getList(Map<String,Object> map);
}


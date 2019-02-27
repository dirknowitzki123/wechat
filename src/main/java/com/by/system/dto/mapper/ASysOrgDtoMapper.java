package com.by.system.dto.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysOrg;

@MyBatisRepositoryMysql
public interface ASysOrgDtoMapper {

	List<ASysOrg> getOrgList(Map<String, Object> param);


}


package com.by.system.mapper;

import java.util.List;
import java.util.Map;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysSecret;

@MyBatisRepositoryMysql
public interface ASysSecretMapper extends BaseMapper<ASysSecret> {

	void delete(String ids);

	List<ASysSecret> getListId(Map<String, Object> map);

}

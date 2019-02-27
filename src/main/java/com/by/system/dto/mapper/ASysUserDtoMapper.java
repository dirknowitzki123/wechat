package com.by.system.dto.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysUser;

@MyBatisRepositoryMysql
public interface ASysUserDtoMapper {
	/**
	 * 获取机构下的用户
	 */
	List<ASysUser> getSubUserLst(Map<String, Object> param);
	/**
	 * 获取销售信息
	 */
	List<Map<String, Object>> getSaler(Map<String, Object> map);
}


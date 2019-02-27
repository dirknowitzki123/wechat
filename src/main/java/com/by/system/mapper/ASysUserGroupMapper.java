package com.by.system.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysUserGroup;

@MyBatisRepositoryMysql
public interface ASysUserGroupMapper extends BaseMapper<ASysUserGroup>{
	
	/**
	 * 根据产品ID 查询该产品所属的销售组
	 * @param prodNo 产品编号
	 * @return
	 */
	List<ASysUserGroup> selectByProduct(Map<String,Object> map);
	
	/**
	 * 根据产品ID查询 还没有与该产品绑定关系的销售组
	 * @param map
	 * @return
	 */
	List<ASysUserGroup> selectNotInProduct(Map<String,Object> map);
}


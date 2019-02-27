package com.by.system.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysUserCrowd;

@MyBatisRepositoryMysql
public interface ASysUserCrowdMapper extends BaseMapper<ASysUserCrowd>{
	
	/**
	 * 根据产品CODE 查询该产品所属的销售群
	 * @param prodNo 产品编号
	 * @return
	 */
	List<ASysUserCrowd> selectByProduct(Map<String,Object> map);
	
	/**
	 * 根据产品CODE查询 还没有与该产品绑定关系的销售群
	 * @param map
	 * @return
	 */
	List<ASysUserCrowd> selectNotInProduct(Map<String,Object> map);
}


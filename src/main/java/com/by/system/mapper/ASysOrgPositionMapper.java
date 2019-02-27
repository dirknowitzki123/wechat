package com.by.system.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysOrgPosition;

@MyBatisRepositoryMysql
public interface ASysOrgPositionMapper extends BaseMapper<ASysOrgPosition>{
	
	/**
	 * 根据机构码查找指定机构下的岗位
	 * @param 
	 * */
	public List<ASysOrgPosition> getPositionInfoByOrgCode(@Param("orgCode")String orgCode);
}


package com.by.pub.dto.mapper;

import java.util.List;
import java.util.Map;

import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.pub.model.ASysAtt;

@MyBatisRepositoryMysql
public interface ASysAttDtoMapper{

	List<ASysAtt> getListPubASysAtts(Map<String, Object> map);
	/**
	 * 根据busiNo和attTyp删除数据
	 * 
	 * @param map    
	 * @return 	void
	 * @throws
	 */
	void deleteByBusiNoAttTyp(Map<String, Object> map);
	
	List<ASysAtt> getListByInstDateDesc(Map<String, Object> map);
}

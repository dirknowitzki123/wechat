package com.by.system.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysCodeGroup;
import com.by.system.model.ASysCodeInfo;
import com.by.core.mapper.BaseMapper;

@MyBatisRepositoryMysql
public interface ASysCodeGroupMapper extends BaseMapper<ASysCodeGroup>{
	public List<ASysCodeGroup> getGroupDistinct(Map<String,Object> map);
	
	public List<ASysCodeInfo> getGrpExistCodeLst(Map<String,Object> map);
	
	public List<ASysCodeInfo> getGrpNotExistCodeLst(Map<String,Object> map);
	
	public void updateByTypeCodeWithGroupCode(Map<String,Object> map);

	/**
	 * 删除码组的基本信息那条数据
	 * @param param
	 */
	public void deleteGrpBasicInfo(Map<String, Object> param);
	/**
	 * 获取码表组的基本信息
	 * @param param
	 * @return
	 */
	public List<ASysCodeGroup> getGrpBasicInfo(Map<String,Object> param);
	
}


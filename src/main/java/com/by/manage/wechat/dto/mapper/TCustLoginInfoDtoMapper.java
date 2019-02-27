package com.by.manage.wechat.dto.mapper;

import java.util.Map;

import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.core.mybatis.MyBatisRepositoryMysql;

@MyBatisRepositoryMysql
public interface TCustLoginInfoDtoMapper {
	public void deleteByCustNo(String custNo);
	
	public void updateByPrimaryKey(Map<String, Object> params);
	
	public TCustLoginInfo getByPrimaryKey(String id);
	
	//通过custNo改变客户状态
	public void updateInfoByCustNo(Map<String, Object> params);
}

package com.by.manage.wechat.dto.mapper;
import java.util.List;
import java.util.Map;

import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.manage.wechat.dto.TCustBaseInfoDto;

@MyBatisRepositoryMysql
public interface TCustBaseInfoDtoMapper extends BaseMapper<TCustBaseInfo>{
	
	//模糊查询后代信息
	public List<TCustBaseInfo> getChildren(Map<String,String> map);
	
	//查询所有客户记录(包括"正常"和"销户"的记录)
	public List<TCustBaseInfoDto> getAllList(Map<String,Object> map);
}


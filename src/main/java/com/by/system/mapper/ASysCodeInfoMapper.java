package com.by.system.mapper;
import java.util.List;
import java.util.Map;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysCodeInfo;
import com.by.core.mapper.BaseMapper;

@MyBatisRepositoryMysql
public interface ASysCodeInfoMapper extends BaseMapper<ASysCodeInfo>{
	public List<String> getGroupCodes(Map<String,Object> map);
	public List<ASysCodeInfo> getCodesByGroupCode(String groupCode);
	public List<Map<String,String>> getCodesByTypes(Map<String,Object> map);
}


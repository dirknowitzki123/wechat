package com.by.system.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysGroupUser;
import com.by.system.model.ASysUser;

@MyBatisRepositoryMysql
public interface ASysGroupUserMapper extends BaseMapper<ASysGroupUser>{
	public List<ASysUser> getUserList(Map<String,Object> map);
	public List<ASysUser> getNotBindUsers(Map<String,Object> map);
	public void delGroupUser(@Param("groupNo")String groupNo,@Param("loginNames")List<String> loginNames);
	
}


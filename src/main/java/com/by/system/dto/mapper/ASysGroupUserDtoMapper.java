package com.by.system.dto.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysGroupUser;
import com.by.system.model.ASysUser;

@MyBatisRepositoryMysql
public interface ASysGroupUserDtoMapper extends BaseMapper<ASysGroupUser>{
	public List<ASysUser> getUserList(Map<String,Object> map);
	public List<ASysUser> getNotBindUsers(Map<String,Object> map);
	public void delGroupUser(@Param("groupNo")String groupNo,@Param("loginNames")List<String> loginNames);
	/**
	 * 获取用户组与用户关联表所有数据
	 */
	@Override
	List<ASysGroupUser> getList(Map<String, Object> params);
	
	
}


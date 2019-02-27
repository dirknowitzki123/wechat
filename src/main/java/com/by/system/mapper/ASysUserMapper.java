package com.by.system.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysOrg;
import com.by.system.model.ASysRole;
import com.by.system.model.ASysUser;

@MyBatisRepositoryMysql
public interface ASysUserMapper extends BaseMapper<ASysUser>{
	List<ASysRole> getRoles(Map<String,Object> map);
	List<ASysOrg> getOrgs(Map<String,Object> map);
	List<ASysUser> getUserList(Map<String,Object> map);
}


package com.by.pub.dto.mapper;

import java.util.List;
import java.util.Map;

import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.pub.model.UserOrgBO;

@MyBatisRepositoryMysql
public interface UserOrgBoMapper{

	List<UserOrgBO> getListUserOrg(Map<String, Object> map);
	List<UserOrgBO> getListUserOrgNoUser(Map<String,Object> map);
}

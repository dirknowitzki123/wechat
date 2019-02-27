package com.by.pub.service;

import java.util.List;
import java.util.Map;

import com.by.core.service.IBaseService;
import com.by.pub.model.UserOrgBO;

public interface ICommService extends IBaseService<UserOrgBO>{
	List<UserOrgBO> getListUserOrg(Map<String,Object> map);
	List<UserOrgBO> getListUserOrgNoUser(Map<String,Object> map);
}

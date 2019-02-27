package com.by.system.service;
import java.util.List;

import com.by.core.service.IBaseService;
import com.by.system.model.ASysRoleUser;

public interface IASysRoleUserService extends IBaseService<ASysRoleUser>{
	 List<ASysRoleUser> findRolesByUserId(String userId);
}


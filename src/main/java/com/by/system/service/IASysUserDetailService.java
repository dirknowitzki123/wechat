package com.by.system.service;
import com.by.core.service.IBaseService;
import com.by.system.model.ASysUser;
import com.by.system.model.ASysUserDetail;

public interface IASysUserDetailService extends IBaseService<ASysUserDetail>{
	//根据用户信息的数据要保存岗位信息
	public void saveforUserInfo(ASysUserDetail obj,ASysUser aSysUser);
}


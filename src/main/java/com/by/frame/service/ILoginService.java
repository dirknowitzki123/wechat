package com.by.frame.service;

import com.by.system.model.ASysUser;

public interface ILoginService {
	
	/**
	 * 用户登录
	 * @param aSysUser
	 */
	void login(ASysUser aSysUser, String code);
}

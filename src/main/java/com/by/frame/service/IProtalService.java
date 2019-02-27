package com.by.frame.service;

import java.util.List;

import com.by.system.model.ASysMenu;
import com.by.system.model.ASysUser;

public interface IProtalService {
	List<ASysMenu> getMenusByUserId(String userId);

	void modifyPassword(ASysUser aSysUser, String newPassword, String oldPassword);
}

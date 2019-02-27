package com.by.system.service;
import java.util.List;
import java.util.Map;

import com.by.core.service.IBaseService;
import com.by.system.model.ASysGroupUser;
import com.by.system.model.ASysUser;

public interface IASysGroupUserService extends IBaseService<ASysGroupUser>{
	/**
	 * 查询用户组已绑定的用户
	 * @param map
	 * @return
	 */
	public List<ASysUser> getUserList(Map<String,Object> map);
	/**
	 * 查询该用户组未绑定的用户
	 * @param map
	 * @return
	 */
	public List<ASysUser> getNotBindUsers(Map<String,Object> map);
	
	/**
	 * 用户组绑定用户
	 * @param loginNames 用户登录名集合
	 * @param groupNo    用户组编号
	 */
	public void saveBindUsers(List<String> loginNames,String groupNo); 
	
	/**
	 * 移除用户组绑定的用户
	 * @param loginNames 用户登录名集合
	 * @param groupNo    用户组编号
	 */
	public void delGroupUser(List<String> loginNames,String groupNo);
	
}


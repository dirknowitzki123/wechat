package com.by.system.service;
import java.util.List;
import java.util.Map;

import com.by.core.service.IBaseService;
import com.by.system.model.ASysUser;
import com.by.system.model.ASysUserDetail;

public interface IASysUserService extends IBaseService<ASysUser>{
	void delete(List<String> ids);
	
	/**
	 * 保存更新用户 并简历用户角色之间的关系
	 * @param aSysUser 保存更新的用户信息
	 * @param roleIds 需要与角色建立的关系数据
	 */
	void save(ASysUser aSysUser,List<String> roleIds);
	
	ASysUser getBygetByPrimaryKey(String userId);
	
	ASysUser getByLoginname(String loginame);
	
	public List<ASysUser> getUserList(Map<String,Object> params);
	
	/**
	 * 获取某个用户机构及其子机构的指定角色的用户
	 * @param loginName 用户登录名
	 * @param subUserRoleCode 想查询的子机构的用户角色编号
	 * @return 返回子机构的用户
	 */
	public List<ASysUser> getSubOrgUsers(String loginName,String subUserRoleCode);
	public void save(ASysUser aSysUser,ASysUserDetail aSysUserDetail);
	/**
	 * 重置密码
	 * @param userId
	 */
	public void resetPassword(String userId);
	
}


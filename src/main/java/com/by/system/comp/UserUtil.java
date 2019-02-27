package com.by.system.comp;

import java.util.List;

import com.by.core.exception.BusinessException;
import com.by.core.startup.ContextInit;
import com.by.core.util.StringUtils;
import com.by.system.model.ASysUser;
import com.by.system.service.IASysUserService;

/**
 * 用户工具类
 * @author HeJian
 *
 */
public class UserUtil {

	/**
	 * 获取某个用户的机构及其子机构下的指定角色的用户，角色为空则查询所有角色的用户
	 * @param loginName 用户登录名称
	 * @param subUserRoleCode 子用户的角色
	 * @return 
	 */
	public static List<ASysUser> getSubOrgUsers(String loginName,String subUserRoleCode){
		IASysUserService service = ContextInit.getContext().getBean(IASysUserService.class);
		if(StringUtils.isEmpty(loginName)) throw new BusinessException("用户登录名不可以为空","100002");
		return service.getSubOrgUsers(loginName, subUserRoleCode);
	}
	
	
	
}

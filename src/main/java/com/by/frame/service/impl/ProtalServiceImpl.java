package com.by.frame.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.constant.CommonConstant;
import com.by.core.exception.BusinessException;
import com.by.core.util.ApplicationUtil;
import com.by.core.util.BeanUtil;
import com.by.core.util.Md5Utils;
import com.by.frame.mapper.ProtalMapper;
import com.by.frame.service.IProtalService;
import com.by.system.mapper.ASysUserMapper;
import com.by.system.model.ASysMenu;
import com.by.system.model.ASysUser;

@Service
public class ProtalServiceImpl implements IProtalService{
	
	private static final List<String> MENU_TYPES 
		= Arrays.asList( new String[]{ CommonConstant.MENU_TYPE_DIR, CommonConstant.MENU_TYPE_IN, CommonConstant.MENU_TYPE_EMBED } );
	
	@Autowired private ProtalMapper protalMapper;
	
	@Autowired private ASysUserMapper aSysUserMapper;
	
	@Override
	public List<ASysMenu> getMenusByUserId(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("menuTypes", MENU_TYPES);
		params.put("isUserAble", CommonConstant.COMMON_YES);
		params.put("modules", ApplicationUtil.getModules());
		return protalMapper.getMenus( params  );
	}
	
	@Override
	public void modifyPassword( ASysUser aSysUser, String newPassword, String oldPassword ) {
		
		if( StringUtils.isEmpty(oldPassword) ) {
			throw new BusinessException("操作失败：旧密码不为空。");
		}
		
		String oldPasswordMD5 = Md5Utils.encode( oldPassword );
		
		if ( !aSysUser.getPassword().equals( oldPasswordMD5 ) ) {
			throw new BusinessException("操作失败：旧密码错误。");
		}
		
		if( StringUtils.isEmpty(newPassword) ) {
			throw new BusinessException("操作失败：新密码不为空。");
		}
		
		String newPasswordMD5 = Md5Utils.encode( newPassword );
		
		ASysUser user = new ASysUser();
		user.setId(aSysUser.getId());
		user.setPassword(newPasswordMD5);
		user.setUpdtDate(new Date());
		aSysUserMapper.updateByPrimaryKey(BeanUtil.transBean2Map(user));
	}
	
}

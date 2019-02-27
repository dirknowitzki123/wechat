package com.by.frame.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.exception.LoginException;
import com.by.core.util.BeanUtil;
import com.by.core.util.Md5Utils;
import com.by.core.util.SessionUtil;
import com.by.frame.service.ILoginService;
import com.by.system.mapper.ASysUserMapper;
import com.by.system.model.ASysUser;

@Service
public class LoginServiceImpl implements ILoginService {
	
	@Autowired private ASysUserMapper aSysUserMapper;
	
	@Override
	public void login( ASysUser aSysUser, String code ) {
		if ( StringUtils.isEmpty( aSysUser.getLoginName() ) ) {
			throw new LoginException("请输入登录名", "110001");
		}
		if ( StringUtils.isEmpty( aSysUser.getPassword() ) ) {
			throw new LoginException("请输入密码", "110002");
		}
//		if ( StringUtils.isEmpty(code) ) {
//			throw new LoginException("请输入验证码", "110003");
//		} 
//		Object captcha = SessionUtil.getSession().getAttribute("captcha");
//		if( captcha == null ) {
//			throw new LoginException("验证码已失效", "110003");
//		}
//		if ( !captcha.equals(code.toUpperCase()) ) {
//			throw new LoginException("", "110003");
//		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", aSysUser.getLoginName());
		ASysUser aSysUser2 = aSysUserMapper.get( params );
			
		if ( aSysUser2 == null ) {
			throw new LoginException("用户不存在", "110004");
		}
		if ( !aSysUser2.getPassword().equals( Md5Utils.encode(aSysUser.getPassword().trim()))) {
			throw new LoginException("", "110002");
		}
		if(!"13900001".equals(aSysUser2.getStatus())) {
			throw new LoginException("用户未启用", "110004");
		}
		aSysUser2.setLastLoginIp( SessionUtil.getClientIP() );
		aSysUser2.setLastLoginDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
		aSysUserMapper.updateByPrimaryKey(BeanUtil.transBean2Map(aSysUser));
			
		UsernamePasswordToken token = new UsernamePasswordToken(aSysUser2.getLoginName(), aSysUser2.getPassword(), false);
		SecurityUtils.getSubject().login(token);
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("userId", aSysUser2.getId());
		aSysUser2.setOrgs(aSysUserMapper.getOrgs(m));//机构
		aSysUser2.setRoles(aSysUserMapper.getRoles(m));//角色
		SessionUtil.setCurrentASysUser(aSysUser2);
	}
}

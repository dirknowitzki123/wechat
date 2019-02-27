package com.by.system.intfc.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.Md5Utils;
import com.by.core.util.SessionUtil;
import com.by.system.dto.mapper.ASysUserDtoMapper;
import com.by.system.intfc.service.IIntUserService;
import com.by.system.mapper.ASysUserMapper;
import com.by.system.model.ASysUser;

@Service
public class IntUserServiceImpl extends BaseService implements IIntUserService{
	@Autowired 
	private ASysUserMapper aSysUserMapper;
	@Autowired 
	private ASysUserDtoMapper aSysUserDtoMapper;
	//登录
	@Override
	public void login(Map<String, Object> map) {
		String loginName = (String)map.get("loginName");
		String password = (String)map.get("password");
		if (StringUtils.isEmpty(loginName)) {
			throw new BusinessException("登录名为空", "110001");
		}
		if ( StringUtils.isEmpty(password) ) {
			throw new BusinessException("密码为空", "110002");
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("loginName", loginName);
		ASysUser old = aSysUserMapper.get(m);
		if ( old == null ) {
			throw new BusinessException("不存在该用户", "110004");
		}
		if ( !old.getPassword().equals( Md5Utils.encode(password.trim()))) {
			throw new BusinessException("密码不正确", "110002");
		}
		if ( !"13900001".equals( old.getStatus() ) ) {
			throw new BusinessException("用户未启用", "110004");
		}
		old.setLastLoginIp(SessionUtil.getClientIP());
		old.setLastLoginDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		daoMysql.update(old);
		
		UsernamePasswordToken token = new UsernamePasswordToken(old.getLoginName(), old.getPassword(), false);
		SecurityUtils.getSubject().login(token);
		
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("userId", old.getId());
//		aSysUser2.setOrgs(aSysUserMapper.getOrgs(m));//机构
//		aSysUser2.setRoles(aSysUserMapper.getRoles(m));//角色
		SessionUtil.setCurrentASysUser(old);
	}
	
	//用户信息查询
	@Override
	public ASysUser get(Map<String, Object> map) {
		String loginName = (String)map.get("loginName");
		if (StringUtils.isEmpty(loginName)) {
			throw new BusinessException("登录名为空", "110001");
		}
		map.clear();
		map.put("loginName", loginName);
		return aSysUserMapper.get(map);
	}
	
	//销售信息查询
	@Override
	public List<Map<String, Object>> getSaler(Map<String, Object> map) {
		String loginName = (String)map.get("loginName");
		if (StringUtils.isEmpty(loginName)) {
			throw new BusinessException("登录名为空", "110001");
		}
		map.clear();
		map.put("loginName", loginName);
		map.put("areaManagerRoleId", "C_SA001");//区域经理角色id，不是code
		return aSysUserDtoMapper.getSaler(map);
	}
	
	@Override
	public List<ASysUser> getList(Map<String, Object> map) {
		return null;
	}

	@Override
	public void save(ASysUser obj) {
	}

	@Override
	public void update(ASysUser Obj) {
	}

	@Override
	public void delete(List<String> ids) {
	}

}

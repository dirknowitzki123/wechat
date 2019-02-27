package com.by.system.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.StringUtils;
import com.by.system.dto.mapper.ASysGroupUserDtoMapper;
import com.by.system.mapper.ASysGroupUserMapper;
import com.by.system.model.ASysGroupUser;
import com.by.system.model.ASysUser;
import com.by.system.service.IASysGroupUserService;

@Service
public class ASysGroupUserServiceImpl extends BaseService implements IASysGroupUserService{
	@Autowired
	private ASysGroupUserMapper aSysGroupUserMapper;
	@Autowired
	private ASysGroupUserDtoMapper aSysGroupUserDtoMapper;
	
	@Override
	public ASysGroupUser get(Map<String,Object> map){
		return aSysGroupUserMapper.get(map);
	}
	@Override
	public List<ASysGroupUser> getList(Map<String,Object> map){
		return aSysGroupUserMapper.getList(map);
	}
	@Override
	public void save(ASysGroupUser obj){
		super.daoMysql.save(obj);
	}
	@Override
	public void update(ASysGroupUser obj){
		super.daoMysql.update(obj);
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysGroupUser.class);
	}
	/**
	 * 根据用户组编号 查询用户列表
	 */
	@Override
	public List<ASysUser> getUserList(Map<String,Object> map){
		return aSysGroupUserDtoMapper.getUserList(map);
	}
	
	/**
	 * 查询该用户组未绑定的用户
	 * @param map
	 * @return
	 */
	public List<ASysUser> getNotBindUsers(Map<String,Object> map){
		return aSysGroupUserDtoMapper.getNotBindUsers(map);
	}
	
	/**
	 * 用户组绑定用户
	 * @param loginNames 用户登录名集合
	 * @param groupNo    用户组编号
	 */
	public void saveBindUsers(List<String> loginNames,String groupNo){
		if(StringUtils.isEmpty(groupNo)){
			throw new BusinessException("用户组获取失败，请返回主页面重新操作");
		}
		if(loginNames.isEmpty()){
			throw new BusinessException("请选择需要绑定的用户");
		}
		ASysGroupUser groupUser = null;
		for (int i = 0; i < loginNames.size(); i++) {
			groupUser = new ASysGroupUser();
			groupUser.setId(super.daoMysql.getUUID());
			groupUser.setLoginName(loginNames.get(i));
			groupUser.setGroupNo(groupNo);
			super.daoMysql.save(groupUser);
		}
	}
	
	/**
	 * 移除用户组绑定的用户
	 * @param loginNames 用户登录名集合
	 * @param groupNo    用户组编号
	 */
	public void delGroupUser(List<String> loginNames,String groupNo){
		if(StringUtils.isEmpty(groupNo)){
			throw new BusinessException("用户组获取失败，请返回主页面重新操作");
		}
		if(loginNames.isEmpty()){
			throw new BusinessException("请选择需要移除的用户");
		}
		aSysGroupUserDtoMapper.delGroupUser(groupNo,loginNames);
	}
}


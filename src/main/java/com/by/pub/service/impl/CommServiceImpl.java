package com.by.pub.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.service.BaseService;
import com.by.pub.dto.mapper.UserOrgBoMapper;
import com.by.pub.model.ASysAttExt;
import com.by.pub.model.UserOrgBO;
import com.by.pub.service.ICommService;

@Service
public class CommServiceImpl  extends BaseService implements ICommService {
	
	@Autowired
	private UserOrgBoMapper userOrgBoMapper;
	
	@Override
	public UserOrgBO get(Map<String,Object> map){
		return null;
	}
	@Override
	public List<UserOrgBO> getList(Map<String,Object> map){
		return null;
	}
	@Override
	public void save(UserOrgBO obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(UserOrgBO obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysAttExt.class);
	}
	
	
	/**
	 * 用户查询(审批流程用户选择  “机构菜单下用户” 展示查询)
	 */
	@Override
	public List<UserOrgBO> getListUserOrg(Map<String,Object> map)
	{
		return userOrgBoMapper.getListUserOrg(map);
	}
	
	public List<UserOrgBO> getListUserOrgNoUser(Map<String,Object> map)
	{
		return userOrgBoMapper.getListUserOrgNoUser(map);
	}
}

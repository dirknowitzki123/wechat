package com.by.system.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.system.mapper.ASysUserDetailMapper;
import com.by.system.model.ASysUser;
import com.by.system.model.ASysUserDetail;
import com.by.system.service.IASysUserDetailService;

@Service
public class ASysUserDetailServiceImpl extends BaseService implements IASysUserDetailService{
	@Autowired
	private ASysUserDetailMapper aSysUserDetailMapper;

	@Override
	public ASysUserDetail get(Map<String,Object> map){
		return aSysUserDetailMapper.get(map);//用mybatis对其中一个数据源进行查询
		 
	}
	@Override
	public List<ASysUserDetail> getList(Map<String,Object> map){
		//aSysUserDetailmapper.getList(map);用mybatis对其中一个数据源进行查询
		return aSysUserDetailMapper.getList(map);
	}
	@Override
	public void save(ASysUserDetail obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(ASysUserDetail obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		for(String id:ids)
		{
			aSysUserDetailMapper.deleteByPrimaryKey(id);
		}
	}
	@Override
	public void saveforUserInfo(ASysUserDetail aSysUserDetail,ASysUser aSysUser)
	{
		aSysUserDetail.setUserName(aSysUser.getUserName());
		aSysUserDetail.setIdNo(aSysUser.getCardNo());
		aSysUserDetail.setIdType("身份证");
		aSysUserDetail.setId(aSysUser.getLoginName());
		String id=aSysUser.getLoginName();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		List<ASysUserDetail> listAdetail=aSysUserDetailMapper.getList(map);
		if(listAdetail!=null && listAdetail.size()!=0)
		{			
			aSysUserDetailMapper.updateByPrimaryKey(BeanUtil.transBean2Map(aSysUserDetail));
		}
		else
		{			
			aSysUserDetailMapper.insert(aSysUserDetail);
		}	
	}
}


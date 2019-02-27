package com.by.system.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.by.core.service.BaseService;
import com.by.system.model.ASysUserOrg;
import com.by.system.mapper.ASysUserOrgMapper;
import com.by.system.service.IASysUserOrgService;

@Service
public class ASysUserOrgServiceImpl extends BaseService implements IASysUserOrgService{
	@Autowired
	private ASysUserOrgMapper aSysUserOrgMapper;

	@Override
	public ASysUserOrg get(Map<String,Object> map){
		return aSysUserOrgMapper.get(map);
	}
	@Override
	public List<ASysUserOrg> getList(Map<String,Object> map){
		return aSysUserOrgMapper.getList(map);
	}
	@Override
	public void save(ASysUserOrg obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(ASysUserOrg obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysUserOrg.class);
	}
}


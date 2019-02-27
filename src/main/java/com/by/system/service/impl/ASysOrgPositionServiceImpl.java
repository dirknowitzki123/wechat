package com.by.system.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.by.core.service.BaseService;
import com.by.system.model.ASysOrgPosition;
import com.by.system.mapper.ASysOrgPositionMapper;
import com.by.system.service.IASysOrgPositionService;

@Service
public class ASysOrgPositionServiceImpl extends BaseService implements IASysOrgPositionService{
	@Autowired
	private ASysOrgPositionMapper aSysOrgPositionMapper;

	@Override
	public ASysOrgPosition get(Map<String,Object> map){
		return aSysOrgPositionMapper.get(map);
	}
	@Override
	public List<ASysOrgPosition> getList(Map<String,Object> map){
		return aSysOrgPositionMapper.getList(map);
	}
	@Override
	public void save(ASysOrgPosition obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(ASysOrgPosition obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysOrgPosition.class);
	}
	@Override
	public List<ASysOrgPosition> getPositionInfoByOrgCode(String orgCode) {
		String orgcode="";
		System.out.println(orgcode);
		if(orgCode.trim().equals("") || orgCode.trim().length()<2)
		{
			return null;
		}
		//这里不管是不是组织机构的部门的下级，都支取两位，总公司的部门
		orgcode=orgCode.substring(0,2);
	    return aSysOrgPositionMapper.getPositionInfoByOrgCode(orgcode);
		
	}
}


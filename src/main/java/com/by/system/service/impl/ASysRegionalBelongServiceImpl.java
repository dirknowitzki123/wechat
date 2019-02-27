package com.by.system.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.by.core.service.BaseService;
import com.by.system.model.ASysRegionalBelong;
import com.by.system.mapper.ASysRegionalBelongMapper;
import com.by.system.service.IASysRegionalBelongService;

@Service
public class ASysRegionalBelongServiceImpl extends BaseService implements IASysRegionalBelongService{
	@Autowired
	private ASysRegionalBelongMapper aSysRegionalBelongMapper;

	@Override
	public ASysRegionalBelong get(Map<String,Object> map){
		return aSysRegionalBelongMapper.get(map);
	}
	@Override
	public List<ASysRegionalBelong> getList(Map<String,Object> map){
		return aSysRegionalBelongMapper.getList(map);
	}
	@Override
	public void save(ASysRegionalBelong obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(ASysRegionalBelong obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysRegionalBelong.class);
	}
}


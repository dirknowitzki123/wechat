package com.by.frame.intfc.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.constant.PubBusinessConstant;
import com.by.core.service.BaseService;
import com.by.frame.intfc.model.ASysClientKey;
import com.by.frame.intfc.mapper.ASysClientKeyMapper;
import com.by.frame.intfc.service.IASysClientKeyService;

@Service
public class ASysClientKeyServiceImpl extends BaseService implements IASysClientKeyService{
	@Autowired
	private ASysClientKeyMapper aSysClientKeyMapper;

	@Override
	public ASysClientKey get(Map<String,Object> map){
		return aSysClientKeyMapper.get(map);
	}
	@Override
	public List<ASysClientKey> getList(Map<String,Object> map){
		return aSysClientKeyMapper.getList(map);
	}
	@Override
	public void save(ASysClientKey obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(ASysClientKey obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysClientKey.class);
	}

	/**
	 * 通过客户端标志获取信息
	 * @param clientFlag
	 * @return
	 */
	public ASysClientKey getByClient(String clientFlag){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("clientFlag", clientFlag);
		param.put("status", PubBusinessConstant.YES);
		return aSysClientKeyMapper.get(param);
	}
	
	
	
}


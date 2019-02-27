package com.by.system.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.core.util.StringUtils;
import com.by.system.mapper.ASysBigDataRequestMapper;
import com.by.system.model.ASysBigDataRequest;
import com.by.system.service.IASysBigDataRequestService;

@Service
public class ASysBigDataRequestServiceImpl extends BaseService implements IASysBigDataRequestService{
	@Autowired
	private ASysBigDataRequestMapper aSysBigDataRequestMapper;

	@Override
	public ASysBigDataRequest get(Map<String,Object> map){
		return aSysBigDataRequestMapper.get(map);
	}
	@Override
	public List<ASysBigDataRequest> getList(Map<String,Object> map){
		return aSysBigDataRequestMapper.getList(map);
	}
	@Override
	public void save(ASysBigDataRequest obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
		if (null == obj) {
			throw new BusinessException("参数不合法","100002");
		}
		if (StringUtils.isEmpty(obj.getId())) {
			obj.setId(StringUtils.getUUID());
			obj.setInstDate(new Date());
			aSysBigDataRequestMapper.insert(obj);
			return;
		}
		obj.setUpdtDate(new Date());
		aSysBigDataRequestMapper.updateByPrimaryKey( BeanUtil.transBean2Map( obj ) );
	}
	@Override
	public void update(ASysBigDataRequest obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysBigDataRequest.class);
	}
}


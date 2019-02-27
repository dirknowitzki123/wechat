package com.by.frame.intfc.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.frame.intfc.model.ASysPortLog;
import com.by.frame.intfc.mapper.ASysPortLogMapper;
import com.by.frame.intfc.service.IASysPortLogService;

@Service
public class ASysPortLogServiceImpl extends BaseService implements IASysPortLogService{
	@Autowired
	private ASysPortLogMapper aSysPortLogMapper;

	@Override
	public ASysPortLog get(Map<String,Object> map){
		return aSysPortLogMapper.get(map);
	}
	@Override
	public List<ASysPortLog> getList(Map<String,Object> map){
		return aSysPortLogMapper.getList(map);
	}
	@Override
	public void save(ASysPortLog obj){
		super.daoMysql.save(obj);//用hibernate对mysql数据源进行操作
	}
	/**
	 * 根据主键更新
	 */
	@Override
	public void update(ASysPortLog obj){
		Map<String,Object> params = BeanUtil.transBean2Map(obj);
		aSysPortLogMapper.updateByPrimaryKey(params);
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysPortLog.class);
	}
}


package com.by.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.service.BaseService;
import com.by.system.mapper.ASysRescMapper;
import com.by.system.model.ASysResc;
import com.by.system.service.IASysRescService;

/**
 *  业务处理
 * @author autocreate
 * @create 2016-03-20
 */
@Service
public class ASysRescServiceImpl extends BaseService implements IASysRescService{
	@SuppressWarnings("unused")
	@Autowired
	private ASysRescMapper aSysRescMapper;
	
	@Override
	public void delete(List<String> ids) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ASysResc get(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void save(ASysResc obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(ASysResc Obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<ASysResc> getList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}
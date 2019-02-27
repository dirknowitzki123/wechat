package com.by.manage.wechat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.bussiness.wechat.mapper.TClientAppointmentCashloanMapper;
import com.by.bussiness.wechat.model.TClientAppointmentCashloan;
import com.by.core.service.BaseService;
import com.by.manage.wechat.dto.mapper.TClientAppointmentCashloanDtoMapper;
import com.by.manage.wechat.service.ITClientAppointmentCashloanService;

@Service
public class TClientAppointmentCashloanServiceImpl extends BaseService implements ITClientAppointmentCashloanService{
	
	@Autowired
	private TClientAppointmentCashloanMapper tClientAppointmentCashloanMapper;
	@Autowired
	private TClientAppointmentCashloanDtoMapper tClientAppointmentCashloanDtoMapper;

	@Override
	public TClientAppointmentCashloan get(Map<String, Object> map) {
		return tClientAppointmentCashloanMapper.get(map);
	}

	@Override
	public List<TClientAppointmentCashloan> getList(Map<String, Object> map) {
		return tClientAppointmentCashloanDtoMapper.getList(map);
	}

	@Override
	public void save(TClientAppointmentCashloan obj) {
	}

	@Override
	public void update(TClientAppointmentCashloan Obj) {
	}

	@Override
	public void delete(List<String> ids) {
		super.daoMysql.delete(ids, TClientAppointmentCashloan.class);
	}

}

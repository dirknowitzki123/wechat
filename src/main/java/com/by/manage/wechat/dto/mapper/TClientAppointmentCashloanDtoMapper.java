package com.by.manage.wechat.dto.mapper;

import java.util.List;
import java.util.Map;

import com.by.bussiness.wechat.model.TClientAppointmentCashloan;
import com.by.core.mybatis.MyBatisRepositoryMysql;

@MyBatisRepositoryMysql
public interface TClientAppointmentCashloanDtoMapper {
	public List<TClientAppointmentCashloan> getList(Map<String, Object> map);
}

package com.by.manage.wechat.dto.mapper;

import com.by.core.mybatis.MyBatisRepositoryMysql;

@MyBatisRepositoryMysql
public interface TCustExclusiveQrcodeDtoMapper {
	public void deleteByBusiNo(String busiNo);
}

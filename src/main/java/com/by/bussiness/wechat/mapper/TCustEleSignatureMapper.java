package com.by.bussiness.wechat.mapper;

import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.core.mapper.BaseMapper;
import com.by.bussiness.wechat.model.TCustEleSignature;

@MyBatisRepositoryMysql
public interface TCustEleSignatureMapper extends BaseMapper<TCustEleSignature>{
	
	/**
	 * 通过phoneNo删除记录
	 * @param phoneNo
	 */
	public void deleteByPhoneNo(String phoneNo);
}


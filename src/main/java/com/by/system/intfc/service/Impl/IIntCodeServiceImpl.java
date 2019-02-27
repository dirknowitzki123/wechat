package com.by.system.intfc.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.by.core.exception.BusinessException;
import com.by.frame.bo.CodeBo;
import com.by.system.intfc.service.IIntCodeService;
import com.by.system.service.IASysCodeInfoService;

/**
 * ***********************************
 * @describe       系统对外码表服务类
 * @version        V1.0
 * @author         张敏
 * @phone          13699488431
 * @date           2016年5月15日
 *
 * @modifyReason 
 * @modifyAuthor
 * @phone           
 * @modifyDate 
 * ***********************************
 */
@Service
public class IIntCodeServiceImpl implements IIntCodeService {

	@Autowired
	private IASysCodeInfoService codeInfoService;
	@Override
	public Map<String, Object>  getCode(Map<String, Object> params) {
		if(null == params){
			throw new BusinessException("参数传递有误", "110001");
		}
		return codeInfoService.getCode2(JSON.parseArray(params.get("list").toString(),CodeBo.class));
	}

}

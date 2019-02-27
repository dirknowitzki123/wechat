package com.by.system.service;
import java.util.List;

import com.by.core.service.IBaseService;
import com.by.system.model.ASysOrgPosition;

public interface IASysOrgPositionService extends IBaseService<ASysOrgPosition>{
	
	/**
	 * 根据机构码查找指定机构下的岗位
	 * @param 
	 * */
	public List<ASysOrgPosition> getPositionInfoByOrgCode(String orgCode);
}


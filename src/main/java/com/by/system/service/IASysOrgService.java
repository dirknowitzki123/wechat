package com.by.system.service;
import java.util.List;
import java.util.Map;

import com.by.core.service.IBaseService;
import com.by.system.dto.ASysOrgDTO;
import com.by.system.model.ASysOrg;

public interface IASysOrgService extends IBaseService<ASysOrg>{
	/**
	 * 查询组织机构 
	 * @param params
	 * @return 返回组织机构列表 包含父类名称
	 */
	List<ASysOrgDTO> getListBo(Map<String, Object> params);
	
	
	/**
	 * 获取用户拥有的机构
	 * @param loginName
	 * @return
	 */
	public List<ASysOrg> getOrgList(String loginName);
}


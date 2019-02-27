package com.by.system.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.dto.ASysOrgDTO;
import com.by.system.model.ASysOrg;

@MyBatisRepositoryMysql
public interface ASysOrgMapper extends BaseMapper<ASysOrg>{
	/**
	 * 查询组织机构 
	 * @param params
	 * @return 返回组织机构列表 包含父类名称
	 */
	List<ASysOrgDTO> getListBo(Map<String, Object> params);
}


package com.by.system.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.constant.PubBusinessConstant;
import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.ProcedureUtil;
import com.by.core.util.SessionUtil;
import com.by.core.util.StringUtils;
import com.by.system.dto.ASysOrgDTO;
import com.by.system.dto.mapper.ASysOrgDtoMapper;
import com.by.system.mapper.ASysOrgMapper;
import com.by.system.mapper.ASysUserOrgMapper;
import com.by.system.model.ASysOrg;
import com.by.system.service.IASysOrgService;

@Service
public class ASysOrgServiceImpl extends BaseService implements IASysOrgService{
	@Autowired
	private ASysOrgMapper aSysOrgMapper;
	@Autowired
	private ASysUserOrgMapper aSysUserOrgMapper;
	@Autowired
	private ASysOrgDtoMapper aSysOrgDtoMapper;
	
	@Override
	public ASysOrg get(Map<String,Object> map){
		return aSysOrgMapper.get(map);
	}
	@Override
	public List<ASysOrg> getList(Map<String,Object> map){
		return aSysOrgMapper.getList(map);
	}
	@Override
	public void save(ASysOrg aSysOrg) {
		if(StringUtils.isEmpty(aSysOrg.getId())){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("orgCode", aSysOrg.getOrgCode());
			ASysOrg old=aSysOrgMapper.get(map);
			if(old!=null){
				throw new BusinessException("该机构编号已存在！");
			}
			
			aSysOrg.setId(aSysOrg.getOrgCode());
			if(StringUtils.isEmpty(aSysOrg.getParentId())){
				aSysOrg.setOrgPath(aSysOrg.getOrgCode());
			}else{
				ASysOrg pOrg=(ASysOrg)daoMysql.get(ASysOrg.class, aSysOrg.getParentId());
				aSysOrg.setOrgPath(pOrg.getOrgPath()+","+aSysOrg.getOrgCode());
			}
			aSysOrg.setInstDate(new Date());
			aSysOrg.setInstUserNo(SessionUtil.getCurrentASysUser().getLoginName());
			daoMysql.save(aSysOrg);
		}else{
			ASysOrg old = (ASysOrg)daoMysql.get(ASysOrg.class, aSysOrg.getId());
			old.setOrgName(aSysOrg.getOrgName());
			old.setOrgOrder(aSysOrg.getOrgOrder());
			old.setOrgLevel(aSysOrg.getOrgLevel());
			old.setOrgType(aSysOrg.getOrgType());
			old.setOrgAddr(aSysOrg.getOrgAddr());
			old.setOrgPhone(aSysOrg.getOrgPhone());
			old.setStatus(aSysOrg.getStatus());
			old.setUpdtDate(new Date());
			old.setProvNo(aSysOrg.getProvNo());
			old.setProvName(aSysOrg.getProvName());
			old.setCityNo(aSysOrg.getCityNo());
			old.setCityName(aSysOrg.getCityName());
			old.setAreaNo(aSysOrg.getAreaNo());
			old.setAreaName(aSysOrg.getAreaName());
			daoMysql.update(old);
		}
	}
	
	@Override
	public void update(ASysOrg aSysOrg) {
		
	}
	
	@Override
	public void delete(List<String> ids) {
		String[] orgIds=ProcedureUtil.callCHILDREN("A_SYS_ORG", ids.get(0), "PARENT_ID","select", daoMysql);
		for(String orgId:orgIds){
			Map<String, Object> m = new HashMap<String,Object>();
			m.put("orgId", orgId);
			daoMysql.delete(aSysUserOrgMapper.getList(m));
			daoMysql.delete(ASysOrg.class, orgId);
		}
	}
	
	@Override
	public List<ASysOrgDTO> getListBo(Map<String, Object> params) {
		return aSysOrgMapper.getListBo(params);
	}
	
	/**
	 * 获取用户拥有的机构
	 * @param loginName
	 * @return
	 */
	public List<ASysOrg> getOrgList(String loginName){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("stat", PubBusinessConstant.YES);
		param.put("loginName", loginName);
		List<ASysOrg> lst = aSysOrgDtoMapper.getOrgList(param);
		return lst;
	}
	
	
	
	
}


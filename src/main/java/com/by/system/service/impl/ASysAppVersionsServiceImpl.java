package com.by.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.core.util.DateUtils;
import com.by.core.util.StringUtils;
import com.by.system.dao.mapper.ASysAppVersionsDtoMapper;
import com.by.system.mapper.ASysAppVersionsMapper;
import com.by.system.model.ASysAppVersions;
import com.by.system.service.IASysAppVersionsService;

@Service
public class ASysAppVersionsServiceImpl extends BaseService implements IASysAppVersionsService {
	@Autowired
	private ASysAppVersionsMapper aSysAppVersionsMapper;
	@Autowired
	private ASysAppVersionsDtoMapper aSysAppVersionsDtoMapper;

	@Override
	public ASysAppVersions get(Map<String, Object> map) {
		return aSysAppVersionsMapper.get(map);
	}

	@Override
	public List<ASysAppVersions> getList(Map<String, Object> map) {
		
		return aSysAppVersionsDtoMapper.getVersionsList(map);
	}
	
	/**
	 * 版本信息保存
	 */
	@Override
	public void save(ASysAppVersions obj) {
		HashMap<String, Object> params= new HashMap<String, Object>();
		params.put("versionNo", obj.getVersionNo());
		List<ASysAppVersions> l = aSysAppVersionsMapper.getList(params);
		
		if (StringUtils.empty(obj.getId())) {
			if(StringUtils.empty(obj.getVersionNo())){
				throw new BusinessException("版本编号不能为空","100002");
			}
			if(StringUtils.empty(obj.getOsType())){
				throw new BusinessException("版本类型不能为空","100002");
			}
			if(StringUtils.empty(obj.getApplyType())){
				throw new BusinessException("适用类型不能为空","100002");
			}
			if((!l.isEmpty())){
				throw new BusinessException("版本编号不能重复","100010");
			}
			obj.setId(StringUtils.getUUID());
			obj.setInstDate(new Date());
			super.daoMysql.save(obj);
			return;
		}
		if(l.size()>0&&!l.get(0).getId().equals(obj.getId())){
			throw new BusinessException("版本编号不能重复","100010");
		}
		obj.setUpdtDate(new Date());
		aSysAppVersionsMapper.updateByPrimaryKey(BeanUtil.transBean2Map( obj ));
	}

	@Override
	public void update(ASysAppVersions obj) {
		super.daoMysql.update(obj);
	}

	@Override
	public void delete(List<String> ids) {
		super.daoMysql.delete(ids, ASysAppVersions.class);
	}

	/**
	 * 获取最新的app下载信息
	 */
	@Override
	public ASysAppVersions queryNewVersion(ASysAppVersions aSysAppVersions) {
		if (null == aSysAppVersions) {
			throw new BusinessException("贷款参数不能为空", "100002");
		}
		// app类型为空 适用类型为空
		if (StringUtils.isEmpty(aSysAppVersions.getOsType()) || StringUtils.isEmpty(aSysAppVersions.getApplyType())) {
			throw new BusinessException("参数不合法", "100001");
		}
		ASysAppVersions newVersion = aSysAppVersionsDtoMapper.queryNewVersion(aSysAppVersions);
		if(null != newVersion){			
			newVersion.setOnlineDate(DateUtils.strToGmt(newVersion.getOnlineDate(), "yyyy-MM-dd"));
		}
		return newVersion;
	}
}

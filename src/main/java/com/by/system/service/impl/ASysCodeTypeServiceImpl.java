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
import com.by.core.util.SessionUtil;
import com.by.core.util.StringUtils;
import com.by.system.mapper.ASysCodeGroupMapper;
import com.by.system.mapper.ASysCodeInfoMapper;
import com.by.system.mapper.ASysCodeTypeMapper;
import com.by.system.model.ASysCodeGroup;
import com.by.system.model.ASysCodeInfo;
import com.by.system.model.ASysCodeType;
import com.by.system.service.IASysCodeTypeService;

@Service
public class ASysCodeTypeServiceImpl extends BaseService implements IASysCodeTypeService{
	@Autowired
	private ASysCodeTypeMapper aSysCodeTypeMapper;
	@Autowired
	private ASysCodeInfoMapper aSysCodeInfoMapper;
	@Autowired
	private ASysCodeGroupMapper aSysCodeGroupMapper;

	@Override
	public ASysCodeType get(Map<String,Object> map){
		return aSysCodeTypeMapper.get(map);
	}
	@Override
	public List<ASysCodeType> getList(Map<String,Object> map){
		return aSysCodeTypeMapper.getList(map);
	}
	@Override
	public void save(ASysCodeType obj){
		if (StringUtils.isEmpty( obj.getId())) {
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("typeCode", obj.getTypeCode());
			List<ASysCodeType> l=aSysCodeTypeMapper.getList(m);
			if(l!=null&&l.size()>0&&l.get(0)!=null){
				throw new BusinessException("码类编码不能重复");
			}
			obj.setId(StringUtils.getUUID());
			obj.setInstDate(new Date());
			obj.setInstUserNo(SessionUtil.getCurrentASysUser().getLoginName());
			super.daoMysql.save(obj);
			return;
		}
		obj.setUpdtDate(new Date());
		aSysCodeTypeMapper.updateByPrimaryKey( BeanUtil.transBean2Map( obj ) );
	}
	@Override
	public void update(ASysCodeType obj){
		obj.setUpdtDate(new Date());
		aSysCodeTypeMapper.updateByPrimaryKey( BeanUtil.transBean2Map( obj ) );
	}
	public void delete(String id){
		super.daoMysql.delete(ASysCodeType.class, id);;
	}
	@Override
	public void delete(List<String> ids) {
		Map<String,Object> map=new HashMap<String,Object>();
		for(String id:ids){
			ASysCodeType aSysCodeType=(ASysCodeType)super.daoMysql.get(ASysCodeType.class, id);
			map.put("typeCode", aSysCodeType.getTypeCode());
			List<ASysCodeInfo> codeInfos=aSysCodeInfoMapper.getList(map);
			for(ASysCodeInfo info:codeInfos){
				super.daoMysql.delete(info);
			}
			
			List<ASysCodeGroup> codeGroups=aSysCodeGroupMapper.getList(map);
			for(ASysCodeGroup group:codeGroups){
				super.daoMysql.delete(group);
			}
			
			super.daoMysql.delete(aSysCodeType);
		}
	}
}


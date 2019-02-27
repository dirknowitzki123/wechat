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
import com.by.system.dto.mapper.ASysUserCrowdDtoMapper;
import com.by.system.mapper.ASysUserCrowdMapper;
import com.by.system.model.ASysUserCrowd;
import com.by.system.service.IASysUserCrowdService;

@Service
public class ASysUserCrowdServiceImpl extends BaseService implements IASysUserCrowdService{
	@Autowired
	private ASysUserCrowdMapper aSysUserCrowdMapper;
	@Autowired
	private ASysUserCrowdDtoMapper aSysUserCrowdDtoMapper;

	@Override
	public ASysUserCrowd get(Map<String,Object> map){
		return aSysUserCrowdMapper.get(map);
	}
	@Override
	public List<ASysUserCrowd> getList(Map<String,Object> map){
		return aSysUserCrowdDtoMapper.getList(map);
	}
	@Override
	public void save(ASysUserCrowd obj){
		if (StringUtils.isEmpty( obj.getId())) {
			//根据用户群编号查询，如果存在数据，则提示用户群编号已存在
			String crowdNo = obj.getCrowdNo();
			if(StringUtils.isEmpty(crowdNo)){
				throw new BusinessException("用户群编号不能为空");
			}
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("crowdNo", crowdNo);
			List<ASysUserCrowd> list = aSysUserCrowdMapper.getList(params);
			if(null != list && list.size() > 0){
				throw new BusinessException("用户群编号["+crowdNo+"]已存在");
			}
			obj.setId(super.daoMysql.getUUID());
			obj.setInstDate(new Date());
			obj.setInstUserNo(SessionUtil.getCurrentASysUser().getLoginName());
			super.daoMysql.save(obj);
		}else{
			obj.setUpdtDate(new Date());
			aSysUserCrowdMapper.updateByPrimaryKey(BeanUtil.transBean2Map(obj));
		}
	}
	@Override
	public void update(ASysUserCrowd obj){
		super.daoMysql.update(obj);//用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, ASysUserCrowd.class);
	}
	@Override
	public List<ASysUserCrowd> selectByProduct(Map<String, Object> map) {
		return aSysUserCrowdMapper.selectByProduct(map);
	}
	@Override
	public List<ASysUserCrowd> selectNotInProduct(Map<String, Object> map) {
		return aSysUserCrowdMapper.selectNotInProduct(map);
	}
}


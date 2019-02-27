package com.by.system.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.by.core.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.core.util.SessionUtil;
import com.by.system.dto.mapper.ASysGroupUserDtoMapper;
import com.by.system.dto.mapper.ASysUserGroupDtoMapper;
import com.by.system.mapper.ASysUserGroupMapper;
import com.by.system.model.ASysUserGroup;
import com.by.system.service.IASysUserGroupService;

@Service
public class ASysUserGroupServiceImpl extends BaseService implements IASysUserGroupService{
	@Autowired
	private ASysUserGroupMapper aSysUserGroupMapper;
	@Autowired
	private ASysUserGroupDtoMapper aSysUserGroupDtoMapper;
	@Autowired
	private ASysGroupUserDtoMapper aSysGroupUserDtoMapper;

	@Override
	public ASysUserGroup get(Map<String,Object> map){
		return aSysUserGroupMapper.get(map);
	}
	@Override
	public List<ASysUserGroup> getList(Map<String,Object> map){
		return aSysUserGroupDtoMapper.getList(map);
	}
	/**
	 * 保存
	 */
	@Override
	public void save(ASysUserGroup obj){
		if (StringUtils.isEmpty( obj.getId())) {
			//根据用户组编号查询，如果存在数据，则提示用户组编号已存在
			String groupNo = obj.getGroupNo();
			if(StringUtils.isEmpty(groupNo)){
				throw new BusinessException("用户组编号不能为空");
			}
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("groupNo", groupNo);
			List<ASysUserGroup> list = aSysUserGroupMapper.getList(params);
			if(null != list && list.size() > 0){
				throw new BusinessException("用户组编号["+groupNo+"]已存在");
			}
			obj.setId(super.daoMysql.getUUID());
			obj.setInstDate(new Date());
			obj.setInstUserNo(SessionUtil.getCurrentASysUser().getLoginName());
			super.daoMysql.save(obj);
		}else{
			obj.setUpdtDate(new Date());
			aSysUserGroupMapper.updateByPrimaryKey(BeanUtil.transBean2Map(obj));
		}
	}
	@Override
	public void update(ASysUserGroup obj){
		obj.setUpdtDate(new Date());
		super.daoMysql.update(obj);
	}
	@Override
	public void delete(List<String> ids){
		if(!ids.isEmpty()){
			ASysUserGroup group = null;
			//先删除用户组与用户的关系表数据
			for (int i = 0; i < ids.size(); i++) {
				group = (ASysUserGroup) super.daoMysql.get(ASysUserGroup.class, ids.get(i));
				if(null == group || StringUtils.isEmpty(group.getGroupNo())){
					continue;
				}
				aSysGroupUserDtoMapper.delGroupUser(group.getGroupNo(), null);
			}
			//删除用户组
			super.daoMysql.delete(ids,ASysUserGroup.class);
		}
	}
	@Override
	public List<ASysUserGroup> selectByProduct(Map<String, Object> map) {
		return aSysUserGroupMapper.selectByProduct(map);
	}
	@Override
	public List<ASysUserGroup> selectNotInProduct(Map<String, Object> map) {
		return aSysUserGroupMapper.selectNotInProduct(map);
	}
}


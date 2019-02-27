package com.by.system.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.core.util.ProcedureUtil;
import com.by.core.util.SessionUtil;
import com.by.core.util.StringUtils;
import com.by.system.dto.mapper.ASysMenuDtoMapper;
import com.by.system.mapper.ASysMenuMapper;
import com.by.system.mapper.ASysRoleMenuMapper;
import com.by.system.model.ASysMenu;
import com.by.system.service.IASysMenuService;

@Service
public class ASysMenuServiceImpl extends BaseService implements IASysMenuService{
	@Autowired
	private ASysMenuMapper aSysMenuMapper;
	@Autowired
	private ASysRoleMenuMapper aSysRoleMenuMapper;
	@Autowired
	private ASysMenuDtoMapper aSysMenuDtoMapper;
	@Override
	public ASysMenu get(Map<String,Object> map){
		return aSysMenuMapper.get(map);
	}
	@Override
	public List<ASysMenu> getList(Map<String,Object> map){
		return aSysMenuMapper.getList(map);
	}
	@Override
	public void save(ASysMenu obj) {
		if (StringUtils.isEmpty( obj.getMenuCode()) || StringUtils.isEmpty(obj.getMenuName()) ) {
			throw new BusinessException("编码和名称不能为空","100002");
		}
		Date date = new Date();
		if (StringUtils.isEmpty( obj.getId())) {
			obj.setId(StringUtils.getUUID());
			obj.setInstUserNo(SessionUtil.getCurrentASysUser().getLoginName());
			obj.setInstDate(date);
			super.daoMysql.save(obj);
			return;
		} 
		
		ASysMenu old=(ASysMenu)daoMysql.get(ASysMenu.class, obj.getId());
		if(!old.getIsUserAble().equals(obj.getIsUserAble())){
			String[] ids=ProcedureUtil.callCHILDREN("A_SYS_MENU", old.getId(), "PARENT_MENU_ID","select", daoMysql);
			for(String id:ids){
				ASysMenu m=(ASysMenu)daoMysql.get(ASysMenu.class, id);
				m.setIsUserAble(obj.getIsUserAble());
				m.setUpdtDate(date);
				daoMysql.update(m);
			}
		}
		obj.setUpdtDate(date);
		aSysMenuMapper.updateByPrimaryKey( BeanUtil.transBean2Map( obj ) );
	}
	@Override
	public void update(ASysMenu Obj) {
		Obj.setUpdtDate(new Date());
		aSysMenuMapper.updateByPrimaryKey( BeanUtil.transBean2Map( Obj ) );
	}
	public void delete(String primaryKey) {
		aSysMenuMapper.deleteByPrimaryKey(primaryKey);
		aSysRoleMenuMapper.deleteByMenuId(primaryKey);
	}
	
	@Override
	public void delete(List<String> ids) {
		for(String id : ids){
			this.delete(id);
		}
	}
	@Override
	public List<ASysMenu> getMenusByModules(Map<String, Object> map) {
		return aSysMenuDtoMapper.getMenusByModules(map);
	}
	
}


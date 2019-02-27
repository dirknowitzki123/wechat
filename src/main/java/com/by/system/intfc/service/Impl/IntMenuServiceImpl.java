package com.by.system.intfc.service.Impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.constant.CommonConstant;
import com.by.core.exception.BusinessException;
import com.by.core.util.StringUtils;
import com.by.system.intfc.service.IIntMenuService;
import com.by.system.mapper.ASysMenuMapper;
import com.by.system.model.ASysMenu;

/**
 * @author wl
 *
 */
@Service
public class IntMenuServiceImpl implements IIntMenuService {

	private static final List<String> MENU_TYPES  = Arrays.asList( new String[]{ CommonConstant.MENU_TYPE_DIR, CommonConstant.MENU_TYPE_IN, CommonConstant.MENU_TYPE_EMBED } );
	@Autowired
	private ASysMenuMapper aSysMenuMapper;
	@Override
	public List<ASysMenu> getMenuBySysCode(Map<String, Object> params) {
		if(null == params){
			throw new BusinessException("参数传递有误", "110001");
		}
		String modules = String.valueOf(params.get("modules"));
		if(StringUtils.empty(modules)){
			throw new BusinessException("参数传递有误", "110001");
		}
		
		params.put("menuTypes", MENU_TYPES);
		params.put("isUserAble", CommonConstant.COMMON_YES);
		return aSysMenuMapper.getMenus(params);
		
	}

}

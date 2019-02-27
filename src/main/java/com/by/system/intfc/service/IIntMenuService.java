package com.by.system.intfc.service;

import java.util.List;
import java.util.Map;

import com.by.system.model.ASysMenu;

/**
 * @author wl
 *
 */
public interface IIntMenuService {
	
	/**
	 * 通过系统编码获取菜单
	 * @param map
	 * @return
	 */
	public List<ASysMenu> getMenuBySysCode(Map<String, Object> map);
}

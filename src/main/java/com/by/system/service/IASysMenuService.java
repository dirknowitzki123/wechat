package com.by.system.service;
import java.util.List;
import java.util.Map;

import com.by.core.service.IBaseService;
import com.by.system.model.ASysMenu;

public interface IASysMenuService extends IBaseService<ASysMenu>{
	public void delete(List<String> ids);
	/**根据配置文件查找菜单*/
	List<ASysMenu> getMenusByModules(Map<String,Object> map);
}


package com.by.system.mapper;
import java.util.List;
import java.util.Map;

import com.by.core.mapper.BaseMapper;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.dto.ASysMenuDTO;
import com.by.system.model.ASysMenu;

@MyBatisRepositoryMysql
public interface ASysMenuMapper extends BaseMapper<ASysMenu>{
	
	List<ASysMenuDTO> getListBO(Map<String,Object> map);
	
	List<ASysMenu> getMenus(Map<String,Object> map);
	
}


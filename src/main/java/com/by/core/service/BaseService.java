package com.by.core.service;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.by.core.dao.BaseDaoMysql;
import com.by.core.dao.BaseDaoOracle;

public class BaseService{
	
	public static final Log log = LogFactory.getLog(BaseService.class); 
	
	@Resource(name="baseDaoMysql")
	public BaseDaoMysql daoMysql;
	
	@Resource(name="baseDaoOracle")
	public BaseDaoOracle daoOracle;
}

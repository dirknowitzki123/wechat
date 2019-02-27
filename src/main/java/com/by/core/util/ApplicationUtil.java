package com.by.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 模块加载
 * @author hemf
 */
public class ApplicationUtil {
	
	private static List<String> modules;

	private static final String MODULE_LIMIT = ",";
	
	/**
	 * 获取当前系统的所加载的模块代码
	 * @return
	 */
	public static List<String> getModules() {
		if ( modules == null ) modules = new ArrayList<String>();
		return modules;
	}

	/**
	 * 设置当前系统所加的模块代码
	 * @param modules 
	 */
	public static void setModules(List<String> modules) {
		ApplicationUtil.modules = modules;
	}
	
	/**
	 * 设置当前系统所加的模块代码
	 * @param modules
	 */
	public static void setModules(String moduleStrs) {
		if ( StringUtils.isEmpty(moduleStrs) ) return;
		modules = Arrays.asList(moduleStrs.split(MODULE_LIMIT)); 
	}
	
	
	
	
}

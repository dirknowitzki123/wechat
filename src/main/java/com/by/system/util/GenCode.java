package com.by.system.util;

import java.util.HashMap;
import java.util.Map;
/**
 * 代码生成器
 * 注：运行main方法后，请手动F5刷新。
 */
public class GenCode {
	public static void main(String[] args) {
		Map<String,String> map=new HashMap<String,String>();
		/* oracle
		map.put("URL", "jdbc:oracle:thin:@114.55.4.217:1521:orcl");
		map.put("NAME", "bytest");
		map.put("PASS", "bycxcom");
		map.put("DRIVER", "oracle.jdbc.xa.client.OracleXADataSource");
		*/
		/**
		 * 必填项
		 */
		//数据库地址
		map.put("URL", "jdbc:mysql://127.0.0.1:3306/research?useUnicode=true&amp;characterEncoding=utf-8");
		//数据库驱动
		map.put("DRIVER", "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
		//数据库登录名(
		map.put("NAME", "root");
		//数据库登录密码
		map.put("PASS", "123123");
		//表名
		map.put("tablename", "t_cust_base_info");
		/**
		 * 可选项
		 */
		//schema
		map.put("schema", "");
		//实体类生成路径,可传空字符串,默认为com.by.system.util
		map.put("entityPath", "com.by.bussiness.wechat.model");
		//mapper生成路径,可传空字符串,默认为com.by.system.util
		map.put("mapperPath", "com.by.bussiness.wechat.mapper");
		//service生成路径,可传空字符串,默认为com.by.system.util
		map.put("servicePath", "com.by.bussiness.wechat.service");
		
		GenCodeUtil util=new GenCodeUtil(map);
		util.genEntity();
		util.genMapper();
		util.genService();
	}
}

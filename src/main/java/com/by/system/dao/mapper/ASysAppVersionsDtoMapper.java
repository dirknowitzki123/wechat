/**
 * 
 */
package com.by.system.dao.mapper;

import java.util.List;
import java.util.Map;

import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.system.model.ASysAppVersions;

/**
 * ***********************************
 * @describe  	获取最新app下载信息
 * @version        V1.0
 * @author         YQR
 * @phone          13086658775
 * @date           2016年5月24日
 *
 * @modifyReason 
 * @modifyAuthor
 * @phone           
 * @modifyDate 
 * ***********************************
 */
@MyBatisRepositoryMysql
public interface ASysAppVersionsDtoMapper {
	public ASysAppVersions queryNewVersion(ASysAppVersions aSysAppVersions);
	
	/**
	 * 模糊查询
	 * @param params
	 * @return
	 */
	public List<ASysAppVersions> getVersionsList(Map<String, Object> params);
}

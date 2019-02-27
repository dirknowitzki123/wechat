/**
 * ***********************************
 * @describe       博雅分期GO   
 * @version        V1.0
 * @author         张敏
 * @phone          13699488431
 * @date           2016年5月24日
 * ***********************************
 */
package com.by.pub.intfc.service;

import java.util.List;
import java.util.Map;

import com.by.pub.model.ASysAtt;

/**
 * ***********************************
 * @describe       
 * @version        V1.0
 * @author         张敏
 * @phone          13699488431
 * @date           2016年5月24日
 *
 * @modifyReason 
 * @modifyAuthor
 * @phone           
 * @modifyDate 
 * ***********************************
 */
public interface AppPubAttaService {
	
	/**
	 * 根据附件ID 获取附件
	 * @param map {id:''}
	 * @return
	 */
	ASysAtt getById(Map<String, Object> map);
	
	ASysAtt save(Map<String, Object> map);
	
	void delete(Map<String, Object> map);
	
	List<ASysAtt> getListByInstDateDesc(Map<String, Object> map);
	
	List<ASysAtt> getList(Map<String, Object> map);

}

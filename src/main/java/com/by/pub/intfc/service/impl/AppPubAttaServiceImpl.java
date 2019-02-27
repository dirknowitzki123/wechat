/**
 * ***********************************
 * @describe       博雅分期GO   
 * @version        V1.0
 * @author         张敏
 * @phone          13699488431
 * @date           2016年5月24日
 * ***********************************
 */
package com.by.pub.intfc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.by.core.exception.BusinessException;
import com.by.pub.dto.mapper.ASysAttDtoMapper;
import com.by.pub.intfc.service.AppPubAttaService;
import com.by.pub.mapper.ASysAttMapper;
import com.by.pub.model.ASysAtt;
import com.by.pub.service.IASysAttService;

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
@Service
public class AppPubAttaServiceImpl implements AppPubAttaService {

	@Autowired
	private IASysAttService aSysAttService;
	@Autowired
	private ASysAttDtoMapper aSysAttDtoMapper;
	@Autowired
	private ASysAttMapper aSysAttMapper;
	
	@Override
	public ASysAtt getById(Map<String, Object> map) {
		if(null == map){
			throw new BusinessException("","");
		}
		String uuid = String.valueOf(map.get("id"));
		ASysAtt att = aSysAttService.getByPrimaryKey(uuid);
		return att;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ASysAtt save(Map<String, Object> map) {
		if(null == map){
			throw new BusinessException("","");
		}
		Object delMaps = map.get("delMaps");
		Object atta = map.get("atta");
		if(null == atta){
			throw new BusinessException("请上传附件");
		}
		
		if(null != delMaps){
			Map<String, Object> delMap = (Map<String, Object>) delMaps;
			aSysAttDtoMapper.deleteByBusiNoAttTyp(delMap);
		}
		ASysAtt sysAtt = JSON.parseObject(atta.toString(), ASysAtt.class);
		aSysAttMapper.insert(sysAtt);
		
		return sysAtt;
	}


	
	@Override
	public void delete(Map<String, Object> map) {
		if(null == map){
			throw new BusinessException("","");
		}
		
		Object obj = map.get("list");
		if(null == obj){
			return ;
		}
		List<String> ids = JSON.parseArray(obj.toString(), String.class);
		for(String id : ids){
			aSysAttMapper.deleteByPrimaryKey(id);
		}
		
	}
	
	@Override
	public List<ASysAtt> getListByInstDateDesc(Map<String, Object> map) {
		List<ASysAtt> list = aSysAttDtoMapper.getListByInstDateDesc(map);
		return list;
	}


	@Override
	public List<ASysAtt> getList(Map<String, Object> map) {
		return aSysAttService.getList(map);
	}

}

package com.by.system.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.constant.PubBusinessConstant;
import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.core.util.SessionUtil;
import com.by.core.util.StringUtils;
import com.by.frame.bo.CodeBo;
import com.by.system.mapper.ASysCodeInfoMapper;
import com.by.system.model.ASysCodeInfo;
import com.by.system.service.IASysCodeInfoService;

@Service
public class ASysCodeInfoServiceImpl extends BaseService implements IASysCodeInfoService{
	
	private static final String DEFUALT_GROUP_KEY = "__default__";
	private static final String LEVEL_CHILDREN_KEY = "children";
	
	private static final String TYPE_CODE_KEY = "typeCode";
	private static final String LEVEL_KEY = "level"; //层级
	private static final String TYPE_CODE_STATUS = "status";
	
	@Autowired
	private ASysCodeInfoMapper aSysCodeInfoMapper;

	@Override
	public ASysCodeInfo get(Map<String,Object> map){
		return aSysCodeInfoMapper.get(map);
	}
	@Override
	public List<ASysCodeInfo> getList(Map<String,Object> map){
		return aSysCodeInfoMapper.getList(map);
	}
	@Override
	public void save(ASysCodeInfo obj) {
		if (StringUtils.isEmpty( obj.getId())) {
			obj.setId(StringUtils.getUUID());
			obj.setInstDate(new Date());
			obj.setInstUserNo(SessionUtil.getCurrentASysUser().getLoginName());
			super.daoMysql.save(obj);
			return;
		} 
		obj.setUpdtDate(new Date());
		aSysCodeInfoMapper.updateByPrimaryKey( BeanUtil.transBean2Map( obj ) );
	}
	@Override
	public void update(ASysCodeInfo Obj) {
		// TODO Auto-generated method stub
		
	}
	public void delete(String id) {
		super.daoMysql.delete(id);
	}
	@Override
	public Map<String, Object> getCode(String typeCode) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("typeCode", typeCode);
		List<ASysCodeInfo> codeList=aSysCodeInfoMapper.getList(map);//__deault__
		List<String> groupCodes=aSysCodeInfoMapper.getGroupCodes(map);
		
		Map<String,Object> re=new HashMap<String,Object>();
		
		Map<String,Object> type=new HashMap<String,Object>();
		type.put("__default__", codeList);
		
		for(String groupCode:groupCodes){
			type.put(groupCode, aSysCodeInfoMapper.getCodesByGroupCode(groupCode));
			
		}
		re.put(typeCode, type);
		return re;
	}
	@Override
	public Map<String, Object> getCode(List<String> list) {
		Map<String,Object> re=new HashMap<String,Object>();
		for(String typeCode:list){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("typeCode", typeCode);
			List<ASysCodeInfo> codeList=aSysCodeInfoMapper.getList(map);//__deault__
			List<String> groupCodes=aSysCodeInfoMapper.getGroupCodes(map);
			
			Map<String,Object> type=new HashMap<String,Object>();
			type.put("__default__", codeList);
			for(String groupCode:groupCodes){
				type.put(groupCode, aSysCodeInfoMapper.getCodesByGroupCode(groupCode));
			}
			re.put(typeCode, type);
		}
		return re;
	}
	@Override
	public void delete(List<String> ids) {
		super.daoMysql.delete(ids,ASysCodeInfo.class);
	}
	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> getCode2(List<CodeBo> codes) {
		Map<String,Object> re = new HashMap<String,Object>(); //返回结果
		Map<String,Object> map;//查询参数 与码表对象
		List<ASysCodeInfo> codeList;//实体码值对象
		List<Object> codeListLevel;//有子集的码值对象
		List<String> groups;//马组
		
		for ( CodeBo code : codes ) {
			map = new HashMap<String,Object>();
			map.put(TYPE_CODE_KEY, code.getType());
			map.put(TYPE_CODE_STATUS,PubBusinessConstant.YES);
			codeList=aSysCodeInfoMapper.getList(map);
			groups=aSysCodeInfoMapper.getGroupCodes(map);
			map.put(LEVEL_KEY, code.getLevel());
			map.put(DEFUALT_GROUP_KEY, getLevelCode(codeList, code.getLevel() - 1 ));
			for ( String group : groups ){
				map.put( group, aSysCodeInfoMapper.getCodesByGroupCode(group) );
			}
			re.put(code.getType(), map);
		}
		
		return re;
	}
	
	/**
	 * 获取级联的码表独享
	 * @param aSysCodeInfos
	 * @param level 层级
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<?> getLevelCode( List<ASysCodeInfo> aSysCodeInfos, Integer level ) {
		if( level == null || level <= 0 ) {
			return aSysCodeInfos;
		}
		List<Object> codeMaps = new ArrayList<Object>();
		Map<String,Object> codeMap;
		Map<String,Object> map = new HashMap<String,Object>();
		List<ASysCodeInfo> codeList;
		List<?> codeListLevel;
		for ( ASysCodeInfo aSysCodeInfo : aSysCodeInfos ) {
			
			map.put(TYPE_CODE_KEY, aSysCodeInfo.getValCode());
			codeList=aSysCodeInfoMapper.getList(map);
			
			codeMap = BeanUtil.transBean2Map(aSysCodeInfo);
			codeMap.put(LEVEL_CHILDREN_KEY, getLevelCode(codeList, level - 1 ));
			codeMaps.add(codeMap);
		}
		
		return codeMaps;
	}
}


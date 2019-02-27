package com.by.system.comp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.by.core.constant.PubBusinessConstant;
import com.by.core.startup.ContextInit;
import com.by.core.util.StringUtils;
import com.by.system.mapper.ASysCodeInfoMapper;
import com.by.system.model.ASysCodeGroup;
import com.by.system.model.ASysCodeInfo;
import com.by.system.service.IASysCodeGroupService;
import com.by.system.service.IASysCodeInfoService;

/**
 * 码表工具类
 * @author HeJian
 *
 */
public class CodeUtil {

	/**
	 * 获取码表组成员
	 * @param grpCode
	 * @return
	 */
	public static List<ASysCodeGroup> getCodeGroup(String grpCode){
		IASysCodeGroupService service = ContextInit.getContext().getBean(IASysCodeGroupService.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupCode", grpCode);
		return service.getList(map);
	}
	
	/**
	 * 获取码表组下有效的的码表信息
	 * @param grpCode
	 * @return
	 */
	public static List<ASysCodeInfo> getGrpCodeValue(String grpCode){
		IASysCodeInfoService service = ContextInit.getContext().getBean(IASysCodeInfoService.class); 
		List<ASysCodeGroup> lst = getCodeGroup(grpCode);
		List<ASysCodeInfo> rsLst = new ArrayList<ASysCodeInfo>();
		for(ASysCodeGroup grp : lst){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("typeCode", grp.getTypeCode());
			map.put("valCode", grp.getValCode());
			map.put("status",PubBusinessConstant.YES);
			ASysCodeInfo code = service.get(map);
			if(code!=null) rsLst.add(code);
		}
		return rsLst;
	}
	
	
	/**
	 * 获取码表组下有效的码表值
	 * @param grpCode
	 * @return
	 */
	public static List<String> getGrpCodes(String grpCode){
		List<ASysCodeInfo> grpCodeValue = getGrpCodeValue( grpCode);
		List<String> rs = new ArrayList<String>(0);
		for(ASysCodeInfo codeInfo:grpCodeValue){
			String codeValue = codeInfo.getValCode();
			if(StringUtils.isEmpty(codeValue)) continue;
			rs.add(codeValue);
		}
		return rs;
	}
	
	/**
	 * 获取码表类型下的码表值
	 * @param typeCode
	 * @return
	 */
	public static List<ASysCodeInfo> getCodeInfoLst(String typeCode){
		if(StringUtils.isEmpty(typeCode)) return null;
		
		IASysCodeInfoService service = ContextInit.getContext().getBean(IASysCodeInfoService.class); 
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeCode", typeCode);
		List<ASysCodeInfo> list = service.getList(map);
		return list;
	}
	
	/**
	 * 获取码表值
	 * @param typeCode
	 * @param valCode
	 * @return
	 */
	public static ASysCodeInfo getCodeInfo(String typeCode,String valCode){
		if(StringUtils.isEmpty(typeCode) || StringUtils.isEmpty(valCode)) return null;
		IASysCodeInfoService service = ContextInit.getContext().getBean(IASysCodeInfoService.class); 
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeCode", typeCode);
		map.put("valCode", valCode);
		ASysCodeInfo aSysCodeInfo = service.get(map);
		return aSysCodeInfo;
	}
	
	/**
	 * 获取码值名称
	 * @param typeCode
	 * @param valCode
	 * @return
	 */
	public static String getValName(String typeCode,String valCode){
		String valName = null;
		ASysCodeInfo codeInfo = getCodeInfo(typeCode, valCode);
		if(null != codeInfo){
			valName = codeInfo.getValName();
		}
		return valName;
	}
	
	/**
	 * 通过codeType  获取码类
	 * @param types  变长参数 码类
	 * @return 返回结果 key为码值  value为码值对应的中文name
	 */
	public static Map<String,String> getCodesByTypes(String ... types){
		Map<String,String> reMap = new HashMap<String,String>(0);
		if(types==null || types.length==0) return reMap;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("types",Arrays.asList( types));
		map.put("status","13900001");
		ASysCodeInfoMapper mapper = ContextInit.getContext().getBean(ASysCodeInfoMapper.class);
		List<Map<String,String>> list = mapper.getCodesByTypes(map);
		if(list!=null&&list.size()>0){
			for(Map<String,String> m:list){
				reMap.put(m.get("valCode"), m.get("valName"));
			}
		}
		return reMap;
	}
}

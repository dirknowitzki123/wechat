package com.by.system.comp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.by.core.startup.ContextInit;
import com.by.core.util.StringUtils;
import com.by.system.model.CkCodeTypeCompare;
import com.by.system.service.ICkCodeTypeCompareService;

/**
 * 
 * ***********************************
 * @describe	mysql系统码表与老系统码表转换 
 * @version        V1.0
 * @author         HeJian
 * @phone          18683448261
 * @date           2016年5月10日
 *
 * @modifyReason 
 * @modifyAuthor
 * @phone           
 * @modifyDate 
 * ***********************************
 */
public class CodeConverter {

	/**
	 * 获取对应的老码表的值
	 * @param newType
	 * @param newCodeValue
	 * @return 没找到则返回为空
	 */
	public static String getOrclCode(String newType,String newCodeValue){
		if(StringUtils.isEmpty(newType)) return null;
		if(StringUtils.isEmpty(newCodeValue)) return null;
		ICkCodeTypeCompareService codeTypeComp = ContextInit.getContext().getBean(ICkCodeTypeCompareService.class);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("newType", newType);
		param.put("newCodeVal", newCodeValue);
		List<CkCodeTypeCompare> list = codeTypeComp.getList(param);
		if(null == list || list.size() == 0){
			return null;
		}
		CkCodeTypeCompare ckCodeTypeCompare = list.get(0);
		if(null == ckCodeTypeCompare){
			return null;
		}
		return ckCodeTypeCompare.getOldCodeVal();
	}
	
	/**
	 * 获取对应的老码表的值
	 * @param newType
	 * @param newCodeValue
	 * @return 没找到则返回为空
	 */
	public static String getOrclCode(String newType,String newCodeValue,String oldType){
		if(StringUtils.isEmpty(newType)) return null;
		if(StringUtils.isEmpty(newCodeValue)) return null;
		ICkCodeTypeCompareService codeTypeComp = ContextInit.getContext().getBean(ICkCodeTypeCompareService.class);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("newType", newType);
		param.put("newCodeVal", newCodeValue);
		param.put("oldType", oldType);
		List<CkCodeTypeCompare> list = codeTypeComp.getList(param);
		if(null == list || list.size() == 0){
			return null;
		}
		CkCodeTypeCompare ckCodeTypeCompare = list.get(0);
		if(null == ckCodeTypeCompare){
			return null;
		}
		return ckCodeTypeCompare.getOldCodeVal();
	}
	
	/**
	 * 根据老系统码值获取对应的新系统码表的值
	 * @param oldType
	 * @param oldCodeValue
	 * @return 没找到则返回为空
	 */
	public static String getNewCode(String oldType,String oldCodeVal){
		if(StringUtils.isEmpty(oldType)) return null;
		if(StringUtils.isEmpty(oldCodeVal)) return null;
		ICkCodeTypeCompareService codeTypeComp = ContextInit.getContext().getBean(ICkCodeTypeCompareService.class);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("oldType", oldType);
		param.put("oldCodeVal", oldCodeVal);
		List<CkCodeTypeCompare> list = codeTypeComp.getList(param);
		if(null == list || list.size() == 0){
			return null;
		}
		CkCodeTypeCompare ckCodeTypeCompare = list.get(0);
		if(null == ckCodeTypeCompare){
			return null;
		}
		return ckCodeTypeCompare.getNewCodeVal();
	}
	
	/**
	 * 根据老系统码值获取对应的新系统码表的值
	 * @param newType
	 * @param newCodeValue
	 * @return 没找到则返回为空
	 */
	public static String getNewCode(String oldType,String oldCodeVal,String newType){
		if(StringUtils.isEmpty(oldType)) return null;
		if(StringUtils.isEmpty(oldCodeVal)) return null;
		ICkCodeTypeCompareService codeTypeComp = ContextInit.getContext().getBean(ICkCodeTypeCompareService.class);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("oldType", oldType);
		param.put("oldCodeVal", oldCodeVal);
		param.put("newType", newType);
		List<CkCodeTypeCompare> list = codeTypeComp.getList(param);
		if(null == list || list.size() == 0){
			return null;
		}
		CkCodeTypeCompare ckCodeTypeCompare = list.get(0);
		if(null == ckCodeTypeCompare){
			return null;
		}
		return ckCodeTypeCompare.getNewCodeVal();
	}
	
}

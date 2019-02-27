package com.by.system.service;
import java.util.List;
import java.util.Map;

import com.by.core.service.IBaseService;
import com.by.system.dto.CodeGroupDTO;
import com.by.system.model.ASysCodeGroup;
import com.by.system.model.ASysCodeInfo;

public interface IASysCodeGroupService extends IBaseService<ASysCodeGroup>{
	public List<ASysCodeGroup> getGroupDistinct(Map<String,Object> map);
	public void sava(CodeGroupDTO codeGroupBO);
	public void delete(List<String> groupCodes,String typeCode);
	/**
	 * 删除码表组下的码表的值
	 * @param valCodes 码值
	 * @param typeCode 码类
	 * @param groupCode 码组
	 */
	public void deleteGrpCodes(List<String> valCodes,String typeCode,String groupCode);
	/**
	 * 获取码组下关联的码值
	 * @param typeCode
	 * @param groupCode
	 * @return
	 */
	public void saveGrpCodes(ASysCodeGroup sysCodeGroup, List<ASysCodeInfo> sysCodeInfos);
	public void saveGroup(String typeCode,String groupCode,String remark);
	public List<ASysCodeInfo> getGrpExistCodeLst(Map<String, Object> params);
	public List<ASysCodeInfo> getGrpNotExistCodeLst(Map<String, Object> params);
}


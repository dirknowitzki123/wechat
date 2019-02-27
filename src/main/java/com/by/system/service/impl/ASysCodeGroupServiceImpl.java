package com.by.system.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.core.util.SessionUtil;
import com.by.core.util.StringUtils;
import com.by.system.dto.CodeGroupDTO;
import com.by.system.mapper.ASysCodeGroupMapper;
import com.by.system.model.ASysCodeGroup;
import com.by.system.model.ASysCodeInfo;
import com.by.system.service.IASysCodeGroupService;

@Service
public class ASysCodeGroupServiceImpl extends BaseService implements IASysCodeGroupService{
	@Autowired
	private ASysCodeGroupMapper aSysCodeGroupMapper;
	
	@Override
	public ASysCodeGroup get(Map<String,Object> map){
		return aSysCodeGroupMapper.get(map);
	}
	@Override
	public List<ASysCodeGroup> getGroupDistinct(Map<String,Object> map){
		return aSysCodeGroupMapper.getGroupDistinct(map);
	}
	@Override
	public List<ASysCodeGroup> getList(Map<String,Object> map){
		return aSysCodeGroupMapper.getList(map);
	}
	@Override
	public void save(ASysCodeGroup obj) {
		if (StringUtils.isEmpty( obj.getId())) {
			obj.setId(StringUtils.getUUID());
			obj.setInstDate(new Date());
			obj.setInstUserNo(SessionUtil.getCurrentASysUser().getLoginName());
			super.daoMysql.save(obj);
			return;
		}
		obj.setUpdtDate(new Date());
		aSysCodeGroupMapper.updateByPrimaryKey( BeanUtil.transBean2Map( obj ) );
	}
	public void sava(CodeGroupDTO codeGroupBO){
		if(codeGroupBO.getList()==null||codeGroupBO.getList().size()<1){
			throw new BusinessException("请勾选码值");
		}
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("groupCode", codeGroupBO.getGroupCode());
		m.put("typeCode", codeGroupBO.getList().get(0).getTypeCode());
		List<ASysCodeGroup> l=aSysCodeGroupMapper.getList(m);
		if("add".equals(codeGroupBO.getOper())){
			if(l!=null&&l.size()>0&&l.get(0)!=null){
				throw new BusinessException("码组编码不能重复");
			}
		}else{
			super.daoMysql.delete(l);
		}
		List<ASysCodeInfo> list=codeGroupBO.getList();
		Date date = new Date();
		String loginName = SessionUtil.getCurrentASysUser().getLoginName(); 
		for(ASysCodeInfo codeInfo:list){
			ASysCodeGroup codeGroup=new ASysCodeGroup();
			codeGroup.setId(super.daoMysql.getUUID());
			codeGroup.setGroupCode(codeGroupBO.getGroupCode().trim());
			codeGroup.setRemark(codeGroupBO.getRemark().trim());
			codeGroup.setTypeCode(codeInfo.getTypeCode());
			codeGroup.setValCode(codeInfo.getValCode());
			codeGroup.setInstUserNo(loginName);
			codeGroup.setInstDate(date);
			super.daoMysql.save(codeGroup);
		}
	}
	@Override
	public void update(ASysCodeGroup Obj) {
		Obj.setUpdtDate(new Date());
		aSysCodeGroupMapper.updateByPrimaryKey( BeanUtil.transBean2Map( Obj ) );
	}
	public void delete(String id) {
		super.daoMysql.delete(id);
	}
	@Override
	public void delete(List<String> ids) {
		super.daoMysql.delete(ids);
	}
	@Override
	public void delete(List<String> groupCodes, String typeCode) {
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("typeCode", typeCode);
		for(String groupCode:groupCodes){
			m.put("groupCode", groupCode);
			List<ASysCodeGroup> l=aSysCodeGroupMapper.getList(m);
			super.daoMysql.delete(l);
		}
	}
	
	/**
	 * 删除组中所包含的值
	 * @param valCodes 码表值
	 * @param typeCode 码表类型
	 */
	public void deleteGrpCodes(List<String> valCodes,String typeCode,String groupCode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeCode", typeCode);
		map.put("groupCode", groupCode);
		for(String valCode : valCodes){
			map.put("valCode", valCode);
			List<ASysCodeGroup> list = aSysCodeGroupMapper.getList(map);
			super.daoMysql.delete(list);
		}
	}
	
	/**
	 * 获取码表组中包含的码表值
	 */
	@Override
	public List<ASysCodeInfo> getGrpExistCodeLst(Map<String,Object> param ) {
		List<ASysCodeInfo> lst = aSysCodeGroupMapper.getGrpExistCodeLst(param);
		return lst;
	}
	
	/**
	 * 获取码表组没有被关联的码表值
	 */
	public List<ASysCodeInfo> getGrpNotExistCodeLst(Map<String,Object> param) {
		List<ASysCodeInfo> lst = aSysCodeGroupMapper.getGrpNotExistCodeLst(param);
		return lst;
	}
	
	/**
	 * 保存码组和码值的关系
	 */
	@Override
	public void saveGrpCodes(ASysCodeGroup sysCodeGroup, List<ASysCodeInfo> sysCodeInfos) {
		if(sysCodeGroup == null) throw new BusinessException("码组信息不存在");
		if(sysCodeInfos==null || sysCodeInfos.size() ==0) throw new BusinessException("请选择码值");
		//先删除码组基本信息的那条记录
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("typeCode", sysCodeGroup.getTypeCode());
		param.put("groupCode", sysCodeGroup.getGroupCode());
		aSysCodeGroupMapper.deleteGrpBasicInfo(param);
		//保存新码组和码值的关系
		for(ASysCodeInfo sysCodeInfo : sysCodeInfos){
			sysCodeGroup.setId(StringUtils.getUUID());
			sysCodeGroup.setInstDate(new Date());
			sysCodeGroup.setValCode(sysCodeInfo.getValCode());
			aSysCodeGroupMapper.insert(sysCodeGroup);
		}
	}
	
	/**
	 * 保存或者修改码组的基本信息
	 */
	@Override
	public void saveGroup(String typeCode,String groupCode,String remark) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("typeCode", typeCode);
		param.put("groupCode", groupCode);
		List<ASysCodeGroup> list = aSysCodeGroupMapper.getGrpBasicInfo(param);
		if(list==null || list.size()==0){ //无此码组，应该保存
			ASysCodeGroup grp = new ASysCodeGroup();
			grp.setId(StringUtils.getUUID());
			grp.setGroupCode(groupCode);
			grp.setInstDate(new Date());
			grp.setInstUserNo(SessionUtil.getCurrentASysUser().getLoginName());
			grp.setRemark(remark);
			grp.setTypeCode(typeCode);
			grp.setValCode(null);
			aSysCodeGroupMapper.insert(grp);
			return;
		}
		//有数据，应该更新且只能更新备注
		param.put("remark", remark);
		param.put("updtDate", new Date());
		aSysCodeGroupMapper.updateByTypeCodeWithGroupCode(param);
	}
	
}


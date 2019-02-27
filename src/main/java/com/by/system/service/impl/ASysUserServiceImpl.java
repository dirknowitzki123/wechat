package com.by.system.service.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.constant.PubBusinessConstant;
import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.util.BeanUtil;
import com.by.core.util.Md5Utils;
import com.by.core.util.ProcedureUtil;
import com.by.core.util.SessionUtil;
import com.by.core.util.StringUtils;
import com.by.system.dto.mapper.ASysGroupUserDtoMapper;
import com.by.system.dto.mapper.ASysUserDtoMapper;
import com.by.system.mapper.ASysRoleUserMapper;
import com.by.system.mapper.ASysUserMapper;
import com.by.system.mapper.ASysUserOrgMapper;
import com.by.system.model.ASysOrg;
import com.by.system.model.ASysRoleUser;
import com.by.system.model.ASysUser;
import com.by.system.model.ASysUserDetail;
import com.by.system.model.ASysUserOrg;
import com.by.system.service.IASysOrgService;
import com.by.system.service.IASysUserDetailService;
import com.by.system.service.IASysUserService;

@Service
public class ASysUserServiceImpl extends BaseService implements IASysUserService{
	@Autowired 
	private ASysUserMapper aSysUserMapper;
	@Autowired
	private ASysRoleUserMapper aSysRoleUserMapper;
	@Autowired
	private ASysUserOrgMapper aSysUserOrgMapper;
	@Autowired
	private IASysOrgService aSysOrgService;
	@Autowired
	private ASysUserDtoMapper aSysUserDtoMapper;
	@Autowired
	private ASysGroupUserDtoMapper aSysGroupUserDtoMapper;
	@Autowired private IASysUserDetailService sSysUserDetailService;
	
	@Override
	public ASysUser get(Map<String,Object> params){
		return aSysUserMapper.get(params);
	}
	@Override
	public List<ASysUser> getList(Map<String,Object> params){
		return aSysUserMapper.getList(params);
	}
	@Override
	public void save(ASysUser aSysUser) {
		String loginName = SessionUtil.getCurrentASysUser().getLoginName();
		Date date = new Date();
		String[] orgCodes=aSysUser.getOrgCode().split(",");
		if(orgCodes != null && orgCodes.length > 0 && StringUtils.isNotEmpty(orgCodes[0])){
			aSysUser.setOrgCode(orgCodes[0]);//取第一个为主机构
		}
		
		if (StringUtils.isEmpty( aSysUser.getId())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loginName", aSysUser.getLoginName());
			if(aSysUserMapper.get(map) != null){
				throw new BusinessException(aSysUser.getLoginName()+"已存在","100010");
			}
			
			aSysUser.setId(aSysUser.getLoginName());
			aSysUser.setPassword(Md5Utils.encode(aSysUser.getPassword().trim()));
			aSysUser.setInstDate(date);
			aSysUser.setInstUserNo(loginName);
			daoMysql.save(aSysUser);
		}else{
			aSysUser.setUpdtDate(date);
			aSysUserMapper.updateByPrimaryKey(BeanUtil.transBean2Map(aSysUser));
			//删除org关系
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("userId", aSysUser.getId());
			daoMysql.delete(aSysUserOrgMapper.getList(m));
		}
		//新增org关系
		if(orgCodes != null && orgCodes.length > 0 && StringUtils.isNotEmpty(orgCodes[0])){
			for(int i=0;i<orgCodes.length;i++){
				ASysUserOrg userOrg = new ASysUserOrg();
				userOrg.setUserId(aSysUser.getId());
				userOrg.setOrgId(orgCodes[i]);
				if(i==0){
					userOrg.setIsMainOrg("13900001");
				}else{
					userOrg.setIsMainOrg("13900002");
				}
				userOrg.setInstUserNo(loginName);
				userOrg.setInstDate(date);
				daoMysql.save(userOrg);
			}
		}
	}
	@Override
	public void delete(List<String> ids) {
		for(String id:ids){
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("userId", id);
			m.put("id", id);
			ASysUser a=aSysUserMapper.get(m);
			if(a!=null)
			{
				List<String> list=new ArrayList<String>();
				list.add(a.getLoginName());
				sSysUserDetailService.delete(list);
			}
			m.remove("id");
			daoMysql.delete(aSysUserOrgMapper.getList(m));
			daoMysql.delete(aSysRoleUserMapper.getList(m));
			daoMysql.delete(ASysUser.class, id);
			m.remove("userId");
			m.put("loginName", a.getLoginName());
			daoMysql.delete(aSysGroupUserDtoMapper.getList(m));
		}
	}
	@Override
	public void update(ASysUser aSysUser) {
		if(null == aSysUser){
			throw new BusinessException("传递的参数有误");
		}
		aSysUser.setUpdtDate(new Date());
		aSysUserMapper.updateByPrimaryKey( BeanUtil.transBean2Map( aSysUser ) );
	}
	public void delete(String id) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", id);
		ASysUser a=aSysUserMapper.get(m);
		if(a!=null)
		{
			List<String> list=new ArrayList<String>();
			list.add(a.getLoginName());
			sSysUserDetailService.delete(list);
		}
		aSysUserMapper.deleteByPrimaryKey(id);
		aSysRoleUserMapper.deleteByUserId(id);
	}
	@Override
	public void save(ASysUser aSysUser, List<String> roleIds) {
		try {
			if(null == aSysUser){
				throw new BusinessException("传递的参数有误");
			}
			aSysRoleUserMapper.deleteByUserId(aSysUser.getId());
			if(!CollectionUtils.isEmpty(roleIds)){
				for(String roleId:roleIds){
					ASysRoleUser aSysRoleUser = new ASysRoleUser();
					aSysRoleUser.setId(daoMysql.getUUID());
					aSysRoleUser.setRoleId(roleId);
					aSysRoleUser.setUserId(aSysUser.getId());
					aSysRoleUserMapper.insert(aSysRoleUser);
				}
			}
			
		} catch (BusinessException e){
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("操作失败");
		}
		
	}
	
	@Override
	public ASysUser getBygetByPrimaryKey(String userId) {
		return aSysUserMapper.getByPrimaryKey(userId);
	}
	
	@Override
	public ASysUser getByLoginname(String loginame) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loginName", loginame);
			return this.get(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询用户异常");
		}
		
	}
	
	@Override
	public List<ASysUser> getUserList(Map<String,Object> params){
		return aSysUserMapper.getUserList(params);
	}
	
	/**
	 * 获取某个用户机构及其子机构的指定角色的用户
	 * @param loginName 用户登录名
	 * @param subUserRoleCode 想查询的子机构的用户角色编号
	 * @return 返回子机构的用户
	 */
	public List<ASysUser> getSubOrgUsers(String loginName,String subUserRoleCode){
		//查询出该用户拥有的机构
		List<ASysOrg> orgList = aSysOrgService.getOrgList(loginName);
		//获取该用户的机构及其子机构的机构id
		Set<String> subOrgIds = new HashSet<String>(0);
		for(ASysOrg org: orgList){
			String orgId = org.getId();
			String[] orgIds = ProcedureUtil.callCHILDREN("A_SYS_ORG", orgId, "PARENT_ID", "select", super.daoMysql);
			subOrgIds.addAll(Arrays.asList(orgIds));
		}
		//获取结果
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("roleCode", subUserRoleCode);
		param.put("orgIds", subOrgIds);
		param.put("stat", PubBusinessConstant.YES);
		List<ASysUser> userLst = aSysUserDtoMapper.getSubUserLst(param);
		return userLst;
	}
	@Override
	public void save(ASysUser aSysUser, ASysUserDetail aSysUserDetail) {
		save(aSysUser);
		sSysUserDetailService.saveforUserInfo(aSysUserDetail, aSysUser);
	
		
	}
	@Override
	public void resetPassword(String userId) {
		ASysUser aSysUser = aSysUserMapper.getByPrimaryKey(userId);
		if(null == aSysUser){
			throw new BusinessException("传递的参数有误");
		}
		aSysUser.setPassword(Md5Utils.encode("123123"));
		aSysUser.setUpdtDate(new Date());
		aSysUserMapper.updateByPrimaryKey( BeanUtil.transBean2Map( aSysUser ) );
		
	}
	
}


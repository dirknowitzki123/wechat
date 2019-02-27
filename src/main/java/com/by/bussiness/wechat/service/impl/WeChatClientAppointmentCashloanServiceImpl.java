package com.by.bussiness.wechat.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.controller.WeChatAuthorizeController;
import com.by.bussiness.wechat.mapper.TClientAppointmentCashloanMapper;
import com.by.bussiness.wechat.mapper.TCustBaseInfoMapper;
import com.by.bussiness.wechat.model.TClientAppointmentCashloan;
import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.bussiness.wechat.service.IWeChatClientAppointmentCashloanService;
import com.by.core.exception.BusinessException;
import com.by.core.util.BeanUtil;
import com.by.core.util.Page;
import com.by.core.util.StringUtils;
import com.by.manage.wechat.dto.mapper.TCustBaseInfoDtoMapper;
import com.by.manage.wechat.mapper.BCoWechatBranchMapper;
import com.by.manage.wechat.model.BCoWechatBranch;

@Service
public class WeChatClientAppointmentCashloanServiceImpl implements IWeChatClientAppointmentCashloanService {
	private static Log logger = LogFactory.getLog(WeChatAuthorizeController.class);
	@Autowired
	private TClientAppointmentCashloanMapper clientAppointmentCashloanMapper;
	@Autowired
	private TCustBaseInfoDtoMapper custBaseInfoDtoMapper;
	@Autowired
	private BCoWechatBranchMapper bCoWechatBranchMapper;

	@Override
	public Page<TClientAppointmentCashloan> save(Page<TClientAppointmentCashloan> page,TClientAppointmentCashloan info) {
		if(null!=info) logger.info("现金贷预约,请求参数："+JSON.toJSONString(info));
		if (null == info
				|| StringUtils.isEmpty(info.getCustName())
				|| StringUtils.isEmpty(info.getPhoneNo())
				|| StringUtils.isEmpty(info.getIvingCity())
				|| StringUtils.isEmpty(info.getReferralCode())
//				|| StringUtils.isEmpty(info.getIsCredites())
//				|| StringUtils.isEmpty(info.getIsStaged())
				) {
			throw new BusinessException("参数不合法","100002");
		}
		
		/*if (Arrays.asList(Constants.IS_NO_GATHER).contains(info.getIsCredites()) == false
				|| Arrays.asList(Constants.IS_STAGED_GATHER).contains(info.getIsStaged()) == false
				) {
			page.setSuccess(false);
			page.setMsg("参数不合法");
			return page;
		}*/
		
		Map<String, Object> params = new HashMap<>();
		params.put("phoneNo", info.getPhoneNo());
		TClientAppointmentCashloan tClientAppointmentCashloan = clientAppointmentCashloanMapper.get(params);
		if (tClientAppointmentCashloan != null) {
			page.setSuccess(false);
			page.setMsg("你已经提交过现金资格预约申请");
			return page;
		}
		//检验推荐码是否有效
		Map<String, Object> custMap = new HashMap<>();
		custMap.put("referralCode", info.getReferralCode());
		custMap.put("status", Constants.STAT_DISABLE);
		TCustBaseInfo custBaseInfo = custBaseInfoDtoMapper.get(custMap);
		if (null == custBaseInfo || StringUtils.isEmpty(custBaseInfo.getReferralCode())) {
			Map<String, Object> branchMap = new HashMap<>();
			branchMap.put("referCode", info.getReferralCode());
			branchMap.put("status", Constants.STAT_ENABLE);
			BCoWechatBranch coWechatBranch = bCoWechatBranchMapper.get(branchMap);
			if (null == coWechatBranch || StringUtils.isEmpty(coWechatBranch.getReferCode())){
				page.setSuccess(false);
				page.setMsg("推荐号码不存在");
				return page;
			}
		}
		info.setId(StringUtils.getUUID());
		info.setCreateDate(new Date());
		clientAppointmentCashloanMapper.insert(info);
		page.setSuccess(true);
		page.setMsg("操作成功");
		return page;
	}

	@Override
	public Page<?> mdlSave(Map<String, Object> map) {
		TClientAppointmentCashloan info = new TClientAppointmentCashloan();
		BeanUtil.transMap2Bean(map, info);
		
		Page<?> page = new Page<TClientAppointmentCashloan>();
		if (null == info
				|| StringUtils.isEmpty(info.getCustName())
				|| StringUtils.isEmpty(info.getPhoneNo())
				|| StringUtils.isEmpty(info.getIvingCity())
				|| StringUtils.isEmpty(info.getReferralCode())
				|| StringUtils.isEmpty(info.getIsCredites())
				|| StringUtils.isEmpty(info.getIsStaged())
				) {
			page.setSuccess(false);
			page.setMsg("参数不合法");
			return page;
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("phoneNo", info.getPhoneNo());
		TClientAppointmentCashloan tClientAppointmentCashloan = clientAppointmentCashloanMapper.get(params);
		if (tClientAppointmentCashloan != null) {
			page.setSuccess(false);
			page.setMsg("你已经提交过现金资格预约申请");
			return page;
		}
		//检验推荐码是否有效
		/*Map<String, Object> custMap = new HashMap<>();
		custMap.put("referralCode", info.getReferralCode());
		custMap.put("status", Constants.STAT_DISABLE);
		TCustBaseInfo custBaseInfo = custBaseInfoDtoMapper.get(custMap);
		if (null == custBaseInfo || StringUtils.isEmpty(custBaseInfo.getReferralCode())) {
			Map<String, Object> branchMap = new HashMap<>();
			branchMap.put("referCode", info.getReferralCode());
			branchMap.put("status", Constants.STAT_ENABLE);
			BCoWechatBranch coWechatBranch = bCoWechatBranchMapper.get(branchMap);
			if (null == coWechatBranch || StringUtils.isEmpty(coWechatBranch.getReferCode())){
				throw new BusinessException("推荐号码不存在");
			}
		}*/
		
		//码值转换
		
		
		info.setId(StringUtils.getUUID());
		info.setCreateDate(new Date());
		clientAppointmentCashloanMapper.insert(info);
		
		page.setSuccess(true);
		page.setMsg("申请成功");
		return page;
	}

}

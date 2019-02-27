package com.by.manage.wechat.service.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.mapper.TCustBaseInfoMapper;
import com.by.bussiness.wechat.mapper.TCustEleSignatureMapper;
import com.by.bussiness.wechat.mapper.TCustExclusiveQrcodeMapper;
import com.by.bussiness.wechat.mapper.TCustLoginInfoMapper;
import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.bussiness.wechat.model.TCustEleSignature;
import com.by.bussiness.wechat.model.TCustExclusiveQrcode;
import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.core.exception.BusinessException;
import com.by.core.service.BaseService;
import com.by.core.startup.AppConfig;
import com.by.core.util.BeanUtil;
import com.by.core.util.HttpHelper;
import com.by.core.util.QRCodeUtils;
import com.by.core.util.StringUtils;
import com.by.manage.wechat.dto.TCustBaseInfoDto;
import com.by.manage.wechat.dto.mapper.TCustBaseInfoDtoMapper;
import com.by.manage.wechat.dto.mapper.TCustExclusiveQrcodeDtoMapper;
import com.by.manage.wechat.dto.mapper.TCustLoginInfoDtoMapper;
import com.by.manage.wechat.service.ITCustBaseInfoService;
import com.file.model.FileBody;

@Service
public class TCustBaseInfoServiceImpl extends BaseService implements ITCustBaseInfoService{
	@Autowired
	private TCustBaseInfoMapper tCustBaseInfoMapper;
	@Autowired
	private TCustBaseInfoDtoMapper tCustBaseInfoDtoMapper;
	@Autowired
    private TCustExclusiveQrcodeMapper custExclusiveQrcodeMapper;
	@Autowired
	private TCustExclusiveQrcodeDtoMapper custExclusiveQrcodeDtoMapper;
	@Autowired
	private TCustLoginInfoDtoMapper custLoginInfoDtoMapper;
	@Autowired
	private TCustLoginInfoMapper custLoginInfoMapper;
	@Autowired
	private TCustEleSignatureMapper custEleSignatureMapper;
	
	@Override
	public TCustBaseInfo get(Map<String,Object> map){
		return tCustBaseInfoDtoMapper.get(map);
	}
	@Override
	public List<TCustBaseInfo> getList(Map<String, Object> map) {
		return tCustBaseInfoDtoMapper.getList(map);
	}
	@Override
	public List<TCustBaseInfoDto> getAllList(Map<String,Object> map){
		return tCustBaseInfoDtoMapper.getAllList(map);
	}
	@Override
	public void save(TCustBaseInfo obj){
		if (obj == null
				|| StringUtils.isEmpty(obj.getParentReferralCode())
				|| StringUtils.isEmpty(obj.getCustName())
				|| StringUtils.isEmpty(obj.getPhoneNo())
				|| StringUtils.isEmpty(obj.getCertNo())
				|| StringUtils.isEmpty(obj.getBankNo())
				|| StringUtils.isEmpty(obj.getOpeningBank())
				|| StringUtils.isEmpty(obj.getReferralCode())
			) {
			throw new BusinessException("参数不合法");
		}
		//1、ID存在修改用户信息
		if (StringUtils.isNotEmpty(obj.getId())) {
			updateInfo(obj); //可以更新推荐码、父级推荐码
			return;
		}
		//2、不存在保存用户
		//1)检查是否注册
		Map<String,Object> param = new HashMap<>();
		param.put("custNo", obj.getPhoneNo());
		TCustLoginInfo custLoginInfo = custLoginInfoMapper.get(param);
		if (null == custLoginInfo){
			throw new BusinessException("该用户尚未注册，不能新增");
		}
		if (StringUtils.isEmpty(obj.getCustNo())) {
			obj.setCustNo(obj.getPhoneNo());
		}
		obj.setCreateDate(new Date());
		String code = AppConfig.get("sys.referralcode.one");
        TCustBaseInfo parentCustBaseInfo = null;
        //2）检查父级推荐码是否存在
		if (!obj.getParentReferralCode().equals(code)) {
		    Map<String, Object> params = new HashMap<>();
	        params.put("referralCode", obj.getParentReferralCode());
	        params.put("status", Constants.STAT_DISABLE);
			parentCustBaseInfo = tCustBaseInfoDtoMapper.get(params );
			if (parentCustBaseInfo == null) {
				throw new BusinessException("父级推荐码不存在");
			}
		}
		//3)检查用户信息是否存在
		Map<String,Object> map = new HashMap<>();
        map.put("custNo", obj.getCustNo());
        map.put("status", Constants.STAT_DISABLE);
        TCustBaseInfo tCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != tCustBaseInfo){
        	throw new BusinessException("用户身份证信息已存在");
        }
        map.remove("custNo");
        map.put("certNo", obj.getCertNo());
        tCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != tCustBaseInfo){
        	throw new BusinessException("用户身份证信息已存在");
        }
        map.remove("certNo");
        map.put("phoneNo", obj.getPhoneNo());
        tCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != tCustBaseInfo){
        	throw new BusinessException("用户身份证信息已存在");
        }
        obj.setId(StringUtils.getUUID());
        obj.setCreateDate(new Date());
        String referralLevel = null;
        String referralPath = null;
        //判断一级推荐码
        if (parentCustBaseInfo==null) {
        	referralLevel = "1";
        	referralPath = obj.getReferralCode();
		}else {
			int level = Integer.valueOf(parentCustBaseInfo.getReferralLevel())+1;
			referralLevel = level+"";
			referralPath = parentCustBaseInfo.getReferralPath()+","+obj.getReferralCode();
		}
        obj.setReferralLevel(referralLevel);
        obj.setReferralPath(referralPath);
        //生成微信二维码（先删在新增）
        Map<String, Object> params = new HashMap<>();
        params.put("busiNo", obj.getCustNo());
        params.put("busiType", Constants.EXCLUSIVE_QR_CODE);
		TCustExclusiveQrcode tCustExclusiveQrcode = custExclusiveQrcodeMapper.get(params );
        if (tCustExclusiveQrcode != null) {
        	custExclusiveQrcodeMapper.deleteByPrimaryKey(tCustExclusiveQrcode);
		}
        File qrFile = new File(AppConfig.get("upload.dedicated_file.path")+obj.getCustNo()+".png"); //二维码保存路径
        if (qrFile.exists() == false) {
    		qrFile.mkdirs();
		}
		String encodeddata = AppConfig.get("cust_dedicated_link")+"/"+obj.getReferralCode(); //二维码内容
		QRCodeUtils.qrCodeEncode(encodeddata, qrFile);
		
		TCustExclusiveQrcode vo = new TCustExclusiveQrcode();
		vo.setId(StringUtils.getUUID());
		vo.setBusiNo(obj.getCustNo());
		vo.setContent(encodeddata);
		vo.setBusiType(Constants.EXCLUSIVE_QR_CODE);
		vo.setIsWechat(Constants.YON_N);
		vo.setAttName(obj.getCustNo());
		vo.setAttSuffix("png");
		vo.setAttPath(AppConfig.get("upload.dedicated_file.path"));
		vo.setRemark("专属二维码（非微信二维码）");
		vo.setCreateDate(new Date());
		//保存用户信息（先生成推荐码再保存用户信息）
		custExclusiveQrcodeMapper.insert(vo);
        tCustBaseInfoMapper.insert(obj);
	}
	@Override
	public void update(TCustBaseInfo obj){
		if (obj == null
				|| StringUtils.isEmpty(obj.getId())
				|| StringUtils.isEmpty(obj.getCustNo())
				|| StringUtils.isEmpty(obj.getParentReferralCode())
				|| StringUtils.isEmpty(obj.getCustName())
				|| StringUtils.isEmpty(obj.getPhoneNo())
				|| StringUtils.isEmpty(obj.getCertNo())
				|| StringUtils.isEmpty(obj.getBankNo())
				|| StringUtils.isEmpty(obj.getOpeningBank())
				|| StringUtils.isEmpty(obj.getReferralCode())
			) {
			throw new BusinessException("参数不合法");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("id", obj.getId());
		params.put("status", Constants.STAT_DISABLE);
		TCustBaseInfo tCustBaseInfo = tCustBaseInfoDtoMapper.get(params);
		if (null == tCustBaseInfo) {
			throw new BusinessException("用户信息不存在");
		}
		
		//3)检查用户信息是否存在
		Map<String,Object> map = new HashMap<>();
        map.put("custNo", obj.getCustNo());
        map.put("status", Constants.STAT_DISABLE);
        TCustBaseInfo custNoToCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != custNoToCustBaseInfo && !custNoToCustBaseInfo.getId().equals(obj.getId())){
        	throw new BusinessException("用户身信息已存在");
        }
        map.remove("custNo");
        map.put("certNo", obj.getCertNo());
        TCustBaseInfo certNoToCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != certNoToCustBaseInfo && !certNoToCustBaseInfo.getId().equals(obj.getId())){
        	throw new BusinessException("用户身份证信息已存在");
        }
        map.remove("certNo");
        map.put("phoneNo", obj.getPhoneNo());
        TCustBaseInfo phoneNoToCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != phoneNoToCustBaseInfo && !phoneNoToCustBaseInfo.getId().equals(obj.getId())){
        	throw new BusinessException("用户手机号信息已存在");
        }
		if (!tCustBaseInfo.getCustNo().equals(obj.getCustNo()) || !tCustBaseInfo.getParentReferralCode().equals(obj.getParentReferralCode())) {
			throw new BusinessException("参数信息错误");
		}
		obj.setCreateDate(tCustBaseInfo.getCreateDate());
		obj.setModifyDate(new Date());
		tCustBaseInfoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(obj));
	}
	@Override
	public void delete(List<String> ids){
		for (int i = 0; i < ids.size(); i++) {
			//通过id销户
			TCustLoginInfo custLoginInfo = custLoginInfoDtoMapper.getByPrimaryKey(ids.get(i));
			custLoginInfo.setStatus(Constants.STAT_DISABLE);
			custLoginInfoDtoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(custLoginInfo));
			
			//调用满得利接口实现信息同步
			//callMdlDisable(custLoginInfo.getCustNo());
			//暂时停止和满得利的信息同步
		}
		
		
	}
	/**
	 * @desc 调满得利接口同步父级推荐码信息
	 * @param custNo   用户手机号
	 * @param newReferCode  用户状态
	 */
	private void callMdlDisable(String custNo){
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("custNo", custNo);
        String jsonStr = JSON.toJSONString(map);
        HttpHelper.postJson(AppConfig.get("mdl.disable.custstatus"), jsonStr);
	}
	@Override
	public void enable(List<String> ids) {
		for (int i = 0; i < ids.size(); i++) {
			//通过id启用用户
			TCustLoginInfo custLoginInfo = custLoginInfoDtoMapper.getByPrimaryKey(ids.get(i));
			custLoginInfo.setStatus(Constants.STAT_ENABLE);
			custLoginInfoDtoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(custLoginInfo));
			
			//调用满得利接口实现信息同步
			//callMdlEnable(custLoginInfo.getCustNo());
			//暂时停止和满得利的信息同步
		}
	}
	/**
	 * @desc 调满得利接口同步父级推荐码信息
	 * @param custNo   用户手机号
	 * @param newReferCode  用户状态
	 */
	private void callMdlEnable(String custNo){
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("custNo", custNo);
        String jsonStr = JSON.toJSONString(map);
        HttpHelper.postJson(AppConfig.get("mdl.enable.custstatus"), jsonStr);
	}
	
	@Override
	public void updateInfo(TCustBaseInfo obj) {
		if (obj == null
				|| StringUtils.isEmpty(obj.getId())
				|| StringUtils.isEmpty(obj.getCustNo())
				|| StringUtils.isEmpty(obj.getParentReferralCode())
				|| StringUtils.isEmpty(obj.getCustName())
				|| StringUtils.isEmpty(obj.getPhoneNo())
				|| StringUtils.isEmpty(obj.getCertNo())
				|| StringUtils.isEmpty(obj.getBankNo())
				|| StringUtils.isEmpty(obj.getOpeningBank())
				|| StringUtils.isEmpty(obj.getReferralCode())
			) {
			throw new BusinessException("参数不合法");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("id", obj.getId());
		params.put("status", Constants.STAT_DISABLE);
		TCustBaseInfo tCustBaseInfo = tCustBaseInfoDtoMapper.get(params);
		if (null == tCustBaseInfo) {
			throw new BusinessException("用户信息不存在");
		}
		
		//3)检查用户信息是否存在
		Map<String,Object> map = new HashMap<>();
        map.put("custNo", obj.getCustNo());
        map.put("status", Constants.STAT_DISABLE);
        TCustBaseInfo custNoToCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != custNoToCustBaseInfo && !custNoToCustBaseInfo.getId().equals(obj.getId())){
        	throw new BusinessException("用户身信息已存在");
        }
        map.remove("custNo");
        map.put("certNo", obj.getCertNo());
        TCustBaseInfo certNoToCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != certNoToCustBaseInfo && !certNoToCustBaseInfo.getId().equals(obj.getId())){
        	throw new BusinessException("用户身份证信息已存在");
        }
        map.remove("certNo");
        map.put("phoneNo", obj.getPhoneNo());
        TCustBaseInfo phoneNoToCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != phoneNoToCustBaseInfo && !phoneNoToCustBaseInfo.getId().equals(obj.getId())){
        	throw new BusinessException("用户手机号信息已存在");
        }
        map.remove("phoneNo");
		if (!tCustBaseInfo.getCustNo().equals(obj.getCustNo())) {
			throw new BusinessException("参数信息错误");
		}
		//修改父级推荐码信息
		updateParentReferCodeInfo(obj, tCustBaseInfo);
		
		//修改推荐码信息
		//updateReferCodeInfo(obj, tCustBaseInfo);
		
		obj.setCreateDate(tCustBaseInfo.getCreateDate());
		obj.setModifyDate(new Date());
		tCustBaseInfoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(obj));
		
		//
	}
	
	/**
	 * @desc 更新父级推荐码信息
	 * @param obj   前端传递过来的实体
	 * @param baseInfo 修改之前的实体
	 */
	private void updateParentReferCodeInfo(TCustBaseInfo obj, TCustBaseInfo baseInfo) {
		
		if (!obj.getParentReferralCode().equals(baseInfo.getParentReferralCode())) {
			//检查新父级推荐码是否存在
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("referralCode", obj.getParentReferralCode());
			//map.put("status", Constants.STAT_DISABLE);
			TCustBaseInfo parentCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
			if (parentCustBaseInfo == null && !obj.getParentReferralCode().equals(AppConfig.get("sys.referralcode.one"))) {
				throw new BusinessException("父级推荐码不存在");
			}
			
			//查询旧父级推荐码(为了获取其路径)
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("referralCode", baseInfo.getParentReferralCode());
			//param.put("status", Constants.STAT_DISABLE);
			TCustBaseInfo oldParCustBaseInfo = tCustBaseInfoDtoMapper.get(param);
			
			int level = 0;   //级别
			String path = "";  //路径
			if (obj.getParentReferralCode().equals(AppConfig.get("sys.referralcode.one"))){ 
				//父级推荐码改为系统默认一级推荐码的情况
				level = 1;     
				path = baseInfo.getReferralPath().replace(oldParCustBaseInfo.getReferralPath()+",", "");
			}else{   
				//父级推荐码改为普通用户推荐码的情况
				level = Integer.valueOf(parentCustBaseInfo.getReferralLevel()) + 1; 
				if (oldParCustBaseInfo == null){
					path = parentCustBaseInfo.getReferralPath()+",".concat(baseInfo.getReferralPath());
				}else{
					path = baseInfo.getReferralPath().replace(oldParCustBaseInfo.getReferralPath(), parentCustBaseInfo.getReferralPath());
				}
			}
			obj.setReferralLevel(level+"");
			obj.setReferralPath(path);
			
			//本身级别变化值
			int levelChange = level - Integer.valueOf(baseInfo.getReferralLevel());
			//查询后代
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("referralPath", baseInfo.getReferralCode());
			paramMap.put("status", Constants.STAT_DISABLE);
			List<TCustBaseInfo> list = tCustBaseInfoDtoMapper.getChildren(paramMap);
			
			//遍历修改后代的级别和路径
			for (TCustBaseInfo info : list){
				int infoLevel = 0;
				if (!info.getReferralCode().equals(baseInfo.getReferralCode())){
					String refPath = info.getReferralPath().replace(baseInfo.getReferralPath(), obj.getReferralPath());
					info.setReferralPath(refPath);
					//加上父类级别变化值
					infoLevel = Integer.valueOf(info.getReferralLevel()) + levelChange;
					if (infoLevel > Integer.valueOf(AppConfig.get("sys.referralcode.max.level"))){
						throw new BusinessException("推荐码级别超过了最大层级");
					}
					info.setReferralLevel(infoLevel+"");
					tCustBaseInfoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(info));
				}
			}
			//调满得利接口实现父级推荐码信息同步
			callMdlModityParReferCode(baseInfo.getReferralCode(), obj.getReferralCode(),
									  baseInfo.getParentReferralCode(),obj.getParentReferralCode());
		}
		
	}
	
	/**
	 * @desc 调满得利接口同步父级推荐码信息
	 * @param oldReferCode   修改前的推荐码
	 * @param newReferCode   修改后的推荐码
	 * @param oldParentReferCode   修改前的父级推荐码
	 * @param newParentReferCode   修改后的父级推荐码
	 */
	private void callMdlModityParReferCode(String oldReferCode, String newReferCode, String oldParentReferCode, String newParentReferCode){
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("oldReferCode", oldReferCode);
        map.put("newReferCode", newReferCode);
        map.put("oldParentReferralCode", oldParentReferCode);
        map.put("newParentReferralCode", newParentReferCode);
        String jsonStr = JSON.toJSONString(map);
        HttpHelper.postJson(AppConfig.get("mdl.update.parent.refercode"), jsonStr);
    }
	
	/**
	 * @desc 更新推荐码信息
	 * @param obj   前端传递过来的实体
	 * @param baseInfo 修改之前的实体
	 */
	private void updateReferCodeInfo(TCustBaseInfo obj, TCustBaseInfo baseInfo){
		//检查自身推荐码是否改变
		if (!obj.getReferralCode().equals(baseInfo.getReferralCode())) {
			//修改自身推荐码
			obj.setReferralCode(obj.getReferralCode());
			//修改路径
			String path = obj.getReferralPath().replace(baseInfo.getReferralCode(), obj.getReferralCode());
			obj.setReferralPath(path);
			
			//查询后代
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("referralPath", baseInfo.getReferralCode());
			paramMap.put("status", Constants.STAT_DISABLE);
			List<TCustBaseInfo> list = tCustBaseInfoDtoMapper.getChildren(paramMap);
			
			//遍历修改后代路径
			for (TCustBaseInfo info : list){
				//修改路径
				String refPath = info.getReferralPath().replace(baseInfo.getReferralCode(), obj.getReferralCode());
				info.setReferralPath(refPath);
				//修改该推荐码下级的父级推荐码
				if (info.getParentReferralCode().equals(baseInfo.getReferralCode())){
					info.setParentReferralCode(obj.getReferralCode());
				}
				tCustBaseInfoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(info));
			}
		}
	}
	
	@Override
	public FileBody getEleContractInputStream(String phoneNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("busiNo", phoneNo);
		params.put("busiType", Constants.ELE_SIGNATURE_CONTRACT);
		TCustEleSignature tCustEleSignature = custEleSignatureMapper.get(params);
		if (tCustEleSignature == null) {
			throw new BusinessException("二维码不存在");
		}
		FileBody fileBody = new FileBody();
		File file = new File(tCustEleSignature.getAttPath() + tCustEleSignature.getAttName()+ "." + tCustEleSignature.getAttSuffix());
		fileBody.setExtName(tCustEleSignature.getAttSuffix());
		fileBody.setOriginFileName(tCustEleSignature.getAttName());
		try {
			InputStream inStream = new FileInputStream(file);
			fileBody.setInputStream(inStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileBody;
	}
	
}


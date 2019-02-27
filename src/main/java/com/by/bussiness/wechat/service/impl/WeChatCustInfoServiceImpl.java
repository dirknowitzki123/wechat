package com.by.bussiness.wechat.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.mapper.TCustBaseInfoMapper;
import com.by.bussiness.wechat.mapper.TCustExclusiveQrcodeMapper;
import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.bussiness.wechat.model.TCustExclusiveQrcode;
import com.by.bussiness.wechat.service.IWeChatCustInfoService;
import com.by.core.exception.BusinessException;
import com.by.core.exception.WeChatSessionException;
import com.by.core.startup.AppConfig;
import com.by.core.util.BeanUtil;
import com.by.core.util.HttpHelper;
import com.by.core.util.Page;
import com.by.core.util.QRCodeUtils;
import com.by.core.util.StringUtils;
import com.by.manage.wechat.dto.mapper.TCustBaseInfoDtoMapper;
import com.by.wechat.util.WeChatSessionUtil;
import com.file.model.FileBody;

/**
 * Created by yiqr on 2017/6/5.
 */
@Service
public class WeChatCustInfoServiceImpl implements IWeChatCustInfoService {

    @Autowired
    private TCustBaseInfoMapper custBaseInfoMapper;
    @Autowired
	private TCustBaseInfoDtoMapper tCustBaseInfoDtoMapper;
    @Autowired
    private TCustExclusiveQrcodeMapper custExclusiveQrcodeMapper;

    @Override
    public TCustBaseInfo get(String custNo) {
        if (StringUtils.isEmpty(custNo)){
            throw new BusinessException("参数不合法","100002");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("custNo",custNo);
        TCustBaseInfo tCustBaseInfo = custBaseInfoMapper.get(map);
        Map<String, Object> params = new HashMap<>();
        TCustExclusiveQrcode tCustExclusiveQrcode = null;
        if (null != tCustBaseInfo) {
        	params.put("busiNo", tCustBaseInfo.getCustNo());
        	params.put("busiType", Constants.EXCLUSIVE_QR_CODE);
        	params.put("isWechat", Constants.YON_N);
        	tCustExclusiveQrcode = custExclusiveQrcodeMapper.get(params);
		}
        if (null == tCustExclusiveQrcode && tCustBaseInfo !=null) {
        	//生成二维码 
        	File qrFile = new File(AppConfig.get("upload.dedicated_file.path")+tCustBaseInfo.getCustNo()+".png"); //二维码保存路径
        	if (qrFile.exists() == false) {
        		qrFile.mkdirs();
			}
        	
    		String encodeddata = AppConfig.get("cust_dedicated_link")+"/"+tCustBaseInfo.getReferralCode(); //二维码内容
    		QRCodeUtils.qrCodeEncode(encodeddata, qrFile);
    		
    		TCustExclusiveQrcode vo = new TCustExclusiveQrcode();
    		vo.setId(StringUtils.getUUID());
    		vo.setBusiNo(tCustBaseInfo.getCustNo());
    		vo.setBusiType(Constants.EXCLUSIVE_QR_CODE);
    		vo.setContent(encodeddata);
    		vo.setIsWechat(Constants.YON_N);
    		vo.setAttName(tCustBaseInfo.getCustNo());
    		vo.setAttSuffix("png");
    		vo.setAttPath(AppConfig.get("upload.dedicated_file.path"));
    		vo.setRemark("专属二维码（非微信二维码）");
    		vo.setCreateDate(new Date());
    		custExclusiveQrcodeMapper.insert(vo);
		}
        return tCustBaseInfo;
    }

    @Override
    public Page<TCustBaseInfo> save(TCustBaseInfo info) {
    	Page<TCustBaseInfo> page = new Page<>();
        if (info == null
                || StringUtils.isEmpty(info.getCertNo())
                || StringUtils.isEmpty(info.getCustName())
                || StringUtils.isEmpty(info.getBankNo())
                || StringUtils.isEmpty(info.getOpeningBank())
                || StringUtils.isEmpty(info.getParentReferralCode())
                ){
            throw new BusinessException("参数不合法","100002");
        }
        
        //1、检查推荐码是否存在（父节点）
        String code = AppConfig.get("sys.referralcode.one");
        TCustBaseInfo parentCustBaseInfo = null;
		if (!info.getParentReferralCode().equals(code)) {
		    Map<String, Object> params = new HashMap<>();
	        params.put("referralCode", info.getParentReferralCode());
	        params.put("status", Constants.STAT_DISABLE);
			parentCustBaseInfo = tCustBaseInfoDtoMapper.get(params );
			if (parentCustBaseInfo == null) {
				page.setSuccess(false);
				page.setMsg("推荐码不存在");
				return page;
			}
		}
        TCustBaseInfo currentWeChatCust = WeChatSessionUtil.getCurrentWeChatCust();
        if (null == currentWeChatCust) {
        	throw new WeChatSessionException("session已失效");
        }
        //2、判断用戶是否存在
        Map<String,Object> map = new HashMap<>();
        map.put("custNo", currentWeChatCust.getCustNo());
        map.put("status", Constants.STAT_DISABLE);
        TCustBaseInfo tCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != tCustBaseInfo){
        	page.setSuccess(false);
			page.setMsg("用户信息已存在");
			return page;
        }
        map.remove("custNo");
        map.put("certNo", info.getCertNo());
        tCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != tCustBaseInfo){
        	page.setSuccess(false);
			page.setMsg("用户身份证信息已存在");
			return page;
        }
        map.remove("certNo");
        map.put("phoneNo", info.getPhoneNo());
        tCustBaseInfo = tCustBaseInfoDtoMapper.get(map);
        if (null != tCustBaseInfo){
        	page.setSuccess(false);
			page.setMsg("用户手机号信息已存在");
			return page;
        }
        
        //生成推荐码
        boolean isOk = true;
        String codeLenth = AppConfig.get("sys.referralcode.length");
        String referralCode = null;
        while (isOk){
            referralCode = StringUtils.createVerificationCode(Integer.parseInt(codeLenth));
            Map<String,Object> codeMap = new HashMap<>();
            codeMap.put("referralCode",referralCode);
            codeMap.put("status", Constants.STAT_DISABLE);
            TCustBaseInfo baseInfo = tCustBaseInfoDtoMapper.get(codeMap);
            if (baseInfo == null){
                isOk = false;
            }
        }
        info.setId(StringUtils.getUUID());
        
        TCustBaseInfo weChatCust = WeChatSessionUtil.getCurrentWeChatCust();
        if (null == weChatCust) {
        	throw new WeChatSessionException("session已失效");
        }
        info.setCustNo(weChatCust.getCustNo());
        info.setReferralCode(referralCode);//推荐码
        info.setCreateDate(new Date());
        String referralLevel = null;
        String referralPath = null;
        //判断一级推荐码
        if (parentCustBaseInfo==null) {
        	referralLevel = "1";
        	referralPath = info.getReferralCode();
		}else {
			int level = Integer.valueOf(parentCustBaseInfo.getReferralLevel())+1;
			referralLevel = level+"";
			referralPath = parentCustBaseInfo.getReferralPath()+","+info.getReferralCode();
		}
        //如果推荐码层级超过4层 不能注册
        if(Integer.parseInt(referralLevel)>Integer.parseInt(AppConfig.get("sys.referralcode.max.level"))){
        	page.setSuccess(false);
			page.setMsg("当前推荐码不能用于注册!");
			return page;
        }
        
        info.setReferralLevel(referralLevel);
        info.setReferralPath(referralPath);
        custBaseInfoMapper.insert(info);
        
        
		//生成微信二维码（先删在新增）
        Map<String, Object> params = new HashMap<>();
        params.put("busiNo", info.getCustNo());
        params.put("busiType", Constants.EXCLUSIVE_QR_CODE);
		TCustExclusiveQrcode tCustExclusiveQrcode = custExclusiveQrcodeMapper.get(params );
        if (tCustExclusiveQrcode != null) {
        	custExclusiveQrcodeMapper.deleteByPrimaryKey(tCustExclusiveQrcode);
		}
        File qrFile = new File(AppConfig.get("upload.dedicated_file.path")+info.getCustNo()+".png"); //二维码保存路径
        if (qrFile.exists() == false) {
    		qrFile.mkdirs();
		}
		String encodeddata = AppConfig.get("cust_dedicated_link")+"/"+referralCode; //二维码内容
		QRCodeUtils.qrCodeEncode(encodeddata, qrFile);
		
		TCustExclusiveQrcode vo = new TCustExclusiveQrcode();
		vo.setId(StringUtils.getUUID());
		vo.setBusiNo(info.getCustNo());
		vo.setContent(encodeddata);
		vo.setBusiType(Constants.EXCLUSIVE_QR_CODE);
		vo.setIsWechat(Constants.YON_N);
		vo.setAttName(info.getCustNo());
		vo.setAttSuffix("png");
		vo.setAttPath(AppConfig.get("upload.dedicated_file.path"));
		vo.setRemark("专属二维码（非微信二维码）");
		vo.setCreateDate(new Date());
		custExclusiveQrcodeMapper.insert(vo);
		
		//调满得利修改密码接口实现信息同步
        /*Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("phoneNo", info.getPhoneNo());
        paramMap.put("openingBank", info.getOpeningBank());
        paramMap.put("certNo", info.getCertNo());
        paramMap.put("userName", info.getCustName());
        paramMap.put("bankNo", info.getBankNo());
        paramMap.put("parentReferralCode", info.getParentReferralCode());
        paramMap.put("referralCode", info.getReferralCode());
        String jsonStr = JSON.toJSONString(paramMap);
        HttpHelper.postJson(AppConfig.get("mdl.submit.custinfo"), jsonStr);*/
		//暂时停止和满得利信息同步
		
		page.setSuccess(true);
        page.setT(info);
		return page;
    }

	@Override
	public Page<Object> getDedicatedLink(Page<Object> page) {
		TCustBaseInfo currentWeChatCust = WeChatSessionUtil.getCurrentWeChatCust();
		if (null == currentWeChatCust) {
			throw new WeChatSessionException("session已失效");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("busiNo", currentWeChatCust.getCustNo());
		params.put("busiType", Constants.EXCLUSIVE_QR_CODE);
		TCustExclusiveQrcode tCustExclusiveQrcode = custExclusiveQrcodeMapper.get(params );
		String url = "";
		if (null != tCustExclusiveQrcode) {
			url = tCustExclusiveQrcode.getContent();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("url", url);
		page.setMap(map);
		return page;
	}

	@Override
	public FileBody showDedicatedQrcode(String custNo) {
		TCustBaseInfo currentWeChatCust = WeChatSessionUtil.getCurrentWeChatCust();
		if (null == currentWeChatCust || !custNo.equals(currentWeChatCust.getCustNo())) {
			throw new WeChatSessionException("session已失效");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("busiNo", custNo);
		params.put("busiType", Constants.EXCLUSIVE_QR_CODE);
		TCustExclusiveQrcode tCustExclusiveQrcode = custExclusiveQrcodeMapper.get(params );
		if (tCustExclusiveQrcode == null) {
			throw new BusinessException("二维码不存在");
		}
		FileBody fileBody = new FileBody();
		File file = new File(tCustExclusiveQrcode.getAttPath()+tCustExclusiveQrcode.getAttName()+"."+tCustExclusiveQrcode.getAttSuffix());
		fileBody.setExtName(tCustExclusiveQrcode.getAttSuffix());
		fileBody.setOriginFileName(tCustExclusiveQrcode.getAttName());
		try {
			InputStream inputStream = new FileInputStream(file);
			fileBody.setInputStream(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileBody;
	}

	@Override
	public Page<?> mdlSave(Map<String, Object> map) {
		TCustBaseInfo info = new TCustBaseInfo();
		BeanUtil.transMap2Bean(map, info);
		
		Page<?> page = new Page<TCustBaseInfo>();
        if (info == null
                || StringUtils.isEmpty(info.getCertNo())
                || StringUtils.isEmpty(info.getPhoneNo())
                || StringUtils.isEmpty(info.getCustName())
                || StringUtils.isEmpty(info.getBankNo())
                || StringUtils.isEmpty(info.getOpeningBank())
                || StringUtils.isEmpty(info.getParentReferralCode())
                || StringUtils.isEmpty(info.getReferralCode())
                ){
        	page.setSuccess(false);
			page.setMsg("参数不合法");
			return page;
        }
        //检查推荐码是否存在（父节点）
        String code = AppConfig.get("sys.referralcode.one");
        TCustBaseInfo parentCustBaseInfo = null;
        if (!info.getParentReferralCode().equals(code)) {
		    Map<String, Object> params = new HashMap<>();
	        params.put("referralCode", info.getParentReferralCode());
	        params.put("status", Constants.STAT_DISABLE);
			parentCustBaseInfo = tCustBaseInfoDtoMapper.get(params );
			/*if (parentCustBaseInfo == null) {
				page.setSuccess(false);
				page.setMsg("父级推荐码不存在");
				return page;
			}*/
		}
       
        //检查推荐码是否存在
        String referralCode = info.getReferralCode();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("referralCode", referralCode);
        TCustBaseInfo tCustBaseInfo = tCustBaseInfoDtoMapper.get(paramMap);
        if (null != tCustBaseInfo){
        	page.setSuccess(false);
			page.setMsg("推荐码已存在");
			return page;
        }    
        
        info.setId(StringUtils.getUUID());
        info.setCustNo(info.getPhoneNo());
        info.setReferralCode(referralCode);//推荐码
        info.setCreateDate(new Date());
        String referralLevel = null;
        String referralPath = null;
        //判断一级推荐码
        if (parentCustBaseInfo==null) {
        	referralLevel = "1";
        	referralPath = info.getReferralCode();
		}else {
			int level = Integer.valueOf(parentCustBaseInfo.getReferralLevel())+1;
			referralLevel = level+"";
			referralPath = parentCustBaseInfo.getReferralPath()+","+info.getReferralCode();
		}
        //如果推荐码层级超过5层 不能注册
        if(Integer.parseInt(referralLevel)>Integer.parseInt(AppConfig.get("sys.referralcode.max.level"))){
        	page.setSuccess(false);
			page.setMsg("推荐码层级超过5层，不可提交");
			return page;
        }
        
        info.setReferralLevel(referralLevel);
        info.setReferralPath(referralPath);
        
        //判断用戶是否存在
        paramMap.remove("referralCode");
        paramMap.put("phoneNo", info.getPhoneNo());
        paramMap.put("status", Constants.STAT_DISABLE);
        TCustBaseInfo custBaseInfo = tCustBaseInfoDtoMapper.get(paramMap);
        
        //如果存在则更新，不存在就插入
        if (custBaseInfo == null){
        	custBaseInfoMapper.insert(info);
        }else{
        	custBaseInfoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(info));
        }
        
        
		//生成微信二维码（先删在新增）
        Map<String, Object> params = new HashMap<>();
        params.put("busiNo", info.getCustNo());
        params.put("busiType", Constants.EXCLUSIVE_QR_CODE);
		TCustExclusiveQrcode tCustExclusiveQrcode = custExclusiveQrcodeMapper.get(params );
        if (tCustExclusiveQrcode != null) {
        	custExclusiveQrcodeMapper.deleteByPrimaryKey(tCustExclusiveQrcode);
		}
        File qrFile = new File(AppConfig.get("upload.dedicated_file.path")+info.getCustNo()+".png"); //二维码保存路径
        if (qrFile.exists() == false) {
    		qrFile.mkdirs();
		}
		String encodeddata = AppConfig.get("cust_dedicated_link")+"/"+referralCode; //二维码内容
		QRCodeUtils.qrCodeEncode(encodeddata, qrFile);
		
		TCustExclusiveQrcode vo = new TCustExclusiveQrcode();
		vo.setId(StringUtils.getUUID());
		vo.setBusiNo(info.getCustNo());
		vo.setContent(encodeddata);
		vo.setBusiType(Constants.EXCLUSIVE_QR_CODE);
		vo.setIsWechat(Constants.YON_N);
		vo.setAttName(info.getCustNo());
		vo.setAttSuffix("png");
		vo.setAttPath(AppConfig.get("upload.dedicated_file.path"));
		vo.setRemark("专属二维码（非微信二维码）");
		vo.setCreateDate(new Date());
		custExclusiveQrcodeMapper.insert(vo);
		
		page.setSuccess(true);
		page.setMsg("提交成功");
		return page;
	}
}

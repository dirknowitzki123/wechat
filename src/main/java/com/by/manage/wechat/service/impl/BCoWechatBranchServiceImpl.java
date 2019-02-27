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

import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.mapper.TCustBaseInfoMapper;
import com.by.bussiness.wechat.mapper.TCustExclusiveQrcodeMapper;
import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.bussiness.wechat.model.TCustExclusiveQrcode;
import com.by.core.exception.BusinessException;
import com.by.core.exception.WeChatSessionException;
import com.by.core.service.BaseService;
import com.by.core.startup.AppConfig;
import com.by.core.util.BeanUtil;
import com.by.core.util.QRCodeUtils;
import com.by.core.util.StringUtils;
import com.by.manage.wechat.model.BCoWechatBranch;
import com.by.manage.wechat.mapper.BCoWechatBranchMapper;
import com.by.manage.wechat.service.IBCoWechatBranchService;
import com.by.wechat.util.WeChatSessionUtil;
import com.file.model.FileBody;

@Service
public class BCoWechatBranchServiceImpl extends BaseService implements IBCoWechatBranchService{
	@Autowired
	private BCoWechatBranchMapper bCoWechatBranchMapper;
	
	@Autowired
    private TCustExclusiveQrcodeMapper custExclusiveQrcodeMapper;
	
	@Autowired
    private TCustBaseInfoMapper custBaseInfoMapper;

	private boolean update_flag = false;
	
	@Override
	public BCoWechatBranch get(Map<String,Object> map){
		return bCoWechatBranchMapper.get(map);
	}
	@Override
	public List<BCoWechatBranch> getList(Map<String,Object> map){
		return bCoWechatBranchMapper.getList(map);
	}
	@Override
	public void save(BCoWechatBranch obj){
		if (obj == null
				|| StringUtils.isEmpty(obj.getBranchName())
				|| StringUtils.isEmpty(obj.getParReferCode())
				|| StringUtils.isEmpty(obj.getReferCode())
			) {
			throw new BusinessException("参数不合法");
		}
		if (StringUtils.isNotEmpty(obj.getId())) {
			updateInfo(obj);
			update_flag = true;
		}
		if (update_flag == false){
			//保存用户信息
			String code = AppConfig.get("sys.referralcode.one");
			BCoWechatBranch parentCoWechatBranch = null;
			if (!obj.getParReferCode().equals(code)) {
				
				//检查经办人是否是商户
			    Map<String, Object> params = new HashMap<>();
		        params.put("referCode", obj.getParReferCode());
		        params.put("status", Constants.STAT_ENABLE);         
		        parentCoWechatBranch = bCoWechatBranchMapper.get(params);
				if (parentCoWechatBranch != null) {
					throw new BusinessException("经办人不能是商户");
				}
				
				//检查经办人状态是否正常      
				params.remove("referCode");
				params.put("referralCode", obj.getParReferCode());
				TCustBaseInfo custBaseInfo = custBaseInfoMapper.get(params);
				if (custBaseInfo == null) {
					throw new BusinessException("经办人不存在或已经销户");
				}
			}
			Map<String,Object> map = new HashMap<>();
	        
	        map.put("branchName", obj.getBranchName());
	        BCoWechatBranch coWechatBranch = bCoWechatBranchMapper.get(map);
	        if (null != coWechatBranch){
	        	throw new BusinessException("商户名称已存在");
	        }
	        
	        //检验商户是否输入推荐码
	        if (null == obj.getReferCode()){
	        	throw new BusinessException("未输入商户推荐码");
	        }
	        
	        //检验用户输入的推荐码是否已存在
	        Map<String,Object> refercodeMap = new HashMap<>();
	        refercodeMap.put("referCode",obj.getReferCode());
            BCoWechatBranch cowechatBranch = bCoWechatBranchMapper.get(refercodeMap);
            if (cowechatBranch != null || obj.getReferCode().equals(AppConfig.get("sys.referralcode.one"))){
            	throw new BusinessException("输入的商户推荐码已存在，请重新输入");
            }
            
            String referCode = null;
            referCode = obj.getReferCode();
            
	        //生成推荐码
            if (null == referCode){
		        boolean isOk = true;
		        String codeLenth = AppConfig.get("sys.referralcode.length");
		        while (isOk){
		        	referCode = StringUtils.createVerificationCode(Integer.parseInt(codeLenth));
		            Map<String,Object> codeMap = new HashMap<>();
		            codeMap.put("referCode",referCode);
		            BCoWechatBranch wechatBranch = bCoWechatBranchMapper.get(codeMap);
		            if (wechatBranch == null){
		                isOk = false;
		            }
		        }
            }
	        obj.setReferCode(referCode);
	        obj.setStatus(Constants.STAT_ENABLE);
	        obj.setId(StringUtils.getUUID());
	        obj.setInstDate(new Date());
	        
	        bCoWechatBranchMapper.insert(obj);
        
	        //生成微信二维码（先删在新增）
	        Map<String, Object> params = new HashMap<>();
	        params.put("busiNo", obj.getId());
	        params.put("busiType", Constants.EXCLUSIVE_QR_CODE);
			TCustExclusiveQrcode tCustExclusiveQrcode = custExclusiveQrcodeMapper.get(params);
	        if (tCustExclusiveQrcode != null) {
	        	custExclusiveQrcodeMapper.deleteByPrimaryKey(tCustExclusiveQrcode);
			}
	        File qrFile = new File(AppConfig.get("upload.dedicated_file.path")+obj.getId()+".png"); //二维码保存路径
	        if (qrFile.exists() == false) {
	    		qrFile.mkdirs();
			}
			String encodeddata = AppConfig.get("cust_dedicated_link")+"/"+obj.getReferCode(); //二维码内容
			QRCodeUtils.qrCodeEncode(encodeddata, qrFile);
			
			TCustExclusiveQrcode vo = new TCustExclusiveQrcode();
			vo.setId(StringUtils.getUUID());
			vo.setBusiNo(obj.getId());
			vo.setContent(encodeddata);
			vo.setBusiType(Constants.EXCLUSIVE_QR_CODE);
			vo.setIsWechat(Constants.YON_N);
			vo.setAttName(obj.getId());
			vo.setAttSuffix("png");
			vo.setAttPath(AppConfig.get("upload.dedicated_file.path"));
			vo.setRemark("专属二维码（非微信二维码）");
			vo.setCreateDate(new Date());
			custExclusiveQrcodeMapper.insert(vo);
		}
		update_flag = false;
	}
	@Override
	public void update(BCoWechatBranch obj){
		if (obj == null
				|| StringUtils.isEmpty(obj.getBranchName())
				|| StringUtils.isEmpty(obj.getParReferCode())
			) {
			throw new BusinessException("参数不合法");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("id", obj.getId());
		BCoWechatBranch coWechatBranch = bCoWechatBranchMapper.get(params);
		if (null == coWechatBranch) {
			throw new BusinessException("商户信息不存在");
		}
		if (!coWechatBranch.getParReferCode().equals(obj.getParReferCode())) {
			throw new BusinessException("参数信息错误");
		}
		obj.setInstDate(coWechatBranch.getInstDate());
		obj.setUpdtDate(new Date());
		bCoWechatBranchMapper.updateByPrimaryKey(BeanUtil.transBean2Map(obj));
	}
	@Override
	public void delete(List<String> ids){
		for (int i = 0; i < ids.size(); i++) {
			//通过id销户
			BCoWechatBranch coWechatBranch = bCoWechatBranchMapper.getByPrimaryKey(ids.get(i));
			coWechatBranch.setStatus(Constants.STAT_DISABLE);
			bCoWechatBranchMapper.updateByPrimaryKey(BeanUtil.transBean2Map(coWechatBranch));
		}
	}
	
	@Override
	public void enable(List<String> ids) {
		for (int i = 0; i < ids.size(); i++) {
			//通过id启用商户
			BCoWechatBranch coWechatBranch = bCoWechatBranchMapper.getByPrimaryKey(ids.get(i));
			coWechatBranch.setStatus(Constants.STAT_ENABLE);
			bCoWechatBranchMapper.updateByPrimaryKey(BeanUtil.transBean2Map(coWechatBranch));
		}
	}

	@Override
	public FileBody showDedicatedQrcode(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("busiNo", id);
		params.put("busiType", Constants.EXCLUSIVE_QR_CODE);
		TCustExclusiveQrcode tCustExclusiveQrcode = custExclusiveQrcodeMapper.get(params);
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
	public void updateInfo(BCoWechatBranch obj) {
		if (obj == null
				|| StringUtils.isEmpty(obj.getBranchName())
				|| StringUtils.isEmpty(obj.getParReferCode())
				|| StringUtils.isEmpty(obj.getReferCode())
			) {
			throw new BusinessException("参数不合法");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("id", obj.getId());
		BCoWechatBranch coWechatBranch = bCoWechatBranchMapper.get(params);
		if (null == coWechatBranch) {
			throw new BusinessException("商户信息不存在");
		}
		
		//检查父级推荐码是否存在
		if (!obj.getParReferCode().equals(coWechatBranch.getParReferCode())) {
			Map<String,Object> map = new HashMap<>();
			map.put("referCode", obj.getParReferCode());
			BCoWechatBranch info = bCoWechatBranchMapper.get(map);
			if (info == null && !obj.getParReferCode().equals(AppConfig.get("sys.referralcode.one"))) {
				throw new BusinessException("父级推荐码不存在");
			}
			//修改父级推荐码
			obj.setParReferCode(obj.getParReferCode());
		}
		//检查自身推荐码是否改变
		if (!obj.getReferCode().equals(coWechatBranch.getReferCode())) {
			//修改自身推荐码
			obj.setReferCode(obj.getReferCode());
		}
		
		obj.setInstDate(coWechatBranch.getInstDate());
		obj.setUpdtDate(new Date());
		bCoWechatBranchMapper.updateByPrimaryKey(BeanUtil.transBean2Map(obj));
	}
}


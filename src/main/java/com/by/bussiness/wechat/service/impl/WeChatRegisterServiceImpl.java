package com.by.bussiness.wechat.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.mapper.TCustLoginInfoMapper;
import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.bussiness.wechat.service.IWeChatCaptchaService;
import com.by.bussiness.wechat.service.IWeChatRegisterService;
import com.by.core.exception.BusinessException;
import com.by.core.startup.AppConfig;
import com.by.core.util.BeanUtil;
import com.by.core.util.HttpHelper;
import com.by.core.util.Md5Utils;
import com.by.core.util.Page;
import com.by.core.util.StringUtils;
import com.by.manage.wechat.dto.mapper.TCustLoginInfoDtoMapper;
import com.by.wechat.util.WeChatSessionUtil;

/**
 * 注册
 * Created by yiqr on 2017/6/5.
 */
@Service
public class WeChatRegisterServiceImpl implements IWeChatRegisterService {
    @Autowired
    private TCustLoginInfoMapper loginInfoMapper;
    @Autowired
    private IWeChatCaptchaService captchaService;
    @Autowired
	private TCustLoginInfoDtoMapper custLoginInfoDtoMapper;

    @Override
    public Page<?> register(Page<TCustLoginInfo> page,HttpSession session) {
        if (null == page){
            throw new BusinessException("参数不合法","100002");
        }
        Map<String, Object> params = page.getParams();
        final String moblNo = (String)params.get("moblNo");
        final String password = (String)params.get("password");
        String checkTyp = (String)params.get("checkTyp");
        String checkReslt = (String)params.get("checkReslt");
        String strAgreMemo = (String)params.get("agreMemo");//获取协议内容
        String agreMemo = strAgreMemo.replaceAll("  ", "");//去除内容中的空格
        System.out.println(agreMemo);
        if (StringUtils.isEmpty(moblNo)
                ||StringUtils.isEmpty(password)
                ||StringUtils.isEmpty(checkTyp)
                ||StringUtils.isEmpty(checkReslt)){
            page.setSuccess(false);
            page.setMsg("参数不合法");
            return page;
        }
        //检查用户是否已经注册
        Map<String,Object> loginMap = new HashMap<>();
        loginMap.put("custNo", moblNo);
        loginMap.put("status", Constants.STAT_ENABLE);
        TCustLoginInfo custLoginInfo = loginInfoMapper.get(loginMap);
        if (null != custLoginInfo){
            page.setSuccess(false);
            page.setMsg("该手机号已经注册过了");
            return page;
        }
        
        //检查用户是否已经销户
        loginMap.remove("status");
        loginMap.put("status", Constants.STAT_DISABLE);
        TCustLoginInfo tcustLoginInfo = loginInfoMapper.get(loginMap);
        if (null != tcustLoginInfo){
            page.setSuccess(false);
            page.setMsg("该手机号已经销户，不能再次注册");
            return page;
        }
        //检查验证码
        Map<String, Object> verify = captchaService.verify(moblNo, checkTyp, checkReslt);
        if (verify == null){
            page.setSuccess(false);
            page.setMsg("验证码验证失败");
            return page;
        }
        Boolean isOk = (Boolean) verify.get("isOk");
        String msg = (String)verify.get("msg");
        if (isOk.booleanValue() == false){
            page.setSuccess(false);
            page.setMsg(msg);
            return page;
        }
        //注册
        custLoginInfo = new TCustLoginInfo();
        custLoginInfo.setId(StringUtils.getUUID());
        custLoginInfo.setCustNo(moblNo);
        custLoginInfo.setCustPwd(Md5Utils.md5(password));
        custLoginInfo.setStatus(Constants.STAT_ENABLE);
        custLoginInfo.setType(AppConfig.get("mobile_check_wechat"));
        custLoginInfo.setCreateDate(new Date());
        custLoginInfo.setAgreFlag("13900001");
        custLoginInfo.setAgreMemo(agreMemo);
        String openId =  "";
        if(WeChatSessionUtil.getCustLoginInfo() != null){
        	openId =  WeChatSessionUtil.getCustLoginInfo().getOpenId();
        }else {
        	if (WeChatSessionUtil.getCode() != null) {
        		//查询参数
                Map<String, String> maps=new HashMap<>();
                maps.put("appid", AppConfig.get("wx_appid"));
                maps.put("secret", AppConfig.get("wx_appsecret"));
                maps.put("code", WeChatSessionUtil.getCode());
                maps.put("grant_type", "authorization_code");
                //httpclient 获取openid
                Map<String, Object> requestByGet = HttpHelper.requestByGet("https://api.weixin.qq.com/sns/oauth2/access_token", maps, null);
                Map<String,String> resultMap = JSON.parseObject(
        				StringUtils.replaceBlank(String.valueOf(requestByGet.get(HttpHelper.RESP_STR))),
        				new TypeReference<Map<String,String>>(){});
                openId =  resultMap.get("openid");
			}
		}
        
        if(StringUtils.isNotEmpty(openId)){
            custLoginInfo.setOpenId(openId);
        }
        loginInfoMapper.insert(custLoginInfo);
        WeChatSessionUtil.setCustLoginInfo(custLoginInfo);
        //同步满得利APP  
        //callMdlRegister(moblNo, password, openId);
        //暂时停止和外快侠信息同步
		page.setSuccess(true);
        page.setMsg("注册成功");
		
        return page;
    }

    private void callMdlRegister(String phoneNo, String password,String openId){
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("phoneNo", phoneNo);
        map.put("password", password);
        map.put("openId", openId);
        String jsonStr = JSON.toJSONString(map);
        HttpHelper.postJson(AppConfig.get("mdl.regist"), jsonStr);
    }
    
	@Override
	public Page<?> mdlRegister(Map<String, Object> map) {
		TCustLoginInfo custLoginInfo = new TCustLoginInfo();
		BeanUtil.transMap2Bean(map, custLoginInfo);
		Page<?> page = new Page<TCustLoginInfo>();
        String moblNo = custLoginInfo.getCustNo();
        String password = custLoginInfo.getCustPwd();
        String imei = custLoginInfo.getImei();
        if (StringUtils.isEmpty(moblNo)
            ||StringUtils.isEmpty(password)){
        	page.setSuccess(false);
            page.setMsg("参数不合法");
            return page;
        }
        //检查用户是否已经注册
        Map<String,Object> loginMap = new HashMap<>();
        loginMap.put("custNo", moblNo);
        loginMap.put("status", Constants.STAT_ENABLE);
        custLoginInfo = loginInfoMapper.get(loginMap);
        if (null != custLoginInfo){
            page.setSuccess(false);
            page.setMsg("该手机号已经注册过了");
            return page;
        }
        
        //注册
        custLoginInfo = new TCustLoginInfo();
        custLoginInfo.setId(StringUtils.getUUID());
        custLoginInfo.setCustNo(moblNo);
        custLoginInfo.setCustPwd(Md5Utils.md5(password));
        custLoginInfo.setStatus(Constants.STAT_ENABLE);
        custLoginInfo.setType(AppConfig.get("mobile_check_wechat"));
        custLoginInfo.setImei(imei);
        custLoginInfo.setCreateDate(new Date());
        String openId =  "";
        if(WeChatSessionUtil.getCustLoginInfo() != null){
        	openId =  WeChatSessionUtil.getCustLoginInfo().getOpenId();
        }
        
        if(StringUtils.isNotEmpty(openId)){
            custLoginInfo.setOpenId(openId);
        }
        loginInfoMapper.insert(custLoginInfo);
        WeChatSessionUtil.setCustLoginInfo(custLoginInfo);
        
        page.setSuccess(true);
        page.setMsg("注册成功");
        return page;
	}

	@Override
	public Page<?> mdlModifyInfo(Map<String, Object> map) {
		TCustLoginInfo custLoginInfo = new TCustLoginInfo();
		BeanUtil.transMap2Bean(map, custLoginInfo);
		Page<?> page = new Page<TCustLoginInfo>();
		if (map.get("status").equals(Constants.YON_Y)){
			custLoginInfo.setStatus(Constants.STAT_ENABLE);
        }else{
        	custLoginInfo.setStatus(Constants.STAT_DISABLE);
        }
		custLoginInfo.setCustPwd(Md5Utils.md5((String) map.get("custPwd")));
        custLoginInfoDtoMapper.updateInfoByCustNo(BeanUtil.transBean2Map(custLoginInfo));
        
        page.setSuccess(true);
        page.setMsg("修改状态成功");
        return page;
        
	}
}

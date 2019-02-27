package com.by.bussiness.wechat.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.by.bussiness.wechat.mapper.TCustLoginInfoMapper;
import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.bussiness.wechat.service.IWeChatCaptchaService;
import com.by.bussiness.wechat.service.IWeChatPasswordService;
import com.by.core.exception.BusinessException;
import com.by.core.startup.AppConfig;
import com.by.core.util.BeanUtil;
import com.by.core.util.HttpHelper;
import com.by.core.util.Md5Utils;
import com.by.core.util.Page;
import com.by.core.util.StringUtils;

/**
 * Created by yiqr on 2017/6/8.
 */
@Service
public class WeChatPasswordServiceImpl implements IWeChatPasswordService {

    @Autowired
    private TCustLoginInfoMapper loginInfoMapper;
    @Autowired
    private IWeChatCaptchaService captchaService;

    @Override
    public Page<?> firstSave(Page<Object> page) {
        if(null == page){
            throw new BusinessException("参数不合法","100002");
        }
        Map<String, Object> params = page.getParams();
        String moblNo = (String)params.get("moblNo");
        String checkTyp = (String)params.get("checkTyp");
        String checkReslt = (String)params.get("checkReslt");
        if (StringUtils.isEmpty(moblNo)
                ||StringUtils.isEmpty(checkTyp)
                ||StringUtils.isEmpty(checkReslt)){
            page.setSuccess(false);
            page.setMsg("参数不合法");
            return page;
        }
        //检查用户是否已经注册
        Map<String,Object> loginMap = new HashMap<>();
        loginMap.put("custNo", moblNo);
        TCustLoginInfo custLoginInfo = loginInfoMapper.get(loginMap);
        if (null == custLoginInfo){
            page.setSuccess(false);
            page.setMsg("该手机号尚未注册");
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
        page.setSuccess(true);
        page.setMsg("验证成功");
        return page;
    }

    @Override
    public Page<?> secondSave(Page<Object> page) {
        if(null == page){
            throw new BusinessException("参数不合法","100002");
        }
        Map<String, Object> params = page.getParams();
        String phoneNo = (String)params.get("phoneNo");
        String password = (String)params.get("password");
        String rePassword = (String)params.get("rePassword");
        if (StringUtils.isEmpty(phoneNo)
                ||StringUtils.isEmpty(password)
                ||StringUtils.isEmpty(rePassword)){
            page.setSuccess(false);
            page.setMsg("参数不合法");
            return page;
        }
        if (!password.equals(rePassword)){
            page.setSuccess(false);
            page.setMsg("密码不一致");
            return page;
        }
        Map<String,Object> loginMap = new HashMap<>();
        loginMap.put("custNo", phoneNo);
        TCustLoginInfo custLoginInfo = loginInfoMapper.get(loginMap);
        if (null == custLoginInfo){
            page.setSuccess(false);
            page.setMsg("该手机号尚未注册");
            return page;
        }
        custLoginInfo.setCustPwd(Md5Utils.md5(password));
        custLoginInfo.setModifyDate(new Date());
        loginInfoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(custLoginInfo));
        
        //调满得利修改密码接口实现信息同步
        /*Map<String, Object> map = new HashMap<String, Object>();
        map.put("custNo", phoneNo);
        map.put("rePassword", rePassword);
        String jsonStr = JSON.toJSONString(map);
        HttpHelper.postJson(AppConfig.get("mdl.modify.password"), jsonStr);*/
        //暂时停止和外快侠信息同步
        
        page.setSuccess(true);
        page.setMsg("密码修改成功");
        return page;
    }

	@Override
	public Page<?> mdlSave(Map<String, Object> map) {
		TCustLoginInfo info = new TCustLoginInfo();
		BeanUtil.transMap2Bean(map, info);
		Page<?> page = new Page<TCustLoginInfo>();
        String phoneNo = info.getCustNo();
        String password = info.getCustPwd();
        if (StringUtils.isEmpty(phoneNo)
                ||StringUtils.isEmpty(password)){
        	page.setSuccess(false);
            page.setMsg("参数不合法");
            return page;
        }
        Map<String,Object> loginMap = new HashMap<>();
        loginMap.put("custNo", phoneNo);
        TCustLoginInfo custLoginInfo = loginInfoMapper.get(loginMap);
        if (null == custLoginInfo){
        	page.setSuccess(false);
            page.setMsg("该手机号尚未注册");
            return page;
        }
        custLoginInfo.setCustPwd(Md5Utils.md5(password));
        custLoginInfo.setModifyDate(new Date());
        loginInfoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(custLoginInfo));
        
        page.setSuccess(true);
        page.setMsg("密码修改成功");
        return page;
	}
}

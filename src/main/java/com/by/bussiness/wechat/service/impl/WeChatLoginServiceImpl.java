package com.by.bussiness.wechat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.mapper.TCustLoginInfoMapper;
import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.bussiness.wechat.service.IWeChatLoginService;
import com.by.core.exception.BusinessException;
import com.by.core.util.BeanUtil;
import com.by.core.util.Md5Utils;
import com.by.core.util.Page;
import com.by.core.util.StringUtils;
import com.by.wechat.util.WeChatSessionUtil;

/**
 * 登录
 * Created by yiqr on 2017/6/2.
 */
@Service
public class WeChatLoginServiceImpl implements IWeChatLoginService {

    @Autowired
    private TCustLoginInfoMapper loginInfoMapper;
    @Autowired
    private TCustLoginInfoMapper custLoginInfoMapper;

    @Override
    public Page<?> login(Page<TCustLoginInfo> page) {
        if (page == null
                ){
            throw new BusinessException("参数不合法","100002");
        }
        Map<String, Object> params = page.getParams();
        String moblNo = (String)params.get("moblNo");
        String password = (String)params.get("password");
        if (StringUtils.isEmpty(moblNo)
                ||StringUtils.isEmpty(password)){
            page.setSuccess(false);
            page.setMsg("参数不合法");
            return page;
        }
        //检查用户是否已经注册
        Map<String,Object> loginMap = new HashMap<>();
        loginMap.put("custNo", moblNo);
        // loginMap.put("custPwd", password);
        TCustLoginInfo custLoginInfo = loginInfoMapper.get(loginMap);
        if (null == custLoginInfo){
            page.setSuccess(false);
            page.setMsg("该用户尚未注册");
            return page;
        }
        if (!Md5Utils.md5(password).equals(custLoginInfo.getCustPwd())){
            page.setSuccess(false);
            page.setMsg("用户名或密码不匹配");
            return page;
        }

        if (custLoginInfo.getStatus().equals(Constants.STAT_DISABLE)){
            page.setSuccess(true);
            page.setMsg("该用户已注销,如需启用请联系管理员");
            return page;
        }
        //如果用户openId更换  记录新的openID
        String openId = "";
        if(WeChatSessionUtil.getCustLoginInfo() != null){
        	openId =  WeChatSessionUtil.getCustLoginInfo().getOpenId();
        }
        if (StringUtils.isNotEmpty(openId)&&!openId.equals(custLoginInfo.getOpenId())) {
        	custLoginInfo.setOpenId(openId);
        	loginInfoMapper.updateByPrimaryKey(BeanUtil.transBean2Map(custLoginInfo));
		}
        
        TCustBaseInfo custBaseInfo = new TCustBaseInfo();
        custBaseInfo.setCustNo(moblNo);
        custBaseInfo.setPhoneNo(moblNo);
        WeChatSessionUtil.setCurrentWeChatCust(custBaseInfo);
        page.setSuccess(true);
        return page;
    }

    @Override
    public Page<TCustLoginInfo> avoidlogin(Page<TCustLoginInfo> page) {
        if (page == null
                ){
            throw new BusinessException("参数不合法","100002");
        }
        Map<String, Object> params = page.getParams();
        String openId = (String)params.get("openId");
        if (StringUtils.isEmpty(openId)){
            page.setSuccess(false);
            page.setMsg("参数不合法");
            return page;
        }
        //检查用户是否已经注册
        Map<String,Object> loginMap = new HashMap<>();
        loginMap.put("openId", openId);
        TCustLoginInfo custLoginInfo = loginInfoMapper.get(loginMap);
        if (null == custLoginInfo){
            page.setSuccess(false);
            page.setMsg("该用户尚未注册");
            return page;
        }
        if (custLoginInfo.getStatus().equals(Constants.STAT_DISABLE)) {
        	 page.setSuccess(false);
             page.setMsg("该用户登录状态异常");
             return page;
		}
        TCustBaseInfo custBaseInfo = new TCustBaseInfo();
        custBaseInfo.setCustNo(custLoginInfo.getCustNo());
        custBaseInfo.setPhoneNo(custLoginInfo.getCustNo());
        WeChatSessionUtil.setCurrentWeChatCust(custBaseInfo);
        page.setSuccess(true);
        page.setT(custLoginInfo);
        return page;
    }

	@Override
	public List<TCustLoginInfo> getList(String openId) {
		if (StringUtils.isEmpty(openId)) {
			throw new BusinessException("参数不合法","100002");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("openId", openId);
		return custLoginInfoMapper.getList(params );
	}
}

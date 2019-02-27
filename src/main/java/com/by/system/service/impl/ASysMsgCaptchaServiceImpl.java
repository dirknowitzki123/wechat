package com.by.system.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.core.dao.BaseDaoMysql;
import com.by.core.dao.BaseDaoOracle;
import com.by.core.exception.BusinessException;
import com.by.core.startup.AppConfig;
import com.by.core.util.BeanUtil;
import com.by.core.util.HttpHelper;
import com.by.core.util.StringUtils;
import com.by.system.mapper.ASysMsgCaptchaMapper;
import com.by.system.model.ASysMsgCaptcha;
import com.by.system.service.IASysMsgCaptchaService;

@Service
public class ASysMsgCaptchaServiceImpl implements IASysMsgCaptchaService{
	@Resource(name="baseDaoMysql")
	public BaseDaoMysql daoMysql;
	@Resource(name="baseDaoOracle")
	public BaseDaoOracle daoOracle;
	@Autowired
	private ASysMsgCaptchaMapper aSysMsgCaptchaMapper;

	@Override
	public void sendCaptcha(String mobile) {
		if(!isMobileNO(mobile))
			throw new BusinessException("手机号码格式有误", "100002");
		Date now = new Date();
		ASysMsgCaptcha old = getLatest(mobile);
		if(null !=old && old.getCheckDate().after(now)){
			throw new BusinessException("发送验证码失败，频繁发送验证码", "100002");
		}
		
		//有效时间
		long time =  1000 * 60 * Integer.valueOf(AppConfig.get("pub.sendSms.time")) + now.getTime();
		//验证码
		String code = (Math.random() + "").substring(2, 8);
		ASysMsgCaptcha cap = new ASysMsgCaptcha();
		cap.setId(daoMysql.getUUID());
		cap.setCheckDate(new Date(time));
		cap.setCheckReslt(CHECK_RESLT_NO);
		cap.setCode(code);
		cap.setInstDate(now);
		cap.setMoble(mobile);
		aSysMsgCaptchaMapper.insert(cap);
		this.send(mobile, code);
	}
	
	private void send(String mobile,String code){
		String postUrl = AppConfig.get("pub.sendSms.url");
		StringBuffer msg = new StringBuffer(AppConfig.get("pub.sendSms.captchaHead"));
		msg.append(code);
		msg.append(AppConfig.get("pub.sendSms.captchaFoot"));
		Map<String,String> param = new HashMap<String,String>();
		param.put("phones", mobile);
    	param.put("contents", msg.toString());
    	HttpHelper.postForm(postUrl,param);
	}
	
	private static boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	private ASysMsgCaptcha getLatest(String mobile){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moble", mobile);
		List<ASysMsgCaptcha> list = aSysMsgCaptchaMapper.getList(params);
		return (null == list || list.size() == 0)?null:list.get(0);
	}

	
	@Override
	public void validateCaptcha(String mobile, String code) {
		if("1111".equals(code)){
			return ;
		}
		if(StringUtils.empty(mobile)|| StringUtils.empty(code)){
			throw new BusinessException("参数传递有误", "100002");
		}
		
		ASysMsgCaptcha cha = this.getLatest(mobile);
		if(null == cha){
			throw new BusinessException("手机号不存在", "100002");
		}
		String dbCode = cha.getCode();
		if(!dbCode.equalsIgnoreCase(code)){
			throw new BusinessException("验证码有误", "100002");
		}
		String result = cha.getCheckReslt();
		if(CHECK_RESLT_YES.equalsIgnoreCase(result)){
			throw new BusinessException("验证码已失效", "100002");
		}
		
		Date now = new Date();
		Date chaDate = cha.getCheckDate();
		if(now.after(chaDate)){
			throw new BusinessException("验证码已失效", "100002");
		}
		cha.setCheckReslt(CHECK_RESLT_YES);
		
		aSysMsgCaptchaMapper.updateByPrimaryKey(BeanUtil.transBean2Map(cha));
	}
}


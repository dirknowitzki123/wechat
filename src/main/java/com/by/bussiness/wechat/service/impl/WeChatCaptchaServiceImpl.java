package com.by.bussiness.wechat.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.by.bussiness.wechat.constants.Constants;
import com.by.bussiness.wechat.mapper.PubMsgCaptchaMapper;
import com.by.bussiness.wechat.mapper.PubMsgPermitMapper;
import com.by.bussiness.wechat.model.PubMsgCaptcha;
import com.by.bussiness.wechat.model.PubMsgPermit;
import com.by.bussiness.wechat.service.IWeChatCaptchaService;
import com.by.core.exception.BusinessException;
import com.by.core.startup.AppConfig;
import com.by.core.util.BeanUtil;
import com.by.core.util.HttpHelper;
import com.by.core.util.Page;
import com.by.core.util.StringUtils;

/**
 * 短信验证码
 * Created by yiqr on 2017/6/2.
 */
@Service
public class WeChatCaptchaServiceImpl implements IWeChatCaptchaService {
    private Log log = LogFactory.getLog(WeChatCaptchaServiceImpl.class);
    @Autowired
    private PubMsgPermitMapper permitMapper;
    @Autowired
    private PubMsgCaptchaMapper msgCaptchaMapper;

    @Override
    public Page<PubMsgCaptcha> send(Page<PubMsgCaptcha> page) {
        if (null == page){
            throw new BusinessException("参数不合法","100002");
        }
        Map<String, Object> pageParams = page.getParams();
        String moblNo =  (String)pageParams.get("moblNo"); //手机号
        String checkTyp =  (String)pageParams.get("checkTyp"); //短信类型 - 注册、修改密码
        if (StringUtils.isEmpty(moblNo)||StringUtils.isEmpty(checkTyp)){
            page.setSuccess(false);
            page.setMsg("参数不合法");
            return page;
        }
        if(!checkTyp.equals(Constants.MSG_TYPE_REGISTER) && !checkTyp.equals(Constants.MSG_TYPE_RESET_PASSWORDS)){
            page.setSuccess(false);
            page.setMsg("参数不合法");
            return page;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("moblNo",moblNo);
        params.put("checkTyp",checkTyp);

        //短信许可 - 是否黑名单
        PubMsgPermit pubMsgPermit = permitMapper.get(params);
        if (null != pubMsgPermit && pubMsgPermit.getBlacklist().equals(Constants.YON_Y)){
            page.setSuccess(false);
            page.setMsg("黑名单用户");
            log.error("=========>手机号:"+moblNo +"=>黑名单");
            return page;
        }
        //先查询moblNo是否已经发送过验证码(有效期内)
        Date now = new Date();
        PubMsgCaptcha pubMsgCaptcha = msgCaptchaMapper.get(params);
        if (pubMsgCaptcha != null && pubMsgCaptcha.getCheckDate().after(now)){
            page.setSuccess(false);
            page.setMsg("已经发送过验证码！");
            log.error("=========>手机号:"+moblNo +"=>已经发送过验证码！");
            return page;
        }
        //发送验证码
        Map<String,Object> msgMap=new HashMap<>();
        Map<String,Object> parameMap=new HashMap<>();
        Map<String,Object> msgMap3=new HashMap<>();
        Map<String,Object> parameter=new HashMap<>();
        //验证码
        String code = (Math.random() + "").substring(2, 8);
        String smsContent = AppConfig.get("sms_content").replace("code", code);
        parameter.put("message",AppConfig.get("sms_sign")+smsContent);
        msgMap3.put("parameter", parameter);
        msgMap3.put("phones",moblNo);
        msgMap3.put("smsType", "27600001");
        msgMap3.put("templateNo", AppConfig.get("sms_template_no"));
        parameMap.put("31400001", msgMap3);
        msgMap.put("msgType", "31400001");
        msgMap.put("parameMap", parameMap);
        HttpHelper.postJson(AppConfig.get("send_sms_url"), JSON.toJSONString(msgMap));

        //保存客户发送信息
        PubMsgCaptcha captcha = new PubMsgCaptcha();
        long time =  1000 * 60 * Integer.valueOf(AppConfig.get("captcha_date")) + now.getTime();
        captcha.setMoblNo(moblNo); //手机号
        captcha.setCheckDate(new Date(time)); //有效时间
        captcha.setCheckDesc(code); //验证码
        captcha.setCheckReslt("");  //未验证
        captcha.setCheckTyp(checkTyp); //类型
        if (pubMsgCaptcha == null){
            captcha.setCreateDate(now); //创建时间
            captcha.setId(StringUtils.getUUID());
            msgCaptchaMapper.insert(captcha);
        }else {
            captcha.setModifyDate(new Date());
            captcha.setId(pubMsgCaptcha.getId());
            captcha.setCreateDate(pubMsgCaptcha.getCreateDate());
            msgCaptchaMapper.updateByPrimaryKey(BeanUtil.transBean2Map(captcha));
        }
        page.setSuccess(true);
        return page;
    }

    @Override
    public Map<String,Object> verify(String moblNo,String checkTyp,String checkReslt) {
        Map<String,Object> reMap = new HashMap<>();
        if (StringUtils.isEmpty(moblNo)
                || StringUtils.isEmpty(checkTyp)
                || StringUtils.isEmpty(checkReslt)){
            //验证失败
            reMap.put("isOk",false);
            reMap.put("msg","参数不合法");
            return reMap;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("moblNo",moblNo);
        params.put("checkTyp",checkTyp);
        //短信许可 - 是否黑名单
        PubMsgPermit pubMsgPermit = permitMapper.get(params);
        if (null != pubMsgPermit && pubMsgPermit.getBlacklist().equals(Constants.YON_Y)){
            //黑名单用户
            reMap.put("isOk",false);
            reMap.put("msg","黑名单用户");
            log.error("=========>手机号:"+moblNo +"=>黑名单");
            return reMap;
        }
        //先查询moblNo是否已经发送过验证码(有效期内)
        Date now = new Date();
        msgCaptchaMapper.getList(params);
        PubMsgCaptcha pubMsgCaptcha = msgCaptchaMapper.get(params);
        if (null == pubMsgCaptcha){
            reMap.put("isOk",false);
            reMap.put("msg","尚未发送验证码，请先获取验证码");
            return reMap;
        }
        if(pubMsgCaptcha.getCheckDate().before(now)){
            reMap.put("isOk",false);
            reMap.put("msg","验证码已失效");
            return reMap;
        }
        if (!checkReslt.equals(pubMsgCaptcha.getCheckDesc())){
            reMap.put("isOk",false);
            reMap.put("msg","证码不匹配");
            return reMap;
        }
        pubMsgCaptcha.setCheckReslt(checkReslt);
        pubMsgCaptcha.setModifyDate(new Date());
        msgCaptchaMapper.updateByPrimaryKey(BeanUtil.transBean2Map(pubMsgCaptcha));
        reMap.put("isOk",true);
        reMap.put("msg","验证成功");
        return reMap;
    }

    public static void main(String[] args) {
//        SmsDto dto = new SmsDto();
//        dto.setTemplateNo("sms-no-tem");//模板编号
//        dto.setPhones("13086658775");//手机号,多个用逗号分隔
//        dto.setSmsType("27600001");
//        dto.setSender("有奖问卷");//发送人
//        Map<String,Object> m = new HashMap<>();
//        m.put("message","短信测试一下");
//        dto.setParameter(m);
//        Sms.getInstance().send(dto, AppConfig.get("send_sms_url")+"send");
        Map<String,Object> msgMap=new HashMap<String, Object>();
        Map<String,Object> parameMap=new HashMap<String, Object>();
        Map<String,Object> msgMap3=new HashMap<String, Object>();
        Map<String,Object> parameter=new HashMap<String, Object>();
        String code = (Math.random() + "").substring(2, 8);
        parameter.put("message","【博雅成信】"+"你的验证码为:"+code+"请不要告诉别人");
        msgMap3.put("parameter", parameter);
        msgMap3.put("phones","13086658775");
        msgMap3.put("smsType", "27600001");
        msgMap3.put("templateNo", "sms-no-tem");
        parameMap.put("31400001", msgMap3);
        msgMap.put("msgType", "31400001");
        msgMap.put("parameMap", parameMap);
        HttpHelper.postJson("http://114.55.62.180:7070/message/anon/send", JSON.toJSONString(msgMap));
    }
}

package com.by.bussiness.wechat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.by.bussiness.wechat.model.TCustLoginInfo;
import com.by.bussiness.wechat.service.IWeChatLoginService;
import com.by.core.startup.AppConfig;
import com.by.core.util.HttpHelper;
import com.by.core.util.Page;
import com.by.core.util.StringUtils;
import com.by.wechat.util.WeChatSessionUtil;

/**
 * 微信用户基本信息授权回调页面
 * 所有微信菜单wap必须经过此接口跳转（重要提示）
 * Created by yiqr on 2017/6/7.
 */
@Controller
@RequestMapping(value = "/authorize")
public class WeChatAuthorizeController {
    private Log logger = LogFactory.getLog(WeChatAuthorizeController.class);
    @Autowired
    private IWeChatLoginService loginService;

    /**
     * 系统暂时停用,点击菜单直接进入到提示用户系统升级的页面
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value = "/wechat/auth")
    public String stopUse(HttpServletRequest request, HttpServletResponse response){
        return "/wechat/tips/tips.index";
    }
    
    
    /**
     * 微信菜单用户授权控制
     * @param request
     * @param session
     * @param response
     * @return
     */
	@RequestMapping(value = "/wechat/auth2")
    public String authorize(HttpServletRequest request, HttpSession session,
                            HttpServletResponse response,Model model){
        String code = request.getParameter("code");//获取授权code代码
        System.out.println("#########################code="+code+"#########################");
        if (!StringUtils.isEmpty(code)) {
            WeChatSessionUtil.setCode(code);
        }
        String openid = null;//微信用户关联公众号openId
        System.out.println("================================ code="+WeChatSessionUtil.getCode()+"  =====================================");
        try{
            //判断是否存在openid
            openid = WeChatSessionUtil.getCurrentOpenId();
        }catch (Exception e) {
            logger.debug("#############session已失效，重新获取openid");
        }
        if(StringUtils.isEmpty(openid)){
            //不存在获取openid
            if(StringUtils.isEmpty(openid)){
                //查询参数
                Map<String, String> maps=new HashMap<>();
                maps.put("appid", AppConfig.get("wx_appid"));
                maps.put("secret", AppConfig.get("wx_appsecret"));
                maps.put("code", code);
                maps.put("grant_type", "authorization_code");
                //httpclient 获取openid
                Map<String, Object> requestByGet = HttpHelper.requestByGet("https://api.weixin.qq.com/sns/oauth2/access_token", maps, null);
                Map<String,String> resultMap = JSON.parseObject(
        				StringUtils.replaceBlank(String.valueOf(requestByGet.get(HttpHelper.RESP_STR))),
        				new TypeReference<Map<String,String>>(){});
                openid =  resultMap.get("openid");
                
                System.out.println("#########################openid="+openid+"#########################");
            }
        }
        if (StringUtils.isNotEmpty(openid)){
            session.setAttribute(WeChatSessionUtil.USER_OPENID, openid);
            //一个微信多注册 --- 登录
            List<TCustLoginInfo> list = loginService.getList(openid);
			if (list.size()>1) {
				return "redirect:"+"/wechat/login";	
			}
            Page<TCustLoginInfo> LogPage = new Page<>();
            LogPage.getParams().put("openId", openid);
            TCustLoginInfo custLoginInfo = new TCustLoginInfo();
            custLoginInfo.setOpenId(openid);
            Page<TCustLoginInfo> page = loginService.avoidlogin(LogPage);
            if (page.getSuccess() && page.getT() != null) {
                TCustLoginInfo loginInfo =  page.getT();
                loginInfo.setOpenId(openid);
                WeChatSessionUtil.setCustLoginInfo(loginInfo);
                if (StringUtils.isNotEmpty(loginInfo.getId())) {
                    return "redirect:"+"/wechat/home";
                }
            }
            WeChatSessionUtil.setCustLoginInfo(custLoginInfo);
        } 
        //取消自动登录功能
        return "redirect:"+"/wechat/login";
    }
}

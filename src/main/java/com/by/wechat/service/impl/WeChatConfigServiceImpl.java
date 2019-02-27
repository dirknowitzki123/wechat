package com.by.wechat.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.by.core.util.StringUtils;
import com.by.wechat.config.WeChatConfig;
import com.by.wechat.service.IWeChatConfigService;
import com.by.wechat.util.WeChatHttpUtil;

/**
 * Created by yiqr on 2017/6/12.
 */
@Service
public class WeChatConfigServiceImpl implements IWeChatConfigService {
    private Log log = LogFactory.getLog(WeChatConfigServiceImpl.class);

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public String getToken() {
        Map<String,String> reMap = new HashMap<String, String>();
        //获取access_token
        String accessTokenUrl = ACCESS_TOKEN_URL
                .replace("APPID", WeChatConfig.getAppid())
                .replace("APPSECRET", WeChatConfig.getAppsecret());

        String accessTokenStr = WeChatHttpUtil.get(accessTokenUrl);
        if(StringUtils.isNotEmpty(accessTokenStr)){
            JSONObject accessTokenJson = JSON.parseObject(accessTokenStr);
            String accessToken = accessTokenJson.getString("access_token");
            WeChatConfig.setAccessToken(accessToken);
            reMap.put("accessToken",accessToken);
        }
        //获取api_ticket
        String apiTicketUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", WeChatConfig.getAccessToken());
        String apiTicketStr = WeChatHttpUtil.get(apiTicketUrl);
        if(StringUtils.isNotEmpty(apiTicketStr)){
            JSONObject apiTicketJson = JSON.parseObject(accessTokenStr);
            String jsapiTicket = apiTicketJson.getString("ticket");
            WeChatConfig.setJsapiTicket(jsapiTicket);
            reMap.put("jsapiTicket",jsapiTicket);
        }
        log.info("###TOKEN:" + WeChatConfig.getToken());
        log.info("###ACCESS_TOKEN:" + WeChatConfig.getAccessToken());
        log.info("###JSAPI_TICKET:" + WeChatConfig.getJsapiTicket());
        return JSON.toJSONString(reMap);
    }

    /**
     * 定时任务获取oken
     */
    @Scheduled(fixedRate = 1000*60*60*2)
    public void timingToken(){
        getToken();
    }

	public String query() {
		Map<String, String> reMap = new HashMap<String, String>();
		reMap.put("accessToken",WeChatConfig.getAccessToken());
		reMap.put("jsapiTicket",WeChatConfig.getJsapiTicket());
		return JSON.toJSONString(reMap);
	}

}

package com.by.wechat.config;

import com.by.core.startup.AppConfig;

/**
 * 微信公众号配置信息
 * Created by yiqr on 2017/6/12.
 */
public class WeChatConfig {
    /* 可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性） */
    private static String token;
    /* 应用编号  */
    private static String appid;
    /* 应用密匙 */
    private static String appsecret;
    /*
     * 是公众号的全局唯一票据，公众号调用各接口时都需使用access_token
     * 有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效
     */
    private static String accessToken;
    /*
     * 公众号用于调用微信JS接口的临时票据
     * jsapi_ticket的有效期为7200秒，通过access_token来获取
     */
    private static String jsapiTicket;

    static {
        token = AppConfig.get("wx_token");
        appid = AppConfig.get("wx_appid");
        appsecret = AppConfig.get("wx_appsecret");
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        WeChatConfig.token = token;
    }

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        WeChatConfig.appid = appid;
    }

    public static String getAppsecret() {
        return appsecret;
    }

    public static void setAppsecret(String appsecret) {
        WeChatConfig.appsecret = appsecret;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        WeChatConfig.accessToken = accessToken;
    }

    public static String getJsapiTicket() {
        return jsapiTicket;
    }

    public static void setJsapiTicket(String jsapiTicket) {
        WeChatConfig.jsapiTicket = jsapiTicket;
    }
}

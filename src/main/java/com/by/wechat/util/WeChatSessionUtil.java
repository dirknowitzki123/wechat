package com.by.wechat.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.by.bussiness.wechat.model.TCustBaseInfo;
import com.by.bussiness.wechat.model.TCustLoginInfo;

/**
 * Created by yiqr on 2017/6/7.
 */
public class WeChatSessionUtil {

    public final static String CURRENT_WECHAT_USER = "WECHAT_USER";
    public final static String CURRENT_WECHAT_LOGIN = "WECHAT_USER_LOGIN";
    public final static String CURRENT_WECHAT_CODE = "WECHAT_USER_CODE";

    public final static String USER_SESSION = "LOGIN_USER";
    public final static String USER_OPENID = "USER_OPENID";
    public final static String USER_SMSCODE= "USER_SMSCODE";
    public final static String USER_CUST_TYP = "USER_CUST_TYP";
    /*用户门店扫描信息*/
    public final static String USER_SCAN_STORE = "USER_SCAN_STORE";

    public final static String MAP_LOCATION_LATITUDE = "MAP_LOCATION_LATITUDE";
    public final static String MAP_LOCATION_LONGITUDE = "MAP_LOCATION_LONGITUDE";
    public final static String MAP_LOCATION_PRECISION = "MAP_LOCATION_Precision";
    public final static String MAP_LOCATION_TIME = "MAP_LOCATION_TIME";
//    /**
//     * 设置session
//     * @param key
//     * @param value
//     */
//    public static void put(String key, Object value) {
//        ContextUtil.put(key, value, ContextUtil.SCOPE_SESSION);
//    }
//
//    /**
//     * 设置session 微信openid
//     * @param openId
//     */
//    public static void setOpenId(String openId) {
//        ContextUtil.put(WeChatSessionUtil.USER_OPENID, openId, ContextUtil.SCOPE_SESSION);
//    }

    /**
     * 获取当前用户微信openID
     * @return
     */
    public static String getCurrentOpenId(){
        String openId = null;
        if(null != getCustLoginInfo()){
            openId = getCustLoginInfo().getOpenId();
        }
        return openId;
    }

    /**
     * 获取session  授权code代码
     * @return
     */
    public static String getCode(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String obj = (String)request.getSession().getAttribute(CURRENT_WECHAT_CODE);
        return obj;
    }


    /**
     * 设置session 授权code代码
     * @param code
     */
    public static void setCode(String code){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute(CURRENT_WECHAT_CODE, code);
    }

    /**
     * 获取当前微信用户
     * @return
     */
    public static TCustBaseInfo getCurrentWeChatCust(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Object obj = request.getSession().getAttribute(CURRENT_WECHAT_USER);
            if(obj==null){
                return null;
            }
            return (TCustBaseInfo)obj;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 设置当前微信用户
     * @param custBaseInfo
     */
    public static void setCurrentWeChatCust(TCustBaseInfo custBaseInfo){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute(CURRENT_WECHAT_USER, custBaseInfo);
    }

    /**
     * 设置当前微信登录用户信息
     * @param loginInfo
     */
    public static void setCustLoginInfo(TCustLoginInfo loginInfo){
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute(CURRENT_WECHAT_LOGIN, loginInfo);
    }

    /**
     * 获取当前微信登录用户信息
     * @return
     */
    public static TCustLoginInfo getCustLoginInfo(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Object obj = request.getSession().getAttribute(CURRENT_WECHAT_LOGIN);
            if(obj==null){
                return null;
            }
            return (TCustLoginInfo)obj;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

}

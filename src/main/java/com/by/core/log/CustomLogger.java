package com.by.core.log;

import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.by.core.util.DateUtils;

public class CustomLogger {
	/**
	 * 调用第三方接口输出日志
	 */
	//private static Logger thirdPartyServiceLogger=null;
	/**
	 * 业务操作日志输出
	 */
	private static Logger  busiOperaLogger = null;
	
	static {
        loadLogger();
    }

    public CustomLogger() {
        super();
    }

    /**
     * 装载系统使用的log
     */
    static void loadLogger() {
    	//thirdPartyServiceLogger = Logger.getLogger("thirdPartyServiceLogger");
    	busiOperaLogger = Logger.getLogger("busiOperaLogger");
    }
    
    //自定义的输出类型
    /*public static void thirdPartyServiceLogInfo(ThirdPartyServiceLog msg) {
    	msg.setReqTime(DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		thirdPartyServiceLogger.info(JSON.toJSONString(msg,true));
    }*/
    
    public static void busiOperaLogInfo(BusiOperaLog busiOperaLog) {
		busiOperaLog.setRespTime(DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		busiOperaLogger.info(JSON.toJSONString(busiOperaLog,true));
    }
    
    public static void busiOperaLogInfo(BusiOperaLog busiOperaLog, Throwable throwable) {
    	busiOperaLog.setRespTime(DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
    	if(throwable != null ){
    		busiOperaLogger.error(JSON.toJSONString(busiOperaLog,true), throwable);
    	}else{
    		busiOperaLogger.info(JSON.toJSONString(busiOperaLog,true));
    	}
    }
    
}

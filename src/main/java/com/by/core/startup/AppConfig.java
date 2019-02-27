package com.by.core.startup;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class AppConfig {
	private Properties prop;
	private static AppConfig appConfig;
	
	private static AppConfig getAppConfig(){
		if(appConfig==null){
			Properties properties = new Properties();
			try {
				properties.load(new InputStreamReader(AppConfig.class.getClassLoader().getResourceAsStream("config/app/app.properties"), "UTF-8")); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			appConfig=new AppConfig();
			appConfig.prop=properties;
		}
		return appConfig;
	}
	public static void init(){
		getAppConfig();
	}
	public static String get(String key){
		return getAppConfig().prop.getProperty(key);
	}
	public static Boolean getBoolean(String key){
		return Boolean.valueOf(get(key));
	}
	public static Integer getInteger(String key){
		return Integer.valueOf(get(key));
	}
}

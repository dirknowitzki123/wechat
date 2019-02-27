package com.by.core.startup;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.by.core.util.ApplicationUtil;
public class ContextInit extends ContextLoaderListener {
	protected static final Log logger = LogFactory.getLog(ContextInit.class);
	private static ApplicationContext context;

	public ContextInit() {}
	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext ctx) {
		context = ctx;
	}

	public void contextInitialized(ServletContextEvent context) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context.getServletContext());
		ContextInit.setContext(webApplicationContext);
		AppConfig.init();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("system.code", AppConfig.get( "system.code" ) );
		map.put("system.name", AppConfig.get( "system.name" ) );
		map.put("system.version", AppConfig.get( "system.version" ) );
		map.put("system.site", AppConfig.get( "system.site" ) );
		map.put("system.url.login", AppConfig.get( "system.url.login" ) );
		map.put("system.url.protal", AppConfig.get( "system.url.protal" ) );
		map.put("system.url.homepage", AppConfig.get( "system.url.homepage" ) );
		
		context.getServletContext().setAttribute( "config", map );
		
		ApplicationUtil.setModules(AppConfig.get("system.modules"));
	}

	public void contextDestroyed(ServletContextEvent servlet) {
		try {
			logger.info("Release base data object successfully!");
		} catch (Exception localException) {
		}
	}
}

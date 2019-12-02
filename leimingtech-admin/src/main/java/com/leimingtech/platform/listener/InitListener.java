package com.leimingtech.platform.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.service.SystemService;

/**
 * 系统初始化监听器,在系统启动时运行,进行一些初始化工作
 * 
 * @author laien
 * 
 */
public class InitListener implements javax.servlet.ServletContextListener {
	
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
	
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		SystemService systemService = (SystemService)webApplicationContext.getBean("systemService");
		Globals.CONTEXTPATH = event.getServletContext().getContextPath();
		// MenuInitService menuInitService = (MenuInitService)
		// webApplicationContext.getBean("menuInitService");
		
		/**
		 * 第一部分：对数据字典进行缓存
		 */
		systemService.initAllTypeGroups();
		
		/**
		 * 对平台配置进行缓存 2014-05-16 add by linjm
		 */
		systemService.initAllPfConfig();
		// 装载所有内容抓取规则
		systemService.loadContentExtractorule();
		// 装载所有微信接口参数
		systemService.loadWechatConfig();
		
		/**
		 * 第二部分：自动加载新增菜单和菜单操作权限
		 * 说明：只会添加，不会删除（添加在代码层配置，但是在数据库层未配置的）
		 * 不用了20140526
		 */
		// if("true".equals(ResourceUtil.getConfigByName("auto.scan.menu.flag").toLowerCase())){
		// menuInitService.initMenu();
		// }
		
	}
	
}

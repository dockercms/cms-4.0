package com.leimingtech.platform.controller;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;



/**
 * 数据库管理
 * 
 * @author 
 * 
 */
@Controller
@RequestMapping("/dataSourceController")
public class DataSourceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DataSourceController.class);

	/**
	 * 跳转到连接池监控页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "goDruid")
	public ModelAndView goDruid() {
		return new ModelAndView("/system/druid/index");
	}
	
	/**
	 * 系统信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "sysInfo")
	public ModelAndView sysInfo() {
		return new ModelAndView("lmPage/main/systeminfo");
	}

}

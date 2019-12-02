package com.leimingtech.platform.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;
/**   
 * @Title: Controller
 * @Description: 前台用户组
 * @author zhangdaihao
 * @date 2014-04-10 10:03:32
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/reportController")
public class ReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReportController.class);

	/**
	 * 快逸报表页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "quiee")
	public ModelAndView quiee(HttpServletRequest request) {
		return new ModelAndView("/lmPage/report/itemReport");
	}
	
}

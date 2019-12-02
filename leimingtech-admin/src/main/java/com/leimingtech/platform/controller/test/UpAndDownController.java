package com.leimingtech.platform.controller.test;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.SystemService;

/**   
 * @Title: Controller
 * @Description: 上传下载
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/upAndDownController")
public class UpAndDownController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UpAndDownController.class);

	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	@RequestMapping(params = "uploadPage")
	public ModelAndView upPage(HttpServletRequest reqeust){
		return new ModelAndView("lmPage/upanddown/upload_page");
	}
	
	
	/**
	 * 	
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		return new ModelAndView("lmPage/course/course_add_model");
	}
}

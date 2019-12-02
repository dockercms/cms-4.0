package com.leimingtech.platform.controller.demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;

/**
 * 演示例子
 * 
 * @author liuzhen 2014年7月9日 16:57:12
 * 
 */
@Controller
@RequestMapping("/demoAction")
public class DemoAction extends BaseController {

	/**
	 * webuploader上传演示
	 */
	@RequestMapping(params = "webuploader")
	public ModelAndView webuploader(HttpServletRequest request) {
		return new ModelAndView("platform/demo/webuploader");
	}
}

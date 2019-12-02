package com.leimingtech.platform.controller.uploader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;

/**
 * webuploader 上传
 * 
 * @author liuzhen 2014年7月14日 20:41:21
 * 
 */
@Controller
@RequestMapping("/webUploaderController")
public class WebUploaderController extends BaseController {
	
	
	@RequestMapping(params = "dialogWebUploader")
	public ModelAndView dialogWebUploader(HttpServletRequest request) {
		
		return new ModelAndView("platform/uploader/dialogWebUploader");
	}
}

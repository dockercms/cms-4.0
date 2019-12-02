package com.leimingtech.cms.controller.tag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;

/**
 * 模板演示
 * 
 * @author liuzhen 2014年4月29日 09:41:35
 * 
 */
@Controller
@RequestMapping("/templatePreViewAction")
public class TemplatePreViewAction extends BaseController {

	@RequestMapping(params = "index")
	public ModelAndView index(HttpServletRequest request) {
		Map<String, Object> m = request.getParameterMap();
		return new ModelAndView("cms/tag/index", m);
	}
	
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		Map<String, Object> m = request.getParameterMap();
		return new ModelAndView("cms/tag/list", m);
	}
	
	@RequestMapping(params = "detail")
	public ModelAndView detail(HttpServletRequest request) {
		Map<String, Object> m = request.getParameterMap();
		return new ModelAndView("cms/tag/detail", m);
	}
}

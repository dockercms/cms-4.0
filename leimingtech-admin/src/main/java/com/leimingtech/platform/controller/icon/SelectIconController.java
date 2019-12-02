package com.leimingtech.platform.controller.icon;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;

/**
 * 选择图标
 * 
 * @author liuzhen 2014年7月10日 17:31:18
 * 
 */
@Controller
@RequestMapping("/selectIconController")
public class SelectIconController extends BaseController {

	/**
	 * 显示图标列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "showicon")
	public ModelAndView showicon(HttpServletRequest request) {
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("selectIconBack", request.getParameter("selectIconBack"));
		return new ModelAndView("platform/icon/showicon",m);
	}
}

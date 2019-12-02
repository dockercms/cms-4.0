package com.leimingtech.cms.controller.preview;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;

/**
 * 模板预览
 * 
 * @author liuzhen 2014年4月18日 17:33:56
 * 
 */
@Controller
@RequestMapping("/previewController")
public class PreviewController extends BaseController {
	/**
	 * 模板演示页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "preview")
	public ModelAndView preview(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentid", 16);
//		try {
//			String html = CreateStaticHtml.preview();//生成静态并返回页面展示
//			m.put("html", html);
//		} catch (TemplateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return new ModelAndView("cms/preview/show", m);
	}
}

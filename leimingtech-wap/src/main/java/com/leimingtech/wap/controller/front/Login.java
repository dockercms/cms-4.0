package com.leimingtech.wap.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.util.PlatFormUtil;

@Controller
@RequestMapping("/front/login")
public class Login {
	@Autowired
	private CommonService commonService;
	/**
	 * 跳转至登陆页面
	 * @return
	 */
	@RequestMapping(params = "toLogin")
	public ModelAndView toLogin(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		String username= reqeust.getParameter("username");  //---注册成功后跳转到登陆页面---
		if(username!=null){
			m.put("username", username);
		}
		m.put("contextPath", reqeust.getContextPath());
		SiteEntity site =PlatFormUtil.getFrontSessionSite();
		
		return new ModelAndView("wwwroot/"+site.getSitePath()+"/template/wap/login/login", m);
	}
	/**
	 * 跳转至注册页面
	 * @return
	 */
	@RequestMapping(params = "toRegister")
	public ModelAndView toRegister(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contextPath", reqeust.getContextPath());
		SiteEntity site =PlatFormUtil.getFrontSessionSite();
		return new ModelAndView("wwwroot/"+site.getSitePath()+"/template/wap/login/register", m);
	}
	/**
	 * 跳转至--《搜索》--页面
	 * @return
	 */
	@RequestMapping(params = "toSearch")
	public ModelAndView toSearch(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contextPath", reqeust.getContextPath());
		SiteEntity site =PlatFormUtil.getFrontSessionSite();
		return new ModelAndView("wwwroot/"+site.getSitePath()+"/template/wap/login/search", m);
	}
	/**
	 * 跳转至--《爆料》--页面
	 * @return
	 */
	@RequestMapping(params = "toDisclose")
	public ModelAndView toDisclose(HttpServletRequest reqeust){
		
		Map<String, Object> m = new HashMap<String, Object>();
		List<ContentCatEntity> allOpenCatalog=commonService.findByQueryString("from ContentCatEntity where siteid='"+PlatFormUtil.getFrontSessionSiteId()+"' and iscontribute=1");
		m.put("allOpenCatalog",allOpenCatalog);
		m.put("contextPath", reqeust.getContextPath());
		SiteEntity site =PlatFormUtil.getFrontSessionSite();
		return new ModelAndView("wwwroot/"+site.getSitePath()+"/template/wap/login/disclose", m);
	}
	/**
	 * 跳转至--《文章评论》--页面
	 * @return
	 */
	@RequestMapping(params = "toComment")
	public ModelAndView toComment(HttpServletRequest reqeust){
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contextPath", reqeust.getContextPath());
		m.put("contentsMobileId", reqeust.getParameter("contentsMobileId"));
		SiteEntity site =PlatFormUtil.getFrontSessionSite();
		return new ModelAndView("wwwroot/"+site.getSitePath()+"/template/wap/login/comment", m);
	}
}

package com.leimingtech.cms.controller.dynamic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatDefault;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;

@Controller
@RequestMapping("/front/homePageController")
public class HomePageController extends BaseController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HomePageController.class);
	@Autowired
	private SystemService systemService;
	private String message;
	@Autowired
	private CommonService commonService;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 首页模板
	 * @throws IOException 
	 */

	@RequestMapping(params = "homePage")
	public ModelAndView homePage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//首页模板
		String siteId =request.getParameter("siteId");
		Map<String, Object> m = new HashMap<String, Object>();
		SiteEntity site =commonService.getEntity(SiteEntity.class, siteId);
		if(site.getIsSwitch().equals("0")){
			response.sendRedirect("http://"+site.getDomain());
			return null;
		}else{
			List<String> list = (List<String>)request.getSession().getAttribute("channel");
			if(list==null){
				List<String> defaultList = new ArrayList<String>();
				List<ContentCatDefault> listcat = commonService.getList(ContentCatDefault.class);
				if(listcat.size()!=0){
					for(ContentCatDefault cat :listcat){
						defaultList.add(cat.getChannelId());
					}
				}
				request.getSession().setAttribute("channel",defaultList);
			}

		}	
		
		String templatePath=site.getIndexTemplate();
		String fileName[] = templatePath.split("\\.");
		
		String sitePath = site.getSitePath();
		m.put("sitePath", sitePath);
		m.put("site",site);
		return new ModelAndView("wwwroot/" + sitePath + "/template/"
				+ fileName[0], m);
	}
}

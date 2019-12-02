package com.leimingtech.mobile.controller.relatecontent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.entity.relatecontent.RelateContentMobileEntity;
import com.leimingtech.mobile.service.relatecontent.RelateContentMobileServiceI;

/**   
 * @Title: Controller
 * @Description: 相关内容
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-13 15:08:12
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/relateContentMobileController")
public class RelateContentMobileController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RelateContentMobileController.class);

	@Autowired
	private RelateContentMobileServiceI relateContentMobileService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 相关内容列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(RelateContentMobileEntity relateContentMobile, HttpServletRequest request) {
		//获取相关内容列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		//查询条件组装器
		Map param = request.getParameterMap();
		Map<String,Object> m=this.relateContentMobileService.getPageList(relateContentMobile, param, pageSize, pageNo);
		
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "relateContentController.do?table");
		return new ModelAndView("mobile/relatecontent/relateContentList", m);
	}

	/**
	 * 相关内容添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest request){
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//内容Id
		String contentId = request.getParameter("contentId");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new RelateContentMobileEntity());
		m.put("temporary", temporary);
		m.put("contentsId", contentId);
		return new ModelAndView("mobile/relatecontent/relateContent", m);
	}
	
	/**
	 * 相关内容更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest request){
		String id = request.getParameter("id");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//内容Id
		String contentId = request.getParameter("contentId");
		RelateContentMobileEntity relateContent = relateContentMobileService.getEntity(id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", relateContent);
		m.put("temporary", temporary);
		m.put("contentsId", contentId);
		return new ModelAndView("mobile/relatecontent/relateContent", m);
	}

	/**
	 * 相关内容保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(RelateContentMobileEntity relateContent, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//内容Id
		String contentId = request.getParameter("contentsId");
		if (StringUtil.isNotEmpty(relateContent.getId())) {

			RelateContentMobileEntity t = relateContentMobileService.getEntity(relateContent.getId());
			message = "相关内容["+relateContent.getRelateTitle()+"]更新成功";
			try {
				MyBeanUtils.copyBeanNotNull2Bean(relateContent, t);
				relateContentMobileService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "相关内容更新失败";
			}
		} else {
			message = "相关内容["+relateContent.getRelateTitle()+"]添加成功";
			relateContent.setCreated(new Date());
			relateContentMobileService.save(relateContent);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsMobileController.do?showDiv&checked=&temporary="+temporary+"&contentId="+contentId);
		String str = j.toString();
		return str;
	}
	/**
	 * 相关内容删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//内容Id
		String contentId = request.getParameter("contentId");
		RelateContentMobileEntity relateContent = relateContentMobileService.getEntity(id);
		message = "相关内容"+relateContent.getRelateTitle()+"删除成功";
		relateContentMobileService.delete(relateContent);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsMobileController.do?showDiv&checked=&temporary="+temporary+"&contentId="+contentId);
		String str = j.toString();
		return str;
	}
	
}

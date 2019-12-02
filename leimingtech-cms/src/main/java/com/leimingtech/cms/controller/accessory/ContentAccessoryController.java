package com.leimingtech.cms.controller.accessory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.service.accessory.ContentAccessoryServiceI;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.ContentAccessoryEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 文章附件表
 * @author 
 * @date 2014-08-20 09:58:36
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentAccessoryController")
public class ContentAccessoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ContentAccessoryController.class);

	@Autowired
	private ContentAccessoryServiceI contentAccessoryService;
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
	 * 文章附件表列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "contentAccessory")
	public ModelAndView contentAccessory(ContentAccessoryEntity contentAccessory, HttpServletRequest request) {
		//获取文章附件表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = this.contentAccessoryService.getPageList(contentAccessory,param,pageSize,pageNo );
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "contentAccessoryController.do?contentAccessory");
		return new ModelAndView("cms/accessory/contentAccessoryList", m);
	}

	/**
	 * 文章附件表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest request){
		String contentId = request.getParameter("contentId");
		////临时给附件中的id赋值为当前毫秒数
		String temporary = request.getParameter("temporary");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentAccessory", new ContentAccessoryEntity());
		m.put("temporary", temporary);
		m.put("contentId", contentId);
		return new ModelAndView("cms/accessory/contentAccessory", m);
	}
	
	/**
	 * 文章附件表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ContentAccessoryEntity contentAccessory = contentAccessoryService.getEntity(id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentAccessory", contentAccessory);
		return new ModelAndView("cms/accessory/contentAccessory", m);
	}

	/**
	 * 文章附件表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(ContentAccessoryEntity contentAccessory, HttpServletRequest request) {
		String contentId = request.getParameter("content_id");
		String temporary = request.getParameter("temporary");
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(contentAccessory.getId())) {
			message = "文章附件表【"+contentAccessory.getAccessoryName()+"】更新成功";
			ContentAccessoryEntity t = contentAccessoryService.getEntity(contentAccessory.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentAccessory, t);
				contentAccessoryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文章附件表【"+contentAccessory.getAccessoryName()+"】更新失败";
			}
		} else {
			message = "文章附件表【"+contentAccessory.getAccessoryName()+"】添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			contentAccessory.setSiteId(site.getId());
			contentAccessory.setCreatedtime(new Date());//创建时间
			contentAccessoryService.save(contentAccessory);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "articleController.do?loadAccessory&contentId="+contentId+"&temporary="+temporary);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 文章附件表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String accessoryId = request.getParameter("id");
		String contentId = request.getParameter("contentId");
		String temporary = request.getParameter("temporary");
		ContentAccessoryEntity contentAccessory = contentAccessoryService.getEntity(accessoryId);
		message = "文章附件表【"+contentAccessory.getAccessoryName()+"】删除成功";
		contentAccessoryService.delete(contentAccessory);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "articleController.do?loadAccessory&contentId="+contentId+"&temporary="+temporary);
		String str = j.toString();
		return str;
	}
}

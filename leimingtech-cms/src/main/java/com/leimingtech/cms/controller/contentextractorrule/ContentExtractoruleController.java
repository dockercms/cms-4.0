package com.leimingtech.cms.controller.contentextractorrule;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.service.contentextractorrule.ContentExtractoruleServiceI;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentExtractoruleEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;

/**   
 * @Title: Controller
 * @Description: 一键抓取规则管理
 * @author 
 * @date 2015-08-04 16:53:56
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentExtractoruleController")
public class ContentExtractoruleController extends BaseController {

	private String message;
	/** 一键抓取规则管理接口 */
	@Autowired
	private ContentExtractoruleServiceI contentExtractoruleService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 一键抓取规则管理列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "contentExtractorule")
	public ModelAndView contentExtractorule(ContentExtractoruleEntity contentExtractorule, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map<? , ?> param = request.getParameterMap();
		Map<String, Object> m = contentExtractoruleService.getPageList(contentExtractorule, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "contentExtractoruleController.do?contentExtractorule");
		return new ModelAndView("cms/contentextractorrule/contentExtractoruleList", m);
	}

	/**
	 * 一键抓取规则管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentExtractorule", new ContentExtractoruleEntity());
		return new ModelAndView("cms/contentextractorrule/contentExtractorule", m);
	}
	
	/**
	 * 一键抓取规则管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ContentExtractoruleEntity contentExtractorule = contentExtractoruleService.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentExtractorule", contentExtractorule);
		return new ModelAndView("cms/contentextractorrule/contentExtractorule", m);
	}

	/**
	 * 一键抓取规则管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ContentExtractoruleEntity contentExtractorule, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		
		if (StringUtils.isNotEmpty(contentExtractorule.getId())) {
			message = "一键抓取规则管理【" + contentExtractorule.getId() + "】更新成功";
			ContentExtractoruleEntity t = contentExtractoruleService.getEntity(contentExtractorule.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentExtractorule, t);
				contentExtractoruleService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				systemService.reloadContentExtractorule();
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "一键抓取规则管理【" + contentExtractorule.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "一键抓取规则管理【" + contentExtractorule.getId() + "】添加成功";
			contentExtractoruleService.save(contentExtractorule);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
			systemService.reloadContentExtractorule();
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentExtractoruleController.do?contentExtractorule");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 一键抓取规则管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ContentExtractoruleEntity contentExtractorule = contentExtractoruleService.getEntity(java.lang.String.valueOf(id));
		message = "一键抓取规则管理【" + contentExtractorule.getId() + "】删除成功";
		contentExtractoruleService.delete(contentExtractorule);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentExtractoruleController.do?contentExtractorule");
		String str = j.toString();
		this.systemService.reloadContentExtractorule();
		return str;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

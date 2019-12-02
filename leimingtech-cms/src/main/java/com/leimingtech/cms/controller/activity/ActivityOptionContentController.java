package com.leimingtech.cms.controller.activity;

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

import com.leimingtech.cms.service.activity.ActivityOptionContentServiceI;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ActivityOptionContentEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;

/**
 * @Title: Controller
 * @Description: 表单选项
 * @author
 * @date 2015-08-07 17:55:15
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/activityOptionContentController")
public class ActivityOptionContentController extends BaseController {

	private String message;
	/** 表单选项接口 */
	@Autowired
	private ActivityOptionContentServiceI activityOptionContentService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;

	/**
	 * 表单选项列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "activityOptionContent")
	public ModelAndView activityOptionContent(
			ActivityOptionContentEntity activityOptionContent,
			HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = activityOptionContentService.getPageList(
				activityOptionContent, param, pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl",
				"activityOptionContentController.do?activityOptionContent");
		return new ModelAndView("cms/activity/activityOptionContentList", m);
	}

	/**
	 * 表单选项添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityOptionContent", new ActivityOptionContentEntity());
		return new ModelAndView("cms/activity/activityOptionContent", m);
	}

	/**
	 * 表单选项更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		ActivityOptionContentEntity activityOptionContent = activityOptionContentService
				.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityOptionContent", activityOptionContent);
		return new ModelAndView("cms/activity/activityOptionContent", m);
	}

	/**
	 * 表单选项保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ActivityOptionContentEntity activityOptionContent,
			HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;

		if (StringUtils.isNotEmpty(activityOptionContent.getId())) {
			message = "表单选项【" + activityOptionContent.getId() + "】更新成功";
			ActivityOptionContentEntity t = activityOptionContentService
					.getEntity(activityOptionContent.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(activityOptionContent, t);
				activityOptionContentService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "表单选项【" + activityOptionContent.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "表单选项【" + activityOptionContent.getId() + "】添加成功";
			activityOptionContentService.save(activityOptionContent);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl",
				"activityOptionContentController.do?activityOptionContent");
		String str = j.toString();
		return str;
	}

	/**
	 * 表单选项删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		ActivityOptionContentEntity activityOptionContent = activityOptionContentService
				.getEntity(java.lang.String.valueOf(id));
		message = "表单选项【" + activityOptionContent.getId() + "】删除成功";
		activityOptionContentService.delete(activityOptionContent);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl",
				"activityOptionContentController.do?activityOptionContent");
		String str = j.toString();
		return str;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

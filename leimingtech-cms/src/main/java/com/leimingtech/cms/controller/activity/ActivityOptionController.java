package com.leimingtech.cms.controller.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.entity.activity.ActivityOptionEntity;
import com.leimingtech.cms.service.activity.ActivityOptionContentServiceI;
import com.leimingtech.cms.service.activity.ActivityOptionServiceI;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ActivityOptionContentEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;

/**
 * @Title: Controller
 * @Description: 活动报名选项内容
 * @author
 * @date 2015-08-07 17:56:10
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/activityOptionController")
public class ActivityOptionController extends BaseController {
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	private String message;
	/** 活动报名选项内容接口 */
	@Autowired
	private ActivityOptionServiceI activityOptionService;
	/** 表单选项接口 */
	@Autowired
	private ActivityOptionContentServiceI activityOptionContentService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;

	/**
	 * 活动报名选项内容列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "activityOption")
	public ModelAndView activityOption(ActivityOptionEntity activityOption,
			HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = activityOptionService.getPageList(
				activityOption, param, pageSize, pageNo);
		String activityids = request.getParameter("activityid");
		List<TSType> typelist = TSTypegroup.allTypes.get("survey_fieldtype");// 数据类型列表
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("activityids", activityids);
		m.put("pageSize", pageSize);
		m.put("typelist", typelist);
		m.put("actionUrl", "activityOptionController.do?activityOption");
		return new ModelAndView("cms/activity/activityOptionList", m);
	}

	/**
	 * 活动报名选项内容添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		List<TSType> typelist = TSTypegroup.allTypes.get("survey_fieldtype");// 数据类型列表
		List<TSType> validationlist = TSTypegroup.allTypes.get("validation");// 验证规则列表
		String activityids = reqeust.getParameter("activityid");
		m.put("typelist", typelist);
		m.put("activityids", activityids);
		m.put("validationlist", validationlist);
		m.put("activityOption", new ActivityOptionEntity());
		return new ModelAndView("cms/activity/activityOption", m);
	}

	/**
	 * 活动报名选项内容更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		ActivityOptionEntity activityOption = activityOptionService
				.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		List<TSType> typelist = TSTypegroup.allTypes.get("survey_fieldtype");// 数据类型列表
		List<TSType> validationlist = TSTypegroup.allTypes.get("validation");// 验证规则列表

		m.put("typelist", typelist);
		m.put("validationlist", validationlist);
		m.put("activityOption", activityOption);
		return new ModelAndView("cms/activity/activityOption", m);
	}

	/**
	 * 活动报名选项内容保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ActivityOptionEntity activityOption,
			HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		String activityids = request.getParameter("activityids");// 活动id
		if (StringUtils.isNotEmpty(activityOption.getId())) {
			message = "活动报名选项内容【" + activityOption.getId() + "】更新成功";
			ActivityOptionEntity t = activityOptionService
					.getEntity(activityOption.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(activityOption, t);
				activityOptionService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "活动报名选项内容【" + activityOption.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {

			message = "活动报名选项内容【" + activityOption.getId() + "】添加成功";
			activityOption.setCreatetime(new Date());// 创建时间
			activityOptionService.save(activityOption);
			// 活动id有值时,根据活动id添加表单选项表的数据
			if (StringUtils.isNotEmpty(activityids)) {

				ActivityOptionContentEntity aoc = new ActivityOptionContentEntity();
				aoc.setOptionids(activityOption.getId());
				aoc.setActivityids(activityids);
				if (activityOption.getIsShow() == 1) {
					aoc.setIsEnableds(1);
					aoc.setIsReceptionshow(1);
					aoc.setIsRequired(1);
				}
				activityOptionContentService.save(aoc);
			}
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "activityOptionController.do?activityOption");
		String str = j.toString();
		return str;
	}

	/**
	 * 活动报名选项内容删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		ActivityOptionEntity activityOption = activityOptionService
				.getEntity(java.lang.String.valueOf(id));
		message = "活动报名选项内容【" + activityOption.getId() + "】删除成功";
		activityOptionService.delete(activityOption);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "activityOptionController.do?activityOption");
		String str = j.toString();
		return str;
	}

	/**
	 * 表单字段管理
	 * 
	 * @param request
	 */
	@RequestMapping(params = "activityOptionForm")
	public ModelAndView activityOptionForm(ActivityOptionEntity activityOption,
			HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = activityOptionService.getPageList(
				activityOption, param, pageSize, pageNo);

		List<TSType> typelist = TSTypegroup.allTypes.get("survey_fieldtype");// 数据类型列表
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("typelist", typelist);
		m.put("actionUrl", "activityOptionController.do?activityOptionForm");
		return new ModelAndView("cms/activity/activityOptionFormList", m);
	}

	/**
	 * 表单字段管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addForm")
	public ModelAndView addForm(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		List<TSType> typelist = TSTypegroup.allTypes.get("survey_fieldtype");// 数据类型列表
		List<TSType> validationlist = TSTypegroup.allTypes.get("validation");// 验证规则列表
		m.put("typelist", typelist);
		m.put("validationlist", validationlist);
		m.put("activityOption", new ActivityOptionEntity());
		return new ModelAndView("cms/activity/activityOptionForm", m);
	}

	/**
	 * 表单字段管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "editForm")
	public ModelAndView editForm(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		ActivityOptionEntity activityOption = activityOptionService
				.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		List<TSType> typelist = TSTypegroup.allTypes.get("survey_fieldtype");// 数据类型列表
		List<TSType> validationlist = TSTypegroup.allTypes.get("validation");// 验证规则列表
		m.put("typelist", typelist);
		m.put("validationlist", validationlist);
		m.put("activityOption", activityOption);
		return new ModelAndView("cms/activity/activityOptionForm", m);
	}

	/**
	 * 表单字段管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveForm")
	@ResponseBody
	public String saveForm(ActivityOptionEntity activityOption,
			HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;

		if (StringUtils.isNotEmpty(activityOption.getId())) {
			message = "活动报名选项内容【" + activityOption.getId() + "】更新成功";
			ActivityOptionEntity t = activityOptionService
					.getEntity(activityOption.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(activityOption, t);
				activityOptionService.saveOrUpdate(t);

				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "活动报名选项内容【" + activityOption.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {

			message = "活动报名选项内容【" + activityOption.getId() + "】添加成功";
			activityOption.setCreatetime(new Date());// 创建时间
			activityOptionService.save(activityOption);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "activityOptionController.do?activityOptionForm");
		String str = j.toString();
		return str;
	}

	/**
	 * 表单字段管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "delForm")
	@ResponseBody
	public String delForm(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		ActivityOptionEntity activityOption = activityOptionService
				.getEntity(java.lang.String.valueOf(id));
		message = "活动报名选项内容【" + activityOption.getId() + "】删除成功";
		activityOptionService.delete(activityOption);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "activityOptionController.do?activityOptionForm");
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

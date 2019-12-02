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

import com.leimingtech.cms.service.activity.ActivityPeopleServiceI;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ActivityPeopleEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;

/**
 * @Title: Controller
 * @Description: 活动报名人表
 * @author
 * @date 2015-08-28 17:57:42
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/activityPeopleController")
public class ActivityPeopleController extends BaseController {

	private String message;
	/** 活动报名人表接口 */
	@Autowired
	private ActivityPeopleServiceI activityPeopleService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;

	/**
	 * 后台活动报名人表列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "activityPeople")
	public ModelAndView activityPeople(ActivityPeopleEntity activityPeople,
			HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));
		String contentCatId = request.getParameter("contentCatId");
		// 内容id
		String contentsId = request.getParameter("contentsId");
		Map param = request.getParameterMap();
		Map<String, Object> m = activityPeopleService.getPageList(
				activityPeople, param, pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl",
				"activityPeopleController.do?activityPeople&contentCatId="
						+ contentCatId + "&contentsId=" + contentsId);
		return new ModelAndView("cms/activity/activityPeopleList", m);
	}

	/**
	 * 活动报名人表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityPeople", new ActivityPeopleEntity());
		return new ModelAndView("cms/activity/activityPeople", m);
	}

	/**
	 * 活动报名人表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		ActivityPeopleEntity activityPeople = activityPeopleService
				.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityPeople", activityPeople);
		return new ModelAndView("cms/activity/activityPeople", m);
	}

	/**
	 * 活动报名人表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ActivityPeopleEntity activityPeople,
			HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;

		if (StringUtils.isNotEmpty(activityPeople.getId())) {
			message = "活动报名人表【" + activityPeople.getId() + "】更新成功";
			ActivityPeopleEntity t = activityPeopleService
					.getEntity(activityPeople.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(activityPeople, t);
				activityPeopleService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "活动报名人表【" + activityPeople.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "活动报名人表【" + activityPeople.getId() + "】添加成功";
			activityPeopleService.save(activityPeople);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "activityPeopleController.do?activityPeople");
		String str = j.toString();
		return str;
	}

	/**
	 * 活动报名人表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		ActivityPeopleEntity activityPeople = activityPeopleService
				.getEntity(java.lang.String.valueOf(id));
		message = "活动报名人表【" + activityPeople.getId() + "】删除成功";
		activityPeopleService.delete(activityPeople);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "activityPeopleController.do?activityPeople");
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

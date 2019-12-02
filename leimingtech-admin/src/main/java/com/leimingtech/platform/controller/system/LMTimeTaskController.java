package com.leimingtech.platform.controller.system;
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

import com.leimingtech.cms.json.AjaxJson;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.core.timer.DynamicTask;
import com.leimingtech.platform.entity.TSTimeTaskEntity;
import com.leimingtech.platform.service.TimeTaskServiceI;


/**   
 * @Title: Controller
 * @Description: 定时任务管理
 * @author jueyue
 * @date 2013-09-21 20:47:43
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/lmTimeTaskController")
public class LMTimeTaskController extends BaseController {

	@Autowired
	private TimeTaskServiceI timeTaskService;
	@Autowired
	private DynamicTask dynamicTask;
	@Autowired
	private SystemService systemService;
	private String message="";
	
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	@Autowired
	public void setTimeTaskService(TimeTaskServiceI timeTaskService) {
		this.timeTaskService = timeTaskService;
	}
	
	@RequestMapping(params = "timeTask")
	public ModelAndView timeTask(TSTimeTaskEntity tstimetask, HttpServletRequest request) {
		//获取文章列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		CriteriaQuery cq = new CriteriaQuery(TSTimeTaskEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, tstimetask, param);
		// 追加部门条件查询 (如果是input型，传的是部门名；如果是combo型，传的是部门ID)
		
		PageList pageList = this.timeTaskService.getPageList(cq, true);
		List<TSTimeTaskEntity> tstimeList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("tstimeList", tstimeList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "lmTimeTaskController.do?timeTask");
		return new ModelAndView("lmPage/timetask/timeTaskTab", m);
	}
	
	

	/**
	 * 删除定时任务管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest req) {
		JSONObject j = new JSONObject();
		String id = req.getParameter("id");
		TSTimeTaskEntity tstimetask = timeTaskService.getEntity(TSTimeTaskEntity.class, id);
			message = "定时任务删除成功";
			timeTaskService.delete(tstimetask);
			j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "lmTimeTaskController.do?timeTask");
		String str = j.toString();
		return str;
	}
	/**
	 * 添加定时任务管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		TSTimeTaskEntity tstimeTask =new TSTimeTaskEntity();
		reqeust.setAttribute("tstimeTask", tstimeTask);
		return new ModelAndView("lmPage/timetask/add_model");
	}
	
	/**
	 * 保存定时任务管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveTimetask")
	@ResponseBody
	public String saveRole(TSTimeTaskEntity timeTask, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if(timeTask.getIsStart()==null){
			timeTask.setIsStart("1");
		}
		if(timeTask.getIsEffect()==null){
			timeTask.setIsEffect("0");
		}
		if (StringUtil.isNotEmpty(timeTask.getId())) {
			message = "定时任务: [" + timeTask.getTaskDescribe() + "]  被更新成功";
			TSTimeTaskEntity t = timeTaskService.get(TSTimeTaskEntity.class, timeTask.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(timeTask, t);
				timeTaskService.saveOrUpdate(t);
			} catch (Exception e) {
				e.printStackTrace();
				message = "定时任务信息更新失败";
			}
		} else {
			message = "定时任务: [" + timeTask.getTaskDescribe() + "] 被添加成功";
			timeTask.setCreateDate(new Date());//创建时间
			timeTaskService.save(timeTask);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "lmTimeTaskController.do?timeTask");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 定时任务管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSTimeTaskEntity timeTask, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(timeTask.getId())) {
			timeTask = timeTaskService.getEntity(TSTimeTaskEntity.class, timeTask.getId());
			req.setAttribute("timeTaskPage", timeTask);
		}
		return new ModelAndView("system/timetask/timeTask");
	}
	
	/**
	 * 修改角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		TSTimeTaskEntity tstimetask = timeTaskService.getEntity(TSTimeTaskEntity.class, id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("tstimetask", tstimetask);
		return new ModelAndView("lmPage/timetask/add", m);
	}
	
	
	@RequestMapping(params = "checkPageModel")
	public ModelAndView checkPageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		TSTimeTaskEntity tstimetask = timeTaskService.getEntity(TSTimeTaskEntity.class, id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("tstimetask", tstimetask);
		return new ModelAndView("lmPage/timetask/check", m);
	}
	/**
	 * 更新任务时间使之生效
	 */
	@RequestMapping(params = "updateTime")
	@ResponseBody
	public AjaxJson updateTime(TSTimeTaskEntity timeTask, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		timeTask = timeTaskService.get(TSTimeTaskEntity.class, timeTask.getId());
		boolean isUpdate = dynamicTask.updateCronExpression(timeTask.getTaskId() , timeTask.getCronExpression());
		if(isUpdate){
			timeTask.setIsEffect("1");
			timeTask.setIsStart("1");
			timeTaskService.updateEntitie(timeTask);
		}
		j.setMsg(isUpdate?"定时任务管理更新成功":"定时任务管理更新失败");
		return j;
	}
	/**
	 * 启动或者停止任务
	 */
	@RequestMapping(params = "startOrStopTask")
	@ResponseBody
	public String startOrStopTask(TSTimeTaskEntity timeTask, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String isStarts = request.getParameter("isStart");
		
		boolean isStart = timeTask.getIsStart().equals("1");
		timeTask = timeTaskService.get(TSTimeTaskEntity.class, timeTask.getId());
		timeTask.setIsStart(isStarts);
		timeTask.setIsEffect(isStarts);
		//boolean isSuccess = dynamicTask.startOrStop(timeTask.getTaskId() ,isStart);
		if(timeTask!=null){
			//timeTask.setIsStart(isStart?"1":"0");
			timeTaskService.updateEntitie(timeTask);
			systemService.addLog((isStart?"开启任务":"停止任务")+timeTask.getTaskId(), Globals.Log_Leavel_INFO,Globals.Log_Type_UPDATE);
			message="状态修改成功!";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "lmTimeTaskController.do?timeTask");
		String str = j.toString();
		return str;
	}
	
	
}

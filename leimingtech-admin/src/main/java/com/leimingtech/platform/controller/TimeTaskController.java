package com.leimingtech.platform.controller;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.json.AjaxJson;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
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
@RequestMapping("/timeTaskController")
public class TimeTaskController extends BaseController {

	@Autowired
	private TimeTaskServiceI timeTaskService;
	@Autowired
	private DynamicTask dynamicTask;
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
	 * 定时任务管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "timeTask")
	public ModelAndView timeTask(HttpServletRequest request) {
		return new ModelAndView("system/timetask/timeTaskList");
	}

	
	/**
	 * 删除定时任务管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSTimeTaskEntity timeTask, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		timeTask = timeTaskService.getEntity(TSTimeTaskEntity.class, timeTask.getId());
		message = "定时任务管理["+timeTask.getTaskDescribe()+"]删除成功";
		timeTaskService.delete(timeTask);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加定时任务管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TSTimeTaskEntity timeTask, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		CronTrigger trigger = new CronTrigger();
		try {
			trigger.setCronExpression(timeTask.getCronExpression());
		} catch (ParseException e) {
			j.setMsg("Cron表达式错误");
			return j;
		}
		if (StringUtil.isNotEmpty(timeTask.getId())) {
			message = "定时任务管理["+timeTask.getTaskDescribe()+"]更新成功";
			TSTimeTaskEntity t = timeTaskService.get(TSTimeTaskEntity.class, timeTask.getId());
			try {
				if(!timeTask.getCronExpression().equals(t.getCronExpression())){
					timeTask.setIsEffect("0");
				}
				MyBeanUtils.copyBeanNotNull2Bean(timeTask, t);
				timeTaskService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "定时任务管理["+timeTask.getTaskDescribe()+"]更新失败";
			}
		} else {
			message = "定时任务管理["+timeTask.getTaskDescribe()+"]添加成功";
			timeTask.setCreateDate(new Date());//创建时间
			timeTaskService.save(timeTask);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.setMsg(message);
		return j;
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
		j.setMsg(isUpdate?"定时任务管理["+timeTask.getTaskDescribe()+"]更新成功":"定时任务管理["+timeTask.getTaskDescribe()+"]更新失败");
		return j;
	}
	/**
	 * 启动或者停止任务
	 */
	@RequestMapping(params = "startOrStopTask")
	@ResponseBody
	public AjaxJson startOrStopTask(TSTimeTaskEntity timeTask, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		boolean isStart = timeTask.getIsStart().equals("1");
		timeTask = timeTaskService.get(TSTimeTaskEntity.class, timeTask.getId());
		boolean isSuccess = dynamicTask.startOrStop(timeTask.getTaskId() ,isStart);
		if(isSuccess){
			timeTask.setIsStart(isStart?"1":"0");
			timeTaskService.updateEntitie(timeTask);
			systemService.addLog((isStart?"开启任务":"停止任务")+timeTask.getTaskId(), Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		}
		j.setMsg(isSuccess?"定时任务管理["+timeTask.getTaskDescribe()+"]更新成功":"定时任务管理["+timeTask.getTaskDescribe()+"]更新失败");
		return j;
	}
	
}

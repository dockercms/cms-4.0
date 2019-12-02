package com.leimingtech.cms.controller.businessconsulting;

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

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.BusinessConsultingEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.service.BusinessConsultingServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;

/**   
 * @Title: Controller
 * @Description: 业务咨询
 * @author 
 * @date 2016-03-31 14:27:11
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/businessConsultingController")
public class BusinessConsultingController extends BaseController {

	private String message;
	/** 业务咨询接口 */
	@Autowired
	private BusinessConsultingServiceI businessConsultingService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 业务咨询列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "businessConsulting")
	public ModelAndView businessConsulting(BusinessConsultingEntity businessConsulting, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = businessConsultingService.getPageList(businessConsulting, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "businessConsultingController.do?businessConsulting");
		return new ModelAndView("cms/businessconsulting/businessConsultingList", m);
	}

/*	*//**
	 * 业务咨询添加
	 * 
	 * @return
	 *//*
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("businessConsulting", new BusinessConsultingEntity());
		return new ModelAndView("cms/businessconsulting/businessConsulting", m);
	}*/
	
	/**
	 * 业务咨询更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		BusinessConsultingEntity businessConsulting = businessConsultingService.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		List<TSType> departList = TSTypegroup.allTypes.get("depart");// 部门列表
		List<TSType> classList = TSTypegroup.allTypes.get("business_class");// 业务类别
		m.put("departList", departList);
		m.put("classList", classList);
		m.put("businessConsulting", businessConsulting);
		return new ModelAndView("cms/businessconsulting/businessConsulting", m);
	}

	/**
	 * 业务咨询保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(BusinessConsultingEntity businessConsulting, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		message = "业务咨询更新成功";
		BusinessConsultingEntity t = businessConsultingService.getEntity(businessConsulting.getId());
		businessConsulting.setReplyTime(new Date());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(businessConsulting, t);
			t.setReplyStatus("1");
			t.setReplyTime(new Date());
			businessConsultingService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "业务咨询更新成功";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "businessConsultingController.do?businessConsulting");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 业务咨询删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		BusinessConsultingEntity businessConsulting = businessConsultingService.getEntity(java.lang.String.valueOf(id));
		message = "业务咨询【" + businessConsulting.getId() + "】删除成功";
		businessConsultingService.delete(businessConsulting);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "businessConsultingController.do?businessConsulting");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 审核通过
	 * @return
	 */
	@RequestMapping(params = "checkBusiness")
	@ResponseBody
	public String checkBusiness(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		String ischeck = request.getParameter("ischeck");
		BusinessConsultingEntity businessConsulting = businessConsultingService.getEntity(String.valueOf(id));
		businessConsulting.setIscheck(ischeck);
		try {
			message = "操作审核["+businessConsulting.getMessage()+"]成功";
			businessConsultingService.saveOrUpdate(businessConsulting);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "操作审核["+businessConsulting.getMessage()+"]失败";
		}
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "businessConsultingController.do?businessConsulting");
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

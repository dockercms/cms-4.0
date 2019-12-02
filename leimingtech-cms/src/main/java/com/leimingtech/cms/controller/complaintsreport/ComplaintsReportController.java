package com.leimingtech.cms.controller.complaintsreport;

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

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ComplaintsReportEntity;
import com.leimingtech.core.service.ComplaintsReportServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;

/**   
 * @Title: Controller
 * @Description: 投诉举报
 * @author 
 * @date 2016-04-01 09:49:17
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/complaintsReportController")
public class ComplaintsReportController extends BaseController {

	private String message;
	/** 投诉举报接口 */
	@Autowired
	private ComplaintsReportServiceI complaintsReportService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 投诉举报列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "complaintsReport")
	public ModelAndView complaintsReport(ComplaintsReportEntity complaintsReport, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = complaintsReportService.getPageList(complaintsReport, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "complaintsReportController.do?complaintsReport");
		return new ModelAndView("cms/complaintsreport/complaintsReportList", m);
	}

	/**
	 * 投诉举报添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("complaintsReport", new ComplaintsReportEntity());
		return new ModelAndView("cms/complaintsreport/complaintsReport", m);
	}
	
	/**
	 * 投诉举报更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ComplaintsReportEntity complaintsReport = complaintsReportService.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("complaintsReport", complaintsReport);
		return new ModelAndView("cms/complaintsreport/complaintsReport", m);
	}

	/**
	 * 投诉举报保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ComplaintsReportEntity complaintsReport, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		
		if (StringUtils.isNotEmpty(complaintsReport.getId())) {
			message = "投诉举报【" + complaintsReport.getId() + "】更新成功";
			ComplaintsReportEntity t = complaintsReportService.getEntity(complaintsReport.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(complaintsReport, t);
				complaintsReportService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "投诉举报【" + complaintsReport.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "投诉举报【" + complaintsReport.getId() + "】添加成功";
			complaintsReportService.save(complaintsReport);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "complaintsReportController.do?complaintsReport");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 投诉举报删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ComplaintsReportEntity complaintsReport = complaintsReportService.getEntity(java.lang.String.valueOf(id));
		message = "投诉举报【" + complaintsReport.getId() + "】删除成功";
		complaintsReportService.delete(complaintsReport);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "complaintsReportController.do?complaintsReport");
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

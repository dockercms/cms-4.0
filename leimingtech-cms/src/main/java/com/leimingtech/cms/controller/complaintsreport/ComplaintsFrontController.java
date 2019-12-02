package com.leimingtech.cms.controller.complaintsreport;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ComplaintsReportEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.ComplaintsReportServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 投诉举报
 * @author 
 * @date 2016-04-01 09:49:17
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/front/complaintsFrontController")
public class ComplaintsFrontController extends BaseController {

	private String message;
	/** 投诉举报接口 */
	@Autowired
	private ComplaintsReportServiceI complaintsReportService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 保存前台举报
	 * 
	 * @param request
	 */
	@RequestMapping(params = "saveJuBao")
	@ResponseBody
	public String saveJuBao(ComplaintsReportEntity complaintsReport,HttpServletRequest request) {
	
			
				JSONObject j = new JSONObject();
			
				message = "添加成功";
			
				complaintsReport.setCreatetime(new Date());//创建时间
				
				SiteEntity site = PlatFormUtil.getFrontSessionSite();
				complaintsReport.setSiteid(site.getId());
				complaintsReportService.save(complaintsReport);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
				j.accumulate("isSuccess", true);
			
				j.accumulate("msg", message);
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

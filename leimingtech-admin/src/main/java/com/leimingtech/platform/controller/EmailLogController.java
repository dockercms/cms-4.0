package com.leimingtech.platform.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.log.EmailLogEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.service.EmailLogServiceI;
/**   
 * @Title: Controller
 * @Description: 邮件日志
 * @author linjm
 * @date 2014-05-13 17:30:18
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/emailLogController")
public class EmailLogController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmailLogController.class);

	@Autowired
	private EmailLogServiceI emailLogService;
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
	 * 邮件日志列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(EmailLogEntity emailLog, HttpServletRequest request) {
		//获取邮件日志列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(EmailLogEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, emailLog, param);
		//排序条件
		//cq.addOrder("id", SortDirection.desc);
		cq.addOrder("sendtime",SortDirection.desc);
		cq.add();
		
		PageList pageList = this.emailLogService.getPageList(cq, true);
		List<EmailLogEntity> testList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "emailLogController.do?table");
		return new ModelAndView("cms/email/emailLogList", m);
	}

	/**
	 * 邮件日志添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new EmailLogEntity());
		return new ModelAndView("cms/email/emailLog", m);
	}
	
	/**
	 * 邮件日志更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		EmailLogEntity emailLog = emailLogService.getEntity(EmailLogEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", emailLog);
		return new ModelAndView("cms/email/emailLog", m);
	}

	/**
	 * 邮件发送并添加日志
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(EmailLogEntity emailLog, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotEmpty(emailLog.getId())) {
			//message = "邮件日志更新成功";
			message = "邮件重发成功";
			EmailLogEntity t = emailLogService.get(EmailLogEntity.class, emailLog.getId());
			try {
				list.add(t.getTomail());
				emailLogService.reSendMail(list , null, t.getId(), t.getContent());
			} catch (Exception e) {
				e.printStackTrace();
				message = "重新发送失败";
			}
		} else {
			message = "发送失败";
			if(emailLog.getTomail()!=null){
				message = "发送成功";
				list.add(emailLog.getTomail());
				emailLogService.sendMail(list , null, emailLog.getTitle(), emailLog.getContent());
			}
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "emailLogController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 邮件日志删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		EmailLogEntity emailLog = emailLogService.getEntity(EmailLogEntity.class, String.valueOf(id));
		message = "邮件日志["+emailLog.getTitle()+"]删除成功";
		emailLogService.delete(emailLog);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "emailLogController.do?table");
		String str = j.toString();
		return str;
	}
}

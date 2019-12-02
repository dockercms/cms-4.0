package com.leimingtech.platform.controller;
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
import com.leimingtech.core.entity.log.SmsLogEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SmsServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.StringUtil;


/**   
 * @Title: Controller
 * @Description: 短信管理
 * @author 
 * @date 2014-05-13 10:23:46
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/smsLogController")
public class SmsLogController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SmsLogController.class);

	@Autowired
	private SmsServiceI smsService;
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
	 * 短信管理列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(SmsLogEntity sms, HttpServletRequest request) {
		//获取短信管理列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(SmsLogEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, sms, param);
		//排序条件
		//cq.addOrder("id", SortDirection.desc);
		cq.addOrder("sendtime",SortDirection.desc);
		cq.add();
		PageList pageList = smsService.getPageList(cq, true);
		List<SmsLogEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "smsLogController.do.do?table");
		return new ModelAndView("cms/sms/smsList", m);
	}

	/**
	 * 短信管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new SmsLogEntity());
		return new ModelAndView("cms/sms/sms", m);
	}
	
	/**
	 * 短信管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		SmsLogEntity sms = smsService.getEntity(SmsLogEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", sms);
		return new ModelAndView("cms/sms/sms", m);
	}

	/**
	 * 短信管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(SmsLogEntity sms, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(sms.getId())) {
			message = "重发成功";
			SmsLogEntity t = smsService.get(SmsLogEntity.class, sms.getId());
			try {
				smsService.reSendSMS(t.getMobile(), t.getContent(), t.getId());
//				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "重发失败";
			}
		} else {
			message = "发送成功";
//			sms.setOp(ResourceUtil.getSessionUserName().getUserName());
//			sms.setSendtime(new Date());
			String  result = smsService.sendSMS(sms.getMobile(),sms.getContent(),"");
//			smsService.save(sms);
//			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "smsLogController.do.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 短信管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		SmsLogEntity sms = smsService.getEntity(SmsLogEntity.class, String.valueOf(id));
		message = "短信管理【"+sms.getContent()+"】删除成功";
		smsService.delete(sms);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "smsLogController.do.do?table");
		String str = j.toString();
		return str;
	}
}

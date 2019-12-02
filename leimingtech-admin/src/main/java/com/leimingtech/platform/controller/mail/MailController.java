package com.leimingtech.platform.controller.mail;
import java.util.Date;
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
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.mail.MailEntity;
import com.leimingtech.platform.service.mail.MailServiceI;

/**   
 * @Title: Controller
 * @Description: 邮件平台
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-03 17:26:27
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/mailController")
public class MailController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MailController.class);

	@Autowired
	private MailServiceI mailService;
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
	 * 邮件平台列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(MailEntity mail, HttpServletRequest request) {
		//获取邮件平台列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(MailEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, mail, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.add();
		PageList pageList = this.mailService.getPageList(cq, true);
		List<MailEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "mailController.do?table");
		return new ModelAndView("lmPage/mail/mailList", m);
	}

	/**
	 * 邮件平台添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		MailEntity mail = new MailEntity();
		m.put("page", mail);
		return new ModelAndView("lmPage/mail/mail", m);
	}
	
	/**
	 * 邮件平台更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		MailEntity mail = mailService.getEntity(MailEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", mail);
		return new ModelAndView("lmPage/mail/mail", m);
	}

	/**
	 * 邮件平台保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(MailEntity mail, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(mail.getId())) {
			message = "邮件平台["+mail.getId()+"]更新成功";
			MailEntity t = mailService.get(MailEntity.class, mail.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(mail, t);
				mailService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "邮件平台["+mail.getId()+"]更新失败";
			}
		} else {
			message = "邮件平台["+mail.getId()+"]添加成功";
			mail.setCreatedtime(new Date());//创建时间
			mailService.save(mail);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mailController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 邮件平台删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		MailEntity mail = mailService.getEntity(MailEntity.class, String.valueOf(id));
		message = "邮件平台["+mail.getId()+"]删除成功";
		mailService.delete(mail);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mailController.do?table");
		String str = j.toString();
		return str;
	}
}

package com.leimingtech.mobile.controller.feedback;
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
import com.leimingtech.mobile.entity.feedback.FeedbackEntity;
import com.leimingtech.mobile.service.feedback.FeedbackServiceI;

/**   
 * @Title: Controller
 * @Description: 意见反馈
 * @author 
 * @date 2014-08-25 09:58:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/feedbackController")
public class FeedbackController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FeedbackController.class);

	@Autowired
	private FeedbackServiceI feedbackService;
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
	 * 意见反馈列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "feedback")
	public ModelAndView feedback(FeedbackEntity feedback, HttpServletRequest request) {
		//获取意见反馈列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(FeedbackEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, feedback, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc );
		cq.add();
		PageList pageList = this.feedbackService.getPageList(cq, true);
		List<FeedbackEntity> resultList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "feedbackController.do?feedback");
		return new ModelAndView("mobile/feedback/feedbackList", m);
	}

	/**
	 * 意见反馈添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("feedback", new FeedbackEntity());
		return new ModelAndView("mobile/feedback/feedback", m);
	}
	
	/**
	 * 意见反馈更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		FeedbackEntity feedback = feedbackService.getEntity(FeedbackEntity.class, id );
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("feedback", feedback);
		return new ModelAndView("mobile/feedback/feedback", m);
	}

	/**
	 * 意见反馈保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(FeedbackEntity feedback, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(feedback.getId())) {
			message = "意见反馈["+feedback.getContent()+"]更新成功";
			FeedbackEntity t = feedbackService.get(FeedbackEntity.class, feedback.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(feedback, t);
				feedbackService.saveOrUpdate(t);
				systemService.addLog(message,  Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "意见反馈["+feedback.getContent()+"]更新失败";
			}
		} else {
			message = "意见反馈["+feedback.getContent()+"]添加成功";
			feedback.setCreatedtime(new Date());//创建时间
			feedbackService.save(feedback);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "feedbackController.do?feedback");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 意见反馈删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		FeedbackEntity feedback = feedbackService.getEntity(FeedbackEntity.class, id);
		message = "意见反馈["+feedback.getContent()+"]删除成功";
		feedbackService.delete(feedback);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "feedbackController.do?feedback");
		String str = j.toString();
		return str;
	}
}

package com.leimingtech.cms.controller.interviewguest;
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
import com.leimingtech.core.entity.InterviewGuestEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.InterviewGuestServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 访谈嘉宾
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-08 11:20:12
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/interviewGuestController")
public class InterviewGuestController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InterviewGuestController.class);

	@Autowired
	private InterviewGuestServiceI interviewGuestService;
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
	 * 访谈嘉宾列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(InterviewGuestEntity interviewGuest, HttpServletRequest request) {
		//获取访谈嘉宾列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(InterviewGuestEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, interviewGuest, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.interviewGuestService.getPageList(cq, true);
		List<InterviewGuestEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "interviewGuestController.do?table");
		return new ModelAndView("cms/interviewguest/interviewGuestList", m);
	}

	/**
	 * 访谈嘉宾添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		//临时给访谈选项中的投票id赋值为当前毫秒数
		String optionId = reqeust.getParameter("optionId");
		//访谈id
		String interviewId = reqeust.getParameter("interviewId");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new InterviewGuestEntity());
		m.put("optionId", optionId);
		m.put("interviewId", interviewId);
		return new ModelAndView("cms/interviewguest/interviewGuest", m);
	}
	
	/**
	 * 访谈嘉宾更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		InterviewGuestEntity interviewGuest = interviewGuestService.getEntity(InterviewGuestEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", interviewGuest);
		return new ModelAndView("cms/interviewguest/interviewGuest", m);
	}

	/**
	 * 访谈嘉宾保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(InterviewGuestEntity interviewGuest, HttpServletRequest request) {
		//传递参数跳转到调查选项添加页面
		String optionId = request.getParameter("optionId");
		//访谈id
		String interviewId = request.getParameter("interviewId");
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(interviewGuest.getId())) {
			message = "访谈嘉宾["+interviewGuest.getGuestIntroduce()+"]更新成功";
			InterviewGuestEntity t = interviewGuestService.get(InterviewGuestEntity.class, interviewGuest.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(interviewGuest, t);
				interviewGuestService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "访谈嘉宾更新失败";
			}
		} else {
			message = "访谈嘉宾["+interviewGuest.getGuestIntroduce()+"]添加成功";
			interviewGuest.setCreatedtime(new Date());//创建时间
			interviewGuestService.save(interviewGuest);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "interviewController.do?jumpOption&optionId="+optionId+"&interviewId="+interviewId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 访谈嘉宾删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		//传递参数跳转到调查选项添加页面
		String optionId = request.getParameter("optionId");
		//访谈id
		String interviewId = request.getParameter("interviewId");
		
		InterviewGuestEntity interviewGuest = interviewGuestService.getEntity(InterviewGuestEntity.class, String.valueOf(id));
		message = "访谈嘉宾["+interviewGuest.getGuestIntroduce()+"]删除成功";
		interviewGuestService.delete(interviewGuest);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "interviewController.do?jumpOption&optionId="+optionId+"&interviewId="+interviewId);
		String str = j.toString();
		return str;
	}
}

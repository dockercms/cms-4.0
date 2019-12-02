package com.leimingtech.member.controller.messages;
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
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.MessagesServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.member.entity.messages.MessagesEntity;


/**   
 * @Title: Controller
 * @Description: 留言表
 * @author 
 * @date 2014-08-07 10:17:57
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/messagesController")
public class MessagesController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MessagesController.class);

	@Autowired
	private MessagesServiceI messagesService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserService userService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 留言表列表页
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "messageslist")
	public ModelAndView messagesList(MessagesEntity messages, HttpServletRequest request) {
		
		Map<String, Object> m = new HashMap<String, Object>();
		m = ModelDate(messages, request);
		return new ModelAndView("cms/messages/messagesList", m);
	}
	
	
	private Map<String, Object> ModelDate(MessagesEntity messages, HttpServletRequest request){
		//获取留言表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(MessagesEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, messages, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.add();
		PageList pageList = this.messagesService.getPageList(cq, true);
		List<MessagesEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "messagesController.do?messageslist");
		return m;
	}

	/**
	 * 留言表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addmessages")
	public ModelAndView addMessages(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("messages", new MessagesEntity());
		return new ModelAndView("cms/messages/messages", m);
	}
	
	/**
	 * 留言表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatemessages")
	public ModelAndView updateMessages(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		MessagesEntity messages = messagesService.getEntity(MessagesEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("messages", messages);
		return new ModelAndView("cms/messages/messages", m);
	}

	/**
	 * 留言表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "savemessage")
	@ResponseBody
	public String saveOrUpdate(MessagesEntity messages, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(messages.getId())) {
			message = "留言表["+messages.getContent()+"]更新成功";
			MessagesEntity t = messagesService.get(MessagesEntity.class, messages.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(messages, t);
				messagesService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "留言表["+messages.getContent()+"]更新失败";
			}
		} else {
			message = "留言表["+messages.getContent()+"]添加成功";
			messages.setCreatedtime(new Date());//创建时间
			messagesService.save(messages);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "messagesController.do?messages");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 留言表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "delmessages")
	@ResponseBody
	public String delMessages(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		MessagesEntity messages = messagesService.getEntity(MessagesEntity.class, String.valueOf(id));
		message = "留言表["+messages.getContent()+"]删除成功";
		messagesService.delete(messages);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "messagesController.do?messageslist");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 留言表回复
	 * 
	 * @return
	 */
	@RequestMapping(params = "replyMessages")
	public ModelAndView replyMessages(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		MessagesEntity messages = messagesService.getEntity(MessagesEntity.class, String.valueOf(id));
		List<TSType> departList = TSTypegroup.allTypes.get("depart");// 部门列表
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("departList", departList);
		m.put("messages", messages);
		return new ModelAndView("cms/messages/replyMessage", m);
	}
	
	/**
	 * 回复保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "savereply")
	@ResponseBody
	public String saveReply(MessagesEntity messages, HttpServletRequest request) {
		TSUser user = PlatFormUtil.getSessionUser();
		if (null == user) {
			user = new TSUser();
		} else {
			user = userService.getEntity(TSUser.class, user.getId());
		}
		JSONObject j = new JSONObject();
		MessagesEntity t = messagesService.get(MessagesEntity.class, messages.getId());
		message = "留言表["+t.getContent()+"]更新成功";
		t.setReplyStatus("1");
		t.setReplyUser(user.getUserName());
		t.setReplyTime(new Date());
		messages.setReplyTime(new Date());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(messages, t);
			
			messagesService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "留言表["+messages.getContent()+"]更新失败";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "messagesController.do?messageslist");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 审核通过
	 * @return
	 */
	@RequestMapping(params = "checkMessages")
	@ResponseBody
	public String checkMessages(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		String ischeck = request.getParameter("ischeck");
		MessagesEntity messages = messagesService.get(MessagesEntity.class, String.valueOf(id));
		messages.setIscheck(ischeck);
		try {
			message = "操作审核["+messages.getContent()+"]成功";
			messagesService.saveOrUpdate(messages);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "操作审核["+messages.getContent()+"]失败";
		}
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "messagesController.do?messageslist");
		String str = j.toString();
		return str;
	}
}

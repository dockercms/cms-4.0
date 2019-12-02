package com.leimingtech.member.controller.messages;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.MessagesServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.member.entity.messages.MessagesEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**   
 * @Title: Controller
 * @Description: 留言表
 * @author 
 * @date 2014-08-07 10:17:57
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/front/messagesFrontController")
public class MessagesFrontController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MessagesFrontController.class);

	@Autowired
	private MessagesServiceI messagesService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private MemberServiceI memberMng;
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
	@RequestMapping(params = "messageslistfront")
	public ModelAndView messagesList(MessagesEntity messages, HttpServletRequest request) {
		
		//获取留言表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(MessagesEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, messages, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);

		cq.notEq("ischeck", "0");
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
		return new ModelAndView("cms/messages/messageslistfront", m);
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
			messages.setAddTime(new Date());
			//messages.setIscheck("0");
			MemberEntity member = memberMng.getSessionMember();
			if(member != null){
				messages.setMemberid(member.getId() + "");
			}
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
	 * 前台公众留言
	 * 
	 * @return
	 */
	@RequestMapping(params = "publicMessagesList")
	public ModelAndView publicMessagesList(HttpServletRequest reqeust){
				
		//获取留言表列表
				
				int pageSize = StringUtils.isEmpty(reqeust.getParameter("pageSize")) ? 20 : Integer.valueOf(reqeust.getParameter("pageSize"));
				int pageNo = StringUtils.isEmpty(reqeust.getParameter("pageNo")) ? 1 : Integer.valueOf(reqeust.getParameter("pageNo"));
				
				CriteriaQuery cq = new CriteriaQuery(MessagesEntity.class, pageSize, pageNo, "", "");	
				//排序条件
				cq.addOrder("createdtime",SortDirection.desc);

				cq.eq("replyStatus", "1");
				cq.eq("siteId", PlatFormUtil.getFrontSessionSiteId());
				cq.add();
				List<MessagesEntity> messagesList = messagesService
						.getListByCriteriaQuery(cq, false);
				PageList pageList = this.messagesService.getPageList(cq, true);
				List<MessagesEntity> resultList = pageList.getResultList();
				
				int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
				if(pageCount <= 0){
					pageCount = 1;
				}
				Map<String, Object> m = new HashMap<String, Object>();
				SiteEntity site = PlatFormUtil.getFrontSessionSite();
				String sitePath = site.getSitePath();
				List<TSType> departList = TSTypegroup.allTypes.get("depart");// 部门列表
				m.put("pageList", resultList);
				m.put("pageNo", pageList.getCurPageNO());
				m.put("pageCount", pageCount);
				m.put("pageSize", pageSize);
				m.put("sitePath", sitePath);
				m.put("site", site);
				m.put("departList", departList);
				m.put("countNum", messagesList.size());
				m.put("searchUrl", "messagesFrontController.do?publicMessagesList");
				return new ModelAndView("wwwroot/" + sitePath + "/template/hudongjiaoliu/gongzhongliuyan", m);
	}
	
	/**
	 * 公众留言保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveMessage")
	@ResponseBody
	public String saveMessage(MessagesEntity messages,HttpServletRequest request) {
	
			
			JSONObject j = new JSONObject();
			HttpSession session = ContextHolderUtils.getSession();
			String randCode = session.getAttribute("randCode").toString();// 验证码
			String code = request.getParameter("valicode");
			
			if(!randCode.equalsIgnoreCase(code)){
				message = "验证码错误!";
			}else{
				message = "添加成功";
				messages.setAddTime(new Date());	
				messages.setCreatedtime(new Date());//创建时间
				messages.setReplyStatus("0");
				SiteEntity site = PlatFormUtil.getFrontSessionSite();
				messages.setSiteId(site.getId());
				messagesService.save(messages);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
				j.accumulate("isSuccess", true);
			}
			j.accumulate("msg", message);
			String str = j.toString();
			return str;
	}
	
	
	@RequestMapping(params = "hdjlhfly")
	@ResponseBody
	public String hdjlhfzx(HttpServletRequest request) {
		CriteriaQuery cq = new CriteriaQuery(MessagesEntity.class);	
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);

		cq.eq("replyStatus", "1");
		cq.eq("siteId", PlatFormUtil.getFrontSessionSiteId());
		cq.add();
		List<MessagesEntity> messagesList = messagesService
				.getListByCriteriaQuery(cq, false);
		List<String> dateList = new ArrayList<String>();
		String date="";
		for (MessagesEntity message : messagesList) {
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			date=df.format(message.getAddTime());
			dateList.add(date);
		}
		JSONObject j = new JSONObject();
		j.accumulate("messagesList", messagesList);
		j.accumulate("dateList", dateList);
		String str = j.toString();
		return str ;
	}
	
	/**
	 * 前台公众留言详情
	 * 
	 * @return
	 */
	@RequestMapping(params = "messageDetail")
	public ModelAndView messageDetail(HttpServletRequest reqeust){
				
		//获取留言表列表
				
				
				String id = reqeust.getParameter("messageId"); //留言id
				
				MessagesEntity message = messagesService.get(MessagesEntity.class, id);
				
				Map<String, Object> m = new HashMap<String, Object>();
				SiteEntity site = PlatFormUtil.getFrontSessionSite();
				String sitePath = site.getSitePath();
				List<TSType> departList = TSTypegroup.allTypes.get("depart");// 部门列表
			
				m.put("sitePath", sitePath);
				m.put("site", site);
				m.put("departList", departList);
				m.put("message", message);
		
				return new ModelAndView("wwwroot/" + sitePath + "/template/hudongjiaoliu/gongzhongliuyan_detail", m);
	}
	
	
}


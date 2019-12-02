package com.leimingtech.mobile.controller.invitation;
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
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.InvitationMobileEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.InvitationMobileServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 移动内容跟帖表
 * @author 
 * @date 2014-07-25 14:38:10
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/invitationMobileController")
public class InvitationMobileController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InvitationMobileController.class);

	@Autowired
	private InvitationMobileServiceI invitationMobileService;
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
	 * 移动内容跟帖表列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "invitationMobile")
	public ModelAndView invitationMobile(InvitationMobileEntity invitationMobile, HttpServletRequest request) {
		//获取移动内容跟帖表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		String contentsId = request.getParameter("contentId");
		String mobileChannelId = request.getParameter("mobileChannelId");
		ContentsMobileEntity contentsMobile= null;
		if(StringUtil.isNotEmpty(contentsId)&&!"null".equals(contentsId)){
			contentsMobile = invitationMobileService.get(ContentsMobileEntity.class,  contentsId );
		}
		CriteriaQuery cq = new CriteriaQuery(InvitationMobileEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, invitationMobile, param);
		//排序条件
		cq.addOrder("createtime",SortDirection.desc );
		cq.add();
		if(StringUtil.isNotEmpty(contentsId)){
			cq.eq("contentsId", String.valueOf(contentsId));
		}
		cq.add();
		PageList pageList = this.invitationMobileService.getPageList(cq, true);
		List<InvitationMobileEntity> resultList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		String url = "invitationMobileController.do?invitationMobile";
		if(StringUtil.isNotEmpty(contentsId)&&!"null".equals(contentsId)){
			url += "&contentId="+contentsId;
		}
		if(StringUtil.isNotEmpty(mobileChannelId)&&!"null".equals(mobileChannelId)){
			url += "&mobileChannelId="+mobileChannelId;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("contentsId", contentsId);
		m.put("mobileChannelId", mobileChannelId);
		m.put("contentsMobile", contentsMobile);
		m.put("actionUrl", url);
		return new ModelAndView("mobile/invitation/invitationMobileList", m);
	}

	/**
	 * 移动内容跟帖表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("invitationMobile", new InvitationMobileEntity());
		return new ModelAndView("mobile/invitation/invitationMobile", m);
	}
	
	/**
	 * 移动内容跟帖表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		InvitationMobileEntity invitationMobile = invitationMobileService.getEntity(InvitationMobileEntity.class, id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("invitationMobile", invitationMobile);
		return new ModelAndView("mobile/invitation/invitationMobile", m);
	}

	/**
	 * 移动内容跟帖表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(InvitationMobileEntity invitationMobile, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(invitationMobile.getId())) {
			message = "移动内容跟帖["+invitationMobile.getTitle()+"]更新成功";
			InvitationMobileEntity t = invitationMobileService.get(InvitationMobileEntity.class, invitationMobile.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(invitationMobile, t);
				invitationMobileService.saveOrUpdate(t);
				systemService.addLog(message , Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE );
			} catch (Exception e) {
				e.printStackTrace();
				message = "移动内容跟帖["+invitationMobile.getTitle()+"]更新失败";
			}
		} else {
			message = "移动内容跟帖["+invitationMobile.getTitle()+"]添加成功";
			invitationMobile.setCreatetime(new Date());//创建时间
			invitationMobileService.save(invitationMobile);
			systemService.addLog(message , Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT );
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "invitationMobileController.do?invitationMobile");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 移动内容跟帖表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		InvitationMobileEntity invitationMobile = invitationMobileService.getEntity(InvitationMobileEntity.class, id);
		message = "移动内容跟帖["+invitationMobile.getTitle()+"] 删除成功";
		invitationMobileService.delete(invitationMobile);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "invitationMobileController.do?invitationMobile");
		String str = j.toString();
		return str;
	}
	/**
	 * 根据跟帖id更新是否通过审核
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateStatus")
	@ResponseBody
	public String updateStatus(HttpServletRequest request){
		JSONObject j = new JSONObject();
		String invitationId = request.getParameter("invitationId");
		String contentsId = request.getParameter("contentId");
		String mobileChannelId = request.getParameter("mobileChannelId");
		String status = request.getParameter("status");
		j = invitationMobileService.updateStatus(contentsId,mobileChannelId,invitationId, status);
		String str = j.toString();
		return str;
	}
}

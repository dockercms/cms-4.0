package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.InvitationMobileServiceI;

@Controller
@RequestMapping("/front/invitationMobileApi")
public class InvitationMobileApi extends BaseController{

	@Autowired
	private InvitationMobileServiceI invitationMobileService;
	
	/**
	 * 单个内容跟帖列表
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/loadInvitationList")
	@ResponseBody
	public JSONObject loadInvitationList(HttpServletRequest request) {
		//是否需要实体全部原有属性
		String all = request.getParameter("all");
		//内容id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//用户id
		String userId = request.getParameter("userId");
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 20 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		JSONObject json = invitationMobileService.getInvitationMobileList(all, contentsMobileId, userId, pageSize, pageNo);
		return json;
	}
	/**
	 * wap端--单个内容跟帖列表
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/loadInvitationListWap")
	@ResponseBody
	public JSONObject loadInvitationListWap(HttpServletRequest request) {
		//是否需要实体全部原有属性
		String all = request.getParameter("all");
		//内容id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//用户id
		String userId = request.getParameter("userId");
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 20 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		JSONObject json = invitationMobileService.getInvitationMobileListWap(all, contentsMobileId, userId, pageSize, pageNo);
		return json;
	}
	/**
	 * 所有内容跟帖列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getInvitationList")
	@ResponseBody
	public JSONObject getInvitationList(HttpServletRequest request) {
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 20 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		JSONObject json = invitationMobileService.getInvitationList(pageSize, pageNo);
		return json;
	}
}

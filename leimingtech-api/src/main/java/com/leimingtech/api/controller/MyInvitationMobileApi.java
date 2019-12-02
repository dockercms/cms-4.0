package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.InvitationMobileServiceI;

@Controller
@RequestMapping("/front/myInvitationMobileApi")
public class MyInvitationMobileApi extends BaseController{

	@Autowired
	private InvitationMobileServiceI invitationMobileService;
	
	/**
	 * 跟帖提交
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/saveInvitation")
	@ResponseBody
	public JSONObject saveComment(HttpServletRequest request) {
		//新闻id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//用户id
		String userId = request.getParameter("userId");
		//跟帖内容
		String content = request.getParameter("content");
		String all = request.getParameter("all");
		JSONObject json = invitationMobileService.saveInvitation(all,userId,contentsMobileId, content);
		return json;
	}
	/**
	 * 跟帖提交
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/saveInvitationWap")
	@ResponseBody
	public JSONObject saveCommentWap(HttpServletRequest request) {
		//新闻id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//用户id
		String userId = request.getParameter("userId");
		//跟帖内容
		String content = request.getParameter("content");
		String all = request.getParameter("all");
		JSONObject json = invitationMobileService.saveInvitationWap(all,userId,contentsMobileId, content);
		return json;
	}
}

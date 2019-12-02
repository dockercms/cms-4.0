package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.mobile.service.comment.CommentMobileServiceI;

@Controller
@RequestMapping("/front/commentMobileApi")
public class CommentMobileApi extends BaseController{

	@Autowired
	private CommentMobileServiceI commentMobileService;
	
	/**
	 * 评论列表
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/loadCommentList")
	@ResponseBody
	public JSONObject loadCommentList(HttpServletRequest request) {
		//是否需要实体全部原有属性
		String all = request.getParameter("all");
		//频道id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//用户id
		String userId = request.getParameter("userId");
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		JSONObject json = commentMobileService.getCommentMobileList(all, contentsMobileId, userId, pageSize, pageNo);
		return json;
	}
}

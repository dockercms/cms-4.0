package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.mobile.service.comment.CommentMobileServiceI;

@Controller
@RequestMapping("/front/myCommentMobileApi")
public class MyCommentMobileApi extends BaseController{

	@Autowired
	private CommentMobileServiceI commentMobileService;
	
	/**
	 * 评论提交
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/saveComment")
	@ResponseBody
	public JSONObject saveComment(HttpServletRequest request) {
		//新闻id
		String contentsMobileId = request.getParameter("contentsMobileId");
		//反馈意见
		String content = request.getParameter("content");
		//用户id
		String userId = request.getParameter("userId");
		//标题
		String title = request.getParameter("title");
		String all = request.getParameter("all");
		JSONObject json = commentMobileService.saveComment(all,contentsMobileId, title, content,userId);
		return json;
	}
}

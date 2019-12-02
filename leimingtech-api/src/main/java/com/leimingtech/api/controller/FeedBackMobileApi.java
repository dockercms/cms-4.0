package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.mobile.service.suggest.SuggestServiceI;

@Controller
@RequestMapping("/front/feedBackMobileApi")
public class FeedBackMobileApi extends BaseController{

	@Autowired
	private SuggestServiceI suggestService;
	
	/**
	 * 反馈列表
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/loadFeedBack")
	@ResponseBody
	public JSONObject loadFeedBack(HttpServletRequest request) {
		//是否需要实体全部原有属性
		String all = request.getParameter("all");
		//反馈内容
		String content = request.getParameter("content");
		//用户id
		String userId = request.getParameter("userId");
		//联系邮箱
		String email = request.getParameter("email");
		JSONObject json = suggestService.getSuggest(all, content,email,userId);
		return json;
	}
}

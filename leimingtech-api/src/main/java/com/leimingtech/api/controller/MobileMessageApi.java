package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.mobile.service.message.MobileMessageServiceI;

@Controller
@RequestMapping("/front/mobileMessageApi")
public class MobileMessageApi extends BaseController{

	@Autowired
	private MobileMessageServiceI mobileMessageService;
	
	/**
	 * 发送通知
	 * @return
	 */
	@RequestMapping(value = "/sendMessage")
	@ResponseBody
	public JSONObject sendMessage(HttpServletRequest request){
		//内容id
		String contentsMobileId = request.getParameter("contentsMobileId");
		JSONObject json = mobileMessageService.loadMessage(contentsMobileId);
		return json;
	}
	/**
	 * 通知列表
	 * @return
	 */
	@RequestMapping(value = "/messageList")
	@ResponseBody
	public JSONObject messageList(HttpServletRequest request){
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 20 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		JSONObject json = mobileMessageService.messageList(pageSize,pageNo);
		return json;
	}
}

package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.service.MobileChannelServiceI;

@Controller
@RequestMapping("/front/mobileChannelApi")
public class MobileChannelApi extends BaseController{
	
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	
	/**
	 * 频道管理列表
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/loadMobileChannelList")
	@ResponseBody
	public JSONObject loadMobileChannelList(MobileChannelEntity mobileChanne,HttpServletRequest request) {
		//是否需要实体全部原有属性
		String all = request.getParameter("all");
		String mobileChannelId = request.getParameter("mobileChannelId");
		JSONObject json = mobileChannelService.getmobileChannelList(all,mobileChannelId);
		
		return json;
	}
}

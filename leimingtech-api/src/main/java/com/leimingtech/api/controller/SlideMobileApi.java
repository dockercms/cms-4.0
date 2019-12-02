package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.SimplespecialEntity;
import com.leimingtech.core.service.ContentsMobileServiceI;

@Controller
@RequestMapping("/front/slideMobileApi")
public class SlideMobileApi extends BaseController{

	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	
	/**
	 * 幻灯片列表
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/loadSlideList")
	@ResponseBody
	public JSONObject loadSlideList(HttpServletRequest request) {
		//是否需要实体全部原有属性
		String all = request.getParameter("all");
		//频道id
		String mobileChannelId = request.getParameter("mobileChannelId");
		//显示几条头图
		int isTop = StringUtils.isEmpty(request.getParameter("isTop"))? 4 : Integer.valueOf(request.getParameter("isTop"));
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 20 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		//专题下所有ids
		String contentIds = "";
		//专题
		SimplespecialEntity simpleSpecial = null;
		JSONObject json = contentsMobileService.getcontentsMobileList(all, mobileChannelId, pageSize, pageNo,isTop,contentIds,simpleSpecial);
		return json;
	}
}

package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.ContentsMobileServiceI;

@Controller
@RequestMapping("/front/searchMobileApi")
public class SearchContentMobileApi extends BaseController {
	
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	
	/**
	 * 搜索
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/loadSearchContentList")
	@ResponseBody
	public JSONObject loadSearchContentList(HttpServletRequest request) {
		//是否需要实体全部原有属性
		String all = request.getParameter("all");
		//关键词
		String key = request.getParameter("key");
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 20 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		JSONObject json = contentsMobileService.getSearchContentList(all, key, pageSize, pageNo);
		return json;
	}
}

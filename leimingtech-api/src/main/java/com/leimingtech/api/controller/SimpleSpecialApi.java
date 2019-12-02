package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.cms.service.simplespecial.SimplespecialServiceI;
import com.leimingtech.core.controller.BaseController;

@Controller
@RequestMapping("/front/simpleSpecialApi")
public class SimpleSpecialApi extends BaseController{
	
	@Autowired
	private SimplespecialServiceI simplespecialService;
	
	/**
	 * 专题列表
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/loadSimpleSpecialList")
	@ResponseBody
	public JSONObject loadSimpleSpecialList(HttpServletRequest request) {
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		JSONObject json = simplespecialService.loadSimpleSpecial(pageSize,pageNo);
		return json;
	}
	
	/**
	 * 单个专题下内容列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadSimpleSpecialContent")
	@ResponseBody
	public JSONObject loadSimpleSpecialContent(HttpServletRequest request) {
		//专题id
		String simpleSpecialId = request.getParameter("simpleSpecialId");
		//不是头图列表
		int isTop = 0;
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		JSONObject json = simplespecialService.loadSimpleSpecialContent(pageSize, pageNo,simpleSpecialId,isTop);
		return json;
	}
}

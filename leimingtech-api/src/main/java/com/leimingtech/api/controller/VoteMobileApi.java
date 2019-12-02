package com.leimingtech.api.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.VoteMobileServiceI;

@Controller
@RequestMapping("/front/voteMobileApi")
public class VoteMobileApi extends BaseController{

	@Autowired
	private VoteMobileServiceI voteMobileService;
	@RequestMapping(value = "/loadVoteList")
	@ResponseBody
	public JSONObject loadContentList(HttpServletRequest request) {
		//分页
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 20 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		JSONObject json = voteMobileService.getVoteMobileList(pageSize, pageNo);
		return json;
	}
}

package com.leimingtech.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.cms.service.ContributeServiceI;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.util.StringUtil;

@Controller
@RequestMapping("/front/contributeApi")
public class ContributeApi extends BaseController{

	@Autowired
	private ContributeServiceI contributeService;
	
	/**
	 * 爆料栏目
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value = "/getContributeCat")
	@ResponseBody
	public JSONObject getContributeCat(HttpServletRequest request) {
		JSONObject json = contributeService.getContributeCat();
		return json;
	}
	/**
	 * 提交保存爆料
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveContribute")
	@ResponseBody
	public JSONObject saveContribute(HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Map<String, String> map = requestMap(request);
			if(StringUtil.isNotEmpty(map.get("userId"))){
				contributeService.saveContribute(map);
				json.accumulate("resultMsg", "爆料成功");
				json.accumulate("resultCode", "1");
			}else{
				json.accumulate("resultMsg", "请登录之后投稿");
				json.accumulate("resultCode", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("resultMsg", "服务器繁忙，稍后重试！");
			json.accumulate("resultCode", "0");
		}
		return json;
	}
	/**
	 * 爆料列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/contributeList")
	@ResponseBody
	public JSONObject contributeList(HttpServletRequest request){
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageIndex")) ? 1 : Integer.valueOf(request.getParameter("pageIndex"));
		Map map = request.getParameterMap();
		ContentsEntity contens = new ContentsEntity();
		String userId = request.getParameter("userId");
		PageList list = contributeService.contentsList(pageSize, pageNo, map, String.valueOf(userId), contens);
		List<ContentsEntity> resultList = list.getResultList();
		JSONObject json = contributeService.getContentList(resultList);
		return json;
	}
}

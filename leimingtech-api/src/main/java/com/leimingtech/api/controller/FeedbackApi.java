package com.leimingtech.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.mobile.entity.feedback.FeedbackEntity;
import com.leimingtech.mobile.service.feedback.FeedbackServiceI;

/**
 * 意见反馈
 * 
 * @author zhangyulong
 * 
 */
@Controller
@RequestMapping(value = "/front/feedbackApi")
public class FeedbackApi {
	@Autowired
	private FeedbackServiceI feedbackService = null;
	private String error = "请求数据出错，请重试！";

	/**
	 * 保存意见反馈
	 * 
	 * @param request
	 *            获取实体
	 * @return 成功 或 失败
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request) {
		Boolean bool = false;
		JSONObject json = new JSONObject();
		Integer resultCode = 0;
		String resultMessage = "";
		// 获取参数
		String content = request.getParameter("content"); // 内容
		String contentEx = "";
		try {
			contentEx = URLDecoder.decode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String email = request.getParameter("email"); // 邮箱
		String system = request.getParameter("system"); // 系统
		String version = request.getParameter("version"); // 版本
		FeedbackEntity entity = new FeedbackEntity();
		entity.setContent(contentEx);
		entity.setEmail(email);
		entity.setSystem(system);
		entity.setVersion(version);
		try {
			bool = feedbackService.saveEntity(entity);

			if (bool) {
				resultCode = 1;
				resultMessage = "保存成功";
			} else {
				resultCode = 0;
				resultMessage = "保存失败";
			}
		} catch (Exception e) {
			resultCode = 3;
			resultMessage = "访问异常";
			e.printStackTrace();
		}
		json.put("resultCode", resultCode);
		json.put("resultMessage", resultMessage);
		return json;
	}

	/**
	 * 获取意见反馈（分页查询）
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页面尺寸
	 * @return
	 */
	@RequestMapping(value = "/getListByPage")
	@ResponseBody
	public Map<String, Object> getListByPage(Integer pageNo, Integer pageSize) {
		Map<String, Object> map = null;
		try {
			map = feedbackService.getListByPage(pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", error);
		}
		return map;
	}

	/**
	 * 获取所有意见反馈
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getList")
	@ResponseBody
	public List getList() {
		List list = null;
		try {
			list = feedbackService.getList(FeedbackEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<String>();
			list.add(error);
		}
		return list;
	}
}

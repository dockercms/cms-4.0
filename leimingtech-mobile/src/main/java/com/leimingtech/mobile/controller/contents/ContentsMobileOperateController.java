package com.leimingtech.mobile.controller.contents;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.mobile.service.contents.ContentsMobileOperateServiceI;

/**
 * 移动内容操作
 * 
 * @author liuzhen 2015-1-21 12:16:57
 * 
 */
@Controller
@RequestMapping("/contentsMobileOperateController")
public class ContentsMobileOperateController extends BaseController {

	/** 移动内容操作 */
	@Autowired
	private ContentsMobileOperateServiceI contentsMobileOperateService;
	@Autowired
	private SystemService systemService;

	/**
	 * 修改移动内容置顶
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateTop")
	@ResponseBody
	public String updateTop(HttpServletRequest request) {
		String contentId = request.getParameter("contentId");

		JSONObject j = new JSONObject();

		if (StringUtils.isEmpty(contentId)) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "参数不合法");
			return j.toString();
		}

		ContentsMobileEntity mobile = contentsMobileOperateService.getEntity(ContentsMobileEntity.class,  contentId );
		if (mobile == null) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "参数不合法");
			return j.toString();
		}

		String logInfo = "移动内容【" + mobile.getTitle() + "】";
		String message = "";
		String result = "";
		if (0 == mobile.getIsTop()) {
			// 执行置顶操作
			Boolean flag = contentsMobileOperateService.setTop(contentId, contentsMobileOperateService.getCurrentMaxTop() + 1);
			if (flag) {
				result = "置顶";
				message = "置顶成功";
				systemService.addLog(logInfo + message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				j.accumulate("isSuccess", true);
				j.accumulate("toUrl", "contentsMobileController.do?table&tab=all&mobileChannelId=" + mobile.getCatid());
			} else {
				message = "置顶失败";
				j.accumulate("isSuccess", false);
			}
		} else {
			// 执行取消置顶操作
			Boolean flag = contentsMobileOperateService.cancelTop(contentId);
			if (flag) {
				result = "";
				message = "成功取消置顶";
				systemService.addLog(logInfo + message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				j.accumulate("isSuccess", true);
				j.accumulate("toUrl", "contentsMobileController.do?table&tab=all&mobileChannelId=" + mobile.getCatid());
			} else {
				message = "取消置顶失败";
				j.accumulate("isSuccess", false);
			}
		}
		LogUtil.info(logInfo + message);
		j.accumulate("result", result);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 设置移动内容头图
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateTopPic")
	@ResponseBody
	public String updateTopPic(HttpServletRequest request) {
		String contentId = request.getParameter("contentId");

		JSONObject j = new JSONObject();

		if (StringUtils.isEmpty(contentId)) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "参数不合法");
			return j.toString();
		}

		ContentsMobileEntity mobile = contentsMobileOperateService.getEntity(ContentsMobileEntity.class,  contentId );
		if (mobile == null) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "参数不合法");
			return j.toString();
		}

		String logInfo = "移动内容【" + mobile.getTitle() + "】";
		String message = "";
		if (0 == mobile.getIsTopPic()) {
			// 执行设为头图操作
			Boolean flag = contentsMobileOperateService.changeTopPic(contentId);
			if (flag) {
				message = "头图设置成功";
				systemService.addLog(logInfo + message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				j.accumulate("isSuccess", true);
				j.accumulate("toUrl", "contentsMobileController.do?table&tab=all&mobileChannelId=" + mobile.getCatid());
			} else {
				message = "头图设置失败";
				j.accumulate("isSuccess", false);
			}
		} else {
			// 执行取消头图操作
			Boolean flag = contentsMobileOperateService.changeTopPic(contentId);
			if (flag) {
				message = "取消头图成功";
				systemService.addLog(logInfo + message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				j.accumulate("isSuccess", true);
				j.accumulate("toUrl", "contentsMobileController.do?table&tab=all&mobileChannelId=" + mobile.getCatid());
			} else {
				message = "取消头图失败";
				j.accumulate("isSuccess", false);
			}
		}
		LogUtil.info(logInfo + message);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
}

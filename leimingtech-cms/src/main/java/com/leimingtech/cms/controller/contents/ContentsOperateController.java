package com.leimingtech.cms.controller.contents;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.cms.service.ContentsOperateServiceI;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;

/**
 * 内容操作
 * 
 * @author liuzhen 2015-1-21 12:16:57
 * 
 */
@Controller
@RequestMapping("/contentsOperateController")
public class ContentsOperateController extends BaseController {

	/** 内容操作 */
	@Autowired
	private ContentsOperateServiceI contentsOperateService;
	@Autowired
	private SystemService systemService;

	/**
	 * 内容置顶
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

		ContentsEntity contents = contentsOperateService.getEntity(ContentsEntity.class,  contentId );
		if (contents == null) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "参数不合法");
			return j.toString();
		}

		String logInfo = "内容【" + contents.getTitle() + "】";
		String message = "";
		String result = "";
		if (0 == contents.getIsTop()) {
			// 执行置顶操作
			Boolean flag = contentsOperateService.setTop(contentId, contentsOperateService.getCurrentMaxTop() + 1);
			if (flag) {
				result = "置顶";
				message = "置顶成功";
				systemService.addLog(logInfo + message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				j.accumulate("isSuccess", true);
				j.accumulate("toUrl", "contentsController.do?table&tab=all&contentCatId=" + contents.getCatid());
			} else {
				message = "置顶失败";
				j.accumulate("isSuccess", false);
			}
		} else {
			// 执行取消置顶操作
			Boolean flag = contentsOperateService.cancelTop(contentId);
			if (flag) {
				result = "";
				message = "成功取消置顶";
				systemService.addLog(logInfo + message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				j.accumulate("isSuccess", true);
				j.accumulate("toUrl", "contentsController.do?table&tab=all&contentCatId=" + contents.getCatid());
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

	
}

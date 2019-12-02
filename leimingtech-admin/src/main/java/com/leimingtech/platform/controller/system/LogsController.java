package com.leimingtech.platform.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.TSLog;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.platform.service.LogService;


/**
 * 日志处理类
 * 
 * @author 
 * 
 */
@Controller
@RequestMapping("/logsController")
public class LogsController extends BaseController {
	
	@Autowired
    private LogService logService;
	
    private String message = null;

	/**
     * 日志管理
     */
	@RequestMapping(params = "log")
	public ModelAndView log(TSLog logs, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 100
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));
		Map param = request.getParameterMap();
		PageList pageList = this.logService.getPageList(pageSize, pageNo, logs,
				param);
		List<TSLog> logList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("loglevels", logs.getLoglevel());
		m.put("searchMap", param);
		m.put("logList", logList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "logsController.do?log");
		return new ModelAndView("lmPage/log/logTab", m);
	}
    
	/**
     * 删除日志
     */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(TSLog tslog, HttpServletRequest req) {
		JSONObject j = new JSONObject();
		tslog = logService.getEntity(tslog.getId());
		if (tslog != null && !"".equals(tslog)) {
			logService.delete(tslog);
			message = "登录日志：" + tslog.getLogcontent() + "  删除成功";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "logsController.do?log");
		String str = j.toString();
		return str;
	}
	
}

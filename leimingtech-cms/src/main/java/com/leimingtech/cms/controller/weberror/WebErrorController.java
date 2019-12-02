package com.leimingtech.cms.controller.weberror;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.WebErrorEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.WebErrorServiceI;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;

/**   
 * @Title: Controller
 * @Description: 网站纠错
 * @author 
 * @date 2016-04-01 15:02:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/webErrorController")
public class WebErrorController extends BaseController {

	private String message;
	/** 网站纠错接口 */
	@Autowired
	private WebErrorServiceI webErrorService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 网站纠错列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "webError")
	public ModelAndView webError(WebErrorEntity webError, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = webErrorService.getPageList(webError, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "webErrorController.do?webError");
		return new ModelAndView("cms/weberror/webErrorList", m);
	}

	/**
	 * 网站纠错添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("webError", new WebErrorEntity());
		return new ModelAndView("cms/weberror/webError", m);
	}
	
	/**
	 * 网站纠错更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		WebErrorEntity webError = webErrorService.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("webError", webError);
		return new ModelAndView("cms/weberror/webError", m);
	}

	/**
	 * 网站纠错保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(WebErrorEntity webError, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		
		if (StringUtils.isNotEmpty(webError.getId())) {
			message = "网站纠错【" + webError.getId() + "】更新成功";
			WebErrorEntity t = webErrorService.getEntity(webError.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(webError, t);
				webErrorService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "网站纠错【" + webError.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "网站纠错【" + webError.getId() + "】添加成功";
			webErrorService.save(webError);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "webErrorController.do?webError");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 网站纠错删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		WebErrorEntity webError = webErrorService.getEntity(java.lang.String.valueOf(id));
		message = "网站纠错【" + webError.getId() + "】删除成功";
		webErrorService.delete(webError);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "webErrorController.do?webError");
		String str = j.toString();
		return str;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

package com.leimingtech.cms.controller.weberror;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.WebErrorEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.WebErrorServiceI;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 网站纠错
 * @author 
 * @date 2016-04-01 15:02:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/front/webErrorFrontController")
public class WebErrorFrontController extends BaseController {

	private String message;
	/** 网站纠错接口 */
	@Autowired
	private WebErrorServiceI webErrorService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	/**
	 * 保存网站纠错
	 * @param emailAddress
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveWebError")
	@ResponseBody
	public String saveWebError(WebErrorEntity webError,HttpServletRequest request) {
		
		
		JSONObject j = new JSONObject();
	
		message = "添加成功";
	
		webError.setCreatetime(new Date());//创建时间
		
		SiteEntity site = PlatFormUtil.getFrontSessionSite();
		webError.setSiteid(site.getId());
		webErrorService.save(webError);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		j.accumulate("isSuccess", true);
	
		j.accumulate("msg", message);
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

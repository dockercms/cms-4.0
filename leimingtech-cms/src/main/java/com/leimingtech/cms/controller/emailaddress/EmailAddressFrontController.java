package com.leimingtech.cms.controller.emailaddress;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.EmailAddressEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.EmailAddressServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;

/**   
 * @Title: Controller
 * @Description: 信访邮箱
 * @author 
 * @date 2016-04-01 11:11:36
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/front/emailAddressFrontController")
public class EmailAddressFrontController extends BaseController {

	private String message;
	/** 信访邮箱接口 */
	@Autowired
	private EmailAddressServiceI emailAddressService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 保存信访邮箱
	 * 
	 * @param request
	 */
	@RequestMapping(params = "saveEmailAddress")
	@ResponseBody
	public String saveEmailAddress(EmailAddressEntity emailAddress,HttpServletRequest request) {
		
		
		JSONObject j = new JSONObject();
	
		message = "添加成功";
	
		emailAddress.setCreatetime(new Date());//创建时间
		
		SiteEntity site = PlatFormUtil.getFrontSessionSite();
		emailAddress.setSiteid(site.getId());
		emailAddressService.save(emailAddress);
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

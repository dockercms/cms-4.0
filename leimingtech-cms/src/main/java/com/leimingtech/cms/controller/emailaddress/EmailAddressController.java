package com.leimingtech.cms.controller.emailaddress;

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
import com.leimingtech.core.entity.EmailAddressEntity;
import com.leimingtech.core.service.EmailAddressServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;

/**   
 * @Title: Controller
 * @Description: 信访邮箱
 * @author 
 * @date 2016-04-01 11:11:36
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/emailAddressController")
public class EmailAddressController extends BaseController {

	private String message;
	/** 信访邮箱接口 */
	@Autowired
	private EmailAddressServiceI emailAddressService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 信访邮箱列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "emailAddress")
	public ModelAndView emailAddress(EmailAddressEntity emailAddress, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = emailAddressService.getPageList(emailAddress, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "emailAddressController.do?emailAddress");
		return new ModelAndView("cms/emailaddress/emailAddressList", m);
	}

	/**
	 * 信访邮箱添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("emailAddress", new EmailAddressEntity());
		return new ModelAndView("cms/emailaddress/emailAddress", m);
	}
	
	/**
	 * 信访邮箱更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		EmailAddressEntity emailAddress = emailAddressService.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("emailAddress", emailAddress);
		return new ModelAndView("cms/emailaddress/emailAddress", m);
	}

	/**
	 * 信访邮箱保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(EmailAddressEntity emailAddress, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		
		if (StringUtils.isNotEmpty(emailAddress.getId())) {
			message = "信访邮箱【" + emailAddress.getId() + "】更新成功";
			EmailAddressEntity t = emailAddressService.getEntity(emailAddress.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(emailAddress, t);
				emailAddressService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "信访邮箱【" + emailAddress.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "信访邮箱【" + emailAddress.getId() + "】添加成功";
			emailAddressService.save(emailAddress);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "emailAddressController.do?emailAddress");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 信访邮箱删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		EmailAddressEntity emailAddress = emailAddressService.getEntity(java.lang.String.valueOf(id));
		message = "信访邮箱【" + emailAddress.getId() + "】删除成功";
		emailAddressService.delete(emailAddress);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "emailAddressController.do?emailAddress");
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

package com.leimingtech.platform.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.PfConfigEntity;
import com.leimingtech.core.service.SmsServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.platform.service.EmailLogServiceI;
import com.leimingtech.platform.service.PfConfigServiceI;

/**   
 * @Title: Controller
 * @Description: 配置项
 * @author 
 * @date 2014-05-15 13:27:30
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/configSettingController")
public class ConfigSettingController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ConfigSettingController.class);

	@Autowired
	private PfConfigServiceI pfConfigService;
	@Autowired
	private EmailLogServiceI emailLogService;
	@Autowired
	private SmsServiceI smsService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 邮件配置页ftl
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "mailSetting")
	public ModelAndView mailSetting(HttpServletRequest request) {
		Map<String, String> mCache = PfConfigEntity.pfconfigCache;
		mCache.put("actionUrl", "configSettingController.do?mailSetting");
		return new ModelAndView("platform/settings/mailSetting",mCache);
	}
	
	/**
	 * 配置项保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "mailSave")
	@ResponseBody
	public String mailSaveOrUpdate(PfConfigEntity pfConfig, HttpServletRequest request) {
		Enumeration parameterNames = request.getParameterNames();
		message = "配置项更新成功";
		String toUrl= "";
		while (parameterNames.hasMoreElements()) {
			Object object = (Object) parameterNames.nextElement();
			//过滤无效的信息
			if("testmail".equals(object) || "mailSave".equals(object)) continue;
			try{
				saveOrUpdataSetting(request, object);
			} catch (Exception e) {
				e.printStackTrace();
				message = "配置项更新失败";
				break;
			}
		}
		JSONObject j = new JSONObject();
		systemService.refleshPfConfig();//更新缓存
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "configSettingController.do?mailSetting");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 测试邮箱配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "testsend")
	@ResponseBody
	public String test(PfConfigEntity pfConfig, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String mail = request.getParameter("testmail");
		message="邮件已发出";
		List<String> list = new ArrayList<String>();
		list.add(mail);
		try {
			emailLogService.sendMail(list , null, "测试邮箱配置", "这里是邮箱配置的测试内容");
		} catch (Exception e) {
			message="发送失败";
			e.printStackTrace();
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "configSettingController.do?mailSetting");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 短信配置页ftl
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "smsSetting")
	public ModelAndView smsSetting(HttpServletRequest request) {
		Map<String, String> mCache = PfConfigEntity.pfconfigCache;
		mCache.put("actionUrl", "configSettingController.do?mailSetting");
		return new ModelAndView("platform/settings/SMSSetting",mCache);
	}
	
	/**
	 * 短信配置项保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "SMSSave")
	@ResponseBody
	public String SMSSaveOrUpdate(PfConfigEntity pfConfig, HttpServletRequest request) {
		Enumeration parameterNames = request.getParameterNames();
		message = "配置项更新成功";
		String toUrl= "";
		while (parameterNames.hasMoreElements()) {
			Object object = (Object) parameterNames.nextElement();
			//过滤无效的信息
			if("testmobile".equals(object) || "SMSSave".equals(object)) continue;
			try{
				saveOrUpdataSetting(request, object);
			} catch (Exception e) {
				e.printStackTrace();
				message = "配置项更新失败";
				break;
			}
		}
		JSONObject j = new JSONObject();
		systemService.refleshPfConfig();//更新缓存
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "configSettingController.do?smsSetting");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 测试短信配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "testSMS")
	@ResponseBody
	public String testSMS(PfConfigEntity pfConfig, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String mobile = request.getParameter("testmobile");
		message="短信已发出";
		try {
			if(StringUtils.isNotEmpty(mobile)){
				String result = smsService.sendSMS(mobile,"短信测试发送成功","");
			}
		} catch (Exception e) {
			message="发送失败";
			e.printStackTrace();
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "configSettingController.do?smsSetting");
		String str = j.toString();
		return str;
	}
	
	
	/**
	 * 短信配置页ftl
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "APISetting")
	public ModelAndView APISetting(HttpServletRequest request) {
		Map<String, String> mCache = PfConfigEntity.pfconfigCache;
		mCache.put("actionUrl", "configSettingController.do?APISetting");
		return new ModelAndView("platform/settings/APISetting",mCache);
	}
	
	/**
	 * 配置项保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "APISave")
	@ResponseBody
	public String APISaveOrUpdate(PfConfigEntity pfConfig, HttpServletRequest request) {
		Enumeration parameterNames = request.getParameterNames();
		message = "配置项更新成功";
		String toUrl= "";
		while (parameterNames.hasMoreElements()) {
			Object object = (Object) parameterNames.nextElement();
			//过滤无效的信息
			if("APISave".equals(object)) continue;
			try{
				saveOrUpdataSetting(request, object);
			} catch (Exception e) {
				e.printStackTrace();
				message = "配置项更新失败";
				break;
			}
		}
		JSONObject j = new JSONObject();
		systemService.refleshPfConfig();//更新缓存
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "configSettingController.do?APISetting");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 公用保存或更新
	 * @param request
	 * @param object
	 */
	private void saveOrUpdataSetting(HttpServletRequest request, Object object) {
		if(object!=null){
			String value = request.getParameter(object.toString());
			PfConfigEntity config = pfConfigService.singleResult("from PfConfigEntity where configkey='"+object.toString()+"'");
			if (config!=null) {
				config.setConfigvalue(value);
				pfConfigService.saveOrUpdate(config);
			} else if(StringUtils.isNotEmpty(object.toString()) && StringUtils.isNotEmpty(value)){
				config = new PfConfigEntity();
				config.setConfigkey(object.toString());
				config.setConfigvalue(value);
				config.setCreatedtime(new Date());//创建时间
				pfConfigService.save(config);
			}
		}
	}
	
}

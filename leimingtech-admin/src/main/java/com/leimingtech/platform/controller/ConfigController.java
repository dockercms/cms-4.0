package com.leimingtech.platform.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.json.AjaxJson;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.TSConfig;
import com.leimingtech.platform.service.ConfigServiceI;




/**
 * 配置信息处理类
 * 
 * @author 
 * 
 */
@Controller
@RequestMapping("/configController")
public class ConfigController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ConfigController.class);
	@Autowired
	private ConfigServiceI configService;
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * 配置列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "config")
	public ModelAndView config() {
		return new ModelAndView("system/config/configList");
	}



	/**
	 * 删除配置信息
	 * 
	 * @param config
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSConfig config, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		config = configService.getEntity(TSConfig.class, config.getId());
		message = "配置信息: " + config.getName() + "被删除 成功";
		configService.delete(config);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		return j;
	}

	/**
	 * 添加和更新配置信息
	 * 
	 * @param config
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TSConfig tsConfig,HttpServletRequest request) {
		if (StringUtil.isEmpty(tsConfig.getId())) {
			TSConfig tsConfig2=configService.findUniqueByProperty(TSConfig.class, "code", tsConfig.getCode());
			if(tsConfig2!=null){
				message = "编码为: " + tsConfig.getCode() + "的配置信息已存在";
			}else{
				tsConfig.setTSUser(PlatFormUtil.getSessionUser());
				tsConfig.setCreatedtime(new Date());//创建时间
				configService.save(tsConfig);
				message = "配置信息: " + tsConfig.getName() + "被添加成功";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			}
			
		}else{
			message = "配置信息: " + tsConfig.getName() + "被修改成功";
			configService.updateEntitie(tsConfig);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		}
		AjaxJson j = new AjaxJson();
		j.setMsg(message);
		
		return j;
	}

	/**
	 * 添加和更新配置信息页面
	 * 
	 * @param config
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSConfig config, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(config.getId())) {
			config = configService.getEntity(TSConfig.class,
					config.getId());
			req.setAttribute("config", config);
		}
		return new ModelAndView("system/config/config");
	}

}

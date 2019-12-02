package com.leimingtech.wechat.controller.wechatconfig;

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
import com.leimingtech.core.entity.WechatConfigEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.wechat.service.wechatconfig.WechatConfigServiceI;

/**   
 * @Title: Controller
 * @Description: 微信参数
 * @author 
 * @date 2015-08-13 14:56:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wechatConfigController")
public class WechatConfigController extends BaseController {

	private String message;
	/** 微信参数接口 */
	@Autowired
	private WechatConfigServiceI wechatConfigService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 微信参数列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "wechatConfig")
	public ModelAndView wechatConfig(WechatConfigEntity wechatConfig, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = wechatConfigService.getPageList(wechatConfig, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "wechatConfigController.do?wechatConfig");
		return new ModelAndView("wechat/wechatconfig/wechatConfigList", m);
	}

	/**
	 * 微信参数添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("wechatConfig", new WechatConfigEntity());
		return new ModelAndView("wechat/wechatconfig/wechatConfig", m);
	}
	
	/**
	 * 微信参数更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		WechatConfigEntity wechatConfig = wechatConfigService.getEntity(String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("wechatConfig", wechatConfig);
		return new ModelAndView("wechat/wechatconfig/wechatConfig", m);
	}

	/**
	 * 微信参数保存
	 *
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(WechatConfigEntity wechatConfig, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;

		if (StringUtils.isNotEmpty(wechatConfig.getId())) {
			message = "微信参数【" + wechatConfig.getId() + "】更新成功";
			WechatConfigEntity t = wechatConfigService.getEntity(wechatConfig.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(wechatConfig, t);
				wechatConfigService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "微信参数【" + wechatConfig.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "微信参数【" + wechatConfig.getId() + "】添加成功";
			wechatConfigService.save(wechatConfig);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		this.systemService.reloadWechatConfig();
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wechatConfigController.do?wechatConfig");
		String str = j.toString();
		return str;
	}

	/**
	 * 微信参数删除
	 *
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		WechatConfigEntity wechatConfig = wechatConfigService.getEntity(String.valueOf(id));
		message = "微信参数【" + wechatConfig.getId() + "】删除成功";
		wechatConfigService.delete(wechatConfig);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wechatConfigController.do?wechatConfig");
		String str = j.toString();
		this.systemService.reloadWechatConfig();
		return str;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

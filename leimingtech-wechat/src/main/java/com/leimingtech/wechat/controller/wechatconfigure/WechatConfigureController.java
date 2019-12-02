package com.leimingtech.wechat.controller.wechatconfigure;

import java.util.Date;
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
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.wechat.entity.wechatconfigure.WechatConfigureEntity;
import com.leimingtech.wechat.service.wechatconfigure.WechatConfigureServiceI;

/**   
 * @Title: Controller
 * @Description: 微信公众账号配置
 * @author 
 * @date 2015-12-02 15:53:37
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wechatConfigureController")
public class WechatConfigureController extends BaseController {

	private String message;
	/** 微信公众账号配置接口 */
	@Autowired
	private WechatConfigureServiceI wechatConfigureService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	@Autowired
	private CommonService commonService;
	

	/**
	 * 微信公众账号配置添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		WechatConfigureEntity wechatConfigure =null;
		SiteEntity site  = PlatFormUtil.getSessionSite();
		wechatConfigure=commonService.findUniqueByProperty(WechatConfigureEntity.class,"siteid",site.getId());
		if(wechatConfigure==null){
			wechatConfigure=new WechatConfigureEntity();
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("wechatConfigure", wechatConfigure);
		return new ModelAndView("wechat/wechatconfigure/wechatConfigure", m);
	}
	
	

	/**
	 * 微信公众账号配置保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(WechatConfigureEntity wechatConfigure, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		SiteEntity site= PlatFormUtil.getSessionSite();
		
		if (StringUtils.isNotEmpty(wechatConfigure.getId())) {
			message = "微信公众账号配置更新成功";
			WechatConfigureEntity t = wechatConfigureService.getEntity(wechatConfigure.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(wechatConfigure, t);
				t.setSiteid(site.getId());
				t.setCreatetime(new Date());
				wechatConfigureService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "微信公众账号配置更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "微信公众账号配置添加成功";
			wechatConfigure.setSiteid(site.getId());
			wechatConfigure.setCreatetime(new Date());
			wechatConfigureService.save(wechatConfigure);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wechatConfigureController.do?add");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 微信公众账号配置删除
	 * 
	 * @return
	 *//*
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		WechatConfigureEntity wechatConfigure = wechatConfigureService.getEntity(java.lang.String.valueOf(id));
		message = "微信公众账号配置【" + wechatConfigure.getId() + "】删除成功";
		wechatConfigureService.delete(wechatConfigure);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wechatConfigureController.do?wechatConfigure");
		String str = j.toString();
		return str;
	}*/
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

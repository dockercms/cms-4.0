package com.leimingtech.wechat.controller.wechatbutton;

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
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.wechat.entity.wechatbutton.WechatButtonEntity;
import com.leimingtech.wechat.enums.WeChatMenuEventEnum;
import com.leimingtech.wechat.service.wechatbutton.WechatButtonServiceI;

/**   
 * @Title: Controller
 * @Description: 自定义菜单管理
 * @author 
 * @date 2015-08-12 18:20:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wechatButtonController")
public class WechatButtonController extends BaseController {

	private String message;
	/** 自定义菜单管理接口 */
	@Autowired
	private WechatButtonServiceI wechatButtonService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 自定义菜单管理列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "wechatButton")
	public ModelAndView wechatButton(WechatButtonEntity wechatButton, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map<? , ?> param = request.getParameterMap();
		Map<String, Object> m = wechatButtonService.getPageList(wechatButton, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "wechatButtonController.do?wechatButton");
		return new ModelAndView("wechat/wechatbutton/wechatButtonList", m);
	}

	/**
	 * 自定义菜单管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		initSaveOrUpdate(m);
		m.put("wechatButton", new WechatButtonEntity());
		return new ModelAndView("wechat/wechatbutton/wechatButton", m);
	}
	
	/**
	 * 自定义菜单管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		WechatButtonEntity wechatButton = wechatButtonService.getEntity(String.valueOf(id));
		Map<String , Object> m = new HashMap<String , Object>();
		initSaveOrUpdate(m);
		m.put("wechatButton", wechatButton);
		return new ModelAndView("wechat/wechatbutton/wechatButton", m);
	}

	private void initSaveOrUpdate( Map<String , Object> m) {
		m.put("menuChineseMap" , WeChatMenuEventEnum.chineseMap.entrySet());
		m.put("menuRemarkMap" , WeChatMenuEventEnum.remarkMap.entrySet());
		m.put("menuEvents" , WeChatMenuEventEnum.values());
	}

	/**
	 * 自定义菜单管理保存
	 *
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(WechatButtonEntity wechatButton, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;

		if (StringUtils.isNotEmpty(wechatButton.getId())) {
			message = "自定义菜单管理【" + wechatButton.getId() + "】更新成功";
			WechatButtonEntity t = wechatButtonService.getEntity(wechatButton.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(wechatButton, t);
				wechatButtonService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "自定义菜单管理【" + wechatButton.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "自定义菜单管理【" + wechatButton.getId() + "】添加成功";
			wechatButtonService.save(wechatButton);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wechatButtonController.do?wechatButton");
		String str = j.toString();
		return str;
	}

	/**
	 * 自定义菜单管理删除
	 *
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		WechatButtonEntity wechatButton = wechatButtonService.getEntity(String.valueOf(id));
		message = "自定义菜单管理【" + wechatButton.getId() + "】删除成功";
		wechatButtonService.delete(wechatButton);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wechatButtonController.do?wechatButton");
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

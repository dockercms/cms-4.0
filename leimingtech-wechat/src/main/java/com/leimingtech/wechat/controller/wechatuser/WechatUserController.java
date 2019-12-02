package com.leimingtech.wechat.controller.wechatuser;

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
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.WechatConfigEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.wechat.entity.wechatuser.WechatUserEntity;
import com.leimingtech.wechat.enums.WechatEncodeModeEnum;
import com.leimingtech.wechat.enums.WechatTypeEnum;
import com.leimingtech.wechat.service.wechatuser.WechatUserServiceI;

/**
 * @Title: Controller
 * @Description: 微信号管理
 * @author
 * @date 2015-08-12 18:18:12
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/wechatUserController")
public class WechatUserController extends BaseController {
	
	private String message;
	/** 微信号管理接口 */
	@Autowired
	private WechatUserServiceI wechatUserService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 微信号管理列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "wechatUser")
	public ModelAndView wechatUser(WechatUserEntity wechatUser , HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		Map param = request.getParameterMap();
		Map<String , Object> m = wechatUserService.getPageList(wechatUser , param , pageSize , pageNo);
		m.put("wechatTypeChineseMap" , WechatTypeEnum.chineseMap.entrySet());
		m.put("wechatTypeRemarkMap" , WechatTypeEnum.remarkMap.entrySet());
		m.put("wechatTypes" , WechatTypeEnum.values());
		m.put("searchMap" , param);
		m.put("pageNo" , pageNo);
		m.put("pageSize" , pageSize);
		m.put("actionUrl" , "wechatUserController.do?wechatUser");
		return new ModelAndView("wechat/wechatuser/wechatUserList" , m);
	}
	
	/**
	 * 微信号管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("wechatUser" , new WechatUserEntity());
		initSaveOrUpdate(m);
		return new ModelAndView("wechat/wechatuser/wechatUser" , m);
	}
	
	/**
	 * 微信号管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		WechatUserEntity wechatUser = wechatUserService.getEntity(String.valueOf(id));
		Map<String , Object> m = new HashMap<String , Object>();
		initSaveOrUpdate(m);
		m.put("wechatUser" , wechatUser);
		return new ModelAndView("wechat/wechatuser/wechatUser" , m);
	}

	private void initSaveOrUpdate(Map<String , Object> m) {
		m.put("wechatTypeChineseMap" , WechatTypeEnum.chineseMap.entrySet());
		m.put("wechatTypeRemarkMap" , WechatTypeEnum.remarkMap.entrySet());
		m.put("wechatTypes" , WechatTypeEnum.values());
		m.put("wechatEncodeModeChineseMap" , WechatEncodeModeEnum.chineseMap.entrySet());
		m.put("wechatEncodeModeRemarkMap" , WechatEncodeModeEnum.remarkMap.entrySet());
		m.put("wechatEncodeModes" , WechatEncodeModeEnum.values());
		m.put("wechatQRSaveFolder" , WechatConfigEntity.getConfig("wechatQRSaveFolder"));
		m.put("wechatHeaderpicSaveFolder" , WechatConfigEntity.getConfig("wechatHeaderpicSaveFolder"));
	}

	/**
	 * 微信号管理保存
	 *
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(WechatUserEntity wechatUser , HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;

		if(StringUtils.isNotEmpty(wechatUser.getId())) {
			message = "微信号管理【" + wechatUser.getId() + "】更新成功";
			WechatUserEntity t = wechatUserService.getEntity(wechatUser.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(wechatUser , t);
				t.setLastupdatetime(new Date());
				t.setRegion(t.getProvince()+t.getCity());
				wechatUserService.saveOrUpdate(t);
				systemService.addLog(message , Globals.Log_Leavel_INFO , Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "微信号管理【" + wechatUser.getId() + "】更新失败";
				LogUtil.error(message , e);
				isSuccess = false;
			}
		} else {
			message = "微信号管理【" + wechatUser.getId() + "】添加成功";
			wechatUser.setCreatetime(new Date());
			wechatUser.setRegion(wechatUser.getProvince()+wechatUser.getCity());
			wechatUserService.save(wechatUser);
			LogUtil.info(message);
			systemService.addLog(message , Globals.Log_Leavel_INFO , Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess" , isSuccess);
		j.accumulate("msg" , message);
		j.accumulate("toUrl" , "wechatUserController.do?wechatUser");
		String str = j.toString();
		return str;
	}

	/**
	 * 微信号管理删除
	 *
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		WechatUserEntity wechatUser = wechatUserService.getEntity(String.valueOf(id));
		message = "微信号管理【" + wechatUser.getId() + "】删除成功";
		wechatUserService.delete(wechatUser);
		LogUtil.info(message);
		systemService.addLog(message , Globals.Log_Leavel_INFO , Globals.Log_Type_DEL);
		j.accumulate("isSuccess" , true);
		j.accumulate("msg" , message);
		j.accumulate("toUrl" , "wechatUserController.do?wechatUser");
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

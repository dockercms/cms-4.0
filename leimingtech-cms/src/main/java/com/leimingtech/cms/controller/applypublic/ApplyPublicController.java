package com.leimingtech.cms.controller.applypublic;

import java.util.HashMap;
import java.util.List;
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
import com.leimingtech.core.entity.ApplyPublicEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.service.ApplyPublicServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;

/**   
 * @Title: Controller
 * @Description: 申请公开
 * @author 
 * @date 2016-04-05 16:57:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/applyPublicController")
public class ApplyPublicController extends BaseController {

	private String message;
	/** 申请公开接口 */
	@Autowired
	private ApplyPublicServiceI applyPublicService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 申请公开列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "applyPublic")
	public ModelAndView applyPublic(ApplyPublicEntity applyPublic, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = applyPublicService.getPageList(applyPublic, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "applyPublicController.do?applyPublic");
		return new ModelAndView("cms/applypublic/applyPublicList", m);
	}

	/**
	 * 申请公开添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("applyPublic", new ApplyPublicEntity());
		return new ModelAndView("cms/applypublic/applyPublic", m);
	}
	
	/**
	 * 申请公开更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ApplyPublicEntity applyPublic = applyPublicService.getEntity(java.lang.String.valueOf(id));
		List<TSType> statusList = TSTypegroup.allTypes.get("application_status");// 申请状态
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("applyPublic", applyPublic);
		m.put("statusList", statusList);
		return new ModelAndView("cms/applypublic/applyPublic", m);
	}

	/**
	 * 申请公开保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ApplyPublicEntity applyPublic, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		
		if (StringUtils.isNotEmpty(applyPublic.getId())) {
			message = "申请公开【" + applyPublic.getId() + "】更新成功";
			ApplyPublicEntity t = applyPublicService.getEntity(applyPublic.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(applyPublic, t);
				applyPublicService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "申请公开【" + applyPublic.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "申请公开【" + applyPublic.getId() + "】添加成功";
			applyPublicService.save(applyPublic);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "applyPublicController.do?applyPublic");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 申请公开删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ApplyPublicEntity applyPublic = applyPublicService.getEntity(java.lang.String.valueOf(id));
		message = "申请公开【" + applyPublic.getId() + "】删除成功";
		applyPublicService.delete(applyPublic);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "applyPublicController.do?applyPublic");
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

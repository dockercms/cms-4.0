package com.leimingtech.platform.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.util.StringUtil;

/**
 * 类型字段处理类
 * 
 * @author 
 * 
 */
@Controller
@RequestMapping("/systemAction")
public class SystemAction extends BaseController {
	private static final Logger logger = Logger.getLogger(SystemAction.class);
	private UserService userService;
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

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping(params = "druid")
	public ModelAndView druid() {
		return new ModelAndView(new RedirectView("druid/index.html"));
	}
	

	/**
	 * 类型分组列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "typeGroupList")
	public ModelAndView typeGroupList(HttpServletRequest request,TSTypegroup typegroup) {
				// 获取分组列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(TSTypegroup.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, typegroup, param);
		// 排序条件
		cq.addOrder("createdtime",SortDirection.desc);

		cq.add();
		PageList pageList = userService.getPageList(cq, true);
		List<TSTypegroup> typegroupList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("typegroupList", typegroupList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("searchMap", param);
		m.put("actionUrl", "systemAction.do?typeGroupList");
		return new ModelAndView("lmPage/type/typeGroupList",m);
	}
	/**
	 * 跳转添加或修改分组页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdate(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		TSTypegroup typegroup = null;
		if (StringUtil.isNotEmpty(id)) {
			typegroup = userService.getEntity(TSTypegroup.class, id);
		} else {
			typegroup = new TSTypegroup();
		}
		m.put("typegroup", typegroup);
		return new ModelAndView("lmPage/type/typegroup_saveOrUpdate", m);
	}
	/**
	 * 保存类型分组
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveTypeGroup")
	@ResponseBody
	public String saveTypeGroup(TSTypegroup typegroup, HttpServletRequest request) {
		JSONObject j=new JSONObject();
		if (StringUtil.isNotEmpty(typegroup.getId())) {
			message = "类型分组: " + typegroup.getTypegroupname() + "被更新成功";
			userService.saveOrUpdate(typegroup);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} else {
			message = "类型分组: " + typegroup.getTypegroupname() + "被添加成功";
			typegroup.setCreatedtime(new Date());//创建时间
			userService.save(typegroup);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		//刷新缓存
		systemService.refleshTypeGroupCach();
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "systemAction.do?typeGroupList");
		String str = j.toString();
		return str;
	}
	/**
	 * 删除类型分组
	 * 
	 * @return
	 */
	@RequestMapping(params = "delTypeGroup")
	@ResponseBody
	public String delTypeGroup(TSTypegroup typegroup, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		typegroup = userService.getEntity(TSTypegroup.class, typegroup.getId());
		message = "类型分组: " + typegroup.getTypegroupname() + "被删除 成功";
		userService.delete(typegroup);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		//刷新缓存
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "systemAction.do?typeGroupList");
		String str = j.toString();
		return str;
	}

	/**
	 * 类型列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "typeList")
	public ModelAndView typeList(HttpServletRequest request,TSType type) {
		//获取请求组类型的ID
		String typegroupid = request.getParameter("typegroupid");
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(TSType.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, type, param);
		// 排序条件
		cq.eq("TSTypegroup.id", typegroupid);
		cq.addOrder("createdtime",SortDirection.desc);

		cq.add();
		PageList pageList = userService.getPageList(cq, true);
		List<TSType> typeList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("searchMap", param);
		m.put("typeList", typeList);
		m.put("typegroupid", typegroupid);
		m.put("searchMap", param);
		m.put("actionUrl", "systemAction.do?typeList&typegroupid="+typegroupid);
		return new ModelAndView("lmPage/type/typeList",m);
	}
	
	/**
	 * 跳转添加或修改类型页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addOrUpdateType")
	public ModelAndView addOrUpdateType(HttpServletRequest request) {
		String id = request.getParameter("id");
		String typegroupid = request.getParameter("typegroupid");
		Map<String, Object> m = new HashMap<String, Object>();
		TSType type = null;
		if (StringUtil.isNotEmpty(id)) {
			type = userService.getEntity(TSType.class, id);
		} else {
			type = new TSType();
		}
		request.setAttribute("typegroupid", typegroupid);
		m.put("type", type);
		return new ModelAndView("lmPage/type/type_saveOrUpdate", m);
	}
	
	/**
	 * 保存类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveType")
	@ResponseBody
	public String saveType(TSType type, HttpServletRequest request) {
		JSONObject j=new JSONObject();
		if (StringUtil.isNotEmpty(type.getId())) {
			message = "类型: " + type.getTypename() + "被更新成功";
			userService.saveOrUpdate(type);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} else {

			TSType type1 = userService.findUniqueByProperty(TSType.class, "typename",type.getTypename());
			if (type1 != null) {
				message = "类型: " + type.getTypename() + "已经存在";
			} else {
				type.setCreatedtime(new Date());//创建时间
				userService.save(type);
				message = "类型: " + type.getTypename() + "添加成功";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
				
			}
		}
		//刷新缓存
		systemService.refleshTypeGroupCach();
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "systemAction.do?typeList&typegroupid="+type.getTSTypegroup().getId());
		String str = j.toString();
		return str;
	}
	/**
	 * 删除类型
	 * 
	 * @return
	 */
	@RequestMapping(params = "delType")
	@ResponseBody
	public String delType(TSType type, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		type = userService.getEntity(TSType.class, type.getId());
		message = "类型分组: " + type.getTypename() + "被删除 成功";
		userService.delete(type);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		//刷新缓存
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "systemAction.do?typeList&typegroupid="+type.getTSTypegroup().getId());
		String str = j.toString();
		return str;
	}

	
}

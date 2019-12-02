package com.leimingtech.platform.controller.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.TSFunction;
import com.leimingtech.core.entity.TSIcon;
import com.leimingtech.core.entity.TSOperation;
import com.leimingtech.core.entity.TSRoleFunction;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.comparator.FunctionComparator;
import com.leimingtech.platform.service.MenuInitService;
import com.leimingtech.platform.service.function.FunctionServiceI;

/**
 * 菜单权限处理类
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/menuController")
public class MenuController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MenuController.class);
	private UserService userService;
	private SystemService systemService;
	@Autowired
	private MenuInitService menuInitService;
	private String message = null;
	/** 菜单管理接口 */
	@Autowired
	private FunctionServiceI functionService;

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

	/**
	 * 菜单管理页面
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "menu")
	public ModelAndView menu(HttpServletRequest request) {
		String id = request.getParameter("id");

		TSFunction function = null;
		List<TSFunction> functionList = null;

		if (StringUtils.isEmpty(id) || "-1".equals(id)) {
			// 点击顶级节点
			function = new TSFunction();
			function.setId(null);
			function.setFunctionName("菜单");
			functionList = functionService.getAllRoot();
		} else {
			function = functionService.getEntity(java.lang.String.valueOf(id));
			functionList = functionService.getListByPid(java.lang.String
					.valueOf(id));
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("jstreeData", functionService.getTreeJson(id).toString());
		m.put("function", function);
		m.put("functionList", functionList);

		return new ModelAndView("lmPage/menu/menu", m);
	}

	/**
	 * 菜单管理页面
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "flushMenu")
	public ModelAndView flushMenu(HttpServletRequest request) {
		ModelAndView mav = this.menu(request);
		String id = request.getParameter("id");
		List<String> idList = new ArrayList<String>();
		if (id != null && !"".equals(id)) {
			TSFunction function = menuInitService.get(TSFunction.class, id);
			idList = this.getParenidList(idList, function);
		}

		Map<String, Object> m = mav.getModel();
		m.put("parentIdList", idList);
		return new ModelAndView("lmPage/menu/menuBody", m);
	}

	private List<String> getParenidList(List<String> idList, TSFunction function) {
		if (function != null) {
			idList.add(function.getId());
			if (function.getTSFunction() != null) {
				idList = this.getParenidList(idList, function.getTSFunction());
			}
		}
		return idList;
	}

	/**
	 * 加载下级菜单
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadMenu")
	@ResponseBody
	public JSONArray loadMenu(HttpServletRequest request) {
		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		cq.eq("TSFunction.id", id);
		cq.add();
		List<TSFunction> list = menuInitService.getListByCriteriaQuery(cq,
				false);
		Collections.sort(list, new FunctionComparator());
		JSONArray jsonArray = new JSONArray();
		for (TSFunction function : list) {
			JSONObject json = new JSONObject();
			if (function.getTSFunctions() != null
					&& function.getTSFunctions().size() > 0) {
				json.put("text", function.getFunctionName());
				json.put("value", function.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", function.getId());
				json.put("href",
						"menuController.do?loadMenu&id=" + function.getId());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			} else {
				json.put("text",
						"<i class=\"icon-file-text\" style=\"width: 1em;margin-right: 4px;\"></i>"
								+ function.getFunctionName());
				json.put("value", function.getId());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", function.getId());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}

	/**
	 * 加载下级菜单
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "menuTable")
	public ModelAndView menuTable(HttpServletRequest request) {
		String id = request.getParameter("id");
		TSFunction parentFunction = null;
		List<TSFunction> list = null;
		if (StringUtils.isEmpty(id) || "-1".equals(id)) {
			// 点击顶级菜单
			parentFunction = new TSFunction();
			parentFunction.setId("");
			parentFunction.setFunctionName("顶级菜单");
			parentFunction.setFunctionUrl("");
			parentFunction.setFunctionOrder("0");

			list=functionService.getAllRoot();

		} else {
			parentFunction = menuInitService.getEntity(TSFunction.class, id);
			parentFunction = (parentFunction == null ? new TSFunction()
					: parentFunction);
			list = functionService.getListByPid(id);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("parentFunction", parentFunction);

		m.put("list", list);
		m.put("selectId", id);
		return new ModelAndView("lmPage/menu/menuTable", m);
	}

	@RequestMapping(params = "editModelPage")
	public ModelAndView editModelPage(HttpServletRequest request) {
		String id = request.getParameter("id");
		String selectId = request.getParameter("selectId");
		TSFunction function = null;
		if (id != null && !"".equals(id)) {
			function = menuInitService.getEntity(TSFunction.class, id);
		} else {
			String parentId = request.getParameter("parentId");
			function = new TSFunction();
			TSFunction parentFunction = new TSFunction();
			parentFunction.setId(parentId);
			function.setTSFunction(parentFunction);
		}
		List<TSIcon> iconList = menuInitService.loadAll(TSIcon.class);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("function", function);
		m.put("iconList", iconList);
		m.put("selectId", selectId);
		return new ModelAndView("lmPage/menu/editModel", m);
	}

	@RequestMapping(params = "update")
	@ResponseBody
	public String update(TSFunction function, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (function.getTSFunction() != null
				&& function.getTSFunction().getId().equals("")) {
			function.setTSFunction(null);
		}

		if (StringUtil.isNotEmpty(function.getId())) {
			message = "菜单【" + function.getFunctionName() + "】更新成功";
			TSFunction t = menuInitService.get(TSFunction.class,
					function.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(function, t);
				if (StringUtils.isEmpty(t.getIconclass())) {
					t.setIconclass("icon-folder-close");
				}
				menuInitService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "菜单【" + function.getFunctionName() + "】更新失败";
			}

			if (function.getTSFunction() != null
					&& StringUtils.isNotEmpty(function.getTSFunction().getId()
							+ "")) {
				j.accumulate("toUrl", "menuController.do?menu&id="
						+ function.getTSFunction().getId());
			} else {
				j.accumulate("toUrl", "menuController.do?menu");
			}
		} else {
			message = "菜单【" + function.getFunctionName() + "】添加成功";
			if (function.getTSFunction() != null
					&& StringUtils.isNotEmpty(function.getTSFunction().getId()
							+ "")) {
				function.setFunctionLevel(Short.valueOf("1"));
				j.accumulate("toUrl", "menuController.do?menu&id="
						+ function.getTSFunction().getId());
			} else {
				function.setFunctionLevel(Short.valueOf("0"));
				j.accumulate("toUrl", "menuController.do?menu");
			}
			if (StringUtils.isEmpty(function.getIconclass())) {
				function.setIconclass("icon-folder-close");
			}
			function.setCreatedtime(new Date());// 创建时间
			menuInitService.save(function);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		TSFunction function = menuInitService.getEntity(TSFunction.class, id);
		try {
			message = "菜单【" + function.getFunctionName() + "】删除成功";

			if (function.getTSFunction() != null
					&& StringUtils.isNotEmpty(function.getTSFunction().getId()
							+ "")) {
				j.accumulate("toUrl", "menuController.do?menu&id="
						+ function.getTSFunction().getId());
			} else {
				j.accumulate("toUrl", "menuController.do?menu");
			}

			delChildFun(function);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_DEL);
			j.accumulate("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			message = "菜单【" + function.getFunctionName() + "】删除失败";
			systemService.addLog(message, Globals.Log_Leavel_ERROR,
					Globals.Log_Type_DEL);
			j.accumulate("isSuccess", false);
		}

		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 删除子集菜单
	 * 
	 * @param function
	 */
	public void delChildFun(TSFunction function) {
		if (function != null) {
			List<TSFunction> childFunctions = menuInitService.findByProperty(
					TSFunction.class, "TSFunction.id", function.getId());
			if (childFunctions != null && childFunctions.size() > 0) {
				for (TSFunction tsFunction : childFunctions) {
					delChildFun(tsFunction);
				}
			}
			List<TSRoleFunction> list = menuInitService.findByProperty(
					TSRoleFunction.class, "TSFunction", function);
			menuInitService.deleteAllEntitie(list);

			List<TSOperation> operationList = menuInitService.findByProperty(
					TSOperation.class, "TSFunction", function);
			menuInitService.deleteAllEntitie(operationList);

			menuInitService.delete(function);
		}
	}

}

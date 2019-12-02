package com.leimingtech.platform.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leimingtech.platform.service.function.FunctionServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.json.AjaxJson;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ComboTree;
import com.leimingtech.core.entity.ComboTreeModel;
import com.leimingtech.core.entity.TSFunction;
import com.leimingtech.core.entity.TSOperation;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSRoleFunction;
import com.leimingtech.core.entity.TSRoleUser;
import com.leimingtech.core.entity.TreeGrid;
import com.leimingtech.core.entity.TreeGridModel;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.RoleServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.SetListSort;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.oConvertUtils;
import com.leimingtech.platform.comparator.FunctionComparator;
import com.leimingtech.platform.core.common.model.json.ValidForm;

/**
 * 角色处理类
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/roleController")
public class RoleController extends BaseController {
	
	@Autowired
	private UserService userService;
	/***/
	@Autowired
	private SystemService systemService;
	/**权限管理接口*/
	@Autowired
	private RoleServiceI roleService;
	
	@Autowired
	private FunctionServiceI functionService;

	private String message = null;

	/**
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	public ModelAndView role(TSRole role, HttpServletRequest request) {
		// 获取文章列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(TSRole.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, role, param);
		
		// 排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.add();
		PageList pageList = this.userService.getPageList(cq, true);
		List<TSRole> roleList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("roleList", roleList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "roleController.do?role");
		return new ModelAndView("lmPage/role/roleTab", m);
	}

	/**
	 * 新增角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust) {
		TSRole role = new TSRole();
		reqeust.setAttribute("role", role);
		return new ModelAndView("lmPage/role/add_model");
	}

	/**
	 * 修改角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		TSRole role = userService.getEntity(TSRole.class, id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("role", role);
		return new ModelAndView("lmPage/role/add", m);
	}

	/**
	 * 删除角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest req) {
		JSONObject j = new JSONObject();
		String id = req.getParameter("id");
		TSRole role = userService.getEntity(TSRole.class, id);
		if ("admin".equals(role.getRoleCode())) {
			message = "管理员[admin]角色不可删除";
			j.accumulate("isSuccess", false);
		} else if ("manager".equals(role.getRoleCode())) {
			message = "普通用户[manager]角色不可删除";
			j.accumulate("isSuccess", false);
		} else {
			message = "角色删除成功";
			// 首先要删除与角色关联的权限 菜单权限
			List<TSRoleFunction> roleFunctionList = userService.findByProperty(TSRoleFunction.class, "TSRole.id", id);
			for (TSRoleFunction roleFunction : roleFunctionList) {
				userService.delete(roleFunction);
			}
			//FIXME 区块权限
			// 区块权限
//			List<SectionClassPrivEntity> sectionClassPrivList = systemService.findByProperty(SectionClassPrivEntity.class, "TSRole.id",
//					String.valueOf(id));
//			for (SectionClassPrivEntity sectionClassPriv : sectionClassPrivList) {
//				systemService.delete(sectionClassPriv);
//			}
			List<TSRoleUser>	roleUserList = userService.findByProperty(TSRoleUser.class, "TSRole.id", id);
			for (TSRoleUser roleUser : roleUserList) {
				userService.delete(roleUser);
			}
			userService.delete(role);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			j.accumulate("isSuccess", true);
		}
		j.accumulate("msg", message);
		j.accumulate("toUrl", "roleController.do?role");
		String str = j.toString();
		return str;
	}

	/**
	 * 删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delRole")
	@ResponseBody
	public AjaxJson delRole(TSRole role, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		// 删除角色之前先删除角色权限关系
		delRoleFunction(role);
		role = userService.getEntity(TSRole.class, role.getId());
		userService.delete(role);
		message = "角色: " + role.getRoleName() + "被删除成功";
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		return j;
	}

	/**
	 * 检查角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "checkRole")
	@ResponseBody
	public ValidForm checkRole(TSRole role, HttpServletRequest request, HttpServletResponse response) {
		ValidForm v = new ValidForm();
		String roleCode = oConvertUtils.getString(request.getParameter("param"));
		String code = oConvertUtils.getString(request.getParameter("code"));
		List<TSRole> roles = userService.findByProperty(TSRole.class, "roleCode", roleCode);
		if (roles.size() > 0 && !code.equals(roleCode)) {
			v.setInfo("角色编码已存在");
			v.setStatus("n");
		}
		return v;
	}

	/**
	 * 删除角色权限
	 * 
	 * @param role
	 */
	protected void delRoleFunction(TSRole role) {
		List<TSRoleFunction> roleFunctions = userService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
		if (roleFunctions.size() > 0) {
			for (TSRoleFunction tsRoleFunction : roleFunctions) {
				userService.delete(tsRoleFunction);
			}
		}
		List<TSRoleUser> roleUsers = userService.findByProperty(TSRoleUser.class, "TSRole.id", role.getId());
		for (TSRoleUser tsRoleUser : roleUsers) {
			userService.delete(tsRoleUser);
		}
	}

	/**
	 * 角色录入
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveRole")
	@ResponseBody
	public String saveRole(TSRole role, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(role.getId())) {
			message = "角色: [" + role.getRoleName() + "]  被更新成功";
			TSRole t = userService.get(TSRole.class, role.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(role, t);
				userService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "角色信息更新失败";
			}
		} else {
			message = "角色: [" + role.getRoleName() + "] 被添加成功";
			role.setCreatedtime(new Date());//创建时间
			userService.save(role);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "roleController.do?role");
		String str = j.toString();
		return str;
	}

	/**
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "fun")
	public ModelAndView fun(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		TSRole role = userService.getEntity(TSRole.class, roleId);
		request.setAttribute("role", role);
		return new ModelAndView("lmPage/role/roleSet");
	}

	/**
	 * 加载权限菜单
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadMenu1")
	@ResponseBody
	public JSONArray loadMenu1(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String id = request.getParameter("id");


		List<TSRoleFunction> roleFunctionList = userService.findByProperty(TSRoleFunction.class, "TSRole.id", roleId);
		Set<String> set = new HashSet<String>();
		if (roleFunctionList.size() != 0) {
			for (TSRoleFunction fun : roleFunctionList) {
				if (fun.getTSFunction() != null) {
					set.add(fun.getTSFunction().getId());
				}
			}
		}

		List<TSFunction> list = functionService.getListByPid(id);
		JSONArray jsonArray = new JSONArray();
		for (TSFunction function : list) {
			JSONObject json = new JSONObject();
			// 父节点
			if (function.getFunctionLevel() == 0) {
				json.put("id", function.getId());
				json.put("name", function.getFunctionName());
				json.put("open", false);
				json.put("click", "operat('" + function.getId() + "')");
				json.put("children", getChildren(set, function, roleId));
				if (set.contains(function.getId())) {
					json.put("checked", true);
				} else {
					json.put("checked", false);
				}
				jsonArray.add(json);
			}
		}
		return jsonArray;
	}

	/**
	 * 子节点下含有节点
	 * 
	 * @param set
	 * @param tsfunction
	 * @param roleId
	 * @return
	 */
	public JSONArray getChildren(Set<String> set, TSFunction tsfunction, String roleId) {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<TSFunction> childFunctionList= functionService.getListByPid(tsfunction.getId());
		if (childFunctionList == null || childFunctionList.size() == 0) {
			return jsonArray;
		}

		for (TSFunction tsfunction1 : childFunctionList) {
			json.put("id", tsfunction1.getId());
			json.put("name", tsfunction1.getFunctionName());
			json.put("children", this.getChildren(set, tsfunction1, roleId));
			json.put("click", "operat('" + tsfunction1.getId() + "')");
			if (set.contains(tsfunction1.getId())) {
				json.put("checked", true);
			} else {
				json.put("checked", false);
			}
			jsonArray.add(json);

		}
		return jsonArray;
	}

	/**
	 * 保存角色菜单权限
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveRoleFunction")
	@ResponseBody
	public String saveRoleFunction(TSRoleFunction roleFunction, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String roleId = request.getParameter("roleId");
		String funVal = request.getParameter("funVal");
		String[] funValArray = funVal.split(",");
		TSRole role = userService.getEntity(TSRole.class, roleId);
		List<TSRoleFunction> roleFunctionList = userService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
		String flag = "";
		// 当前角色没有任何菜单
		if (roleFunctionList.size() == 0) {
			for (int i = 0; i < funValArray.length; i++) {
				String funValId = funValArray[i];
				roleFunction = new TSRoleFunction();
				roleFunction.setTSRole(role);// 添加角色ID
				TSFunction tsFunction = userService.getEntity(TSFunction.class, funValId);
				roleFunction.setTSFunction(tsFunction);// 添加菜单ID
				roleFunction.setCreatedtime(new Date());
				userService.saveOrUpdate(roleFunction);
			}
		} else {// 有选中的菜单
			for (int k = 0; k < roleFunctionList.size(); k++) {
				roleFunction = roleFunctionList.get(k);
				String roleFunctionId = "";
				if(null!=roleFunction.getTSFunction()){
					roleFunctionId = roleFunction.getTSFunction().getId();
					flag += roleFunctionId + ",";
					// 去掉已勾选的菜单项
					if (!funVal.contains(roleFunctionId)) {
						userService.delete(roleFunction);
					}
				}
			}
			for (int i = 0; i < funValArray.length; i++) {
				String funValId = funValArray[i];
				if (!flag.contains(funValId)) {
					roleFunction = new TSRoleFunction();
					roleFunction.setTSRole(role);// 添加角色ID
					TSFunction tsFunction = userService.getEntity(TSFunction.class, funValId);
					roleFunction.setTSFunction(tsFunction);// 添加菜单ID
					userService.saveOrUpdate(roleFunction);
				}
			}
		}
		message = "角色为【"+role.getRoleName()+"】菜单权限更新成功";
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "roleController.do?role");
		String str = j.toString();
		return str;
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
		List<TSFunction> list = userService.getListByCriteriaQuery(cq, false);
		JSONArray jsonArray = new JSONArray();
		for (TSFunction function : list) {
			JSONObject json = new JSONObject();
			if (function.getTSFunctions() != null && function.getTSFunctions().size() > 0) {
				json.put("text", function.getFunctionName());
				json.put("value", function.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", function.getId());
				json.put("href", "menuController.do?loadMenu&id=" + function.getId());
				json.put("children", new JSONArray());
			} else {
				json.put("text", function.getFunctionName());
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
	 * 设置权限
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "setAuthority")
	@ResponseBody
	public List<ComboTree> setAuthority(TSRole role, HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if (comboTree.getId() != null) {
			cq.eq("TSFunction.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSFunction");
		}
		cq.notEq("functionLevel", Short.parseShort("-1"));
		cq.add();
		List<TSFunction> functionList = userService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		String roleId = request.getParameter("roleId");
		List<TSFunction> loginActionlist = new ArrayList<TSFunction>();// 已有权限菜单
		role = this.userService.get(TSRole.class, roleId);
		if (role != null) {
			List<TSRoleFunction> roleFunctionList = userService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
			if (roleFunctionList.size() > 0) {
				for (TSRoleFunction roleFunction : roleFunctionList) {
					TSFunction function = (TSFunction) roleFunction.getTSFunction();
					loginActionlist.add(function);
				}
			}
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "functionName", "TSFunctions");
		comboTrees = userService.ComboTree(functionList, comboTreeModel, loginActionlist);
		return comboTrees;
	}

	/**
	 * 更新权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateAuthority")
	@ResponseBody
	public AjaxJson updateAuthority(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		// update--Author:宋双旺 Date:20130414 for：删除角色，点击保存权限报错
		try {
			String roleId = request.getParameter("roleId");
			String rolefunction = request.getParameter("rolefunctions");
			TSRole role = this.userService.get(TSRole.class, roleId);
			List<TSRoleFunction> roleFunctionList = userService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
			Map<String, TSRoleFunction> map = new HashMap<String, TSRoleFunction>();
			for (TSRoleFunction functionOfRole : roleFunctionList) {
				map.put(functionOfRole.getTSFunction().getId(), functionOfRole);
			}
			String[] roleFunctions = rolefunction.split(",");
			Set<String> set = new HashSet<String>();
			for (String s : roleFunctions) {
				set.add(s);
			}
			updateCompare(set, role, map);
			j.setMsg("权限更新成功");
		} catch (Exception e) {
			j.setMsg("权限更新失败");
		}
		return j;
	}

	/**
	 * 权限比较
	 * 
	 * @param set
	 *            最新的权限列表
	 * @param role
	 *            当前角色
	 * @param map
	 *            旧的权限列表
	 * @param entitys
	 *            最后保存的权限列表
	 */
	private void updateCompare(Set<String> set, TSRole role, Map<String, TSRoleFunction> map) {
		List<TSRoleFunction> entitys = new ArrayList<TSRoleFunction>();
		List<TSRoleFunction> deleteEntitys = new ArrayList<TSRoleFunction>();
		for (String s : set) {
			if (map.containsKey(s)) {
				map.remove(s);
			} else {
				TSRoleFunction rf = new TSRoleFunction();
				TSFunction f = this.userService.get(TSFunction.class, s);
				rf.setTSFunction(f);
				rf.setTSRole(role);
				entitys.add(rf);
			}
		}
		Collection<TSRoleFunction> collection = map.values();
		Iterator<TSRoleFunction> it = collection.iterator();
		for (; it.hasNext();) {
			deleteEntitys.add(it.next());
		}
		
		userService.batchSave(entitys);
		userService.deleteAllEntitie(deleteEntitys);

	}

	/**
	 * 角色页面跳转
	 * 
	 * @param role
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSRole role, HttpServletRequest req) {
		if (role.getId() != null) {
			role = userService.getEntity(TSRole.class, role.getId());
			req.setAttribute("role", role);
		}
		return new ModelAndView("system/role/role");
	}

	/**
	 * 权限操作列表
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "setOperate")
	@ResponseBody
	public List<TreeGrid> setOperate(HttpServletRequest request, TreeGrid treegrid) {
		String roleId = request.getParameter("roleId");
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if (treegrid.getId() != null) {
			cq.eq("TSFunction.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("TSFunction");
		}
		cq.add();
		List<TSFunction> functionList = userService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		Collections.sort(functionList, new SetListSort());
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setRoleid(roleId);
		treeGrids = userService.treegrid(functionList, treeGridModel);
		return treeGrids;

	}

	/**
	 * 操作录入
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveOperate")
	@ResponseBody
	public AjaxJson saveOperate(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String fop = request.getParameter("fp");
		String roleId = request.getParameter("roleId");
		// 录入操作前清空上一次的操作数据
		clearp(roleId);
		String[] fun_op = fop.split(",");
		String aa = "";
		String bb = "";
		// 只有一个被选中
		if (fun_op.length == 1) {
			bb = fun_op[0].split("_")[1];
			aa = fun_op[0].split("_")[0];
			savep(roleId, bb, aa);
		} else {
			// 至少2个被选中
			for (int i = 0; i < fun_op.length; i++) {
				String cc = fun_op[i].split("_")[0]; // 操作id
				if (i > 0 && bb.equals(fun_op[i].split("_")[1])) {
					aa += "," + cc;
					if (i == (fun_op.length - 1)) {
						savep(roleId, bb, aa);
					}
				} else if (i > 0) {
					savep(roleId, bb, aa);
					aa = fun_op[i].split("_")[0]; // 操作ID
					if (i == (fun_op.length - 1)) {
						bb = fun_op[i].split("_")[1]; // 权限id
						savep(roleId, bb, aa);
					}

				} else {
					aa = fun_op[i].split("_")[0]; // 操作ID
				}
				bb = fun_op[i].split("_")[1]; // 权限id

			}
		}

		return j;
	}

	/**
	 * 更新操作
	 * 
	 * @param roleId
	 * @param functionid
	 * @param ids
	 */
	public void savep(String roleId, String functionid, String ids) {
		// String hql = "from TSRoleFunction t where" + " t.TSRole.id=" +
		// oConvertUtils.getInt(roleId,0)
		// + " " + "and t.TSFunction.id=" + oConvertUtils.getInt(functionid,0);
		CriteriaQuery cq = new CriteriaQuery(TSRoleFunction.class);
		cq.eq("TSRole.id", roleId);
		cq.eq("TSFunction.id", functionid);
		cq.add();
		List<TSRoleFunction> rFunctions = userService.getListByCriteriaQuery(cq, false);
		if (rFunctions.size() > 0) {
			TSRoleFunction roleFunction = rFunctions.get(0);
			roleFunction.setOperation(ids);
			userService.saveOrUpdate(roleFunction);
		}
	}

	/**
	 * 清空操作
	 * 
	 * @param roleId
	 */
	public void clearp(String roleId) {
		List<TSRoleFunction> rFunctions = userService.findByProperty(TSRoleFunction.class, "TSRole.id", roleId);
		if (rFunctions.size() > 0) {
			for (TSRoleFunction tRoleFunction : rFunctions) {
				tRoleFunction.setOperation(null);
				userService.saveOrUpdate(tRoleFunction);
			}
		}
	}

	/**
	 * 按钮权限展示
	 * 
	 * @param request
	 * @param functionId
	 * @param roleId
	 * @return
	 */
	@RequestMapping(params = "operationListForFunction")
	public ModelAndView operationListForFunction(HttpServletRequest request, String functionId, String roleId) {
		CriteriaQuery cq = new CriteriaQuery(TSOperation.class);
		cq.eq("TSFunction.id", functionId);
		cq.add();
		List<TSOperation> operationList = this.userService.getListByCriteriaQuery(cq, false);
		Set<String> operationCodes = systemService.getOperationCodesByRoleIdAndFunctionId(roleId, functionId);
		request.setAttribute("operationList", operationList);
		request.setAttribute("operationcodes", operationCodes);
		request.setAttribute("functionId", functionId);
		return new ModelAndView("system/role/operationListForFunction");
	}

	/**
	 * 更新按钮权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateOperation")
	@ResponseBody
	public AjaxJson updateOperation(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String roleId = request.getParameter("roleId");
		String functionId = request.getParameter("functionId");
		String operationcodes = request.getParameter("operationcodes");
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", roleId);
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = userService.getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			tsRoleFunction.setOperation(operationcodes);
			userService.saveOrUpdate(tsRoleFunction);
		}
		j.setMsg("按钮权限更新成功");
		return j;
	}
}

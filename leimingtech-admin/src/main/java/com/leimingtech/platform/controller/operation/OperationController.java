package com.leimingtech.platform.controller.operation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
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
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSRoleFunction;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.operation.OperationEntity;
import com.leimingtech.platform.service.operation.OperationServiceI;

/**
 * @Title: Controller
 * @Description: 功能按钮管理
 * @author linjm 20140402
 * @date 2014-04-16 13:41:11
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/operationController")
public class OperationController extends BaseController {

	@Autowired
	private OperationServiceI operationService;
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
	 * 功能按钮管理列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(OperationEntity operation,
			HttpServletRequest request) {
		// 获取功能按钮管理列表
		String functionid = request.getParameter("functionid");
		TSFunction tsfunction = operationService.get(TSFunction.class,
				functionid);
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(OperationEntity.class, pageSize,
				pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, operation, param);
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.operationService.getPageList(cq, true);
		List<OperationEntity> testList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("functionid", functionid);
		if (tsfunction != null) {
			m.put("functionname", tsfunction.getFunctionName());
		}
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "operationController.do?table");
		return new ModelAndView("lmPage/operation/operationList", m);
	}

	/**
	 * 功能按钮管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust) {
		String functionid = reqeust.getParameter("functionid");
		Map<String, Object> m = new HashMap<String, Object>();
		List<TSIcon> iconlist = operationService.getList(TSIcon.class);
		m.put("functionid", functionid);
		m.put("iconlist", iconlist);
		m.put("page", new OperationEntity());
		return new ModelAndView("lmPage/operation/operation", m);
	}

	/**
	 * 功能按钮管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		String functionid = reqeust.getParameter("functionid");
		OperationEntity operation = operationService.getEntity(
				OperationEntity.class, id);
		Map<String, Object> m = new HashMap<String, Object>();
		List<TSIcon> iconlist = operationService.getList(TSIcon.class);
		m.put("functionid", functionid);
		m.put("iconlist", iconlist);
		m.put("page", operation);
		return new ModelAndView("lmPage/operation/operation", m);
	}

	/**
	 * 功能按钮管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(OperationEntity operation,
			HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String functionid = request.getParameter("functionid");
		if (StringUtil.isNotEmpty(operation.getId())) {
			message = "功能按钮管理[" + operation.getOperationname() + "]更新成功";
			OperationEntity t = operationService.get(OperationEntity.class,
					operation.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(operation, t);
				operationService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "功能按钮管理[" + operation.getOperationname() + "]更新失败";
			}
		} else {
			message = "功能按钮管理[" + operation.getOperationname() + "]添加成功";
			if (functionid != null && !"".equals(functionid)) {
				operation.setFunctionid(functionid);
			}
			operation.setCreatedtime(new Date());// 创建时间
			operationService.save(operation);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		// j.accumulate("toUrl", "operationController.do?table");
		j.accumulate("toUrl", "operationController.do?table&functionid="
				+ functionid);
		String str = j.toString();
		return str;
	}

	/**
	 * 功能按钮管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request,String funId) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		OperationEntity operation = operationService.getEntity(
				OperationEntity.class, id);
		
		
		message = "功能按钮管理[" + operation.getOperationname() + "]删除成功";
		operationService.delete(operation);
		
		//更新cms_role_function表中数据
		List<TSRoleFunction> list = operationService.findByProperty(TSRoleFunction.class, "TSFunction.id", operation.getFunctionid());
		String operateUrls = list.get(0).getOperation();
		String[] ou = operateUrls.split(",");
		List ls1 = java.util.Arrays.asList(ou);
		List ls=new ArrayList<>(ls1);
		int k = -1;
		for (int i = 0; i < ls.size(); i++) {
			if ((operation.getOperationurl()).equals(ls.get(i))) {
				k = i;
			}
		}
		ls.remove(ls.get(k));
		String li = StringUtils.join(ls.toArray(), ",");
		if(li==null||li.equals("")){
			operationService.delete(list.get(0));
		}else {
			list.get(0).setOperation(li);
		}
		
		
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "operationController.do?table&functionid="
				+ operation.getFunctionid());
		String str = j.toString();
		return str;
	}

	/**
	 * 获取菜单中的功能列表
	 * 
	 * @param request
	 * @param funId
	 * @return
	 */
	@RequestMapping(params = "getFunOperationList")
	public ModelAndView getFunOperationList(HttpServletRequest request,
			String funId, String roleId) {

		CriteriaQuery cq = new CriteriaQuery(OperationEntity.class);
		// cq.eq("status", 1);
		cq.eq("functionid", funId);
		cq.add();
		List<OperationEntity> operateList = operationService
				.getListByCriteriaQuery(cq, false);

		CriteriaQuery cq2 = new CriteriaQuery(TSRoleFunction.class);
		// cq.eq("status", 1);
		cq2.eq("TSFunction.id", funId);
		cq2.eq("TSRole.id", roleId);
		cq2.add();

		List<TSRoleFunction> roleFunctionList = operationService
				.getListByCriteriaQuery(cq2, false);
		Set<String> set = new HashSet<String>();
		if (roleFunctionList.size() != 0) {
			for (TSRoleFunction fun : roleFunctionList) {
				if (StringUtils.isNotEmpty(fun.getOperation())) {
					String[] operationurls = fun.getOperation().split(",");
					for (int j = 0; j < operationurls.length; j++) {
						if (StringUtil.isNotEmpty(operationurls[j])) {
							set.add(operationurls[j]);
						}
					}
				}
			}
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("operateList", operateList);
		m.put("operatePrivUrl", set);
		m.put("funId", funId);
		m.put("roleId", roleId);

		return new ModelAndView("lmPage/role/operateList", m);
	}

	/**
	 * 保存功能权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveOperate")
	@ResponseBody
	public String saveOperate(HttpServletRequest request, String funId,
			String roleId) {
		JSONObject j = new JSONObject();
		String[] operateUrls = request.getParameterValues("privUrls");


			CriteriaQuery cq = new CriteriaQuery(TSRoleFunction.class);
			// cq.eq("status", 1);
			cq.eq("TSFunction.id", funId);
			cq.eq("TSRole.id", roleId);
			cq.add();

			List<TSRoleFunction> roleFunctionList = operationService
					.getListByCriteriaQuery(cq, false);
			if (roleFunctionList != null && roleFunctionList.size() > 0) {
				TSRoleFunction roleFun = roleFunctionList.get(0);
				roleFun.setOperation(StringUtils.join(operateUrls, ","));
				operationService.saveOrUpdate(roleFun);
			} else {
				TSRoleFunction roleFun = new TSRoleFunction();
				TSFunction tsFunction = operationService.getEntity(
						TSFunction.class, funId);
				roleFun.setTSFunction(tsFunction);

				TSRole tsRole = operationService
						.getEntity(TSRole.class, roleId);
				roleFun.setTSRole(tsRole);

				roleFun.setOperation(StringUtils.join(operateUrls, ","));
				roleFun.setCreatedtime(new Date());// 创建时间
				operationService.save(roleFun);

				savePfun(tsFunction, tsRole);
			}
			message = "功能权限保存成功！";
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_UPDATE);


		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 如果添加了功能按钮权限也相应具备了菜单权限已经父级菜单权限
	 * 
	 * @param tsFunction
	 * @param tsRole
	 */
	private void savePfun(TSFunction tsFunction, TSRole tsRole) {
		if (tsFunction.getTSFunction() != null
				&& StringUtils.isNotEmpty(tsFunction.getTSFunction().getId())) {
			CriteriaQuery cq = new CriteriaQuery(TSRoleFunction.class);
			// cq.eq("status", 1);
			cq.eq("TSFunction.id", tsFunction.getTSFunction().getId());
			cq.eq("TSRole.id", tsRole.getId());
			cq.add();

			List<TSRoleFunction> roleFunctionList = operationService
					.getListByCriteriaQuery(cq, false);
			if (roleFunctionList == null || roleFunctionList.size() == 0) {
				TSRoleFunction roleFun = new TSRoleFunction();
				roleFun.setTSRole(tsRole);
				roleFun.setTSFunction(tsFunction.getTSFunction());
				roleFun.setCreatedtime(new Date());// 创建时间
				operationService.save(roleFun);
				savePfun(tsFunction.getTSFunction(), tsRole);
			}
		}

	}
}

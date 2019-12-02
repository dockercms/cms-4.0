package com.leimingtech.cms.controller.workflow;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.WorkFlowEntity;
import com.leimingtech.core.entity.WorkFlowStepEntity;
import com.leimingtech.core.entity.WorkflowAuditingEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.service.WorkFlowServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
/**   
 * @Title: Controller
 * @Description: 工作流
 * @author zhangdaihao
 * @date 2014-04-22 15:26:54
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/workFlowController")
public class WorkFlowController extends BaseController {

	@Autowired
	private WorkFlowServiceI workFlowService;
	@Autowired
	private UserService userService;
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
	 * 工作流列表页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "workFlow")
	public ModelAndView workFlow(WorkFlowEntity workFlow,HttpServletRequest request) {
		// 获取分组列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(WorkFlowEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, workFlow, param);

		cq.eq("siteid",PlatFormUtil.getSessionSiteId());
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = workFlowService.getPageList(cq, true);
		List<WorkFlowEntity> workFlowList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("workFlowList", workFlowList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "workFlowController.do?workFlow");
		return new ModelAndView("cms/workflow/workFlowList",m);
	}

	/**
	 * 工作流添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdate(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		WorkFlowEntity workFlow = null;
		if (StringUtil.isNotEmpty(id)) {
			workFlow = workFlowService.getEntity(WorkFlowEntity.class, id);
		} else {
			workFlow = new WorkFlowEntity();
		}
		m.put("workFlow", workFlow);
		return new ModelAndView("cms/workflow/workFlow", m);
	}
	
	/**
	 * 工作流保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(WorkFlowEntity workFlow, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(workFlow.getId())) {
			message = "工作流["+workFlow.getName()+"]更新成功";
			WorkFlowEntity t = workFlowService.get(WorkFlowEntity.class, workFlow.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(workFlow, t);
				workFlowService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "工作流["+workFlow.getName()+"]更新失败";
			}
		} else {
			message = "工作流["+workFlow.getName()+"]添加成功";
			//获取当前站点id
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			workFlow.setSiteid(site.getId());
			workFlow.setCreatedtime(new Date());//创建时间
			workFlowService.save(workFlow);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "workFlowController.do?workFlow");
		String str = j.toString();
		return str;
	}

	/**
	 * 工作流删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(WorkFlowEntity workFlow,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		workFlow = workFlowService.getEntity(WorkFlowEntity.class, workFlow.getId());
		message = "工作流["+workFlow.getName()+"]删除成功";
		workFlowService.delMain(workFlow);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "workFlowController.do?workFlow");
		String str = j.toString();
		return str;
	}
	/**
	 * 工作流   页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "workFlowStep")
	public ModelAndView workFlowStep(WorkFlowStepEntity workFlowStep,HttpServletRequest request) {
		// 获取分组列表
		String gid = request.getParameter("gid");
		
		CriteriaQuery cq = new CriteriaQuery(WorkFlowStepEntity.class);
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, workFlowStep, param);
		// 排序条件
		cq.eq("gid", gid);
		cq.addOrder("step", SortDirection.asc);
		// 排序条件
		cq.add();
		PageList pageList = workFlowService.getPageList(cq, false);
		
		List<WorkFlowStepEntity> workFlowStepList = pageList.getResultList();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("maprole", userService.mapByRoleId());
		m.put("searchMap", param);
		m.put("workFlowStepList", workFlowStepList);
		m.put("gid", gid);
		m.put("actionUrl", "workFlowController.do?workFlowStep");
		return new ModelAndView("cms/workflow/workFlowStepList",m);
	}
	
	/**
	 * 工作流跳转添加或修改页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addOrUpdateSub")
	public ModelAndView addOrUpdateType(HttpServletRequest request) {
		String id = request.getParameter("id");
		String gid = request.getParameter("gid");
		List<TSRole>  cmstoplist=userService.getList(TSRole.class);
		Map<String, Object> m = new HashMap<String, Object>();
		WorkFlowStepEntity workFlowStep = null;
		if (StringUtil.isNotEmpty(id)) {
			workFlowStep = workFlowService.getEntity(WorkFlowStepEntity.class, id);
		} else {
			workFlowStep = new WorkFlowStepEntity();
		}
		request.setAttribute("gid", gid);
		m.put("cmstoplist", cmstoplist);
		m.put("workFlowStep", workFlowStep);
		return new ModelAndView("cms/workflow/workFlowStep", m);
	}
	/**
	 * 工作流保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveSub")
	@ResponseBody
	public String saveOrUpdate(WorkFlowStepEntity workFlowStep, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		int i=0;
		if (StringUtil.isNotEmpty(workFlowStep.getId())) {
			message = "工作流["+workFlowStep.getId()+"]更新成功";
			WorkFlowStepEntity t = workFlowService.get(WorkFlowStepEntity.class, workFlowStep.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(workFlowStep, t);
				workFlowService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "工作流["+workFlowStep.getId()+"]更新失败";
			}
		} else {
			message = "工作流["+workFlowStep.getId()+"]添加成功";
			i=workFlowService.getflowsteaplist(workFlowStep.getGid()+"");
			workFlowStep.setStep(i+1);
			//获取当前站点id
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			workFlowStep.setSiteid(site.getId());
			workFlowStep.setCreatedtime(new Date());//创建时间
			workFlowService.save(workFlowStep);
			WorkFlowEntity t = workFlowService.get(WorkFlowEntity.class, workFlowStep.getGid());
			t.setSteps(i+1);
			workFlowService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "workFlowController.do?workFlowStep&gid="+workFlowStep.getGid());
		String str = j.toString();
		return str;
	}

	/**
	 * 工作流删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "delSub")
	@ResponseBody
	public String del(WorkFlowStepEntity workFlowStep,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		workFlowStep = workFlowService.getEntity(WorkFlowStepEntity.class, workFlowStep.getId());
		message = "工作流["+workFlowStep.getId()+"]删除成功";
		workFlowService.delete(workFlowStep);
		int jo=workFlowService.getflowsteaplist(workFlowStep.getGid()+"");
		WorkFlowEntity t = workFlowService.get(WorkFlowEntity.class, workFlowStep.getGid());
		t.setSteps(jo);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "workFlowController.do?workFlowStep&gid="+workFlowStep.getGid());
		String str = j.toString();
		return str;
	}
	/**
	 * 工作流下拉框
	 * 
	 * @return
	 */
	@RequestMapping(params = "flowOpinions")
	@ResponseBody
	public String flowOpinions(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		List<WorkFlowEntity> workflowList = workFlowService.findByProperty(WorkFlowEntity.class, "siteid", client.getSite().getId());
		//j.accumulate("isSuccess", true);
		j.accumulate("flowOpinions", workflowList);
		//j.accumulate("toUrl", "workFlowController.do?workFlow");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 查看评审记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "workFlowStepLook")
	public ModelAndView workFlowStepLook(HttpServletRequest request) {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		//and (delflag!=-1 or delflag is null)查看当前审核记录
		//查看所有审核记录
		String hql = "from WorkflowAuditingEntity where relationid="+id+" and type="+Integer.valueOf(type)+" and auditingresult is not null order by step asc";
		List<WorkflowAuditingEntity> auditList = workFlowService.findByQueryString(hql);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("auditList", auditList);
		m.put("maprole",userService.mapByRoleId());
		return new ModelAndView("cms/workflow/workFlowStepLook", m);
	}
	
}

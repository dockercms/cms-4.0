package com.leimingtech.cms.controller.sensitivity;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.entity.sensitivity.SensitivityEntity;
import com.leimingtech.cms.service.SensitivityServiceI;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 敏感词管理
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-21 14:49:00
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sensitivityController")
public class SensitivityController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SensitivityController.class);

	@Autowired
	private SensitivityServiceI sensitivityService;
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
	 * 敏感词管理列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(SensitivityEntity sensitivity, HttpServletRequest request) {
		//获取敏感词管理列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(SensitivityEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		sensitivity.setSiteid(site.getId());
		HqlGenerateUtil.installHql(cq, sensitivity, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.sensitivityService.getPageList(cq, true);
		List<SensitivityEntity> testList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "sensitivityController.do?table");
		return new ModelAndView("cms/sensitivity/sensitivityList", m);
	}

	/**
	 * 敏感词管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new SensitivityEntity());
		return new ModelAndView("cms/sensitivity/sensitivity", m);
	}
	
	/**
	 * 敏感词管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		SensitivityEntity sensitivity = sensitivityService.getEntity(SensitivityEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", sensitivity);
		return new ModelAndView("cms/sensitivity/sensitivity", m);
	}

	/**
	 * 敏感词管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(SensitivityEntity sensitivity, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(sensitivity.getId())) {
			message = "敏感词管理["+sensitivity.getSearch()+"]更新成功";
			SensitivityEntity t = sensitivityService.get(SensitivityEntity.class, sensitivity.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sensitivity, t);
				sensitivityService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "敏感词管理["+sensitivity.getSearch()+"]更新失败";
			}
		} else {
			message = "敏感词管理["+sensitivity.getSearch()+"]添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			sensitivity.setSiteid(site.getId());
			sensitivity.setCreatedtime(new Date());//创建时间
			sensitivityService.save(sensitivity);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sensitivityController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 敏感词管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		SensitivityEntity sensitivity = sensitivityService.getEntity(SensitivityEntity.class, String.valueOf(id));
		message = "敏感词管理["+sensitivity.getSearch()+"]删除成功";
		sensitivityService.delete(sensitivity);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sensitivityController.do?table");
		String str = j.toString();
		return str;
	}
}

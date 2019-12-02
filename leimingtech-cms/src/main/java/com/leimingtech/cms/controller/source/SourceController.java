package com.leimingtech.cms.controller.source;
import java.io.UnsupportedEncodingException;
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

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SourceServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PinyinUtil;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 来源管理
 * @author 
 * @date 2014-05-17 12:28:09
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sourceController")
public class SourceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SourceController.class);

	@Autowired
	private SourceServiceI sourceService;
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
	 * 来源管理列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(SourceEntity source, HttpServletRequest request) {
		//获取来源管理列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(SourceEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		source.setSiteId(site.getId());
		HqlGenerateUtil.installHql(cq, source, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		PageList pageList = this.sourceService.getPageList(cq, true);
		List<SourceEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "sourceController.do?table");
		return new ModelAndView("cms/source/sourceList", m);
	}

	/**
	 * 来源管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new SourceEntity());
		return new ModelAndView("cms/source/source", m);
	}
	
	/**
	 * 来源管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		SourceEntity source = sourceService.getEntity(SourceEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", source);
		return new ModelAndView("cms/source/source", m);
	}

	/**
	 * 来源管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(SourceEntity source, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(source.getId())) {
			message = "来源管理["+source.getName()+"]更新成功";
			SourceEntity t = sourceService.get(SourceEntity.class, source.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(source, t);
				sourceService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "来源管理["+source.getName()+"]更新失败";
			}
		} else {
			message = "来源管理["+source.getName()+"]添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			source.setSiteId(site.getId());
			source.setCreatedtime(new Date());
			sourceService.save(source);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sourceController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 来源管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		SourceEntity source = sourceService.getEntity(SourceEntity.class, String.valueOf(id));
		message = "来源管理["+source.getName()+"]删除成功";
		sourceService.delete(source);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sourceController.do?table");
		String str = j.toString();
		return str;
	}
	
	
	/**
	 * 获取名称拼音
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "pyChange")
	@ResponseBody
	public String pyChange(HttpServletRequest request) {
		String name = request.getParameter("name").trim();
		String str = "";
		try {
			str = java.net.URLDecoder.decode(name, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String quanpin = PinyinUtil.hanziToPinyin(str, "");// 全拼
		String acronym = PinyinUtil.getPinYinHeadChar(str);// 首字母
		JSONObject j = new JSONObject();
		j.accumulate("quanpin", quanpin);
		j.accumulate("acronym", acronym);
		str = j.toString();
		return str;
	}
	
}

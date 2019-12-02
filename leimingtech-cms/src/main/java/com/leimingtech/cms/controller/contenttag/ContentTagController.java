package com.leimingtech.cms.controller.contenttag;
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
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ContentTagServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: Tag标签管理
 * @author 
 * @date 2014-05-17 12:02:49
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/contentTagController")
public class ContentTagController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ContentTagController.class);

	@Autowired
	private ContentTagServiceI contentTagService;
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
	 * Tag标签管理列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(ContentTagEntity contentTag, HttpServletRequest request) {
		//获取Tag标签管理列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(ContentTagEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		contentTag.setSiteId(site.getId());
		HqlGenerateUtil.installHql(cq, contentTag, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.contentTagService.getPageList(cq, true);
		List<ContentTagEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "contentTagController.do?table");
		return new ModelAndView("cms/contenttag/contentTagList", m);
	}

	/**
	 * Tag标签管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new ContentTagEntity());
		return new ModelAndView("cms/contenttag/contentTag", m);
	}
	
	/**
	 * Tag标签管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ContentTagEntity contentTag = contentTagService.getEntity(ContentTagEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", contentTag);
		return new ModelAndView("cms/contenttag/contentTag", m);
	}

	/**
	 * Tag标签管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(ContentTagEntity contentTag, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(contentTag.getId())) {
			message = "Tag标签管理["+contentTag.getTagName()+"]更新成功";
			ContentTagEntity t = contentTagService.get(ContentTagEntity.class, contentTag.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contentTag, t);
				contentTagService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "Tag标签管理更新失败";
			}
		} else {
			message = "Tag标签管理["+contentTag.getTagName()+"]添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			contentTag.setSiteId(site.getId());
			contentTag.setCreatedtime(new Date());//创建时间
			contentTagService.save(contentTag);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentTagController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * Tag标签管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ContentTagEntity contentTag = contentTagService.getEntity(ContentTagEntity.class, String.valueOf(id));
		message = "Tag标签管理["+contentTag.getTagName()+"]删除成功";
		contentTagService.delete(contentTag);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentTagController.do?table");
		String str = j.toString();
		return str;
	}
}

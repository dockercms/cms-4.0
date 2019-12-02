package com.leimingtech.platform.controller;
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
import com.leimingtech.core.entity.PfConfigEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.WaterMarkEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.WaterMarkServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;


/**   
 * @Title: Controller
 * @Description: 水印方案
 * @author 
 * @date 2014-05-16 13:56:39
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/waterMarkController")
public class WaterMarkController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WaterMarkController.class);

	@Autowired
	private WaterMarkServiceI waterMarkService;
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
	 * 水印方案列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(WaterMarkEntity waterMark, HttpServletRequest request) {
		//获取水印方案列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(WaterMarkEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		waterMark.setSiteId(site.getId());
		HqlGenerateUtil.installHql(cq, waterMark, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.add();
		
		PageList pageList = this.waterMarkService.getPageList(cq, true);
		List<WaterMarkEntity> testList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, String> cache = PfConfigEntity.pfconfigCache;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("isMark", cache.get("watermark"));
		m.put("actionUrl", "waterMarkController.do?table");
		return new ModelAndView("platform/watermark/waterMarkList", m);
	}

	/**
	 * 水印方案添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new WaterMarkEntity());
		return new ModelAndView("platform/watermark/waterMark", m);
	}
	
	/**
	 * 水印方案更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		WaterMarkEntity waterMark = waterMarkService.getEntity(WaterMarkEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", waterMark);
		return new ModelAndView("platform/watermark/waterMark", m);
	}

	/**
	 * 水印方案保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(WaterMarkEntity waterMark, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(waterMark.getId())) {
			message = "水印方案["+waterMark.getName()+"]更新成功";
			WaterMarkEntity t = waterMarkService.get(WaterMarkEntity.class, waterMark.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(waterMark, t);
				waterMarkService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "水印方案["+waterMark.getName()+"]更新失败";
			}
		} else {
			message = "水印方案["+waterMark.getName()+"]添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			waterMark.setSiteId(site.getId());
			waterMark.setDefaultset(1);
			waterMark.setCreatedtime(new Date());//创建时间
			waterMarkService.save(waterMark);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "waterMarkController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 水印方案删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		WaterMarkEntity waterMark = waterMarkService.getEntity(WaterMarkEntity.class, String.valueOf(id));
		message = "水印方案["+waterMark.getName()+"]删除成功";
		waterMarkService.delete(waterMark);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "waterMarkController.do?table");
		String str = j.toString();
		return str;
	}
}

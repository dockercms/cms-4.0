package com.leimingtech.mobile.controller.advertisemen;
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
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.entity.advertisemen.AdvertisemenStartingEntity;
import com.leimingtech.mobile.service.advertisemen.AdvertisemenStartingServiceI;

/**   
 * @Title: Controller
 * @Description: 启动画面广告
 * @author 
 * @date 2014-08-20 21:04:40
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/advertisemenStartingController")
public class AdvertisemenStartingController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AdvertisemenStartingController.class);

	@Autowired
	private AdvertisemenStartingServiceI advertisemenStartingService;
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
	 * 启动画面广告列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "advertisemenStarting")
	public ModelAndView advertisemenStarting(AdvertisemenStartingEntity advertisemenStarting, HttpServletRequest request) {
		//获取启动画面广告列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(AdvertisemenStartingEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, advertisemenStarting, param);
		//排序条件
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("createdtime",SortDirection.desc);
		cq.add();
		PageList pageList = this.advertisemenStartingService.getPageList(cq, true);
		List<AdvertisemenStartingEntity> resultList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "advertisemenStartingController.do?advertisemenStarting");
		return new ModelAndView("mobile/advertisemen/advertisemenStartingList", m);
	}

	/**
	 * 启动画面广告添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("advertisemenStarting", new AdvertisemenStartingEntity());
		return new ModelAndView("mobile/advertisemen/advertisemenStarting", m);
	}
	
	/**
	 * 启动画面广告更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		AdvertisemenStartingEntity advertisemenStarting = advertisemenStartingService.getEntity(AdvertisemenStartingEntity.class,  id );
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("advertisemenStarting", advertisemenStarting);
		return new ModelAndView("mobile/advertisemen/advertisemenStarting", m);
	}
	

	/**
	 * 启动画面广告保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(AdvertisemenStartingEntity advertisemenStarting, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(advertisemenStarting.getId())) {
			message = "启动画面广告更新成功";
			AdvertisemenStartingEntity t = advertisemenStartingService.get(AdvertisemenStartingEntity.class, advertisemenStarting.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(advertisemenStarting, t);
				advertisemenStartingService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "启动画面广告更新失败";
			}
		} else {
			message = "启动画面广告添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			advertisemenStarting.setSiteId(site.getId());
			advertisemenStarting.setCreatedtime(new Date());
			advertisemenStartingService.save(advertisemenStarting);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "advertisemenStartingController.do?advertisemenStarting");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 启动画面广告删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		AdvertisemenStartingEntity advertisemenStarting = advertisemenStartingService.getEntity(AdvertisemenStartingEntity.class,id);
		message = "启动画面广告删除成功";
		advertisemenStartingService.delete(advertisemenStarting);
		systemService.addLog(message, Globals.Log_Leavel_INFO ,Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "advertisemenStartingController.do?advertisemenStarting");
		String str = j.toString();
		return str;
	}
}

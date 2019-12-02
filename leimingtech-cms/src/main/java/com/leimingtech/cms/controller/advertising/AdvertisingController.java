package com.leimingtech.cms.controller.advertising;

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

import com.leimingtech.cms.entity.advertising.AdvertisingEntity;
import com.leimingtech.cms.entity.advertising.AdvertisingSpaceEntity;
import com.leimingtech.cms.service.advertising.AdvertisingServiceI;
import com.leimingtech.cms.service.advertising.AdvertisingSpaceServiceI;
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
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**
 * 广告管理
 * 
 * @author liuzhen 2014年8月6日 20:13:18
 * 
 */
@Controller
@RequestMapping("/advertisingController")
public class AdvertisingController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AdvertisingController.class);

	@Autowired
	private AdvertisingServiceI advertisingService;

	@Autowired
	private AdvertisingSpaceServiceI advertisingSpaceService;// 广告位管理接口
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
	 * 广告列表 指定栏位
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		// 获取分组列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		AdvertisingSpaceEntity advertisingSpace = null;

		CriteriaQuery cq = new CriteriaQuery(AdvertisingEntity.class, pageSize, pageNo, "", "");

		cq.eq("siteId", PlatFormUtil.getSessionSiteId());
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			cq.eq("advertisingSpace.id", String.valueOf(id));
			advertisingSpace = advertisingSpaceService.getEntity(AdvertisingSpaceEntity.class, String.valueOf(id));// 获取全部广告版位
		}

		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.addOrder("adWeight", SortDirection.desc);
		cq.add();
		PageList pageList = advertisingService.getPageList(cq, true);
		List<AdvertisingEntity> advertisingList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("advertisingList", advertisingList);
		m.put("advertisingSpace", advertisingSpace);// 广告版位
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("pid", id);
		m.put("actionUrl", "advertisingController.do?list&id=" + id);
		return new ModelAndView("cms/advertising/advList", m);
	}

	/**
	 * 字典类型列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "advertising")
	public ModelAndView advertising(AdvertisingEntity advertising, HttpServletRequest request) {
		// 获取分组列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(AdvertisingEntity.class, pageSize, pageNo, "", "");

		// 查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		advertising.setSiteId(site.getId());
		HqlGenerateUtil.installHql(cq, advertising, param);
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = advertisingService.getPageList(cq, true);
		List<AdvertisingEntity> advertisingList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		List<AdvertisingSpaceEntity> advertisingSpaceList = advertisingSpaceService.getAll();// 获取全部广告版位
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("advertisingList", advertisingList);
		m.put("advertisingSpaceList", advertisingSpaceList);// 广告版位
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "advertisingController.do?advertising");
		return new ModelAndView("cms/advertising/advertisingList", m);
	}

	/**
	 * 跳转添加或修改页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdate(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pid = request.getParameter("pid");
		String type = request.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			type = "img";
		}

		String viewName = "cms/advertising/img";

		Map<String, Object> m = new HashMap<String, Object>();
		AdvertisingEntity advertising = null;
		if (StringUtil.isNotEmpty(id)) {
			advertising = advertisingService.getEntity(AdvertisingEntity.class, id);
			type = advertising.getCategory();
		} else {
			advertising = new AdvertisingEntity();
		}

		if ("flash".equalsIgnoreCase(type)) {
			viewName = "cms/advertising/flash";
		} else if ("word".equalsIgnoreCase(type)) {
			viewName = "cms/advertising/word";
		} else if ("code".equalsIgnoreCase(type)) {
			viewName = "cms/advertising/code";
		}

		m.put("advertising", advertising);
		m.put("type", type);
		m.put("pid", pid);
		return new ModelAndView(viewName, m);
	}

	/**
	 * 字典类型保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(AdvertisingEntity advertising, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(advertising.getId())) {
			message = "广告管理【"+advertising.getAdName()+"】更新成功";
			AdvertisingEntity t = advertisingService.get(AdvertisingEntity.class, advertising.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(advertising, t);
				advertisingService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "广告管理更新失败";
			}
		} else {
			message = "广告管理【"+advertising.getAdName()+"】添加成功";

			advertising.setSiteId(PlatFormUtil.getSessionSiteId());
			advertising.setCreatedtime(new Date());//创建时间
			advertisingService.save(advertising);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "advertisingController.do?list&id="+advertising.getAdvertisingSpace().getId());
		String str = j.toString();
		return str;
	}

	/**
	 * 字典类型删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(AdvertisingEntity advertising, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		advertising = advertisingService.getEntity(AdvertisingEntity.class, advertising.getId());
		message = "广告管理【"+advertising.getAdName()+"】删除成功";
		j.accumulate("toUrl", "advertisingController.do?list&id="+advertising.getAdvertisingSpace().getId());
		advertisingService.delete(advertising);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 广告展现次数增加
	 * 
	 * @param advId
	 *            广告id
	 * @return
	 */
	@RequestMapping(params = "advDisplayAdd")
	@ResponseBody
	public String advDisplayAdd(String advId) {
		JSONObject j = new JSONObject();
		if (StringUtils.isEmpty(advId) || !StringUtils.isNumeric(advId)) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "操作错误");
			return j.toString();
		}
		advertisingService.advDisplayAdd(advId);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", "操作成功");
		return j.toString();
	}

	/**
	 * 广告点击次数增加
	 * 
	 * @param advId
	 *            广告id
	 * @return
	 */
	@RequestMapping(params = "advClickAdd")
	@ResponseBody
	public String advClickAdd(String advId) {
		JSONObject j = new JSONObject();
		if (StringUtils.isEmpty(advId) || !StringUtils.isNumeric(advId)) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "操作错误");
			return j.toString();
		}
		advertisingService.advClickAdd(advId);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", "操作成功");
		return j.toString();
	}
}

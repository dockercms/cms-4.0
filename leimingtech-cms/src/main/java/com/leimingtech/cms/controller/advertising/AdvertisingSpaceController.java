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

import com.leimingtech.cms.entity.advertising.AdvertisingSpaceEntity;
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
import com.leimingtech.core.util.StringUtil;
/**   
 * @Title: Controller
 * @Description: 广告栏位
 * @author zhangdaihao
 * @date 2014-04-16 17:27:51
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/advertisingSpaceController")
public class AdvertisingSpaceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AdvertisingSpaceController.class);

	@Autowired
	private AdvertisingSpaceServiceI advertisingSpaceService;
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
	 * 广告栏位列表页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "advertisingSpace")
	public ModelAndView advertisingSpace(AdvertisingSpaceEntity advertisingSpace,HttpServletRequest request) {
		// 获取分组列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(AdvertisingSpaceEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		advertisingSpace.setSiteId(site.getId());
		HqlGenerateUtil.installHql(cq, advertisingSpace, param);
		// 排序条件
		cq.eq("siteId",site.getId());
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = advertisingSpaceService.getPageList(cq, true);
		List<AdvertisingSpaceEntity> advertisingSpaceList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("advertisingSpaceList", advertisingSpaceList);
		m.put("pageSize", pageSize);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("actionUrl", "advertisingSpaceController.do?advertisingSpace");
		return new ModelAndView("cms/advertising/advertisingSpaceList",m);
	}

	/**
	 * 广告栏位添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdate(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		AdvertisingSpaceEntity advertisingSpace = null;
		if (StringUtil.isNotEmpty(id)) {
			advertisingSpace = advertisingSpaceService.getEntity(AdvertisingSpaceEntity.class,id);
		} else {
			advertisingSpace = new AdvertisingSpaceEntity();
		}
		m.put("advertisingSpace", advertisingSpace);
		return new ModelAndView("cms/advertising/advertisingSpace", m);
	}
	
	/**
	 * 广告栏位保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(AdvertisingSpaceEntity advertisingSpace, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(advertisingSpace.getId())) {
			message = "广告栏位["+advertisingSpace.getAdName()+"]更新成功";
			AdvertisingSpaceEntity t = advertisingSpaceService.get(AdvertisingSpaceEntity.class, advertisingSpace.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(advertisingSpace, t);
				advertisingSpaceService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "广告栏位["+advertisingSpace.getAdName()+"]更新失败";
			}
		} else {
			message = "广告栏位["+advertisingSpace.getAdName()+"]添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			advertisingSpace.setSiteId(site.getId());
			advertisingSpace.setCreatedtime(new Date());//创建时间
			advertisingSpaceService.save(advertisingSpace);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "advertisingSpaceController.do?advertisingSpace");
		String str = j.toString();
		return str;
	}

	/**
	 * 广告栏位删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(AdvertisingSpaceEntity advertisingSpace,HttpServletRequest request) {
		JSONObject j = new JSONObject(); 
		advertisingSpace = advertisingSpaceService.getEntity(AdvertisingSpaceEntity.class, advertisingSpace.getId());
		message = "广告栏位["+advertisingSpace.getAdName()+"]删除成功";
		advertisingSpaceService.delMain(advertisingSpace);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "advertisingSpaceController.do?advertisingSpace");
		String str = j.toString();
		return str;
	}
}

package com.leimingtech.cms.controller.friendlink;

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

import com.leimingtech.cms.entity.friendlink.FriendLinkCtgEntity;
import com.leimingtech.cms.entity.friendlink.FriendLinkEntity;
import com.leimingtech.cms.service.FriendLinkCtgServiceI;
import com.leimingtech.cms.service.FriendLinkServiceI;
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
 * @Title: Controller
 * @Description: 友情链接
 * @date 2014-04-18 13:34:37
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/friendLinkController")
public class FriendLinkController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FriendLinkController.class);

	@Autowired
	private FriendLinkServiceI friendLinkService;

	@Autowired
	private FriendLinkCtgServiceI friendLinkCtgService;// 友情链接类别管理
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
	 * 字典类型列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "friendLink")
	public ModelAndView friendLink(FriendLinkEntity friendLink, HttpServletRequest request) {
		// 获取分组列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(FriendLinkEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		friendLink.setSiteId(site.getId());
		HqlGenerateUtil.installHql(cq, friendLink, param);

		cq.eq("siteId", PlatFormUtil.getSessionSiteId());
		cq.addOrder("createdtime", SortDirection.desc);
		cq.addOrder("priority", SortDirection.desc);
		//cq.addOrder("id", SortDirection.desc);
		cq.add();
		PageList pageList = friendLinkService.getPageList(cq, true);
		List<FriendLinkEntity> friendLinkList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		
		List<FriendLinkCtgEntity> friendLinkCtgList = friendLinkCtgService.getAll();//获取所有友情链接类别
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("friendLinkList", friendLinkList);
		m.put("friendLinkCtgList", friendLinkCtgList);//友情链接分类
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "friendLinkController.do?friendLink");
		return new ModelAndView("cms/friendlink/friendLinkList", m);
	}

	/**
	 * 跳转添加或修改类型页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdateType(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		FriendLinkEntity friendLink = null;
		if (StringUtil.isNotEmpty(id)) {
			friendLink = friendLinkService.getEntity(FriendLinkEntity.class, id);
		} else {
			friendLink = new FriendLinkEntity();
		}
		List<FriendLinkCtgEntity> friendlinkctList = friendLinkCtgService.getAll();//获取所有友情链接类别
		m.put("friendLink", friendLink);
		m.put("friendlinkctList", friendlinkctList);
		return new ModelAndView("cms/friendlink/friendLink", m);
	}

	/**
	 * 友情链接保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(FriendLinkEntity friendLink, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(friendLink.getId())) {
			message = "友情链接【"+friendLink.getSiteName()+"】更新成功";
			FriendLinkEntity t = friendLinkService.get(FriendLinkEntity.class, friendLink.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(friendLink, t);
				friendLinkService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "友情链接【"+friendLink.getSiteName()+"】更新失败";
			}
		} else {
			message = "友情链接【"+friendLink.getSiteName()+"】添加成功";

			friendLink.setSiteId(PlatFormUtil.getSessionSiteId());
			friendLink.setViews(0);
			friendLink.setCreatedtime(new Date());//创建时间
			friendLinkService.save(friendLink);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "friendLinkController.do?friendLink");
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
	public String del(FriendLinkEntity friendLink, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		friendLink = friendLinkService.getEntity(FriendLinkEntity.class, friendLink.getId());
		message = "友情链接【"+friendLink.getSiteName()+"】删除成功";
		friendLinkService.delete(friendLink);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "friendLinkController.do?friendLink");
		String str = j.toString();
		return str;
	}
}

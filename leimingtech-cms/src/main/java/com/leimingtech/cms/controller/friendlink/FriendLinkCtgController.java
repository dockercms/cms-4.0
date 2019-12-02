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
import com.leimingtech.cms.service.FriendLinkCtgServiceI;
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
 * @Description: 友情链接类别
 * @author zhangdaihao
 * @date 2014-04-18 13:37:52
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/friendLinkCtgController")
public class FriendLinkCtgController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FriendLinkCtgController.class);

	@Autowired
	private FriendLinkCtgServiceI friendLinkCtgService;
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
	 * 友情链接类别列表页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "friendLinkCtg")
	public ModelAndView friendLinkCtg(FriendLinkCtgEntity friendLinkCtg,HttpServletRequest request) {
		// 获取分组列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(FriendLinkCtgEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		friendLinkCtg.setSiteId(site.getId());
		HqlGenerateUtil.installHql(cq, friendLinkCtg, param);
		
		cq.eq("siteId", PlatFormUtil.getSessionSiteId());
		cq.addOrder("createdtime", SortDirection.desc);
		cq.addOrder("priority", SortDirection.desc);
		//cq.addOrder("id", SortDirection.desc);
		cq.add();
		PageList pageList = friendLinkCtgService.getPageList(cq, true);
		List<FriendLinkCtgEntity> friendLinkCtgList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("friendLinkCtgList", friendLinkCtgList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "friendLinkCtgController.do?friendLinkCtg");
		return new ModelAndView("cms/friendlink/friendLinkCtgList",m);
	}

	/**
	 * 友情链接类别添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdate(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		FriendLinkCtgEntity friendLinkCtg = null;
		if (StringUtil.isNotEmpty(id)) {
			friendLinkCtg = friendLinkCtgService.getEntity(FriendLinkCtgEntity.class, id);
		} else {
			friendLinkCtg = new FriendLinkCtgEntity();
		}
		m.put("friendLinkCtg", friendLinkCtg);
		return new ModelAndView("cms/friendlink/friendLinkCtg", m);
	}
	
	/**
	 * 友情链接类别保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(FriendLinkCtgEntity friendLinkCtg, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(friendLinkCtg.getId())) {
			message = "友情链接分类【"+friendLinkCtg.getFriendlinkctgName()+"】更新成功";
			FriendLinkCtgEntity t = friendLinkCtgService.get(FriendLinkCtgEntity.class, friendLinkCtg.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(friendLinkCtg, t);
				friendLinkCtgService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "友情链接类别【"+friendLinkCtg.getFriendlinkctgName()+"】更新失败";
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_ERROR);
			}
		} else {
			message = "友情链接分类【"+friendLinkCtg.getFriendlinkctgName()+"】添加成功";
		
			friendLinkCtg.setSiteId(PlatFormUtil.getSessionSiteId());
			friendLinkCtg.setCreatedtime(new Date());//创建时间
			friendLinkCtgService.save(friendLinkCtg);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "friendLinkCtgController.do?friendLinkCtg");
		String str = j.toString();
		return str;
	}

	/**
	 * 友情链接类别删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(FriendLinkCtgEntity friendLinkCtg,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		friendLinkCtg = friendLinkCtgService.getEntity(FriendLinkCtgEntity.class, friendLinkCtg.getId());
		message = "友情链接分类【"+friendLinkCtg.getFriendlinkctgName()+"】删除成功";
		friendLinkCtgService.delMain(friendLinkCtg);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "friendLinkCtgController.do?friendLinkCtg");
		String str = j.toString();
		return str;
	}
}

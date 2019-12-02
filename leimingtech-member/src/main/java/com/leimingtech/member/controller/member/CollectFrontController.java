package com.leimingtech.member.controller.member;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.entity.member.MemberEntity;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.CollectFrontEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.member.service.member.CollectFrontServiceI;

/**
 * @Title: Controller
 * @Description: 收藏
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-09 15:13:33
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/member/collectFrontController")
public class CollectFrontController extends BaseController {

	@Autowired
	private CollectFrontServiceI collectFrontService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private MemberServiceI memberMng;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 收藏列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	// @RequestMapping(params = "table")
	public ModelAndView table(CollectFrontEntity collectFront, HttpServletRequest request) {
		// 获取收藏列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(CollectFrontEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, collectFront, param);
		// 排序条件
		cq.addOrder("createdtime",SortDirection.desc );
		cq.add();
		PageList pageList = this.collectFrontService.getPageList(cq, true);
		List<CollectFrontEntity> testList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "collectFrontController.do?table");
		return new ModelAndView("member/collectFrontList", m);
	}

	/**
	 * 收藏添加
	 * 
	 * @return
	 */
	// @RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new CollectFrontEntity());
		return new ModelAndView("member/collectadd", m);
	}

	/**
	 * 收藏更新
	 * 
	 * @return
	 */
	// @RequestMapping(params = "modify")
	public ModelAndView modify(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		CollectFrontEntity collectFront = collectFrontService.getEntity(CollectFrontEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", collectFront);
		return new ModelAndView("member/collectFront", m);
	}

	/**
	 * 收藏保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		try {
			String title = request.getParameter("title");
			String url = request.getParameter("url");
			CollectFrontEntity collectFront = new CollectFrontEntity();
			collectFront.setTitle(title);
			collectFront.setUrl(url);
			collectFront.setCollecttime(new Date());
			MemberEntity member = memberMng.getSessionMember();
			if(member != null){
				collectFront.setCollectpersonid(member.getId());
				collectFront.setCollectperson(member.getName());
				message = ""+collectFront.getTitle()+"收藏成功";
				collectFront.setCreatedtime(new Date());//创建时间
				collectFrontService.save(collectFront);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			}else{
				message = "请先登录";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "收藏失败";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "collectFrontController.do?collect");
		String str = j.toString();
		return str;
	}

	/**
	 * 收藏删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();

		try {
			String id = request.getParameter("id");
			CriteriaQuery cq = new CriteriaQuery(CollectFrontEntity.class);
			cq.eq("collectpersonid", memberMng.getSessionMember().getId());
			cq.eq("id", String.valueOf(id));
			cq.add();
			List<CollectFrontEntity> collectList = collectFrontService.getListByCriteriaQuery(cq, false);
			if (collectList != null && collectList.size() > 0) {
				collectFrontService.deleteAllEntitie(collectList);
				message = "收藏" + collectList.get(0).getTitle() + "删除成功！";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
				j.accumulate("isSuccess", true);
			}else{
				message = "收藏删除失败！";
				systemService.addLog(message, Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
				j.accumulate("isSuccess", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "收藏删除失败！";
			j.accumulate("isSuccess", false);
			systemService.addLog(message + "，原因：" + e.getMessage(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
		}

		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 收藏列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "collect")
	public ModelAndView collect(HttpServletRequest request, String title) {

		String pageSizeStr = request.getParameter("pageSize");
		String pageIndexStr = request.getParameter("pageIndex");
		Integer pageSize = 5;
		Integer pageIndex = 1;
		if (StringUtils.isNotEmpty(pageSizeStr) && StringUtils.isNumeric(pageSizeStr)) {
			pageSize = Integer.valueOf(pageSizeStr);
		}

		if (StringUtils.isNotEmpty(pageIndexStr) && StringUtils.isNumeric(pageIndexStr)) {
			pageIndex = Integer.valueOf(pageIndexStr);
		}

		CriteriaQuery cq = new CriteriaQuery(CollectFrontEntity.class, pageSize, pageIndex, "", "");
		cq.eq("collectpersonid", memberMng.getSessionMember().getId());
		cq.addOrder("id", SortDirection.desc);
		if (StringUtils.isNotEmpty(title)) {
			try {
				title = new String(title.getBytes("iso-8859-1"), "UTF-8");
				cq.like("title", "%"+title+"%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		cq.add();
		PageList pageList = collectFrontService.getPageList(cq, true);
		List<CollectFrontEntity> collectList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("title", title);
		m.put("collectList", collectList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("countNum", pageList.getCount());
		m.put("sitePath", memberMng.getSitePath());
		m.put("searchUrl", "collectFrontController.do?collect");
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		return new ModelAndView("member/collectlist", m);
	}
}

package com.leimingtech.cms.controller.commentadvertising;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.cms.service.comments.CommentsServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.util.PlatFormUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.cms.entity.commentadvertising.CommentAdvertisingEntity;
import com.leimingtech.cms.service.commentadvertising.CommentAdvertisingServiceI;

/**   
 * @Title: Controller
 * @Description: 评论广告
 * @author 
 * @date 2017-04-26 11:55:54
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/commentAdvertisingController")
public class CommentAdvertisingController extends BaseController {

	private String message;
	/** 评论广告接口 */
	@Autowired
	public CommentAdvertisingServiceI commentAdvertisingService;
	@Autowired
	public CommentsServiceI commentsService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	
	/**
	 * 评论广告列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "commentAdvertising")
	public ModelAndView commentAdvertising(CommentAdvertisingEntity commentAdvertising, HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1: Integer.valueOf(request.getParameter("pageNo"));
		CriteriaQuery cq = new CriteriaQuery(CommentAdvertisingEntity.class, pageSize, pageNo, "", "");
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq,commentAdvertising,param);
		cq.eq("siteId",PlatFormUtil.getSessionSiteId());
		cq.addOrder("weight",SortDirection.desc);
		cq.addOrder("createTime",SortDirection.desc);
		cq.add();
		PageList pageList = commentsService.getPageList(cq,true);
		List<CommentAdvertisingEntity> commentAdvertisingList=pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("commentAdvertisingList",commentAdvertisingList);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "commentAdvertisingController.do?commentAdvertising");
		return new ModelAndView("cms/commentadvertising/commentAdvertisingList", m);
	}

	/**
	 * 评论广告添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		String siteId= PlatFormUtil.getSessionSiteId();
		m.put("siteId",siteId);
		m.put("commentAdvertising", new CommentAdvertisingEntity());
		return new ModelAndView("cms/commentadvertising/commentAdvertising", m);
	}
	
	/**
	 * 评论广告更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		CommentAdvertisingEntity commentAdvertising = commentAdvertisingService.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("commentAdvertising", commentAdvertising);
		return new ModelAndView("cms/commentadvertising/commentAdvertising", m);
	}

	/**
	 * 评论广告保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(CommentAdvertisingEntity commentAdvertising, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		
		if (StringUtils.isNotEmpty(commentAdvertising.getId())) {
			commentAdvertising.setSiteId(PlatFormUtil.getSessionSiteId());
			message = "评论广告【" + commentAdvertising.getId() + "】更新成功";
			CommentAdvertisingEntity t = commentAdvertisingService.getEntity(commentAdvertising.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(commentAdvertising, t);
				commentAdvertisingService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "评论广告【" + commentAdvertising.getId() + "】更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "评论广告【" + commentAdvertising.getId() + "】添加成功";
			commentAdvertising.setCreateTime(new Date());
			commentAdvertising.setSiteId(PlatFormUtil.getSessionSiteId());
			commentAdvertisingService.save(commentAdvertising);
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "commentAdvertisingController.do?commentAdvertising");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 评论广告删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		CommentAdvertisingEntity commentAdvertising = commentAdvertisingService.getEntity(java.lang.String.valueOf(id));
		message = "评论广告【" + commentAdvertising.getId() + "】删除成功";
		commentAdvertisingService.delete(commentAdvertising);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "commentAdvertisingController.do?commentAdvertising");
		String str = j.toString();
		return str;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

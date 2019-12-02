package com.leimingtech.member.controller.member;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.CommentaryFrontEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommentaryFrontServiceI;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.date.DataUtils;

/**
 * @Title: Controller
 * @Description: 评论
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-06 15:26:26
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/member/commentaryFrontController")
public class CommentaryFrontController extends BaseController {

	@Autowired
	private CommentaryFrontServiceI commentaryFrontService;
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
	 * 评论列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	//@RequestMapping(params = "commentaryList")
	public ModelAndView commentaryList(CommentaryFrontEntity commentaryFront, HttpServletRequest request) {
		// 获取评论列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, commentaryFront, param);
		// 排序条件
		cq.addOrder("createdtime",SortDirection.desc );
		
		PageList pageList = this.commentaryFrontService.getPageList(cq, true);
		List<CommentaryFrontEntity> commentaryList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("commentaryList", commentaryList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "commentaryFrontController.do?commentaryList");
		return new ModelAndView("member/commentarylist", m);
	}

	/**
	 * 评论添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new CommentaryFrontEntity());
		return new ModelAndView("member/commentaryadd", m);
	}

	/**
	 * 评论更新
	 * 
	 * @return
	 */
	//@RequestMapping(params = "modify")
	public ModelAndView modify(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		CommentaryFrontEntity commentaryFront = commentaryFrontService.getEntity(CommentaryFrontEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", commentaryFront);
		return new ModelAndView("member/commentarymodify", m);
	}

	/**
	 * 评论保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(CommentaryFrontEntity commentaryFront, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String con = request.getParameter("content");
		String contentid = request.getParameter("contentid");
		if (memberMng.getSessionMember().getId()==null) {
			message ="登陆后才可以评论";
		} else if(con == ""){
			message="评论不能为空";
		}else{
			message = "评论["+commentaryFront.getContent()+"]添加成功";
			ContentsEntity contents=commentaryFrontService.findUniqueByProperty(ContentsEntity.class, "id", contentid);
			commentaryFront.setContent(con);
			commentaryFront.setContentid(contents.getId());
			commentaryFront.setPersonid(memberMng.getSessionMember().getId());
			commentaryFront.setTitle(contents.getTitle());
			commentaryFront.setCreatedtime(new Date());
			commentaryFront.setCommentaryperson(memberMng.getSessionMember().getName());
			commentaryFrontService.save(commentaryFront);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 评论删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();

		try {
			String id = request.getParameter("id");
			CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class);
			cq.eq("personid", memberMng.getSessionMember().getId());
			cq.eq("id", String.valueOf(id));
			cq.add();
			List<CommentaryFrontEntity> commentaryList = commentaryFrontService.getListByCriteriaQuery(cq, false);
			if (commentaryList != null && commentaryList.size() > 0) {
				commentaryFrontService.deleteAllEntitie(commentaryList);
				message = "评论"+commentaryList.get(0).getTitle()+"删除成功";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
				j.accumulate("isSuccess", true);
			}else{
				message="评论删除失败";
				j.accumulate("isSuccess", false);
				systemService.addLog(message, Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message="评论删除失败";
			j.accumulate("isSuccess", false);
			systemService.addLog(message+"，原因："+e.getMessage(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
		}

		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	@RequestMapping(params = "commentary")
	public ModelAndView commentary(HttpServletRequest request, String begintime, String endtime) {
		
		String pageSizeStr =request.getParameter("pageSize");
		String pageIndexStr = request.getParameter("pageNo");
		Integer pageSize = 5;
		Integer pageNo = 1;
		if (StringUtils.isNotEmpty(pageSizeStr) && StringUtils.isNumeric(pageSizeStr)) {
			pageSize = Integer.valueOf(pageSizeStr);
		}

		if (StringUtils.isNotEmpty(pageIndexStr) && StringUtils.isNumeric(pageIndexStr)) {
			pageNo = Integer.valueOf(pageIndexStr);
		}

		CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class, pageSize, pageNo, "", "");
		cq.eq("personid", memberMng.getSessionMember().getId());
		cq.addOrder("id", SortDirection.desc);
		try {
			if (StringUtils.isNotEmpty(begintime)) {
				cq.gt("commentarytime", DataUtils.parseDate(begintime + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));

			}
			if (StringUtils.isNotEmpty(endtime)) {
				cq.lt("commentarytime", DataUtils.parseDate(endtime + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cq.add();

		PageList pageList = commentaryFrontService.getPageList(cq, true);
		List<CommentaryFrontEntity> commentarylist = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("begintime", begintime);
		m.put("endtime", endtime);
		m.put("commentarylist", commentarylist);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("countNum", pageList.getCount());
		m.put("sitePath", memberMng.getSitePath());
		m.put("searchUrl", "commentaryFrontController.do?commentary");
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		return new ModelAndView("member/commentarylist", m);
	}

	/**
	 * 评论列表展现
	 * 
	 * @return
	 */
	//@RequestMapping(params = "commentaryListTest")
	public ModelAndView commentaryListTest(HttpServletRequest reqeust) {
		List<CommentaryFrontEntity> commentaryList1 = commentaryFrontService.getList(CommentaryFrontEntity.class);
		int count = commentaryList1.size();
		CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class, 2, 1, "", "");
		PageList pageList = this.commentaryFrontService.getPageList(cq, true);
		List<CommentaryFrontEntity> commentaryList = pageList.getResultList();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new CommentaryFrontEntity());
		m.put("commentaryList", commentaryList);
		m.put("count", count);
		m.put("sitePath", memberMng.getSitePath());
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		return new ModelAndView("member/testcommentary", m);
	}

	/**
	 * 评论列表展现
	 * 
	 * @return
	 */
	//@RequestMapping(params = "commentaryListTest1")
	public ModelAndView commentaryListTest1(HttpServletRequest reqeust, String pageIndex) {
		int pageIndex1 = 1;
		if (StringUtil.isNotEmpty(pageIndex)) {
			pageIndex1 = Integer.parseInt(pageIndex);
		}
		CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class, 2, pageIndex1, "", "");
		PageList pageList = this.commentaryFrontService.getPageList(cq, true);
		List<CommentaryFrontEntity> commentaryList = pageList.getResultList();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new CommentaryFrontEntity());
		m.put("commentaryList", commentaryList);
		return new ModelAndView("member/testcommentary1", m);
	}
}

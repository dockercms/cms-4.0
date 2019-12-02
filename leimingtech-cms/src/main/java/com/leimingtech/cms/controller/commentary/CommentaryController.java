package com.leimingtech.cms.controller.commentary;

import com.leimingtech.cms.entity.reply.ReplyEntity;
import com.leimingtech.cms.service.comments.CommentsServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.CommentaryFrontEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommentaryFrontServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论后台管理
 * @author lkang
 * 2014-08-08 16:00
 */
@Controller
@RequestMapping("/commentaryController")
public class CommentaryController extends BaseController {
	@Autowired
	private CommentaryFrontServiceI commentaryFrontService;
	@Autowired
	private SystemService systemService;
    @Autowired
    private CommentsServiceI commentsServiceI;
	
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
	 */
	@RequestMapping(params = "commentaryList")
	public ModelAndView commentaryList(CommentaryFrontEntity commentaryFront, HttpServletRequest request) {
		// 获取评论列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class, pageSize, pageNo, "", "");
		cq.addOrder("id", SortDirection.desc);
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, commentaryFront, param);
		// 排序条件
        cq.eq("siteId",PlatFormUtil.getSessionSiteId());
		cq.addOrder("commentarytime", SortDirection.desc);
		cq.add();
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
		m.put("actionUrl", "commentaryController.do?commentaryList");
		return new ModelAndView("cms/commentary/commentarylist", m);
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
            CommentaryFrontEntity commentaryFrontEntity = commentsServiceI.get(CommentaryFrontEntity.class, id);
            String contentid = commentaryFrontEntity.getContentid();
            ContentsEntity contentsEntity = commentsServiceI.get(ContentsEntity.class, contentid);
            //删除 评论
            commentsServiceI.deleteEntityById(CommentaryFrontEntity.class,id);
            message = "评论删除成功";
            systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
            j.accumulate("isSuccess", true);
            //删除 评论中的 跟帖
            List<ReplyEntity> replyEntityList = commentsServiceI.findByProperty(ReplyEntity.class, "commentId", id);
            if (replyEntityList != null && replyEntityList.size() > 0) {
                commentsServiceI.deleteAllEntitie(replyEntityList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message="评论删除失败";
			j.accumulate("isSuccess", false);
			systemService.addLog(message+"，原因："+e.getMessage(), Globals.Log_Type_DEL, Globals.Log_Leavel_ERROR);
		}
		j.accumulate("toUrl", "commentaryController.do?commentaryList");
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 评论审核
	 * @return
	 */
	@RequestMapping(params = "checkcommentary")
	@ResponseBody
	public String checkCommentary(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
        String pageNo = request.getParameter("pageNo");
		String ischeck = request.getParameter("ischeck");
		CommentaryFrontEntity commentary = commentaryFrontService.get(CommentaryFrontEntity.class, String.valueOf(id));
		commentary.setIscheck(ischeck);
		try {
			message = "操作成功";
			commentaryFrontService.saveOrUpdate(commentary);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "操作失败";
		}
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "commentaryController.do?commentaryList&pageNo="+pageNo);
		String str = j.toString();
		return str;
	}
}

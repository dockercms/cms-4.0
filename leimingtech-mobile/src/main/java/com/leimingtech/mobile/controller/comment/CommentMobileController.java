package com.leimingtech.mobile.controller.comment;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.entity.comment.CommentMobileEntity;
import com.leimingtech.mobile.service.comment.CommentMobileServiceI;

/**   
 * @Title: Controller
 * @Description: 移动内容评价表
 * @author 
 * @date 2014-07-23 18:35:53
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/commentMobileController")
public class CommentMobileController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CommentMobileController.class);

	@Autowired
	private CommentMobileServiceI commentMobileService;
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
	 * 移动内容评价表列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "commentMobile")
	public ModelAndView commentMobile(CommentMobileEntity commentMobile, HttpServletRequest request) {
		//获取移动内容评价表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(CommentMobileEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, commentMobile, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc );
		cq.add();
		PageList pageList = this.commentMobileService.getPageList(cq, true);
		List<CommentMobileEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "commentMobileController.do?commentMobile");
		return new ModelAndView("mobile/comment/commentMobileList", m);
	}

	/**
	 * 移动内容评价表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("commentMobile", new CommentMobileEntity());
		return new ModelAndView("mobile/comment/commentMobile", m);
	}
	
	/**
	 * 移动内容评价表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		CommentMobileEntity commentMobile = commentMobileService.getEntity(CommentMobileEntity.class,  id );
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("commentMobile", commentMobile);
		return new ModelAndView("mobile/comment/commentMobile", m);
	}

	/**
	 * 移动内容评价表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(CommentMobileEntity commentMobile, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(commentMobile.getId())) {
			message = "移动内容评价["+commentMobile.getTitle()+"]更新成功";
			CommentMobileEntity t = commentMobileService.get(CommentMobileEntity.class, commentMobile.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(commentMobile, t);
				commentMobileService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "移动内容评价["+commentMobile.getTitle()+"]更新失败";
			}
		} else {
			message = "移动内容评价["+commentMobile.getTitle()+"]添加成功";
			commentMobile.setCreatedtime(new Date());           //创建时间
			commentMobileService.save(commentMobile);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "commentMobileController.do?commentMobile");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 移动内容评价表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		CommentMobileEntity commentMobile = commentMobileService.getEntity(CommentMobileEntity.class, id );
		message = "移动内容评价["+commentMobile.getTitle()+"]删除成功";
		commentMobileService.delete(commentMobile);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "commentMobileController.do?commentMobile");
		String str = j.toString();
		return str;
	}
}

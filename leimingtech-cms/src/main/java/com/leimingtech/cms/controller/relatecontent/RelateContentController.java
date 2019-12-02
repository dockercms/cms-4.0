package com.leimingtech.cms.controller.relatecontent;
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

import com.leimingtech.cms.service.RelateContentServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.RelateContentEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 相关内容
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-13 15:08:12
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/relateContentController")
public class RelateContentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RelateContentController.class);

	@Autowired
	private RelateContentServiceI relateContentService;
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
	 * 相关内容列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(RelateContentEntity relateContent, HttpServletRequest request) {
		//获取相关内容列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(RelateContentEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, relateContent, param);
		//排序条件
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		PageList pageList = this.relateContentService.getPageList(cq, true);
		List<RelateContentEntity> testList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "relateContentController.do?table");
		return new ModelAndView("cms/relatecontent/relateContentList", m);
	}

	/**
	 * 相关内容添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest request){
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//内容Id
		String contentId = request.getParameter("contentId");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new RelateContentEntity());
		m.put("temporary", temporary);
		m.put("contentsId", contentId);
		return new ModelAndView("cms/relatecontent/relateContent", m);
	}
	
	/**
	 * 相关内容更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest request){
		String id = request.getParameter("id");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//内容Id
		String contentId = request.getParameter("contentId");
		RelateContentEntity relateContent = relateContentService.getEntity(RelateContentEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", relateContent);
		m.put("temporary", temporary);
		m.put("contentsId", contentId);
		return new ModelAndView("cms/relatecontent/relateContent", m);
	}

	/**
	 * 相关内容保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(RelateContentEntity relateContent, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//内容Id
		String contentId = request.getParameter("contentsId");
		if (StringUtil.isNotEmpty(relateContent.getId())) {
			message = "相关内容["+relateContent.getRelateTitle()+"]更新成功";
			RelateContentEntity t = relateContentService.get(RelateContentEntity.class, relateContent.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(relateContent, t);
				relateContentService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "相关内容["+relateContent.getRelateTitle()+"]更新失败";
			}
		} else {
			message = "相关内容["+relateContent.getRelateTitle()+"]添加成功";
			relateContent.setCreated(new Date());
			relateContentService.save(relateContent);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsController.do?showDiv&checked=&temporary="+temporary+"&contentId="+contentId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 相关内容删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//内容Id
		String contentId = request.getParameter("contentId");
		RelateContentEntity relateContent = relateContentService.getEntity(RelateContentEntity.class, String.valueOf(id));
		message = "相关内容["+relateContent.getRelateTitle()+"]删除成功";
		relateContentService.delete(relateContent);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "contentsController.do?showDiv&checked=&temporary="+temporary+"&contentId="+contentId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 文章相关搜索弹出框编辑，手动添加方法
	 * 
	 * @return
	 * @author larry
	 */
	@RequestMapping(params = "correlationEditORAdd")
	public ModelAndView correlationEditORAdd(HttpServletRequest reqeust){		
		Map<String, Object> m = new HashMap<String, Object>();
		String type =reqeust.getParameter("cms_type");   //用于区分类型
		String id=reqeust.getParameter("relat_id");               //ID
		if(type=="edit"){
	//	List<RelateContentEntity> relateContent = systemService.findByProperty(RelateContentEntity.class, "contentId", id);
	//	RelateContentEntity relate = systemService.getEntity(RelateContentEntity.class, String.valueOf(id));
		List<ModelItemEntity> relate = relateContentService.findByProperty(ModelItemEntity.class, "content_id", String.valueOf(id));
		m.put("relateContent", relate);
		}else if(type=="add"){
			m.put("relateContent", new RelateContentEntity());
			return new ModelAndView("cms/article/article_editoradd",m);
		}
		
		return new ModelAndView("cms/article/article_editoradd",m);
	}
	
}

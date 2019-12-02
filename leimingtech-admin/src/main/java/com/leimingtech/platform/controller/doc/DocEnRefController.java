package com.leimingtech.platform.controller.doc;

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
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.platform.entity.doc.DocEnEntity;
import com.leimingtech.platform.entity.doc.DocEnRefEntity;
import com.leimingtech.platform.entity.doc.DocEntity;
import com.leimingtech.platform.service.doc.DocEnRefServiceI;

/**
 * @Title: Controller
 * @Description: 文档和文档引用的实体关联表
 * @author
 * @date 2014-06-25 19:39:58
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/docEnRefController")
public class DocEnRefController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DocEnRefController.class);
	
	@Autowired
	private DocEnRefServiceI docEnRefService;
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
	 * 文档和文档引用的实体关联表列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "docEnRef")
	public ModelAndView docEnRef(DocEnRefEntity docEnRef, HttpServletRequest request, String docid) {
		// 获取文档和文档引用的实体关联表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(DocEnRefEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, docEnRef, param);

		cq.eq("doc.id", docid);
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.docEnRefService.getPageList(cq, true);
		List<DocEnRefEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("docid", docid);
		m.put("actionUrl", "docEnRefController.do?docEnRef&docid="+docid);
		return new ModelAndView("platform/doc/docEnRefList", m);
	}

	/**
	 * 文档和文档引用的实体关联表添加
	 * 
	 * @return
	 */
//	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("docEnRef", new DocEnRefEntity());
		return new ModelAndView("platform/doc/docEnRef", m);
	}

	/**
	 * 文档和文档引用的实体关联表更新
	 * 
	 * @return
	 */
//	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		DocEnRefEntity docEnRef = docEnRefService.getEntity(DocEnRefEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("docEnRef", docEnRef);
		return new ModelAndView("platform/doc/docEnRef", m);
	}

	/**
	 * 文档和文档引用的实体关联表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, String docid, String entityid) {
		JSONObject j = new JSONObject();
		TSUser user = PlatFormUtil.getSessionUser();
		
		DocEnRefEntity docEnRef=new DocEnRefEntity();
		DocEntity doc=docEnRefService.getEntity(DocEntity.class, docid);
		DocEnEntity docEn=docEnRefService.getEntity(DocEnEntity.class, entityid);
		
		if(docEn!=null&&doc!=null){
			docEnRef.setDoc(doc);
			docEnRef.setDocEn(docEn);
			
			docEnRef.setSort(0);
			docEnRef.setStatus(1);
			docEnRef.setCreatedtime(new Date());
			docEnRef.setUpdatecount(0);
			docEnRef.setCreatedby(user.getUserName());
			docEnRefService.save(docEnRef);
			message = "文档和文档引用的实体关联["+docEnRef.getId()+"]表添加成功";
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			j.accumulate("isSuccess", true);
		}else{
			message = "添加失败";
			j.accumulate("isSuccess", false);
		}

		j.accumulate("msg", message);
		j.accumulate("toUrl", "docEnRefController.do?docEnRef&docid=" + docid);
		String str = j.toString();
		return str;
	}

	/**
	 * 文档和文档引用的实体关联表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		DocEnRefEntity docEnRef = docEnRefService.getEntity(DocEnRefEntity.class, String.valueOf(id));
		j.accumulate("toUrl", "docEnRefController.do?docEnRef&docid="+docEnRef.getDoc().getId());
		message = "文档和文档引用的实体关联表["+docEnRef.getId()+"]删除成功";
		docEnRefService.delete(docEnRef);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

}

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
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.doc.DocEnEntity;
import com.leimingtech.platform.entity.doc.DocEnProEntity;
import com.leimingtech.platform.entity.doc.DocEnRefEntity;
import com.leimingtech.platform.entity.doc.DocReturnValueEntity;
import com.leimingtech.platform.service.doc.DocEnServiceI;

/**
 * @Title: Controller
 * @Description: 文档引用实体表
 * @author
 * @date 2014-06-25 19:34:32
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/docEnController")
public class DocEnController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DocEnController.class);

	@Autowired
	private DocEnServiceI docEnService;
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
	 * 文档引用实体表列表页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "docEn")
	public ModelAndView docEn(DocEnEntity docEn, HttpServletRequest request) {
		// 获取分组列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(DocEnEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, docEn, param);
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = docEnService.getPageList(cq, true);
		List<DocEnEntity> docEnList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("docEnList", docEnList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "docEnController.do?docEn");
		return new ModelAndView("platform/doc/docEnList", m);
	}

	/**
	 * 文档引用实体表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addOrUpdate")
	public ModelAndView addOrUpdate(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		DocEnEntity docEn = null;
		if (StringUtil.isNotEmpty(id)) {
			docEn = docEnService.getEntity(DocEnEntity.class, String.valueOf(id));
		} else {
			docEn = new DocEnEntity();
		}
		m.put("docEn", docEn);
		return new ModelAndView("platform/doc/docEn", m);
	}

	/**
	 * 文档引用实体表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(DocEnEntity docEn, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		TSUser user = PlatFormUtil.getSessionUser();
		String code = request.getParameter("code");
		if (StringUtil.isNotEmpty(docEn.getId())) {
			message = "文档引用实体表["+docEn.getName()+"]更新成功";
			//根据code更新返回结果
			DocEnEntity t = docEnService.get(DocEnEntity.class, docEn.getId());
			List<DocReturnValueEntity> docReturnList = docEnService.findByProperty(DocReturnValueEntity.class, "code", t.getCode());
			if(docReturnList.size()>0 || docReturnList!=null){
				int i= docEnService.updateBySqlString("update cms_doc_returnvalue set code='"+code+"' where code = '"+t.getCode()+"'");

			}
			try {
				MyBeanUtils.copyBeanNotNull2Bean(docEn, t);
				t.setUpdatecount(t.getUpdatecount()+1);
				t.setUpdatetime(new Date());
				t.setUpdateby(user.getUserName());
				docEnService.saveOrUpdate(t);
				
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文档引用实体表["+docEn.getName()+"]更新失败";
			}
		} else {
			message = "文档引用实体表["+docEn.getName()+"]添加成功";
			if (StringUtils.isEmpty(docEn.getDescription())) {
				docEn.setDescription(docEn.getName());
			}
			
			docEn.setSort(0);
			docEn.setStatus(1);
			docEn.setCreatedtime(new Date());
			docEn.setCreatedby(user.getUserName());
			docEn.setUpdatecount(0);
			docEnService.save(docEn);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "docEnController.do?docEn");
		String str = j.toString();
		return str;
	}

	/**
	 * 文档引用实体表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(DocEnEntity docEn, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		docEn = docEnService.getEntity(DocEnEntity.class, docEn.getId());
		message = "文档引用实体表["+docEn.getName()+"]删除成功";
		docEnService.delMain(docEn);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "docEnController.do?docEn");
		String str = j.toString();
		return str;
	}

	/**
	 * 文档引用实体表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "docEnPro")
	public ModelAndView docEnPro(DocEnProEntity docEnPro, HttpServletRequest request) {
		// 获取分组列表
		String entityid = request.getParameter("entityid");
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(DocEnProEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, docEnPro, param);
		cq.eq("entityid", entityid);
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = docEnService.getPageList(cq, true);
		List<DocEnProEntity> docEnProList = pageList.getResultList();
		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		DocEnEntity docEn = docEnService.getEntity(DocEnEntity.class, String.valueOf(entityid));

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("docEnProList", docEnProList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("entityid", entityid);
		m.put("docEn", docEn);
		m.put("actionUrl", "docEnController.do?docEnPro");
		return new ModelAndView("platform/doc/docEnProList", m);
	}

	/**
	 * 文档引用实体表跳转添加或修改页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addOrUpdateSub")
	public ModelAndView addOrUpdateType(HttpServletRequest request) {
		String id = request.getParameter("id");
		String entityid = request.getParameter("entityid");
		Map<String, Object> m = new HashMap<String, Object>();
		DocEnProEntity docEnPro = null;
		if (StringUtil.isNotEmpty(id)) {
			docEnPro = docEnService.getEntity(DocEnProEntity.class, String.valueOf(id));
		} else {
			docEnPro = new DocEnProEntity();
		}
		request.setAttribute("entityid", entityid);
		m.put("docEnPro", docEnPro);
		return new ModelAndView("platform/doc/docEnPro", m);
	}

	/**
	 * 文档引用实体表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveSub")
	@ResponseBody
	public String saveOrUpdate(DocEnProEntity docEnPro, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		TSUser user = PlatFormUtil.getSessionUser();
		if (StringUtil.isNotEmpty(docEnPro.getId())) {
			message = "文档引用实体属性表["+docEnPro.getName()+"]更新成功";
			DocEnProEntity t = docEnService.get(DocEnProEntity.class, docEnPro.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(docEnPro, t);
				
				t.setUpdatecount(t.getUpdatecount()+1);
				t.setUpdateby(user.getUserName());
				t.setUpdatetime(new Date());
				docEnService.saveOrUpdate(t);
				
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文档引用实体属性表["+docEnPro.getName()+"]更新失败";
			}
		} else {
			message = "文档引用实体属性表["+docEnPro.getName()+"]添加成功";
			if(StringUtils.isEmpty(docEnPro.getDescription())){
				docEnPro.setDescription(docEnPro.getName());
			}
			
			docEnPro.setCreatedby(user.getUserName());
			docEnPro.setCreatedtime(new Date());
			docEnPro.setUpdatecount(0);
			docEnPro.setSort(0);
			docEnPro.setStatus(1);
			docEnService.save(docEnPro);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "docEnController.do?docEnPro&entityid=" + docEnPro.getEntityid());
		String str = j.toString();
		return str;
	}

	/**
	 * 文档引用实体属性表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "delSub")
	@ResponseBody
	public String del(DocEnProEntity docEnPro, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		docEnPro = docEnService.getEntity(DocEnProEntity.class, docEnPro.getId());
		message = "文档引用实体属性表["+docEnPro.getName()+"]删除成功";
		docEnService.delete(docEnPro);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "docEnController.do?docEnPro&entityid=" + docEnPro.getEntityid());
		String str = j.toString();
		return str;
	}

	/**
	 * 展示实体列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "showEnList")
	public ModelAndView add(HttpServletRequest reqeust, String docid, String closeModel, String selectValShowIn) {
		List<DocEnEntity> docEnList = docEnService.getList(DocEnEntity.class);
		List<DocEnRefEntity> docEnRefList = docEnService.findByProperty(DocEnRefEntity.class, "doc.id", docid);
		if (docEnList != null && docEnList.size() > 0 && docEnRefList != null && docEnRefList.size() > 0) {
			for (int i = 0; i < docEnList.size(); i++) {
				DocEnEntity docEn = docEnList.get(i);
				for (int j = 0; j < docEnRefList.size(); j++) {
					DocEnRefEntity docEnRef = docEnRefList.get(j);
					if (docEn.getId() == docEnRef.getDocEn().getId()) {
						docEnList.remove(docEn);
					}
				}
			}
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("closeModel", closeModel);
		m.put("selectValShowIn", selectValShowIn);
		m.put("docEnList", docEnList);
		return new ModelAndView("platform/doc/showEnList", m);
	}
}

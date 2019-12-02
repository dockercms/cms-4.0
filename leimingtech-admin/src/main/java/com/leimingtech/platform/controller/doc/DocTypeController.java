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
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.doc.DocTypeEntity;
import com.leimingtech.platform.service.doc.DocTypeServiceI;

/**
 * @Title: Controller
 * @Description: 文档分类
 * @author
 * @date 2014-06-11 13:14:22
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/docTypeController")
public class DocTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DocTypeController.class);

	@Autowired
	private DocTypeServiceI docTypeService;
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
	 * 文档分类列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "docType")
	public ModelAndView docType(HttpServletRequest request, String docname) {
		// 获取文档分类列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(DocTypeEntity.class, pageSize, pageNo, "", "");
		if (StringUtils.isNotEmpty(docname)) {
			cq.like("name", "%" + docname.trim() + "%");
		}
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.docTypeService.getPageList(cq, true);
		List<DocTypeEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("docname", docname);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "docTypeController.do?docType");
		return new ModelAndView("platform/doc/docTypeList", m);
	}

	/**
	 * 文档分类添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("docType", new DocTypeEntity());
		return new ModelAndView("platform/doc/docType", m);
	}

	/**
	 * 文档分类更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		DocTypeEntity docType = docTypeService.getEntity(DocTypeEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("docType", docType);
		return new ModelAndView("platform/doc/docType", m);
	}

	/**
	 * 文档分类保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(DocTypeEntity docType, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(docType.getId())) {
			message = "文档分类["+docType.getName()+"]更新成功";
			DocTypeEntity t = docTypeService.get(DocTypeEntity.class, docType.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(docType, t);

				t.setUpdateby(PlatFormUtil.getSessionUser().getUserName());
				t.setUpdatetime(new Date());
				t.setUpdatecount(t.getUpdatecount() + 1);
				docTypeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文档分类["+docType.getName()+"]更新失败";
			}
		} else {
			message = "文档分类["+docType.getName()+"]添加成功";

			docType.setUpdatecount(0);
			docType.setCreatedtime(new Date());
			docType.setCreatedby(PlatFormUtil.getSessionUser().getUserName());
			docTypeService.save(docType);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "docTypeController.do?docType");
		String str = j.toString();
		return str;
	}

	/**
	 * 文档分类删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		DocTypeEntity docType = docTypeService.getEntity(DocTypeEntity.class, String.valueOf(id));
		message = "文档分类["+docType.getName()+"]删除成功";
		docTypeService.delete(docType);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "docTypeController.do?docType");
		String str = j.toString();
		return str;
	}
}

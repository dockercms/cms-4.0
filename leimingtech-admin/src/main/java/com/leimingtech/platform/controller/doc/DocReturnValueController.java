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
import com.leimingtech.platform.entity.doc.DocEntity;
import com.leimingtech.platform.entity.doc.DocReturnValueEntity;
import com.leimingtech.platform.entity.doc.DocTypeEntity;
import com.leimingtech.platform.service.doc.DocReturnValueServiceI;
import com.leimingtech.platform.service.doc.DocServiceI;
import com.leimingtech.platform.service.doc.DocTypeServiceI;

/**
 * @Title: Controller
 * @Description: 返回结果
 * @author
 * @date 2014-06-19 09:32:39
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/docReturnValueController")
public class DocReturnValueController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DocReturnValueController.class);

	@Autowired
	private DocReturnValueServiceI docReturnValueService;// 文档返回结果管理接口
	@Autowired
	private DocTypeServiceI docTypeService;
	@Autowired
	private DocServiceI docService;// 文档管理接口
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
	 * 返回结果列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "docReturnValue")
	public ModelAndView docReturnValue(DocReturnValueEntity docReturnValue, HttpServletRequest request, String docid) {
		// 获取返回结果列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(DocReturnValueEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, docReturnValue, param);

		//if (docid != null && docid > 0){ 
		if (docid != null && !docid.equals("0")) {
			cq.eq("docid", docid);
		}
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.docReturnValueService.getPageList(cq, true);
		List<DocReturnValueEntity> resultList = pageList.getResultList();

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
		if (docid != null && !docid.equals("0")) {
			m.put("docid", docid);
			DocEntity doc = docService.getDoc(docid);
			DocTypeEntity docType = docTypeService.getEntity(DocTypeEntity.class, doc.getTypeid());
			m.put("docType", docType.getName());
			m.put("pid", doc.getPid());
		}
		m.put("actionUrl", "docReturnValueController.do?docReturnValue&docid=" + docid);
		return new ModelAndView("platform/doc/docReturnValueList", m);
	}

	/**
	 * 返回结果添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust, String docid) {
		Map<String, Object> m = new HashMap<String, Object>();
		DocReturnValueEntity docReturnValue = new DocReturnValueEntity();
		if (docid != null && !docid.equals("0")) {
			docReturnValue.setDocid(docid);
			DocEntity doc =docService.getDoc(docid);
			DocTypeEntity docType = docTypeService.getEntity(DocTypeEntity.class, doc.getTypeid());
			m.put("docType", docType.getName());
		}
		m.put("docReturnValue", docReturnValue);
		return new ModelAndView("platform/doc/docReturnValue", m);
	}

	/**
	 * 返回结果更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		DocReturnValueEntity docReturnValue = docReturnValueService.getEntity(DocReturnValueEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();

		//if (docReturnValue.getDocid() != null && docReturnValue.getDocid() > 0) {
		if (docReturnValue.getDocid() != null && !docReturnValue.getDocid().equals("0")) {
			DocEntity doc = docService.getDoc(docReturnValue.getDocid());
			DocTypeEntity docType = docTypeService.getEntity(DocTypeEntity.class, doc.getTypeid());
			m.put("docType", docType.getName());
		}

		m.put("docReturnValue", docReturnValue);
		return new ModelAndView("platform/doc/docReturnValue", m);
	}

	/**
	 * 返回结果保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(DocReturnValueEntity docReturnValue, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		TSUser user = PlatFormUtil.getSessionUser();
		if (StringUtil.isNotEmpty(docReturnValue.getId())) {
			message = "返回结果["+docReturnValue.getName()+"]更新成功";
			DocReturnValueEntity t = docReturnValueService.get(DocReturnValueEntity.class, docReturnValue.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(docReturnValue, t);

				t.setUpdatetime(new Date());
				t.setUpdateby(user.getUserName());
				t.setUpdatecount(t.getUpdatecount() + 1);
				docReturnValueService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "返回结果["+docReturnValue.getName()+"]更新失败";
			}
		} else {
			message = "返回结果["+docReturnValue.getName()+"]添加成功";
			if (StringUtils.isEmpty(docReturnValue.getDescription())) {
				docReturnValue.setDescription(docReturnValue.getName());
			}
			docReturnValue.setCreatedtime(new Date());
			docReturnValue.setCreatedby(user.getUserName());
			docReturnValue.setStatus(1);
			docReturnValue.setUpdatecount(0);
			docReturnValueService.save(docReturnValue);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "docReturnValueController.do?docReturnValue&docid=" + docReturnValue.getDocid());
		String str = j.toString();
		return str;
	}

	/**
	 * 返回结果删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		DocReturnValueEntity docReturnValue = docReturnValueService.getEntity(DocReturnValueEntity.class, String.valueOf(id));
		j.accumulate("toUrl", "docReturnValueController.do?docReturnValue&docid=" + docReturnValue.getDocid());
		message = "返回结果["+docReturnValue.getName()+"]删除成功";
		docReturnValueService.delete(docReturnValue);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
}

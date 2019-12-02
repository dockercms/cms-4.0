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
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.doc.DocEntity;
import com.leimingtech.platform.entity.doc.DocParamEntity;
import com.leimingtech.platform.service.doc.DocParamServiceI;
import com.leimingtech.platform.service.doc.DocServiceI;

/**
 * @Title: Controller
 * @Description: 标签及api输入参数
 * @author
 * @date 2014-06-13 17:04:41
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/docParamController")
public class DocParamController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DocParamController.class);

	@Autowired
	private DocParamServiceI docParamService;
	@Autowired
	private DocServiceI docService;
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
	 * 标签及api输入参数列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "docParam")
	public ModelAndView docParam(DocParamEntity docParam, HttpServletRequest request, String docid) {
		// 获取标签及api输入参数列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(DocParamEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, docParam, param);

		if (docid != null && !docid.equals("0")) {
			cq.eq("docid", docid);
		}
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.docParamService.getPageList(cq, true);
		List<DocParamEntity> resultList = pageList.getResultList();

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
			m.put("pid", doc.getPid());
		}
		m.put("actionUrl", "docParamController.do?docParam&docid=" + docid);
		return new ModelAndView("platform/doc/docParamList", m);
	}

	/**
	 * 标签及api输入参数添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust, String docid) {
		Map<String, Object> m = new HashMap<String, Object>();
		DocParamEntity docParam = new DocParamEntity();

		if (docid != null && !docid.equals("0")){
			docParam.setDocid(docid);
		}

		m.put("docParam", docParam);
		return new ModelAndView("platform/doc/docParam", m);
	}

	/**
	 * 标签及api输入参数更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		DocParamEntity docParam = docParamService.getEntity(DocParamEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("docParam", docParam);
		return new ModelAndView("platform/doc/docParam", m);
	}

	/**
	 * 标签及api输入参数保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(DocParamEntity docParam, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(docParam.getId())) {
			message = "标签及api输入参数["+docParam.getName()+"]更新成功";
			DocParamEntity t = docParamService.get(DocParamEntity.class, docParam.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(docParam, t);
				docParamService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "标签及api输入参数["+docParam.getName()+"]更新失败";
			}
		} else {
			message = "标签及api输入参数["+docParam.getName()+"]添加成功";
			docParam.setCreatedtime(new Date());//创建时间
			docParamService.save(docParam);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "docParamController.do?docParam&docid=" + docParam.getDocid());
		String str = j.toString();
		return str;
	}

	/**
	 * 标签及api输入参数删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		DocParamEntity docParam = docParamService.getEntity(DocParamEntity.class, String.valueOf(id));
		j.accumulate("toUrl", "docParamController.do?docParam&docid=" + docParam.getDocid());
		message = "标签及api输入参数["+docParam.getName()+"]删除成功";
		docParamService.delete(docParam);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
}

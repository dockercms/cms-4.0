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
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.doc.DocEntity;
import com.leimingtech.platform.entity.doc.DocTypeEntity;
import com.leimingtech.platform.service.doc.DocServiceI;
import com.leimingtech.platform.service.doc.DocTypeServiceI;

/**
 * @Title: Controller
 * @Description: 文档
 * @author
 * @date 2014-06-11 16:04:26
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/docController")
public class DocController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DocController.class);

	@Autowired
	private DocServiceI docService;// 文档管理接口
	@Autowired
	private SystemService systemService;
	@Autowired
	private DocTypeServiceI docTypeService;// 文档类型管理接口
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 文档列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "doc")
	public ModelAndView doc(HttpServletRequest request, String name, String typeid, String pid,String platform) {

		// 获取文档列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		Map<String, Object> m = docService.getPageList( name,typeid,pid,platform, pageSize, pageNo);

		m.put("typeid", typeid);
		m.put("name", name);
		m.put("pageSize", pageSize);
		m.put("pid", pid);// 父文档id
		m.put("platform", platform);
	
		return new ModelAndView("platform/doc/docList", m);
	}

	/**
	 * 文档添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust, String pid) {

		Map<String, Object> m = new HashMap<String, Object>();
		DocEntity doc = new DocEntity();
		if (StringUtils.isNotEmpty(pid) && !pid.equals("0")) {
			// 确定是添加子集文档查出类型
			doc.setPid(pid);
			doc.setTypeid(docTypeService.findByProperty(DocTypeEntity.class, "name", "API").get(0).getId());
			doc.setType("API");
		} else {
			// 如果添加的是一级文档 将查出分类作为下拉
			CriteriaQuery cq = new CriteriaQuery(DocTypeEntity.class);
			cq.eq("status", 1);
			cq.addOrder("sort", SortDirection.desc);
			cq.add();

			List<DocTypeEntity> docTypeList = docTypeService.getListByCriteriaQuery(cq, false);
			m.put("docTypeList", docTypeList);
		}
		m.put("doc", doc);
		return new ModelAndView("platform/doc/doc", m);
	}

	/**
	 * 文档更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");

		DocEntity doc = docService.getDoc(id);
		DocTypeEntity doctype = docTypeService.getEntity(DocTypeEntity.class, doc.getTypeid());
		doc.setType(doctype.getName());// 修改时文档类型将不可变 查出作为显示

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("doc", doc);
		return new ModelAndView("platform/doc/doc", m);
	}

	/**
	 * 文档保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(DocEntity doc, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		
		if (StringUtil.isNotEmpty(doc.getId())) {
			message = "文档["+doc.getName()+"]更新成功";
			DocEntity t = docService.getDoc(doc.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(doc, t);

				t.setUpdateby(PlatFormUtil.getSessionUser().getUserName());
				t.setUpdatetime(new Date());
				t.setUpdatecount(t.getUpdatecount());
				docService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文档["+doc.getName()+"]更新失败";
			}
		} else {
			if(doc.getPid().equals("")){
				message = "文档["+doc.getName()+"]添加成功";
				doc.setCreatedby(PlatFormUtil.getSessionUser().getUserName());
				doc.setPid("0");
				doc.setCreatedtime(new Date());
				doc.setUpdatecount(0);
				docService.save(doc);
				
			}else{
	            message = "文档["+doc.getName()+"]添加成功";
				
				doc.setCreatedby(PlatFormUtil.getSessionUser().getUserName());
				doc.setCreatedtime(new Date());
				doc.setUpdatecount(0);
				docService.save(doc);
			}
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		if (doc.getPid() != null && !doc.getPid().equals("0")) {
			j.accumulate("toUrl", "docController.do?doc&pid=" + doc.getPid());
		} else {
			j.accumulate("toUrl", "docController.do?doc");
		}
		String str = j.toString();
		return str;
	}

	/**
	 * 文档删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		DocEntity doc = docService.getDoc(id);

		if (doc.getPid() != null && !doc.getPid().equals("0")) {
			j.accumulate("toUrl", "docController.do?doc&pid=" + doc.getPid());
		} else {
			j.accumulate("toUrl", "docController.do?doc");
		}

		message = "文档["+doc.getName()+"]删除成功";
		docService.delete(doc);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
}

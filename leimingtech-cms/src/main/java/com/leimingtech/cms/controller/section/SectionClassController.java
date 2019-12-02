package com.leimingtech.cms.controller.section;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.entity.section.SectionClassEntity;
import com.leimingtech.cms.service.SectionClassServiceI;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**
 * @Title: Controller
 * @Description: 区块分类
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-24 09:35:35
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/sectionClassController")
public class SectionClassController extends BaseController {
	private static final Logger logger = Logger.getLogger(SectionClassController.class);
	private String message = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	private SystemService systemService;

	@Autowired
	private SectionClassServiceI sectionClassMng;
	/**PC栏目管理接口*/
	@Autowired
	private ContentCatServiceI contentCatService;
	@Autowired
	private CommonService commonService;
	/**
	 * 列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "sectionClass")
	public ModelAndView function(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		String siteid = ClientManager.getInstance().getClient(session.getId()).getSite().getId();

		CriteriaQuery cq = new CriteriaQuery(SectionClassEntity.class);
		cq.eq("levels", 0);
		cq.eq("siteid", siteid);
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		List<SectionClassEntity> list = sectionClassMng.getListByCriteriaQuery(cq, false);

		SectionClassEntity parent = new SectionClassEntity();
		parent.setId(null);
		parent.setName("顶级页面");

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		m.put("parentFunction", parent);
		return new ModelAndView("cms/section/sectionClassList", m);
	}

	/**
	 * 菜单管理页面
	 *
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "flushMenu")
	public ModelAndView flushMenu(HttpServletRequest request) {
		ModelAndView mav = this.function(request);
		String id = request.getParameter("id");
		List<String> idList = new ArrayList<String>();
		if (id != null && !"".equals(id)) {
			SectionClassEntity function = sectionClassMng.getEntity(SectionClassEntity.class, String.valueOf(id));
			idList = this.getParenidList(idList, function);
		}

		Map<String, Object> m = mav.getModel();
		m.put("parentIdList", idList);
		return new ModelAndView("cms/section/menuBody", m);
	}

	private List<String> getParenidList(List<String> idList, SectionClassEntity function) {
		if (function != null) {
			idList.add(function.getId() + "");
			if (function.getSectionClass() != null) {
				idList = this.getParenidList(idList, function.getSectionClass());
			}
		}
		return idList;
	}

	/**
	 * 加载下级
	 *
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "load")
	@ResponseBody
	public JSONArray loadMenu(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		String siteid = ClientManager.getInstance().getClient(session.getId()).getSite().getId();

		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(SectionClassEntity.class);
		cq.eq("sectionClass.id", String.valueOf(id));
		cq.eq("siteid", siteid);
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		List<SectionClassEntity> list = sectionClassMng.getListByCriteriaQuery(cq, false);
		JSONArray jsonArray = new JSONArray();
		for (SectionClassEntity sectionClass : list) {
			JSONObject json = new JSONObject();
			if (sectionClass.getSectionClasss() != null && sectionClass.getSectionClasss().size() > 0) {
				json.put("text", sectionClass.getName());
				json.put("value", sectionClass.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", sectionClass.getId());
				json.put("href", "sectionClassController.do?load&id=" + sectionClass.getId());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			} else {
				json.put("text", "<i class=\"icon-file-text\" style=\"width: 1em;margin-right: 4px;\"></i>" + sectionClass.getName());
				json.put("value", sectionClass.getId());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", sectionClass.getId());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}

	/**
	 * 加载下级地区
	 *
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loads")
	@ResponseBody
	public String loadMenus(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		String siteid = ClientManager.getInstance().getClient(session.getId()).getSite().getId();

		String id = request.getParameter("id");
		CriteriaQuery cq = new CriteriaQuery(SectionClassEntity.class);
		cq.eq("sectionClass.id", String.valueOf(id));
		cq.eq("siteid", siteid);
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		List<SectionClassEntity> list = sectionClassMng.getListByCriteriaQuery(cq, false);
		JSONArray jsonArray = new JSONArray();
		for (SectionClassEntity sectionClass : list) {
			JSONObject json = new JSONObject();
			if (sectionClass.getSectionClasss() != null && sectionClass.getSectionClasss().size() > 0) {
				json.put("text", sectionClass.getName());
				json.put("value", sectionClass.getId());
				json.put("leaf", false);
				json.put("expanded", false);
				json.put("cls", "folder");
				json.put("id", sectionClass.getId());
				json.put("href", "sectionClassController.do?load&id=" + sectionClass.getId());
				json.put("data-role", "branch");
				json.put("children", new JSONArray());
			} else {
				json.put("text", sectionClass.getName());
				json.put("value", sectionClass.getId());
				json.put("leaf", true);
				json.put("href", "javascript:void(0);");
				json.put("data-role", "leaf");
				json.put("id", sectionClass.getId());
				json.put("checked", false);
			}
			jsonArray.add(json);
		}
		return jsonArray.toString();
	}

	/**
	 * 修改
	 *
	 * @param test
	 * @param request
	 * @return
	 */

	@RequestMapping(params = "editModelPage")
	public ModelAndView editModelPage(HttpServletRequest request) {
		String id = request.getParameter("id");
		String selectId = request.getParameter("selectId");
		SectionClassEntity sectionClass = null;
		if (id != null && !"".equals(id)) {
			sectionClass = sectionClassMng.getEntity(SectionClassEntity.class, String.valueOf(id));
		} else {
			String parentId = request.getParameter("parentId");
			sectionClass = new SectionClassEntity();
			SectionClassEntity parent = new SectionClassEntity();
			if (parentId != null && !"".equals(parentId)) {
				parent.setId(String.valueOf(parentId));
			}
			sectionClass.setSectionClass(parent);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sectionClass", sectionClass);
		m.put("selectId", selectId);
		return new ModelAndView("cms/section/sectionClasseditModel", m);
	}

	@RequestMapping(params = "update")
	@ResponseBody
	public String update(SectionClassEntity sectionClass, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (sectionClass.getSectionClass() != null && sectionClass.getSectionClass().getId() == null) {
			sectionClass.setSectionClass(null);
		}
		if (StringUtil.isNotEmpty(sectionClass.getId())) {
			message = "菜单信息更新成功";
			SectionClassEntity t = sectionClassMng.get(SectionClassEntity.class, sectionClass.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sectionClass, t);
				sectionClassMng.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "菜单信息["+sectionClass.getName()+"]更新失败";
			}
		} else {
			message = "菜单信息["+sectionClass.getName()+"]添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			String siteid = ClientManager.getInstance().getClient(session.getId()).getSite().getId();
			sectionClass.setSiteid(siteid);// 加入站点

			if (sectionClass.getSectionClass() == null) {
				sectionClass.setLevels(0);
			} else {
				SectionClassEntity pSection = sectionClassMng.get(SectionClassEntity.class, sectionClass.getSectionClass().getId());
				sectionClass.setLevels(pSection.getLevels() + 1);
			}
			sectionClass.setCreatedtime(new Date());//创建时间
			sectionClassMng.save(sectionClass);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 加载下级
	 *
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "Table")
	public ModelAndView menuTable(HttpServletRequest request) {
		String id = request.getParameter("id");
		SectionClassEntity parent = null;
		HttpSession session = ContextHolderUtils.getSession();
		String siteid = ClientManager.getInstance().getClient(session.getId()).getSite().getId();
		Map<String, Object> m = new HashMap<String, Object>();
		if (id != null && "".equals(id)) {
			// 点击顶级菜单
			parent = new SectionClassEntity();
			parent.setId(null);
			parent.setName("顶级菜单");
		} else {
			parent = sectionClassMng.getEntity(SectionClassEntity.class, String.valueOf(id));
			CriteriaQuery cqClass = new CriteriaQuery(SectionClassEntity.class);
			cqClass.eq("sectionClass.id", String.valueOf(id));
			cqClass.eq("siteid", siteid);
			cqClass.addOrder("sort", SortDirection.desc);
			cqClass.add();
			List<SectionClassEntity> listClass = sectionClassMng.getListByCriteriaQuery(cqClass, false);

			m.put("list", listClass);
			m.put("selectId", id);
		}

		m.put("parentFunction", parent);

		// 三级级联菜单第一级
		CriteriaQuery cq = new CriteriaQuery(SectionClassEntity.class);
		cq.eq("levels", 1);
		cq.eq("siteid", siteid);
		cq.addOrder("sort", SortDirection.desc);
		cq.add();
		List<SectionClassEntity> list = sectionClassMng.getListByCriteriaQuery(cq, false);
		m.put("list_test", list);
		return new ModelAndView("cms/section/sectionClass", m);

	}

	/**
	 * 区块删除
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		SectionClassEntity sectionClass = sectionClassMng.getEntity(SectionClassEntity.class, String.valueOf(id));

		message = "区块分类 " + sectionClass.getName() + " 删除成功！";
		sectionClassMng.deleteSectionClass(sectionClass);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sectionClassController.do?sectionClass");
		String str = j.toString();
		return str;
	}

	/**
	 * 区块分类树
	 *
	 * @param requset
	 * @return
	 */
	@RequestMapping(params = "sectionClassTree")
	public ModelAndView sectionClassTree(HttpServletRequest requset) {
		List<ContentCatEntity> list = contentCatService.getAllIndexContentCat();//获取当前站点中是首页的栏目
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		return new ModelAndView("cms/section/sectionClassTree", m);

	}

}

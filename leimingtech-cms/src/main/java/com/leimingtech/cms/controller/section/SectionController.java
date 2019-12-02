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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SectionDataEntity;
import com.leimingtech.core.entity.SectionEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.SectionDataServiceI;
import com.leimingtech.core.service.SectionServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**
 * @Title: Controller
 * @Description: 区块信息
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-22 11:44:29
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/sectionController")
public class SectionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SectionController.class);

	@Autowired
	private IStatic staticMng;

	@Autowired
	private SectionServiceI sectionMng;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SectionDataServiceI sectionDataService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 区块信息列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(SectionEntity section, HttpServletRequest request) {
		// 获取区块信息列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(SectionEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, section, param);
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.sectionMng.getPageList(cq, true);
		List<SectionEntity> testList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "sectionController.do?sectionList&catalogid=" + section.getClassid());
		return new ModelAndView("cms/section/sectionList", m);
	}

	/**
	 * 区块信息列表含所属区块分类信息
	 * 
	 * @param section
	 *            区块查询信息
	 * @param request
	 */
	@RequestMapping(params = "sectionList")
	public ModelAndView sectionList(SectionEntity section, HttpServletRequest request) {
		String catalogid = request.getParameter("catalogid");
		Map<String, Object> m = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(catalogid)) {
			ContentCatEntity catalog = null;
			if ("-1".equals(catalogid)) {
				catalog = new ContentCatEntity();
				catalog.setId(String.valueOf(catalogid));
				catalog.setName("首页");
				catalog.setPathids("-1,");
			} else {
				catalog = sectionDataService.getEntity(ContentCatEntity.class, String.valueOf(catalogid));
			}
			CriteriaQuery cq = new CriteriaQuery(SectionEntity.class);
			// 查询条件组装器
			cq.like("pathids", catalog.getPathids() + "%");
			cq.eq("siteid", PlatFormUtil.getSessionSiteId());
			cq.addOrder("sort", SortDirection.desc);
			cq.add();

			List<SectionEntity> sectionList = sectionDataService.getListByCriteriaQuery(cq, false);
			m.put("sectionList", sectionList);
			m.put("catalog", catalog);
			if (sectionList != null && sectionList.size() > 0) {
				m.put("section", sectionList.get(0));

				String sectionid = sectionList.get(0).getId();
				CriteriaQuery cqData = new CriteriaQuery(SectionDataEntity.class);
				cqData.eq("sectionid", sectionid);
				cqData.addOrder("sort", SortDirection.desc);
				cqData.add();
				List<SectionDataEntity> sectionDataList = sectionDataService.getListByCriteriaQuery(cqData, false);
				sectionDataService.waterListAddCreateUser(sectionDataList);
				m.put("sectionDataList", sectionDataList);
			} else {
				m.put("warning", "<span style='color:red'>" + catalog.getName() + "</span>下还没有区块，可以点击下面添加区块按钮进行添加！");
			}
		}

		return new ModelAndView("cms/section/sectionListInClass", m);
	}

	/**
	 * 区块信息添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		String classid = request.getParameter("classid");
		ContentCatEntity catalog = null;
		if (StringUtils.isNotEmpty(classid) && "-1".equals(classid)) {
			catalog = new ContentCatEntity();
			catalog.setId(String.valueOf(classid));
			catalog.setName("首页");
		} else {
			catalog = sectionDataService.getEntity(ContentCatEntity.class, String.valueOf(classid));
		}
		m.put("catalog", catalog);
		m.put("page", new SectionEntity());
		return new ModelAndView("cms/section/section", m);
	}

	/**
	 * 区块信息更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest request) {
		String id = request.getParameter("id");
		SectionEntity section = sectionMng.getEntity(SectionEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		String classid = request.getParameter("classid");
		ContentCatEntity catalog = null;
		if (StringUtils.isNotEmpty(classid) && "-1".equals(classid)) {
			catalog = new ContentCatEntity();
			catalog.setId(String.valueOf(classid));
			catalog.setName("首页");
		} else {
			catalog = sectionDataService.getEntity(ContentCatEntity.class, String.valueOf(classid));
		}

		m.put("catalog", catalog);
		m.put("page", section);
		return new ModelAndView("cms/section/section", m);
	}

	/**
	 * 区块信息保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(HttpServletRequest request, SectionEntity section) {
		JSONObject j = new JSONObject();
		String classid = request.getParameter("classid");
		if ("-1".equals(classid)) {
			section.setPathids("-1,");
		} else {
			ContentCatEntity catalog = sectionDataService.getEntity(ContentCatEntity.class, String.valueOf(classid));
			section.setPathids(catalog.getPathids());
		}
		if (StringUtil.isNotEmpty(section.getId())) {
			message = "区块【"+section.getName()+"】更新成功";
			SectionEntity t = sectionMng.get(SectionEntity.class, section.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(section, t);
				t.setModifiedtime(new Date());

				t.setModifiedby(PlatFormUtil.getSessionUser().getId());
				sectionMng.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "区块【"+section.getName()+"】更新失败";
			}
		} else {
			message = "区块【"+section.getName()+"】添加成功";
			section.setCreatedtime(new Date());
			section.setCreatedby(1);
			section.setSiteid(PlatFormUtil.getSessionSiteId());
			sectionMng.save(section);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sectionController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 区块信息删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		SectionEntity section = sectionDataService.getEntity(SectionEntity.class, String.valueOf(id));
		message = "区块【"+section.getName()+"】删除成功";
		List<SectionEntity> sectionList = new ArrayList<SectionEntity>();
		sectionList.add(section);
		sectionMng.delSectionList(sectionList);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sectionController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 区块生成静态
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "createSectionStatic")
	@ResponseBody
	public String createSectionStatic(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");// 区块分类id
		ContentCatEntity catalog = null;
		if (StringUtils.isNotEmpty(id) && "-1".equals(id)) {
			catalog = new ContentCatEntity();
			catalog.setName("首页");
			catalog.setId("-1");
		} else {
			catalog = sectionDataService.getEntity(ContentCatEntity.class, String.valueOf(id));
		}
		try {
			staticMng.staticOneSectionClass(id,PlatFormUtil.getSessionSite());
			message = "区块分类《 " + catalog.getName() + " 》下的区块生成成功！";
			j.accumulate("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			j.accumulate("isSuccess", false);
			message = "区块分类《 " + catalog.getName() + "》 下的区块生成失败！原因：" + e.getMessage();
		}
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	/**
	 * 推送到区块
	 */
	@RequestMapping(params = "toSection")
	public ModelAndView toSection(HttpServletRequest request) {

		String countId= request.getParameter("countId");//内容id
		ContentsEntity content = sectionMng.getEntity(ContentsEntity.class, countId);
		String imglist[] = content.getThumb().split(",");
		String img =imglist[0].replace(",", "");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("content", content);
		m.put("img", img);
		return new ModelAndView("cms/section/toSection", m);
	}
	/**
	 * ztree 推送到区块 区块名称
	 *
	 * @param request
	 * @return json
	 * @author larry
	 */
	@RequestMapping(params = "sectionName")
	@ResponseBody
	public JSONArray sectionName(HttpServletRequest request) {

		JSONArray jsonArray = new JSONArray();
		// 频道tree

		HttpSession session = ContextHolderUtils.getSession();
		String siteid = ClientManager.getInstance().getClient(session.getId()).getSite().getId();

		CriteriaQuery cq = new CriteriaQuery(SectionEntity.class);
		cq.eq("siteid", siteid);
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		List<SectionEntity> list = sectionMng.getListByCriteriaQuery(cq, false);

		for (SectionEntity section : list) {
			JSONObject json = new JSONObject();
			// 父节点

			json.put("id", section.getId());
			json.put("name", section.getName());
			json.put("open", false);
			jsonArray.add(json);
		}
		return jsonArray;

	}

	/**
	 * 区块所关联数据保存
	 *
	 * @return
	 */
	@RequestMapping(params = "sendSection")
	@ResponseBody
	public String sendSection(SectionDataEntity sectionData, HttpServletRequest request) {
			JSONObject j = new JSONObject();
			String sectionid =sectionData.getSectionid();
			String[] sectionids =sectionid.split(",");
			for (int i=0;i<sectionids.length;i++){
				SectionDataEntity section =new SectionDataEntity();
				section.setContentid(sectionData.getContentid());
				section.setDescription(sectionData.getDescription());
				section.setThumb(sectionData.getThumb());
				section.setSort(sectionData.getSort());
				section.setTitle(sectionData.getTitle());
				section.setSectionid(sectionids[i]);
				section.setUrl(sectionData.getUrl());
				section.setCreated(new Date());
				section.setCreatedby(PlatFormUtil.getSessionUser().getId());
				section.setColor("#000");
				sectionDataService.save(section);
			}
			message = "区块数据添加成功";

			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

			j.accumulate("isSuccess", true);
			j.accumulate("msg", message);
			String str = j.toString();
			return str;
	}

}

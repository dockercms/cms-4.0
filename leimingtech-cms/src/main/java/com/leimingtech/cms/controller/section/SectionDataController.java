package com.leimingtech.cms.controller.section;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.SectionDataEntity;
import com.leimingtech.core.entity.SectionEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SectionDataServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**
 * @Title: Controller
 * @Description: 区块所关联数据
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-22 11:46:30
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/sectionDataController")
public class SectionDataController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SectionDataController.class);

	@Autowired
	private SectionDataServiceI sectionDataService;
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
	 * 区块所关联数据列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(SectionDataEntity sectionData, HttpServletRequest request) {
		// 获取区块所关联数据列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(SectionDataEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, sectionData, param);
		// 排序条件
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		PageList pageList = this.sectionDataService.getPageList(cq, true);
		List<SectionDataEntity> testList = pageList.getResultList();

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
		m.put("actionUrl", "sectionDataController.do?table");
		return new ModelAndView("cms/section/sectionDataList", m);
	}

	/**
	 * 区块所关联数据添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest request) {
		String sectionid = request.getParameter("sectionid");
		Map<String, Object> m = new HashMap<String, Object>();
		SectionDataEntity sectionDate = new SectionDataEntity();
		sectionDate.setSectionid(String.valueOf(sectionid));
		m.put("page", sectionDate);
		return new ModelAndView("cms/section/sectionData", m);
	}

	/**
	 * 区块所关联数据更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		SectionDataEntity sectionData = sectionDataService.getEntity(SectionDataEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", sectionData);
		return new ModelAndView("cms/section/sectionData", m);
	}

	/**
	 * 区块所关联数据保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(SectionDataEntity sectionData, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(sectionData.getId())) {
			message = "区块数据更新成功";
			SectionDataEntity t = sectionDataService.get(SectionDataEntity.class, sectionData.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sectionData, t);
				t.setCommended(new Date());
				t.setCommendedby(1);
				sectionDataService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "区块数据更新失败";
			}
		} else {
			message = "区块数据添加成功";
			sectionData.setCreated(new Date());
			sectionData.setCreatedby(PlatFormUtil.getSessionUser().getId());
			sectionData.setColor("#000");
			sectionDataService.save(sectionData);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sectionDataController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 区块所关联数据删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		SectionDataEntity sectionData = sectionDataService.getEntity(SectionDataEntity.class, String.valueOf(id));
		message = "区块所关联数据删除成功";
		sectionDataService.delete(sectionData);
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sectionDataController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 区块下数据
	 * 
	 * @param section
	 *            区块查询信息
	 * @param request
	 */
	@RequestMapping(params = "sectionDataList")
	public ModelAndView sectionList(SectionDataEntity sectionData, HttpServletRequest request) {
		String sectionid = request.getParameter("sectionid");
		Map<String, Object> m = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(sectionid)) {
			SectionEntity section = sectionDataService.getEntity(SectionEntity.class, String.valueOf(sectionid));
			CriteriaQuery cq = new CriteriaQuery(SectionDataEntity.class);
			cq.eq("sectionid", String.valueOf(sectionid));
			cq.addOrder("sort", SortDirection.desc);
			cq.addOrder("id", SortDirection.desc);
			cq.add();
			List<SectionDataEntity> sectionDataList = sectionDataService.getListByCriteriaQuery(cq, false);
			sectionDataService.waterListAddCreateUser(sectionDataList);//向区块数据中添加创建人信息
			
			m.put("sectionDataList", sectionDataList);
			m.put("section", section);

			ContentCatEntity catalog = null;
			if ("-1".equals(section.getClassid())) {
				catalog = new ContentCatEntity();
				catalog.setName("首页");
				catalog.setId("-1");
			} else {
				catalog = sectionDataService.getEntity(ContentCatEntity.class, section.getClassid());
			}

			m.put("catalog", catalog);
		}
		return new ModelAndView("cms/section/sectionDatebody", m);
	}
}

package com.leimingtech.cms.controller.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.controller.contents.ContentClassify;
import com.leimingtech.cms.controller.contents.ContentsbaseController;
import com.leimingtech.cms.service.activity.ActivityOptionContentServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.ActivityEntity;
import com.leimingtech.core.entity.ActivityOptionContentEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ModelExtEntity;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ActivityServiceI;
import com.leimingtech.core.service.ContentTagServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.SourceServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.modelext.ModelExtServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * @Title: Controller
 * @Description: 活动
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-06 16:05:37
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/activityController")
public class ActivityController extends ContentsbaseController {

	@Autowired
	private ActivityServiceI activityService;
	@Autowired
	private ActivityOptionContentServiceI activityOptionContentService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private ModelManageServiceI modelManageService;
	@Autowired
	private ModelExtServiceI modelExtService;
	@Autowired
	private ModelItemServiceI modelItemService;
	@Autowired
	private ContentTagServiceI contentTagService;
	@Autowired
	private SourceServiceI sorceService;
	@Autowired
	private IStatic staticImpl;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 活动列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(ActivityEntity activity,
			HttpServletRequest request) {
		// 获取活动列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(ActivityEntity.class, pageSize,
				pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, activity, param);
		// 排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.activityService.getPageList(cq, true);
		List<ActivityEntity> testList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "activityController.do?table");
		return new ModelAndView("cms/activity/activityList", m);
	}

	/**
	 * 活动添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust) {
		String contentCatId = reqeust.getParameter("contentCatId");
		String modelsId = reqeust.getParameter("modelsId");
		String temporary = String.valueOf(System.currentTimeMillis());
		// 区分添加/编辑页面
		String flag = "add";
		ContentCatEntity contentCat = contentsService.get(
				ContentCatEntity.class, String.valueOf(contentCatId));
		ModelManageEntity modelManage = null;
		if (StringUtils.isNotEmpty(contentCat.getJsonid())) {
			modelManage = modelManageService.getEntity(contentCat.getJsonid());
		}
		List<ModelItemEntity> modelItemList = new ArrayList<ModelItemEntity>();
		if (modelManage != null) {
			modelItemList = modelItemService.findByModelId(modelManage.getId());
		}

		List<ContentTagEntity> contentTagList = contentTagService
				.loadAll(ContentTagEntity.class); // 获取Tags标签内容
		List<SourceEntity> sourceEntityList = sorceService
				.loadAll(SourceEntity.class); // 获取来源内容

		Map<String, Object> m = new HashMap<String, Object>();
		String sourceStr = "";
		for (int i = 0; i < sourceEntityList.size(); i++) {
			sourceStr += sourceEntityList.get(i).getName() + ",";
		}
		// 从数据字典中获取内容标记
		List<TSTypegroup> typeGroupList = activityService.findByProperty(
				TSTypegroup.class, "typegroupcode", "content_mark");
		List<TSType> typeList = new ArrayList<TSType>();
		if (typeGroupList.size() != 0) {
			typeList = typeGroupList.get(0).getTSTypes();
		}
		ContentsEntity contents = new ContentsEntity();
		// 设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
		contents.setClassify(ContentClassify.CONTENT_ACTIVITY);
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(sourceStr)) {
			m.put("sourceStr", sourceStr.substring(0, sourceStr.length() - 1));
		} else {
			m.put("sourceStr", CmsConstants.DEFAULT_SOURCE);
		}
		SiteEntity site =PlatFormUtil.getSessionSite();
		m.put("site", site);
		m.put("page", new ActivityEntity());
		m.put("contents", contents);
		m.put("contentCat", contentCat);
		m.put("modelsId", modelsId);
		m.put("typeList", typeList);
		m.put("modelItemList", modelItemList);
		m.put("flag", flag);
		m.put("contentTagList", contentTagList);
		m.put("sourceEntityList", sourceEntityList);
		m.put("markpeople", PlatFormUtil.getSessionUser().getUserName());
		m.put("temporary", temporary);
		m.put("classify", ContentClassify.CONTENT_ACTIVITY);
		m.put("sessionId", reqeust.getSession().getId());
		m.put("memberinfo", PlatFormUtil.getSessionUser());

		return new ModelAndView("cms/activity/activity_open", m);
	}

	/**
	 * 活动更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		ActivityEntity activity = activityService.getEntity(
				ActivityEntity.class, String.valueOf(id));
		List<ContentTagEntity> contentTagList = contentTagService
				.loadAll(ContentTagEntity.class); // 获取Tags标签内容
		List<SourceEntity> sourceEntityList = sorceService
				.loadAll(SourceEntity.class); // 获取来源内容
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", activity);
		m.put("contentTagLists", contentTagList);
		m.put("sourceEntityLists", sourceEntityList);
		return new ModelAndView("cms/activity/activity", m);
	}

	/**
	 * 活动保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(ActivityEntity activity,
			ActivityOptionContentEntity activityOptionContent,
			ContentsEntity contents, ModelItemEntity modelItem,
			HttpServletRequest request) {
		String contentCatId = request.getParameter("contentCatId");
		// 模型模块id
		String modelsId = request.getParameter("modelsId");
		// 扩展字段id
		String modelId = request.getParameter("modelId");
		// 获取内容id
		String contentsId = request.getParameter("contentsId");

		// 获取参数名称
		List<ModelItemEntity> modelItemList = modelItemService.findByModelId(modelId);

		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(activity.getId())) {
			message = "活动[" + activity.getActivitycontent() + "]更新成功";
			ActivityEntity t = activityService.get(ActivityEntity.class,
					activity.getId());
			ContentsEntity c = contentsService.get(ContentsEntity.class,
					String.valueOf(contentsId));
			contents.setId(null);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contents, c);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// 获取自定义字段/值
			String sql = "select modelext.id from cms_model_ext modelext where modelext.model_id="
					+ modelId + " and content_id=" + c.getId();
			List<ModelExtEntity> modelExtList = new ArrayList<ModelExtEntity>();
			List<Map<String, Object>> mapList = activityService
					.findForJdbc(sql);
			if (mapList.size() > 0) {
				Map<String, Object> map;
				for (int k = 0; k < mapList.size(); k++) {
					String modelextid = "";
					map = mapList.get(k);
					modelextid = map.get("id") + "";
					ModelExtEntity modelExt1 = modelExtService.getEntity(modelextid);
					modelExtList.add(modelExt1);
				}
			}
			try {
				// 保存参数名称和value
				for (int i = 0; i < modelExtList.size(); i++) {
					ModelExtEntity modelExt = modelExtList.get(i);
					String name = request.getParameter("name" + i);// label
					String input = request.getParameter(i + "");// input
					if (name.equals(modelExt.getAttrName())) {
						modelExt.setAttrValue(input);
						modelExtService.saveOrUpdate(modelExt);
					}
				}
				MyBeanUtils.copyBeanNotNull2Bean(activity, t);
				contentsService.saveOrUpdate(c);

				activityService.saveOrUpdate(t);
				staticImpl.staticContentActOnPreview(c);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "活动[" + activity.getActivitycontent() + "]更新失败";
			}
		} else {
			message = "活动[" + activity.getActivitycontent() + "]添加成功";
			ContentCatEntity contentcat = activityService.getEntity(
					ContentCatEntity.class, String.valueOf(contentCatId));
			contents.setPathids(contentcat.getPathids());
			contents.setCatid(contentcat.getId());
			contents.setModelid(modelsId);
			// 设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contents.setClassify(ContentClassify.CONTENT_ACTIVITY);
			contents.setCreated(new Date());// 创建时间
			contentsService.save(contents);

			// 进入工作流，判断是否需要审核，如果需要设为待审，如果不需要审核设为已审
			enterworkflow(contents, String.valueOf(contentCatId));

			// 在modelExt中保存modelId/attrName
			for (int i = 0; i < modelItemList.size(); i++) {
				modelItem = modelItemList.get(i);
				String name = request.getParameter("name" + i);// label
				String input = request.getParameter(i + "");// input
				if (name.equals(modelItem.getItemLabel())) {
					ModelExtEntity modelExt = new ModelExtEntity();
					modelExt.setAttrName(modelItem.getItemLabel());
					modelExt.setModelId(String.valueOf(modelId));
					modelExt.setContentId(contents.getId());
					modelExt.setModelItemId(modelItem.getId());
					modelExt.setAttrToken(modelItem.getField());
					modelExt.setDataType(modelItem.getDataType());
					modelExt.setAttrValue(input);
					modelExt.setCreatedtime(new Date());// 创建时间
					modelExtService.save(modelExt);
				}
			}
			activity.setId(contents.getId());
			activity.setCreatedtime(new Date());// 创建时间

			activityService.save(activity);
			activityOptionContentService.save(activityOptionContent);
			staticImpl.staticContentActOnPreview(contents);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl",
				"contentsController.do?table&tab=alone&contentCatId="
						+ contentCatId);
		String str = j.toString();
		return str;
	}

	/**
	 * 活动删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		ActivityEntity activity = activityService.getEntity(
				ActivityEntity.class, String.valueOf(id));
		message = "活动[" + activity.getActivitycontent() + "]删除成功";
		activityService.delete(activity);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "activityController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 活动列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "jumpOption")
	public ModelAndView jumpOption(HttpServletRequest request) {

		String id = request.getParameter("id");
		String sql = null;
		if (StringUtils.isNotEmpty(id)) {
			sql = "select ap.is_enabled as isEnabled, apc.optionids as optionids,ap.is_show as isShow, ap.option_name as optionName,apc.is_enableds as isEnableds,apc.is_receptionShow as isReceptionshow,apc.is_required as isRequired "
					+ "from cms_activity_optioncontent  apc,cms_activity_option ap "
					+ "WHERE ap.id=apc.optionids and ap.is_enabled=1 and  apc.activityids='"
					+ id + "' ORDER BY ap.sort DESC,ap.createtime DESC";
		} else {
			sql = "select ap.is_enabled as isEnabled, ap.option_name as optionName, ap.id as optionids,ap.is_show as isShow from cms_activity_option ap ORDER BY ap.sort DESC,ap.createtime DESC ";
		}

		List<Map<String, Object>> activityOptionContentList = this.activityService
				.findForJdbc(sql);

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityOptionContentList", activityOptionContentList);

		return new ModelAndView("cms/activity/activityOptionRefrech", m);
	}

	/**
	 * 百度地图跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "baiduMap")
	public ModelAndView baiduMap(HttpServletRequest reqeust) {

		Map<String, Object> m = new HashMap<String, Object>();

		return new ModelAndView("cms/activity/baiduMap", m);
	}
}

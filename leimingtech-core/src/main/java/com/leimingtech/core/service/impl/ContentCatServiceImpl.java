package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentCatVOTreeEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.SectionServiceI;
import com.leimingtech.core.service.contentcatdefault.ContentCatDefaultServiceI;
import com.leimingtech.core.service.departchannel.TSDepartChannelServiceI;
import com.leimingtech.core.util.CMSUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.oConvertUtils;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 栏目管理接口实现
 * 
 * @author liuzhen 2014年8月8日 13:48:29
 * 
 */
@Service("contentCatService")
@Transactional
public class ContentCatServiceImpl implements ContentCatServiceI {

	@Autowired
	private ContentsServiceI contentsService;

	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;

	/** 区块管理 */
	@Autowired
	private SectionServiceI sectionService;
	/**会员默认栏目权限管理接口*/
	@Autowired
	private ContentCatDefaultServiceI contentCatDefaultService;
	/**部门下关联栏目接口*/
	@Autowired
	private TSDepartChannelServiceI tsDepartChannelService;

	/**
	 * 
	 * 保存栏目
	 * 
	 * @param contentCat
	 * @return
	 */
	public String save(ContentCatEntity contentCat) {
		contentCat.setCreated(new Date());// 创建时间
		return (String) commonService.save(contentCat);
	}

	/**
	 * 更新栏目
	 * 
	 * @param contentCat
	 */
	@Override
	public void saveOrUpdate(ContentCatEntity contentCat) {
		commonService.saveOrUpdate(contentCat);
	}

	/**
	 * 通过id获取栏目
	 * 
	 * @param id
	 *            栏目id
	 * @return
	 */
	@Override
	public ContentCatEntity getEntity(String id) {
		return commonService.getEntity(ContentCatEntity.class, id);
	}

	/**
	 * 获取分页后的栏目数据集
	 * 
	 * @param contentCat
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return contentCatList栏目数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(ContentCatEntity contentCat,
			Map param, int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class, pageSize,
				pageNo, "", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, contentCat, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ContentCatEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentCatList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除栏目
	 * 
	 * @param contentCat
	 */
	@Override
	public void delete(ContentCatEntity contentCat) {

		List<String> ids = new ArrayList<String>();
		getchildidList(ids, contentCat);

		String[] idArray = new String[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			idArray[i] = ids.get(i);
		}

		// 删除栏目级联删除栏目下所有内容
		deleteContentAll(idArray);
		// 删除栏目也将删除区块
		sectionService.deleteSectionByClass(idArray);
		//删除跟此栏目相关的会员默认栏目权限
		contentCatDefaultService.deleteByContentCats(idArray);
		//删除跟此栏目的部门关联
		tsDepartChannelService.deleteByContentCats(idArray);

		ContentCatEntity pcat = contentCat.getContentCat();
		if (pcat != null) {
			List<ContentCatEntity> contentCatList = getListByPid(pcat.getId());
			if (contentCatList == null || contentCatList.size() == 0) {
				pcat.setIscatend(1);// 修改节点状态 为根节点
				saveOrUpdate(pcat);
			} else if (contentCatList.size() == 1) {
				// 当只有一条时有可能是是当前自己，也需要修改节点状态
				if (contentCatList.get(0).getId().equals(contentCat.getId())) {
					pcat.setIscatend(1);// 修改节点状态 为根节点
					saveOrUpdate(pcat);
				}
			}
		}

		commonService.delete(contentCat);
	}

	private void getchildidList(List<String> idList, ContentCatEntity contentCat) {
		if (contentCat != null) {
			idList.add(contentCat.getId());
			List<ContentCatEntity> catalogList = getListByPid(contentCat
					.getId());
			for (ContentCatEntity catalog : catalogList) {
				this.getchildidList(idList, catalog);
			}
		}
	}

	/**
	 * 获取当前站点中所有开启的栏目
	 * 
	 * @param iscatend
	 *            是否是只获取末级开启的栏目
	 * @return
	 */
	@Override
	public List<String> getAllOpenContentCatIdList(SiteEntity site,
			boolean iscatend) {
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);

		DetachedCriteria dc = cq.getDetachedCriteria();
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("id").as("id"));
		dc.setProjection(pList);

		cq.setDetachedCriteria(dc);

		cq.eq("siteid", site.getId());
		if (iscatend) {
			cq.eq("iscatend", 1);
		}
		cq.eq("isshow", "1");
		cq.eq("disabled", 0);
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<String> catalogList = commonService.getListByCriteriaQuery(cq,
				false);

		return catalogList;
	}

	/**
	 * 获取当前站点中所有开启的栏目
	 * 
	 * @param iscatend
	 *            是否是只获取末级开启的栏目
	 * @return
	 */
	@Override
	public String[] getAllOpenContentCatIdArray(SiteEntity site,
			boolean iscatend) {
		List<String> catalogidList = getAllOpenContentCatIdList(site, iscatend);
		if (catalogidList != null && catalogidList.size() > 0) {
			String[] ids = new String[catalogidList.size()];
			catalogidList.toArray(ids);
			return ids;
		}
		return new String[] {};
	}

	/**
	 * 栏目的jsonarray数据，供树形结构使用
	 * 
	 * @param models
	 *            栏目可提供的模型，如文章，组图。传入值例：{文章，组图}
	 * @param isleaf
	 *            是否为末级节点 0:不是末级节点 1:末级节点
	 * @param catids
	 *            加载栏目树形结构默认选中的栏目的id 例：1,2,3,4
	 * @return
	 */
	public JSONArray getContentCatForJsonArray(String[] models, Integer isleaf,
			String catids, String iscontribute) {
		JSONArray jsonArray = new JSONArray();
		int modelslength = models.length;
		// 频道tree
		List<ContentCatEntity> contentCatList = commonService.findByProperty(
				ContentCatEntity.class, "iscatend", isleaf);
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		cq.eq("iscatend", isleaf);
		cq.eq("iscontribute", 1);
		if (StringUtils.isNotEmpty(iscontribute)) {
			cq.eq("iscontribute", Integer.parseInt(iscontribute));
		}
		cq.add();
		contentCatList = commonService.getListByCriteriaQuery(cq, false);
		for (ContentCatEntity contentCat : contentCatList) {
			JSONObject json = new JSONObject();
			String catid = contentCat.getId() + "";
			json.put("id", contentCat.getId());
			json.put("name", contentCat.getName());
			json.put("open", false);
			if (contentCat.getContentCats().size() != 0) {
				json.put("nocheck", true);
			}
			if (StringUtils.isNotEmpty(catids)) {
				List<String> catidlist = Arrays.asList(catids);
				if (catidlist.contains(catid)) { // 用于修改时判断节点是否选中
					json.put("checked", true);
				}
			}

			if (1 == isleaf) { // 末级节点
				String templateindex = contentCat.getTemplateIndex();
				if (modelslength > 0) {
					for (int i = 0; i < models.length; i++) {
						String model = models[i];
						JSONObject jsonobject = JSONObject
								.fromObject(templateindex);
						String selemodel = jsonobject.get(model) + "";
						if (StringUtils.isNotEmpty(selemodel)
								&& "true".equals(selemodel)) {
							jsonArray.add(json);
						}
					}
				} else {
					jsonArray.add(json);
				}
			} else if (0 == isleaf) { // 非末级节点
				// 父节点
				if (contentCat.getLevels() == 0) {
					json.put("children", getChildren(contentCat));
					jsonArray.add(json);
				}
			}
		}
		return jsonArray;
	}

	/**
	 * 子节点
	 * 
	 * @param Channel
	 * @return
	 */
	private JSONArray getChildren(ContentCatEntity contentCat) {
		JSONArray jsonArray = new JSONArray();
		if (contentCat == null) {
			return jsonArray;
		}

		List<ContentCatEntity> contentCatList = getListByPid(contentCat.getId());

		if (contentCatList == null || contentCatList.size() == 0) {
			return jsonArray;
		}

		for (ContentCatEntity child : contentCatList) {
			JSONObject json = new JSONObject();
			json.put("id", child.getId());
			json.put("name", child.getName());
			json.put("open", false);
			if (child.getContentCats().size() != 0) {
				json.put("nocheck", true);
			}
			json.put("children", getChildren(child));
			jsonArray.add(json);
		}
		return jsonArray;
	}

	@Override
	public void deleteContentAll(String[] ids) {
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class);
		cq.in("catid", ids);
		cq.add();
		List<ContentsEntity> contentsList = commonService
				.getListByCriteriaQuery(cq, false);
		String conid = "";
		for (ContentsEntity contents : contentsList) {
			conid += contents.getId() + ",";
		}
		String[] conids = conid.split(",");
		// 删除内容以及下属分类
		if (StringUtil.isNotEmpty(conids[0])) {
			contentsService.delContent(conids);
		}
	}

	/**
	 * 获取第一级栏目(根据当前站点)
	 * 
	 * @return 栏目集合
	 */
	@Override
	public List<ContentCatEntity> getRootContentCat() {
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		cq.eq("levels", 0);
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<ContentCatEntity> list = commonService.getListByCriteriaQuery(cq,
				false);
		return list;
	}

	/**
	 * 通过栏目父id获取栏目集合
	 * 
	 * @param id
	 *            栏目id
	 * @return 栏目集合
	 */
	@Override
	public List<ContentCatEntity> getListByPid(String id) {
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		cq.eq("contentCat.id", id);
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<ContentCatEntity> list = commonService.getListByCriteriaQuery(cq,
				false);
		return list;
	}

	/**
	 * 获取开启的一级栏目
	 * 
	 * @param siteid
	 *            站点id
	 * @return 栏目集合
	 */
	@Override
	public List<ContentCatEntity> getOpenRootContentCat(String siteid) {
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		cq.eq("levels", 0);
		cq.eq("siteid", siteid);
		cq.eq("isshow", "1");
		cq.eq("disabled", 0);
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<ContentCatEntity> list = commonService.getListByCriteriaQuery(cq,
				false);
		return list;
	}

	/**
	 * 递归栏目子节点数据
	 * @param jArray
	 * @param list
	 * @param openNodes
	 * @param selectNode
	 * @param pId
	 */
	private void setChildInfo(JSONArray jArray,
								   List<ContentCatVOTreeEntity> list, Set<String> openNodes, String selectNode, String pId) {

		for (ContentCatVOTreeEntity cat : list) {

			if (null == pId && cat.getLevels() != 0) {
				continue;
			}

			if (null != pId && !pId.equals(cat.getParentid())) {
				continue;
			}

			JSONObject jChildObject = new JSONObject();

			jChildObject.accumulate("id", cat.getId());
			jChildObject.accumulate("text", cat.getName());

			JSONObject stateJson = new JSONObject();
			if (StringUtils.isNotEmpty(selectNode)
					&& selectNode.equals(cat.getId() + "")) {
				stateJson.accumulate("selected", true);
			}

			if (cat.getIscatend() == 0) {
				if (openNodes.contains(cat.getId())) {
					stateJson.accumulate("opened", true);
				}
				jChildObject.accumulate("state", stateJson);

				JSONArray jChildArray = new JSONArray();
				setChildInfo(jChildArray, list, openNodes, selectNode, cat.getId());
				jChildObject.accumulate("children", jChildArray);
			} else {
				jChildObject.accumulate("state", stateJson);
			}

			jArray.add(jChildObject);
		}
	}

	@Override
	public JSONArray getTreeJson(ContentCatEntity contentCat, String siteId) {
		JSONArray json = new JSONArray();

		JSONObject jstreeData = new JSONObject();
		jstreeData.accumulate("id", "-1");
		jstreeData.accumulate("text", "栏目");
		JSONObject stateJson = new JSONObject();
		stateJson.accumulate("opened", true);

		Set<String> openIds=new HashSet<>();
		if (StringUtils.isEmpty(contentCat.getId())) {
			stateJson.accumulate("selected", true);
			String pathIds=contentCat.getPathids();
			if(StringUtils.isNotBlank(pathIds)){
				CollectionUtils.addAll(openIds,pathIds.split(","));
			}
		}
		jstreeData.accumulate("state", stateJson);


		List<ContentCatVOTreeEntity> contentCatList=this.getSimpleAllBySite(siteId);//获取站点下所有栏目数据，返回字段比较简洁，针对ztree使用
		if (contentCatList != null && contentCatList.size() > 0) {
			JSONArray jChildArray = new JSONArray();
			setChildInfo(jChildArray, contentCatList, openIds, contentCat.getId(),null);
			jstreeData.accumulate("children", jChildArray);
		}
		json.add(jstreeData);
		return json;
	}

	@Override
	public List<ContentCatEntity> findByQueryString(String hql) {
		return commonService.findByQueryString(hql);
	}

	/**
	 * 根据站点获取一级栏目列表
	 * 
	 * @param siteid
	 * @return
	 */
	@Override
	public List<ContentCatEntity> getRootContentCatBySite(String siteid) {
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		cq.eq("siteid", siteid);
		cq.eq("levels", 0);
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<ContentCatEntity> list = commonService.getListByCriteriaQuery(cq,
				false);
		return list;
	}

	@Override
	public List<ContentCatEntity> getAllIndexContentCat() {
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		cq.isNotNull("contentCat");
		cq.isNotNull("indexTemplate");
		cq.eq("levels", 0);
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<ContentCatEntity> list = commonService.getListByCriteriaQuery(cq,
				false);
		//filterContentCat(list);// 过滤掉不显示的栏目
		return list;
	}

	/**
	 * 过滤掉不显示的栏目
	 * 
	 * @param list
	 */
	public void filterContentCat(List<ContentCatEntity> list) {
		if (list != null && list.size() > 0) {
			for (int i = list.size() - 1; i >= 0; i--) {
				ContentCatEntity contentCat = list.get(i);
				if (StringUtil.isEmpty(contentCat.getIndexTemplate())
						&& contentCat.getContentCat() != null) {
					list.remove(i);
				} else {
					List<ContentCatEntity> childList = list.get(i)
							.getContentCats();
					filterContentCat(childList);
				}
			}
		}
	}

	/**
	 * 获取当前站点中所有栏目数据，treedata
	 *
	 * @return
	 */
	@Override
	public JSONArray getContentCatTreeData() {
		String siteid = PlatFormUtil.getSessionSiteId();
		if (StringUtils.isBlank(siteid)) {
			return new JSONArray();
		}

		return getContentCatTreeData(siteid);
	}

	/**
	 * 获取指定站点中所有栏目数据，treedata
	 * 
	 * @param siteId
	 * @return
	 */
	@Override
	public JSONArray getContentCatTreeData(String siteId) {

		if (StringUtils.isBlank(siteId)) {
			return getContentCatTreeData();
		}

		JSONArray jsonArray = new JSONArray();
		List<ContentCatEntity> contentCatList = getRootContentCatBySite(siteId);

		for (ContentCatEntity child : contentCatList) {
			JSONObject json = new JSONObject();
			json.put("id", child.getId());
			json.put("name", child.getName());
			json.put("open", true);
			json.put("children", getAllOpenChildTreeData(child,siteId));
			jsonArray.add(json);
		}
		return jsonArray;
	}


	/**
	 * 获取站点下ztree需要的栏目数据，json中包含id/pId/name/open等节点
	 *
	 * @param siteId     站点
	 * @param selectCats 需要选中的栏目
	 * @return
	 */
	@Override
	public JSONArray getzTreeData(String siteId, Set<String> selectCats) {
		JSONArray jArray = new JSONArray();
		List<Map<String,Object>> list = getContentCatVOZtreeList(siteId);
		if (list == null || list.size() == 0) {
			return jArray;
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> contentCat = list.get(i);
			JSONObject j = new JSONObject();
			j.accumulate("id", contentCat.get("id"));
			j.accumulate("pId", contentCat.get("parentid"));
			j.accumulate("name", contentCat.get("name"));
			if (0 == oConvertUtils.getInt(contentCat.get("iscatend"),0)) {
				j.accumulate("open", true);
			}
			if(selectCats!=null && selectCats.contains(oConvertUtils.getString(contentCat.get("id"),""))){
				j.accumulate("checked",true);
			}
			jArray.add(j);
		}
		return jArray;
	}

	/**
	 * 获取站点下所有栏目数据，返回字段比较简洁，针对ztree使用
	 *
	 * @param siteid 站点id
	 * @return
	 */
	@Override
	public List<ContentCatVOTreeEntity> getSimpleAllBySite(String siteid) {
			CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);

		//指定要返回的列
		DetachedCriteria dc =cq.getDetachedCriteria();
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("id").as("id"));
		pList.add(Projections.property("contentCat.id").as("parentid"));
		pList.add(Projections.property("name").as("name"));
		pList.add(Projections.property("levels").as("levels"));
		pList.add(Projections.property("iscatend").as("iscatend"));
		dc.setProjection(pList);
		dc.setResultTransformer(Transformers.aliasToBean(ContentCatVOTreeEntity.class));

		cq.eq("siteid", siteid);
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		List<ContentCatVOTreeEntity> resultList = commonService.getListByCriteriaQuery(cq, false);
		return resultList;
	}

	/**
	 * 过滤栏目权限
	 *
	 * @param list
	 */
	@Override
	public void filterPCCatalogPermission(List<ContentCatVOTreeEntity> list) {
		if (list != null && list.size() > 0) {
			Set<String> pcCatalog = CMSUtil.initPCCatalogPermission();
			if (pcCatalog == null || pcCatalog.size() == 0) {
				list.clear();
				return;
			}
			for (int i = list.size() - 1; i >= 0; i--) {
				if (!pcCatalog.contains(list.get(i).getId().toString())) {
					list.remove(i);
				}
			}
		}
	}

	/**
	 * 获取指定站点下所有栏目id集合
	 *
	 * @param siteId
	 * @return
	 */
	@Override
	public List<String> getAllIdList(String siteId) {
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);

		DetachedCriteria dc = cq.getDetachedCriteria();
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("id").as("id"));
		dc.setProjection(pList);

		cq.setDetachedCriteria(dc);

		if(StringUtils.isNotBlank(siteId)){
			cq.eq("siteid", siteId);
		}
		cq.add();
		List<String> catalogList = commonService.getListByCriteriaQuery(cq,
				false);
		return catalogList;
	}

	/**
	 * 根据指定栏目查询出所有子集启用的栏目id集合
	 *
	 * @param catalogPathIds 栏目id集，包含所有当前栏目的父级id和当前栏目id用逗号分隔
	 * @return
	 */
	@Override
	public List<String> getOpenChildIdList(String catalogPathIds) {
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);

		DetachedCriteria dc = cq.getDetachedCriteria();
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("id").as("id"));
		dc.setProjection(pList);

		cq.setDetachedCriteria(dc);

		cq.like("pathids",catalogPathIds, MatchMode.START);
		cq.eq("isshow", "1");
		cq.eq("disabled", 0);
		cq.add();
		List<String> catalogList = this.commonService.getListByCriteriaQuery(cq,
				false);
		return catalogList;
	}

	private List<Map<String, Object>> getContentCatVOZtreeList(String siteId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select id,parentid,name,iscatend ");
		sql.append(" from cms_content_cat ");
		sql.append(" where site_id= ? ");
		sql.append(" order by sort desc,created desc ");

		List<Map<String, Object>> list = this.commonService.findForJdbc(sql.toString(), siteId);
		return list;
	}

	/**
	 * 子节点
	 *
	 * @param contentCat
	 * @param siteId
	 * @return
	 */
	private JSONArray getAllOpenChildTreeData(ContentCatEntity contentCat, String siteId) {
		JSONArray jsonArray = new JSONArray();
		if (contentCat == null) {
			return jsonArray;
		}

		List<ContentCatEntity> contentCatList = contentCat.getContentCats();

		if (contentCatList == null || contentCatList.size() == 0) {
			return jsonArray;
		}

		for (ContentCatEntity child : contentCatList) {
			JSONObject json = new JSONObject();
			json.put("id", child.getId());
			json.put("name", child.getName());
			json.put("open", true);
			json.put("children", getAllOpenChildTreeData(child,siteId));
			jsonArray.add(json);
		}
		return jsonArray;
	}

	/**
	 * 获取站点下栏目ztree节点，可以提供选中项
	 *
	 * @param set    选中的栏目节点id
	 * @param siteid 站点
	 * @return 栏目ztree节点数据
	 */
	@Override
	public JSONArray contentCatTree(Set<String> set, String siteid) {
		List<ContentCatVOTreeEntity> contentCatList = this.getSimpleAllBySite(siteid);//获取站点下所有栏目数据，返回字段比较简洁，针对ztree使用

		JSONArray jsonArray = new JSONArray();
		for (ContentCatVOTreeEntity contentCat : contentCatList) {
			JSONObject json = new JSONObject();
			json.put("id", contentCat.getId());
			json.put("name", contentCat.getName());
			json.put("open", true);

			if (StringUtils.isBlank(contentCat.getParentid())) {
				json.put("pId", siteid);
			} else {
				json.put("pId", contentCat.getParentid());
			}

			if (set.contains(contentCat.getId())) {
				json.put("checked", true);
			} else {
				json.put("checked", false);
			}
			jsonArray.add(json);
		}

		return jsonArray;
	}
}
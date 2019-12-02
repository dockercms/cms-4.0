package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.util.PlatFormUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 站点管理接口实现
 * 
 * @author liuzhen 2014年6月16日 11:38:43
 * 
 */
@Service("siteService")
@Transactional
public class SiteServiceImpl implements SiteServiceI {

	/** 公共dao接口 */
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;
	/**
	 * 验证站点路径是否存在
	 * 
	 * @param path
	 * @return true存在 false不存在
	 */
	@Override
	public Boolean checkPath(String path) {

		List<SiteEntity> siteList = commonService.findByProperty(
				SiteEntity.class, "sitePath", path);
		if (siteList != null && siteList.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取站点集合
	 * 
	 * @return
	 */
	public List<SiteEntity> siteList() {

		CriteriaQuery cq = new CriteriaQuery(SiteEntity.class);
		cq.addOrder("isMaster",SortDirection.desc);
		cq.addOrder("createdtime", SortDirection.asc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}

	/**
	 * 根据id获取站点
	 * 
	 * @param siteId
	 * @return
	 */
	public SiteEntity getSite(String siteId) {
		SiteEntity site = commonService.getEntity(SiteEntity.class, siteId);
		return site;
	}

	/**
	 * 保存站点
	 * 
	 * @param site
	 * @return
	 */
	@Override
	public java.lang.String save(SiteEntity site) {
		site.setCreatedtime(new Date());// 创建时间
		return (java.lang.String) commonService.save(site);
	}

	/**
	 * 更新站点
	 * 
	 * @param site
	 */
	@Override
	public void saveOrUpdate(SiteEntity site) {
		commonService.saveOrUpdate(site);
	}

	/**
	 * 获取分页后的站点数据集
	 *
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return siteList栏目数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(SiteEntity site, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(SiteEntity.class, pageSize,
				pageNo, "", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, site, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<SiteEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pageList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除站点
	 * 
	 * @param site
	 */
	@Override
	public void delete(SiteEntity site) {
		// FIXME liuzhen 2015年6月11日 11:07:35 以后将删除站点下所有关联数据 未完善
		commonService.delete(site);
	}

	/**
	 * 站点ucenter是否开启
	 * 
	 * @return
	 */
	@Override
	public boolean ucenterIsOpen() {
		SiteEntity site = PlatFormUtil.getSessionSite();
		if (null == site || site.getUcenterisOpen() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * 获取默认站点
	 */
	@Override
	public SiteEntity getDefaultMainSite() {
		//获取自己权限的站点
		List<SiteEntity> sites = userService.getRoleSite();
		if (sites != null && sites.size() > 0) {
			return (SiteEntity) sites.get(0);
		}
		
		return getMasterSite();
	}

	/**
	 * 修改其他站点为子站
	 */
	@Override
	public void modifyMainSite() {
		commonService.updateBySqlString("update cms_site set is_master=0");
	}

	/**
	 * 获取站点tree数据
	 * 
	 * @param siteids
	 *            需要选中的站点
	 * @return
	 */
	@Override
	public JSONArray getSiteTreeData(Set<String> siteids) {
		JSONArray sitesJsonArray = new JSONArray();

		List<SiteEntity> siteList = siteList();

		for (SiteEntity site : siteList) {
			JSONObject sitejsonObject = new JSONObject();
			sitejsonObject.put("id", site.getId());
			sitejsonObject.put("name", site.getSiteName());
			sitejsonObject.put("open", true);
			if (siteids.contains(String.valueOf(site.getId()))) {
				sitejsonObject.put("checked", true);
			} else {
				sitejsonObject.put("checked", false);
			}
			sitesJsonArray.add(sitejsonObject);
		}
		return sitesJsonArray;
	}
	
	/**
	 * 获取主站点
	 * @return
	 */
	@Override
	public SiteEntity getMasterSite(){
		return commonService.findUniqueByProperty(SiteEntity.class, "isMaster", 1);
	}


}
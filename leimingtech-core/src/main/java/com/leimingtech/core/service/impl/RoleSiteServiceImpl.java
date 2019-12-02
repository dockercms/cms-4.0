package com.leimingtech.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.comparator.SiteComparator;
import com.leimingtech.core.entity.RoleSiteEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.RoleServiceI;
import com.leimingtech.core.service.RoleSiteServiceI;
import com.leimingtech.core.service.SiteServiceI;







/**
 * @Title: interface
 * @Description: 站点权限接口实现
 * @author
 * @date 2015-10-20 11:16:58
 * @version V1.0
 * 
 */
@Service("roleSiteService")
@Transactional
public class RoleSiteServiceImpl implements RoleSiteServiceI {

	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	/** 权限管理接口 */
	@Autowired
	private RoleServiceI roleService;
	/** 站点管理接口 */
	@Autowired
	private SiteServiceI siteService;

	/**
	 * 保存站点权限
	 * 
	 * @param roleSite
	 * @return
	 */
	public java.lang.String save(RoleSiteEntity roleSite) {
		return (java.lang.String) commonService.save(roleSite);
	}

	/**
	 * 更新站点权限
	 * 
	 * @param roleSite
	 */
	@Override
	public void saveOrUpdate(RoleSiteEntity roleSite) {
		commonService.saveOrUpdate(roleSite);
	}

	/**
	 * 通过id获取站点权限
	 * 
	 * @param id
	 *            站点权限id
	 * @return
	 */
	@Override
	public RoleSiteEntity getEntity(java.lang.String id) {
		return commonService.getEntity(RoleSiteEntity.class, id);
	}

	/**
	 * 获取分页后的站点权限数据集
	 * 
	 * @param roleSite
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return roleSiteList站点权限数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(RoleSiteEntity roleSite, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(RoleSiteEntity.class, pageSize,
				pageNo, "", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, roleSite, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<RoleSiteEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("roleSiteList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除站点权限
	 * 
	 * @param roleSite
	 */
	@Override
	public void delete(RoleSiteEntity roleSite) {
		commonService.delete(roleSite);
	}

	/**
	 * 通过角色查出站点id集合
	 * 
	 * @param roleId
	 * @return
	 */
	@Override
	public Set<String> findSiteSetByRole(String roleId) {
		List<RoleSiteEntity> roleSiteList = findSiteByRole(roleId);

		Set<String> set = new HashSet<String>();
		for (RoleSiteEntity roleSite : roleSiteList) {
			if (null != roleSite.getSite()) {
				set.add(roleSite.getSite().getId());
			}
		}
		return set;
	}

	/**
	 * 通过角色查找站点权限
	 * 
	 * @param roleId
	 * @return
	 */
	@Override
	public List<RoleSiteEntity> findSiteByRole(String roleId) {
		return commonService.findByProperty(RoleSiteEntity.class, "role.id",
				roleId);
	}

	/**
	 * 保存站点权限
	 * 
	 * @param roleId
	 *            角色id
	 * @param funVal
	 *            选中的站点
	 */
	@Override
	public void saveSiteRole(String roleId, String funVal) {
		String[] funValArray = funVal.split(",");

		Set<String> newVal = new HashSet<String>();
		CollectionUtils.addAll(newVal, funValArray);

		TSRole role = roleService.getEntity(roleId);
		List<RoleSiteEntity> roleSietList = findSiteByRole(roleId);
		Set<String> flag = new HashSet<String>();
		// 当前角色没有站点权限
		if (roleSietList.size() == 0) {
			for (int i = 0; i < funValArray.length; i++) {
				String funValId = funValArray[i];
				if (StringUtils.isBlank(funValId)) {
					continue;
				}
				RoleSiteEntity roleSite = new RoleSiteEntity();

				roleSite.setRole(role);// 添加角色Id
				SiteEntity site = null;
				if (StringUtils.isNotEmpty(funValId)) {
					site = siteService.getSite(funValId);
				}
				roleSite.setSite(site);// 添加站点Id
				roleSite.setCreatedTime(new Date());
				saveOrUpdate(roleSite);
			}
		} else {
			for (int k = 0; k < roleSietList.size(); k++) {
				RoleSiteEntity roleSite = roleSietList.get(k);
				String siteid = roleSite.getSite().getId();
				flag.add(siteid);
				// 去掉未勾选的项
				if (!newVal.contains(siteid)) {
					delete(roleSite);
				}
			}
			for (int i = 0; i < funValArray.length; i++) {
				String funValId = funValArray[i];
				if (StringUtils.isBlank(funValId)) {
					continue;
				}
				if (!flag.contains(funValId)) {
					RoleSiteEntity roleSite = new RoleSiteEntity();
					roleSite.setRole(role);// 添加角色Id
					SiteEntity site = null;
					if (StringUtils.isNotEmpty(funValId)) {
						site = siteService.getSite(funValId);
					}
					roleSite.setSite(site);// 添加站点Id
					roleSite.setCreatedTime(new Date());
					saveOrUpdate(roleSite);
				}
			}
		}

	}

	/**
	 * 通过多个角色获取站点列表
	 * 
	 * @param roles
	 *            多个角色id
	 * @return
	 */
	@SuppressWarnings("null")
	@Override
	public List<SiteEntity> findSiteByRole(String[] roles) {
		CriteriaQuery cq = new CriteriaQuery(RoleSiteEntity.class);
		//FIXME 联查
		cq.in("role.id", roles);
		cq.addOrder("site.isMaster", SortDirection.desc);
		cq.addOrder("site.createdtime", SortDirection.asc);
		cq.add();
		List<RoleSiteEntity> roleSiteList = commonService
				.getListByCriteriaQuery(cq, false);

		if (roleSiteList == null || roleSiteList.size() == 0) {
			return null;
		}
		
		Set set = new HashSet();
		for (int i = 0; i < roleSiteList.size(); i++) {
			
			set.add(String.valueOf(roleSiteList.get(i).getSite().getId()));
			
		}
		CriteriaQuery cq1 = new CriteriaQuery(SiteEntity.class);
		String siteId[]= (String[]) set.toArray(new String[set.size()]);
		
		cq1.in("id", siteId);
		cq1.addOrder("isMaster", SortDirection.desc);
		cq1.addOrder("createdtime", SortDirection.desc);
		cq1.add();
		List<SiteEntity> siteList = commonService
				.getListByCriteriaQuery(cq1, false);
		return siteList;
		 
	}

	@Override
	public List<RoleSiteEntity> getRoleEntity(String id) {
		List<RoleSiteEntity> roleSite = commonService.findByProperty(RoleSiteEntity.class,"site.id",id);
		return roleSite;
	}

}
package com.leimingtech.core.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.WapconfigEntity;
import com.leimingtech.core.entity.WapexpandEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.WapconfigServiceI;

/**
 * wap配置接口实现
 * 
 * @author liuzhen 2014年7月23日 17:45:46
 * 
 */
@Service("wapconfigService")
@Transactional
public class WapconfigServiceImpl extends CommonServiceImpl implements WapconfigServiceI {

	/**
	 * 获取wap配置
	 * 
	 * @return如果站点wap未开启将返回null
	 */
	@Override
	public WapconfigEntity getWapConfig(SiteEntity site) {
		WapconfigEntity wap = getCurrentSiteWapConfig(site);
		if (wap != null && "1".equals(wap.getWapstatus())) {
			return wap;
		}
		return null;
	}

	/**
	 * 获取开启的模型
	 * 
	 * @return
	 */
	@Override
	public Set<String> getOpenModel(SiteEntity site) {
		WapconfigEntity wap =getWapConfig(site);
		if (wap != null) {
			WapexpandEntity wapexpand = getWapexpand(wap.getId());
			if (StringUtils.isNotEmpty(wapexpand.getModelids())) {
				String[] modelids = StringUtils.split(wapexpand.getModelids(), ",");
				if (modelids != null && modelids.length > 0) {
					Set<String> set = new HashSet<String>();
					for (int i = 0; i < modelids.length; i++) {
						set.add(modelids[i]);
					}
					return set;
				}
			}
		}
		return new HashSet<String>();
	}

	/**
	 * 获取开启的栏目
	 * 
	 * @return
	 */
	@Override
	public Set<String> getOpenCatalog(SiteEntity site) {
		WapconfigEntity wap =getWapConfig(site);
		if (wap != null) {
			WapexpandEntity wapexpand = getWapexpand(String.valueOf(wap.getId()));
			if (StringUtils.isNotEmpty(wapexpand.getCatalogids())) {
				String[] catalogids = StringUtils.split(wapexpand.getCatalogids(), ",");
				
				if (catalogids != null && catalogids.length > 0) {
					Set<String> set = new HashSet<String>();
					for (int i = 0; i < catalogids.length; i++) {
						set.add(String.valueOf(catalogids[i]));
					}
					
					String[] idarray = new String[set.size()];
					idarray = set.toArray(idarray);
					CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
					cq.in("id", idarray);
					cq.eq("siteid", site.getId());
					cq.eq("iscatend", 1);
					cq.eq("isshow", "1");
					cq.eq("disabled", 0);
					cq.addOrder("sort", SortDirection.desc);
					cq.add();
					List<ContentCatEntity> catalogList = getListByCriteriaQuery(cq, false);
					if (catalogList != null && catalogList.size() > 0) {
						Set<String> finalSet = new HashSet<String>();
						for (int i = 0; i < catalogList.size(); i++) {
							if(set.contains(catalogList.get(i).getId())){
								finalSet.add(catalogList.get(i).getId());
							}
						}
						set = finalSet;
						finalSet = null;
					}
					return set;
				}
			}
		}
		return new HashSet<String>();
	}

	/**
	 * 通过当前session中站点获取关联的wap配置信息
	 * 
	 * @return
	 */
	@Override
	public WapconfigEntity getCurrentSiteWapConfig(SiteEntity site) {
		return findUniqueByProperty(WapconfigEntity.class, "siteid", site.getId());
	}

	/**
	 * 获取wap配置拓展
	 * 
	 * @param configid
	 * @return
	 */
	@Override
	public WapexpandEntity getWapexpand(String configid) {
		if (StringUtils.isEmpty(configid))
			return null;

		return findUniqueByProperty(WapexpandEntity.class, "configid", configid);
	}

}
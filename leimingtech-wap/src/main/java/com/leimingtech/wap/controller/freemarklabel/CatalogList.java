package com.leimingtech.wap.controller.freemarklabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.BaseFreeMarkerTag;
import com.leimingtech.core.common.WapManager;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.util.PlatFormUtil;

import freemarker.template.TemplateModelException;

@Component
public class CatalogList extends BaseFreeMarkerTag {
	@Autowired
	private ContentsServiceI contentsService;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		SiteEntity site = PlatFormUtil.getSessionSite();//当前站点
		WapManager wapmanager = new WapManager(site);
		 
		String siteid = site.getId();
		Set<String> catalogsSet = wapmanager.getCatalogs();
		String[] catalogids = new String[catalogsSet.size()];
		catalogids = catalogsSet.toArray(catalogids);

		List<ContentCatEntity> allOpenCatalog = new ArrayList<ContentCatEntity>();

		if (catalogids != null && catalogids.length > 0) {
			// 查出开启的栏目
			CriteriaQuery cqCatalog = new CriteriaQuery(ContentCatEntity.class);
			cqCatalog.eq("siteid", siteid);
			cqCatalog.eq("iscatend", 1);
			cqCatalog.eq("isshow", "1");
			cqCatalog.in("id", catalogids);
			cqCatalog.eq("disabled", 0);
			cqCatalog.addOrder("sort", SortDirection.desc);
			cqCatalog.add();
			allOpenCatalog = contentsService.getListByCriteriaQuery(cqCatalog, false);
		}
		 
		return allOpenCatalog;
	}

}

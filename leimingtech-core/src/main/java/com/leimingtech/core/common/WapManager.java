package com.leimingtech.core.common;

import java.util.Set;

import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.WapconfigEntity;
import com.leimingtech.core.service.WapconfigServiceI;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * wap生成管理
 * 
 * @author liuzhen 2014年7月23日 18:29:44
 * 
 */
public class WapManager {
	private Boolean wapIsOpen = false;
	private WapconfigEntity wapconfig;// wap配置信息
	private Set<String> models; // 开启的模型
	private Set<String> catalogs; // 开启的栏目

	private WapconfigServiceI wapconfigService;
	
	public WapManager(SiteEntity site) {
		wapconfigService = (WapconfigServiceI) PlatFormUtil.getInterface("wapconfigService");
		if (wapconfigService != null) {
			wapconfig = wapconfigService.getWapConfig(site);
			if (wapconfig != null) {
				models = wapconfigService.getOpenModel(site);
				catalogs = wapconfigService.getOpenCatalog(site);
				if (models == null || models.size() == 0 || catalogs == null || catalogs.size() == 0) {
					wapIsOpen = false;
				} else {
					wapIsOpen = true;
				}
			}

		}
	}

	public Boolean getWapIsOpen() {
		return wapIsOpen;
	}

	public void setWapIsOpen(Boolean wapIsOpen) {
		this.wapIsOpen = wapIsOpen;
	}

	public Set<String> getModels() {
		return models;
	}

	public void setModels(Set<String> models) {
		this.models = models;
	}

	public Set<String> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(Set<String> catalogs) {
		this.catalogs = catalogs;
	}

	public WapconfigServiceI getWapconfigService() {
		return wapconfigService;
	}

	public void setWapconfigService(WapconfigServiceI wapconfigService) {
		this.wapconfigService = wapconfigService;
	}

	public WapconfigEntity getWapconfig() {
		return wapconfig;
	}

	public void setWapconfig(WapconfigEntity wapconfig) {
		this.wapconfig = wapconfig;
	}

}

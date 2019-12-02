package com.leimingtech.core.service;

import java.util.Set;

import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.WapconfigEntity;
import com.leimingtech.core.entity.WapexpandEntity;

/**
 * wap配置信息管理接口
 * 
 * @author liuzhen 2014年7月17日 15:43:49
 * 
 */
public interface WapconfigServiceI extends CommonService {

	/**
	 * 获取wap配置拓展
	 * 
	 * @param configid
	 * @return
	 */
	public WapexpandEntity getWapexpand(String configid);

	/**
	 * 通过当前session中站点获取关联的wap配置信息
	 * 
	 * @return
	 */
	public WapconfigEntity getCurrentSiteWapConfig(SiteEntity site);


	/**
	 * 判获取wap配置 
	 * 
	 * @return如果站点wap未开启将返回null
	 */
	public WapconfigEntity getWapConfig(SiteEntity site);

	/**
	 * 获取开启的模型
	 * 
	 * @return
	 */
	public Set<String> getOpenModel(SiteEntity site);

	/**
	 * 获取开启的栏目
	 * 
	 * @return
	 */
	public Set<String> getOpenCatalog(SiteEntity site);
}

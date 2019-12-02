package com.leimingtech.cms.service.catalog;

import java.util.List;

import com.leimingtech.core.entity.ContentCatEntity;

/**
 * 栏目管理接口
 * 
 * @author liuzhen 2014年5月4日 09:51:33
 * 
 */
public interface CatalogServiceI {
	/**
	 * 获取栏目路径
	 * 
	 * @param catalogid
	 *            栏目id
	 * @return
	 */
	public String getCatalogPath(Integer catalogid);

	/**
	 * 获取栏目全路径路径 如：
	 * 
	 * @param catalogid
	 * @return
	 */
	public String getCatalogFullPath(Integer catalogid);
	
	/**
	 * 获取当前站点的栏目列表
	 * @return
	 */
	public List<ContentCatEntity> catalogList(String siteid); 
}

package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import com.leimingtech.core.entity.SiteEntity;


/**
 * 站点管理接口
 * 
 * @author liuzhen 2014年6月16日 11:30:48
 * 
 */
public interface SiteServiceI {

	/**
	 * 验证站点路径是否存在
	 * 
	 * @param path
	 * @return true存在 false不存在
	 */
	Boolean checkPath(String path);

	/**
	 * 获取站点集合
	 * 
	 * @return
	 */
	List<SiteEntity> siteList();

	/**
	 * 根据id获取站点
	 * 
	 * @param siteId
	 * @return
	 */
	SiteEntity getSite(String siteId);

	/**
	 * 删除站点
	 * 
	 * @param site
	 */
	void delete(SiteEntity site);

	/**
	 * 获取分页后的站点数据集
	 * 
	 * @param contentCat
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return siteList栏目数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(SiteEntity site, Map param, int pageSize,
			int pageNo);

	/**
	 * 更新站点
	 * 
	 * @param site
	 */
	void saveOrUpdate(SiteEntity site);

	/**
	 * 保存站点
	 * 
	 * @param site
	 * @return
	 */
	String save(SiteEntity site);

	/**
	 * 站点ucenter是否开启
	 * 
	 * @return
	 */
	boolean ucenterIsOpen();

	/**
	 * 获取默认站点
	 * 
	 * @return
	 */
	SiteEntity getDefaultMainSite();

	/**
	 * 修改其他站点为子站
	 */
	void modifyMainSite();

	/**
	 * 获取站点tree数据
	 * 
	 * @param siteids
	 *            需要选中的站点
	 * @return
	 */
	JSONArray getSiteTreeData(Set<String> siteids);
	
	/**
	 * 获取主站点
	 * @return
	 */
	public SiteEntity getMasterSite();

}

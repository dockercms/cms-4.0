package com.leimingtech.core.service;

import java.util.List;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;

/**
 * wap生成静态页接口（所有生成操作都在线程中）
 * 
 * @author liuzhen 2014年7月23日 19:42:02
 * 
 */
public interface StaticWapHtmlServiceI {

	/**
	 * 生成wap全站 以及wap首页、栏目列表页、文章详细页、 默认生成栏目前20页
	 */
	public void staticWapSite(SiteEntity site);

	/**
	 * 生成wap全站
	 * 
	 * @param pagecount
	 *            生成栏目页数
	 */
	public void staticWapSite(SiteEntity site,Integer pagecount);

	/**
	 * 生成wap栏目列表页 以及文章详细页 默认生成栏目前20页
	 * 
	 * @param id
	 *            栏目id
	 */
	public void staticWapCatalog(String id);

	/**
	 * 生成wap栏目列表页 以及文章详细页 默认生成栏目前20页
	 * 
	 * @param catalog
	 *            栏目
	 */
	public void staticWapCatalog(ContentCatEntity catalog);

	/**
	 * 生成wap栏目列表页 以及文章详细页
	 * 
	 * @param id
	 *            栏目id
	 * @param pagecount
	 *            生成栏目页数
	 */
	public void staticWapCatalog(String id, Integer pagecount);

	/**
	 * 生成wap栏目列表页 以及文章详细页
	 * 
	 * @param catalog
	 *            栏目
	 * @param pagecount
	 *            生成栏目页数
	 */
	public void staticWapCatalog(ContentCatEntity catalog, Integer pagecount);

	/**
	 * 生成wap详细页
	 * 
	 * @param id
	 *            文章id
	 */
	public void staticWapArticle(String id);

	/**
	 * 生成wap详细页
	 * 
	 * @param content
	 *            文章
	 */
	public void staticWapArticle(ContentsEntity content);

	/**
	 * 多篇文章同时生成
	 * 
	 * @param ids
	 */
	public void staticWapArticle(String[] ids);

	/**
	 * 多篇文章同时生成
	 * 
	 * @param contentList
	 */
	public void staticWapArticle(List<ContentsEntity> contentList);

	/**
	 * 生成wap首页
	 */
	public void staticWapIndex(SiteEntity site);
}

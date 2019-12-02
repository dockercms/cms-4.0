package com.leimingtech.core.service;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;

/**
 * 发布接口
 * 
 * @author liuzhen 2014年5月14日 10:12:22
 * 
 */
public interface IStatic{
	/**
	 * 快速发布一个站点
	 * 
	 * @throws Exception
	 */
	void staticSite(SiteEntity site) throws Exception;

	/**
	 * 发布站点
	 * 
	 * @param site
	 * @param quickCount
	 *            发布多少条，-1代表发布整站栏目列表页、详细页、区块页，谨慎使用；
	 * @throws Exception
	 */
	void staticSite(SiteEntity site,Integer quickCount) throws Exception;

	/**
	 * 快速发布一个栏目、及子栏目、和内容静态化
	 * 
	 * @param site
	 * @param catalogid
	 * @throws Exception
	 */
	void staticCatalog(SiteEntity site, String catalogid) throws Exception;

	/**
	 * 发布一个栏目、及子栏目、和内容静态化
	 * 
	 * @param site
	 * @param catalogid
	 * @param quickCount
	 * @throws Exception
	 */
	void staticCatalog(SiteEntity site, String catalogid, Integer quickCount) throws Exception;

	/**
	 * 发布一个站点里所有区块
	 * 
	 * @param site
	 * @throws Exception
	 */
	void staticSection(SiteEntity site) throws Exception;

	/**
	 * 发布一个区块分类下的包含区块
	 * 
	 * @param sectionClassid
	 * @param site
	 * @throws Exception
	 */
	void staticOneSectionClass(String sectionClassid,SiteEntity site) throws Exception;

	/**
	 * 发布一篇内容及它的所有负栏目列表页、和全部区块；
	 * 
	 * @param site
	 * @param catalogid
	 * @param contentid
	 * @throws Exception
	 */
	void staticContent(SiteEntity site, String catalogid, String contentid) throws Exception;

	/**
	 * 发布多篇文章及它的所有父栏目列表页、和全部区块
	 * 
	 * @param articleids
	 * @throws Exception
	 */
	void staticContents(SiteEntity site, ContentCatEntity contentCat, String[] articleids) throws Exception;

	/**
	 * 线程生成用于预览的文章页面
	 * 
	 * @param article
	 *            文章
	 * @throws Exception
	 */
	void staticContentActOnPreview(ContentsEntity article);

	/**
	 * 线程生成用于预览的文章页面
	 * 
	 * @param articleid
	 *            文章id
	 * @throws Exception
	 */
	void staticContentActOnPreview(String articleid);
	
	/**
	 * 生成首页静态文件
	 * @throws Exception 
	 */
	void staticIndex(SiteEntity site) throws Exception;
}

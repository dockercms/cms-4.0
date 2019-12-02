package com.leimingtech.core.service;

import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.SiteEntity;

public interface StaticMobileHtmlServiceI {

	/**
	 * 生成移动端详细页面
	 * 
	 * @param site
	 * @param content
	 */
	public void staticMobileArticle(SiteEntity site, ContentsMobileEntity content, MobileChannelEntity mobileChannel) throws Exception;

	/**
	 * 发布站点中所有移动端页面
	 * 
	 * @param site
	 *            站点
	 * @throws Exception
	 *             生成文件异常
	 */
	public void staticAllMobile(SiteEntity site) throws Exception;
}

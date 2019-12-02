package com.leimingtech.platform.constants;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.ResourceUtil;
import com.leimingtech.core.util.SystemPath;

/**
 * CMS常量
 * 
 * @author liuzhen 2014年5月4日 15:13:19
 * 
 */
public class CmsConstants {
	
	/** 站点存放位置 */
	public static final String SITE_STORAGE_PATH = "wwwroot";

	/** 站点模板存放位置 */
	public static final String SITE_TEMPLATE_PATH = "template";

	/** 模板引用路径 */
	public static final String INCLUDEPATH = "/section/";

	/** 生成预览文章文件名后缀 */
	public static final String PREVIEW_ARTICLE_SUFFIX = "_preview";

	/** 繁体文件名追加 */
	public static final String BIG5 = "_big5";
	
	/**视频默认预览图后缀*/
	public static final String VIDEO_DEFIMAGE_SUFFIX = ".default.jpg";
	
	/**文章分页符*/
	public static final String CONTENT_PAGE_CODE = "_ueditor_page_break_tag_";
	/**默认来源*/
	public static final String DEFAULT_SOURCE = "阅·红河";

	// 模板类型 关系到生成文件的分类
	/** 区块 */
	public static final int SECTION = 1;
	/** 模板 */
	public static final int TEMPLATE = 2;
	/** 分页 */
	public static final int PAGE = 3;
	/** 首页 */
	public static final int INDEX = 4;

	/**
	 * 获取站点模板根路径（绝对路径）
	 * @param session ContextHolderUtils.getSession()
	 * @return
	 */
	public static String getSiteTemplatePath(HttpSession session) {
		String separator = System.getProperty("file.separator");

		return getSitePath(session) + SITE_TEMPLATE_PATH + separator;
	}

	/**
	 * 获取站点根路径
	 * 
	 * @param session
	 *            ContextHolderUtils.getSession()
	 * @return
	 */
	public static String getSitePath(HttpSession session) {
		String separator = System.getProperty("file.separator");

		/*
		 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径 没有配置的话就读取工程
		 * WebRoot/wwwroot目录
		 */
		String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径

		// 从Session中获取站点
		Client client = ClientManager.getInstance().getClient(session.getId());

		if (client == null || client.getSite() == null) {
			return SystemPath.getSysPath();
		}
		SiteEntity site = client.getSite();
		String sitePath = site.getSitePath();// 站点路径
		if (StringUtils.isNotEmpty(staticFilesPath)) {
			return staticFilesPath + separator + sitePath + separator;// 站点资源路径
		} else {
			String sysPath = SystemPath.getSysPath();// 工程根路径 webroot
			return sysPath + SITE_STORAGE_PATH + separator + sitePath + separator;// 站点模板资源路径
		}
	}
	
	/**
	 * 获取站点根路径
	 * 
	 * @param SiteEntity
	 * 
	 * @return
	 */
	public static String getSitePath(SiteEntity site){
		if (site == null) {
			return SystemPath.getSysPath();
		}
		
		String sitePath = site.getSitePath();// 站点路径
		return getWWWROOT() + sitePath + "/";// 站点模板资源路径
	}
	
	/**获取wwwroot目录*/
	public static String getWWWROOT(){
		/*
		 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径 没有配置的话就读取工程
		 * WebRoot/wwwroot目录
		 */
		String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径

		if (StringUtils.isNotEmpty(staticFilesPath)) {
			staticFilesPath += "/";
			return staticFilesPath;// 站点资源路径
		} else {
			String sysPath = SystemPath.getSysPath();// 工程根路径 webroot
			return sysPath + SITE_STORAGE_PATH + "/";// wwwroot路径
		}
	}
	
	/**
	 * 获取站点模板根路径（绝对路径）
	 * @param site SiteEntity
	 * @return
	 */
	public static String getSiteTemplatePath(SiteEntity site) {
		String separator = System.getProperty("file.separator");

		return getSitePath(site) + SITE_TEMPLATE_PATH + separator;
	}
	
	/**
	 * 获取LMCMS站点静态文件发布路径
	 * 
	 * @author liuzhen 2015年6月11日 10:46:11
	 * 
	 * @param siteid
	 *            站点id
	 * 
	 * @return
	 */
	public static String getUploadFilesPath(String siteid) {
		
		SiteServiceI siteService = (SiteServiceI) PlatFormUtil
				.getInterface("siteService");
		SiteEntity site = siteService.getSite(siteid);

		return getUploadFilesPath(site);
	}
	
	/**
	 * 获取LMCMS站点静态文件发布路径
	 * 
	 * @author liuzhen 2015年8月31日 14:08:47
	 * 
	 * @param site
	 *            站点
	 * 
	 * @return
	 */
	public static String getUploadFilesPath(SiteEntity site) {
		
		boolean flag = ResourceUtil.getCMSUploadFilesSeparation();// LMCMS站点文件上传路径是否分离

		String uploadFilesPath = ResourceUtil.getCMSUploadFilesPath();// 获取LMCMS站点文件上传路径
		if (StringUtils.isNotEmpty(uploadFilesPath)) {
			if (flag) {
				// 根据站点分离时返回站点目录
				uploadFilesPath += ("/" + site.getSitePath() + "/");
			}
			return uploadFilesPath;
		}

		if (flag) {
			return getSitePath(site);
		} else {
			// 根据站点不分离时返回wwwwroot目录
			return CmsConstants.getWWWROOT();
		}
	}
	
}

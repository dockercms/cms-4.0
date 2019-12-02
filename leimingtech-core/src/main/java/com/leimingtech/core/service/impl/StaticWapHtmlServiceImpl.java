package com.leimingtech.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.common.ContentStatus;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.WapManager;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentVideoEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SurveyEntity;
import com.leimingtech.core.entity.TagCreator;
import com.leimingtech.core.entity.VoteEntity;
import com.leimingtech.core.entity.WapconfigEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.ContentVideoServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.IContentTagMng;
import com.leimingtech.core.service.StaticWapHtmlServiceI;
import com.leimingtech.core.service.SurveyServiceI;
import com.leimingtech.core.service.VoteServiceI;
import com.leimingtech.core.util.FileUtils;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.PathFormat;
import com.leimingtech.core.util.PlatFormUtil;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * wap生成静态页接口实现
 * 
 * @author liuzhen 2014年7月23日 19:41:37
 * 
 */
@Service("staticWapHtmlService")
@Transactional
public class StaticWapHtmlServiceImpl implements StaticWapHtmlServiceI {

	private String message;
	@Autowired
	private ContentVideoServiceI contentVideoService;
	@Autowired
	private SurveyServiceI curveyService;
	@Autowired
	private VoteServiceI voteService;
	@Autowired
	private ContentsServiceI contentsService;

	/**
	 * 生成wap全站 以及wap首页、栏目列表页、文章详细页、 默认生成栏目前10页
	 */
	public void staticWapSite(SiteEntity site) {
		WapManager wapmanager = new WapManager(site);
		if (wapmanager.getWapIsOpen()) {
			try {
				staticWapIndexStart(site, wapmanager);
				staticWapSiteStart(site, wapmanager);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成wap全站 以及wap首页、栏目列表页、文章详细页、 默认生成栏目前10页
	 * 
	 * @param manager
	 *            wap生成管理
	 * @throws Exception
	 */
	private void staticWapSiteStart(SiteEntity site, WapManager manager) throws Exception {
		staticWapSiteStart(site, 10, manager);
	}

	/**
	 * 生成wap全站
	 * 
	 * @param pagecount
	 *            生成栏目页数
	 */
	public void staticWapSite(SiteEntity site, Integer pagecount) {
		WapManager wapmanager = new WapManager(site);
		if (wapmanager.getWapIsOpen()) {
			try {
				staticWapIndexStart(site, wapmanager);
				staticWapSiteStart(site, pagecount, wapmanager);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成wap全站
	 * 
	 * @param pagecount
	 *            生成栏目页数
	 * @param manager
	 *            wap生成管理
	 * @throws Exception
	 */
	private void staticWapSiteStart(SiteEntity site, Integer pagecount, WapManager manager) throws Exception {
		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return;

		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		Set<String> catalogSet = manager.getCatalogs();
		String[] catalogids = new String[catalogSet.size()];
		catalogids = catalogSet.toArray(catalogids);
		cq.in("id", catalogids);
		cq.eq("siteid", site.getId());
		cq.eq("iscatend", 1);
		cq.eq("isshow", "1");
		cq.eq("disabled", 0);
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("id", SortDirection.desc);
		cq.add();
		List<ContentCatEntity> catalogList = contentsService.getListByCriteriaQuery(cq, false);

		Boolean isPublishAllContent = false;
		if (StringUtils.isNotEmpty(pagecount + "")) {
			if (pagecount == -1) {
				isPublishAllContent = true;
			}
		}

		if (catalogList != null && catalogList.size() > 0) {
			for (int i = 0; i < catalogList.size(); i++) {
				staticWapCatalogStart(site, catalogList.get(i), pagecount, manager, isPublishAllContent, true);
			}
		}
	}

	/**
	 * 生成wap栏目列表页 --以及新闻内容详细页-- 默认生成栏目前20页 ======= 生成wap栏目列表页 以及文章详细页 默认生成栏目前10页
	 * 
	 * @param id
	 *            栏目id
	 */
	public void staticWapCatalog(String id) {
		if (StringUtils.isEmpty(id + ""))
			return;
		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return;
		ContentCatEntity catalog = contentsService.get(ContentCatEntity.class, id);
		if (catalog == null)
			return;
		SiteEntity site = contentsService.getEntity(SiteEntity.class, catalog.getSiteid());
		if (site == null)
			return;
		WapManager manager = new WapManager(site);

		if (manager.getWapIsOpen()) {
			try {
				staticWapCatalogStart(site, catalog, manager);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成wap栏目列表页 以及文章详细页 默认生成栏目前10页
	 * 
	 * @param catalog
	 *            栏目
	 */
	public void staticWapCatalog(ContentCatEntity catalog) {
		if (catalog == null)
			return;

		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return;

		SiteEntity site = contentsService.getEntity(SiteEntity.class, catalog.getId());
		if (site == null)
			return;

		WapManager wapmanager = new WapManager(site);
		if (wapmanager.getWapIsOpen()) {
			try {
				staticWapCatalogStart(site, catalog, wapmanager);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成wap栏目列表页 以及文章详细页 默认生成栏目前10页
	 * 
	 * @param catalog
	 *            栏目
	 * @param manager
	 *            wap生成管理
	 * @throws Exception
	 */
	private void staticWapCatalogStart(SiteEntity site, ContentCatEntity catalog, WapManager manager) throws Exception {

		staticWapCatalogStart(site, catalog, 10, manager, false, true);

	}

	/**
	 * 生成wap栏目列表页 以及文章详细页
	 * 
	 * @param id
	 *            栏目id
	 * @param pagecount
	 *            生成栏目页数
	 */
	public void staticWapCatalog(String id, Integer pagecount) {
		if (StringUtils.isEmpty(id + ""))
			return;

		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return;

		ContentCatEntity catalog = contentsService.getEntity(ContentCatEntity.class, id);
		if (catalog == null)
			return;

		SiteEntity site = contentsService.getEntity(SiteEntity.class, catalog.getId());
		if (site == null)
			return;

		WapManager manager = new WapManager(site);
		if (manager.getWapIsOpen()) {
			try {
				if (StringUtils.isNotEmpty(pagecount + "")) {
					if (pagecount > 0) {
						staticWapCatalogStart(site, catalog, pagecount, manager, false, false);
					} else if (pagecount == -1) {
						staticWapCatalogStart(site, catalog, pagecount, manager, true, true);
					}
				} else {
					staticWapCatalogStart(site, catalog, pagecount, manager, false, false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成wap栏目列表页 以及文章详细页
	 * 
	 * @param catalog
	 *            栏目
	 * @param pagecount
	 *            生成栏目页数
	 */
	public void staticWapCatalog(ContentCatEntity catalog, Integer pagecount) {
		if (catalog == null)
			return;

		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return;

		SiteEntity site = contentsService.getEntity(SiteEntity.class, catalog.getId());
		if (site == null)
			return;

		final WapManager wapmanager = new WapManager(site);
		if (wapmanager.getWapIsOpen()) {
			try {
				if (StringUtils.isNotEmpty(pagecount + "")) {
					if (pagecount > 0) {
						staticWapCatalogStart(site, catalog, pagecount, wapmanager, false, false);
					} else if (pagecount == -1) {
						staticWapCatalogStart(site, catalog, pagecount, wapmanager, true, true);
					}
				} else {
					staticWapCatalogStart(site, catalog, -1, wapmanager, true, true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成wap栏目列表页 以及文章详细页
	 * 
	 * @param catalog
	 *            栏目
	 * @param quickCount
	 *            生成栏目页数
	 * @param manager
	 *            wap生成管理
	 * @param session
	 *            ContextHolderUtils.getSession();
	 * @param isPublishAllContent
	 *            是否发布栏目下所有文章
	 * @param isAndPublishContent
	 *            是否发布内容
	 * @throws Exception
	 */
	private void staticWapCatalogStart(SiteEntity site, ContentCatEntity catalog, Integer quickCount, WapManager manager, Boolean isPublishAllContent, Boolean isAndPublishContent) throws Exception {
		if (catalog == null)
			return;

		List<ContentCatEntity> catalogChildList = catalog.getContentCats();
		if (catalogChildList != null && catalogChildList.size() > 0) {
			// 如果当前栏目有子栏目不做生成操作
			for (int i = 0; i < catalogChildList.size(); i++) {
				staticWapCatalogStart(site, catalogChildList.get(i), quickCount, manager, isPublishAllContent, isAndPublishContent);
			}
			return;
		}

		if (catalog.getIscatend() == 0)
			return;

		Set<String> catalogs = manager.getCatalogs();// 当栏目没开启 不生成
		if (!catalogs.contains(catalog.getId()))
			return;

		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class);
		cq.eq("catid", catalog.getId());// 42
		Set<String> modelsSet = manager.getModels();
		String[] modelids = new String[modelsSet.size()];
		modelids = modelsSet.toArray(modelids);
		Set<String> catalogSet = manager.getCatalogs();
		String[] catalogids = new String[catalogSet.size()];
		catalogids = catalogSet.toArray(catalogids);
		cq.in("modelid", modelids);
		cq.in("constants", new String[] { ContentStatus.CONTENT_COMMITTED, ContentStatus.CONTENT_PUBLISHED });
		Date date = new Date();
		cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
		cq.le("published", date);
		cq.addOrder("published", SortDirection.desc);
		cq.addOrder("id", SortDirection.desc);
		cq.add();
		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return;
		List<ContentsEntity> contentList = contentsService.getListByCriteriaQuery(cq, false);
		WapconfigEntity wapconfig = manager.getWapconfig();
		Integer total = 0;// 栏目中内容总数
		Integer pagesize = 15;
		if (StringUtils.isNotEmpty(wapconfig.getListcount())) {
			pagesize = Integer.valueOf(wapconfig.getListcount());
		}
		if (contentList != null && contentList.size() > 0 && pagesize > 0) {

			Integer pagecount = contentList.size() / pagesize;
			if (contentList.size() % pagesize != 0) {
				pagecount++;
			}
			if (quickCount == -1 || pagecount < quickCount) {
				// 查询栏目全部数据
				total = contentList.size();
			} else {
				pagecount = quickCount;
				total = pagecount * pagesize;
			}
			// 静态化栏目下的详细页
			if (isAndPublishContent) {
				int publishCount = total;
				if (isPublishAllContent) {
					publishCount = contentList.size();
				}
				for (int i = 0; i < publishCount; i++) {
					staticWapArticleStart(site, contentList.get(i), manager, catalog);
				}
			}
			// 生成栏目列表页
			staticWapCatalogListStart(site, catalog, pagecount, pagesize, total, manager);
		} else {
			// 没有数据只有一页
			staticWapCatalogListStart(site, catalog, 1, total, pagesize, manager);
		}
	}

	/**
	 * 生成栏目列表页
	 * 
	 * @param catalog
	 *            栏目
	 * @param pagecount
	 *            生成总页数
	 * @param total
	 *            数据总数
	 * @param pagesize
	 *            每页条数
	 * @param manager
	 *            wap生成管理
	 * @throws Exception
	 */
	private void staticWapCatalogListStart(SiteEntity site, ContentCatEntity catalog, Integer pagecount, Integer total, Integer pagesize, WapManager manager) throws Exception {
		Date date = new Date();
		// ---------------------------------查询头条---------------------------------------------------------
		Set<String> catalogsSet = manager.getCatalogs();
		String[] catalogids = new String[catalogsSet.size()];
		catalogids = catalogsSet.toArray(catalogids);
		List<ContentsEntity> contentListHeadLine = new ArrayList<ContentsEntity>();
		CriteriaQuery cq1 = new CriteriaQuery(ContentsEntity.class, pagesize, 1, null, null);
		Set<String> modelsSet1 = manager.getModels();
		String[] modelids1 = new String[modelsSet1.size()];
		modelids1 = catalogsSet.toArray(modelids1);
		cq1.in("catid", catalogids);
		cq1.eq("isHeadline", 1);
		cq1.add();
		contentListHeadLine = contentsService.getListByCriteriaQuery(cq1, true);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("headlineList", contentListHeadLine);
		// ----------------------------------查询开启的栏目--------------------------------------------------------
		List<ContentCatEntity> allOpenCatalog = new ArrayList<ContentCatEntity>();
		String siteid = site.getId();
		if (catalogids != null && catalogids.length > 0) {
			// 查出开启的栏目
			CriteriaQuery cqCatalog = new CriteriaQuery(ContentCatEntity.class);
			cqCatalog.eq("siteid", siteid);
			cqCatalog.eq("iscatend", 1);
			cqCatalog.eq("isshow", "1");
			cqCatalog.in("id", catalogids);
			cqCatalog.eq("disabled", 0);
			cqCatalog.addOrder("sort", SortDirection.desc);
			cqCatalog.addOrder("id", SortDirection.desc);
			cqCatalog.add();
			allOpenCatalog = contentsService.getListByCriteriaQuery(cqCatalog, false);
		}
		data.put("catalogList", allOpenCatalog);
		// ------------------------------------------------------------------------------------------
		String templateFilepath = manager.getWapconfig().getListtemplate();

		if (org.apache.commons.lang3.StringUtils.isEmpty(templateFilepath)) {
			message = "wap栏目《" + catalog.getName() + "》发布失败！原因：模板未配置！";
			LogUtil.error(message);
			return;
		}

		String siteTemplatePath = CmsConstants.getSiteTemplatePath(site);
		templateFilepath = siteTemplatePath + templateFilepath;
		File templateFile = new File(PathFormat.format(templateFilepath));
		if (!templateFile.exists()) {
			message = "wap栏目《" + catalog.getName() + "》发布失败！原因：模板不存在！";
			LogUtil.error(message);
			return;
		}

		

		String filename = "";
		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		for (int i = 0; i < pagecount; i++) {
			data.put("catalogF", catalog);
			data.put("catalogidF", catalog.getId());
			data.put("pageindexF", i + 1);
			// data.put("pagesizeF", pagesize);

			CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class, pagesize, i + 1, null, null);
			cq.eq("catid", catalog.getId());
			cq.eq("siteid", site.getId());
			Set<String> modelsSet = manager.getModels();
			String[] modelids = new String[modelsSet.size()];
			modelids = modelsSet.toArray(modelids);
			cq.in("modelid", modelids);

			cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
			cq.le("published", date);
			cq.in("constants", new String[] { ContentStatus.CONTENT_COMMITTED, ContentStatus.CONTENT_PUBLISHED });
			cq.addOrder("published", SortDirection.desc);
			cq.addOrder("id", SortDirection.desc);
			cq.add();

			List<ContentsEntity> contentList = contentsService.getListByCriteriaQuery(cq, true);
			data.put("contentList", contentList);
			data.put("prevpage", i);
			if (i == pagecount - 1) {
				data.put("nextpage", false);
			} else {
				data.put("nextpage", i + 2);
			}
			if (i == 0) {
				filename = "wap_list";
				data.put("prevpage", false);
				catalog.setWapUrl(catalog.getPath()+"wap_list.shtml"); //-----设置wapUrl-----------------------
				
			} else {
				filename = "wap_list_" + (i + 1);
			}
			invoke(site, templateFilepath, catalog.getPath(), data, filename);
		}
		
		

	}

	/**
	 * 生成wap详细页
	 * 
	 * @param id
	 *            文章id
	 */
	public void staticWapArticle(final String id) {

		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return;

		ContentsEntity content = contentsService.getEntity(ContentsEntity.class, id);
		if (content == null)
			return;

		SiteEntity site = contentsService.getEntity(SiteEntity.class, content.getId());

		final WapManager wapmanager = new WapManager(site);
		if (wapmanager.getWapIsOpen()) {
			try {
				staticWapIndexStart(site, wapmanager);// 发布首页

				staticWapArticleStart(site, content, wapmanager, null);
				ContentCatEntity catalog = contentsService.getEntity(ContentCatEntity.class, content.getCatid());

				staticWapCatalogStart(site, catalog, 10, wapmanager, false, false);// 发布栏目

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成wap详细页
	 * 
	 * @param id
	 *            文章id
	 * @param manager
	 *            wap生成管理
	 * @param session
	 *            ContextHolderUtils.getSession();
	 * @param catalog
	 *            是否是同一栏目==>是同一栏目减少查询次数 直接传入栏目
	 * @throws Exception
	 */
	private void staticWapArticleStart(SiteEntity site, String id, WapManager manager, ContentCatEntity catalog) throws Exception {
		if (StringUtils.isEmpty(id + ""))
			throw new IllegalArgumentException("内容id空值,请传入正确的id");

		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService != null) {
			staticWapArticleStart(site, (ContentsEntity) contentsService.getEntity(ContentsEntity.class, id), manager, catalog);
		}
	}

	/**
	 * 生成wap详细页
	 * 
	 * @param content
	 *            文章
	 */
	public void staticWapArticle(final ContentsEntity content) {
		if (content == null)
			return;

		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return;

		SiteEntity site = contentsService.getEntity(SiteEntity.class, content.getId());

		final WapManager wapmanager = new WapManager(site);
		if (wapmanager.getWapIsOpen()) {
			try {
				staticWapIndexStart(site, wapmanager);// 发布首页
				staticWapArticleStart(site, content, wapmanager, null);

				ContentCatEntity catalog = contentsService.getEntity(ContentCatEntity.class, content.getCatid());

				staticWapCatalogStart(site, catalog, 10, wapmanager, false, false);// 发布栏目

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成wap详细页
	 * 
	 * @param content
	 *            文章
	 * @param manager
	 *            wap生成管理
	 * @param catalog
	 *            是否是同一栏目==>是同一栏目减少查询次数 直接传入栏目
	 * @throws Exception
	 */
	private void staticWapArticleStart(SiteEntity site, ContentsEntity content, WapManager manager, ContentCatEntity catalog) throws Exception {
		 
		if (content == null)
			throw new IllegalArgumentException("内容空值,请传入正确的内容实体");

		if (catalog == null) {// 当此文章不是在同一栏目
			catalog = isCntentGenerate(content, manager);

			if (catalog == null)// 并且此内容不需要生成
				return;
		}

		if (!isModelGenerate(content, manager))// 模型没开启不生成
			return;

		WapconfigEntity wapconfig = manager.getWapconfig();
		String template = "";
		Map<String, Object> data = new HashMap<String, Object>();
		// -----------------------------文章--------------------------------------------
		if (ContentClassify.CONTENT_ARTICLE.equals(content.getClassify() + "")) {
			template = wapconfig.getContenttemplate();
		}
		// ---------------------------组图---------------------------------------------
		if (ContentClassify.CONTENT_PICTUREGROUP.equals(content.getClassify() + "")) {
			template = wapconfig.getPicturestemplate();
		}
		// ---------------------------视频模型-----------------------------------------
		if (ContentClassify.CONTENT_VIDEO.equals(content.getClassify())) {
			template = wapconfig.getVideoTemplate();
			List<ContentVideoEntity> contentVideos = contentVideoService.findByProperty(ContentVideoEntity.class, "contentid", content.getId());
			if (contentVideos.size() == 1) {
				data.put("contentVideo", contentVideos.get(0));
			}
		}
		// ----------------------------调查---------------------------------------------
		if (ContentClassify.CONTENT_SURVEY.equals(content.getClassify())) {// CONTENT_SURVEY=8
			template = wapconfig.getSurveyTemplate();
			List<SurveyEntity> surveys = curveyService.findByProperty(SurveyEntity.class, "contentid", content.getId());
			if (surveys.size() == 1) {
				data.put("surveyF", surveys.get(0));
			}
		}
		// ----------------------------投票---------------------------------------------
		if (ContentClassify.CONTENT_VOTE.equals(content.getClassify()))
			template = wapconfig.getVoteTemplate();
		List<VoteEntity> votes = voteService.findByProperty(VoteEntity.class, "contentid", content.getId());
		if (votes.size() == 1) {
			data.put("voteF", votes.get(0));
		}

		if (StringUtils.isEmpty(template)) {
			message = "wap文章《" + content.getTitle() + "》发布失败！原因：模板未配置！";
			LogUtil.error(message);
			return;
		}

		String siteTemplatePath = CmsConstants.getSiteTemplatePath(site);
		template = siteTemplatePath + template;
		File templateFile = new File(PathFormat.format(template));
		if (!templateFile.exists()) {
			message = "wap文章《" + content.getTitle() + "》发布失败！原因：模板不存在！";
			LogUtil.error(message);
			return;
		}

		IContentTagMng articleTagMng = (IContentTagMng) PlatFormUtil.getInterface("contentTagMng");
		data.put("contentF", content);
		data.put("contentidF", content.getId());
		data.put("catalogF", catalog);
		data.putAll(articleTagMng.getContent(content));

		invoke(site, template, catalog.getPath(), data, "wap_" + content.getId());

	}

	/**
	 * 判断内容所属栏目是否需要生成==》需要生成返回栏目
	 * 
	 * @param content
	 *            文章
	 * @param manager
	 *            wap生成管理
	 * @return
	 */
	private ContentCatEntity isCntentGenerate(ContentsEntity content, WapManager manager) {
		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return null;

		ContentCatEntity catalog = contentsService.getEntity(ContentCatEntity.class, content.getCatid());
		if (catalog == null || catalog.getIscatend() == 0)
			return null;

		Set<String> catalogs = manager.getCatalogs();// 档栏目没开启 不生成
		if (!catalogs.contains(catalog.getId()))
			return null;

		return catalog;
	}

	/**
	 * 判断内容模型是否开启
	 * 
	 * @param content
	 *            文章
	 * @param manager
	 *            wap生成管理
	 * @return
	 */
	private Boolean isModelGenerate(ContentsEntity content, WapManager manager) {
		Set<String> models = manager.getModels();
		if (!models.contains(content.getModelid()))// 当内容模型没开启 不生成
			return false;

		return true;
	}

	/**
	 * 多篇文章同时生成
	 * 
	 * @param ids
	 */
	@Override
	public void staticWapArticle(final String[] ids) {
		if (ids == null || ids.length == 0)
			return;

		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null || ids == null || ids.length == 0)
			return;

		ContentsEntity content = contentsService.getEntity(ContentsEntity.class, ids[0]);
		if (content == null)
			return;

		ContentCatEntity catalog = contentsService.getEntity(ContentCatEntity.class, content.getCatid());

		SiteEntity site = contentsService.getEntity(SiteEntity.class, content.getSiteid());
		if (site == null)
			return;

		final WapManager wapmanager = new WapManager(site);
		if (wapmanager.getWapIsOpen()) {
			try {
				staticWapIndexStart(site, wapmanager);// 发布首页
				staticWapArticleStart(site, ids, wapmanager);

				staticWapCatalogStart(site, catalog, 10, wapmanager, false, false);// 发布栏目

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 多篇文章同时生成
	 * 
	 * @param ids
	 *            内容id
	 * @param manager
	 *            wap生成管理
	 * @throws Exception
	 */
	private void staticWapArticleStart(SiteEntity site, String[] ids, WapManager manager) throws Exception {
		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		ContentsEntity content = null;
		ContentCatEntity catalog = null;
		if (contentsService == null)
			return;

		content = contentsService.getEntity(ContentsEntity.class, ids[0]);
		catalog = isCntentGenerate(content, manager);
		if (catalog == null) {
			return;
		}

		if (site == null)
			return;

		if (ids.length == 1) {
			staticWapArticleStart(site, content, manager, catalog);
			return;
		}

		for (int i = 1; i < ids.length; i++) {
			staticWapArticleStart(site, ids[i], manager, catalog);
		}
	}

	/**
	 * 多篇文章同时生成
	 * 
	 * @param contentList
	 */
	@Override
	public void staticWapArticle(List<ContentsEntity> contentList) {
		if (contentList == null || contentList.size() == 0)
			return;

		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null || contentList == null || contentList.size() == 0)
			return;

		ContentCatEntity catalog = contentsService.getEntity(ContentCatEntity.class, contentList.get(0).getCatid());

		if (catalog == null)
			return;

		SiteEntity site = contentsService.getEntity(SiteEntity.class, catalog.getId());
		if (site == null)
			return;

		final WapManager wapmanager = new WapManager(site);
		if (wapmanager.getWapIsOpen()) {
			try {
				staticWapIndexStart(site, wapmanager);// 发布首页
				staticWapArticleStart(site, contentList, wapmanager);

				staticWapCatalogStart(site, catalog, 10, wapmanager, false, false);// 发布栏目

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 多篇文章同时生成
	 * 
	 * @param contentList
	 *            内容列表
	 * @param manager
	 *            wap生成管理
	 * @param session
	 *            ContextHolderUtils.getSession();
	 * @throws Exception
	 */
	public void staticWapArticleStart(SiteEntity site, List<ContentsEntity> contentList, WapManager manager) throws Exception {
		ContentCatEntity catalog = isCntentGenerate(contentList.get(0), manager);
		if (catalog == null) {
			return;
		}

		if (contentList.size() == 1) {
			staticWapArticleStart(site, contentList.get(0), manager, catalog);
			return;
		}

		for (int i = 1; i < contentList.size(); i++) {
			staticWapArticleStart(site, contentList.get(0), manager, catalog);
		}

	}

	/**
	 * 生成wap首页
	 */
	public void staticWapIndex(SiteEntity site) {
		WapManager wapmanager = new WapManager(site);
		if (wapmanager.getWapIsOpen()) {
			try {
				staticWapIndexStart(site, wapmanager);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成wap首页
	 * 
	 * @param manager
	 *            wap生成管理
	 * @throws Exception
	 */
	private void staticWapIndexStart(SiteEntity site, WapManager manager) throws Exception {
		WapconfigEntity wapconfig = manager.getWapconfig();

		String template = wapconfig.getIndextemplate();

		if (StringUtils.isEmpty(template)) {
			message = "wap首页发布失败！原因：模板未配置！";
			LogUtil.error(message);
			return;
		}

		String siteTemplatePath = CmsConstants.getSiteTemplatePath(site);
		template = siteTemplatePath + template;
		File templateFile = new File(PathFormat.format(template));
		if (!templateFile.exists()) {
			message = "wap首页发布失败！原因：模板不存在！";
			LogUtil.error(message);
			return;
		}

		Map<String, Object> data = new HashMap<String, Object>();
		String indexcolumncount = wapconfig.getIndexcolumncount();
		String columncount = wapconfig.getColumncount();
		if (StringUtils.isEmpty(indexcolumncount) || !StringUtils.isNumeric(indexcolumncount) || StringUtils.isEmpty(columncount) || !StringUtils.isNumeric(columncount))
			return;
		ContentsServiceI contentsService = (ContentsServiceI) PlatFormUtil.getInterface("contentsService");
		if (contentsService == null)
			return;

		//putIndexData(site, manager, data, indexcolumncount, columncount, contentsService);
		invoke(site, template, null, data, "wap_index");
	}

	/**
	 * 在wap首页模板中注入数据
	 * 
	 * @param manager
	 * @param session
	 * @param data
	 * @param indexcolumncount
	 * @param columncount
	 * @param contentsService
	 */
	private void putIndexData(SiteEntity site, WapManager manager, Map<String, Object> data, String indexcolumncount, String columncount, ContentsServiceI contentsService) {
		String siteid = site.getId();
		Set<String> catalogsSet = manager.getCatalogs();
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
			cqCatalog.addOrder("id", SortDirection.desc);
			cqCatalog.add();
			allOpenCatalog = contentsService.getListByCriteriaQuery(cqCatalog, false);
		}
		data.put("catalogList", allOpenCatalog);

		List<ContentsEntity> contentList = new ArrayList<ContentsEntity>();
		if (allOpenCatalog != null && allOpenCatalog.size() > 0) {
			// 查出头条
			CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class, Integer.valueOf(indexcolumncount), 1, null, null);
			Set<String> modelsSet = manager.getModels();
			String[] modelids = new String[modelsSet.size()];
			modelids = catalogsSet.toArray(modelids);
			cq.in("catid", catalogids);
		 
			cq.eq("isHeadline", 1);
			cq.add();
			contentList = contentsService.getListByCriteriaQuery(cq, true);
		}
		 
		data.put("headlineList", contentList);

	}

	/**
	 * 添加参数支持
	 * 
	 * @param templateFileName
	 *            以站点资源为根的模板路径
	 * @param filePath
	 *            生成文件路径 根据栏目结构产生的文件路径
	 * @param data
	 *            注入数据
	 * @param filename
	 *            生成文件名 不含后缀
	 * @throws Exception
	 */
	private String invoke(SiteEntity site, String templateFilepath, String filePath, Map<String, Object> data, String filename) throws Exception {
		if (MapUtils.isEmpty(data))
			data = new HashMap<String, Object>();

		String sitePath = CmsConstants.getSitePath(site);

		String staticSuffix = site.getStaticSuffix();// 静态页面后缀
		if (StringUtils.isEmpty(staticSuffix)) {
			staticSuffix = ".shtml";
		}

		File templateFile = new File(templateFilepath);

		if (StringUtils.isEmpty(filename)) {
			filename = FileUtils.getFilePrefix(templateFile.getName()) + "_0" + staticSuffix;
		} else {
			filename += staticSuffix;
		}

		String returnPath = "";
		if (StringUtils.isNotEmpty(filePath)) {
			returnPath = PathFormat.format(filePath + filename);
		} else {
			returnPath = filename;
		}
		String generateFilePath = PathFormat.format(sitePath + returnPath);

		data.put("newTag", new TagCreator());
		data.put("site", site);
		data.put("contextpath", Globals.CONTEXTPATH);
		generateFile(templateFile, data, generateFilePath);

		return returnPath;
	}

	/**
	 * 生成创建静态文件
	 * 
	 * @param templateFile
	 *            模板文件
	 * @param data
	 *            注入数据
	 * @param generateFilePath
	 *            生成文件路径
	 * 
	 * @throws Exception
	 * @return 生成路径 站点为根路径
	 */
	private void generateFile(File templateFile, Map<String, Object> data, String generateFilePath) throws Exception {
		Writer out = null;
		File tempFile = FileUtils.getTmpFile();
		try {
			Configuration cfg = new Configuration();
			cfg.setLocale(Locale.CHINA);
			cfg.setDefaultEncoding("UTF-8");
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate("myTemplate", IOUtils.toString(new FileInputStream(templateFile), "utf-8"));
			cfg.setTemplateLoader(stringLoader);
			Template template = cfg.getTemplate("myTemplate", "utf-8");

			String fileDir = StringUtils.substringBeforeLast(generateFilePath, "/");
			org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + "/"));
			out = new OutputStreamWriter(new FileOutputStream(tempFile), "utf-8");
			template.process(data, out);
			out.flush();
			out.close();

			try {
				org.apache.commons.io.FileUtils.copyFile(tempFile, new File(generateFilePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			tempFile.delete();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

}

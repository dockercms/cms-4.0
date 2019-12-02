package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.*;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.*;
import com.leimingtech.core.util.*;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发布实现
 * 
 * @author liuzhen 2014年5月14日 10:11:08
 * 
 */
@Service("staticImpl")
@Transactional
public class StaticImpl extends CommonServiceImpl implements IStatic {

	private String message;

	@Autowired
	private IContentTagMng contentMng;
	/**栏目管理接口*/
	@Autowired
	private ContentCatServiceI contentCatService;
	/**内容接口*/
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private UserService userService;

	/**
	 * 快速发布一个站点
	 * 
	 * @throws Exception
	 */
	public void staticSite(SiteEntity site) throws Exception {
		staticSite(site,10);// 默认发布每个栏目的前10页；
	}

	/**
	 * 发布站点
	 * 
	 * @param quickCount
	 *            发布多少页，-1代表发布整站栏目列表页、详细页、区块页，谨慎使用；
	 * @throws Exception
	 */
	public void staticSite(SiteEntity site,Integer quickCount) throws Exception {
		staticIndex(site);
		// 然后发布区块
		staticSection(site);
		
		List<ContentCatEntity> catalogList=contentCatService.getOpenRootContentCat(site.getId());// 一级栏目

		for (int i = 0; i < catalogList.size(); i++) {
			staticCatalogListAndContent(site, catalogList.get(i), quickCount);
		}
	}

	/**
	 * 快速发布一个栏目、及子栏目、和内容静态化
	 * 
	 * @param site
	 * @param catalogid
	 * @throws Exception
	 */
	public void staticCatalog(SiteEntity site, String catalogid) throws Exception {
		staticCatalog(site, catalogid, 10);
	}

	/**
	 * 发布一个栏目、及子栏目、和内容静态化
	 * 
	 * @param site
	 * @param catalogid
	 * @param quickCount
	 * @throws Exception
	 */
	public void staticCatalog(SiteEntity site, String catalogid, Integer quickCount) throws Exception {
		staticCatalogListAndContent(site, catalogid, quickCount);
		// 然后发布区块
		staticSection(site);
	}

	/**
	 * 发布一个站点里所有区块
	 * 
	 * @param site
	 * @throws Exception
	 */
	public void staticSection(SiteEntity site) throws Exception {

		CriteriaQuery cq = new CriteriaQuery(SectionEntity.class);
		cq.eq("siteid", site.getId());
		cq.add();
		List<SectionEntity> sectionlist = getListByCriteriaQuery(cq, false);

		if (sectionlist != null && sectionlist.size() > 0) {

			for (int i = 0; i < sectionlist.size(); i++) {
				staticOneSection(site,sectionlist.get(i));
			}
		}

	}

	/**
	 * 发布一个区块分类下的包含区块
	 * 
	 * @param sectionClassid
	 * @param site
	 * @throws Exception
	 */
	public void staticOneSectionClass(String sectionClassid,SiteEntity site) throws Exception {
		CriteriaQuery cq = new CriteriaQuery(SectionEntity.class);
		cq.eq("classid", sectionClassid);
		cq.add();
		List<SectionEntity> sectionlist = getListByCriteriaQuery(cq, false);

		if (sectionlist != null && sectionlist.size() > 0) {
			for (int i = 0; i < sectionlist.size(); i++) {
				staticOneSection(site, sectionlist.get(i));
			}
		}
	}

	/**
	 * 发布一个区块
	 * 
	 * @param section
	 * @throws Exception
	 */
	public void staticOneSection(SiteEntity site,SectionEntity section) throws Exception {
		if (section == null)
			return;

		try {
			Integer pagesize = section.getNum();
			Map<String, Object> data = new HashMap<String, Object>();
			List<SectionDataEntity> dataList = null;
			// 获取区块所关联数据列表
			int pageNo = 1;
			CriteriaQuery cq = new CriteriaQuery(SectionDataEntity.class);
			cq.eq("sectionid", section.getId());
			cq.add();
			if (pagesize == 0) {
				dataList = getListByCriteriaQuery(cq, false);
			} else {
				cq.setPageSize(pagesize);
				cq.setCurPage(pageNo);
				dataList = getListByCriteriaQuery(cq, true);
			}
			data.put("site", site);
			data.put("sectionDataListF", dataList);
			data.put("contextpath", Globals.CONTEXTPATH);
			
			
			String separator = System.getProperty("file.separator");
			// 模板位置
			String templateFilepath= section.getTemplepath();
			templateFilepath = templateFilepath.replaceAll("/", separator + separator);
			templateFilepath=CmsConstants.getSiteTemplatePath(site) + templateFilepath;

			File file = new File(templateFilepath);
			if(!file.exists()){
				LogUtil.error("区块《" + section.getName() + "》发布失败！ 原因：模板不存在"
						+ templateFilepath);
				return;
			}
			
			invoke(site, templateFilepath, CmsConstants.INCLUDEPATH, data, null, null);
			message = "区块《" + section.getName() + "》发布成功！";
		} catch (TemplateException e) {
			e.printStackTrace();
			message = "区块《" + section.getName() + "》发布失败！ 原因：" + e.getMessage();
			LogUtil.error(message);
		} catch (IOException e) {
			e.printStackTrace();
			message = "区块《" + section.getName() + "》发布失败！ 原因：" + e.getMessage();
			LogUtil.error(message);
		}
	}

	/**
	 * 发布一个栏目、及子栏目、和内容静态化
	 * 
	 * @param site
	 * @param catalogid
	 * @param quickCount
	 *            静态化页数
	 * @throws Exception
	 */
	private void staticCatalogListAndContent(SiteEntity site, String catalogid, Integer quickCount) throws Exception {
		ContentCatEntity catalog = getEntity(ContentCatEntity.class, catalogid);
		staticCatalogListAndContent(site, catalog, quickCount);
	}

	/**
	 * 发布一个栏目、及子栏目、和内容静态化
	 * 
	 * @param site
	 * @param catalog
	 * @param quickCount
	 *            静态化页数
	 * @throws Exception
	 */
	private void staticCatalogListAndContent(SiteEntity site, ContentCatEntity catalog, Integer quickCount) throws Exception {
		if(catalog==null||catalog.getId()==null){
			return ;
		}
		Integer pagesize = catalog.getPagesize() == 0 ? 10 : catalog.getPagesize();

		List<String> catalogIds = new ArrayList<String>();
		catalogIds.add(catalog.getId());
		if (0 == catalog.getIscatend()) {
			//当前栏目不是最末级目录才会有获取栏目下所有数据情况
			catalogIds = this.contentCatService.getOpenChildIdList(catalog.getPathids());//根据指定栏目查询出所有子集启用的栏目id集合
		}

		long contentCount=this.contentsService.getCatalogOpenContentCount(catalogIds);//获取栏目下允许查看的内容总数
		long total = 0;// 栏目中文章总数
		
		if (contentCount>0 && pagesize > 0) {
			long pagecount = contentCount / pagesize;
			if (contentCount % pagesize != 0) {
				pagecount++;
			}

			if (quickCount == -1 || pagecount < quickCount) {
				// 查询栏目全部数据；
				total = contentCount;
			} else {
				pagecount = quickCount;
				total = pagecount * pagesize;
			}
			// 静态化本栏目下的文章
			staticContent(catalog,site);

			// 静态化列表页
			staticOnlyCatalogList(site, catalog, pagecount, total);
			
		} else {
			// 没有数据只有一页
			staticOnlyCatalogList(site, catalog, 1, total);
		}
		
		// 级联查询子栏目来发布
		CriteriaQuery cqChild = new CriteriaQuery(ContentCatEntity.class);
		cqChild.eq("contentCat.id",catalog.getId());
		cqChild.eq("disabled",0);
		cqChild.eq("isshow", "1");
		cqChild.add();
		List<ContentCatEntity> childCatalogList = getListByCriteriaQuery(cqChild, false);
		if (childCatalogList != null && childCatalogList.size() > 0) {
			for (int i = 0; i < childCatalogList.size(); i++) {
				if(childCatalogList.get(i)==null){
					continue;
				}
				staticCatalogListAndContent(site, childCatalogList.get(i), quickCount);
			}
		}
	}

	/**
	 * 静态化指定栏目下的文章
	 * 
	 * @param catalog
	 *            栏目
	 * @throws Exception
	 */
	private void staticContent(ContentCatEntity catalog,SiteEntity site) throws Exception {
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class);
		cq.eq("catid", catalog.getId());
		Date date = new Date();
		cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
		cq.le("published", date);
		cq.in("constants", new String[]{ContentStatus.CONTENT_COMMITTED, ContentStatus.CONTENT_PUBLISHED});
		cq.add();
		List<ContentsEntity> contentList = getListByCriteriaQuery(cq, false);
		if (contentList != null && contentList.size() > 0) {
			int size = contentList.size();
			for (int i = 0; i < size; i++) {
				ContentsEntity content = contentList.get(i);
				if (content == null)
					continue;

				staticOnlyContent(content,catalog,site);
			}
		}
	}

	/**
	 * 发布一个栏目
	 * 
	 * @param site
	 * @param catalog
	 * @param quickCount
	 *            发布总页数
	 * @throws Exception
	 */
	private void staticOnlyCatalogList(SiteEntity site, ContentCatEntity catalog, long quickCount, long total) throws Exception {
		String path = Globals.CONTEXTPATH;
		if(catalog.getIsLinkUrl()!=null && catalog.getIsLinkUrl()==1){
			catalog.setUrl(catalog.getLinkUrl());
			saveOrUpdate(catalog);
			message = "栏目《" + catalog.getName() + "发布成功！";
			return ;
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("catalogF", catalog);
		data.put("site", site);
		data.put("catalogidF", catalog.getId());
		data.put("contextpath", Globals.CONTEXTPATH);

		if (StringUtils.isEmpty(catalog.getListModel())) {
			message = "栏目《" + catalog.getName() + "》发布失败！原因：列表模板未配置！";
			LogUtil.error(message);
			
		}

		if (StringUtils.isEmpty(catalog.getPath())) {
			message = "栏目《" + catalog.getName() + "》发布失败！原因：模板生成路径不存在！";
			LogUtil.error(message);
			
		}
		if (catalog.getIsLeaf() == 1) {
			// 单页栏目
			ContentsEntity content = this.contentsService.getCatalogOneChild(catalog.getId());//获取栏目下最新的一条内容
			staticOnlyContent(content, catalog, site);
			return ;
		}

		String filename = "";
		String catalogPath = "";

		Boolean isSave = false;
		if (StringUtils.isNotEmpty(catalog.getIndexTemplate())) {
			isSave = true;

			String separator = System.getProperty("file.separator");
			// 模板位置
			String templateFilepath= catalog.getIndexTemplate();
			templateFilepath = templateFilepath.replaceAll("/", separator + separator);
			templateFilepath=CmsConstants.getSiteTemplatePath(site) + templateFilepath;

			File file = new File(templateFilepath);
			if(!file.exists()){
				LogUtil.error("栏目《" + catalog.getName() + "》首页发布失败！原因：模板不存在"
						+ templateFilepath);
				return;
			}

			catalogPath = invoke(site, templateFilepath, catalog.getPath(), data, "index", null);

			if(StringUtils.isNotEmpty(site.getIsSwitch())  && site.getIsSwitch().equals("1")){
				catalog.setUrl(path+"/front/newsListController.do?newsList&catId="+catalog.getId());
			}else{
				catalog.setUrl(catalogPath);
			}
			catalog.setStaticUrl(catalogPath);

			catalog.setDynamicUrl(path+"/front/newsListController.do?newsList&catId="+catalog.getId());
			saveOrUpdate(catalog);
		}

		for (int i = 0; i < quickCount; i++) {
			try {
				data.put("pageindexF", i + 1);
				if (i == 0) {
					filename = "list";
				} else {
					filename = "list_" + (i + 1);
				}

				//生成rss文件
				String rssTemplate=catalog.getRssTemplate();
				if(org.apache.commons.lang3.StringUtils.isNotEmpty(rssTemplate)){
					String suffix = FileType.getSuffixByFilename(rssTemplate);

					String separator = System.getProperty("file.separator");
					// 模板位置
					rssTemplate = rssTemplate.replaceAll("/", separator + separator);
					rssTemplate=CmsConstants.getSiteTemplatePath(site) + rssTemplate;

					File file = new File(rssTemplate);
					if(!file.exists()){
						LogUtil.error("栏目rss《" + catalog.getName() + "》第" + (i + 1) + "页发布失败！原因：模板不存在"
								+ rssTemplate);
					}else{
						invoke(site, rssTemplate, catalog.getPath(), data, filename, suffix);
					}

				}
				// 分页
				TemplateData t = new TemplateData();
				t.setFirstFileName("list" + site.getStaticSuffix());
				t.setPageSize(catalog.getPagesize());
				t.setPageCount(quickCount);
				t.setTotal(total);
				t.setPageIndex(i+1);
				t.setOtherFileName("list_@INDEX" + site.getStaticSuffix());
				String pagebar = t.getPageBar(0);

				String separator = System.getProperty("file.separator");
				// 模板位置
				String templateFilepath= catalog.getListModel();
				templateFilepath = templateFilepath.replaceAll("/", separator + separator);
				templateFilepath=CmsConstants.getSiteTemplatePath(site) + templateFilepath;

				File file = new File(templateFilepath);
				if(!file.exists()){
					LogUtil.error("栏目《" + catalog.getName() + "》第" + (i + 1) + "页发布失败！原因：模板不存在"
							+ templateFilepath);
					continue;
				}

				data.put("pagebar", pagebar);
				if (i == 0 && !isSave) {
					catalogPath = invoke(site, templateFilepath, catalog.getPath(), data, filename, null);


					if(StringUtils.isNotEmpty(site.getIsSwitch())  && site.getIsSwitch().equals("1")){
						catalog.setUrl(path+"/front/newsListController.do?newsList&catId="+catalog.getId());


					}else{
						catalog.setUrl(catalogPath);

					}
					catalog.setStaticUrl(catalogPath);
					catalog.setDynamicUrl(path+"/front/newsListController.do?newsList&catId="+catalog.getId());
					saveOrUpdate(catalog);
				} else {
					invoke(site, templateFilepath, catalog.getPath(), data, filename, null);
				}
				message = "栏目《" + catalog.getName() + "》第" + (i + 1) + "页发布成功！";
			} catch (Exception e) {
				e.printStackTrace();
				message = "栏目《" + catalog.getName() + "》第" + (i + 1) + "页发布失败！原因：" + e.getMessage();
				LogUtil.error(message);
			}
		}
	}

	/**
	 * 仅仅发布一个栏目、及子栏目的列表页
	 *
	 * @param site
	 * @param catalog
	 * @param quickCount
	 * @throws Exception
	 */
	private void staticOnlyCatalogList(SiteEntity site, ContentCatEntity catalog, long quickCount) throws Exception {

		if(catalog==null){
			return;
		}

		Integer pagesize = catalog.getPagesize() == 0 ? 10 : catalog.getPagesize();

		List<String> catalogIds = null;
		if (0 == catalog.getIscatend()) {
			//当前栏目不是最末级目录才会有获取栏目下所有数据情况
			catalogIds = this.contentCatService.getOpenChildIdList(catalog.getPathids());//根据指定栏目查询出所有子集启用的栏目id集合
		}

		long contentCount=this.contentsService.getCatalogOpenContentCount(catalogIds);//获取栏目下允许查看的内容总数

		long total = 0;// 栏目中文章总数
		if (contentCount > 0 && pagesize > 0) {
			long pagecount = contentCount / pagesize;
			if (contentCount % pagesize != 0) {
				pagecount++;
			}

			if (quickCount == -1 || pagecount < quickCount) {
				// 查询栏目全部数据；
				total = contentCount;
			} else {
				pagecount = quickCount;
				total = pagecount * pagesize;
			}
			// 静态化列表页
			staticOnlyCatalogList(site, catalog, pagecount, total);

		} else {
			// 没有数据只有一页
			staticOnlyCatalogList(site, catalog, 1, total);
		}

	}

	
	/**
	 * 仅仅发布一篇内容
	 * 
	 * @param contentid
	 * @throws Exception
	 */
	private void staticOnlyContent(String contentid, ContentCatEntity catalog, SiteEntity site) throws Exception {
		ContentsEntity content = this.contentsService.getContensById(contentid);
		staticOnlyContent(content,catalog,site);
	}

	/**
	 * 发布多篇文章及它的所有父栏目列表页、和全部区块
	 * 
	 * @param articleids
	 * @throws Exception
	 */
	public void staticContents(SiteEntity site, ContentCatEntity catalog, String[] articleids) throws Exception {
		for (int i = 0; i < articleids.length; i++) {
			ContentsEntity content = contentsService.getContensById(articleids[i]);
			staticOnlyContent(content, catalog, site);
		}
		// 然后发布所属栏目列表
		staticOnlyCatalogList(site, catalog, 10);
		// 然后发布全部区块。
		staticSection(site);
	}

	/**
	 * 仅仅发布一篇内容
	 * 
	 * @param content
	 * @throws Exception
	 */
	private void staticOnlyContent(ContentsEntity content,ContentCatEntity catalog,SiteEntity site) throws Exception {
		
		if (ContentClassify.CONTENT_LINK.equals(content.getClassify())) {
			// 链接 
			return;
		}
		
		String filename = "";
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("contentF", content);
		data.put("contentidF", content.getId());
		data.put("site", site);
		data.put("contextpath", Globals.CONTEXTPATH);
		data.put("PageBreakBar", "");
		try {
			filename = content.getId().toString();
			data.put("catalogF", catalog);
			data.put("catalogidF", catalog.getId());
			if (catalog.getIsLeaf() == 1) {
				// 单页栏目
				filename = "index";
			}
			String templatePath = contentMng.getContentTemplatePath(content,catalog);
			
			if (StringUtils.isEmpty(templatePath)) {
				message = "文章《" + content.getTitle() + "》发布失败！原因：模板路径未配置！";
				LogUtil.error(message);
			}

			if (StringUtils.isEmpty(catalog.getPath())) {
				message = "文章《" + content.getTitle() + "》发布失败！原因：模板生成路径不存在！";
				LogUtil.error(message);
			}
			
			String separator = System.getProperty("file.separator");
			// 模板位置
			templatePath = templatePath.replaceAll("/", separator + separator);
			templatePath=CmsConstants.getSiteTemplatePath(site) + templatePath;

			File file = new File(templatePath);
			if(!file.exists()){
				LogUtil.error("文章《" + content.getTitle() + "》发布失败！原因：模板不存在"
						+ templatePath);
				return;
			}

			Map<String, Object> m = contentMng.getContent(content);
			if (MapUtils.isNotEmpty(m)) {
				data.putAll(m);
			}

			String staticfilePath = "";
			ArticleEntity art = (ArticleEntity) data.get("articleDataF");
			if (art != null && StringUtils.isNotEmpty(art.getContent())) {
				String[] datapage = art.getContent().split(CmsConstants.CONTENT_PAGE_CODE);
				if (datapage.length > 1) {

					String firstName = filename + site.getStaticSuffix();
					String otherName = filename + "_@INDEX" + site.getStaticSuffix();
					TemplateData td = new TemplateData();
					td.setPageCount(datapage.length);
					td.setFirstFileName(firstName);
					td.setTotal(datapage.length);
					td.setOtherFileName(otherName);

					for (int i = 0; i < datapage.length; i++) {
						ArticleEntity a = new ArticleEntity();
						MyBeanUtils.copyBeanNotNull2Bean(art, a);
						a.setContent(datapage[i]);
						data.put("articleDataF", a);

						td.setPageIndex(i+1);
						String html = td.getPageBreakBar(1);
						data.put("PageBreakBar", html);

						if (i == 0) {
							staticfilePath = invoke(site, templatePath, catalog.getPath(), data, filename, null);
						} else {
							invoke(site, templatePath, catalog.getPath(), data, filename + "_" + (i + 1), null);
						}
					}
				} else {
					staticfilePath = invoke(site, templatePath, catalog.getPath(), data, filename, null);
				}
			} else {
				staticfilePath = invoke(site, templatePath, catalog.getPath(), data, filename, null);
			}
			String path = Globals.CONTEXTPATH;
			
			if(StringUtils.isNotEmpty(site.getIsSwitch())  && site.getIsSwitch().equals("1")){
				content.setUrl(path+"/front/newsDeatilController.do?newsDeatil&contentId="+content.getId());
			}else{
				content.setUrl(staticfilePath);
				
			}
			
			content.setStaticUrl(staticfilePath);
			
			content.setDynamicUrl(path+"/front/newsDeatilController.do?newsDeatil&contentId="+content.getId());
			content.setWapurl("/" + catalog.getPath() + "wap_" + content.getId() + site.getStaticSuffix());
			if (null == content.getPublished()) {
				content.setPublished(new Date());
			}
			if(StringUtils.isEmpty(content.getPublishedby())){
				String realName=userService.getRealName(content.getCreatedby());//获取用户真实姓名，真实姓名为空则返回用户名
				content.setPublishedby(realName);
				content.setPublishedbyid(content.getCreatedby());
			}
			if(ContentStatus.CONTENT_COMMITTED.equals(content.getConstants())){
				content.setConstants(ContentStatus.CONTENT_PUBLISHED);
			}
			saveOrUpdate(content);

			if (catalog.getIsLeaf() == 1) {
				// 单页栏目
				
				
				if(StringUtils.isNotEmpty(site.getIsSwitch())  && site.getIsSwitch().equals("1")){
					catalog.setUrl(path+"/front/newsDeatilController.do?newsDeatil&contentId="+content.getId());
				}else{
					catalog.setUrl(staticfilePath);
					
				}
				catalog.setStaticUrl(staticfilePath);
				
			
				catalog.setDynamicUrl(path+"/front/newsDeatilController.do?newsDeatil&contentId="+content.getId());
				saveOrUpdate(catalog);
			}

			message = "文章《" + content.getTitle() + "》发布成功！";
		} catch (Exception e) {
			e.printStackTrace();
			message = "文章《" + content.getTitle() + "》发布失败！原因：" + e.getMessage();
			LogUtil.error(message);
			
		}

	}

	/**
	 * 发布一篇内容及它的所有父栏目列表页、和全部区块；
	 * 
	 * @param site
	 * @param catalogId
	 * @param contentid
	 * @throws Exception
	 */
	public void staticContent(SiteEntity site, String catalogId, String contentid) throws Exception {
		ContentCatEntity contentCat=this.contentCatService.getEntity(catalogId);
		// 发布内容页本身；
		staticOnlyContent(contentid,contentCat,site);

		// 然后发布所属栏目列表
		staticOnlyCatalogList(site, contentCat, 10);
		// 然后发布全部区块。
		staticSection(site);
	}

	/**
	 * 添加参数支持
	 * 
	 * @param templateFilepath
	 *            模板路径
	 * @param data
	 *            注入数据
	 * @param filename
	 *            生成文件名 不含后缀
	 * @param filePath
	 *            生成文件路径
	 * @throws Exception
	 */
	private String invoke(SiteEntity site, String templateFilepath, String filePath, Map<String, Object> data, String filename,
			String Suffix) throws Exception {
		if (MapUtils.isEmpty(data))
			data = new HashMap<String, Object>();
		data.put("newTag", new TagCreator());
		return generateFile(site, templateFilepath, data, filePath, filename, Suffix);
	}

	/**
	 * 生成创建静态文件
	 * 
	 * @param templateFilepath
	 *            模板路径
	 * @param data
	 *            注入数据
	 * @param filename
	 *            生成文件名 不含后缀
	 * @param filePath
	 *            生成文件路径
	 * 
	 * @throws Exception
	 * @return 生成路径 站点为根路径
	 */
	private String generateFile(SiteEntity site,String templateFilepath, Map<String, Object> data, String filePath, String filename,String Suffix) throws Exception {
		Writer out = null;
		String returnPath = "";
		String separator = System.getProperty("file.separator");
	
		String sitePath = site.getSitePath();// 站点路径
		if (org.apache.commons.lang3.StringUtils.isEmpty(Suffix)) {
			Suffix = site.getStaticSuffix();// 静态页面后缀;
		}
		File tempFile=FileUtils.getTmpFile();

		/*
		 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径
		 * 没有配置的话就读取工程 WebRoot/wwwroot目录
		 */
		String siteFullPath = "";
		String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径
		if (StringUtils.isNotEmpty(staticFilesPath)) {
			siteFullPath = staticFilesPath + separator + sitePath + separator;// 站点资源路径
		} else {
			String projectPath = SystemPath.getSysPath();// 工程根路径 webroot
			siteFullPath = projectPath + CmsConstants.SITE_STORAGE_PATH + separator + sitePath + separator;// 站点资源路径
		}

		Configuration cfg = new Configuration();
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");

		// 模板位置
		File file = new File(templateFilepath);

		if (StringUtils.isEmpty(filename)) {
			filename = FileUtils.getFilePrefix(file.getName()) + "_0";
		}

		// 根据类型指定生成位置
		String fileNamePath = siteFullPath + filename + Suffix;
		returnPath = separator + filename + Suffix;
		String oldpath = "";// 如果是文章就会有预览文件 如果有找到删除

		// 文件最终生成路径
		if (StringUtils.isNotEmpty(filePath)) {
			filePath = filePath.replaceAll("/", separator + separator);
			fileNamePath = siteFullPath + filePath + filename + Suffix;
			oldpath = siteFullPath + filePath + filename + CmsConstants.PREVIEW_ARTICLE_SUFFIX + Suffix;
			returnPath = separator + filePath + filename + Suffix;
		}

		String ftl = IOUtils.toString(new FileInputStream(file), "utf-8");
		String regStr = replaceHtmlTag(ftl, "#include", "<!--#include virtual=\"" + CmsConstants.INCLUDEPATH + "", Suffix
				+ "\"-->");

		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate", regStr);
		cfg.setTemplateLoader(stringLoader);
		Template template = cfg.getTemplate("myTemplate", "utf-8");

		String fileDir = StringUtils.substringBeforeLast(fileNamePath, separator);
		org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + separator));
		out = new OutputStreamWriter(new FileOutputStream(tempFile), "utf-8");

		template.process(data, out);
		out.flush();
		out.close();
		
		try {
			org.apache.commons.io.FileUtils.copyFile(tempFile, new File(fileNamePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		tempFile.delete();

		// 如果存在预览文件删除
		File oldFile = new File(oldpath);
		if (oldFile.exists()) {
			oldFile.delete();
		}

		if (filename.indexOf(CmsConstants.PREVIEW_ARTICLE_SUFFIX) == -1) {
			// 当不是生成的预览文件
			convertBig5(fileNamePath, filename);
		}
		returnPath = returnPath.replaceAll(separator + separator, "/");
		return returnPath;
	}
	
	/**
	 * 将html转换为繁体中文
	 * 
	 * @param filepath
	 * @param filename
	 */
	private void convertBig5(String filepath, String filename) {
		File file = new File(filepath);
		if (file.exists()) {
			String html = FileUtil.readText(file, "utf-8");
			String big5Html = Big5Convert.toTradition(html);

			big5Html = replaceHref(big5Html);

			String newFileName = Big5Convert.toTradition(filename);

			String big5FileName = filepath.replace(filename, newFileName + CmsConstants.BIG5);

			big5Html = replaceVirtual(big5Html);

			FileUtil.writeText(big5FileName, big5Html, "utf-8");
		}
	}

	private String replaceVirtual(String big5Html) {
		Pattern includePattern = Pattern.compile("\\s(virtual)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2", Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher m = includePattern.matcher(big5Html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(big5Html.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			String includeFile = m.group(3);
			includeFile = includeFile.substring(0, includeFile.lastIndexOf('.')) + CmsConstants.BIG5
					+ includeFile.substring(includeFile.lastIndexOf('.'));
			sb.append(" virtual=\"" + includeFile + "\"");
		}
		sb.append(big5Html.substring(lastEndIndex));
		big5Html = sb.toString();
		return big5Html;
	}

	private String replaceHref(String big5Html) {
		Pattern hrefPattern = Pattern.compile("\\s(href)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = hrefPattern.matcher(big5Html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(big5Html.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			String hrefFile = m.group(3);
			if (hrefFile.lastIndexOf('.') != -1 && hrefFile.lastIndexOf(".css") == -1) {
				hrefFile = hrefFile.substring(0, hrefFile.lastIndexOf('.')) + CmsConstants.BIG5
						+ hrefFile.substring(hrefFile.lastIndexOf('.'));
			}
			sb.append(" href=\"" + hrefFile + "\"");
		}
		sb.append(big5Html.substring(lastEndIndex));
		big5Html = sb.toString();
		return big5Html;
	}


	/**
	 * 
	 * 基本功能：替换指定的标签
	 * <p>
	 * 
	 * @param str
	 * @param beforeTag
	 *            要替换的标签
	 * @param startTag
	 *            新标签开始标记
	 * @param endTag
	 *            新标签结束标记
	 * @return String
	 * @如：替换img标签的src属性值为[img]属性值[/img]
	 */
	private String replaceHtmlTag(String str, String beforeTag, String startTag, String endTag) {
		String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = "\"([^\"]+)\"";
		String regxpForTaginfo = "([^\"]+)/([^\"]+)(?:\\.[a-z])";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Pattern patternForinfo = Pattern.compile(regxpForTaginfo);
		Matcher matcherForTag = patternForTag.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
			if (matcherForAttrib.find()) {
				// System.out.println("frlUrl-->"+matcherForAttrib.group(matcherForAttrib.groupCount()));
				Matcher matcherForinfo = patternForinfo.matcher(matcherForAttrib.group(1));

				if (matcherForinfo.find()) {
					// System.out.println("ftlInfo-->"+matcherForinfo.group(matcherForinfo.groupCount()));
					matcherForinfo.appendReplacement(sbreplace, startTag + matcherForinfo.group(matcherForinfo.groupCount()) + "_0"
							+ endTag);
				} else {
					matcherForAttrib.appendReplacement(sbreplace, startTag + matcherForAttrib.group(1) + endTag);
				}
			}
			matcherForTag.appendReplacement(sb, sbreplace.toString());
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 线程生成用于预览的文章页面
	 * 
	 * @param content
	 *            文章
	 * @throws Exception
	 */
	@Override
	public void staticContentActOnPreview(final ContentsEntity content) {
		ContextHolderUtils.getRequest().setAttribute("static2Html",true);
		if (content != null) {
			String filename = "";
			SiteEntity site = getEntity(SiteEntity.class, content.getSiteid());
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("contentF", content);
			data.put("site", site);
			data.put("contentidF", content.getId());
			data.put("contextpath", Globals.CONTEXTPATH);
			data.put("PageBreakBar", "");
			String templateFilepath = "";
			try {
				filename = content.getId() + CmsConstants.PREVIEW_ARTICLE_SUFFIX;
				ContentCatEntity catalog = contentMng.getEntity(ContentCatEntity.class, content.getCatid());
				data.put("catalogF", catalog);
				data.put("catalogidF", catalog.getId());
				templateFilepath = contentMng.getContentTemplatePath(content,catalog);
				if (StringUtils.isEmpty(templateFilepath)) {
					message = "文章《" + content.getTitle() + "》发布失败！原因：模板路径不存在！";
					//systemService.addLog(message, Globals.Log_Leavel_ERROR, Globals.Log_Type_OTHER);
					LogUtil.error(message);
					return;
				}

				if (StringUtils.isEmpty(catalog.getPath())) {
					message = "文章《" + content.getTitle() + "》发布失败！原因：模板生成路径不存在！";
					//systemService.addLog(message, Globals.Log_Leavel_ERROR, Globals.Log_Type_OTHER);
					LogUtil.error(message);
					return;
				}
				
				String separator = System.getProperty("file.separator");
				// 模板位置
				templateFilepath = templateFilepath.replaceAll("/", separator + separator);
				templateFilepath=CmsConstants.getSiteTemplatePath(site) + templateFilepath;

				File file = new File(templateFilepath);
				if(!file.exists()){
					LogUtil.error("文章《" + content.getTitle() + "》发布失败！原因：模板不存在" + templateFilepath);
					return;
				}

				Map<String, Object> m = contentMng.getContent(content);
				if (MapUtils.isNotEmpty(m)) {
					data.putAll(m);
				}
				data.put("newTag", new TagCreator());
				String filePath = catalog.getPath();
				String returnPath = "";
				// 从Session中获取站点
				String sitePath = site.getSitePath();// 站点路径
				String staticSuffix = site.getStaticSuffix();// 静态页面后缀

				String staticfilePath = "";
				ArticleEntity art = (ArticleEntity) data.get("articleDataF");
				if (art != null && StringUtils.isNotEmpty(art.getContent())) {
					String[] datapage = art.getContent().split(
							"<div style=\"page-break-after: always;\"><span style=\"display: none;\">&nbsp;</span></div>");
					if (datapage.length > 1) {

						String firstName = filename + site.getStaticSuffix();
						String otherName = filename + "_@INDEX" + site.getStaticSuffix();
						TemplateData td = new TemplateData();
						td.setPageCount(datapage.length);
						td.setFirstFileName(firstName);
						td.setTotal(datapage.length);
						td.setOtherFileName(otherName);

						for (int i = 0; i < datapage.length; i++) {
							ArticleEntity a = new ArticleEntity();
							MyBeanUtils.copyBeanNotNull2Bean(art, a);
							a.setContent(datapage[i]);
							data.put("articleDataF", a);

							td.setPageIndex(i+1);
							String html = td.getPageBreakBar(1);
							data.put("PageBreakBar", html);

							if (i == 0) {
								staticfilePath = invoteThread(site,filename, data, templateFilepath, filePath, returnPath, separator, sitePath,
										staticSuffix);
							} else {
								invoteThread(site,filename+ "_" + (i + 1), data, templateFilepath, filePath, returnPath, separator, sitePath, staticSuffix);
							}
						}
					} else {
						staticfilePath = invoteThread(site,filename, data, templateFilepath, filePath, returnPath, separator, sitePath,
								staticSuffix);
					}
				} else {
					staticfilePath = invoteThread(site,filename, data, templateFilepath, filePath, returnPath, separator, sitePath, staticSuffix);
				}

				staticfilePath = staticfilePath.replaceAll(separator + separator, "/");
				
				String path =Globals.CONTEXTPATH;
				
				if(StringUtils.isNotEmpty(site.getIsSwitch())  && site.getIsSwitch().equals("1")){
					content.setUrl(path+"/front/newsDeatilController.do?newsDeatil&contentId="+content.getId());
				}else{
					content.setUrl(staticfilePath);
					
				}
				content.setStaticUrl(staticfilePath);
				content.setDynamicUrl(path+"/front/newsDeatilController.do?newsDeatil&contentId="+content.getId());
				contentMng.saveOrUpdate(content);
				message = "文章《" + content.getTitle() + "》发布成功！";
//				LogUtil.info(message);
			} catch (Exception e) {
				message = "内容id："+content.getId()+"\t《" + content.getTitle() + "》发布失败！\t模板路径："+templateFilepath+"\t原因：" + e.getMessage();
				LogUtil.error(message,e);
			}
		}

	}
	
	private String invoteThread(SiteEntity site,String filename, Map<String, Object> data, String templateFilepath, String filePath, String returnPath,
			String separator, String sitePath, String staticSuffix) throws Exception {
		Writer out;

		/*
		 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径
		 * 没有配置的话就读取工程 WebRoot/wwwroot目录
		 */
		String siteFullPath = "";
		String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径
		if (StringUtils.isNotEmpty(staticFilesPath)) {
			siteFullPath = staticFilesPath + separator + sitePath + separator;// 站点资源路径
		} else {
			String projectPath = SystemPath.getSysPath();// 工程根路径
															// webroot
			siteFullPath = projectPath + CmsConstants.SITE_STORAGE_PATH + separator + sitePath + separator;// 站点资源路径
		}

		Configuration cfg = new Configuration();
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");

		// 模板位置
		File file = new File(templateFilepath);

		if (StringUtils.isEmpty(filename)) {
			filename = FileUtils.getFilePrefix(file.getName()) + "_0";
		}

		// 根据类型指定生成位置
		String fileNamePath = siteFullPath + filename + staticSuffix;
		returnPath = separator + filename + staticSuffix;
		String oldpath = "";// 如果是文章就会有预览文件 如果有找到删除

		// 文件最终生成路径
		if (filePath != null) {
			filePath = filePath.replaceAll("/", separator + separator);
			fileNamePath = siteFullPath + filePath + filename + staticSuffix;
			oldpath = siteFullPath + filePath + filename + CmsConstants.PREVIEW_ARTICLE_SUFFIX + staticSuffix;
			returnPath = separator + filePath + filename + staticSuffix;
		}

		String ftl = IOUtils.toString(new FileInputStream(file),"utf-8");
		String regStr = replaceHtmlTag(ftl, "#include", "<!--#include virtual=\"" + CmsConstants.INCLUDEPATH + "", staticSuffix
				+ "\"-->");

		File tempFile=FileUtils.getTmpFile();

		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate", regStr);
		cfg.setTemplateLoader(stringLoader);
		Template template = cfg.getTemplate("myTemplate", "utf-8");

		String fileDir = StringUtils.substringBeforeLast(fileNamePath, separator);
		org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + separator));
		out = new OutputStreamWriter(new FileOutputStream(tempFile), "utf-8");

		template.process(data, out);
		out.flush();
		out.close();

		String html = FileUtil.readText(tempFile, "utf-8");
		FileUtil.writeText(fileNamePath, html, "utf-8");
		tempFile.delete();

		// 如果存在预览文件删除
		File oldFile = new File(oldpath);
		if (oldFile.exists()) {
			oldFile.delete();
		}

		if (filename.indexOf(CmsConstants.INCLUDEPATH) == -1) {
			// 当不是生成的预览文件
			convertBig5(fileNamePath, filename);
		}
		return returnPath;
	}

	public class StaticThread extends Thread {
		private ContentsEntity content;
		private IContentTagMng contentMng;
		private SystemService systemService;
		private HttpSession session;
		private SiteEntity site;

		public StaticThread(ContentsEntity article, IContentTagMng articleMng, SystemService systemService, HttpServletRequest request, SiteEntity site) {
			this.content = article;
			this.contentMng = articleMng;
			this.systemService = systemService;
			this.session = request.getSession();
			this.site=site;
		}

		@Override
		public void run() {
			String filename = "";

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("contentF", content);
			data.put("site", site);
			data.put("contentidF", content.getId());
			data.put("contextpath", Globals.CONTEXTPATH);
			try {
				filename = content.getId().toString() + CmsConstants.PREVIEW_ARTICLE_SUFFIX;
				ContentCatEntity catalog = contentMng.getEntity(ContentCatEntity.class, content.getCatid());

				data.put("catalogF", catalog);
				data.put("catalogidF", catalog.getId());
				
				String templateFilepath = contentMng.getContentTemplatePath(content,catalog);
				if (StringUtils.isEmpty(templateFilepath)) {
					message = "文章《" + content.getTitle() + "》发布失败！原因：模板路径不存在！";
					systemService.addLog(message, Globals.Log_Leavel_ERROR, Globals.Log_Type_OTHER);
					LogUtil.error(message);
					return;
				}

				if (StringUtils.isEmpty(catalog.getPath())) {
					message = "文章《" + content.getTitle() + "》发布失败！原因：模板生成路径不存在！";
					systemService.addLog(message, Globals.Log_Leavel_ERROR, Globals.Log_Type_OTHER);
					LogUtil.error(message);
					return;
				}
				
				String separator = System.getProperty("file.separator");
				// 模板位置
				templateFilepath = templateFilepath.replaceAll("/", separator + separator);
				templateFilepath=CmsConstants.getSiteTemplatePath(site) + templateFilepath;

				File file = new File(templateFilepath);
				if(!file.exists()){
					LogUtil.error("文章《" + content.getTitle() + "》发布失败！原因：模板不存在" + templateFilepath);
					return;
				}

				Map<String, Object> m = contentMng.getContent(content);
				if (MapUtils.isNotEmpty(m)) {
					data.putAll(m);
				}
				data.put("newTag", new TagCreator());
				String filePath = catalog.getPath();
				String returnPath = "";
				// 从Session中获取站点
				SiteEntity site = ClientManager.getInstance().getClient(session.getId()).getSite();
				String sitePath = site.getSitePath();// 站点路径
				String staticSuffix = site.getStaticSuffix();// 静态页面后缀

				String staticfilePath = "";
				ArticleEntity art = (ArticleEntity) data.get("articleDataF");
				if (art != null && StringUtils.isNotEmpty(art.getContent())) {
					String[] datapage = art.getContent().split(
							"<div style=\"page-break-after: always;\"><span style=\"display: none;\">&nbsp;</span></div>");
					if (datapage.length > 1) {

						String firstName = filename + site.getStaticSuffix();
						String otherName = filename + "_@INDEX" + site.getStaticSuffix();
						TemplateData td = new TemplateData();
						td.setPageCount(datapage.length);
						td.setFirstFileName(firstName);
						td.setTotal(datapage.length);
						td.setOtherFileName(otherName);

						for (int i = 0; i < datapage.length; i++) {
							ArticleEntity a = new ArticleEntity();
							MyBeanUtils.copyBeanNotNull2Bean(art, a);
							a.setContent(datapage[i]);
							data.put("articleDataF", a);

							td.setPageIndex(i);
							String html = td.getPageBreakBar(1);
							data.put("PageBreakBar", html);

							if (i == 0) {
								staticfilePath = invoteThread(filename, data, templateFilepath, filePath, returnPath, separator, sitePath,
										staticSuffix);
							} else {
								invoteThread(filename+ "_" + (i + 1), data, templateFilepath, filePath, returnPath, separator, sitePath, staticSuffix);
							}
						}
					} else {
						staticfilePath = invoteThread(filename, data, templateFilepath, filePath, returnPath, separator, sitePath,
								staticSuffix);
					}
				} else {
					staticfilePath = invoteThread(filename, data, templateFilepath, filePath, returnPath, separator, sitePath, staticSuffix);
				}

				staticfilePath = staticfilePath.replaceAll(separator + separator, "/");
				String path = Globals.CONTEXTPATH;
				
				if(StringUtils.isNotEmpty(site.getIsSwitch())  && site.getIsSwitch().equals("1")){
					content.setUrl(path+"/front/newsDeatilController.do?newsDeatil&contentId="+content.getId());
				}else{
					content.setUrl(staticfilePath);
					
				}
				content.setStaticUrl(staticfilePath);
				content.setDynamicUrl(path+"/front/newsDeatilController.do?newsDeatil&contentId="+content.getId());
				contentMng.saveOrUpdate(content);
				message = "文章《" + content.getTitle() + "》发布成功！";
//				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文章《" + content.getTitle() + "》发布失败！原因：" + e.getMessage();
				LogUtil.error(message);
			}
		}

		private String invoteThread(String filename, Map<String, Object> data, String templateFilepath, String filePath, String returnPath,
				String separator, String sitePath, String staticSuffix) throws Exception {
			Writer out;
			try {

				/*
				 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径
				 * 没有配置的话就读取工程 WebRoot/wwwroot目录
				 */
				String siteFullPath = "";
				String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径
				if (StringUtils.isNotEmpty(staticFilesPath)) {
					siteFullPath = staticFilesPath + separator + sitePath + separator;// 站点资源路径
				} else {
					String projectPath = SystemPath.getSysPath();// 工程根路径
																	// webroot
					siteFullPath = projectPath + CmsConstants.SITE_STORAGE_PATH + separator + sitePath + separator;// 站点资源路径
				}

				Configuration cfg = new Configuration();
				cfg.setLocale(Locale.CHINA);
				cfg.setDefaultEncoding("UTF-8");

				// 模板位置
				File file = new File(templateFilepath);

				if (StringUtils.isEmpty(filename)) {
					filename = FileUtils.getFilePrefix(file.getName()) + "_0";
				}

				// 根据类型指定生成位置
				String fileNamePath = siteFullPath + filename + staticSuffix;
				returnPath = separator + filename + staticSuffix;
				String oldpath = "";// 如果是文章就会有预览文件 如果有找到删除

				// 文件最终生成路径
				if (filePath != null) {
					filePath = filePath.replaceAll("/", separator + separator);
					fileNamePath = siteFullPath + filePath + filename + staticSuffix;
					oldpath = siteFullPath + filePath + filename + CmsConstants.PREVIEW_ARTICLE_SUFFIX + staticSuffix;
					returnPath = separator + filePath + filename + staticSuffix;
				}

				String ftl = IOUtils.toString(new FileInputStream(file),"utf-8");
				String regStr = replaceHtmlTag(ftl, "#include", "<!--#include virtual=\"" + CmsConstants.INCLUDEPATH + "", staticSuffix
						+ "\"-->");

				StringTemplateLoader stringLoader = new StringTemplateLoader();
				stringLoader.putTemplate("myTemplate", regStr);
				cfg.setTemplateLoader(stringLoader);
				Template template = cfg.getTemplate("myTemplate", "utf-8");

				String fileDir = StringUtils.substringBeforeLast(fileNamePath, separator);
				org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + separator));
				out = new OutputStreamWriter(new FileOutputStream(fileNamePath), "utf-8");

				template.process(data, out);
				out.flush();
				out.close();

				// 如果存在预览文件删除
				File oldFile = new File(oldpath);
				if (oldFile.exists()) {
					oldFile.delete();
				}

				if (filename.indexOf(CmsConstants.INCLUDEPATH) == -1) {
					// 当不是生成的预览文件
					convertBig5(fileNamePath, filename);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return returnPath;
		}

	}

	/**
	 * 线程生成用于预览的文章页面
	 * 
	 * @param contentid
	 *            文章id
	 * @throws Exception
	 */
	@Override
	public void staticContentActOnPreview(String contentid) {
		if (contentid != null) {
			ContentsEntity content = getEntity(ContentsEntity.class, contentid);
			if (content != null) {
				staticContentActOnPreview(content);
			}
		}
	}

	/**
	 * 生成首页静态文件
	 * 
	 * @throws Exception
	 */
	public void staticIndex(SiteEntity site) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("contextpath", Globals.CONTEXTPATH);
		data.put("site", site);	

		if (StringUtils.isEmpty(site.getIndexTemplate())) {
			message = "首页发布失败！原因：模板路径不存在！";
			LogUtil.error(message);
			return;
		}
		
		String separator = System.getProperty("file.separator");
		// 模板位置
		String templatePath=null;
		if(site.getIsSwitch().equals("1") && StringUtils.isNotEmpty(site.getIsSwitch())){
			templatePath="static/index.html";
			templatePath = templatePath.replaceAll("/", separator + separator);
			templatePath=CmsConstants.getWWWROOT() + templatePath;
		}else{
			
			templatePath=site.getIndexTemplate();
			templatePath = templatePath.replaceAll("/", separator + separator);
			templatePath=CmsConstants.getSiteTemplatePath(site) + templatePath;
		}

		File file = new File(templatePath);
		if(!file.exists()){
			LogUtil.error("首页发布失败！原因：模板不存在" + templatePath);
			return;
		}

		try {
				invoke(site, templatePath, null, data, "index", null);
			
			message = "首页发布成功！";
		} catch (Exception e) {
			e.printStackTrace();
			message = "首页发布失败！原因：" + e.getMessage();
			LogUtil.error(message);

		}
	}

}

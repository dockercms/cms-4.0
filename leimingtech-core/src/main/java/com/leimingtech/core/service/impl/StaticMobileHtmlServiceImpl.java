package com.leimingtech.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.ContentLinkMobileEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TagCreator;
import com.leimingtech.core.service.IArticleMobileTagMng;
import com.leimingtech.core.service.StaticMobileHtmlServiceI;
import com.leimingtech.core.util.FileUtils;
import com.leimingtech.core.util.PathFormat;
import com.leimingtech.core.util.PlatFormUtil;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("staticMobileImpl")
@Transactional
public class StaticMobileHtmlServiceImpl extends CommonServiceImpl implements StaticMobileHtmlServiceI {

	@Override
	public void staticMobileArticle(SiteEntity site, ContentsMobileEntity content, MobileChannelEntity mobileChannel) throws Exception {
		if (content == null) {
			throw new IllegalArgumentException("内容空值,请传入正确的内容实体");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		String template = "";

		String contentid = content.getId();
		String modelType = content.getClassify();
		IArticleMobileTagMng articleMobileTagMng = (IArticleMobileTagMng) PlatFormUtil.getInterface("articleMobileTagMng");

		if (ContentClassify.CONTENT_VIDEO.equals(modelType)) {
			template = "mobile/video_detail.html";
		}

		if (ContentClassify.CONTENT_ACTIVITY.equals(modelType)) {
			// 活动
			template = "mobile/activity_detail.html";
		}

		if (ContentClassify.CONTENT_ARTICLE.equals(modelType)) {
			// 文章
			template = "mobile/news_detail.html";
		}

		if (ContentClassify.CONTENT_INTERVIEW.equals(modelType)) {
			// 访谈
			template = "mobile/interview_detail.html";
		}

		if (ContentClassify.CONTENT_LINK.equals(modelType)) {
			// 链接
//			template = "mobile/link_detail.html";
			if (content.getPublished() == null) {
				content.setPublished(new Date());
			}
			
			Map<String,Object> linkData=articleMobileTagMng.getContent(content);
			
			if(!MapUtils.isEmpty(linkData)){
				ContentLinkMobileEntity link=(ContentLinkMobileEntity)linkData.get("linkF");
				if(link!=null&&org.apache.commons.lang3.StringUtils.isNotEmpty(link.getLinkurl())){
					content.setUrl(link.getLinkurl());
				}
			}
			
			saveOrUpdate(content);
			return;
		}

		if (ContentClassify.CONTENT_PICTUREGROUP.equals(modelType)) {
			// 组图
			template = "mobile/picture_detail.html";
		}

		if (ContentClassify.CONTENT_SPECIAL.equals(modelType)) {
			// 专题
			template = "mobile/special_detail.html";
		}

		if (ContentClassify.CONTENT_SURVEY.equals(modelType)) {
			// 调查
			template = "mobile/survey_detail.html";
		}

		if (ContentClassify.CONTENT_VOTE.equals(modelType)) {
			// 投票
			template = "mobile/vote_detail.html";
		}

		data.putAll(articleMobileTagMng.getContent(content));
		String url = invoke(site, template, "mobile/", data, "mobile_" + content.getId());
		content.setUrl(url);
		if (content.getPublished() == null) {
			content.setPublished(new Date());
		}
		saveOrUpdate(content);
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
	private String invoke(SiteEntity site, String templateFilepath, String filePath, Map<String, Object> data, String filename)
			throws Exception {
		if (MapUtils.isEmpty(data))
			data = new HashMap<String, Object>();

		if (StringUtils.isEmpty(templateFilepath))
			throw new IllegalArgumentException("模板地址空值，请配置模板路径后重新生成");

		String siteTemplatePath = CmsConstants.getSiteTemplatePath(site);
		String sitePath = CmsConstants.getSitePath(site);

		String staticSuffix = site.getStaticSuffix();// 静态页面后缀
		if (StringUtils.isEmpty(staticSuffix)) {
			staticSuffix = ".shtml";
		}

		File templateFile = new File(PathFormat.format(siteTemplatePath + templateFilepath));
		if (!templateFile.exists())
			throw new IllegalArgumentException("模板路径找不到，请配置正确的模板路径后重新生成");

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

		return "/"+returnPath;
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
	private String generateFile(File templateFile, Map<String, Object> data, String generateFilePath) throws Exception {
		Writer out = null;
		String path = "";
		try {
			Configuration cfg = new Configuration();
			cfg.setLocale(Locale.CHINA);
			cfg.setDefaultEncoding("UTF-8");
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate("myTemplate", IOUtils.toString(new FileInputStream(templateFile),"utf-8"));
			cfg.setTemplateLoader(stringLoader);
			Template template = cfg.getTemplate("myTemplate", "utf-8");

			String fileDir = StringUtils.substringBeforeLast(generateFilePath, "/");
			org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + "/"));
			out = new OutputStreamWriter(new FileOutputStream(generateFilePath), "utf-8");
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return path;
	}

	@Override
	public void staticAllMobile(SiteEntity site) throws Exception {
		List<ContentsMobileEntity> list = findByProperty(ContentsMobileEntity.class, "siteid", site.getId());
		for (ContentsMobileEntity contentsMobile : list) {
			MobileChannelEntity mobileChannel = getEntity(MobileChannelEntity.class, contentsMobile.getCatid());
			staticMobileArticle(site, contentsMobile, mobileChannel);
		}
	}
}

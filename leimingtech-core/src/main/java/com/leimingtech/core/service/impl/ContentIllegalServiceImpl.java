package com.leimingtech.core.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.ContentStatus;
import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.ConstantsServiceI;
import com.leimingtech.core.service.ContentIllegalServiceI;
import com.leimingtech.core.service.IContentTagMng;
import com.leimingtech.core.util.FileUtils;

/**
 * 非法内容管理接口实现
 * 
 * @author liuzhen 2014年8月20日 12:14:21
 * 
 */
@Service("contentIllegalServiceImpl")
@Transactional
public class ContentIllegalServiceImpl extends CommonServiceImpl implements ContentIllegalServiceI {

	@Autowired
	private IContentTagMng contentMng;

	@Autowired
	private ConstantsServiceI constantsService;// 内容状态管理

	/**
	 * 删除内容页面文件
	 * 
	 * @param content
	 */
	@Override
	public void delContentHtmlFile(ContentsEntity content) {
		if (null == content)
			return;

		SiteEntity site = getEntity(SiteEntity.class, content.getSiteid());
		if (site == null)
			return;

		String staticSuffix = site.getStaticSuffix();// 静态页面后缀
		if (StringUtils.isEmpty(staticSuffix)) {
			staticSuffix = ".shtml";
		}

		String filename = "";
		filename = content.getId().toString();

		String catid=content.getCatid();
		if(StringUtils.isBlank(catid)){
			//栏目不存在无法正常删除静态页面
			return;
		}

		ContentCatEntity catalog = getEntity(ContentCatEntity.class, content.getCatid());
		String catalogPath = catalog.getPath();
		if (catalog.getIsLeaf() == 1) {
			// 单页栏目
			filename = "index";
		}

		Map<String, Object> m = contentMng.getContent(content);
		ArticleEntity art = (ArticleEntity) m.get("articleDataF");
		String sitePath = CmsConstants.getSitePath(site);
		removeFile(sitePath, catalogPath, filename, content.getId(), staticSuffix, null,content.getTitle());
		constantsService.downContent(content.getId().toString(), ContentStatus.CONTENT_RECALL);// 改成下线状态
	}

	/**
	 * 删除文件
	 * 
	 * @param sitepath
	 * @param catalogPath
	 * @param filename
	 * @param contentid
	 * @param staticSuffix
	 */
	private void removeFile(String sitePath, String catalogPath, String filename, String contentid, String staticSuffix, Integer pageindex,String title) {
		String wapHtmlName = "";
		String htmlName = "";
		String htmlBig5Name = "";
		String htmlViewBig5name = "";
		String twoCodePng = "";
		if (null == pageindex) {
			wapHtmlName = sitePath + catalogPath + "wap_" + contentid + staticSuffix;
			htmlName = sitePath + catalogPath + filename + staticSuffix;
			htmlBig5Name = sitePath + catalogPath + filename + CmsConstants.BIG5 + staticSuffix;
			htmlViewBig5name = sitePath + catalogPath + filename + CmsConstants.PREVIEW_ARTICLE_SUFFIX + CmsConstants.BIG5 + staticSuffix;
		} else {
			htmlName = sitePath + catalogPath + filename + "_" + pageindex + staticSuffix;
			wapHtmlName = sitePath + catalogPath + "wap_" + pageindex + contentid + CmsConstants.BIG5 + staticSuffix;
			htmlBig5Name = sitePath + catalogPath + "wap_" + pageindex + contentid + staticSuffix;
		}
		twoCodePng = sitePath+"upload/twocode/"+title+"_"+contentid+".png" ;
		FileUtils.delete(wapHtmlName);
		FileUtils.delete(htmlName);
		FileUtils.delete(htmlBig5Name);
		FileUtils.delete(htmlViewBig5name);
		FileUtils.delete(twoCodePng);
	}

	/**
	 * 删除多个内容页面文件
	 * 
	 * @param contentList
	 */
	@Override
	public void delContentHtmlFile(List<ContentsEntity> contentList) {
		if (null == contentList || contentList.size() == 0)
			return;

		for (int i = 0; i < contentList.size(); i++) {
			delContentHtmlFile(contentList.get(i));
		}
	}

	/**
	 * 根据内容id删除页面文件
	 * 
	 * @param id
	 */
	@Override
	public void delContentHtmlFile(Integer id) {
		if (null == id)
			return;

		ContentsEntity content = getEntity(ContentsEntity.class, id);
		delContentHtmlFile(content);
	}

}

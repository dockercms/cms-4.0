package com.leimingtech.cms.controller.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.controller.contents.ContentClassify;
import com.leimingtech.cms.controller.contents.ContentsbaseController;
import com.leimingtech.cms.net.content.ContentExtractorUtil;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentExtractoruleEntity;
import com.leimingtech.core.entity.ContentTagEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SourceEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.service.ArticleServiceI;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.ContentTagServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.service.ModelManageServiceI;
import com.leimingtech.core.service.SourceServiceI;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * @Title: Controller
 * @Description: 文章
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-22 10:29:49
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/articleController")
public class ArticleController extends ContentsbaseController {
	
	@Autowired
	private ArticleServiceI articleService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private ModelManageServiceI modelManageService;
	@Autowired
	private ModelItemServiceI modelItemService;
	@Autowired
	private ContentTagServiceI contentTagService;
	@Autowired
	private SourceServiceI sourceService;
	/**栏目管理接口*/
	@Autowired
	private ContentCatServiceI contentCatService;
	private String message;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 文章添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust) {
		String contentCatId = reqeust.getParameter("contentCatId");
		String modelsId = reqeust.getParameter("modelsId");
		// 当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		// 区分添加/编辑页面
		String flag = "add";
		ContentCatEntity contentCat = contentCatService.getEntity(contentCatId);
		ModelManageEntity modelManage = null;
		
		//自定义字段
		if(StringUtils.isNotEmpty(contentCat.getJsonid())) {
			modelManage = modelManageService.getEntity(contentCat.getJsonid());
		}
		List<ModelItemEntity> modelItemList = new ArrayList<ModelItemEntity>();
		if(modelManage != null) {
			modelItemList = modelItemService.findByModelId(modelManage.getId());
		}
		
		List<ContentTagEntity> contentTagList = contentTagService.loadAll(ContentTagEntity.class); // 获取Tags标签内容
		List<SourceEntity> sourceEntityList = sourceService.loadAll(SourceEntity.class); // 获取来源内容
		Map<String , Object> m = new HashMap<String , Object>();
		String sourceStr = "";
		for(int i = 0 ; i < sourceEntityList.size() ; i++) {
			sourceStr += sourceEntityList.get(i).getName() + ",";
		}
		// 从数据字典中获取内容标记
		List<TSTypegroup> typeGroupList = articleService.findByProperty(TSTypegroup.class , "typegroupcode" , "content_mark");
		List<TSType> typeList = new ArrayList<TSType>();
		if(typeGroupList.size() != 0) {
			typeList = typeGroupList.get(0).getTSTypes();
		}
		ContentsEntity contents = new ContentsEntity();
		// 设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
		contents.setClassify(ContentClassify.CONTENT_ARTICLE);
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(sourceStr)) {
			m.put("sourceStr" , sourceStr.substring(0 , sourceStr.length() - 1));
		} else {
			m.put("sourceStr" , CmsConstants.DEFAULT_SOURCE);
		}
		SiteEntity site =PlatFormUtil.getSessionSite();
		m.put("site", site);
		m.put("page" , new ArticleEntity());
		m.put("contents" , contents);
		m.put("contentCat" , contentCat);
		m.put("modelsId" , modelsId);
		m.put("typeList" , typeList);
		m.put("modelItemList" , modelItemList);
		m.put("flag" , flag);
		m.put("contentTagList" , contentTagList);
		m.put("sourceEntityList" , sourceEntityList);
		m.put("markpeople" , PlatFormUtil.getSessionUser().getUserName());
		m.put("temporary" , temporary);
		m.put("classify" , ContentClassify.CONTENT_ARTICLE);
		m.put("sessionId" , reqeust.getSession().getId());
		m.put("memberinfo" , PlatFormUtil.getSessionUser());
		m.put("contentCattreeJsonData", contentCatService.getContentCatTreeData().toString());
		return new ModelAndView("cms/article/article_open" , m);
	}
	
	/**
	 * 文章附件div
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadAccessory")
	public ModelAndView loadAccessory(HttpServletRequest request) {
		String contentId = request.getParameter("contentId");
		// //临时给附件中的id赋值为当前毫秒数
		String temporary = request.getParameter("temporary");
		Map<String , Object> m = articleService.loadAccessory(contentId , temporary);
		
		return new ModelAndView("cms/accessory/accessoryRefrech" , m);
	}


	
	/**
	 * 网编工具一键抓取
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "networkActicleEdit")
	public ModelAndView networkActicleEdit(HttpServletRequest request) {
		Map<String , Object> m = new HashMap<String , Object>();
		try {
			String contentCatId = request.getParameter("contentCatId");
			String modelsId = request.getParameter("modelsId");
			// 当前毫秒数
			String temporary = String.valueOf(System.currentTimeMillis());
			// 区分添加/编辑页面
			String flag = "add";
			ContentCatEntity contentCat = contentsService.get(ContentCatEntity.class , contentCatId);
			List<ContentTagEntity> contentTagList = contentTagService.loadAll(ContentTagEntity.class); // 获取Tags标签内容
			List<SourceEntity> sourceEntityList = sourceService.loadAll(SourceEntity.class); // 获取来源内容
			String sourceStr = "";
			for(int i = 0 ; i < sourceEntityList.size() ; i++) {
				sourceStr += sourceEntityList.get(i).getName() + ",";
			}
			// 从数据字典中获取内容标记
			List<TSTypegroup> typeGroupList = articleService.findByProperty(TSTypegroup.class , "typegroupcode" , "content_mark");
			List<TSType> typeList = new ArrayList<TSType>();
			if(typeGroupList.size() != 0) {
				typeList = typeGroupList.get(0).getTSTypes();
			}
			ContentsEntity contents = new ContentsEntity();
			// 设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
			contents.setClassify(ContentClassify.CONTENT_ARTICLE);
			if(org.apache.commons.lang3.StringUtils.isNotEmpty(sourceStr)) {
				m.put("sourceStr" , sourceStr.substring(0 , sourceStr.length() - 1));
			} else {
				m.put("sourceStr" , CmsConstants.DEFAULT_SOURCE);
			}
			m.put("markpeople" , PlatFormUtil.getSessionUser().getUserName());
			m.put("contentCat" , contentCat);
			m.put("modelsId" , modelsId);
			m.put("typeList" , typeList);
			m.put("flag" , flag);
			m.put("contentTagList" , contentTagList);
			m.put("sourceEntityList" , sourceEntityList);
			m.put("temporary" , temporary);
			m.put("classify" , ContentClassify.CONTENT_ARTICLE);
			m.put("sessionId" , request.getSession().getId());
			m.put("memberinfo" , PlatFormUtil.getSessionUser());
			// 抓取当前浏览网页主体内容
			ContentExtractorUtil contentExtractor = null;
			ArrayList<String> imagesList = new ArrayList<String>();
			ArticleEntity article = new ArticleEntity();
			try {
				String source = request.getParameter("source");
				String sourcetitle = request.getParameter("sourcetitle");
				String tags = request.getParameter("tags");
				String characterEncoding = request.getCharacterEncoding();
				if(!characterEncoding.toUpperCase().equals("UTF-8")) {
					sourcetitle = ContentExtractorUtil.decode(sourcetitle);
					tags = ContentExtractorUtil.decode(tags);
				}
				contents.setTitle(sourcetitle);// 原网页标题
				contents.setSubtitle(sourcetitle);// 设置短标题
				contents.setTags(tags);// 设置标签
				ContentExtractoruleEntity cere = ContentExtractorUtil.isHaveRule(source);
				String subjectHtml = "";
				if(cere != null) {
					// 尝试使用规则抓取内容
					subjectHtml = ContentExtractorUtil.contentExtratorule(source , cere);
					contentExtractor = ContentExtractorUtil.getContentExtractorByHtml(subjectHtml);
					if(contentExtractor.getHtml().equals("")) {
						contentExtractor.setHtml(subjectHtml);
					}
					ContentExtractorUtil.autoPaging(source , cere , contentExtractor);// 尝试翻页
					subjectHtml = contentExtractor.getHtml().equals("") ? subjectHtml : contentExtractor.getHtml();
				}
				if(StringUtils.isEmpty(subjectHtml)) {
					LogUtil.info("需要抓取的网站未配置规则或已配置规则但是没抓到数据即将尝试使用通用抓取方式抓取数据\n"+source);
					// 如果需要抓取的网站未配置规则或已配置规则但是没抓到数据就尝试使用通用抓取方式抓取数据
					contentExtractor = ContentExtractorUtil.getContentExtractorByURL(source);
					ContentExtractorUtil.autoPaging(source , contentExtractor);// 尝试翻页
					subjectHtml = contentExtractor.getHtml();
				}
				subjectHtml = ContentExtractorUtil.filterDom(subjectHtml);
				subjectHtml = ContentExtractorUtil.amendmentImgSrc(source , subjectHtml);
				article.setContent(subjectHtml);// 原网页主体内容
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(contentExtractor != null) {
				imagesList.addAll(contentExtractor.getImagesList());
			}
			m.put("allImage" , imagesList);
			m.put("page" , article);
			m.put("contents" , contents);
			m.put("contentCattreeJsonData", contentCatService.getContentCatTreeData().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("cms/article/article_networkedit" , m);
	}
}

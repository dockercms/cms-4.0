package com.leimingtech.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ContentAccessoryEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.ArticleServiceI;
import com.leimingtech.core.service.ConstantsServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

@Service("articleService")
@Transactional
public class ArticleServiceImpl extends CommonServiceImpl implements ArticleServiceI {

	@Autowired
	private SystemService systemService;
	@Autowired
	private ConstantsServiceI constantsService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private IStatic staticImpl;
	@Override
	public void saveArticle(ContentsEntity contents,ArticleEntity article) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		//栏目id
		String contentCatId = contents.getCatid();
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//顺序值
		String divValue = request.getParameter("divValue");
		List<ContentAccessoryEntity> accessoryList = new ArrayList<ContentAccessoryEntity>();
		if(StringUtil.isNotEmpty(temporary)){
			//文章附件
			accessoryList = findByProperty(ContentAccessoryEntity.class, "contentId", temporary);
		}
		ContentCatEntity contentcat=getEntity(ContentCatEntity.class,  contentCatId );
		String message = "";
		if (StringUtil.isNotEmpty(article.getContentid())) {
			//修改文章
			updateArticle(contents, article, contentcat, accessoryList, temporary, divValue);
		} else {
			String logInfo = "PC内容【" + contents.getTitle() + "】";
			message = "添加成功";
			//增加文章
			article.setContentid(contents.getId());
			article.setSiteid(contents.getSiteid());
			article.setCreatedtime(new Date());//创建时间
			save(article);
			//在modelExt中保存modelId/attrName
			contentsService.saveModelItem(contents);
			//保存相关内容
			contentsService.saveRelateContent(contents, temporary, divValue);
			//保存文章附件
			for(ContentAccessoryEntity accessory:accessoryList){
				accessory.setContentId(String.valueOf(contents.getId()));
				accessory.setCreatedtime(new Date());//创建时间
				save(accessory);
			}
			//生成预览页面
			staticImpl.staticContentActOnPreview(contents);
			//进入判断工作流和状态
			constantsService.isWorkFlow(contents, contentcat);
			//日志添加
			systemService.addLog(logInfo+message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
	}
	
	
	/**
	 * 根据id获取文章内容
	 * @param id 文章id
	 * @return
	 */
	public ArticleEntity getArticleById(String id) {
		CriteriaQuery cq = new CriteriaQuery(ArticleEntity.class);
		cq.eq("contentid", id);
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		List<ArticleEntity> articlelist = getListByCriteriaQuery(cq, false);
		ArticleEntity article = new ArticleEntity();
		if(articlelist.size() > 0){
			article = articlelist.get(0);
		}
		return article;
	}
	
	/**
	 * 根据CONTENTID删除文章内容
	 * @param id
	 */
	public void deleArticle(ArticleEntity article) {
		delete(article);
	}
	/**
	 * 文章附件div
	 * @param contentId
	 * @param temporary
	 * @return
	 */
	@Override
	public Map<String, Object> loadAccessory(String contentId, String temporary) {
		List<ContentAccessoryEntity> accessoryList = new ArrayList<ContentAccessoryEntity>();
		List<ContentAccessoryEntity> accessoryListT = new ArrayList<ContentAccessoryEntity>();
		if(StringUtil.isNotEmpty(contentId)){
			accessoryList = findByProperty(ContentAccessoryEntity.class, "contentId", contentId);
		}
		if(StringUtil.isNotEmpty(temporary)){
			accessoryListT = findByProperty(ContentAccessoryEntity.class, "contentId", temporary);
			for(ContentAccessoryEntity accessory :accessoryListT){
				accessoryList.add(accessory);
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("accessoryList", accessoryList);
		m.put("temporary", temporary);
		m.put("contentId", contentId);
		return m;
	}


	@Override
	public void updateArticle(ContentsEntity contents, ArticleEntity article,ContentCatEntity contentcat,
			List<ContentAccessoryEntity> accessoryList,String temporary,String divValue) {
		ArticleEntity t = findUniqueByProperty(ArticleEntity.class, "contentid", article.getContentid());
		String message = "";
		String logInfo = "PC内容【" + contents.getTitle() + "】";
		try {
			message = "更新成功";
			MyBeanUtils.copyBeanNotNull2Bean(article, t);
			saveOrUpdate(t);
			//保存扩展参数名称和value
			contentsService.saveModelExt(contents);
			//保存相关内容
			contentsService.saveRelateContent(contents, temporary, divValue);
			//保存文章附件
			for(ContentAccessoryEntity accessory:accessoryList){
				accessory.setContentId(String.valueOf(contents.getId()));
				saveOrUpdate(accessory);
			}
			//生成预览页面
			staticImpl.staticContentActOnPreview(contents);
			//进入判断工作流和状态
			constantsService.isWorkFlow(contents, contentcat);
			//日志添加
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新失败";
		}
		systemService.addLog(logInfo+message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
	}


	
}
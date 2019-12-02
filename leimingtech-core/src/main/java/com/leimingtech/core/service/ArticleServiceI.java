package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;

import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ContentAccessoryEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;

public interface ArticleServiceI extends CommonService{

	/**
	 * 保存文章
	 * @param article
	 * @param contents
	 * @param modelItem
	 * @param map
	 * @return
	 */
	void saveArticle(ContentsEntity contents,ArticleEntity article);
	/**
	 * 修改文章
	 * @param contents
	 * @param article
	 * @param contentcat
	 * @param accessoryList
	 * @param temporary
	 * @param divValue
	 */
	void updateArticle(ContentsEntity contents,ArticleEntity article,ContentCatEntity contentcat,List<ContentAccessoryEntity> accessoryList,String temporary,String divValue);
	
	/**
	 * 根据id获取文章内容
	 * @param id 文章id
	 * @return
	 */
	ArticleEntity getArticleById(String id);
	
	/**
	 * 根据CONTENTID删除文章内容
	 * @param id
	 */
	void deleArticle(ArticleEntity article);
	/**
	 * 文章附件div
	 * @param contentId
	 * @param temporary
	 * @return
	 */
	Map<String, Object> loadAccessory(String contentId,String temporary);
}

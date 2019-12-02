package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;

/**
 * 文档标签接口
 * 
 * @author liuzhen 2014年4月25日 17:13:16
 * 
 */
public interface IContentTagMng extends CommonService {

	/**
	 * 获取文章（此方法由标签调用）
	 * 
	 * @param params
	 *            文章参数
	 * @return 根据参数不同既可以获取单个文章也可以获取文章列表 content or List<content>
	 */
	Object getContentByTag(Map params);
	
	
	/**
	 * 获取文章（此方法由标签调用）
	 * 
	 * @param params
	 *            文章参数
	 * @return 根据isTop获取置顶文章
	 */
	Object getContentByTagTop(Map params);

	/**
	 * 通过文章获取文章模板路径
	 * 
	 * @return 模板路径
	 */
	String getContentTemplatePath(ContentsEntity content, ContentCatEntity catalog);

	/**
	 * 根据文章id获取相关模型数据
	 * 
	 * @param contentid
	 * @return map
	 */
	Map<String, Object> getContent(Integer contentid);

	
	/**
	 * 根据文章获取相关模型数据
	 * 
	 * @param content
	 * @return map
	 */
	Map<String, Object> getContent(ContentsEntity content);
	
	/**
	 * 模型转换 int转str
	 * 
	 * @param modelType
	 * @return
	 */
	String parseIntModelToStr(String modelType);

}

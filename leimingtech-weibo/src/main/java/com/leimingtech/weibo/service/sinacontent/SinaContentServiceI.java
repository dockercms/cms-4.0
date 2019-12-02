package com.leimingtech.weibo.service.sinacontent;

import java.util.Map;

import com.leimingtech.weibo.entity.sinacontent.SinaContentEntity;

/**
 * @Title: interface
 * @Description: 新浪微博内容接口
 * @author
 * @date 2015-12-05 13:56:58
 * @version V1.0
 * 
 */
public interface SinaContentServiceI {

	/**
	 * 保存新浪微博内容
	 * 
	 * @param sinaContent
	 * @return
	 */
	java.lang.String save(SinaContentEntity sinaContent);

	/**
	 * 更新新浪微博内容
	 * 
	 * @param sinaContent
	 */
	void saveOrUpdate(SinaContentEntity sinaContent);

	/**
	 * 通过id获取新浪微博内容
	 * 
	 * @param id
	 *            新浪微博内容id
	 * @return
	 */
	SinaContentEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的新浪微博内容数据集
	 * 
	 * @param sinaContent
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return sinaContentList新浪微博内容数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(SinaContentEntity sinaContent, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除新浪微博内容
	 * 
	 * @param sinaContent
	 */
	void delete(SinaContentEntity sinaContent);

}

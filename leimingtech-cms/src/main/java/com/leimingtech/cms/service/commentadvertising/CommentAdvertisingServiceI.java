package com.leimingtech.cms.service.commentadvertising;

import java.util.Map;

import com.leimingtech.cms.entity.commentadvertising.CommentAdvertisingEntity;
import com.leimingtech.core.service.CommonService;

/**
 * @Title: interface
 * @Description: 评论广告接口
 * @author
 * @date 2017-04-26 11:55:55
 * @version V1.0
 * 
 */
public interface CommentAdvertisingServiceI{

	/**
	 * 保存评论广告
	 * 
	 * @param commentAdvertising
	 * @return
	 */
	java.lang.String save(CommentAdvertisingEntity commentAdvertising);

	/**
	 * 更新评论广告
	 * 
	 * @param commentAdvertising
	 */
	void saveOrUpdate(CommentAdvertisingEntity commentAdvertising);

	/**
	 * 通过id获取评论广告
	 * 
	 * @param id
	 *            评论广告id
	 * @return
	 */
	CommentAdvertisingEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的评论广告数据集
	 * 
	 * @param commentAdvertising
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return commentAdvertisingList评论广告数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(CommentAdvertisingEntity commentAdvertising, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除评论广告
	 * 
	 * @param commentAdvertising
	 */
	void delete(CommentAdvertisingEntity commentAdvertising);

	 Object getCommentAdvertisingByTag(Map params);

}

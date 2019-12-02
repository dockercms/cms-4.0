package com.leimingtech.cms.service.reply;

import com.leimingtech.cms.entity.reply.ReplyEntity;

import java.util.Map;

/**
 * @Title: interface
 * @Description: 跟帖接口
 * @author
 * @date 2017-03-27 19:11:05
 * @version V1.0
 * 
 */
public interface ReplyFrontServiceI {

	/**
	 * 保存跟帖
	 * 
	 * @param reply
	 * @return
	 */
	java.lang.String save(ReplyEntity reply);

	/**
	 * 更新跟帖
	 * 
	 * @param reply
	 */
	void saveOrUpdate(ReplyEntity reply);

	/**
	 * 通过id获取跟帖
	 * 
	 * @param id
	 *            跟帖id
	 * @return
	 */
	ReplyEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的跟帖数据集
	 * 
	 * @param reply
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return replyList跟帖数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ReplyEntity reply, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除跟帖
	 * 
	 * @param reply
	 */
	void delete(ReplyEntity reply);

    /**
     * 增加赞同数
     * @param replyId
     * @return
     */
    Integer addReplySupport(String replyId);

    Integer deleteReplySupport(String replyId);

}

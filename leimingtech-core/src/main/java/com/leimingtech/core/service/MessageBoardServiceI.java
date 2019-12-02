package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;

import com.leimingtech.core.entity.MessageBoardEntity;
import com.leimingtech.core.entity.MessageManagementEntity;

/**
 * @Title: interface
 * @Description: 留言板接口
 * @author
 * @date 2016-04-26 15:41:54
 * @version V1.0
 * 
 */
public interface MessageBoardServiceI {

	/**
	 * 保存留言板
	 * 
	 * @param messageBoard
	 * @return
	 */
	java.lang.String save(MessageBoardEntity messageBoard);

	/**
	 * 更新留言板
	 * 
	 * @param messageBoard
	 */
	void saveOrUpdate(MessageBoardEntity messageBoard);

	/**
	 * 通过id获取留言板
	 * 
	 * @param id
	 *            留言板id
	 * @return
	 */
	MessageBoardEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的留言板数据集
	 * 
	 * @param messageBoard
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return messageBoardList留言板数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(MessageBoardEntity messageBoard, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除留言板
	 * 
	 * @param messageBoard
	 */
	void delete(MessageBoardEntity messageBoard);
	
	/***
	 * 获取全部数据
	 * 
	 * @return
	 */
	List<MessageBoardEntity> getAllData();
	
	/**
	 * 通过主表id获取关联数据
	 * 
	 * @param id
	 * @return
	 */
	List<MessageManagementEntity> getListByPid(java.lang.String id);
	
	/**
	 * 获取分页后的留言管理数据集
	 * 
	 * @param messageManagement
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @param id
	 *            主表id
	 * @return messageManagementList留言管理数据集 pageCount总页数
	 */
	Map<String, Object> getListByPid(MessageManagementEntity messageManagement, Map param, int pageSize,
			int pageNo,java.lang.String id);
	
	/**
	 * 删除留言管理
	 * 
	 * @param messageManagement
	 */
	void delete(MessageManagementEntity messageManagement);
	
	/**
	 * 通过id获取留言管理
	 * 
	 * @param id
	 *            留言管理id
	 * @return
	 */
	MessageManagementEntity getMessageManagementEntity(java.lang.String id);
	
	/**
	 * 保存留言管理
	 * 
	 * @param messageManagement
	 * @return
	 */
	java.lang.String save(MessageManagementEntity messageManagement);

	/**
	 * 更新留言管理
	 * 
	 * @param messageManagement
	 */
	void saveOrUpdate(MessageManagementEntity messageManagement);
	
}

package com.leimingtech.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.MessageBoardEntity;
import com.leimingtech.core.entity.MessageManagementEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.MessageBoardServiceI;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * @Title: interface
 * @Description: 留言板接口实现
 * @author
 * @date 2016-04-26 15:41:54
 * @version V1.0
 * 
 */
@Service("messageBoardService")
@Transactional
public class MessageBoardServiceImpl implements MessageBoardServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存留言板
	 * 
	 * @param messageBoard
	 * @return
	 */
	public java.lang.String save(MessageBoardEntity messageBoard) {
		return (java.lang.String) commonService.save(messageBoard);
	}

	/**
	 * 更新留言板
	 * 
	 * @param messageBoard
	 */
	@Override
	public void saveOrUpdate(MessageBoardEntity messageBoard) {
		commonService.saveOrUpdate(messageBoard);
	}

	/**
	 * 通过id获取留言板
	 * 
	 * @param id
	 *            留言板id
	 * @return
	 */
	@Override
	public MessageBoardEntity getEntity(java.lang.String id) {
		return commonService.getEntity(MessageBoardEntity.class, id);
	}

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
	@Override
	public Map<String, Object> getPageList(MessageBoardEntity messageBoard, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(MessageBoardEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, messageBoard, param);
		cq.eq("siteId", PlatFormUtil.getSessionSiteId());
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("createtime", SortDirection.desc);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<MessageBoardEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("messageBoardList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除留言板
	 * 
	 * @param messageBoard
	 */
	@Override
	public void delete(MessageBoardEntity messageBoard) {
		if (messageBoard == null)
			return;

		List<MessageManagementEntity> messageManagementList = getListByPid(messageBoard.getId());
		if (messageManagementList != null && messageManagementList.size() > 0) {
			int messageManagementMaxIndex = messageManagementList.size() - 1;
			for (int i = messageManagementMaxIndex; i >= 0; i--) {
				delete(messageManagementList.get(i));
			}
		}
		commonService.delete(messageBoard);
	}
	
	/***
	 * 获取全部数据
	 * 
	 * @return
	 */
	@Override
	public List<MessageBoardEntity> getAllData() {
		CriteriaQuery cq = new CriteriaQuery(MessageBoardEntity.class);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}
	
	/**
	 * 通过主表id获取关联数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<MessageManagementEntity> getListByPid(java.lang.String id) {
		CriteriaQuery cq = new CriteriaQuery(MessageManagementEntity.class);
		cq.eq("boardId", id);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}
	
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
	@Override
	public Map<String, Object> getListByPid(MessageManagementEntity messageManagement, Map param,
			int pageSize, int pageNo,java.lang.String id) {
		CriteriaQuery cq = new CriteriaQuery(MessageManagementEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, messageManagement, param);
		cq.eq("boardId", java.lang.String.valueOf(id));
		cq.addOrder("addTime", SortDirection.desc);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<MessageManagementEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("messageManagementList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}
	
	/**
	 * 删除留言管理
	 * 
	 * @param messageManagement
	 */
	@Override
	public void delete(MessageManagementEntity messageManagement) {
		commonService.delete(messageManagement);
	}
	
	/**
	 * 通过id获取留言管理
	 * 
	 * @param id
	 *            留言管理id
	 * @return
	 */
 	@Override
	public MessageManagementEntity getMessageManagementEntity(java.lang.String id){
		return commonService.getEntity(MessageManagementEntity.class, id);
	}
	
	/**
	 * 保存留言管理
	 * 
	 * @param messageManagement
	 * @return
	 */
	public java.lang.String save(MessageManagementEntity messageManagement) {
		return (java.lang.String) commonService.save(messageManagement);
	}

	/**
	 * 更新留言管理
	 * 
	 * @param messageManagement
	 */
	@Override
	public void saveOrUpdate(MessageManagementEntity messageManagement) {
		commonService.saveOrUpdate(messageManagement);
	}
	
}
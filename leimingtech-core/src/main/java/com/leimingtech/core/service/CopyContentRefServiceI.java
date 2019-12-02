package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.CopyContentRefEntity;

/**
 * @Title: interface
 * @Description: 稿件复制或引用关联接口
 * @author
 * @date 2015-10-26 17:20:02
 * @version V1.0
 * 
 */
public interface CopyContentRefServiceI {

	/**
	 * 保存稿件复制或引用关联
	 * 
	 * @param copyContentRef
	 * @return
	 */
	java.lang.String save(CopyContentRefEntity copyContentRef);

	/**
	 * 更新稿件复制或引用关联
	 * 
	 * @param copyContentRef
	 */
	void saveOrUpdate(CopyContentRefEntity copyContentRef);

	/**
	 * 通过id获取稿件复制或引用关联
	 * 
	 * @param id
	 *            稿件复制或引用关联id
	 * @return
	 */
	CopyContentRefEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的稿件复制或引用关联数据集
	 * 
	 * @param copyContentRef
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return copyContentRefList稿件复制或引用关联数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(CopyContentRefEntity copyContentRef,
			Map param, int pageSize, int pageNo);

	/**
	 * 删除稿件复制或引用关联
	 * 
	 * @param copyContentRef
	 */
	void delete(CopyContentRefEntity copyContentRef);

	/**
	 * 添加关联
	 * 
	 * @param contents
	 *            主稿件
	 * @param id
	 *            子稿件
	 */
	void save(ContentsEntity contents, String id);

	/**
	 * 通过复制后的文章获取锁定的源稿件
	 * 
	 * @param id
	 *            复制后的稿件id
	 * @return
	 */
	public ContentsEntity findLockMainContent(String id);

}

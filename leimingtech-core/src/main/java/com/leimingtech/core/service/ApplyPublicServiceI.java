package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.ApplyPublicEntity;

/**
 * @Title: interface
 * @Description: 申请公开接口
 * @author
 * @date 2016-04-05 16:57:35
 * @version V1.0
 * 
 */
public interface ApplyPublicServiceI {

	/**
	 * 保存申请公开
	 * 
	 * @param applyPublic
	 * @return
	 */
	java.lang.String save(ApplyPublicEntity applyPublic);

	/**
	 * 更新申请公开
	 * 
	 * @param applyPublic
	 */
	void saveOrUpdate(ApplyPublicEntity applyPublic);

	/**
	 * 通过id获取申请公开
	 * 
	 * @param id
	 *            申请公开id
	 * @return
	 */
	ApplyPublicEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的申请公开数据集
	 * 
	 * @param applyPublic
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return applyPublicList申请公开数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ApplyPublicEntity applyPublic, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除申请公开
	 * 
	 * @param applyPublic
	 */
	void delete(ApplyPublicEntity applyPublic);

}

package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;

import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.hibernate.qbc.PageList;


public interface ModelItemServiceI{

	PageList getPageList(ModelItemEntity modelItem, Map param);

	ModelItemEntity getEntity(String id);

	void saveOrUpdate(ModelItemEntity modelItem);

	void save(ModelItemEntity modelItem);

	void delete(ModelItemEntity modelItem);

	/**
	 * 根据方案查询所有字段
	 * @param modelId
	 * @return
	 */
	List<ModelItemEntity> findByModelId(String modelId);

	/**
	 * 按照数据进行调整排序值
	 * @param ids
	 */
	void updateSort(String[] ids);

	/**
	 * 获取指定方案中的最大排序值
	 * @param modelId
	 * @return
	 */
	int getMaxPriorityByModel(String modelId);

	/**
	 * 检查指定方案中变量名是否有重名
	 * @param modelId
	 * @param field
	 * @return
	 */
	boolean checkFieldExits(String modelId, String field);
	
}

package com.leimingtech.core.service.modelext;

import java.util.List;

import com.leimingtech.core.entity.ModelExtEntity;


public interface ModelExtServiceI{

	void save(ModelExtEntity modelExt);

	void saveOrUpdate(ModelExtEntity modelExt);

	ModelExtEntity getEntity(String id);

	/**
	 * 获取指定内容下的拓展字段
	 * @param id
	 * @return
	 */
	List<ModelExtEntity> getContentAllExt(String id);

	void delete(ModelExtEntity modelExt);

	List<ModelExtEntity> findByItemId(String itemId);

}

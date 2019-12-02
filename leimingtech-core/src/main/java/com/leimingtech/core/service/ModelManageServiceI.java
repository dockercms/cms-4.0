package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;

import com.leimingtech.core.entity.ModelManageEntity;
import com.leimingtech.core.hibernate.qbc.PageList;

/**
 * 拓展字段方案管理接口
 * 
 * @author liuzhen
 * 
 */
public interface ModelManageServiceI {

	/**
	 * 根据id获取方案
	 * 
	 * @param id
	 * @return
	 */
	ModelManageEntity getEntity(String id);

	PageList getPageList(int pageSize, int pageNo,
			ModelManageEntity modelManage, Map param);

	void saveOrUpdate(ModelManageEntity modelManage);

	void save(ModelManageEntity modelManage);

	void delete(ModelManageEntity modelManage);

	/**
	 * 获取拓展字段所有方案
	 * @return
	 */
	List<ModelManageEntity> getAll();
}

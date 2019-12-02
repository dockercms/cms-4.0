package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.TSType;


/**
 * 通用类型字典管理接口
 * 
 * @author liuzhen 2015年9月1日 11:48:12
 * 
 */
public interface TypeServiceI {
	/**
	 * 保存通用类型字典
	 * 
	 * @param type
	 * @return
	 */
	java.lang.String save(TSType type);

	/**
	 * 更新通用类型字典
	 * 
	 * @param type
	 */
	void saveOrUpdate(TSType type);

	/**
	 * 通过id获取通用类型字典
	 * 
	 * @param id
	 *            通用类型字典id
	 * @return
	 */
	TSType getEntity(java.lang.String id);

	/**
	 * 获取分页后的通用类型字典数据集
	 * 
	 * @param type
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return typeList通用类型字典数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(TSType type, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除通用类型字典
	 * 
	 * @param type
	 */
	void delete(TSType type);
}

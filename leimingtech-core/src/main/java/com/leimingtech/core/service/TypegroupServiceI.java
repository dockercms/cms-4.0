package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.TSTypegroup;


/**
 * @Title: interface
 * @Description: 数据字典分类接口
 * @author
 * @date 2015-09-01 18:46:05
 * @version V1.0
 * 
 */
public interface TypegroupServiceI {

	/**
	 * 保存数据字典分类
	 * 
	 * @param typegroup
	 * @return
	 */
	java.lang.String save(TSTypegroup typegroup);

	/**
	 * 更新数据字典分类
	 * 
	 * @param typegroup
	 */
	void saveOrUpdate(TSTypegroup typegroup);

	/**
	 * 通过id获取数据字典分类
	 * 
	 * @param id
	 *            数据字典分类id
	 * @return
	 */
	TSTypegroup getEntity(java.lang.String id);

	/**
	 * 获取分页后的数据字典分类数据集
	 * 
	 * @param typegroup
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return typegroupList数据字典分类数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(TSTypegroup typegroup, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除数据字典分类
	 * 
	 * @param typegroup
	 */
	void delete(TSTypegroup typegroup);

	/**
	 * 通过code获取数据字典分类
	 * 
	 * @param code
	 * @return
	 */
	TSTypegroup findByTypegroupCode(String code);

}

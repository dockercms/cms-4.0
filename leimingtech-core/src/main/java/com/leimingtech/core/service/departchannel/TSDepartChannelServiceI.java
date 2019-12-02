package com.leimingtech.core.service.departchannel;

import java.util.Map;

import com.leimingtech.core.entity.TSDepartChannel;

/**
 * @Title: interface
 * @Description: 部门下关联栏目接口
 * @author
 * @date 2016-04-22 14:53:35
 * @version V1.0
 * 
 */
public interface TSDepartChannelServiceI {

	/**
	 * 保存部门下关联栏目
	 * 
	 * @param tSDepartChannel
	 * @return
	 */
	java.lang.String save(TSDepartChannel tSDepartChannel);

	/**
	 * 更新部门下关联栏目
	 * 
	 * @param tSDepartChannel
	 */
	void saveOrUpdate(TSDepartChannel tSDepartChannel);

	/**
	 * 通过id获取部门下关联栏目
	 * 
	 * @param id
	 *            部门下关联栏目id
	 * @return
	 */
	TSDepartChannel getEntity(java.lang.String id);

	/**
	 * 获取分页后的部门下关联栏目数据集
	 * 
	 * @param tSDepartChannel
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return tSDepartChannelList部门下关联栏目数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(TSDepartChannel tSDepartChannel, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除部门下关联栏目
	 * 
	 * @param tSDepartChannel
	 */
	void delete(TSDepartChannel tSDepartChannel);

	/**
	 * 根据栏目删除部门与栏目的关联
	 * @param idArray
	 */
	void deleteByContentCats(String[] idArray);

	/**
	 * 根据部门删除栏目跟部门间的关联
	 * @param departId
	 */
	void deleteByDepart(String departId);

}

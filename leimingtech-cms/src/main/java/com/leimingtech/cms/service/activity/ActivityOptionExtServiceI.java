package com.leimingtech.cms.service.activity;

import java.util.Map;

import com.leimingtech.core.entity.ActivityOptionExtEntity;

/**
 * @Title: interface
 * @Description: 活动报名表接口
 * @author
 * @date 2015-08-28 17:59:53
 * @version V1.0
 * 
 */
public interface ActivityOptionExtServiceI {

	/**
	 * 保存活动报名表
	 * 
	 * @param activityOptionExt
	 * @return
	 */
	java.lang.String save(ActivityOptionExtEntity activityOptionExt);

	/**
	 * 更新活动报名表
	 * 
	 * @param activityOptionExt
	 */
	void saveOrUpdate(ActivityOptionExtEntity activityOptionExt);

	/**
	 * 通过id获取活动报名表
	 * 
	 * @param id
	 *            活动报名表id
	 * @return
	 */
	ActivityOptionExtEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的活动报名表数据集
	 * 
	 * @param activityOptionExt
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return activityOptionExtList活动报名表数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ActivityOptionExtEntity activityOptionExt, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除活动报名表
	 * 
	 * @param activityOptionExt
	 */
	void delete(ActivityOptionExtEntity activityOptionExt);

}

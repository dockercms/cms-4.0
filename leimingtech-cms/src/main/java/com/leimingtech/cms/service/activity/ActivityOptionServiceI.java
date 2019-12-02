package com.leimingtech.cms.service.activity;

import java.util.Map;

import com.leimingtech.cms.entity.activity.ActivityOptionEntity;

/**
 * @Title: interface
 * @Description: 活动报名选项内容接口
 * @author
 * @date 2015-08-07 17:56:10
 * @version V1.0
 * 
 */
public interface ActivityOptionServiceI {

	
	
	
	/**
	 * 保存活动报名选项内容
	 * 
	 * @param activityOption
	 * @return
	 */
	java.lang.String save(ActivityOptionEntity activityOption);

	/**
	 * 更新活动报名选项内容
	 * 
	 * @param activityOption
	 */
	void saveOrUpdate(ActivityOptionEntity activityOption);

	/**
	 * 通过id获取活动报名选项内容
	 * 
	 * @param id
	 *            活动报名选项内容id
	 * @return
	 */
	ActivityOptionEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的活动报名选项内容数据集
	 * 
	 * @param activityOption
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return activityOptionList活动报名选项内容数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ActivityOptionEntity activityOption, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除活动报名选项内容
	 * 
	 * @param activityOption
	 */
	void delete(ActivityOptionEntity activityOption);

}

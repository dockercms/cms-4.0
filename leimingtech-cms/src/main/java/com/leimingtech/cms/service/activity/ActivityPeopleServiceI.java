package com.leimingtech.cms.service.activity;

import java.util.Map;

import com.leimingtech.core.entity.ActivityPeopleEntity;

/**
 * @Title: interface
 * @Description: 活动报名人表接口
 * @author
 * @date 2015-08-28 17:57:43
 * @version V1.0
 * 
 */
public interface ActivityPeopleServiceI {

	/**
	 * 保存活动报名人表
	 * 
	 * @param activityPeople
	 * @return
	 */
	java.lang.String save(ActivityPeopleEntity activityPeople);

	/**
	 * 更新活动报名人表
	 * 
	 * @param activityPeople
	 */
	void saveOrUpdate(ActivityPeopleEntity activityPeople);

	/**
	 * 通过id获取活动报名人表
	 * 
	 * @param id
	 *            活动报名人表id
	 * @return
	 */
	ActivityPeopleEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的活动报名人表数据集
	 * 
	 * @param activityPeople
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return activityPeopleList活动报名人表数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ActivityPeopleEntity activityPeople, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除活动报名人表
	 * 
	 * @param activityPeople
	 */
	void delete(ActivityPeopleEntity activityPeople);



}

package com.leimingtech.cms.service.activity;

import java.util.Map;

import com.leimingtech.core.entity.ActivityOptionContentEntity;

/**
 * @Title: interface
 * @Description: 表单选项接口
 * @author
 * @date 2015-08-07 17:55:16
 * @version V1.0
 * 
 */
public interface ActivityOptionContentServiceI {

	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	 

	
	
	
	
	/**
	 * 保存表单选项
	 * 
	 * @param activityOptionContent
	 * @return
	 */
	java.lang.String save(ActivityOptionContentEntity activityOptionContent);

	/**
	 * 更新表单选项
	 * 
	 * @param activityOptionContent
	 */
	void saveOrUpdate(ActivityOptionContentEntity activityOptionContent);

	/**
	 * 通过id获取表单选项
	 * 
	 * @param id
	 *            表单选项id
	 * @return
	 */
	ActivityOptionContentEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的表单选项数据集
	 * 
	 * @param activityOptionContent
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return activityOptionContentList表单选项数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ActivityOptionContentEntity activityOptionContent, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除表单选项
	 * 
	 * @param activityOptionContent
	 */
	void delete(ActivityOptionContentEntity activityOptionContent);

}

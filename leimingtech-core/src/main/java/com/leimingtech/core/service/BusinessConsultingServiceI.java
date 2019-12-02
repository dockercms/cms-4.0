package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.BusinessConsultingEntity;

/**
 * @Title: interface
 * @Description: 业务咨询接口
 * @author
 * @date 2016-03-31 14:27:12
 * @version V1.0
 * 
 */
public interface BusinessConsultingServiceI {

	/**
	 * 保存业务咨询
	 * 
	 * @param businessConsulting
	 * @return
	 */
	java.lang.String save(BusinessConsultingEntity businessConsulting);

	/**
	 * 更新业务咨询
	 * 
	 * @param businessConsulting
	 */
	void saveOrUpdate(BusinessConsultingEntity businessConsulting);

	/**
	 * 通过id获取业务咨询
	 * 
	 * @param id
	 *            业务咨询id
	 * @return
	 */
	BusinessConsultingEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的业务咨询数据集
	 * 
	 * @param businessConsulting
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return businessConsultingList业务咨询数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(BusinessConsultingEntity businessConsulting, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除业务咨询
	 * 
	 * @param businessConsulting
	 */
	void delete(BusinessConsultingEntity businessConsulting);

}

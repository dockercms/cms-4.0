package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.WebErrorEntity;

/**
 * @Title: interface
 * @Description: 网站纠错接口
 * @author
 * @date 2016-04-01 15:02:22
 * @version V1.0
 * 
 */
public interface WebErrorServiceI {

	/**
	 * 保存网站纠错
	 * 
	 * @param webError
	 * @return
	 */
	java.lang.String save(WebErrorEntity webError);

	/**
	 * 更新网站纠错
	 * 
	 * @param webError
	 */
	void saveOrUpdate(WebErrorEntity webError);

	/**
	 * 通过id获取网站纠错
	 * 
	 * @param id
	 *            网站纠错id
	 * @return
	 */
	WebErrorEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的网站纠错数据集
	 * 
	 * @param webError
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return webErrorList网站纠错数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(WebErrorEntity webError, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除网站纠错
	 * 
	 * @param webError
	 */
	void delete(WebErrorEntity webError);

}

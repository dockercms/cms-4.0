package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.EmailAddressEntity;

/**
 * @Title: interface
 * @Description: 信访邮箱接口
 * @author
 * @date 2016-04-01 11:11:36
 * @version V1.0
 * 
 */
public interface EmailAddressServiceI {

	/**
	 * 保存信访邮箱
	 * 
	 * @param emailAddress
	 * @return
	 */
	java.lang.String save(EmailAddressEntity emailAddress);

	/**
	 * 更新信访邮箱
	 * 
	 * @param emailAddress
	 */
	void saveOrUpdate(EmailAddressEntity emailAddress);

	/**
	 * 通过id获取信访邮箱
	 * 
	 * @param id
	 *            信访邮箱id
	 * @return
	 */
	EmailAddressEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的信访邮箱数据集
	 * 
	 * @param emailAddress
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return emailAddressList信访邮箱数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(EmailAddressEntity emailAddress, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除信访邮箱
	 * 
	 * @param emailAddress
	 */
	void delete(EmailAddressEntity emailAddress);

}

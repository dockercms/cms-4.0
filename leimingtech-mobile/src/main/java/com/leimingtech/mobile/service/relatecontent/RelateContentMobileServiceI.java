package com.leimingtech.mobile.service.relatecontent;

import java.util.List;
import java.util.Map;

import com.leimingtech.mobile.entity.relatecontent.RelateContentMobileEntity;

/**
 * @Title: interface
 * @Description: 相关内容接口
 * @author
 * @date 2015-06-30 15:11:10
 * @version V1.0
 * 
 */
public interface RelateContentMobileServiceI {

	/**
	 * 保存相关内容
	 * 
	 * @param relateContentMobile
	 * @return
	 */
	java.lang.String save(RelateContentMobileEntity relateContentMobile);

	/**
	 * 更新相关内容
	 * 
	 * @param relateContentMobile
	 */
	void saveOrUpdate(RelateContentMobileEntity relateContentMobile);

	/**
	 * 通过id获取相关内容
	 * 
	 * @param id
	 *            相关内容id
	 * @return
	 */
	RelateContentMobileEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的相关内容数据集
	 * 
	 * @param relateContentMobile
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return relateContentMobileList相关内容数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(RelateContentMobileEntity relateContentMobile, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除相关内容
	 * 
	 * @param relateContentMobile
	 */
	void delete(RelateContentMobileEntity relateContentMobile);

	/**
	 * 通过移动稿件主表id获取关联的稿件
	 * 
	 * @param contentID
	 * @return
	 */
	List<RelateContentMobileEntity> getListByContentID(String contentID);

}

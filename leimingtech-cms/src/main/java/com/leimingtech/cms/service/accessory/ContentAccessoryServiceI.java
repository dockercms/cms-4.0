package com.leimingtech.cms.service.accessory;

import com.leimingtech.core.entity.ContentAccessoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 附件管理接口
 * @author liuzhen
 *
 */
public interface ContentAccessoryServiceI{

	/**
	 * 获取内容的附件
	 * @param id
	 * @return
	 */
	List<ContentAccessoryEntity> findByContentId(String id);

	ContentAccessoryEntity getEntity(String accessoryId);

	void delete(ContentAccessoryEntity contentAccessory);

	void save(ContentAccessoryEntity contentAccessory);

	void saveOrUpdate(ContentAccessoryEntity t);

	/**
	 * 获取分页后的附件数据集
	 *
	 * @param contentAccessory
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return contentAccessoryList投稿配置数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(
			ContentAccessoryEntity contentAccessory, Map param, int pageSize,
			int pageNo);

	void delete(List<ContentAccessoryEntity> accessoryList);

}

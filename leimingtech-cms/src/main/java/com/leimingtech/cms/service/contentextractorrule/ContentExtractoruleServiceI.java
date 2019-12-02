package com.leimingtech.cms.service.contentextractorrule;

import java.util.Map;

import com.leimingtech.core.entity.ContentExtractoruleEntity;

/**
 * @Title: interface
 * @Description: 一键抓取规则管理接口
 * @author
 * @date 2015-08-04 16:53:56
 * @version V1.0
 * 
 */
public interface ContentExtractoruleServiceI {

	/**
	 * 保存一键抓取规则管理
	 * 
	 * @param contentExtractorule
	 * @return
	 */
	java.lang.String save(ContentExtractoruleEntity contentExtractorule);

	/**
	 * 更新一键抓取规则管理
	 * 
	 * @param contentExtractorule
	 */
	void saveOrUpdate(ContentExtractoruleEntity contentExtractorule);

	/**
	 * 通过id获取一键抓取规则管理
	 * 
	 * @param id
	 *            一键抓取规则管理id
	 * @return
	 */
	ContentExtractoruleEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的一键抓取规则管理数据集
	 * 
	 * @param contentExtractorule
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return contentExtractoruleList一键抓取规则管理数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ContentExtractoruleEntity contentExtractorule, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除一键抓取规则管理
	 * 
	 * @param contentExtractorule
	 */
	void delete(ContentExtractoruleEntity contentExtractorule);

}

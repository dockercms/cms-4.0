package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.ComplaintsReportEntity;

/**
 * @Title: interface
 * @Description: 投诉举报接口
 * @author
 * @date 2016-04-01 09:49:18
 * @version V1.0
 * 
 */
public interface ComplaintsReportServiceI {

	/**
	 * 保存投诉举报
	 * 
	 * @param complaintsReport
	 * @return
	 */
	java.lang.String save(ComplaintsReportEntity complaintsReport);

	/**
	 * 更新投诉举报
	 * 
	 * @param complaintsReport
	 */
	void saveOrUpdate(ComplaintsReportEntity complaintsReport);

	/**
	 * 通过id获取投诉举报
	 * 
	 * @param id
	 *            投诉举报id
	 * @return
	 */
	ComplaintsReportEntity getEntity(java.lang.String id);

	/**
	 * 获取分页后的投诉举报数据集
	 * 
	 * @param complaintsReport
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return complaintsReportList投诉举报数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ComplaintsReportEntity complaintsReport, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除投诉举报
	 * 
	 * @param complaintsReport
	 */
	void delete(ComplaintsReportEntity complaintsReport);

}

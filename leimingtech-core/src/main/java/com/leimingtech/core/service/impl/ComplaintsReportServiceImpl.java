package com.leimingtech.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.ComplaintsReportEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ComplaintsReportServiceI;

/**
 * @Title: interface
 * @Description: 投诉举报接口实现
 * @author
 * @date 2016-04-01 09:49:17
 * @version V1.0
 * 
 */
@Service("complaintsReportService")
@Transactional
public class ComplaintsReportServiceImpl implements ComplaintsReportServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存投诉举报
	 * 
	 * @param complaintsReport
	 * @return
	 */
	public java.lang.String save(ComplaintsReportEntity complaintsReport) {
		return (java.lang.String) commonService.save(complaintsReport);
	}

	/**
	 * 更新投诉举报
	 * 
	 * @param complaintsReport
	 */
	@Override
	public void saveOrUpdate(ComplaintsReportEntity complaintsReport) {
		commonService.saveOrUpdate(complaintsReport);
	}

	/**
	 * 通过id获取投诉举报
	 * 
	 * @param id
	 *            投诉举报id
	 * @return
	 */
	@Override
	public ComplaintsReportEntity getEntity(java.lang.String id) {
		return commonService.getEntity(ComplaintsReportEntity.class, id);
	}

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
	@Override
	public Map<String, Object> getPageList(ComplaintsReportEntity complaintsReport, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ComplaintsReportEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, complaintsReport, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ComplaintsReportEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("complaintsReportList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除投诉举报
	 * 
	 * @param complaintsReport
	 */
	@Override
	public void delete(ComplaintsReportEntity complaintsReport) {
		commonService.delete(complaintsReport);
	}
	
}
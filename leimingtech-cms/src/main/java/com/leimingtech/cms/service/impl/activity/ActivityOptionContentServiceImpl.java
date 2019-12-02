package com.leimingtech.cms.service.impl.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.activity.ActivityOptionContentServiceI;
import com.leimingtech.core.entity.ActivityOptionContentEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;

/**
 * @Title: interface
 * @Description: 表单选项接口实现
 * @author
 * @date 2015-08-07 17:55:15
 * @version V1.0
 * 
 */
@Service("activityOptionContentService")
@Transactional
public class ActivityOptionContentServiceImpl implements ActivityOptionContentServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存表单选项
	 * 
	 * @param activityOptionContent
	 * @return
	 */
	public java.lang.String save(ActivityOptionContentEntity activityOptionContent) {
		return (java.lang.String) commonService.save(activityOptionContent);
	}

	/**
	 * 更新表单选项
	 * 
	 * @param activityOptionContent
	 */
	@Override
	public void saveOrUpdate(ActivityOptionContentEntity activityOptionContent) {
		commonService.saveOrUpdate(activityOptionContent);
	}

	/**
	 * 通过id获取表单选项
	 * 
	 * @param id
	 *            表单选项id
	 * @return
	 */
	@Override
	public ActivityOptionContentEntity getEntity(java.lang.String id) {
		return commonService.getEntity(ActivityOptionContentEntity.class, id);
	}

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
	@Override
	public Map<String, Object> getPageList(ActivityOptionContentEntity activityOptionContent, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ActivityOptionContentEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, activityOptionContent, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ActivityOptionContentEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityOptionContentList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除表单选项
	 * 
	 * @param activityOptionContent
	 */
	@Override
	public void delete(ActivityOptionContentEntity activityOptionContent) {
		commonService.delete(activityOptionContent);
	}
	
}
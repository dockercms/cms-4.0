package com.leimingtech.cms.service.impl.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.activity.ActivityOptionExtServiceI;
import com.leimingtech.core.entity.ActivityOptionExtEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;

/**
 * @Title: interface
 * @Description: 活动报名表接口实现
 * @author
 * @date 2015-08-28 17:59:53
 * @version V1.0
 * 
 */
@Service("activityOptionExtService")
@Transactional
public class ActivityOptionExtServiceImpl implements ActivityOptionExtServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存活动报名表
	 * 
	 * @param activityOptionExt
	 * @return
	 */
	public java.lang.String save(ActivityOptionExtEntity activityOptionExt) {
		return (java.lang.String) commonService.save(activityOptionExt);
	}

	/**
	 * 更新活动报名表
	 * 
	 * @param activityOptionExt
	 */
	@Override
	public void saveOrUpdate(ActivityOptionExtEntity activityOptionExt) {
		commonService.saveOrUpdate(activityOptionExt);
	}

	/**
	 * 通过id获取活动报名表
	 * 
	 * @param id
	 *            活动报名表id
	 * @return
	 */
	@Override
	public ActivityOptionExtEntity getEntity(java.lang.String id) {
		return commonService.getEntity(ActivityOptionExtEntity.class, id);
	}

	/**
	 * 获取分页后的活动报名表数据集
	 * 
	 * @param activityOptionExt
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return activityOptionExtList活动报名表数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(ActivityOptionExtEntity activityOptionExt, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ActivityOptionExtEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, activityOptionExt, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ActivityOptionExtEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityOptionExtList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除活动报名表
	 * 
	 * @param activityOptionExt
	 */
	@Override
	public void delete(ActivityOptionExtEntity activityOptionExt) {
		commonService.delete(activityOptionExt);
	}
	
}
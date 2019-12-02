package com.leimingtech.cms.service.impl.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.activity.ActivityOptionEntity;
import com.leimingtech.cms.service.activity.ActivityOptionServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;

/**
 * @Title: interface
 * @Description: 活动报名选项内容接口实现
 * @author
 * @date 2015-08-07 17:56:10
 * @version V1.0
 * 
 */
@Service("activityOptionService")
@Transactional
public class ActivityOptionServiceImpl implements ActivityOptionServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存活动报名选项内容
	 * 
	 * @param activityOption
	 * @return
	 */
	public java.lang.String save(ActivityOptionEntity activityOption) {
		return (java.lang.String) commonService.save(activityOption);
	}

	/**
	 * 更新活动报名选项内容
	 * 
	 * @param activityOption
	 */
	@Override
	public void saveOrUpdate(ActivityOptionEntity activityOption) {
		commonService.saveOrUpdate(activityOption);
	}

	/**
	 * 通过id获取活动报名选项内容
	 * 
	 * @param id
	 *            活动报名选项内容id
	 * @return
	 */
	@Override
	public ActivityOptionEntity getEntity(java.lang.String id) {
		return commonService.getEntity(ActivityOptionEntity.class, id);
	}

	/**
	 * 获取分页后的活动报名选项内容数据集
	 * 
	 * @param activityOption
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return activityOptionList活动报名选项内容数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(ActivityOptionEntity activityOption, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ActivityOptionEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, activityOption, param);
		cq.addOrder("sort", SortDirection.desc);
		cq.addOrder("createtime", SortDirection.desc);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ActivityOptionEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("activityOptionList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除活动报名选项内容
	 * 
	 * @param activityOption
	 */
	@Override
	public void delete(ActivityOptionEntity activityOption) {
		commonService.delete(activityOption);
	}
	
}
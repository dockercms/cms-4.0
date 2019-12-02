package com.leimingtech.cms.service.impl.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.activity.ActivityPeopleServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.ActivityEntity;
import com.leimingtech.core.entity.ActivityPeopleEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;

/**
 * @Title: interface
 * @Description: 活动报名人表接口实现
 * @author
 * @date 2015-08-28 17:57:43
 * @version V1.0
 * 
 */
@Service("activityPeopleService")
@Transactional
public class ActivityPeopleServiceImpl implements ActivityPeopleServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存活动报名人表
	 * 
	 * @param activityPeople
	 * @return
	 */
	public java.lang.String save(ActivityPeopleEntity activityPeople) {
		return (java.lang.String) commonService.save(activityPeople);
	}

	/**
	 * 更新活动报名人表
	 * 
	 * @param activityPeople
	 */
	@Override
	public void saveOrUpdate(ActivityPeopleEntity activityPeople) {
		commonService.saveOrUpdate(activityPeople);
	}

	/**
	 * 通过id获取活动报名人表
	 * 
	 * @param id
	 *            活动报名人表id
	 * @return
	 */
	@Override
	public ActivityPeopleEntity getEntity(java.lang.String id) {
		return commonService.getEntity(ActivityPeopleEntity.class, id);
	}

	/**
	 * 获取分页后的活动报名人表数据集
	 * 
	 * @param activityPeople
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return activityPeopleList活动报名人表数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(ActivityPeopleEntity activityPeople, Map param,
			int pageSize, int pageNo) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		// 栏目id，用于返回
		String contentCatId = request.getParameter("contentCatId");
				// 内容id
		String contentsId = request.getParameter("contentsId");
		List<ActivityEntity> activityList = commonService.findByProperty(ActivityEntity.class, "contentid", String.valueOf(contentsId));
		// 活动id
		String activityId = "0";
		if(activityList.size()!=0){
			activityId = activityList.get(0).getId();
		}
		
		CriteriaQuery cq = new CriteriaQuery(ActivityPeopleEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, activityPeople, param);
		cq.eq("activityids", activityId);
		// 排序条件
		cq.addOrder("createtime", SortDirection.desc);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ActivityPeopleEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		
		m.put("activityPeopleList", resultList);
		m.put("pageCount", pageCount);
		m.put("contentCatId", contentCatId);
		return m;
	}

	/**
	 * 删除活动报名人表
	 * 
	 * @param activityPeople
	 */
	@Override
	public void delete(ActivityPeopleEntity activityPeople) {
		commonService.delete(activityPeople);
	}
	
}
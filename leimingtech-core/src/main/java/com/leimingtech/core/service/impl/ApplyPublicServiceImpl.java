package com.leimingtech.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.ApplyPublicEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ApplyPublicServiceI;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * @Title: interface
 * @Description: 申请公开接口实现
 * @author
 * @date 2016-04-05 16:57:35
 * @version V1.0
 * 
 */
@Service("applyPublicService")
@Transactional
public class ApplyPublicServiceImpl implements ApplyPublicServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存申请公开
	 * 
	 * @param applyPublic
	 * @return
	 */
	public java.lang.String save(ApplyPublicEntity applyPublic) {
		return (java.lang.String) commonService.save(applyPublic);
	}

	/**
	 * 更新申请公开
	 * 
	 * @param applyPublic
	 */
	@Override
	public void saveOrUpdate(ApplyPublicEntity applyPublic) {
		commonService.saveOrUpdate(applyPublic);
	}

	/**
	 * 通过id获取申请公开
	 * 
	 * @param id
	 *            申请公开id
	 * @return
	 */
	@Override
	public ApplyPublicEntity getEntity(java.lang.String id) {
		return commonService.getEntity(ApplyPublicEntity.class, id);
	}

	/**
	 * 获取分页后的申请公开数据集
	 * 
	 * @param applyPublic
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return applyPublicList申请公开数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(ApplyPublicEntity applyPublic, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ApplyPublicEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, applyPublic, param);
		cq.addOrder("createtime",SortDirection.desc);
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ApplyPublicEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("applyPublicList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除申请公开
	 * 
	 * @param applyPublic
	 */
	@Override
	public void delete(ApplyPublicEntity applyPublic) {
		commonService.delete(applyPublic);
	}
	
}
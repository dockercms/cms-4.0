package com.leimingtech.mobile.service.impl.relatecontent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.mobile.entity.relatecontent.RelateContentMobileEntity;
import com.leimingtech.mobile.service.relatecontent.RelateContentMobileServiceI;

/**
 * @Title: interface
 * @Description: 相关内容接口实现
 * @author
 * @date 2015-06-30 15:11:10
 * @version V1.0
 * 
 */
@Service("relateContentMobileService")
@Transactional
public class RelateContentMobileServiceImpl implements RelateContentMobileServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存相关内容
	 * 
	 * @param relateContentMobile
	 * @return
	 */
	public java.lang.String save(RelateContentMobileEntity relateContentMobile) {
		return (java.lang.String) commonService.save(relateContentMobile);
	}

	/**
	 * 更新相关内容
	 * 
	 * @param relateContentMobile
	 */
	@Override
	public void saveOrUpdate(RelateContentMobileEntity relateContentMobile) {
		commonService.saveOrUpdate(relateContentMobile);
	}

	/**
	 * 通过id获取相关内容
	 * 
	 * @param id
	 *            相关内容id
	 * @return
	 */
	@Override
	public RelateContentMobileEntity getEntity(java.lang.String id) {
		return commonService.getEntity(RelateContentMobileEntity.class, id);
	}

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
	@Override
	public Map<String, Object> getPageList(RelateContentMobileEntity relateContentMobile, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(RelateContentMobileEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, relateContentMobile, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<RelateContentMobileEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("relateContentMobileList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除相关内容
	 * 
	 * @param relateContentMobile
	 */
	@Override
	public void delete(RelateContentMobileEntity relateContentMobile) {
		commonService.delete(relateContentMobile);
	}

	/**
	 * 通过移动稿件主表id获取关联的稿件
	 * 
	 * @param contentID
	 * @return
	 */
	@Override
	public List<RelateContentMobileEntity> getListByContentID(String contentID) {
		CriteriaQuery cq=new CriteriaQuery(RelateContentMobileEntity.class);
		cq.eq("contentId", contentID);
		cq.addOrder("relateOrder", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}
	
}
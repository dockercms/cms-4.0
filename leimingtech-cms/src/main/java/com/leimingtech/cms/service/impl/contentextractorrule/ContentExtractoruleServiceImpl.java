package com.leimingtech.cms.service.impl.contentextractorrule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.contentextractorrule.ContentExtractoruleServiceI;
import com.leimingtech.core.entity.ContentExtractoruleEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;

/**
 * @Title: interface
 * @Description: 一键抓取规则管理接口实现
 * @author
 * @date 2015-08-04 16:53:56
 * @version V1.0
 * 
 */
@Service("contentExtractoruleService")
@Transactional
public class ContentExtractoruleServiceImpl implements ContentExtractoruleServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存一键抓取规则管理
	 * 
	 * @param contentExtractorule
	 * @return
	 */
	public java.lang.String save(ContentExtractoruleEntity contentExtractorule) {
		return (java.lang.String) commonService.save(contentExtractorule);
	}

	/**
	 * 更新一键抓取规则管理
	 * 
	 * @param contentExtractorule
	 */
	@Override
	public void saveOrUpdate(ContentExtractoruleEntity contentExtractorule) {
		commonService.saveOrUpdate(contentExtractorule);
	}

	/**
	 * 通过id获取一键抓取规则管理
	 * 
	 * @param id
	 *            一键抓取规则管理id
	 * @return
	 */
	@Override
	public ContentExtractoruleEntity getEntity(java.lang.String id) {
		return commonService.getEntity(ContentExtractoruleEntity.class, id);
	}

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
	@Override
	public Map<String, Object> getPageList(ContentExtractoruleEntity contentExtractorule, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ContentExtractoruleEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, contentExtractorule, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ContentExtractoruleEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentExtractoruleList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除一键抓取规则管理
	 * 
	 * @param contentExtractorule
	 */
	@Override
	public void delete(ContentExtractoruleEntity contentExtractorule) {
		commonService.delete(contentExtractorule);
	}
	
}
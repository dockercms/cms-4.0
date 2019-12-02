package com.leimingtech.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.WebErrorEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.WebErrorServiceI;

/**
 * @Title: interface
 * @Description: 网站纠错接口实现
 * @author
 * @date 2016-04-01 15:02:22
 * @version V1.0
 * 
 */
@Service("webErrorService")
@Transactional
public class WebErrorServiceImpl implements WebErrorServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存网站纠错
	 * 
	 * @param webError
	 * @return
	 */
	public java.lang.String save(WebErrorEntity webError) {
		return (java.lang.String) commonService.save(webError);
	}

	/**
	 * 更新网站纠错
	 * 
	 * @param webError
	 */
	@Override
	public void saveOrUpdate(WebErrorEntity webError) {
		commonService.saveOrUpdate(webError);
	}

	/**
	 * 通过id获取网站纠错
	 * 
	 * @param id
	 *            网站纠错id
	 * @return
	 */
	@Override
	public WebErrorEntity getEntity(java.lang.String id) {
		return commonService.getEntity(WebErrorEntity.class, id);
	}

	/**
	 * 获取分页后的网站纠错数据集
	 * 
	 * @param webError
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return webErrorList网站纠错数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(WebErrorEntity webError, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(WebErrorEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, webError, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<WebErrorEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("webErrorList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除网站纠错
	 * 
	 * @param webError
	 */
	@Override
	public void delete(WebErrorEntity webError) {
		commonService.delete(webError);
	}
	
}
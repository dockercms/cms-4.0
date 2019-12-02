package com.leimingtech.weibo.service.impl.sinacontent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.weibo.entity.sinacontent.SinaContentEntity;
import com.leimingtech.weibo.service.sinacontent.SinaContentServiceI;

/**
 * @Title: interface
 * @Description: 新浪微博内容接口实现
 * @author
 * @date 2015-12-05 13:56:58
 * @version V1.0
 * 
 */
@Service("sinaContentService")
@Transactional
public class SinaContentServiceImpl implements SinaContentServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存新浪微博内容
	 * 
	 * @param sinaContent
	 * @return
	 */
	public java.lang.String save(SinaContentEntity sinaContent) {
		return (java.lang.String) commonService.save(sinaContent);
	}

	/**
	 * 更新新浪微博内容
	 * 
	 * @param sinaContent
	 */
	@Override
	public void saveOrUpdate(SinaContentEntity sinaContent) {
		commonService.saveOrUpdate(sinaContent);
	}

	/**
	 * 通过id获取新浪微博内容
	 * 
	 * @param id
	 *            新浪微博内容id
	 * @return
	 */
	@Override
	public SinaContentEntity getEntity(java.lang.String id) {
		return commonService.getEntity(SinaContentEntity.class, id);
	}

	/**
	 * 获取分页后的新浪微博内容数据集
	 * 
	 * @param sinaContent
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return sinaContentList新浪微博内容数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(SinaContentEntity sinaContent, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(SinaContentEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, sinaContent, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<SinaContentEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sinaContentList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除新浪微博内容
	 * 
	 * @param sinaContent
	 */
	@Override
	public void delete(SinaContentEntity sinaContent) {
		commonService.delete(sinaContent);
	}
	
}
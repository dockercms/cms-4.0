package com.leimingtech.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.TypeServiceI;



/**
 * 通用类型字典管理接口实现
 * 
 * @author liuzhen 2015年9月1日 11:49:16
 * 
 */
@Service("typeService")
@Transactional
public class TypeServiceImpl implements TypeServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存通用类型字典
	 * 
	 * @param type
	 * @return
	 */
	public java.lang.String save(TSType type) {
		return (java.lang.String) commonService.save(type);
	}

	/**
	 * 更新通用类型字典
	 * 
	 * @param type
	 */
	@Override
	public void saveOrUpdate(TSType type) {
		commonService.saveOrUpdate(type);
	}

	/**
	 * 通过id获取通用类型字典
	 * 
	 * @param id
	 *            通用类型字典id
	 * @return
	 */
	@Override
	public TSType getEntity(java.lang.String id) {
		return commonService.getEntity(TSType.class, id);
	}

	/**
	 * 获取分页后的通用类型字典数据集
	 * 
	 * @param type
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return typeList通用类型字典数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(TSType type, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(TSType.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, type, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<TSType> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("typeList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除通用类型字典
	 * 
	 * @param type
	 */
	@Override
	public void delete(TSType type) {
		commonService.delete(type);
	}

}

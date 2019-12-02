package com.leimingtech.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.TypegroupServiceI;



/**
 * @Title: interface
 * @Description: 数据字典分类接口实现
 * @author
 * @date 2015-09-01 18:46:04
 * @version V1.0
 * 
 */
@Service("typegroupService")
@Transactional
public class TypegroupServiceImpl implements TypegroupServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存数据字典分类
	 * 
	 * @param typegroup
	 * @return
	 */
	public java.lang.String save(TSTypegroup typegroup) {
		return (java.lang.String) commonService.save(typegroup);
	}

	/**
	 * 更新数据字典分类
	 * 
	 * @param typegroup
	 */
	@Override
	public void saveOrUpdate(TSTypegroup typegroup) {
		commonService.saveOrUpdate(typegroup);
	}

	/**
	 * 通过id获取数据字典分类
	 * 
	 * @param id
	 *            数据字典分类id
	 * @return
	 */
	@Override
	public TSTypegroup getEntity(java.lang.String id) {
		return commonService.getEntity(TSTypegroup.class, id);
	}

	/**
	 * 获取分页后的数据字典分类数据集
	 * 
	 * @param typegroup
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return typegroupList数据字典分类数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(TSTypegroup typegroup, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(TSTypegroup.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, typegroup, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<TSTypegroup> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("typegroupList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除数据字典分类
	 * 
	 * @param typegroup
	 */
	@Override
	public void delete(TSTypegroup typegroup) {
		commonService.delete(typegroup);
	}

	/**
	 * 通过code获取数据字典分类
	 * 
	 * @param code
	 * @return
	 */
	@Override
	public TSTypegroup findByTypegroupCode(String code) {
		return commonService.findUniqueByProperty(TSTypegroup.class, "typegroupcode", code);
	}
	
}
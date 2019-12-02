package com.leimingtech.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.EmailAddressEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.EmailAddressServiceI;

/**
 * @Title: interface
 * @Description: 信访邮箱接口实现
 * @author
 * @date 2016-04-01 11:11:36
 * @version V1.0
 * 
 */
@Service("emailAddressService")
@Transactional
public class EmailAddressServiceImpl implements EmailAddressServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存信访邮箱
	 * 
	 * @param emailAddress
	 * @return
	 */
	public java.lang.String save(EmailAddressEntity emailAddress) {
		return (java.lang.String) commonService.save(emailAddress);
	}

	/**
	 * 更新信访邮箱
	 * 
	 * @param emailAddress
	 */
	@Override
	public void saveOrUpdate(EmailAddressEntity emailAddress) {
		commonService.saveOrUpdate(emailAddress);
	}

	/**
	 * 通过id获取信访邮箱
	 * 
	 * @param id
	 *            信访邮箱id
	 * @return
	 */
	@Override
	public EmailAddressEntity getEntity(java.lang.String id) {
		return commonService.getEntity(EmailAddressEntity.class, id);
	}

	/**
	 * 获取分页后的信访邮箱数据集
	 * 
	 * @param emailAddress
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return emailAddressList信访邮箱数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(EmailAddressEntity emailAddress, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(EmailAddressEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, emailAddress, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<EmailAddressEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("emailAddressList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除信访邮箱
	 * 
	 * @param emailAddress
	 */
	@Override
	public void delete(EmailAddressEntity emailAddress) {
		commonService.delete(emailAddress);
	}
	
}
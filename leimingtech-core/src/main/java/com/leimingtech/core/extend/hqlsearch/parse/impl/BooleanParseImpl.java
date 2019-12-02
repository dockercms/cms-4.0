package com.leimingtech.core.extend.hqlsearch.parse.impl;

import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.extend.hqlsearch.parse.IHqlParse;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;



/**
 *
 * @author 谢进伟
 *
 * @date 2015-8-16 上午11:22:13
 */

public class BooleanParseImpl implements IHqlParse {

	
	public void addCriteria(CriteriaQuery cq, String name, Object value) {
		if (HqlGenerateUtil.isNotEmpty(value))
			cq.eq(name, value);
	}

	
	public void addCriteria(CriteriaQuery cq, String name, Object value,
			String beginValue, String endValue) {
		if (HqlGenerateUtil.isNotEmpty(beginValue)) {
			cq.ge(name, Boolean.parseBoolean(beginValue));
		}
		if (HqlGenerateUtil.isNotEmpty(endValue)) {
			cq.le(name, Boolean.parseBoolean(endValue));
		}
		if (HqlGenerateUtil.isNotEmpty(value)) {
			cq.eq(name, value);
		}
	}

}

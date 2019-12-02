package com.leimingtech.core.extend.hqlsearch.parse.impl;

import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.extend.hqlsearch.parse.IHqlParse;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;


public class FloatParseImpl implements IHqlParse {

	
	public void addCriteria(CriteriaQuery cq, String name, Object value) {
		if (HqlGenerateUtil.isNotEmpty(value))
			cq.eq(name, value);
	}

	
	public void addCriteria(CriteriaQuery cq, String name, Object value,
			String beginValue, String endValue) {
		if (HqlGenerateUtil.isNotEmpty(beginValue)) {
			cq.ge(name, Float.parseFloat(beginValue));
		}
		if (HqlGenerateUtil.isNotEmpty(endValue)) {
			cq.le(name, Float.parseFloat(endValue));
		}
		if (HqlGenerateUtil.isNotEmpty(value)) {
			cq.eq(name, value);
		}
	}

}

package com.leimingtech.cms.service.impl.acquisition;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.acquisition.AcquisitionHistoryEntity;
import com.leimingtech.cms.service.acquisition.AcquisitionHistoryServiceI;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.impl.CommonServiceImpl;

/**
 * 采集历史记录管理实现
 * 
 * @author liuzhen 2014年4月17日 14:25:36
 * 
 */
@Service("acquisitionHistoryService")
@Transactional
public class AcquisitionHistoryServiceImpl extends CommonServiceImpl implements AcquisitionHistoryServiceI {

	/**
	 * 判断当前内容是否已经被采集
	 * 
	 * @param Boolean
	 *            title 传递参数是否是标题
	 * @param String
	 *            value 当title为true value值标题      当title为false value值是URL
	 */
	public Boolean checkExistByProperties(Boolean title, String value) {
		CriteriaQuery cq = new CriteriaQuery(AcquisitionHistoryEntity.class);
		if (title) {
			cq.add(Restrictions.eq("title", value));
		} else {
			cq.add(Restrictions.eq("contentUrl", value));
		}
		List<AcquisitionHistoryEntity> acquisitionHistoryList = getListByCriteriaQuery(cq, false);
		return acquisitionHistoryList.size() > 0 ? true : false;
	}

}
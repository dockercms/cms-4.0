package com.leimingtech.cms.service.impl.acquisition;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.acquisition.AcquisitionreplaceEntity;
import com.leimingtech.cms.service.acquisition.AcquisitionreplaceServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.impl.CommonServiceImpl;

/**
 * 接口实现 数据采集关联表 内容替换
 * 
 * @author liuzhen 2015年4月7日 16:13:46
 * 
 */
@Service("acquisitionreplaceService")
@Transactional
public class AcquisitionreplaceServiceImpl extends CommonServiceImpl implements AcquisitionreplaceServiceI {

	/**
	 * 根据采集id获取内容替换集合<br/>
	 * 按照创建时间倒序返回数据
	 * 
	 * @param acqId
	 *            采集id
	 * @return 内容替换集合
	 */
	@Override
	public List<AcquisitionreplaceEntity> getListByAcq(String acqId) {

		if (StringUtils.isEmpty(acqId))
			return null;

		CriteriaQuery cq = new CriteriaQuery(AcquisitionreplaceEntity.class);
		cq.eq("acquisitionId", acqId);
		cq.addOrder("createtime", SortDirection.asc);
		cq.add();
		return getListByCriteriaQuery(cq, false);
	}

}
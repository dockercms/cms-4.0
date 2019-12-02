package com.leimingtech.cms.service.impl.advertising;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.advertising.AdvertisingEntity;
import com.leimingtech.cms.entity.advertising.AdvertisingSpaceEntity;
import com.leimingtech.cms.service.advertising.AdvertisingSpaceServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * 广告位管理接口实现
 * 
 * @author liuzhen 2014年8月6日 20:15:01
 * 
 */
@Service("advertisingSpaceService")
@Transactional
public class AdvertisingSpaceServiceImpl extends CommonServiceImpl implements AdvertisingSpaceServiceI {

	public void delMain(AdvertisingSpaceEntity advertisingSpace) {
		// 删除主表信息
		this.delete(advertisingSpace);
		// 获取参数
		Object id0 = advertisingSpace.getId();
		// 删除-动态报表配置明细
		String hql0 = "from AdvertisingEntity where 1 = 1 AND gid = ? ";
		List<AdvertisingEntity> advertisingOldList = this.findHql(hql0, id0);
		this.deleteAllEntitie(advertisingOldList);
	}

	/**
	 * 获取开启的广告位列表
	 * 
	 * @return
	 */
	@Override
	public List<AdvertisingSpaceEntity> getOpenAdvSpaceList() {
		CriteriaQuery cq = new CriteriaQuery(AdvertisingSpaceEntity.class);
		cq.eq("isEnabled", "1");
		cq.eq("siteId", PlatFormUtil.getSessionSiteId());
		cq.addOrder("id", SortDirection.desc);
		cq.add();

		List<AdvertisingSpaceEntity> advertisingSpaceList = getListByCriteriaQuery(cq, false);

		return advertisingSpaceList;
	}

	/**
	 * 获取当前站点下全部广告位
	 * 
	 * @return
	 */
	@Override
	public List<AdvertisingSpaceEntity> getAll() {
		CriteriaQuery cq = new CriteriaQuery(AdvertisingSpaceEntity.class);
		cq.eq("siteId", PlatFormUtil.getSessionSiteId());
		cq.addOrder("id", SortDirection.desc);
		cq.add();

		List<AdvertisingSpaceEntity> advertisingSpaceList = getListByCriteriaQuery(cq, false);
		return advertisingSpaceList;
	}

}
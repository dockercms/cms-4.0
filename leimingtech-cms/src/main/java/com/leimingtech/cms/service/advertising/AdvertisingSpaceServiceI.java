package com.leimingtech.cms.service.advertising;

import java.util.List;

import com.leimingtech.cms.entity.advertising.AdvertisingSpaceEntity;
import com.leimingtech.core.service.CommonService;

/**
 * 广告位管理接口
 * 
 * @author liuzhen 2014年8月6日 20:16:01
 * 
 */
public interface AdvertisingSpaceServiceI extends CommonService {

	public void delMain(AdvertisingSpaceEntity advertisingSpace);

	/**
	 * 获取开启的广告位列表
	 * 
	 * @return
	 */
	public List<AdvertisingSpaceEntity> getOpenAdvSpaceList();

	/**
	 * 获取当前站点下全部广告位
	 * 
	 * @return
	 */
	public List<AdvertisingSpaceEntity> getAll();

}

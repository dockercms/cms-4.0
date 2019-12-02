package com.leimingtech.cms.service.advertising;

import java.util.List;
import java.util.Map;

import com.leimingtech.cms.entity.advertising.AdvertisingEntity;
import com.leimingtech.core.service.CommonService;

/**
 * 广告管理接口
 * 
 * @author liuzhen 2014年4月25日 09:53:04
 * 
 */
public interface AdvertisingServiceI extends CommonService {
	/**
	 * 删除广告
	 * 
	 * @param advertising
	 *            广告
	 */
	public void delMain(AdvertisingEntity advertising);

	/**
	 * 获取广告列表 （此方法由标签调用）
	 * 
	 * @param params
	 *            广告参数<br/>
	 *            key(name) value=可以是广告位名称（全名）也可以是广告位id <br/>
	 *            key(count) value=获取条数 默认全部
	 * @return List 广告列表
	 */
	public List<AdvertisingEntity> getAdvListByTag(Map params);

	/**
	 * 广告展现次数增加
	 * @param advId 广告id
	 */
	public void advDisplayAdd(String advId);

	/**
	 * 广告点击次数增加
	 * @param advId 广告id
	 */
	public void advClickAdd(String advId);
}

package com.leimingtech.cms.service;

import java.util.List;
import java.util.Map;

import com.leimingtech.cms.entity.friendlink.FriendLinkEntity;
import com.leimingtech.core.service.CommonService;

/**
 * 友情链接接口
 * 
 * @author liuzhen 2014年4月25日 14:41:03
 * 
 */
public interface FriendLinkServiceI extends CommonService {
	public void delMain(FriendLinkEntity friendLink);

	/**
	 * 获取友情链接 （此方法由标签调用）
	 * 
	 * @param params
	 *            友情链接参数<br/>
	 *            key(count) 获取数据条数<br/>
	 *            key(name) 类型id或类型全名
	 * @return list 友情链接集合
	 */
	public List<FriendLinkEntity> getFriendLinkListByTag(Map params);
}

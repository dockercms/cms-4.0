package com.leimingtech.cms.service;

import java.util.List;

import com.leimingtech.cms.entity.friendlink.FriendLinkCtgEntity;
import com.leimingtech.core.service.CommonService;

/**
 * 友情链接类别管理接口
 * 
 * @author liuzhen 2014年8月14日 09:33:22
 * 
 */
public interface FriendLinkCtgServiceI extends CommonService {

	public void delMain(FriendLinkCtgEntity friendLinkCtg);

	/**
	 * 获取当前站点中全部友情链接类别
	 * 
	 * @return
	 */
	public List<FriendLinkCtgEntity> getAll();
}

package com.leimingtech.cms.service.tag;

import java.util.Map;

/**
 * 友情链接分类接口
 * 
 * @author liuzhen 2014年5月21日 17:45:04
 * 
 */
public interface IFriendLinkCtgTagMng{

	/**
	 * 通过标签传递参数获取友情链接分类
	 * 
	 * @param params
	 * @return
	 */
	Object getFriendLinkCtg(Map params);
}

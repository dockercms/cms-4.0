package com.leimingtech.cms.service.tag;

import com.leimingtech.core.entity.ActivityEntity;

/**
 * 活动标签接口
 * 
 * @author liuzhen 2014年5月21日 17:45:04
 * 
 */
public interface IActivityTagMng {

	/**
	 * 通过标签传递参数获取投票
	 * 
	 * @param params
	 * @return
	 */
	ActivityEntity getActivity(String name);

	
}

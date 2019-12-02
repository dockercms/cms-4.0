package com.leimingtech.cms.service.tag;

import java.util.Map;

import com.leimingtech.core.service.CommonService;

/**
 * 组图标签接口
 * 
 * @author liuzhen 2014年5月26日 17:56:05
 * 
 */
public interface IPictureGroupTagMng extends CommonService{

	/**
	 * 获取组图
	 * 
	 * @param params
	 * @return
	 */
	Object getPictureGroupByTag(Map params);

	/**
	 * 获取组图数据
	 * 
	 * @param params
	 * @return
	 */
	Object getPictureDataByTag(Map params);

}

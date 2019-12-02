package com.leimingtech.cms.service;

import com.leimingtech.core.service.CommonService;

/**
 * 移动内容操作接口
 * 
 * @author liuzhen 2015-1-21 12:18:45
 * 
 */
public interface ContentsOperateServiceI extends CommonService {

	/**
	 * 取消置顶
	 * 
	 * @param contentId
	 *           内容id
	 * @return 是否操作成功
	 */
	public Boolean cancelTop(String contentId);

	/**
	 * 获取当前内容中最大置顶值
	 * 
	 * @return
	 */
	public Integer getCurrentMaxTop();

	/**
	 * 设置置顶内容置顶
	 * 
	 * @param contentId
	 *            内容id
	 * @param setTopValue
	 *            置顶值
	 * @return 是否操作成功
	 */
	public Boolean setTop(String contentId, Integer setTopValue);

	
}

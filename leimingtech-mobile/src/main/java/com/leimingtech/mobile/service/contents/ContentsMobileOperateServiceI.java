package com.leimingtech.mobile.service.contents;

import com.leimingtech.core.service.CommonService;

/**
 * 移动内容操作接口
 * 
 * @author liuzhen 2015-1-21 12:18:45
 * 
 */
public interface ContentsMobileOperateServiceI extends CommonService {

	/**
	 * 取消置顶
	 * 
	 * @param contentId
	 *            移动内容id
	 * @return 是否操作成功
	 */
	public Boolean cancelTop(String contentId);

	/**
	 * 获取当前移动内容中最大置顶值
	 * 
	 * @return
	 */
	public Integer getCurrentMaxTop();

	/**
	 * 设置置顶移动内容置顶
	 * 
	 * @param contentId
	 *            移动内容id
	 * @param setTopValue
	 *            置顶值
	 * @return 是否操作成功
	 */
	public Boolean setTop(String contentId, Integer setTopValue);

	/**
	 * 更改移动头图<br/>
	 * 调用方法如果当前内容已经为头图将会取消头图,否则将设为头图
	 * 
	 * @param contentId
	 * @return
	 */
	public Boolean changeTopPic(String contentId);
}

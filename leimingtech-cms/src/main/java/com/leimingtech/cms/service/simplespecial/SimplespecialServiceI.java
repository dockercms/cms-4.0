package com.leimingtech.cms.service.simplespecial;

import net.sf.json.JSONObject;

import com.leimingtech.core.service.CommonService;

public interface SimplespecialServiceI extends CommonService{
	
	/**
	 * 加载所有专题
	 * @return
	 */
	public JSONObject loadSimpleSpecial(int pageSize,int pageNo);
	/**
	 * 根据专题id获取专题下内容集合
	 * @param simpleSpecialId
	 * @return
	 */
	public JSONObject loadSimpleSpecialContent(int pageSize,int pageNo,String simpleSpecialId,int isTop);
	/**
	 * 根据专题id解除所选的内容关联
	 * @param contentId
	 * @param simplespecial
	 * @return
	 */
	public JSONObject unlockContent(String contentId,String simplespecial);
	/**
	 * 取消置顶
	 * 
	 * @param contentId
	 *           专题id
	 * @return 是否操作成功
	 */
	public Boolean cancelTop(String simplespecialId);

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
	 *            专题id
	 * @param setTopValue
	 *            置顶值
	 * @return 是否操作成功
	 */
	public Boolean setTop(String simplespecialId, Integer setTopValue);
}

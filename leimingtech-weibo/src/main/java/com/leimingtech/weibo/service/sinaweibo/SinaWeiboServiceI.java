package com.leimingtech.weibo.service.sinaweibo;

import java.util.Map;

import com.leimingtech.weibo.entity.sinaweibo.SinaWeiboEntity;

/**
 * @Title: interface
 * @Description: 新浪微博接口
 * @author
 * @date 2015-12-03 14:22:47
 * @version V1.0
 * 
 */
public interface SinaWeiboServiceI {

	/**
	 * 保存新浪微博
	 * 
	 * @param sinaWeibo
	 * @return
	 */
	java.lang.String save(SinaWeiboEntity sinaWeibo);

	/**
	 * 更新新浪微博
	 * 
	 * @param sinaWeibo
	 */
	void saveOrUpdate(SinaWeiboEntity sinaWeibo);

	/**
	 * 通过id获取新浪微博
	 * 
	 * @param id
	 *            新浪微博id
	 * @return
	 */
	SinaWeiboEntity getEntity(java.lang.String id);

	/**
	 * 通过站点id获取新浪微博
	 * 
	 * @param id
	 *            站点id
	 * @return
	 */
	SinaWeiboEntity getSitEntity(java.lang.String id);

	
	/**
	 * 获取微博设置信息
	 * 
	 * @param id
	 *            信息内容
	 * @return
	 */
	String getWeiboinfo(java.lang.String id,java.lang.String site);
	
	/**
	 * 获取分页后的新浪微博数据集
	 * 
	 * @param sinaWeibo
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return sinaWeiboList新浪微博数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(SinaWeiboEntity sinaWeibo, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除新浪微博
	 * 
	 * @param sinaWeibo
	 */
	void delete(SinaWeiboEntity sinaWeibo);

}

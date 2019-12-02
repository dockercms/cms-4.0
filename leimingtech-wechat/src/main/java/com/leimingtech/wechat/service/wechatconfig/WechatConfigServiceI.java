package com.leimingtech.wechat.service.wechatconfig;

import java.util.Map;

import com.leimingtech.core.entity.WechatConfigEntity;

/**
 * @Title: interface
 * @Description: 微信参数接口
 * @author
 * @date 2015-08-13 14:56:07
 * @version V1.0
 * 
 */
public interface WechatConfigServiceI {

	/**
	 * 保存微信参数
	 * 
	 * @param wechatConfig
	 * @return
	 */
	String save(WechatConfigEntity wechatConfig);

	/**
	 * 更新微信参数
	 *
	 * @param wechatConfig
	 */
	void saveOrUpdate(WechatConfigEntity wechatConfig);

	/**
	 * 通过id获取微信参数
	 *
	 * @param id
	 *            微信参数id
	 * @return
	 */
	WechatConfigEntity getEntity(String id);

	/**
	 * 获取分页后的微信参数数据集
	 * 
	 * @param wechatConfig
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return wechatConfigList微信参数数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(WechatConfigEntity wechatConfig, Map param, int pageSize,
									int pageNo);

	/**
	 * 删除微信参数
	 * 
	 * @param wechatConfig
	 */
	void delete(WechatConfigEntity wechatConfig);

}

package com.leimingtech.wechat.service.wechatconfigure;

import com.leimingtech.wechat.entity.wechatconfigure.WechatConfigureEntity;

import java.util.Map;

/**
 * @Title: interface
 * @Description: 微信公众账号配置接口
 * @author
 * @date 2015-12-02 15:53:38
 * @version V1.0
 * 
 */
public interface WechatConfigureServiceI {

	/**
	 * 保存微信公众账号配置
	 * 
	 * @param wechatConfigure
	 * @return
	 */
	String save(WechatConfigureEntity wechatConfigure);

	/**
	 * 更新微信公众账号配置
	 *
	 * @param wechatConfigure
	 */
	void saveOrUpdate(WechatConfigureEntity wechatConfigure);

	/**
	 * 通过id获取微信公众账号配置
	 *
	 * @param id
	 *            微信公众账号配置id
	 * @return
	 */
	WechatConfigureEntity getEntity(String id);

	/**
	 * 获取分页后的微信公众账号配置数据集
	 * 
	 * @param wechatConfigure
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return wechatConfigureList微信公众账号配置数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(WechatConfigureEntity wechatConfigure, Map param, int pageSize,
									int pageNo);

	/**
	 * 删除微信公众账号配置
	 * 
	 * @param wechatConfigure
	 */
	void delete(WechatConfigureEntity wechatConfigure);

}

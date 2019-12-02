package com.leimingtech.wechat.service.wechatpush;

import com.leimingtech.wechat.entity.wechatcontent.WeChatContentEntity;
import com.leimingtech.wechat.entity.wechatpush.WeChatPushEntity;

import java.util.List;
import java.util.Map;

/**
 * @Title: interface
 * @Description: 微信推送接口
 * @author
 * @date 2015-09-11 14:47:12
 * @version V1.0
 * 
 */
public interface WeChatPushServiceI {

	/**
	 * 保存微信推送
	 * 
	 * @param weChatPush
	 * @return
	 */
	String save(WeChatPushEntity weChatPush);

	/**
	 * 更新微信推送
	 *
	 * @param weChatPush
	 */
	void saveOrUpdate(WeChatPushEntity weChatPush);

	/**
	 * 通过id获取微信推送
	 *
	 * @param id
	 *            微信推送id
	 * @return
	 */
	WeChatPushEntity getEntity(String id);

	/**
	 * 获取分页后的微信推送数据集
	 *
	 * @param weChatPush
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return weChatPushList微信推送数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(WeChatPushEntity weChatPush, Map param, int pageSize,
									int pageNo);

	/**
	 * 删除微信推送
	 *
	 * @param weChatPush
	 */
	void delete(WeChatPushEntity weChatPush);

	/***
	 * 获取全部数据
	 *
	 * @return
	 */
	List<WeChatPushEntity> getAllData();

	/**
	 * 通过主表id获取关联数据
	 *
	 * @param id
	 * @return
	 */
	List<WeChatContentEntity> getListByPid(String id);

	/**
	 * 获取分页后的微信内容数据集
	 *
	 * @param weChatContent
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @param id
	 *            主表id
	 * @return weChatContentList微信内容数据集 pageCount总页数
	 */
	Map<String, Object> getListByPid(WeChatContentEntity weChatContent, Map param, int pageSize,
									 int pageNo, String id);

	/**
	 * 删除微信内容
	 *
	 * @param weChatContent
	 */
	void delete(WeChatContentEntity weChatContent);

	/**
	 * 通过id获取微信内容
	 *
	 * @param id
	 *            微信内容id
	 * @return
	 */
	WeChatContentEntity getWeChatContentEntity(String id);

	/**
	 * 保存微信内容
	 *
	 * @param weChatContent
	 * @return
	 */
	String save(WeChatContentEntity weChatContent);

	/**
	 * 更新微信内容
	 * 
	 * @param weChatContent
	 */
	void saveOrUpdate(WeChatContentEntity weChatContent);
	
}

package com.leimingtech.wechat.service.wechatuser;

import com.leimingtech.wechat.entity.wechatuser.WechatUserEntity;

import java.util.Map;

/**
 * @Title: interface
 * @Description: 微信号管理接口
 * @author
 * @date 2015-08-12 18:18:13
 * @version V1.0
 * 
 */
public interface WechatUserServiceI {
	
	/**
	 * 保存微信号管理
	 * 
	 * @param wechatUser
	 * @return
	 */
	String save(WechatUserEntity wechatUser);

	/**
	 * 更新微信号管理
	 *
	 * @param wechatUser
	 */
	void saveOrUpdate(WechatUserEntity wechatUser);

	/**
	 * 通过id获取微信号管理
	 *
	 * @param id
	 *            微信号管理id
	 * @return
	 */
	WechatUserEntity getEntity(String id);
	
	/**
	 * 获取分页后的微信号管理数据集
	 * 
	 * @param wechatUser
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return wechatUserList微信号管理数据集 pageCount总页数
	 */
	Map<String , Object> getPageList(WechatUserEntity wechatUser, Map<?, ?> param, int pageSize, int pageNo);
	
	/**
	 * 删除微信号管理
	 * 
	 * @param wechatUser
	 */
	void delete(WechatUserEntity wechatUser);
	
	void autoGetWechatUserAccessToken();
}

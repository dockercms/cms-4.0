package com.leimingtech.wechat.service.wechatbutton;

import com.leimingtech.wechat.entity.wechatbutton.WechatButtonEntity;

import java.util.Map;

/**
 * @Title: interface
 * @Description: 自定义菜单管理接口
 * @author
 * @date 2015-08-12 18:20:35
 * @version V1.0
 * 
 */
public interface WechatButtonServiceI {
	
	/**
	 * 保存自定义菜单管理
	 * 
	 * @param wechatButton
	 * @return
	 */
	String save(WechatButtonEntity wechatButton);

	/**
	 * 更新自定义菜单管理
	 *
	 * @param wechatButton
	 */
	void saveOrUpdate(WechatButtonEntity wechatButton);

	/**
	 * 通过id获取自定义菜单管理
	 *
	 * @param id
	 *            自定义菜单管理id
	 * @return
	 */
	WechatButtonEntity getEntity(String id);
	
	/**
	 * 获取分页后的自定义菜单管理数据集
	 * 
	 * @param wechatButton
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return wechatButtonList自定义菜单管理数据集 pageCount总页数
	 */
	Map<String , Object> getPageList(WechatButtonEntity wechatButton, Map param, int pageSize, int pageNo);
	
	/**
	 * 删除自定义菜单管理
	 * 
	 * @param wechatButton
	 */
	void delete(WechatButtonEntity wechatButton);
	
	/**
	 * 自动同步微信服务气端菜单列表
	 */
	void autoGetWechatServerAllMenus();
	
}

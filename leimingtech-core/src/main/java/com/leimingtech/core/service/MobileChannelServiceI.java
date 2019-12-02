package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.leimingtech.core.entity.BeanListApi;
import com.leimingtech.core.entity.MobileChannelEntity;

/**
 * 移动频道管理接口
 * @author liuzhen 2015年8月31日 17:18:21
 *
 */
public interface MobileChannelServiceI{
	/**
	 * 频道列表
	 * @param all
	 * @param mobileChannelId
	 * @return String
	 */
	public JSONObject getmobileChannelList(String all,String mobileChannelId);
	/**
	 * 一级频道下所有频道
	 * @param mobileChannelList1
	 * @return
	 */
	public List<BeanListApi> getValue(List<MobileChannelEntity> mobileChannelList1);
	/**
	 * 设置过滤值为空的属性，使得生成的 json 字符串只包含非空的值
	 * @return
	 */
	public JsonConfig getJsonConfig();
	/**
	 * 获取内容类型
	 * @param classify
	 * @return
	 */
	public String getContentType(String classify);
	
	/**
	 * 给栏目子集排序
	 * 
	 * @param list
	 */
	public void sortChildCatalog(List<MobileChannelEntity> list);
	

	/**
	 * 获取第一级栏目
	 * 
	 * @return 栏目集合
	 */
	List<MobileChannelEntity> getRootMobileChannel();

	/**
	 * 通过栏目父id获取栏目集合
	 * 
	 * @param id
	 *            栏目id
	 * @return 栏目集合
	 */
	List<MobileChannelEntity> getListByPid(String id);

	
	/**
	 * 获取tree节点数据
	 * 
	 * @param id
	 *            选中的节点
	 * @return
	 */
	JSONArray getTreeJson(String id);
	
	
	public BeanListApi setValue();
	
	/**
	 * 保存移动频道
	 * 
	 * @param mobileChannel
	 * @return
	 */
	java.lang.String save(MobileChannelEntity mobileChannel);
	
	/**
	 * 更新移动频道
	 * 
	 * @param mobileChannel
	 */
	void saveOrUpdate(MobileChannelEntity mobileChannel);
	
	/**
	 * 通过id获取移动频道
	 * 
	 * @param id
	 *            移动频道id
	 * @return
	 */
	MobileChannelEntity getEntity(java.lang.String id);
	
	/**
	 * 获取分页后的移动频道数据集
	 * 
	 * @param mobileChannel
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return mobileChannelList移动频道数据集 pageCount总页数
	 */
	Map<String , Object> getPageList(MobileChannelEntity mobileChannel , Map param , int pageSize , int pageNo);
	
	/**
	 * 删除移动频道
	 * 
	 * @param mobileChannel
	 */
	void delete(MobileChannelEntity mobileChannel);
	
	/**
	 * 将指定同级目录中的频道更改为非头条 
	 * @param id
	 * @param pid
	 */
	public void updateToNotTop(String id, String pid);
	
	/**
	 * 根据一级频道获取下一级中是否已经存在头条频道
	 * 
	 * @param id
	 * @return
	 */
	public boolean isOneTop(String pid);
	
	/**
	 * 根据站点获取以及频道列表
	 * @param siteid
	 * @return
	 */
	public List<MobileChannelEntity> getRootMobileChannelBySite(String siteid);
}

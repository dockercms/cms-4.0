package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.leimingtech.core.entity.BeanListApi;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.SimplespecialEntity;

/**
 * 移动稿件管理接口
 * 
 * @author liuzhen 2015年8月31日 15:51:48
 * 
 */
public interface ContentsMobileServiceI{
	/**
	 * 内容列表
	 * 
	 * @param all
	 * @param mobileChannelId
	 * @param pageSize
	 * @param pageNo
	 * @return String
	 */
	public JSONObject getcontentsMobileList(String all,String mobileChannelId,int pageSize,int pageNo,
			int isTop,String contentIds,SimplespecialEntity simpleSpecial);
	/**
	 * 搜索内容列表
	 * @param all
	 * @param key
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public JSONObject getSearchContentList(String all,String key,int pageSize,int pageNo);
	/**
	 * 内容列表下新闻和头图内容
	 * @param contentsMobileList
	 * @param num
	 * @return
	 */
	public List<BeanListApi> getContentsMobileList(List<ContentsMobileEntity> contentsMobileList,int num,int pageNo);
	/**
	 * 内容列表下所有内容
	 * @param contentsMobileList
	 * @return
	 */
	public List<BeanListApi> getAllContentsMobileList(List<ContentsMobileEntity> contentsMobileList);
	/**
	 * 是否收藏
	 * @param contentId
	 * @param isCollect
	 * @return
	 */
	public JSONObject isCollect(String contentId,String isCollect);
	/**
	 * 
	 * @param contentsMobile
	 * @return
	 */
	public BeanListApi topAndList(ContentsMobileEntity contentsMobile);
	/**
	 * 删除内容
	 * @param ids
	 */
	public void delContent(String[] ids);
	
	public JSONObject savePushMobile(String contentId,String mobileChannelId,String classify);

	public void saveOrDel(String contentId, String specialids);

	/**
	 * 生成二维码
	 * @param contentId
	 * @throws Exception
	 */
	public void encode(String contentId) throws Exception;
	
	/**
	 * 浏览量递增
	 * 
	 * @param contentId
	 */
	public void addPv(String contentId);
	
	/**
	 * 保存移动稿件
	 * 
	 * @param mobile
	 * @return
	 */
	java.lang.String save(ContentsMobileEntity mobile);
	
	/**
	 * 更新移动稿件
	 * 
	 * @param mobile
	 */
	void saveOrUpdate(ContentsMobileEntity mobile);
	
	/**
	 * 通过id获取移动稿件
	 * 
	 * @param id
	 *            移动稿件id
	 * @return
	 */
	ContentsMobileEntity getEntity(java.lang.String id);
	
	/**
	 * 获取分页后的移动稿件数据集
	 * 
	 * @param mobile
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return moibleList移动稿件数据集 pageCount总页数
	 */
	Map<String , Object> getPageList(ContentsMobileEntity mobile , Map param , int pageSize , int pageNo);
	
	/**
	 * 删除移动稿件
	 * 
	 * @param mobile
	 */
	void delete(ContentsMobileEntity mobile);
	
	/**
	 * 通过频道删除包含稿件
	 * @param id
	 */
	public void deleteByCat(String id);
	
	/**
	 * 通过频道获取所有包含的稿件
	 * 
	 * @param id 频道id
	 * @return
	 */
	public List<ContentsMobileEntity> getListByCat(String id);
	
	/**
	 * 通过PC稿件获取关联的移动稿件
	 * @param id
	 * @return
	 */
	public List<ContentsMobileEntity> getListByRelevanceContent(String id);
	
	/**
	 * 根据多条件获取移动稿件
	 * 
	 * @param publishedEnd 发布结束时间
	 * @param publishedStart 发布开始时间
	 * @param tab tab=all/alone,分别表示显示全部/当前栏目下的所有内容
	 * @param mobileChannel 频道
	 * @param contentsMobile 移动稿件(此实体所有值在这里都是查询条件)
	 * @param param
	 * @param pageSize 每页条数
	 * @param pageNo 当前页码
	 * @return
	 */
	public Map<String,Object> getPageList(String publishedEnd,
			String publishedStart, String tab, MobileChannelEntity mobileChannel,
			ContentsMobileEntity contentsMobile, Map param, int pageSize,
			int pageNo);
	
	/**
	 * 根据稿件类别获取移动稿件
	 * 
	 * @param classify1 稿件类别
	 * @param contentsMobile 移动稿件
	 * @param param
	 * @param pageSize 每页大小
	 * @param pageNo 当前页码
	 * @return
	 */
	public Map<String, Object> getPageListByClassify(String classify1,
			ContentsMobileEntity contentsMobile, Map param, int pageSize,
			int pageNo);

}

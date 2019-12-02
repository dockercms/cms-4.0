package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;

import com.leimingtech.core.entity.ContentBean;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.hibernate.qbc.PageList;

/**
 * 内容管理接口
 * 
 * @author liuzhen 2014年8月1日 15:16:04
 */
public interface ContentsServiceI extends CommonService {
	/**
	 * 保存内容时会对应保存一条链接内容
	 * @param contents
	 */
	public void saveLinkContent(ContentsEntity contents, String funVal0);
	/**
	 * 根据参数不同获取内容（wap标签调用）
	 * 
	 * @param catalogid 栏目id
	 * @return
	 */
	Object getContentByTag(Map params);
	/**
	 * 获取当前站点中允许访问的文章
	 * @return Map key==>contentList 内容列表 key==>contentDataMap 内容包含数据列表
	 */
	public Map<String, Object> getAllOpenContent(SiteEntity site);
	/**
	 * 移动到、引用到内容
	 * @param ids
	 * @param contentCatId
	 * @param toType
	 * @return
	 */
	public String mobiles(String ids,String contentCatId,String toType,String siteId);
	/**
	 * 根据id获取文章
	 * @param id
	 * @return
	 */
	ContentsEntity getContensById(String id);
	/**
	 * 获取文章分页列表
	 * @param pagesize
	 * @param pageno
	 * @param map
	 * @param memberid   为－1时查询全部
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	PageList contentsList(Integer pageSize, Integer pageNo, Map map, String memberid, ContentsEntity contens);
	/**
	 * 根据id删除数据
	 * @param id
	 */
	void delContentsById(String id);
	/**
	 * 增加浏览量
	 * @param contentId
	 */
	void addPv(String contentId);
	/**
	 * 通过内容Id获取内容全部信息
	 * @param contentId
	 * @return
	 */
	ContentBean loadContentDetail(String contentId);
	/**
	 * 获取内容列表
	 * @return
	 */
	List<ContentsEntity> getContentsList(Map params);
	/**
	 * 保存内容及分类入口
	 * @param contents
	 */
	void saveContent(Map<String,Object> map);
	/**
	 * 
	 */
	void saveOtherContent(String contentId,String otherCatIds);
	 
	void saveModelExt(ContentsEntity contents);
	/**
	 * 在modelExt中保存modelId/attrName
	 * @param contents
	 */
	void saveModelItem(ContentsEntity contents);
	/**
	 * 保存相关内容
	 * @param contents
	 */
	void saveRelateContent(ContentsEntity contents,String temporary,String divValue);
	
	/**
	 * 根据内容id获取要索引的内容
	 * @param contentId
	 * @return
	 */
	Map<String, Object> getIndexContent(String contentId);
	/**
	 * 删除内容
	 * @param ids
	 */
	void delContent(String[] ids);
	
	/**
	 * 获取符合下线的内容
	 * @return
	 */
	List<ContentsEntity> getOfflineContent();
	/**
	 * 该用户角色所分配的所有栏目
	 * 
	 */
	String[] catRoleList();

	/**
	 * 获取栏目下允许查看的内容总数
	 * @param catalogIds
	 * @return
     */
	long getCatalogOpenContentCount(List<String> catalogIds);

	/**
	 * 获取栏目下最新的一条内容
	 * @param catalogId
	 * @return
     */
	ContentsEntity getCatalogOneChild(String catalogId);

	/**
	 *
	 * 增加赞同数
	 *
	 * @param contentId
     */
	Integer addSupport(String contentId);/**
	 *
	 * 增加反对数
	 *
	 * @param contentId
     */
	Integer addOppose(String contentId);

	Integer getSupport(String contentId);

	Integer getOppose(String contentId);

    long calculationCommentCount(String contentId);
}

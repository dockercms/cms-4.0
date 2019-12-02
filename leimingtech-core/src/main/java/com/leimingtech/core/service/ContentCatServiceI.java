package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.leimingtech.core.entity.ContentCatVOTreeEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.SiteEntity;

/**
 * 栏目管理接口
 * 
 * @author liuzhen 2014年8月8日 13:44:48
 * 
 */
public interface ContentCatServiceI {

	/**
	 * 保存栏目
	 * 
	 * @param contentCat
	 * @return
	 */
	String save(ContentCatEntity contentCat);

	/**
	 * 更新栏目
	 * 
	 * @param contentCat
	 */
	void saveOrUpdate(ContentCatEntity contentCat);

	/**
	 * 通过id获取栏目
	 * 
	 * @param id
	 *            栏目id
	 * @return
	 */
	ContentCatEntity getEntity(String id);

	/**
	 * 获取分页后的栏目数据集
	 * 
	 * @param contentCat
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return contentCatList栏目数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ContentCatEntity contentCat, Map param,
									int pageSize, int pageNo);

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	List<ContentCatEntity> findByQueryString(String hql);

	/**
	 * 删除栏目
	 * 
	 * @param contentCat
	 */
	void delete(ContentCatEntity contentCat);

	/**
	 * 获取当前站点中所有开启的栏目
	 * 
	 * @param iscatend
	 *            是否是只获取末级开启的栏目
	 * @return
	 */
	List<String> getAllOpenContentCatIdList(SiteEntity site, boolean iscatend);

	/**
	 * 获取当前站点中所有开启的栏目
	 * 
	 * @param iscatend
	 *            是否是只获取末级开启的栏目
	 * @return
	 */
	String[] getAllOpenContentCatIdArray(SiteEntity site, boolean iscatend);

	/**
	 * 栏目的jsonarray数据，供树形结构使用
	 * 
	 * @param models
	 *            栏目可提供的模型，如文章，组图。传入值例：{文章，组图}
	 * @param isleaf
	 *            是否为末级节点 0:不是末级节点 1:末级节点
	 * @param catids
	 *            加载栏目树形结构默认选中的栏目的id 例：1,2,3,4
	 * @param iscontribute
	 *            是否允许投搞,如果为空则为全部
	 * @return
	 */
	JSONArray getContentCatForJsonArray(String[] models, Integer isleaf,
										String catids, String iscontribute);

	void deleteContentAll(String[] idArray);

	/**
	 * 获取第一级栏目
	 * 
	 * @return 栏目集合
	 */
	List<ContentCatEntity> getRootContentCat();

	/**
	 * 通过栏目父id获取栏目集合
	 * 
	 * @param id
	 *            栏目id
	 * @return 栏目集合
	 */
	List<ContentCatEntity> getListByPid(String id);

	/**
	 * 获取开启的一级栏目
	 * 
	 * @param siteid
	 *            站点id
	 * @return 栏目集合
	 */
	List<ContentCatEntity> getOpenRootContentCat(String siteid);

	/**
	 * 获取tree节点数据
	 * 
	 * @param contentCat
	 *            选中的节点
	 * @param siteId
	 * @return
	 */
	JSONArray getTreeJson(ContentCatEntity contentCat, String siteId);

	/**
	 * 根据站点获取一级栏目列表
	 * 
	 * @param siteid
	 * @return
	 */
	List<ContentCatEntity> getRootContentCatBySite(String siteid);

	/**
	 * 获取当前站点中是首页的栏目
	 * 
	 * @return
	 */
	List<ContentCatEntity> getAllIndexContentCat();

	/**
	 * 获取当前站点中所有栏目数据，treedata
	 *
	 * @return
	 */
	JSONArray getContentCatTreeData();

	/**
	 * 获取指定站点中所有栏目数据，treedata，站点为空返回当前站点数据
	 * 
	 * @param siteId
	 * @return
	 */
	JSONArray getContentCatTreeData(String siteId);

	/**
	 *获取站点下ztree需要的栏目数据，json中包含id/pId/name/open等节点
	 * @param siteId 站点
	 * @param selectCats 需要选中的栏目
     * @return
     */
	JSONArray getzTreeData(String siteId, Set<String> selectCats);

	/**
	 * 获取站点下所有栏目数据，返回字段比较简洁，针对ztree使用
	 * @param siteid 站点id
	 * @return
     */
	List<ContentCatVOTreeEntity> getSimpleAllBySite(String siteid);

	/**
	 * 过滤栏目权限
	 * @param list
     */
	void filterPCCatalogPermission(List<ContentCatVOTreeEntity> list);

	/**
	 * 获取指定站点下所有栏目id集合
	 * @param siteId
	 * @return
     */
	List<String> getAllIdList(String siteId);

	/**
	 * 根据指定栏目查询出所有子集启用的栏目id集合
	 * @param catalogPathIds 栏目id集，包含所有当前栏目的父级id和当前栏目id用逗号分隔
	 * @return
     */
	List<String> getOpenChildIdList(String catalogPathIds);

	/**
	 * 获取站点下栏目ztree节点，可以提供选中项
	 * @param set 选中的栏目节点id
	 * @param siteid 站点
	 * @return 栏目ztree节点数据
	 */
	JSONArray contentCatTree(Set<String> set, String siteid);
}

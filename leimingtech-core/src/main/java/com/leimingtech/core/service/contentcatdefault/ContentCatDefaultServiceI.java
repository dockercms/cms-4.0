package com.leimingtech.core.service.contentcatdefault;

import com.leimingtech.core.entity.ContentCatDefault;

import java.util.List;
import java.util.Map;

/**
 * @Title: interface
 * @Description: 会员默认栏目权限接口
 * @author
 * @date 2016-04-22 14:17:39
 * @version V1.0
 * 
 */
public interface ContentCatDefaultServiceI {

	/**
	 * 保存会员默认栏目权限
	 * 
	 * @param contentCatDefault
	 * @return
	 */
	java.lang.String save(ContentCatDefault contentCatDefault);

	/**
	 * 更新会员默认栏目权限
	 * 
	 * @param contentCatDefault
	 */
	void saveOrUpdate(ContentCatDefault contentCatDefault);

	/**
	 * 通过id获取会员默认栏目权限
	 * 
	 * @param id
	 *            会员默认栏目权限id
	 * @return
	 */
	ContentCatDefault getEntity(java.lang.String id);

	/**
	 * 获取分页后的会员默认栏目权限数据集
	 * 
	 * @param contentCatDefault
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return contentCatDefaultList会员默认栏目权限数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(ContentCatDefault contentCatDefault, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除会员默认栏目权限
	 * 
	 * @param contentCatDefault
	 */
	void delete(ContentCatDefault contentCatDefault);
	
	/**
	 * 删除跟此栏目相关的会员默认栏目权限
	 * 
	 * @param idArray
	 *            栏目id数据
	 */
	void deleteByContentCats(String[] idArray);

	/**
	 * 获取所有会员默认的栏目id
	 * @return
	 */
	List<String> getAllContentCatId();
}

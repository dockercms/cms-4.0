package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.leimingtech.core.entity.RoleSiteEntity;
import com.leimingtech.core.entity.SiteEntity;



/**
 * @Title: interface
 * @Description: 站点权限接口
 * @author
 * @date 2015-10-20 11:16:58
 * @version V1.0
 * 
 */
public interface RoleSiteServiceI {

	/**
	 * 保存站点权限
	 * 
	 * @param roleSite
	 * @return
	 */
	java.lang.String save(RoleSiteEntity roleSite);

	/**
	 * 更新站点权限
	 * 
	 * @param roleSite
	 */
	void saveOrUpdate(RoleSiteEntity roleSite);

	/**
	 * 通过id获取站点权限
	 * 
	 * @param id
	 *            站点权限id
	 * @return
	 */
	RoleSiteEntity getEntity(java.lang.String id);

	/**
	 * 通过站点id获取站点权限
	 * 
	 * @param id
	 *            站点权限id
	 * @return
	 */
	List<RoleSiteEntity> getRoleEntity(java.lang.String id);
	/**
	 * 获取分页后的站点权限数据集
	 * 
	 * @param roleSite
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return roleSiteList站点权限数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(RoleSiteEntity roleSite, Map param,
			int pageSize, int pageNo);

	/**
	 * 删除站点权限
	 * 
	 * @param roleSite
	 */
	void delete(RoleSiteEntity roleSite);

	/**
	 * 通过角色查出站点id集合
	 * 
	 * @param roleId
	 * @return
	 */
	Set<String> findSiteSetByRole(String roleId);

	/**
	 * 通过角色查找站点权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RoleSiteEntity> findSiteByRole(String roleId);

	/**
	 * 保存站点权限
	 * 
	 * @param roleId
	 *            角色id
	 * @param funVal
	 *            选中的站点
	 */
	public void saveSiteRole(String roleId, String funVal);

	/**
	 * 通过多个角色获取站点列表
	 * 
	 * @param roles
	 *            多个角色id
	 * @return
	 */
	List<SiteEntity> findSiteByRole(String[] roles);

}

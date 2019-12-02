package com.leimingtech.core.service;

import java.util.Map;

import com.leimingtech.core.entity.MemberDepart;

/**
 * @Title: interface
 * @Description: 会员与部门关联接口
 * @author
 * @date 2016-04-22 16:14:34
 * @version V1.0
 * 
 */
public interface MemberDepartServiceI {

	/**
	 * 保存会员与部门关联
	 * 
	 * @param memberDepart
	 * @return
	 */
	java.lang.String save(MemberDepart memberDepart);

	/**
	 * 更新会员与部门关联
	 * 
	 * @param memberDepart
	 */
	void saveOrUpdate(MemberDepart memberDepart);

	/**
	 * 通过id获取会员与部门关联
	 * 
	 * @param id
	 *            会员与部门关联id
	 * @return
	 */
	MemberDepart getEntity(java.lang.String id);

	/**
	 * 获取分页后的会员与部门关联数据集
	 * 
	 * @param memberDepart
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return memberDepartList会员与部门关联数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(MemberDepart memberDepart, Map param, int pageSize,
			int pageNo);

	/**
	 * 删除会员与部门关联
	 * 
	 * @param memberDepart
	 */
	void delete(MemberDepart memberDepart);

	/**
	 * 通过会员删除与部门之间的关联
	 * @param memberId
	 */
	void deleteByMember(String memberId);

	/**
	 * 根据部门删除会员跟部门间的关联
	 * @param departId
	 */
	void deleteByDepart(String departId);

	/**
	 * 获取会员的所有部门
	 * @param memberId
	 * @return
	 */
	String[] getMemberDeparts(String memberId);

}

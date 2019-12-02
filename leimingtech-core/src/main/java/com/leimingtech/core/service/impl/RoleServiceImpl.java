package com.leimingtech.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSRoleUser;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.RoleServiceI;
import com.leimingtech.core.service.RoleUserService;



/**
 * 角色管理接口实现
 * 
 * @author liuzhen 2015年8月28日 12:02:24
 * 
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleServiceI {

	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	/** 用户角色管理接口 */
	@Autowired
	private RoleUserService roleUserService;

	/**
	 * 通过id获取角色
	 * 
	 * @param roleId
	 * @return
	 */
	@Override
	public TSRole getEntity(String roleId) {
		return commonService.get(TSRole.class, roleId);
	}

	/**
	 * 通过用户id获取角色id数组
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	@Override
	public String[] getRoleIdArrayByUserId(String userId) {
		List<TSRoleUser> roleUserList = roleUserService
				.getRoleUserByuserId(userId);

		if (roleUserList == null || roleUserList.size() == 0)
			return null;

		String[] roleIds = new String[roleUserList.size()];

		for (int i = 0; i < roleUserList.size(); i++) {
			roleIds[i] = roleUserList.get(i).getTSRole().getId();
		}

		return roleIds;
	}

}

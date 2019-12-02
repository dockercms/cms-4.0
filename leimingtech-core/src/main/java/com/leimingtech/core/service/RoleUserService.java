package com.leimingtech.core.service;

import java.util.List;

import com.leimingtech.core.entity.TSRoleUser;


/**
 * 用户角色权限管理接口
 * 
 * @author liuzhen
 * 
 */
public interface RoleUserService {

	/**
	 * 通过用户id获取所有相关用户角色
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 */
	List<TSRoleUser> getRoleUserByuserId(String userid);

}

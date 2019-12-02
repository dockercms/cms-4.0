package com.leimingtech.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.TSRoleUser;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.RoleUserService;

/**
 * 用户角色权限管理接口
 * 
 * @author liuzhen
 * 
 */
@Service("roleUserService")
@Transactional
public class RoleUserServiceImpl implements RoleUserService {

	/** 公共dao接口 */
	@Autowired
	private CommonService commonService;

	@Override
	public List<TSRoleUser> getRoleUserByuserId(String userid) {
		return commonService.findByProperty(TSRoleUser.class, "TSUser.id",
				userid);
	}

}

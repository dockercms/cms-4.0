package com.leimingtech.platform.webservice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.leimingtech.core.entity.TSDepart;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.platform.webservice.IUser;
import com.leimingtech.platform.webservice.UserBean;

@WebService(endpointInterface = "com.leimingtech.platform.webservice.IUser", serviceName = "User")
public class UserImpl implements IUser {

	@Autowired
	private UserService userService;

	@Override
	public UserBean getUser(String userName) {
		TSUser user = userService.findUniqueByProperty(TSUser.class,
				"userName", userName);
		UserBean u = new UserBean();
		if(user!=null){
			try {
				MyBeanUtils.copyBean2Bean(u, user);
				u.setDepartid(user.getTSDepart().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return u;
	}

	@Override
	public List<UserBean> getUserList(String userName) {
		List<TSUser> userList = userService
		.findByQueryString("from TSUser where userName like '%"
				+ userName + "%'");
		List<UserBean> us = new ArrayList<UserBean>();
		if(userList.size()>0){
			try {
				for (TSUser tsUser : userList) {
					UserBean u = new UserBean();
					MyBeanUtils.copyBean2Bean(u, tsUser);
					u.setDepartid(tsUser.getTSDepart().getId());
					us.add(u);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return us;
	}

	@Override
	public boolean updateUser(UserBean u) {
		if(u==null){
			return false;
		}
		TSUser user = new TSUser();
		try {
			MyBeanUtils.copyBean2Bean(user, u);
			TSDepart depart = new TSDepart();
			depart.setId(u.getDepartid());
			user.setTSDepart(depart);
			userService.saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean insertUser(UserBean u) {
		if(u==null){
			return false;
		}
		TSUser user = new TSUser();
		try {
			MyBeanUtils.copyBean2Bean(user, u);
			TSDepart depart = new TSDepart();
			depart.setId(u.getDepartid());
			user.setTSDepart(depart);
			user.setCreatedtime(new Date());//创建时间
			userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delUser(String id) {
		try {
			userService.deleteEntityById(TSUser.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

package com.leimingtech.platform.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;




@WebService
public interface IUser {
	
	UserBean getUser(@WebParam(name="userName")String userName);
	List<UserBean> getUserList(@WebParam(name="userName")String userName);
	boolean updateUser(@WebParam(name="user")UserBean user);
	boolean insertUser(@WebParam(name="user")UserBean user);
	boolean delUser(@WebParam(name="id")String id);
	
}

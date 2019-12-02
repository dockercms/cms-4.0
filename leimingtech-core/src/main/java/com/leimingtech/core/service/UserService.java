package com.leimingtech.core.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSDepart;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.service.CommonService;


/**
 * 用户管理接口
 * 
 * @author
 * 
 */
public interface UserService extends CommonService {

	public TSUser checkUserExits(TSUser user);

	public String getUserRole(TSUser user);

	public void pwdInit(TSUser user, String newPwd);

	/**
	 * 判断这个角色是不是还有用户使用
	 * 
	 * @Author JueYue
	 * @date 2013-11-12
	 * @param id
	 * @return
	 */
	public int getUsersOfThisRole(String id);

	public Map mapByRoleId();

	/**
	 * 加载部门
	 * 
	 * @return
	 */
	public JSONArray departQuery(String userId);

	/**
	 * 部门子节点
	 * 
	 * @param depart
	 * @return
	 */
	public JSONArray getDepartChildren(TSDepart depart, TSUser user);

	/**
	 * 获得自己权限中的站点
	 * 
	 * @return
	 */
	public List<SiteEntity> getRoleSite();

	/**
	 * 获取用户真实姓名，真实姓名为空则返回用户名
	 * @param userId
	 * @return
     */
	String getRealName(String userId);
}

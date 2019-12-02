package com.leimingtech.core.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.leimingtech.core.entity.*;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.RoleServiceI;
import com.leimingtech.core.service.RoleSiteServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.UserService;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;




/**
 * 
 * @author
 * 
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	/** 公共dao接口 */
	@Autowired
	private CommonService commonService;
	/** 角色管理接口 */
	@Autowired
	private RoleServiceI roleService;
	/** 站点权限管理接口 */
	@Autowired
	private RoleSiteServiceI roleSiteService;

	@Autowired
	private SystemService systemService;

	public TSUser checkUserExits(TSUser user) {
		return this.systemService.checkUserExits(user);
	}

	public String getUserRole(TSUser user) {
		return this.commonDao.getUserRole(user);
	}

	public void pwdInit(TSUser user, String newPwd) {
		this.commonDao.pwdInit(user, newPwd);
	}

	public int getUsersOfThisRole(String id) {
		Criteria criteria = getSession().createCriteria(TSRoleUser.class);
		criteria.add(Restrictions.eq("TSRole.id", id));
		int allCounts = ((Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
		return allCounts;
	}

	@Override
	public Map mapByRoleId() {
		List<TSRole> cmstoprole = getList(TSRole.class);
		HashMap<String, String> map = new HashMap<String, String>();
		for (Iterator iterator = cmstoprole.iterator(); iterator.hasNext();) {
			TSRole tsrole = (TSRole) iterator.next();
			map.put(tsrole.getId().toString(), tsrole.getRoleName());
		}
		return map;
	}

	/**
	 * 加载部门树
	 * 
	 * @return
	 */
	@Override
	public JSONArray departQuery(String userId) {
		JSONArray jsonArray = new JSONArray();
		TSUser user = new TSUser();
		if (StringUtil.isNotEmpty(userId)) {
			user = get(TSUser.class, userId);
		}

		List<TSDepart> departList = loadAll(TSDepart.class);
		for (TSDepart depart : departList) {
			JSONObject json = new JSONObject();
			if (null == depart.getTSPDepart()) {
				json.put("id", depart.getId());
				json.put("name", depart.getDepartname());
				json.put("open", false);
				json.put("children", getDepartChildren(depart, user));
				if (null != user.getTSDepart()) {
					if (depart.getId().equals(user.getTSDepart().getId())) {
						json.put("checked", true);
					}
				}
				jsonArray.add(json);
			}
		}
		return jsonArray;
	}

	@Override
	public JSONArray getDepartChildren(TSDepart depart, TSUser user) {
		JSONArray jsonArray = new JSONArray();
		if (null == depart.getTSDeparts() || depart.getTSDeparts().size() == 0) {
			return jsonArray;
		}
		for (TSDepart depart1 : depart.getTSDeparts()) {
			/*
			 * 修改了下拉框的问题，把JSONObject json = new JSONObject();
			 * 更换了位置
			 */
			JSONObject json = new JSONObject();
			json.put("id", depart1.getId());
			json.put("name", depart1.getDepartname());
			json.put("open", false);
			json.put("children", getDepartChildren(depart1, user));
			if (null != user.getTSDepart()) {
				if (depart1.getId().equals(user.getTSDepart().getId())) {
					json.put("checked", true);
				}
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}

	/**
	 * 获得自己权限中的站点
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SiteEntity> getRoleSite() {
		TSUser user = PlatFormUtil.getSessionUser();
		//未登陆从session中取不到数据
		if(user==null){
			return null;
		}
		String[] roles = roleService.getRoleIdArrayByUserId(user.getId());
		List<SiteEntity> siteList =  roleSiteService.findSiteByRole(roles);
		return siteList;
	}

	/**
	 * 获取用户真实姓名，真实姓名为空则返回用户名
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public String getRealName(String userId) {
		CriteriaQuery cq = new CriteriaQuery(TSBaseUser.class);

		DetachedCriteria dc = cq.getDetachedCriteria();
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("userName").as("userName"));
		pList.add(Projections.property("realName").as("realName"));
		dc.setProjection(pList);

		dc.setResultTransformer(Transformers.aliasToBean(TSBaseUser.class));

		cq.eq("id", userId);
		cq.add();
		List<TSBaseUser> result = this.commonService.getListByCriteriaQuery(cq,
				false);
		if(result!=null && result.size()>0){
			TSBaseUser user=result.get(0);
			if(StringUtil.isNotEmpty(user.getRealName())){
				return user.getRealName();
			}

			return user.getUserName();
		}
		return "";
	}

}

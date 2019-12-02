package com.leimingtech.core.service.depart;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import com.leimingtech.core.entity.TSDepart;
import com.leimingtech.core.service.CommonService;

public interface DepartServiceI extends CommonService {

	/**
	 * 通过栏目父id获取栏目集合
	 * 
	 * @param id
	 *            栏目id
	 * @return 栏目集合
	 */
	List<TSDepart> getListByPid(String id);
	
	
	/***
	 * 获取全部一级数据
	 * 
	 * @return
	 */
	List<TSDepart> getAllRoot();
	
	
	JSONArray getTreeJson(String id);

	/**
	 * 保存部门和会员的关联关系
	 * @param departId
	 * @param memberIds
     */
	void saveDepartMember(String departId, String[] memberIds);

	 JSONArray loadChannelTree(String departId);
	 JSONArray contentCatTree(Set<String> set, String siteid);

	void saveDepartChannel(String departId, String funVal);

	void saveDefaultChannel(String funval);

	void saveDepartChannelSpread(String id, String funval);
	
	void delete(TSDepart tsDepart);
	
	JSONArray ztreeJson(Set<String> checkeds);

	/**
	 * 获取所有部门信息转为map，key为id，value为部门名称
	 * @return
	 */
	Map<String, String> getAllToMap();
}

package com.leimingtech.cms.service.contentcatpriv;

import net.sf.json.JSONArray;

import com.leimingtech.core.service.CommonService;

public interface ContentCatPrivServiceI extends CommonService{
	
	/**
	 * 加载PC栏目权限列表
	 * @param roleId
	 * @return
	 */
	public JSONArray loadContentCatTree(String roleId);

	/**
	 * 保存已勾选PC栏目权限
	 * @param roleId
	 * @param funVal
	 */
	public void saveContentCatPriv(String roleId,String funVal);
}

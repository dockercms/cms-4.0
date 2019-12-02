package com.leimingtech.mobile.service.mobilechannelpriv;

import net.sf.json.JSONArray;

import com.leimingtech.core.service.CommonService;

public interface MobileChannelPrivServiceI extends CommonService{
	/**
	 * 加载移动栏目权限列表
	 * @param roleId
	 * @return
	 */
	public JSONArray loadMobileChannelTree(String roleId);
	/**
	 * 保存已勾选移动栏目权限
	 * @param roleId
	 * @param funVal
	 */
	public void saveMobileChannelPriv(String roleId, String funVal);

}

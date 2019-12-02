package com.leimingtech.core.service;

import net.sf.json.JSONObject;

public interface VoteMobileServiceI extends CommonService{
	
	/**
	 * 获取所有投票列表
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public JSONObject getVoteMobileList(int pageSize,int pageNo);

}

package com.leimingtech.mobile.service.suggest;

import net.sf.json.JSONObject;

import com.leimingtech.core.service.CommonService;

public interface SuggestServiceI extends CommonService{
	public JSONObject getSuggest(String all,String content,String email,String userId);
}

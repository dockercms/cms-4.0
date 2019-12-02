package com.leimingtech.mobile.service.feedback;

import net.sf.json.JSONObject;

import com.leimingtech.core.service.CommonService;

public interface ForgetPasswordServiceI extends CommonService{
	/**
	 * 验证用户名，邮箱以及发送邮件
	 * @param userName
	 * @param email
	 * @return
	 */
	JSONObject getPassword(String username,String useremail);

}

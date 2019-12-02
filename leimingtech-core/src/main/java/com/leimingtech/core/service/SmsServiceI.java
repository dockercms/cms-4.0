package com.leimingtech.core.service;

import com.leimingtech.core.service.CommonService;

public interface SmsServiceI extends CommonService{
	
	/**
	 * 发短信 并生成日志
	 * @param mobile  not null
	 * @param msg    not null
	 * @param time  default null
	 * @return
	 */
	public String sendSMS(String mobile,String msg ,String time);
	
	/**
	 * 重新发送 并生成日志
	 * @param mobile  not null
	 * @param msg    not null
	 * @param time  default null
	 * @return
	 */
	public String reSendSMS(String mobile,String msg ,String parentid);

}

package com.leimingtech.core.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.log.SmsLogEntity;
import com.leimingtech.core.service.SmsServiceI;
import com.leimingtech.core.util.SMSUtil;

@Service("smsService")
@Transactional
public class SmsServiceImpl extends CommonServiceImpl implements SmsServiceI {

	@Override
	public String sendSMS(String mobile, String msg, String time) {
		String result=""; 
		try {
			result = SMSUtil.sendSms(mobile, msg, time);
			SmsLogEntity sms = new SmsLogEntity();
			sms.setMobile(mobile);
			sms.setSendtime(new Date());
			sms.setContent(msg);
			sms.setStatus(1);//成功
			
			if(result.contains("-"))
				sms.setStatus(0);//返回-12 为失败 其他文成功
			sms.setCreatedtime(new Date());//创建时间
			this.save(sms);
		} catch (Exception e) {
			e.printStackTrace();
			return "-12";
		}
		return result;
	}

	@Override
	public String reSendSMS(String mobile, String msg, String parentid) {
		String result=""; 
		try {
			result = SMSUtil.sendSms(mobile, msg, "");
			SmsLogEntity resms = new SmsLogEntity();
			resms.setId(parentid);
//			resms.setMobile(mobile);
//			resms.setSendtime(new Date());
//			resms.setContent(msg);
			resms.setStatus(1);//重新发送成功
			if(result.contains("-"))
				resms.setStatus(0);//返回-12 为失败 其他文成功
			resms.setIsresend(1);
			this.saveOrUpdate(resms);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "-12";
		}
		return null;
	}
	
}
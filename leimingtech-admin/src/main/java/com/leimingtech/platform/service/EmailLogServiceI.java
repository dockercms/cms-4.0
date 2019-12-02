package com.leimingtech.platform.service;

import java.util.List;

import com.leimingtech.core.service.CommonService;



/**
 * @author  :linjm linjianmao@gmail.com
 * @version :2014-5-13下午05:19:56
 **/
public interface EmailLogServiceI extends CommonService {
	
	/**
	 * 邮件发送接口
	 * @param List tomails
	 * @param String frommail
	 * @param String title
	 * @param String content
	 * @return
	 */
	public String sendMail(List<String> tomails, String frommail, String title, String content);
	
	/**
	 * 重新发送接口
	 * @param List tomails
	 * @param String frommail
	 * @param String title
	 * @param String content
	 * @return
	 */
	public String reSendMail(List<String> tomails, String frommail, String id, String content);

}

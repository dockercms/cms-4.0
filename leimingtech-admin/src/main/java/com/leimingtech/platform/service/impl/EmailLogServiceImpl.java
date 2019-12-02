package com.leimingtech.platform.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.PfConfigEntity;
import com.leimingtech.core.entity.log.EmailLogEntity;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.MailUtil;
import com.leimingtech.platform.service.EmailLogServiceI;


/**
 * @author  :linjm linjianmao@gmail.com
 * @version :2014-5-13下午05:21:30
 *  description :
 **/
@Service("emailLogService")
@Transactional
public class EmailLogServiceImpl extends CommonServiceImpl implements EmailLogServiceI {
	
	Map<String, String> mCache = PfConfigEntity.pfconfigCache;

	@Override
	public String sendMail(List<String> tomails, String frommail, String title, String content) {
//		frommail = ResourceUtil.getConfigByName("mailUsername");
		frommail = mCache.get("qq_mailUsername");
		MailUtil.sendMails(tomails, frommail, title, content);
		for(int i=0 ;i<tomails.size() ;i++){
			EmailLogEntity mail = new EmailLogEntity();
			mail.setFrommail(frommail);
			mail.setTitle(title);
			mail.setContent(content);
			mail.setTomail(tomails.get(i));
			mail.setStatus(1);
			mail.setSendtime(new Date());
			mail.setCreatedtime(new Date());//创建时间
			this.save(mail);
		}
		return null;
	}

	@Override
	public String reSendMail(List<String> tomails, String frommail,
			String id, String content) {
//		frommail = ResourceUtil.getConfigByName("mailUsername");
		frommail = mCache.get("qq_mailUsername");
		MailUtil.sendMails(tomails, frommail, id, content);
		for(int i=0 ;i<tomails.size() ;i++){
			EmailLogEntity mail = this.getEntity(EmailLogEntity.class, id);
			mail.setFrommail(frommail);
			mail.setId(id);
			mail.setContent(content);
			mail.setTomail(tomails.get(i));
			mail.setStatus(1);
			mail.setSendtime(new Date());
			mail.setIsresend(1);
			mail.setCreatedtime(new Date());//创建时间
			this.saveOrUpdate(mail);
		}
		return null;
	}

}

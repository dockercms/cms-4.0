package com.leimingtech.platform.service.impl.mail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.mail.MailServiceI;

@Service("mailService")
@Transactional
public class MailServiceImpl extends CommonServiceImpl implements MailServiceI {
	
}